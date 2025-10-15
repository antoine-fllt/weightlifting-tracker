package com.lifttrack.rest.mapper;

import com.lifttrack.rest.response.RecordResponse;
import com.lifttrack.model.Record;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {
    
    public RecordResponse toDTO(Record record) {
        return RecordResponse.builder()
            .id(record.getId())
            .exerciseId(record.getExercise().getId())
            .exerciseName(record.getExercise().getName())
            .weight(record.getWeight())
            .reps(record.getReps())
            .dateAchieved(record.getDateAchieved())
            .notes(record.getNotes())
            .createdAt(record.getCreatedAt().toLocalDate())
            .build();
    }
}
