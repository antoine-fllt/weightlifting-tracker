package com.lifttrack.rest.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordRequest {
    @NotNull(message = "L'exercice est obligatoire")
    private Long exerciseId;
    
    @NotNull(message = "Le poids est obligatoire")
    @DecimalMin(value = "0.0", message = "Le poids doit être positif")
    private BigDecimal weight;
    
    @NotNull(message = "Le nombre de répétitions est obligatoire")
    @Min(value = 1, message = "Le nombre de répétitions doit être au moins 1")
    private Integer reps;
    
    @NotNull(message = "La date est obligatoire")
    @PastOrPresent(message = "La date ne peut pas être dans le futur")
    private LocalDate dateAchieved;
    
    @Size(max = 500, message = "Les notes ne peuvent pas dépasser 500 caractères")
    private String notes;
}
