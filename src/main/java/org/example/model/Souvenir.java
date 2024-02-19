package org.example.model;

import lombok.*;
import org.example.annotation.Property;

import java.time.ZoneId;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Souvenir extends CsvModel<Souvenir> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    @Property
    private Long id;
    @Property
    private String name;
    @Property
    private Date manufacturingDate;
    @Property
    private double price;
    @Property
    private Long manufacturerId;

    public int getManufacturingYear() {
        return getManufacturingDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().getYear();
    }
}