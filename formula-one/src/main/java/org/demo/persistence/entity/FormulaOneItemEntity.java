package org.demo.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.demo.model.EntryFeeStatus;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "formula")
public class FormulaOneItemEntity {

    @Id
    UUID id = UUID.randomUUID();

    @Column
    @NotNull
    @NotEmpty
    String name;

    @Column
    @NotNull
    @NotEmpty
    String foundationYear;

    @Column
    @NotNull
    @Min(0)
    @Max(100)
    Integer championships;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    EntryFeeStatus entryFeeStatus;

    public FormulaOneItemEntity apply(FormulaOneItemEntity formulaOneItemEntity) {
        return formulaOneItemEntity;
    }
}
