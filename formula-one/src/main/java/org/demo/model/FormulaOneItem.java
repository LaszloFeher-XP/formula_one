package org.demo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormulaOneItem {

    @EqualsAndHashCode.Include
    UUID id;
    String name;
    String foundationYear;
    Integer championships;
    EntryFeeStatus entryFeeStatus;
}
