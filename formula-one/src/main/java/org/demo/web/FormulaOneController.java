package org.demo.web;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.demo.model.DeleteResponse;
import org.demo.model.FormulaOneItem;
import org.demo.service.FormulaOneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("api/f1")
public class FormulaOneController {

    FormulaOneService formulaOneService;

    @GetMapping
    ResponseEntity<List<FormulaOneItem>> getTeams() {
        return ResponseEntity.ok(formulaOneService.getTeams());
    }

    @PostMapping("/team")
    public ResponseEntity<FormulaOneItem> addTeam(@RequestBody FormulaOneItem formulaOneItem) {
        return ResponseEntity.ok(formulaOneService.addTeam(formulaOneItem));
    }

    @PutMapping("/team")
    public ResponseEntity<FormulaOneItem> updateTeam(@RequestBody FormulaOneItem formulaOneItem) {
        return ResponseEntity.ok(formulaOneService.updateTeam(formulaOneItem));
    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<DeleteResponse> deleteTeam(@PathVariable("id") String id) {
        formulaOneService.deleteTeam(id);
        return ResponseEntity.ok(DeleteResponse.builder().message(String.format("Team deleted with id: %s", id)).build());
    }
}
