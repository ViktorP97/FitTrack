package com.vp.fittrack.repositories;

import com.vp.fittrack.models.Exercise;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

  Optional<Exercise> findById(Long id);
}
