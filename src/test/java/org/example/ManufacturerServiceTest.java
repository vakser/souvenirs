package org.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.example.model.Manufacturer;
import org.example.model.Souvenir;
import org.example.service.ManufacturerService;
import org.example.service.SouvenirService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManufacturerServiceTest {

    private static ManufacturerService manufacturerService;
    private static SouvenirService souvenirService;

    @BeforeAll
    public static void setUp() {
        manufacturerService = ManufacturerService.getInstance();
        souvenirService = SouvenirService.getInstance();
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testSaveManufacturerAndSouvenir")
    public void testSaveManufacturerAndSouvenir(Manufacturer expectedManufacturer, Souvenir expectedSouvenir) {
        Long manufacturerId = manufacturerService.readAll().getLast().getId() + 1;
        expectedManufacturer.setId(manufacturerId);
        Long souvenirId = souvenirService.readAll().getLast().getId() + 1;
        expectedSouvenir.setId(souvenirId);
        expectedSouvenir.setManufacturerId(manufacturerId);
        Manufacturer actualManufacturer = manufacturerService.save(expectedManufacturer);
        Souvenir actualSouvenir = souvenirService.save(expectedSouvenir);
        assertEquals(expectedManufacturer.getName(), actualManufacturer.getName());
        assertEquals(expectedManufacturer.getCountry(), actualManufacturer.getCountry());
        assertEquals(expectedSouvenir.getName(), actualSouvenir.getName());
        assertEquals(expectedSouvenir.getManufacturerId(), actualSouvenir.getManufacturerId());
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
