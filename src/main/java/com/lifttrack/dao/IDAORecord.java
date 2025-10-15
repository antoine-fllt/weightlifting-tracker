package com.lifttrack.dao;

import com.lifttrack.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IDAORecord extends JpaRepository<Record, Long>{
    List<Record> findByUserIdOrderByDateAchievedDesc(Long userId);
    
    List<Record> findByUserIdAndExerciseIdOrderByDateAchievedDesc(Long userId, Long exerciseId);
    
    @Query("SELECT r FROM Record r WHERE r.user.id = :userId " +
           "AND r.exercise.id = :exerciseId " +
           "ORDER BY r.weight DESC, r.dateAchieved DESC")
    List<Record> findMaxWeightByUserAndExercise(Long userId, Long exerciseId);
    
}
