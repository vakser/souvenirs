package org.example.model;

import lombok.*;
import org.example.annotation.Property;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Manufacturer extends CsvModel<Manufacturer> {
    @Property
    private Long id;
    @Property
    private String name;
    @Property
    private String country;

}
