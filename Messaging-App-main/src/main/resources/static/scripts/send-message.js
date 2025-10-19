/*
document.addEventListener("DOMContentLoaded", function () {
    const sendMessageForm = document.getElementById("send-message-form");
    const messageDiv = document.getElementById("message");
    const token = localStorage.getItem("jwtToken");
    const decodedToken = parseJwt(token);

    const isAdmin =decodedToken.isAdmin;
    if (isAdmin === false) {
        document.getElementById("nav-list-user").style.display = "none";
        document.getElementById("nav-add-user").style.display = "none";
        document.getElementById("main-page").href = "user-dashboard.html";
    }

    sendMessageForm.addEventListener("submit", function (event) {
        event.preventDefault();
        sendMessage();
    });

    function sendMessage() {
        const toUser = document.getElementById("toUser").value;
        const subject = document.getElementById("subject").value;
        const content = document.getElementById("content").value;
        const token = localStorage.getItem("jwtToken");
        const fromUser = getUsernameFromToken(token); // Extracting username from token

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/api/v1/messages", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 201) {
                    messageDiv.textContent = "Message sent successfully!";
                    messageDiv.style.display = "block";
                    sendMessageForm.reset();
                } else if (xhr.status === 401) {
                    document.querySelector('body').style.display = 'none';
                    localStorage.removeItem("jwtToken");
                    await wait(300);
                    alert("Fail! Current user has been removed by admin");
                    window.location.href = "login.html";
                } else {
                    messageDiv.textContent = "Username does not exist. Please try again.";
                    messageDiv.style.display = "block";
                }
            }
        };

        const data = JSON.stringify({
            fromUser: fromUser,
            toUser: toUser,
            subject: subject,
            content: content,
        });

        xhr.send(data);
    }

    function getUsernameFromToken(token) {
        if (!token) {
            return null;
        }

        const payloadBase64 = token.split('.')[1];
        const decodedPayload = JSON.parse(atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/')));
        return decodedPayload.sub; // Assuming the username is stored in the 'sub' claim
    }
});

 */


/*
document.addEventListener("DOMContentLoaded", function () {
    const sendMessageForm = document.getElementById("send-message-form");
    const messageDiv = document.getElementById("message");
    const token = localStorage.getItem("jwtToken");
    const decodedToken = parseJwt(token);

    const isAdmin =decodedToken.isAdmin;
    if (isAdmin === false) {
        document.getElementById("nav-list-user").style.display = "none";
        document.getElementById("nav-add-user").style.display = "none";
        document.getElementById("main-page").href = "user-dashboard.html";
    }

    sendMessageForm.addEventListener("submit", function (event) {
        event.preventDefault();
        sendMessage();
    });

    function sendMessage() {
        const toUser = document.getElementById("toUser").value;
        const subject = document.getElementById("subject").value;
        const content = document.getElementById("content").value;
        const token = localStorage.getItem("jwtToken");
        const fromUser = getUsernameFromToken(token); // Extracting username from token

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/api/v1/messages", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 201) {
                    messageDiv.textContent = "Message sent successfully!";
                    messageDiv.style.display = "block";
                    sendMessageForm.reset();
                } else if (xhr.status === 401) {
                    document.querySelector('body').style.display = 'none';
                    localStorage.removeItem("jwtToken");
                    await wait(300);
                    alert("Fail! Current user has been removed by admin");
                    window.location.href = "login.html";
                } else {
                    messageDiv.textContent = "Username does not exist. Please try again.";
                    messageDiv.style.display = "block";
                }
            }
        };

        const data = JSON.stringify({
            fromUser: fromUser,
            toUser: toUser,
            subject: subject,
            content: content,
        });

        xhr.send(data);
    }

    function getUsernameFromToken(token) {
        if (!token) {
            return null;
        }

        const payloadBase64 = token.split('.')[1];
        const decodedPayload = JSON.parse(atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/')));
        return decodedPayload.sub; // Assuming the username is stored in the 'sub' claim
    }
});

 */




