'use strict';
// // schedule treining
// const dateValue = document.getElementById('date');
// const saveTrainingBtn = document.getElementById('schedule-training-btn');
// console.log(saveTrainingBtn);

// saveTrainingBtn.addEventListener('click', function () {
//   if (dateValue.value === '') {
//     console.log('wrong');
//   } else {
//     const today = new Date();
//     console.log(today.getTime());
//     console.log(dateValue.valueAsDate.getTime());
//   }
// });

const userId = document.querySelector('.user-id');

console.log("User ID", userId.id);
const remove = document.querySelector('.remove');
const addFoodModal = document.querySelector('.add-food-modal');
const overlay = document.querySelector('.overlay');
const hlavne = document.querySelector('.hlavne');
// const remove5 = document.getElementById('remove-5');

// const closeModalBtn = document.querySelector('.btn--close-modal');
const noBtn = document.querySelector('.remove--no');

const yesBtn = document.querySelector('.remove--yes');

const addFoodBtn = document.querySelector('.food-btn');
const closeAddFoodModal = document.querySelector('.close-add');
const search = document.querySelector('.search');
const searchInput = document.querySelector('.search-input');
const wrongFoodMsg = document.querySelector('.wrong-food');
const foundFoodsModal = document.querySelector('.choose-food');
const searchForm = document.querySelector('.search-form');
let inputFromSearch;

const date = document.querySelector('.date');
const leftArrow = document.querySelector(".left-arrow");
const rightArrow = document.querySelector('.right-arrow');


const desireCaloriesModal = document.querySelector(".set-desire-calories");
const desireCaloriesValue = document.querySelector(".desire-calories-input-value");
const desireCaloriesWrongMsg = document.querySelector(".desire-calories-wrong");
const addDesireCaloriesBtn = document.querySelector(".desire-calories-btn");

const createOwnFoodModal = document.querySelector('.create-own-food');
const createFoodBtn = document.querySelector('.own-food');
const closeOwnFoodModal = document.querySelector('.close-own-modal');
const createOwnFoodBtn = document.querySelector('.create-food-btn');
// const createAndAddToPlanBtn = document.querySelector('.create-add-btn');

const nameValue = document.querySelector('.food-name-input-value');
const proteinValue = document.querySelector('.proteins-input-value');
const carbsValue = document.querySelector('.carbs-input-value');
const fatsValue = document.querySelector('.fats-input-value');
const caloriesValue = document.querySelector('.calories-input-value');
const wrongInputsCreate = document.querySelector('.wrong-inputs-create');

const createdMsgModal = document.querySelector(".created-modal");
const createdMsg = document.querySelector(".created-msg");

//vyberanie si jedla
const foodItemsContainer = document.querySelector('.food-items');
const btnCloseFoundFoods = document.querySelector('.close-found-foods');
const addFoodToPlanBtn = document.querySelector('.add-to-plan');

const gramsContainer = document.querySelector(".grams-container");
const gramsWrongMSg = document.querySelector(".grams-wrong");
const gramsInput = document.querySelector(".grams-input-value");

const foodTableSection = document.querySelector(".section--content");

//table
const caloriesMain = document.querySelector('.total-cal');

const removeMsg = document.querySelector('.remove-msg');

class FoodItem {
  constructor(name, proteins, carbs, fats, calories) {
    this.name = name;
    this.proteins = proteins;
    this.carbs = carbs;
    this.fats = fats;
    this.calories = calories;
  }
}

class FoodItemToSave {
  constructor(name, proteins, carbs, fats, calories, grams, date) {
    this.name = name;
    this.proteins = proteins;
    this.carbs = carbs;
    this.fats = fats;
    this.calories = calories;
    this.grams = grams;
    this.date = date;
  }
}

