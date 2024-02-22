package org.example;

import org.example.model.Manufacturer;
import org.example.service.ManufacturerService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManufacturerServiceTest {

    private static ManufacturerService manufacturerService;

    @BeforeAll
    public static void setUp() {
        manufacturerService = ManufacturerService.getInstance();
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testSaveManufacturer")
    public void testSaveNonExistingManufacturer(Manufacturer expectedManufacturer) {
        Manufacturer actualManufacturer = manufacturerService.save(expectedManufacturer);
        assertEquals(expectedManufacturer.getName(), actualManufacturer.getName());
        assertEquals(expectedManufacturer.getCountry(), actualManufacturer.getCountry());
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testReadManufacturersWithPricesLessThan")
    public void testReadManufacturersWithPricesLessThan(List<Manufacturer> expected, double price) {
        List<Manufacturer> actual = manufacturerService.readManufacturersWithPricesLessThan(price);
        assertEquals(expected.getFirst().getName(), actual.getFirst().getName());
        assertEquals(expected.getFirst().getCountry(), actual.getFirst().getCountry());
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testReadManufacturersWhereSouvenirsOfYear")
    public void testReadManufacturersWhereSouvenirsOfYear(List<Manufacturer> expected, int year) {
        List<Manufacturer> actual = manufacturerService.readManufacturersWhereSouvenirsOfYear(year);
        assertEquals(expected.getFirst().getName(), actual.getFirst().getName());
        assertEquals(expected.getFirst().getCountry(), actual.getFirst().getCountry());
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testReadManufacturersWhereSouvenirsNameAndYear")
    public void testReadManufacturersWhereSouvenirsNameAndYear(List<Manufacturer> expected, String name, int year) {
        List<Manufacturer> actual = manufacturerService.readManufacturersWhereSouvenirsNameAndYear(name, year);
        assertEquals(expected.getFirst().getName(), actual.getFirst().getName());
        assertEquals(expected.getFirst().getCountry(), actual.getFirst().getCountry());
        assertEquals(expected.getLast().getName(), actual.getLast().getName());
        assertEquals(expected.getLast().getCountry(), actual.getLast().getCountry());
    }

    @AfterAll
    public static void deleteManufacturerAndSouvenirs() {
        long id = manufacturerService.readAll().getLast().getId();
        manufacturerService.deleteManufacturerAndSouvenirs(id);
    }
}
