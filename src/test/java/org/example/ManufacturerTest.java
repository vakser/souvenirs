package org.example;

import org.junit.jupiter.api.Test;
import org.example.model.Manufacturer;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ManufacturerTest {

    @Test
    public void testFromString() {
        String line = "0, Souvenir Factory, Ukraine";
        Manufacturer expected = Manufacturer.builder()
                .id(0L)
                .name("Souvenir Factory")
                .country("Ukraine")
                .build();
        Manufacturer actual = Manufacturer.builder().build().fromCsvString(line, Manufacturer.class);
        assertEquals(expected.getId(), actual.getId(), "Ids must be equal");
        assertEquals(expected.getName(), actual.getName(), "Names must be equal");
        assertEquals(expected.getCountry(), actual.getCountry(), "Countries must be equal");
    }
}
