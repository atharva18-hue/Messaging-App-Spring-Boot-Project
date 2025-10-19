document
    .getElementById("logout-btn")
    .addEventListener("click", function (event) {
            const token = localStorage.getItem("jwtToken");
            console.log('logout');
            localStorage.removeItem("jwtToken");
        event.preventDefault();
        console.log("logout");
        const xhr = new XMLHttpRequest();

        xhr.open("GET", "http://localhost:8080/users/logout");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = function () {
            window.location.href = "login.html";
        };
        xhr.send();
    });