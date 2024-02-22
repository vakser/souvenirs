package org.example;

import org.junit.jupiter.params.provider.Arguments;
import org.example.model.Manufacturer;
import org.example.model.Souvenir;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;

public class TestDataProvider {

    public static Stream<Arguments> testReadManufacturersWithPricesLessThan() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Manufacturer(0L, "Souvenir factory", "Ukraine")
                        ),
                        2
                )
        );
    }

    public static Stream<Arguments> testReadManufacturersWhereSouvenirsOfYear() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Manufacturer(0L, "Souvenir factory", "Ukraine")
                        ),
                        2000
                )
        );
    }

    public static Stream<Arguments> testReadManufacturersWhereSouvenirsNameAndYear() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Manufacturer(-11L, "Potsdam GmBH", "Germany"),
                                new Manufacturer(-10L, "Meier und Sohn", "Germany")
                        ),
                        "Gartenzwerg",
                        1913
                )
        );
    }

    public static Stream<Arguments> testSaveManufacturer() {
        return Stream.of(
                Arguments.of(
                        new Manufacturer(-2L, "Zambian souvenirs", "Zambia")
                )
        );
    }

    public static Stream<Arguments> testReadAllByYear() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Souvenir(-1L, "Small doll",
                                        dateFormat.parse("2000-12-04"), 1, 0L)
                        ),
                        2000
                )
        );
    }

    public static Stream<Arguments> testReadAllByCountry() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Souvenir(0L, "Dream catcher",
                                        dateFormat.parse("2008-06-06"), 13, -1L)
                        ),
                        "Trinidad&Tobago"
                )
        );
    }

    public static Stream<Arguments> testReadAllByManufacturerId() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Souvenir(0L, "Dream catcher",
                                        dateFormat.parse("2008-06-06"), 13, -1L)
                        ),
                        -1L
                )
        );
    }
}