/*
document.addEventListener("DOMContentLoaded", function () {
    const sendMessageForm = document.getElementById("send-message-form");
    const messageDiv = document.getElementById("message");
    const toUserInput = document.getElementById("toUser");
    const token = localStorage.getItem("jwtToken");
    const decodedToken = parseJwt(token);
    let allUsernames = [];

    const isAdmin = decodedToken.isAdmin;
    if (isAdmin === false) {
        document.getElementById("nav-list-user").style.display = "none";
        document.getElementById("nav-add-user").style.display = "none";
        document.getElementById("main-page").href = "user-dashboard.html";
    }

    sendMessageForm.addEventListener("submit", function (event) {
        event.preventDefault();
        sendMessage();
    });

    toUserInput.addEventListener("input", function () {
        const query = toUserInput.value.toLowerCase();
        const filteredUsernames = allUsernames.filter(username => username.toLowerCase().includes(query));
        showDropdown(filteredUsernames);
    });

    function sendMessage() {
        const toUser = document.getElementById("toUser").value;
        const subject = document.getElementById("subject").value;
        const content = document.getElementById("content").value;
        const fromUser = getUsernameFromToken(token);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/api/v1/messages", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 201) {
                    messageDiv.textContent = "Message sent successfully!";
                    messageDiv.style.display = "block";
                    sendMessageForm.reset();
                } else if (xhr.status === 401) {
                    document.querySelector('body').style.display = 'none';
                    localStorage.removeItem("jwtToken");
                    await wait(300);
                    alert("Fail! Current user has been removed by admin");
                    window.location.href = "login.html";
                } else {
                    messageDiv.textContent = "Username does not exist. Please try again.";
                    messageDiv.style.display = "block";
                }
            }
        };

        const data = JSON.stringify({
            fromUser: fromUser,
            toUser: toUser,
            subject: subject,
            content: content,
        });

        xhr.send(data);
    }

    function getUsernameFromToken(token) {
        if (!token) {
            return null;
        }

        const payloadBase64 = token.split('.')[1];
        const decodedPayload = JSON.parse(atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/')));
        return decodedPayload.sub;
    }

    function fetchAllUsernames() {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/api/v1/usernames", true);
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                allUsernames = JSON.parse(xhr.responseText);
            }
        };

        xhr.send();
    }

    function showDropdown(usernames) {
        clearDropdown();

        const dropdown = document.createElement("ul");
        dropdown.setAttribute("id", "dropdown-menu");
        dropdown.setAttribute("class", "dropdown-menu");

        usernames.forEach(function (username) {
            const item = document.createElement("li");
            item.textContent = username;
            item.addEventListener("click", function () {
                toUserInput.value = username;
                clearDropdown();
            });
            dropdown.appendChild(item);
        });

        toUserInput.parentNode.appendChild(dropdown);
    }

    function clearDropdown() {
        const dropdown = document.getElementById("dropdown-menu");
        if (dropdown) {
            dropdown.parentNode.removeChild(dropdown);
        }
    }

    document.addEventListener("click", function (e) {
        if (e.target !== toUserInput) {
            clearDropdown();
        }
    });

    // Fetch all usernames on page load
    fetchAllUsernames();
});
 */



document.addEventListener("DOMContentLoaded", function () {
    const sendMessageForm = document.getElementById("send-message-form");
    const messageDiv = document.getElementById("message");
    const toUserInput = document.getElementById("toUser");
    const userDropdown = document.getElementById("userDropdown");
    const token = localStorage.getItem("jwtToken");
    const decodedToken = parseJwt(token);
    let allUsernames = [];

    const isAdmin = decodedToken.isAdmin;
    if (isAdmin === false) {
        document.getElementById("nav-list-user").style.display = "none";
        document.getElementById("nav-add-user").style.display = "none";
        document.getElementById("main-page").href = "user-dashboard.html";
    }

    fetchUsernames();

    toUserInput.addEventListener("input", function () {
        const input = toUserInput.value.toLowerCase();
        userDropdown.innerHTML = '';
        if (input) {
            const filteredUsernames = allUsernames.filter(username => username.toLowerCase().startsWith(input));
            filteredUsernames.forEach(username => {
                const div = document.createElement("div");
                div.textContent = username;
                div.addEventListener("click", function () {
                    toUserInput.value = username;
                    userDropdown.innerHTML = '';
                    userDropdown.style.display = 'none';
                });
                userDropdown.appendChild(div);
            });
            userDropdown.style.display = 'block';
        } else {
            userDropdown.style.display = 'none';
        }
    });

    sendMessageForm.addEventListener("submit", function (event) {
        event.preventDefault();
        sendMessage();
    });

    function fetchUsernames() {
        const xhr = new XMLHttpRequest();
        xhr.open("GET", "http://localhost:8080/api/v1/usernames", true);
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                allUsernames = JSON.parse(xhr.responseText);
            } else if (xhr.status === 401) {
                document.querySelector('body').style.display = 'none';
                localStorage.removeItem("jwtToken");
                await wait(300);
                alert("Fail! Current user has been removed by admin");
                window.location.href = "login.html";
            }
        };

        xhr.send();
    }

    function sendMessage() {
        const toUser = document.getElementById("toUser").value;
        const subject = document.getElementById("subject").value;
        const content = document.getElementById("content").value;
        const token = localStorage.getItem("jwtToken");
        const fromUser = getUsernameFromToken(token); // Extracting username from token

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/api/v1/messages", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 201) {
                    messageDiv.textContent = "Message sent successfully!";
                    messageDiv.style.display = "block";
                    sendMessageForm.reset();
                } else if (xhr.status === 401) {
                    document.querySelector('body').style.display = 'none';
                    localStorage.removeItem("jwtToken");
                    await wait(300);
                    alert("Fail! Current user has been removed by admin");
                    window.location.href = "login.html";
                } else {
                    messageDiv.textContent = "Username does not exist. Please try again.";
                    messageDiv.style.display = "block";
                }
            }
        };

        const data = JSON.stringify({
            fromUser: fromUser,
            toUser: toUser,
            subject: subject,
            content: content,
        });

        xhr.send(data);
    }

    function getUsernameFromToken(token) {
        if (!token) {
            return null;
        }

        const payloadBase64 = token.split('.')[1];
        const decodedPayload = JSON.parse(atob(payloadBase64.replace(/-/g, '+').replace(/_/g, '/')));
        return decodedPayload.sub; // Assuming the username is stored in the 'sub' claim
    }
});