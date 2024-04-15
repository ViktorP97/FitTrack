'use strict';
const userMenu = document.querySelector('.user--menu');
const account = document.querySelector('.top-image');

const addActivityModal = document.querySelector('.create-activity');
const overlay = document.querySelector('.overlay');
const closeAddActivitModal = document.querySelector('.btn--close-modal');

const userId = document.querySelector('.user-id');

const nameActivityInput = document.querySelector(".activity-name-input-value");
const activityDistanceInput = document.querySelector(".distance-input-value");
const activityTimeInput = document.querySelector(".time-input-value");
const activityDateInput = document.querySelector(".date-input-value");
const wrongInput = document.querySelector(".add-activity-wrong");
const createActivityBtn = document.querySelector(".create-activity-btn");
const activitiesContainer = document.querySelector(".activities-container");

const removeActivityModal = document.querySelector(".remove-activity");
const activityToRemoveName = document.querySelector(".activity-remove-name");
const removeActivityNoBtn = document.querySelector(".activity-remove--no");
const removeActivityYesBtn = document.querySelector(".activity-remove--yes");
let removingId;
let workoutDivToRemove;

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

class Workout {
  id;
  constructor(name, coords, distance, duration, date) {
    this.name = name;
    this.coords = coords;
    this.distance = distance; //km
    this.duration = duration; // min
    this.date = date;
  }
}
class App {
  #map;
  #mapEvent;
  workouts = [];

  markers = [];

  constructor() {
    this._getPosition();
    this._init();

    createActivityBtn.addEventListener("click", this._newActivity.bind(this));
    closeAddActivitModal.addEventListener("click", this._hideForm.bind(this));
    activitiesContainer.addEventListener("click", this._moveToPopup.bind(this));
    activitiesContainer.addEventListener("click", this._removeActivity.bind(this));
    removeActivityNoBtn.addEventListener("click", this._closeRemoveModal.bind(this));
    removeActivityYesBtn.addEventListener("click", this._removeActivityYesBtn.bind(this));
  }

  _init() {
    const apiEndpoint = '/all/activities/' + userId.id;
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
      data.forEach(a => {
        const workout = new Workout(a.name,[a.lat, a.lng], a.distance, a.duration, a.date);
        workout.id = a.id;
        this.workouts.push(workout);
        this._renderWorkoutMarkaerForDatabase(workout);

        this._renderWorkout(workout);

      })
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
  }

