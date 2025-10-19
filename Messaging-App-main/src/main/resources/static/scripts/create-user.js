document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("create-user-form");

    form.addEventListener("submit", function (event) {
        event.preventDefault();

        const username = document.getElementById("username").value;
        const fullName = document.getElementById("fullName").value;
        const email = document.getElementById("email").value;
        const address = document.getElementById("address").value;
        const gender = document.getElementById("gender").value;
        const birthDate = document.getElementById("birthDate").value;
        const password = document.getElementById("password").value;
        const role = document.getElementById("role").value;

        const newUser = {
            userName: username,
            fullName: fullName,
            email: email,
            address: address,
            gender: gender,
            birthDate: birthDate,
            password: password,
            admin: (role === 'admin') ? true : false // Convert role value to boolean
        };

        createUser(newUser);
    });
});

function createUser(user) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/api/v1/users", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Authorization", localStorage.getItem("jwtToken"));

    xhr.onreadystatechange = async function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 201) {
                window.location.href = "admin-dashboard.html";
            } else if (xhr.status === 401) {
                localStorage.removeItem("jwtToken");
                alert("Fail! Current user has been removed by admin");
                await wait(300);
                window.location.href = "login.html";
            } else {
                alert("Failed to create user. Please try again.");
            }
        }
    };

    const data = JSON.stringify(user);
    xhr.send(data);
}