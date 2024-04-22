package com.vp.fittrack.services;

import com.vp.fittrack.exceptions.FoodNameExistException;
import com.vp.fittrack.dtos.FoodDatabaseDto;
import com.vp.fittrack.models.FoodDatabase;
import com.vp.fittrack.repositories.FoodDatabaseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodDatabaseService {

  private final FoodDatabaseRepository foodDatabaseRepository;

  @Autowired
  public FoodDatabaseService(FoodDatabaseRepository foodDatabaseRepository) {
    this.foodDatabaseRepository = foodDatabaseRepository;
  }

  public FoodDatabase saveFoodToDatabase(FoodDatabaseDto foodDatabaseDto) {
    List<FoodDatabase> foodDatabaseList = foodDatabaseRepository.findAll();
    for (FoodDatabase foodDatabase : foodDatabaseList) {
      if (foodDatabase.getName().equalsIgnoreCase(foodDatabaseDto.getName())) {
        throw new FoodNameExistException("Food with this name is already in database");
      }
    }
    FoodDatabase foodDatabase = new FoodDatabase(foodDatabaseDto.getName(), foodDatabaseDto.getProteins(), foodDatabaseDto.getCarbs(), foodDatabaseDto.getFats(), foodDatabaseDto.getCalories());
    foodDatabaseRepository.save(foodDatabase);
    return foodDatabase;
  }

  public FoodDatabase findFoodByName(String name) {
    List<FoodDatabase> foodDatabaseList = foodDatabaseRepository.findAll();
    for (FoodDatabase foodDatabase : foodDatabaseList) {
      if (foodDatabase.getName().equalsIgnoreCase(name)) {
        return foodDatabase;
      }
    }
    return null;
  }
}
