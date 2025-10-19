document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("jwtToken");
    if (!token) {
        alert("You are not logged in!");
        window.location.href = "login.html"; // Redirect to login page if not logged in
        return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const username = urlParams.get('username');
    document.getElementById("username").value = username;

    const fieldSelect = document.getElementById("field");
    const dynamicInputContainer = document.getElementById("dynamic-input-container");

    fieldSelect.addEventListener("change", function () {
        const selectedField = this.value;

        // Clear the dynamic input container
        dynamicInputContainer.innerHTML = '';

        if (selectedField === "gender") {
            dynamicInputContainer.innerHTML = `
        <label>Gender:</label><br />
        <input type="radio" id="male" name="value" value="MALE" required />
        <label for="male">Male</label><br />
        <input type="radio" id="female" name="value" value="FEMALE" required />
        <label for="female">Female</label><br /><br />
      `;
        } else if (selectedField === "birthDate") {
            dynamicInputContainer.innerHTML = `
        <label for="value">New Birth Date:</label>
        <input type="date" id="value" name="value" required /><br /><br />
      `;
        } else if (selectedField === "role") {
            dynamicInputContainer.innerHTML = `
        <label>Role:</label><br />
        <input type="radio" id="user" name="value" value="user" required />
        <label for="user">User</label><br />
        <input type="radio" id="admin" name="value" value="admin" required />
        <label for="admin">Admin</label><br /><br />
      `;
        } else {
            dynamicInputContainer.innerHTML = `
        <label for="value">New ${selectedField}:</label>
        <input type="text" id="value" name="value" required /><br /><br />
      `;
        }
    });

    const updateUserForm = document.getElementById("update-user-form");
    updateUserForm.addEventListener("submit", function (event) {
        event.preventDefault();

        const field = fieldSelect.value;
        let value;

        if (field === "gender" || field === "role") {
            value = document.querySelector('input[name="value"]:checked').value;
        } else {
            value = document.getElementById("value").value;
        }

        const xhr = new XMLHttpRequest();
        xhr.open("PUT", `http://localhost:8080/api/v1/users?username=${username}&field=${field}&value=${value}`, true);
        xhr.setRequestHeader("Authorization", token);
        xhr.setRequestHeader("Content-Type", "application/json");

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    alert("User updated successfully!");
                    window.location.href = 'list-users.html'; // Redirect to list users page after successful update
                } else if (xhr.status === 401) {
                    document.querySelector('body').style.display = 'none';
                    localStorage.removeItem("jwtToken");
                    await wait(300);
                    alert("Fail! Current user has been removed by admin");
                    window.location.href = "login.html";
                } else {
                    alert("Failed to update user. Please try again.");
                }
            }
        };

        xhr.send();
    });

    // Trigger the change event to set the initial state
    fieldSelect.dispatchEvent(new Event('change'));
});