package org.demo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
