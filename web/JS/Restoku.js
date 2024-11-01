let menu = document.querySelector('#menu-bars');
let navbar = document.querySelector('.navbar');

menu.onclick = () =>{
    menu.classList.toggle('fa-times');
    navbar.classList.toggle('active');
}

//fungsi untuk home swiper
var swiper = new Swiper(".home-slider", {
    spaceBetween: 30,
    centeredSlides: true,
    autoplay: {
      delay: 7500,
      disableOnInteraction: false,
    },
    pagination: {
      el: ".swiper-pagination",
      clickable: true,
    },
    loop:true,
  });

  //fungsi untuk review (udah diapus:( )
  var swiper = new Swiper(".review-slider", {
    spaceBetween: 20,
    centeredSlides: true,
    autoplay: {
      delay: 7500,
      disableOnInteraction: false,
    },
    loop:true,
    breakpoints: {
        0: {
            slidesPerView: 1,
        },

        640: {
            slidesPerView: 2,
        },

        768: {
            slidesPerView: 2,
        },
        1024: {
            slidesPerView: 3,
        },  
    },
  });

  
document.addEventListener('DOMContentLoaded', (event) => {
    const datePicker = document.getElementById('datePicker');
    const today = new Date();
    const todayStr = today.toISOString().split('T')[0];

    // Set the min attribute to today
    datePicker.setAttribute('min', todayStr);

    // Add event listener for date change
    datePicker.addEventListener('change', function() {
        validateDateTime();
    });
});

function showError(message) {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: message,
    });
}

function showSuccess(message) {
    Swal.fire({
        icon: 'success',
        title: 'Success!',
        text: message,
    });
}

function validateName(inputId, errorMessageEmpty, errorMessageInvalid) {
    let name = document.getElementById(inputId).value;

    if (name === "") {
        showError(errorMessageEmpty);
        return false;
    }

    let regex = /^[a-zA-Z ]+$/;
    if (!regex.test(name)) {
        showError(errorMessageInvalid);
        return false;
    }

    return true;
}

function validateTelp() {
    let telp = document.getElementById('number-input').value;
    telp = telp.replace(/\D/g, '');
    if (telp === "") {
        showError("Number can't be Empty");
        return false;
    }

    let pattern = /^\d{12,13}$/;
    if (!pattern.test(telp)) {
        showError("Number must be 12 or 13 digits");
        return false;
    }

    return true;
}

function validateEmail() {
    let email = document.getElementById('email-input').value;

    if (email === "") {
        showError("Email can't be Empty");
        return false;
    }

    let regex = /^[a-zA-Z0-9._-]+@gmail\.com$/;
    if (!regex.test(email)) {
        showError("Email input must have @gmail.com");
        return false;
    }

    return true;
}

function validatePeople() {
    let people = document.getElementById('people-input').value;

    if (people === "") {
        showError("Enter the valid number");
        return false;
    }

    if (isNaN(people)) {
        showError("Must be a Number");
        return false;
    }

    if (people <= 0 || people > 10) {
        showError("Number of people must be more than 0 and less than 11");
        return false;
    }

    return true;
}

function validateDateTime() {
    const datePicker = document.getElementById('datePicker');
    const today = new Date();
    const todayStr = today.toISOString().split('T')[0];
    const selectedDate = new Date(datePicker.value);
    
    // Check if selected date is valid and not before today
    if (datePicker.value === '' || selectedDate < today) {
        showError("Date cannot be before today");
        datePicker.value = ''; // Clear the invalid date
        return false;
    }

    return true;
}

function validateForm() {
    const isValidFirst = validateName('first-input', "First name can't be Empty", "First name can't be Number");
    const isValidLast = validateName('last-input', "Last name can't be Empty", "Last name can't be Number");
    const isValidTelp = validateTelp();
    const isValidEmail = validateEmail();
    const isValidPeople = validatePeople();
    const isValidDateTime = validateDateTime();

    return isValidFirst && isValidLast && isValidTelp && isValidEmail && isValidPeople && isValidDateTime;
}

document.getElementById('reservationForm').addEventListener('submit', function(event) {
    if (!validateForm()) {
        event.preventDefault();
        showError('Please fix the errors in the form.');
    } else {
        handleFormSubmission();
    }
});

function handleFormSubmission() {
    showSuccess('Reservation successfully submitted!');
    document.getElementById('first-input').value = "";
    document.getElementById('last-input').value = "";
    document.getElementById('number-input').value = "";
    document.getElementById('email-input').value = "";
    document.getElementById('people-input').value = "";
    document.getElementById('datePicker').value = "";
    document.getElementById('timePicker').value = "";
}
