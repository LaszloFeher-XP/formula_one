package org.demo.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.model.FormulaOneItem;
import org.demo.service.FormulaOneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api")
public class FormulaOneController {

    FormulaOneService formulaOneService;

    @PostMapping
    public ResponseEntity<FormulaOneItem> addTeam(@RequestBody FormulaOneItem formulaOneItem){
        return ResponseEntity.ok(formulaOneService.addTeam(formulaOneItem));
    }
}
