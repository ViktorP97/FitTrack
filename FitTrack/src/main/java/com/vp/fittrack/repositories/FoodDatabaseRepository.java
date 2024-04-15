package com.vp.fittrack.repositories;

import com.vp.fittrack.models.FoodDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodDatabaseRepository extends JpaRepository<FoodDatabase, Long> {

}
