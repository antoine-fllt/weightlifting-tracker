package com.lifttrack.service;

import com.lifttrack.dao.IDAORecord;
import com.lifttrack.model.Record;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {
    private final IDAORecord daoRecord;

    @Transactional(readOnly = true)
    public List<Record> getUserRecords(Long userId) {
        return daoRecord.findByUserIdOrderByDateAchievedDesc(userId);
    }
    
    @Transactional(readOnly = true)
    public List<Record> getExerciseHistory(Long userId, Long exerciseId) {
        return daoRecord.findByUserIdAndExerciseIdOrderByDateAchievedDesc(userId, exerciseId);
    }
    
    @Transactional(readOnly = true)
    public Map<String, Object> getPersonalBests(Long userId) {
        List<Record> allRecords = daoRecord.findByUserIdOrderByDateAchievedDesc(userId);
        
        Map<Long, Record> bestByExercise = allRecords.stream()
            .collect(Collectors.toMap(
                r -> r.getExercise().getId(),
                r -> r,
                (r1, r2) -> r1.getWeight().compareTo(r2.getWeight()) > 0 ? r1 : r2
            ));
        
        return Map.of("personalBests", new ArrayList<>(bestByExercise.values()));
    }
    
    @Transactional
    public Record addRecord(Record record) {
        return daoRecord.save(record);
    }
    
    @Transactional
    public void deleteRecord(Long recordId) {
        daoRecord.deleteById(recordId);
    }
    
    @Transactional
    public Record updateRecord(Long recordId, Record updatedRecord) {
        Record existing = daoRecord.findById(recordId)
            .orElseThrow(() -> new RuntimeException("Record not found"));
        
        existing.setWeight(updatedRecord.getWeight());
        existing.setReps(updatedRecord.getReps());
        existing.setDateAchieved(updatedRecord.getDateAchieved());
        existing.setNotes(updatedRecord.getNotes());
        
        return daoRecord.save(existing);
    }
    
}