class App {
  #desireCalories;
  #clickedFood;
  #foodIdToRemove;
  foodValues = [];
  foods = [];
  foundFoods = [];
  constructor() {
    this._initTable(date.textContent);
    this._findUserDesireCalories();
    addDesireCaloriesBtn.addEventListener("click", this._addDesireCalories.bind(this));
    createFoodBtn.addEventListener('click', this._openCreateFoodModal.bind(this));
    closeOwnFoodModal.addEventListener('click', this._closeCreateFoodModal.bind(this));
    addFoodBtn.addEventListener('click', this._openAddFoodModal.bind(this));
    closeAddFoodModal.addEventListener('click', this._closeAddFoodModal.bind(this));
    createOwnFoodBtn.addEventListener('click', this._createFood.bind(this));
    // createAndAddToPlanBtn.addEventListener("click", this._createAndAdd.bind(this));

    searchForm.addEventListener('submit', function (e) {
      e.preventDefault();
    });
    search.addEventListener('keyup', this._seachFormSubmit.bind(this));
    foodItemsContainer.addEventListener('click', this._selectFood.bind(this));
    btnCloseFoundFoods.addEventListener('click', this._closeFoundFoodsModal.bind(this));
    addFoodToPlanBtn.addEventListener("click", this._addFoodToPlan.bind(this));

    leftArrow.addEventListener("click", this._decreaseDate.bind(this));
    rightArrow.addEventListener("click", this._increaseDate.bind(this));
    noBtn.addEventListener('click', this._closeRemoveModal.bind(this));
    yesBtn.addEventListener("click", this._removeFood.bind(this));
  }

