package com.lifttrack.rest.controller;

import com.lifttrack.model.Exercise;
import com.lifttrack.dao.IDAOExercice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ExerciseController {
    private final IDAOExercice daoExercice;
    
    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(daoExercice.findAllByOrderByNameAsc());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExercise(@PathVariable Long id) {
        return daoExercice.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Exercise>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(daoExercice.findByCategory(category));
    }
}