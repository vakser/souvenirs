package org.example.repository;

import org.example.model.Souvenir;

import java.util.List;

public class SouvenirFileRepository extends AbstractFileRepository<Souvenir> {

    private static SouvenirFileRepository INSTANCE = getInstance();
    private static final String PATH = "src/main/resources/souvenirs.csv";

    public static SouvenirFileRepository getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (SouvenirFileRepository.class) {
            if (INSTANCE == null) {
                INSTANCE = new SouvenirFileRepository();
            }
        }
        return INSTANCE;
    }

    @Override
    String filePropertiesHeader() {
        return "id, name, manufacturing date, price, manufacturerId";
    }

    @Override
    Souvenir fromString(String line) {
        return new Souvenir().fromCsvString(line, Souvenir.class);
    }

    @Override
    String toString(Souvenir souvenir) {
        return souvenir.toCsvString(Souvenir.class);
    }

    @Override
    String getFilePath() {
        return PATH;
    }

    @Override
    long generateUniqueId() {
        List<Souvenir> souvenirs = readAll();
        long id = 1;
        if (!souvenirs.isEmpty()) {
            id = souvenirs.getLast().getId() + 1;
        }
        return id;
    }
}
