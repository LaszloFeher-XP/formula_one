package org.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormulaOneItem {
    UUID id;
    String name;
    String foundationYear;
    Integer championships;
    EntryFeeStatus entryFeeStatus;
}
