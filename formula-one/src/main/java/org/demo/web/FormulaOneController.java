package org.demo.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.model.FormulaOneItem;
import org.demo.service.FormulaOneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/team")
public class FormulaOneController {

    FormulaOneService formulaOneService;

    @GetMapping
    ResponseEntity<List<FormulaOneItem>> getTeams(){
        return ResponseEntity.ok(formulaOneService.getTeams());
    }

    @PostMapping
    public ResponseEntity<FormulaOneItem> addTeam(@RequestBody FormulaOneItem formulaOneItem){
        return ResponseEntity.ok(formulaOneService.addTeam(formulaOneItem));
    }

    @PatchMapping
    public ResponseEntity<FormulaOneItem> updateTeam(@RequestBody FormulaOneItem formulaOneItem){
        return ResponseEntity.ok(formulaOneService.updateTeam(formulaOneItem));
    }

    @DeleteMapping("/{id}")
    public void deleteTeam(@PathVariable("id") String id) {
        formulaOneService.deleteTeam(id);
    }
}
