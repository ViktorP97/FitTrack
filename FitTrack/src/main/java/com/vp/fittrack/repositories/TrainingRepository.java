package com.vp.fittrack.repositories;

import com.vp.fittrack.models.Training;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

  Optional<Training> findById(Long id);
}
