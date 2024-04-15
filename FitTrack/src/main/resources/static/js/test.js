'use strict';
let selectedExercises = [];

document.addEventListener("DOMContentLoaded", function() {
  const checkboxes = document.querySelectorAll('.exercise-checkbox');

  checkboxes.forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
      updateSelectedExercises(checkbox);
      updateForm();
    });
  });
});

function updateSelectedExercises(checkbox) {
  const exerciseId = checkbox.value;

  if (checkbox.checked) {
    selectedExercises.push(exerciseId);
  } else {
    selectedExercises = selectedExercises.filter(item => item !== exerciseId);
  }

  document.getElementById("exerciseOrder").value = selectedExercises.join(",");
}

function updateForm() {
  const formContainer = document.getElementById('dynamicFormContainer');

  // Clear existing form
  formContainer.innerHTML = '';

  // Add a form element for each selected exercise using insertAdjacentHTML
  selectedExercises.forEach(function(exerciseId) {
    const formHTML = `
        <form class="exercise-${exerciseId}">
          <!-- You can add more elements or attributes to the form if needed -->
        </form>
      `;
    formContainer.insertAdjacentHTML('afterbegin', formHTML);
  });
}