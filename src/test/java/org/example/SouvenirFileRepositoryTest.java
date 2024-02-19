package org.example;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.example.repository.SouvenirFileRepository;
import org.example.model.Souvenir;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;

public class SouvenirFileRepositoryTest {

    private static SouvenirFileRepository souvenirFileRepository;

    @BeforeAll
    public static void setUp() {
        souvenirFileRepository = SouvenirFileRepository.getInstance();
    }

    @Test
    public void addTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long souvenirId = souvenirFileRepository.readAll().getLast().getId() + 1;
        Souvenir expected = Souvenir.builder()
                .id(souvenirId)
                .name("Big doll")
                .manufacturingDate(dateFormat.parse("2024-02-02"))
                .price(50)
                .manufacturerId(0L)
                .build();
        Souvenir actual = souvenirFileRepository.add(expected);
        assertNotEquals(0, actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getManufacturingDate(), actual.getManufacturingDate());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    public void readTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long souvenirId = souvenirFileRepository.readAll().getLast().getId();
        Souvenir expected = Souvenir.builder()
                .id(souvenirId)
                .name("Big doll")
                .manufacturingDate(dateFormat.parse("2024-02-02"))
                .price(50)
                .manufacturerId(0L)
                .build();
        Souvenir actual = souvenirFileRepository.read(souvenirId);
        assertEquals(expected.getId(), actual.getId(), "Ids must be equal.");
        assertEquals(expected.getName(), actual.getName(), "Names must be equal.");
        assertEquals(expected.getManufacturingDate(), actual.getManufacturingDate(), "Manufacturing dates must be equal.");
        assertEquals(expected.getPrice(), actual.getPrice(), "Prices must be equal.");
        assertEquals(expected.getManufacturerId(), actual.getManufacturerId(), "Manufacturer ids must be equal");
        assertNull(souvenirFileRepository.read(souvenirId + 1));
    }

    @Test
    public void updateTest() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Long souvenirId = souvenirFileRepository.readAll().getLast().getId();
        Souvenir expected = Souvenir.builder()
                .id(souvenirId)
                .name("Big toy")
                .manufacturingDate(dateFormat.parse("2020-12-12"))
                .price(100)
                .manufacturerId(0L)
                .build();
        souvenirFileRepository.update(expected);
        Souvenir actual = souvenirFileRepository.read(souvenirId);
        assertEquals(expected.getId(), actual.getId(), "Ids must be equal.");
        assertEquals(expected.getName(), actual.getName(), "Names must be equal.");
        assertEquals(expected.getManufacturingDate(), actual.getManufacturingDate(), "Manufacturing dates must be equal.");
        assertEquals(expected.getPrice(), actual.getPrice(), "Prices must be equal.");
        assertEquals(expected.getManufacturerId(), actual.getManufacturerId(), "Manufacturer ids must be equal");
        assertFalse(souvenirFileRepository.update(new Souvenir()));
    }

    @Test
    public void deleteTest() {
        Long souvenirId = souvenirFileRepository.readAll().getLast().getId();
        assertTrue(souvenirFileRepository.delete(souvenirId));
        assertNull(souvenirFileRepository.read(souvenirId));
        assertFalse(souvenirFileRepository.delete(souvenirId));
    }
}
