package org.example;

import org.junit.jupiter.api.Test;
import org.example.model.Souvenir;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SouvenirTest {

    @Test
    public void testFromString() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String line = "0,Big doll,2024-02-02,20.0,0";
        Souvenir expected = Souvenir.builder()
                .id(0L)
                .name("Big doll")
                .manufacturingDate(dateFormat.parse("2024-02-02"))
                .price(20)
                .manufacturerId(0L)
                .build();
        Souvenir actual = Souvenir.builder().build().fromCsvString(line, Souvenir.class);
        assertEquals(expected.getId(), actual.getId(), "Ids must be equal");
        assertEquals(expected.getName(), actual.getName(), "Names must be equal");
        assertEquals(expected.getManufacturingYear(), actual.getManufacturingYear(), "Manufacturing years must be equal");
        assertEquals(expected.getPrice(), actual.getPrice(), "Prices must be equal");
        assertEquals(expected.getManufacturerId(), actual.getManufacturerId(), "Manufacturer ids must be equal");
    }
}
