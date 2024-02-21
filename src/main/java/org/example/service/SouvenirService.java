package org.example.service;

import org.example.model.Manufacturer;
import org.example.model.Souvenir;
import org.example.repository.FileRepository;
import org.example.repository.SouvenirFileRepository;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class SouvenirService implements Service<Souvenir> {

    private static SouvenirService INSTANCE = getInstance();
    private final FileRepository<Souvenir> fileRepository;
    private Service<Manufacturer> manufacturerService;

    private SouvenirService() {
        this.fileRepository = SouvenirFileRepository.getInstance();
    }

    public static SouvenirService getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (SouvenirService.class) {
            if (INSTANCE == null) {
                INSTANCE = new SouvenirService();
                INSTANCE.setManufacturerService(ManufacturerService.getInstance());
            }
        }
        return INSTANCE;
    }

    private void setManufacturerService(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    public List<Souvenir> readAllByManufacturerId(long id) {
        return readAll(souvenir -> souvenir.getManufacturerId().equals(id));
    }

    public List<Souvenir> readAllByCountry(String country) {
        List<Long> manufacturerIds = manufacturerService
                .readAll(manufacturer -> manufacturer.getCountry().equals(country))
                .stream().map(Manufacturer::getId)
                .toList();
        return readAll(souvenir -> manufacturerIds.contains(souvenir.getManufacturerId()));
    }

    public List<Souvenir> readAllByYear(int year) {
        return readAll(souvenir -> souvenir.getManufacturingYear() == year);
    }

    public List<Souvenir> readAllByNameAndYear(String name, int year) {
        return readAll(souvenir -> souvenir.getName().equals(name) && souvenir.getManufacturingYear() == year);
    }

    @Override
    public Souvenir save(Souvenir souvenir) {
        List<Souvenir> existingSouvenirs = fileRepository.readAll();
        for (Souvenir existingSouvenir : existingSouvenirs) {
            if (existingSouvenir.getName().equals(souvenir.getName()) &&
                    existingSouvenir.getManufacturingDate().equals(souvenir.getManufacturingDate()) &&
                    existingSouvenir.getPrice() == souvenir.getPrice() &&
                    Objects.equals(existingSouvenir.getManufacturerId(), souvenir.getManufacturerId())) {
                throw new RuntimeException("Such souvenir already exists!");
            }
        }
        return fileRepository.add(souvenir);
    }

    @Override
    public boolean update(Souvenir souvenir) {
        return fileRepository.update(souvenir);
    }

    @Override
    public Souvenir read(Long id) {
        return fileRepository.read(id);
    }

    @Override
    public List<Souvenir> readAll() {
        return fileRepository.readAll();
    }

    @Override
    public List<Souvenir> readAll(Predicate<Souvenir> predicate) {
        return fileRepository.readAll(predicate);
    }

    @Override
    public boolean delete(Long id) {
        return fileRepository.delete(id);
    }

    @Override
    public boolean delete(Predicate<Souvenir> predicate) {
        return fileRepository.delete(predicate);
    }
}