  _findUserDesireCalories() {
    const apiEndpoint = '/user/calories/' + userId.id;

    fetch(apiEndpoint, {
      method: 'POST',
    })
    .then(response => {
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      return response.json();
    })
    .then(data => {
      console.log(data === 0);
      if (data === 0) {
        desireCaloriesModal.classList.remove("hidden");
        overlay.classList.remove("hidden");
      } else {
        this.#desireCalories = data;
      }
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
  }

  _addDesireCalories(e) {
    e.preventDefault();
    if (desireCaloriesValue.value <= 0) {
      desireCaloriesWrongMsg.textContent = "Please enter a valid positive number.";
    } else {
      desireCaloriesWrongMsg.textContent = "";
      const apiEndpoint = '/add/calories/' + userId.id;
      const formData = new FormData();
      formData.append("desireCalories", desireCaloriesValue.value);

      fetch(apiEndpoint, {
        method: 'POST',
        body: formData,
      })
      .then(response => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then(data => {
        this.#desireCalories = data;
        console.log("Desire calories from server", this.#desireCalories);
        desireCaloriesModal.classList.add("hidden");
        overlay.classList.add("hidden");
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
    }
  }

  _initTable(date) {
    fetch('/find-foods/' + userId.id, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: date
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      // console.log(response);
      return response.json();
    })
    .then(data => {
      console.log(data);
      this.foods = data;
      this._createTable(this.foods);
      // if (data !== null) {
      //   const food = new FoodItem(data.name, data.proteins, data.carbs, data.fats, data.calories);
      //   this.foundFoods.push(food);
      // }
    })
    .catch(error => {
      console.error('Error:', error);
    });
  }

  _createTable(data) {
    foodTableSection.textContent = "";
    if (data.length > 0) {
      caloriesMain.classList.remove("hidden");
      let html = `
      <div class="trainings-table-container">
             <table class="trainings-table">
                <thead>
               <tr>
                <th>Remove</th>
                 <th>Food</th>
                 <th>Grams</th>
                  <th>Proteins</th>
                  <th>Carbs</th>
                  <th>Fats</th>
                  <th>Calories</th>
                </tr>
                </thead>
                <tbody class="table&#45;&#45;body">
      `;
      data.forEach(d => {
        html += `
                 <tr class="tr">
                  <td>
                    <button id="${d.id}">X</button>
                  </td>
                  <td>${d.name}</td>
                  <td>${d.grams}g</td>
                 <td>${d.proteins}</td>
                 <td>${d.carbs}</td>
                 <td>${d.fats}</td>
                  <td>${d.calories}</td>
                </tr>
        `;
      });

      html += `
                <tr class="totals-row">
                  <td class="totals-rows"></td>
                  <td class="totals-rows">TOTALS</td>
                  <td class="totals-rows"></td>
                  <td class="totals-rows" id="total-proteins">0</td>
                  <td class="totals-rows" id="total-carbs">0</td>
                  <td class="totals-rows" id="total-fats">0</td>
                  <td class="totals-rows" id="total-calories">0</td>
                </tr>
                </tbody>
              </table>
            </div>
      `;
      foodTableSection.insertAdjacentHTML("beforeend", html);
      this._updateTotalRow();
      const removeButtons = document.querySelectorAll('.tr button');

      removeButtons.forEach(button => {
      button.addEventListener('click', this._openRemoveModal.bind(this));
    });
    } else {
      caloriesMain.classList.add("hidden");
      const html = `
           <div class="no-food">
             <h1>No food entries for today.</h1>
        </div>
         `;
      foodTableSection.insertAdjacentHTML("beforeend", html);
    }
  }
  _decreaseDate(e) {
    e.preventDefault();
    if (date.textContent === 'Today') {
      let currentDate = new Date();
      currentDate.setDate(currentDate.getDate() - 1);
      let day = String(currentDate.getDate()).padStart(2, '0'); // Ensure two digits for day
      let month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Ensure two digits for month
      let decreasedFormattedDate = `${day}.${month}.${currentDate.getFullYear()}`;
      date.textContent = decreasedFormattedDate;
      this._initTable(date.textContent);
    } else {
      let dateString = date.textContent;
      let dateParts = dateString.split(".");
      let year = parseInt(dateParts[2]);
      let month = parseInt(dateParts[1]) - 1; // Month is zero-based in JavaScript Date object
      let day = parseInt(dateParts[0]);
      let currentDate = new Date(year, month, day);
      currentDate.setDate(currentDate.getDate() - 1);
      let dayDecreased = String(currentDate.getDate()).padStart(2, '0'); // Ensure two digits for day
      let monthDecreased = String(currentDate.getMonth() + 1).padStart(2, '0'); // Ensure two digits for month
      let decreasedFormattedDate = `${dayDecreased}.${monthDecreased}.${currentDate.getFullYear()}`;
      date.textContent = decreasedFormattedDate;
      this._initTable(date.textContent);
    }
    rightArrow.classList.remove('hidden');
  }

  _increaseDate(e) {
    e.preventDefault();
    let dateString = date.textContent;
    let dateParts = dateString.split(".");
    let year = parseInt(dateParts[2]);
    let month = parseInt(dateParts[1]) - 1; // Month is zero-based in JavaScript Date object
    let day = parseInt(dateParts[0]);
    let currentDate = new Date(year, month, day);

    let today = new Date();
    currentDate.setDate(currentDate.getDate() + 1);

    if (currentDate.getDate() === today.getDate() &&
      currentDate.getMonth() === today.getMonth() &&
      currentDate.getFullYear() === today.getFullYear()) {
      date.textContent = 'Today';
      this._initTable(date.textContent);
      rightArrow.classList.add('hidden');
    } else {
      let dayDecreased = String(currentDate.getDate()).padStart(2, '0'); // Ensure two digits for day
      let monthDecreased = String(currentDate.getMonth() + 1).padStart(2, '0'); // Ensure two digits for month
      let increasedFormattedDate = `${dayDecreased}.${monthDecreased}.${currentDate.getFullYear()}`;
      date.textContent = increasedFormattedDate;
      this._initTable(date.textContent);
    }
  }

  _openCreateFoodModal(e) {
    e.preventDefault();
    createOwnFoodModal.classList.remove('hidden');
    overlay.classList.remove('hidden');
  }

  _createFood(e) {
    e.preventDefault();
    if (
      nameValue.value === '' ||
      proteinValue.value === '' ||
      carbsValue.value === '' ||
      fatsValue.value === '' ||
      caloriesValue.value === ''
    ) {
      wrongInputsCreate.textContent = 'Please set values for all fields.';
    } else if (
      proteinValue.value < 0 ||
      carbsValue.value < 0 ||
      fatsValue.value < 0 ||
      caloriesValue.value < 0
    ) {
      wrongInputsCreate.textContent = 'For numbers, enter positive values.';
    } else {
      wrongInputsCreate.textContent = '';
      const formData = new FormData();
      formData.append("name", nameValue.value);
      formData.append("proteins", proteinValue.value);
      formData.append("carbs", carbsValue.value);
      formData.append("fats", fatsValue.value);
      formData.append("calories", caloriesValue.value);
      const apiEndpoint = '/add-to-database';
      fetch(apiEndpoint, {
        method: 'POST',
        body: formData,
      })
      .then(response => {
        if (!response.ok) {
          if (response.status === 400) {
            throw new Error("Food with this name already exists");
          } else {
            throw new Error("Network response was not ok");
          }
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
        const msg = `${data.name.charAt(0).toUpperCase() + data.name.slice(1).toLowerCase()} added to database`;
        this._openCreatedModal(msg);
      })
      .catch(error => {
        if (error.message === "Food with this name already exists") {
          wrongInputsCreate.textContent = 'Food with this name already exists.';
        } else {
          console.error('Unhandled error:', error);
        }
      });
    }
  }

  _openCreatedModal(data) {
    createOwnFoodModal.classList.add('hidden');
    createdMsgModal.classList.remove("hidden");
    createdMsg.textContent = data;
    setTimeout(() => {
      nameValue.value = '';
      proteinValue.value = '' ;
      carbsValue.value = '';
      fatsValue.value = '';
      caloriesValue.value = '';
      createdMsgModal.classList.add("hidden");
      overlay.classList.add("hidden");
    }, 1000);
  }

  _closeCreateFoodModal() {
    createOwnFoodModal.classList.add('hidden');
    overlay.classList.add('hidden');
    setTimeout(() => {
      wrongInputsCreate.textContent = '';
      nameValue.value = '';
      proteinValue.value = '';
      carbsValue.value = '';
      fatsValue.value = '';
      caloriesValue.value = '';
    }, 1000);
  }

  _openAddFoodModal(e) {
    e.preventDefault();
    addFoodModal.classList.remove('hidden');
    overlay.classList.remove('hidden');
  }

  _closeAddFoodModal() {
    addFoodModal.classList.add('hidden');
    overlay.classList.add('hidden');
    setTimeout(() => {
      wrongFoodMsg.textContent = "";
      searchInput.value = '';
    }, 1000);
  }

  async _seachFormSubmit(event) {
    if (event.key === 'Enter') {
      event.preventDefault();
      console.log(searchInput.value);
      if (searchInput.value === '') {
        wrongFoodMsg.textContent = 'Please enter a food item to search.';
      } else {
        wrongFoodMsg.textContent = '';
        inputFromSearch = searchInput.value;
        console.log(typeof searchInput.value);
        this.foundFoods = [];
        await this._findFoodFromAPI(inputFromSearch);
        await this._findFoodFromDatabase(inputFromSearch);
        this._createFoodItems(this.foundFoods);
        searchInput.value = '';
        foundFoodsModal.classList.remove('hidden');
        addFoodModal.classList.add('hidden');
      }
    }
  }
  async _findFoodFromAPI(food) {
    await fetch('https://api.calorieninjas.com/v1/nutrition?query=' + food, {
          method: 'GET',
          headers: {
            'X-Api-Key': 'rG0KeiVMrF3qdIxnU1Gpcg==op0M2BavAepU0rRq',
            'Content-Type': 'application/json',
          },
        })
          .then(response => {
            if (!response.ok) {
              throw new Error('Network response was not ok');
            }
            return response.json();
          })
          .then(data => {
            console.log(data.items[0]);
            if (data.items.length > 0) {
              const food = new FoodItem(data.items[0].name,
                data.items[0].protein_g,
                data.items[0].carbohydrates_total_g,
                data.items[0].fat_total_g,
                data.items[0].calories);
              this.foundFoods.push(food);
              console.log(this.foundFoods);
            }
          })
          .catch(error => {
            console.error('Error:', error);
          });
  }

  async _findFoodFromDatabase(food) {
    await fetch('/find-food', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: food
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      if (response.status === 204) {
        // Handle empty response (null) here
        console.log('Response is empty (null)');
        return null;
      }
      console.log(response);
      return response.json();
    })
    .then(data => {
      console.log(data);
      if (data !== null) {
        const food = new FoodItem(data.name, data.proteins, data.carbs, data.fats, data.calories);
        this.foundFoods.push(food);
      }
    })
    .catch(error => {
      console.error('Error:', error);
    });
  }

  _createFoodItems(foods) {
    console.log("Food items from create method", this.foundFoods);
    foodItemsContainer.innerHTML = "";
    if (foods.length > 0) {
      btnCloseFoundFoods.classList.remove("hidden");
      foods.forEach(food => {
        const html = `
        <div class="food-item">
      <h2 style="display: flex; justify-content: center">${food.name}</h2>
      <div class="rows">
        <div class="row">
          <h2>Proteins: ${food.proteins}</h2>
          <h2>Carbs: ${food.carbs}</h2>
        </div>
        <div class="row">
          <h2>Fats: ${food.fats}</h2>
          <h2>Calories: ${food.carbs}</h2>
        </div>
      </div>
    </div>
        `;

        foodItemsContainer.insertAdjacentHTML("beforeend", html);
      })
    } else {
      btnCloseFoundFoods.classList.add("hidden");
      const html = `
      <h1>
    Sorry, the food you're looking for is not in our database.<br> You can add
    your own custom entry.
  </h1>
      `;
      foodItemsContainer.insertAdjacentHTML("beforeend", html);
      nameValue.value = inputFromSearch;
      setTimeout(() => {
        foundFoodsModal.classList.add('hidden');
        createOwnFoodModal.classList.remove('hidden');
      }, 2000);
    }
  }

  _selectFood(e) {
    e.preventDefault();
    if (this.foundFoods.length > 0) {
      const foodItems = document.querySelectorAll('.food-item');
      const clicked = e.target;
      const clickedFood = clicked.closest('.food-item');
      foodItems.forEach(food => food.classList.remove('orange-background'));
      clickedFood.classList.add('orange-background');
      addFoodToPlanBtn.classList.remove('hidden');
      gramsContainer.classList.remove("hidden");
      this.#clickedFood = clickedFood;
      // const h2Elements = clickedFood.querySelectorAll('h2');
      // console.log("H2 elements: ", h2Elements);
      console.log("Clicked Food From head: ", this.#clickedFood);
    }
  }

  _closeFoundFoodsModal() {
    const foodItems = document.querySelectorAll('.food-item');
    foundFoodsModal.classList.add('hidden');
    overlay.classList.add('hidden');
    addFoodToPlanBtn.classList.add('hidden');
    gramsContainer.classList.add("hidden");
    setTimeout(() => {
      this.foodValues = [];
      gramsInput.value = "";
      foodItems.forEach(food => food.classList.remove('orange-background'));
    }, 1000);
  }

  // _createAndAdd(e) {
  //   e.preventDefault();
  //   this.foundFoods = [];
  //   const food = new FoodItem(nameValue.value, proteinValue.value, carbsValue.value, fatsValue.value, caloriesValue.value);
  //   this.foundFoods.push(food);
  // }

  _addFoodToPlan(e) {
    e.preventDefault();
    if (gramsInput.value <= 0) {
      gramsWrongMSg.classList.remove("hidden");
    } else {
      this.foundFoods = [];
      gramsWrongMSg.classList.add("hidden");
      const h2Elements = this.#clickedFood.querySelectorAll('h2');
      Array.from(h2Elements).slice(1).forEach(h => {
        const number = parseFloat(h.outerText.match(/\d+(\.\d+)?/)[0]);
        // console.log(number);
        this.foodValues.push(number);
        // console.log(h.outerText);
      });
      const food = new FoodItemToSave(
        h2Elements[0].outerText,
        this.foodValues[0],
        this.foodValues[1],
        this.foodValues[2],
        this.foodValues[3],
        gramsInput.value,
        date.textContent
        );
      console.log(food);
      // /save-food/{id}
      // console.log("Hodnoty :", this.foodValues);
      // console.log(h2Elements);
      const apiEndpoint = '/save-food/' + userId.id;
      const formData = new FormData();
      formData.append("name", food.name);
      formData.append("proteins", food.proteins);
      formData.append("carbs", food.carbs);
      formData.append("fats", food.fats);
      formData.append("calories", food.calories);
      formData.append("grams", food.grams);
      formData.append("date", food.date);

      fetch(apiEndpoint, {
        method: 'POST',
        body: formData,
      })
      .then(response => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then(data => {
        console.log(data);
        this.foods.push(data);
        this._createTable(this.foods);
        foundFoodsModal.classList.add("hidden");
        createdMsgModal.classList.remove("hidden");
        createdMsg.textContent = `${data.name} added to your plan`;
        setTimeout(() => {
          gramsInput.value = "";
          addFoodToPlanBtn.classList.add('hidden');
          gramsContainer.classList.add("hidden");
          createdMsgModal.classList.add("hidden");
          overlay.classList.add("hidden");
        }, 1000);

      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
    }
  }

  _updateTotalRow() {
  const totalsRow = document.querySelector('.totals-row');
  const totalProteinsCell = document.getElementById('total-proteins');
  const totalCarbsCell = document.getElementById('total-carbs');
  const totalFatsCell = document.getElementById('total-fats');
  const totalCaloriesCell = document.getElementById('total-calories');

  let totalProteins = 0;
  let totalCarbs = 0;
  let totalFats = 0;
  let totalCalories = 0;

  const rows = document.querySelectorAll('.table--body tr');

  rows.forEach(row => {
    const proteins = parseFloat(row.cells[3].textContent) || 0;
    const carbs = parseFloat(row.cells[4].textContent) || 0;
    const fats = parseFloat(row.cells[5].textContent) || 0;
    const calories = parseFloat(row.cells[6].textContent) || 0;

    totalProteins += proteins;
    totalCarbs += carbs;
    totalFats += fats;
    totalCalories += calories;
  });

  totalProteinsCell.textContent = totalProteins.toFixed(2);
  totalCarbsCell.textContent = totalCarbs.toFixed(2);
  totalFatsCell.textContent = totalFats.toFixed(2);
  totalCaloriesCell.textContent = totalCalories.toFixed(2);

  caloriesMain.textContent = totalCalories.toFixed(2);

  //zobrazenie totalnych kalorii v zavyslosti od ciela == ciel zobereme od usera
    if (totalCalories.toFixed(2) <= this.#desireCalories) {
      caloriesMain.style.color = '#079f09';
    } else {
      caloriesMain.style.color = '#FF0000';
    }
  }

  _openRemoveModal(e) {
    e.preventDefault();
    const clickedButton = e.target;
    // console.log(clickedButtonId);
    const clickedRow = clickedButton.closest('tr');
    this.#foodIdToRemove = clickedButton.id;
    const nameOfFood =
      clickedRow.querySelector('td:nth-child(2)').textContent; // Adjust the nth-child index based on your structure
    // console.log(
    //   `Button with ID ${clickedButton.id} clicked, and the first td value is ${nameOfFood}`
    // );
    console.log(typeof this.#foodIdToRemove);
    removeMsg.textContent = `Do you want to remove ${nameOfFood}?`;
    remove.classList.remove('hidden');
    overlay.classList.remove('hidden');
  }

  _closeRemoveModal() {
    remove.classList.add('hidden');
    overlay.classList.add('hidden');
  }

  _removeFood(e) {
    e.preventDefault();
    const apiEndpoint = '/' + userId.id + '/remove-food/' + this.#foodIdToRemove;

    fetch(apiEndpoint, {
      method: 'POST',
    })
    .then(response => {
      console.log(response);
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json(); // Parse the response body as JSON
    })
    .then(data => {
      this.foods = data;
      // Handle the returned exercises data
      this._closeRemoveModal();
      this._initTable(date.textContent);

    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });

  }
}

const app = new App();


// const removeButtons = document.querySelectorAll('.tr button');

// console.log("remove btns", removeButtons);


// createAndAddToPlanBtn.addEventListener('click', function (e) {
//   e.preventDefault();
//   if (
//     nameValue.value === '' ||
//     proteinValue.value === '' ||
//     carbsValue.value === '' ||
//     fatsValue.value === '' ||
//     caloriesValue.value === ''
//   ) {
//     wrongInputsCreate.textContent = 'Please set values for all fields.';
//   } else if (
//     proteinValue.value < 0 ||
//     carbsValue.value < 0 ||
//     fatsValue.value < 0 ||
//     caloriesValue.value < 0
//   ) {
//     wrongInputsCreate.textContent = 'For numbers, enter positive values.';
//   } else {
//     wrongInputsCreate.textContent = '';
//   }
// });

//
// const today = new Date();
// const yyyy = today.getFullYear();
// let mm = today.getMonth() + 1; // Months start at 0!
// let dd = today.getDate();
//
// if (dd < 10) dd = '0' + dd;
// if (mm < 10) mm = '0' + mm;
//
// const formattedToday = dd + '.' + mm + '.' + yyyy;
//
// console.log(formattedToday);

// const openModal = function (e) {
//   e.preventDefault();
//   remove.classList.remove('hidden');
//   overlay.classList.remove('hidden');
// };

// const closeModal = function () {
//   remove.classList.add('hidden');
//   overlay.classList.add('hidden');
// };

// const openRemove2 = function (e) {
//   e.preventDefault();
//   remove.classList.add('hidden');
//   remove2.classList.remove('hidden');
// };

// remove5.addEventListener('click', openModal);
// overlay.addEventListener('click', closeModal);

// closeModalBtn.addEventListener('click', closeModal);

// removeButtons.forEach(button => {
//   button.addEventListener('click', function (e) {
//     e.preventDefault();
//     const clickedButton = e.target;
//     // console.log(clickedButtonId);
//     const clickedRow = clickedButton.closest('tr');
//     const nameOfFood =
//       clickedRow.querySelector('td:nth-child(2)').textContent; // Adjust the nth-child index based on your structure
//     console.log(
//       `Button with ID ${clickedButton.id} clicked, and the first td value is ${nameOfFood}`
//     );
//     removeMsg.textContent = `Do you want to remove ${nameOfFood}?`;
//     remove.classList.remove('hidden');
//     overlay.classList.remove('hidden');
//   });
// });


// yesBtn.addEventListener('click', openRemove2);

// const food = [];

////hodnotu pola food zoberieme od usera pre dnesny den
// if (food.length > 0) {
//   document.querySelector('.total-cal').classList.remove('hidden');
// } else {
//   document.querySelector('.total-cal').classList.add('hidden');
// }
const navContainer = document.querySelector('.button-container');
const icons = document.querySelectorAll('.sidebar-btn');
const userMenu = document.querySelector('.user--menu');
const account = document.querySelector('.top-image');
const table = document.querySelector('.table--body');
const tr = document.querySelectorAll('.tr');

// navContainer.addEventListener('click', function (e) {
//   const clicked = e.target.closest('.sidebar-btn');
//   if (!clicked) return;
//   icons.forEach(icon => icon.classList.remove('icon_color'));
//   clicked.classList.add('icon_color');
// });

const showMenu = function (e) {
  e.preventDefault();
  userMenu.classList.remove('hidden');
};

const closeMenu = function () {
  userMenu.classList.add('hidden');
};
let clicked = 0;
account.addEventListener('click', function (e) {
  e.preventDefault();
  clicked++;
  if (clicked % 2 !== 0) {
    showMenu(e);
  }
  if (clicked % 2 === 0) {
    closeMenu();
  }
});

// function updateTotals() {
//   const totalsRow = document.querySelector('.totals-row');
//   const totalProteinsCell = document.getElementById('total-proteins');
//   const totalCarbsCell = document.getElementById('total-carbs');
//   const totalFatsCell = document.getElementById('total-fats');
//   const totalCaloriesCell = document.getElementById('total-calories');
//
//   const caloriesMain = document.querySelector('.total-cal');
//
//   let totalProteins = 0;
//   let totalCarbs = 0;
//   let totalFats = 0;
//   let totalCalories = 0;
//
//   const rows = document.querySelectorAll('.table--body tr');
//
//   rows.forEach(row => {
//     const proteins = parseFloat(row.cells[2].textContent) || 0;
//     const carbs = parseFloat(row.cells[3].textContent) || 0;
//     const fats = parseFloat(row.cells[4].textContent) || 0;
//     const calories = parseFloat(row.cells[5].textContent) || 0;
//
//     totalProteins += proteins;
//     totalCarbs += carbs;
//     totalFats += fats;
//     totalCalories += calories;
//   });
//
//   totalProteinsCell.textContent = totalProteins.toFixed(2);
//   totalCarbsCell.textContent = totalCarbs.toFixed(2);
//   totalFatsCell.textContent = totalFats.toFixed(2);
//   totalCaloriesCell.textContent = totalCalories.toFixed(2);
//
//   caloriesMain.textContent = totalCalories.toFixed(2);
//
//   //zobrazenie totalnych kalorii v zavyslosti od ciela == ciel zobereme od usera
//   if (totalCalories.toFixed(2) <= 637.95) {
//     caloriesMain.style.color = '#079f09';
//   } else {
//     caloriesMain.style.color = '#FF0000';
//   }
// }
// updateTotals();
