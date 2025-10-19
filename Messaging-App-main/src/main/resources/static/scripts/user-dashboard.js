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
    } else {
        // No token found, show login form
        window.location.href = "login.html";
    }

    const isAdmin = localStorage.getItem("isAdmin");
    if (isAdmin === 'false') {
        document.getElementById("main-page").href = "user-dashboard.html";
    }

    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("jwtToken");
        localStorage.removeItem("isAdmin");
        window.location.href = "login.html";
    });

    document.getElementById("mailbox-btn").addEventListener("click", function () {
        window.location.href = "mailbox.html";
    });

    document.getElementById("send-msg-btn").addEventListener("click", function () {
        window.location.href = "send-message.html";
    });

});