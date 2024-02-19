package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.example.model.Souvenir;
import org.example.service.SouvenirService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SouvenirServiceTest {

    private static SouvenirService souvenirService;

    @BeforeAll
    public static void setUp() {
        souvenirService = SouvenirService.getInstance();
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testReadAllByYear")
    public void testReadAllByYear(List<Souvenir> expected, int year) {
        List<Souvenir> actual = souvenirService.readAllByYear(year);
        assertEquals(expected.getFirst().getId(), actual.getFirst().getId());
        assertEquals(expected.getFirst().getName(), actual.getFirst().getName());
        assertEquals(expected.getFirst().getManufacturingYear(), actual.getFirst().getManufacturingYear());
        assertEquals(expected.getFirst().getPrice(), actual.getFirst().getPrice());
        assertEquals(expected.getFirst().getManufacturerId(), actual.getFirst().getManufacturerId());
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testReadAllByCountry")
    public void testReadAllByCountry(List<Souvenir> expected, String country) {
        List<Souvenir> actual = souvenirService.readAllByCountry(country);
        assertEquals(expected.getFirst().getId(), actual.getFirst().getId());
        assertEquals(expected.getFirst().getName(), actual.getFirst().getName());
        assertEquals(expected.getFirst().getManufacturingYear(), actual.getFirst().getManufacturingYear());
        assertEquals(expected.getFirst().getPrice(), actual.getFirst().getPrice());
        assertEquals(expected.getFirst().getManufacturerId(), actual.getFirst().getManufacturerId());
    }

    @ParameterizedTest
    @MethodSource("org.example.TestDataProvider#testReadAllByManufacturerId")
    public void testReadAllByManufacturerId(List<Souvenir> expected, long manufacturerId) {
        List<Souvenir> actual = souvenirService.readAllByManufacturerId(manufacturerId);
        assertEquals(expected.getFirst().getId(), actual.getFirst().getId());
        assertEquals(expected.getFirst().getName(), actual.getFirst().getName());
        assertEquals(expected.getFirst().getManufacturingYear(), actual.getFirst().getManufacturingYear());
        assertEquals(expected.getFirst().getPrice(), actual.getFirst().getPrice());
        assertEquals(expected.getFirst().getManufacturerId(), actual.getFirst().getManufacturerId());
    }
}
