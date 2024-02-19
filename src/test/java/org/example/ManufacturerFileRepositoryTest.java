package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.example.repository.ManufacturerFileRepository;
import org.example.model.Manufacturer;

import static org.junit.jupiter.api.Assertions.*;

public class ManufacturerFileRepositoryTest {

    private static ManufacturerFileRepository manufacturerFileRepository;

    @BeforeAll
    public static void setUp() {
        manufacturerFileRepository = ManufacturerFileRepository.getInstance();
    }

    @Test
    public void addTest() {
        Manufacturer expected = Manufacturer.builder()
                .name("DHL")
                .country("Germany")
                .build();
        Manufacturer actual = manufacturerFileRepository.add(expected);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getCountry(), actual.getCountry());
    }

    @Test
    public void readTest() {
        Long manufacturerId = manufacturerFileRepository.readAll().getLast().getId();
        Manufacturer expected = Manufacturer.builder()
                .id(manufacturerId)
                .name("DHL")
                .country("Germany")
                .build();
        Manufacturer actual = manufacturerFileRepository.read(manufacturerId);
        assertEquals(expected.getName(), actual.getName(), "Names must be equal.");
        assertEquals(expected.getCountry(), actual.getCountry(), "Countries must be equal");
    }

    @Test
    public void updateTest() {
        Long manufacturerId = manufacturerFileRepository.readAll().getLast().getId();
        Manufacturer expected = Manufacturer.builder()
                .id(manufacturerId)
                .name("Seagate")
                .country("USA")
                .build();
        assertTrue(manufacturerFileRepository.update(expected));
        Manufacturer actual = manufacturerFileRepository.read(manufacturerId);
        assertEquals(expected.getName(), actual.getName(), "Names must be equal.");
        assertEquals(expected.getCountry(), actual.getCountry(), "Countries must be equal");
    }

    @Test
    public void deleteTest() {
        Long manufacturerId = manufacturerFileRepository.readAll().getLast().getId();
        assertTrue(manufacturerFileRepository.delete(manufacturerId));
        assertNull(manufacturerFileRepository.read(manufacturerId));
        assertFalse(manufacturerFileRepository.delete(manufacturerId));
    }
}
