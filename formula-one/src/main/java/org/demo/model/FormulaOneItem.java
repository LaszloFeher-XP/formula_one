package org.demo.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FormulaOneItem {

    @EqualsAndHashCode.Include
    UUID id;
    String name;
    String foundationYear;
    Integer championships;
    EntryFeeStatus entryFeeStatus;

}
