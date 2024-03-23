package org.demo.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FormulaOneItem {

    UUID id;
    @EqualsAndHashCode.Include
    String name;
    String foundationYear;
    Integer championships;
    EntryFeeStatus entryFeeStatus;

}
