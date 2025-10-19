(function() {
    const token = localStorage.getItem("jwtToken");
    if (token) {
        const decodedToken = parseJwt(token);

        if (!decodedToken) {
            localStorage.removeItem("jwtToken");
            alert("invalid token");
            window.location.href = "login.html";
        } else if (isTokenExpired(decodedToken)) {
            localStorage.removeItem("jwtToken");
            alert("Session expired");
            const xhr = new XMLHttpRequest();
            xhr.open("GET", "http://localhost:8080/users/logout");
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.setRequestHeader("Authorization", token);
            xhr.onreadystatechange = function () {
                window.location.href = "login.html";
            };
            xhr.send();
        } else {
            const isAdmin = decodedToken.isAdmin;
            console.log('Here ' + isAdmin === false);
            if (isAdmin === false) {
                alert("Forbidden: You are not an admin");
                window.location.href = "user-dashboard.html";
            }
        }
    } else {
        // No token found, show login form
        alert("You are not logged in!");
        window.location.href = "login.html";
    }
})();