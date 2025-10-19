document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("jwtToken");

    let currentPage = 0;
    let pageSize = 10;
    let filterField = '';
    let filterValue = '';

    const pageSizeSelect = document.getElementById("page-size");
    const pageNumberSelect = document.getElementById("page-number");
    const filterFieldSelect = document.getElementById("filter-field");
    const filterValueInput = document.getElementById("filter-value");
    const filterBtn = document.getElementById("filter-btn");

    function listUsers(page, size, field = '', value = '') {
        let url = `http://localhost:8080/api/v1/users?page=${page}&size=${size}`;
        if (field && value) {
            url += `&field=${field}&value=${value}`;
        }

        const xhr = new XMLHttpRequest();
        xhr.open("GET", url, true);
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);
                const users = response.content;
                const userTableBody = document.getElementById("user-table-body");
                userTableBody.innerHTML = "";


                if (field === "role" || field === "gender") {
                    value = value.toUpperCase();
                }

                users.forEach((user) => {
                    const row = document.createElement("tr");
                    row.innerHTML = `
                        <td>${user.userName}</td>
                        <td>${user.fullName}</td>
                        <td>${user.email}</td>
                        <td>${user.address}</td>
                        <td>${user.gender === "MALE" ? "male" : "female"}</td>
                        <td>${user.birthDate}</td>
                        <td>${user.admin ? "admin" : "user"}</td>
                        <td>
                            <button class="action-btn" onclick="window.location.href = 'update-user.html?username=${user.userName}'">
                                <i class="fas fa-edit"></i>
                            </button>
                            <button class="remove-btn" onclick="removeUser('${user.userName}')">
                                <i class="fas fa-trash-alt"></i>
                            </button>
                        </td>
                    `;
                    userTableBody.appendChild(row);
                });

                pageNumberSelect.innerHTML = '';
                for (let i = 0; i < response.totalPages; i++) {
                    const option = document.createElement("option");
                    option.value = i;
                    option.textContent = i + 1;
                    if (i === page) option.selected = true;
                    pageNumberSelect.appendChild(option);
                }

                document.getElementById("prev-page").disabled = response.first;
                document.getElementById("next-page").disabled = response.last;
            } else if (xhr.status === 401) {
                document.querySelector('body').style.display = 'none';
                localStorage.removeItem("jwtToken");
                await wait(300);
                alert("Fail! Current user has been removed by admin");
                window.location.href = "login.html";
            } else if (xhr.readyState === 4) {
                alert("Failed to fetch users");
            }
        };

        xhr.send();
    }

    pageSizeSelect.addEventListener("change", function () {
        pageSize = parseInt(this.value);
        currentPage = 0; // Reset to first page
        listUsers(currentPage, pageSize, filterField, filterValue);
    });

    pageNumberSelect.addEventListener("change", function () {
        currentPage = parseInt(this.value);
        listUsers(currentPage, pageSize, filterField, filterValue);
    });

    document.getElementById("prev-page").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            listUsers(currentPage, pageSize, filterField, filterValue);
        }
    });

    document.getElementById("next-page").addEventListener("click", function () {
        currentPage++;
        listUsers(currentPage, pageSize, filterField, filterValue);
    });

    filterBtn.addEventListener("click", function () {
        filterField = filterFieldSelect.value;
        filterValue = filterValueInput.value;
        currentPage = 0;
        listUsers(currentPage, pageSize, filterField, filterValue);
    });

    window.removeUser = function(username) {
        if (confirm("Are you sure you want to remove this user?")) {
            const xhr = new XMLHttpRequest();
            xhr.open("DELETE", `http://localhost:8080/api/v1/users/${username}`, true);
            xhr.setRequestHeader("Authorization", token);

            xhr.onreadystatechange = async function () {
                if (xhr.readyState === 4) {
                    if (xhr.status === 200) {
                        listUsers(currentPage, pageSize, filterField, filterValue); // Reload current page after deletion
                    } else if (xhr.status === 401) {
                        document.querySelector('body').style.display = 'none';
                        localStorage.removeItem("jwtToken");
                        await wait(300);
                        alert("Fail! Current user has been removed by admin");
                        window.location.href = "login.html";
                    } else {
                        alert("Failed to remove user. Please try again.");
                    }
                }
            };

            xhr.send();
        }
    }

    listUsers(currentPage, pageSize);
});