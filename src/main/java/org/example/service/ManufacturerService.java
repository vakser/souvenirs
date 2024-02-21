package org.example.service;

import org.example.model.Manufacturer;
import org.example.model.Souvenir;
import org.example.repository.FileRepository;
import org.example.repository.ManufacturerFileRepository;

import java.util.List;
import java.util.function.Predicate;

public class ManufacturerService implements Service<Manufacturer> {

    private static ManufacturerService INSTANCE = getInstance();
    private final FileRepository<Manufacturer> fileRepository;
    private SouvenirService souvenirService;

    private ManufacturerService() {
        this.fileRepository = ManufacturerFileRepository.getInstance();
    }

    public static ManufacturerService getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (ManufacturerService.class) {
            if (INSTANCE == null) {
                INSTANCE = new ManufacturerService();
                INSTANCE.setSouvenirService(SouvenirService.getInstance());
            }
        }
        return INSTANCE;
    }

    private void setSouvenirService(SouvenirService souvenirService) {
        this.souvenirService = souvenirService;
    }

    public List<Manufacturer> readManufacturersWithPricesLessThan(double price) {
        List<Long> manufacturerIds = souvenirService.readAll(souvenir -> souvenir.getPrice() < price)
                .stream()
                .map(Souvenir::getManufacturerId)
                .distinct()
                .toList();
        return readAll(manufacturer -> manufacturerIds.contains(manufacturer.getId()));
    }

    public List<Manufacturer> readManufacturersWhereSouvenirsOfYear(int year) {
        List<Long> manufacturerIds = souvenirService.readAllByYear(year).stream()
                .map(Souvenir::getManufacturerId)
                .distinct()
                .toList();
        return readAll(manufacturer -> manufacturerIds.contains(manufacturer.getId()));
    }

    public List<Manufacturer> readManufacturersWhereSouvenirsNameAndYear(String name, int year) {
        List<Long> manufacturerIds = souvenirService.readAllByNameAndYear(name, year).stream()
                .map(Souvenir::getManufacturerId)
                .distinct()
                .toList();
        return readAll(manufacturer -> manufacturerIds.contains(manufacturer.getId()));
    }

    public boolean deleteManufacturerAndSouvenirs(long id) {
        boolean manufacturerDeleted = delete(id);
        boolean souvenirsDeleted = false;
        if (manufacturerDeleted) {
            souvenirsDeleted = souvenirService.delete(souvenir -> souvenir.getManufacturerId().equals(id));
        }
        return manufacturerDeleted && souvenirsDeleted;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        List<Manufacturer> existingManufacturers = fileRepository.readAll();
        for (Manufacturer existingManufacturer : existingManufacturers) {
            if (existingManufacturer.getName().equals(manufacturer.getName()) &&
                    existingManufacturer.getCountry().equals(manufacturer.getCountry())) {
                throw new RuntimeException("Such manufacturer already exists!");
            }
        }
        return fileRepository.add(manufacturer);
    }

    @Override
    public boolean update(Manufacturer manufacturer) {
        return fileRepository.update(manufacturer);
    }

    @Override
    public Manufacturer read(Long id) {
        return fileRepository.read(id);
    }

    @Override
    public List<Manufacturer> readAll() {
        return fileRepository.readAll();
    }

    @Override
    public List<Manufacturer> readAll(Predicate<Manufacturer> predicate) {
        return fileRepository.readAll(predicate);
    }

    @Override
    public boolean delete(Long id) {
        return fileRepository.delete(id);
    }

    @Override
    public boolean delete(Predicate<Manufacturer> predicate) {
        return fileRepository.delete(predicate);
    }

}
