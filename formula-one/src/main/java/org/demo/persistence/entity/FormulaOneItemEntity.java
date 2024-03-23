package org.demo.persistence.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
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
    String name;

    @Column
    String foundationYear;

    @Column
    Integer championships;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    EntryFeeStatus entryFeeStatus;

    public FormulaOneItemEntity apply(FormulaOneItemEntity formulaOneItemEntity) {
        return formulaOneItemEntity;
    }
}