  _getPosition() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        this._loadMap.bind(this),
        function () {
          alert('Could not get your position');
        }
      );
    }
  }

  _loadMap(position) {
    const { latitude } = position.coords;
    const { longitude } = position.coords;
    const coords = [latitude, longitude];

    this.#map = L.map('map').setView(coords, 13); //ptrebueje v html div s id map

    //google mapa
    L.tileLayer('http://{s}.google.com/vt/lyrs=m&x={x}&y={y}&z={z}', {
      maxZoom: 20,
      subdomains: ['mt0', 'mt1', 'mt2', 'mt3'],
    }).addTo(this.#map);

    //to iste ako addEventListener
    this.#map.on('click', this._showForm.bind(this));

    this.workouts.forEach(work => {
      this._renderWorkoutMarkaer(work);
    });
  }

  _showForm(mapE) {
    this.#mapEvent = mapE;
    addActivityModal.classList.remove('hidden');
    overlay.classList.remove('hidden');
  }

  _hideForm() {
    nameActivityInput.value = '';
    activityDistanceInput.value = '';
    activityTimeInput.value = '';
    activityDateInput.value = '';

    addActivityModal.classList.add('hidden');
    overlay.classList.add('hidden');
  }

  _newActivity(e) {
    e.preventDefault();
    const name = nameActivityInput.value;
    const distance = activityDistanceInput.value;
    const time = activityTimeInput.value;
    const date = activityDateInput.value;
    const { lat, lng } = this.#mapEvent.latlng;
    let workout;

    if (name === "" || distance === "" || time === "" || date === "") {
      wrongInput.textContent = "You need to set all values";
    } else {
      wrongInput.textContent = "";
      workout = new Workout(name,[lat, lng], distance, time, date);
      const newDate = new Date(date);

      const apiEndpoint = '/save/activity/' + userId.id;
      const formData = new FormData();
      formData.append("name", workout.name);
      formData.append("lat", workout.coords[0]);
      formData.append("lng", workout.coords[1]);
      formData.append("distance", workout.distance);
      formData.append("duration", workout.duration);
      formData.append("date", newDate.getTime().toString());

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
        // console.log(data);
        workout.id = data.id;
        this.workouts.push(workout);

        this._renderWorkoutMarkaer(workout);

        this._renderWorkout(data);

        this._hideForm();
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
    }
  }

  _renderWorkoutMarkaerForDatabase(workout) {
    //2024-03-13T01:00:00
    const dateParts = workout.date.split('-');
    const formattedDate = `${dateParts[2].slice(0, 2)}.${dateParts[1]}.${dateParts[0]}`;

   const marker = L.marker(workout.coords)
    .addTo(this.#map)
    .bindPopup(
      L.popup({
        maxWidth: 250,
        minWidth: 100,
        autoClose: false,
        closeOnClick: false
      })
    )
    .setPopupContent(
      `${workout.name} ${formattedDate}`
    )
    .openPopup();

    this.markers.push(marker);
  }

  _renderWorkoutMarkaer(workout) {
    const dateParts = workout.date.split('-');
    const formattedDate = `${dateParts[2]}.${dateParts[1]}.${dateParts[0]}`;

   const marker = L.marker(workout.coords)
    .addTo(this.#map)
    .bindPopup(
      L.popup({
        maxWidth: 250,
        minWidth: 100,
        autoClose: false,
        closeOnClick: false
      })
    )
    .setPopupContent(
      `${workout.name} ${formattedDate}`
    )
    .openPopup();

    this.markers.push(marker);
  }
  _renderWorkout(workout) {
    const dateParts = workout.date.split('-');
    const formattedDate = `${dateParts[2].slice(0, 2)}.${dateParts[1]}.${dateParts[0]}`;
    let html = `
    <li class="workout workout--running" data-id=${workout.id}>
              <h2 class="workout__title">${workout.name} ${formattedDate}</h2>
              <div class="workout__details">
                <span class="workout__icon">Distance</span>
                <span class="workout__value">${workout.distance}</span>
                <span class="workout__unit">km</span>
              </div>
              <div class="workout__details">
                <span class="workout__icon">Time</span>
                <span class="workout__value">${workout.duration}</span>
                <span class="workout__unit">min</span>
              </div>
              <div class="workout__details">
                <a class="remove-activity-btn remove-act">
                  <img src="/images/bin.png" />
                </a>
              </div>
            </li>
    `;

    activitiesContainer.insertAdjacentHTML('afterbegin', html);
  }

  _moveToPopup(e) {
    const workoutEl = e.target.closest(".workout");
    if (e.target.closest('.remove-activity-btn')) {
      return;
    }
    if (!workoutEl) return;
    const numberId = Number(workoutEl.dataset.id);
    const workout = this.workouts.find(
      work => work.id === numberId
    );

    this.#map.setView(workout.coords, 13, {
      animate: true,
      pan: {
        duration: 1,
      }
    });
  }

  _removeActivity(e) {
    console.log(e.target.classList);
    console.log(activitiesContainer);
    const removeActElement = activitiesContainer.querySelector('.remove-act');

    console.log("Remove activity", removeActElement);
    if (e.target.matches('.remove-activity-btn img')) {
      e.preventDefault();
      console.log("TU SOM");
      const workoutElement = e.target.closest('.workout');

      const workoutId = workoutElement.dataset.id;
      console.log(workoutId);
      removingId = workoutElement.dataset.id;
      workoutDivToRemove = workoutElement;
      const workoutTitleElement = workoutElement.querySelector('.workout__title');
      console.log(workoutTitleElement.textContent);
      activityToRemoveName.textContent = workoutTitleElement.textContent;
      this._openRemoveModal();
    }
  }

  _openRemoveModal() {
    overlay.classList.remove("hidden");
    removeActivityModal.classList.remove("hidden");
  }

  _removeActivityYesBtn(e) {
    e.preventDefault();
    console.log(workoutDivToRemove);
    console.log(removingId);

    const apiEndpoint = '/' + userId.id + '/delete/activity/' + removingId;

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
      const numberId = Number(removingId);
      this.workouts = this.workouts.filter(work => work.id !== numberId);
      console.log("CVIKYYY", this.workouts);
      this._removeMarkerByCoords(data);
      this._closeRemoveModal();
      workoutDivToRemove.remove();
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
  }

  _closeRemoveModal() {
    overlay.classList.add("hidden");
    removeActivityModal.classList.add("hidden");
  }

  _removeMarkerByCoords(coords) {
    const markerToRemove = this.markers.find(marker => {
      const markerLatLng = marker.getLatLng();
      if (coords.lat === markerLatLng.lat && coords.lng === markerLatLng.lng) {
        return marker;
      }
    });

    if (markerToRemove) {
      console.log("Marker", markerToRemove);
      this._removeMarker(markerToRemove);
    }
  }

  _removeMarker(marker) {
    if (marker && this.#map.hasLayer(marker)) {
      marker.removeFrom(this.#map);
      this.markers = this.markers.filter(m => m !== marker);
    }
  }
}

const app = new App();
