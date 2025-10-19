(function() {
    const token = localStorage.getItem("jwtToken");
    if (token) {
        const decodedToken = parseJwt(token);
        if (!decodedToken) {
            localStorage.removeItem("jwtToken");
            alert("invalid token");
            window.location.href = "login.html";
        } else if (isTokenExpired(decodedToken)) {
            alert("Session expired");
            const xhr = new XMLHttpRequest();
            xhr.open("GET", "http://localhost:8080/users/logout");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.setRequestHeader("Authorization", token);
            xhr.onreadystatechange = function () {
                window.location.href = "login.html";
            };
            xhr.send();
        }
    } else {
        // No token found, show login form
        window.location.href = "login.html";
        alert("You are not logged in!");
    }
})();