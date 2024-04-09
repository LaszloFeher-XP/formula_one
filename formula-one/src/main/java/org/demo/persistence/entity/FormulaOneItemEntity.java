package org.demo.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.model.EntryFeeStatus;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "formula")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FormulaOneItemEntity {

    @Id
    UUID id = UUID.randomUUID();

    @Column
    @NotNull(message="Team Name must be specified")
    @NotEmpty(message="Team Name must be specified")
    String name;

    @Column
    @NotNull(message="Foundation Year must be specified")
    @NotEmpty(message="Foundation Year must be specified")
    String foundationYear;

    @Column
    @NotNull(message="Number of Championships must be specified")
    @Min(value = 0, message = "Minimum value is 0")
    @Max(value = 100, message = "Maximum value is 100")
    Integer championships;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message="Entry Fee Status must be specified")
    EntryFeeStatus entryFeeStatus;

    public FormulaOneItemEntity apply(FormulaOneItemEntity formulaOneItemEntity) {
        return formulaOneItemEntity;
    }
}
