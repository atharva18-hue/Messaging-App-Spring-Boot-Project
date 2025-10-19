document.addEventListener("DOMContentLoaded", function () {
    // Check for token on page load
    const token = localStorage.getItem("jwtToken");
    if (token) {
        const decodedToken = parseJwt(token);
        if (!decodedToken && isTokenExpired(decodedToken)) {
            localStorage.removeItem("jwtToken");
            localStorage.removeItem("isAdmin");
            window.location.href = "login.html";
        }
        const isAdmin = localStorage.getItem("isAdmin");
        console.log(isAdmin === "false");
        if (isAdmin === "false") {
            window.location.href = "user-dashboard.html";
        }

    } else {
        // No token found, show login form
        window.location.href = "login.html";
    }
});

function parseJwt(token) {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const jsonPayload = decodeURIComponent(
        atob(base64)
            .split("")
            .map(function (c) {
                return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
            })
            .join("")
    );

    return JSON.parse(jsonPayload);
}

function isTokenExpired(decodedToken) {
    const now = Math.floor(Date.now() / 1000);
    return decodedToken.exp < now;
}