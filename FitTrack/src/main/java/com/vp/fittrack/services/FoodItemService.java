package com.vp.fittrack.services;

import com.vp.fittrack.dtos.FoodItemDto;
import com.vp.fittrack.models.FoodItem;
import com.vp.fittrack.models.UserData;
import com.vp.fittrack.repositories.FoodItemRepository;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodItemService {

  private final FoodItemRepository foodItemRepository;

  private final UserDataService userDataService;

  @Autowired
  public FoodItemService(FoodItemRepository foodItemRepository, UserDataService userDataService) {
    this.foodItemRepository = foodItemRepository;
    this.userDataService = userDataService;
  }

  public FoodItem saveFood(Long id, FoodItemDto foodItemDto) {
    UserData user = userDataService.findUserById(id);
    if (foodItemDto.getDate().equals("Today")) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
      LocalDate currentDate = LocalDate.now();
      return saveWithDate(user, currentDate, foodItemDto);
    } else {
//      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
      String[] dateParts = foodItemDto.getDate().split("\\.");
      int day = Integer.parseInt(dateParts[0]);
      int month = Integer.parseInt(dateParts[1]);
      int year = Integer.parseInt(dateParts[2]);
      LocalDate date = LocalDate.of(year, month, day);
//      LocalDate date = LocalDate.parse(foodItemDto.getDate(), formatter);
      return saveWithDate(user, date, foodItemDto);
    }
  }

  public FoodItem saveWithDate(UserData user, LocalDate date, FoodItemDto foodItemDto) {
    double proteins = Math.round((foodItemDto.getGrams() / 100.0) * foodItemDto.getProteins() * 100.0) / 100.0;
    double carbs = Math.round((foodItemDto.getGrams() / 100.0) * foodItemDto.getCarbs() * 100.0) / 100.0;
    double fats = Math.round((foodItemDto.getGrams() / 100.0) * foodItemDto.getFats() * 100.0) / 100.0;
    double calories = Math.round((foodItemDto.getGrams() / 100.0) * foodItemDto.getCalories() * 100.0) / 100.0;

    FoodItem food = new FoodItem(
        foodItemDto.getName(),
        proteins,
        carbs,
        fats,
        calories,
        foodItemDto.getGrams()
    );
    food.setDate(date);
    food.setUser(user);
    foodItemRepository.save(food);
    user.getFoods().add(food);
    userDataService.saveUser(user);
    return food;
  }

  public List<FoodItem> findUsersFoodForChosenDate(Long id, String date) {
    UserData user = userDataService.findUserById(id);
    List<FoodItem> userFoods = user.getFoods();
    if ("Today".equals(date)) {
      LocalDate currentDate = LocalDate.now();
      List<FoodItem> filteredFoods = userFoods.stream()
          .filter(foodItem -> foodItem.getDate().equals(currentDate))
          .collect(Collectors.toList());
      return filteredFoods;
    } else {
      String[] dateParts = date.split("\\.");
      int day = Integer.parseInt(dateParts[0]);
      int month = Integer.parseInt(dateParts[1]);
      int year = Integer.parseInt(dateParts[2]);
      LocalDate currentDate = LocalDate.of(year, month, day);
      List<FoodItem> filteredFoods = userFoods.stream()
          .filter(foodItem -> foodItem.getDate().equals(currentDate))
          .collect(Collectors.toList());
      return filteredFoods;
    }
  }

  public List<FoodItem> removeFood(Long userId, Long foodId) {
    UserData user = userDataService.findUserById(userId);
    List<FoodItem> foods = user.getFoods();
    FoodItem foodToRemove = foods.stream()
        .filter(food -> food.getId().equals(foodId))
        .findFirst()
        .orElse(null);
    if (foodToRemove != null) {
      user.getFoods().remove(foodToRemove);
      foodItemRepository.delete(foodToRemove);
    }
    userDataService.saveUser(user);
    return user.getFoods();
  }
}
