package com.vp.fittrack.repositories;

import com.vp.fittrack.models.FoodItem;
import com.vp.fittrack.models.UserData;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

}
