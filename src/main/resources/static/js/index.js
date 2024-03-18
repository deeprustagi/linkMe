document.addEventListener("DOMContentLoaded", function() {

    const registrationForm = document.getElementById("registrationForm");
    const loginForm = document.getElementById("loginForm");
    const redirctToLogin = document.getElementById('redirctToLogin');
    const redirctToRegister = document.getElementById('redirctToRegister');

    function redirectToLoginMethod() {
        document.getElementById("login").classList.remove("hidden");
        document.getElementById("register").classList.add("hidden");
    }

    function redirctToRegisterMethod() {
        document.getElementById("register").classList.remove("hidden");
        document.getElementById("login").classList.add("hidden");
    }

    redirctToLogin.onclick = redirectToLoginMethod;
    redirctToRegister.onclick = redirctToRegisterMethod;

    // User Registration form submission
    registrationForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const firstName = document.getElementById("firstname").value;
        const lastName = document.getElementById("lastname").value;
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        const user = {
            firstName: firstName,
            lastName: lastName,
            userName: username,
            password: password
        };

        const jsonData = JSON.stringify(user);

        fetch('/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        })
        .then(response => {
            if (response.ok) {
                alert("User registered successfully!");
                document.getElementById("login").classList.remove("hidden");
                document.getElementById("register").classList.add("hidden");
            } else {
                alert("Error: Unable to register user!");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Error: Unable to register user!");
        });
    });

    // User Login form submission
    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const username = document.getElementById("loginUsername").value;
        const password = document.getElementById("loginPassword").value;

        const user = {
            firstName: "",
            lastName: "",
            userName: username,
            password: password
        };

        const jsonData = JSON.stringify(user);

        fetch('/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: jsonData
        })
        .then(response => {
            if (response.ok) {
                alert("User logged in successfully!");
                localStorage.setItem("username", username);
                window.location.href = "profile.html";
            } else {
                alert("Error: Unable to login user!");
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert("Error: Unable to login user!");
        });
    });
});