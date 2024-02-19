package org.example.repository;

import org.example.model.Manufacturer;

import java.util.List;

public class ManufacturerFileRepository extends AbstractFileRepository<Manufacturer> {

    private static ManufacturerFileRepository INSTANCE = getInstance();
    private static final String PATH = "src/main/resources/manufacturers.csv";

    public static ManufacturerFileRepository getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (ManufacturerFileRepository.class) {
            if (INSTANCE == null) {
                INSTANCE = new ManufacturerFileRepository();
            }
        }
        return INSTANCE;
    }

    @Override
    String filePropertiesHeader() {
        return "id, name, country";
    }

    @Override
    Manufacturer fromString(String line) {
        return new Manufacturer().fromCsvString(line, Manufacturer.class);
    }

    @Override
    public String toString(Manufacturer manufacturer) {
        return manufacturer.toCsvString(Manufacturer.class);
    }

    @Override
    String getFilePath() {
        return PATH;
    }

    @Override
    long generateUniqueId() {
        List<Manufacturer> manufacturers = readAll();
        long id = 1;
        if (!manufacturers.isEmpty()) {
            id = manufacturers.getLast().getId() + 1;
        }
        return id;
    }
}
