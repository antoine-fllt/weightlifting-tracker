package com.lifttrack.dao;

import com.lifttrack.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IDAOExercice extends JpaRepository<Exercise, Long> {
    List<Exercise> findAllByOrderByNameAsc();
}
