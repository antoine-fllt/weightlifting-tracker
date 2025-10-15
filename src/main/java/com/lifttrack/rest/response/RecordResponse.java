package com.lifttrack.rest.response;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecordResponse {
    private Long id;
    private Long exerciseId;
    private String exerciseName;
    private BigDecimal weight;
    private Integer reps;
    private LocalDate dateAchieved;
    private String notes;
    private LocalDate createdAt;
}
