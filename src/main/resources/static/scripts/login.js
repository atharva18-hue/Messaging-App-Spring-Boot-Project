document
    .getElementById("login-form")
    .addEventListener("submit", function (event) {
        event.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/users/login", true);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    const token = xhr.responseText;
                    localStorage.setItem("jwtToken", token);

                    const decodedToken = parseJwt(token);
                    const isAdmin = decodedToken.isAdmin;

                    if (isAdmin) {
                        window.location.href = "admin-dashboard.html";
                    } else {
                        window.location.href = "user-dashboard.html";
                    }

                } else {
                    //alert("Login failed!");
                    document.getElementById("login-fail").innerHTML = "Wrong credentials!"
                }
            }
        };
        const data = JSON.stringify({ userName: username, password: password });
        xhr.send(data);
    });