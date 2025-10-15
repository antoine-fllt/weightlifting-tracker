package com.lifttrack.rest.controller;

import com.lifttrack.model.Exercise;
import com.lifttrack.model.User;
import com.lifttrack.model.Record;
import com.lifttrack.rest.mapper.RecordMapper;
import com.lifttrack.rest.request.RecordRequest;
import com.lifttrack.rest.response.RecordResponse;
import com.lifttrack.service.RecordService;
import com.lifttrack.dao.IDAOExercice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RecordController {
    private final RecordService recordService;
    private final RecordMapper recordMapper;
    private final IDAOExercice daoExercice;
    
    @GetMapping
    public ResponseEntity<List<RecordResponse>> getUserRecords(Authentication auth) {
        Long userId = getUserIdFromAuth(auth);
        List<Record> records = recordService.getUserRecords(userId);
        List<RecordResponse> dtos = records.stream()
            .map(recordMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/exercise/{exerciseId}")
    public ResponseEntity<List<RecordResponse>> getExerciseHistory(
            @PathVariable Long exerciseId,
            Authentication auth) {
        Long userId = getUserIdFromAuth(auth);
        List<Record> records = recordService.getExerciseHistory(userId, exerciseId);
        List<RecordResponse> dtos = records.stream()
            .map(recordMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/personal-bests")
    public ResponseEntity<Map<String, Object>> getPersonalBests(Authentication auth) {
        Long userId = getUserIdFromAuth(auth);
        Map<String, Object> pbs = recordService.getPersonalBests(userId);
        
        // Convertir les records en DTOs
        @SuppressWarnings("unchecked")
        List<Record> records = (List<Record>) pbs.get("personalBests");
        List<RecordResponse> dtos = records.stream()
            .map(recordMapper::toDTO)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(Map.of("personalBests", dtos));
    }
    
    @PostMapping
    public ResponseEntity<RecordResponse> addRecord(
            @Valid @RequestBody RecordRequest request,
            Authentication auth) {
        Long userId = getUserIdFromAuth(auth);
        
        // Récupérer l'exercice
        Exercise exercise = daoExercice.findById(request.getExerciseId())
            .orElseThrow(() -> new RuntimeException("Exercice non trouvé"));
        
        // Créer le record
        User user = User.builder().id(userId).build();
        Record record = Record.builder()
            .user(user)
            .exercise(exercise)
            .weight(request.getWeight())
            .reps(request.getReps())
            .dateAchieved(request.getDateAchieved())
            .notes(request.getNotes())
            .build();
        
        Record savedRecord = recordService.addRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(recordMapper.toDTO(savedRecord));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RecordResponse> updateRecord(
            @PathVariable Long id,
            @Valid @RequestBody RecordRequest request,
            Authentication auth) {
        
        Exercise exercise = daoExercice.findById(request.getExerciseId())
            .orElseThrow(() -> new RuntimeException("Exercice non trouvé"));
        
        Record record = Record.builder()
            .exercise(exercise)
            .weight(request.getWeight())
            .reps(request.getReps())
            .dateAchieved(request.getDateAchieved())
            .notes(request.getNotes())
            .build();
        
        Record updatedRecord = recordService.updateRecord(id, record);
        return ResponseEntity.ok(recordMapper.toDTO(updatedRecord));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id, Authentication auth) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
    
    private Long getUserIdFromAuth(Authentication auth) {
        // Pour le développement initial, retourner 1
        // À remplacer par l'extraction du JWT plus tard
        return 1L;
    }
}