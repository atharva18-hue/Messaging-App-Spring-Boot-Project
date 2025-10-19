document.addEventListener("DOMContentLoaded", function () {
    const token = localStorage.getItem("jwtToken");

    const decodedToken = parseJwt(token);

    if (decodedToken.isAdmin === false) {
        document.getElementById("nav-list-user").style.display = "none";
        document.getElementById("nav-add-user").style.display = "none";
        document.getElementById("main-page").href = "user-dashboard.html";
    }

    if (!token) {
        alert("You are not logged in!");
        window.location.href = "login.html";
        return;
    }

    let currentPage = 0;
    let pageSize = 10;
    let filterField = '';
    let filterValue = '';
    let totalMessages = 0;

    const pageSizeSelect = document.getElementById("page-size");
    const pageNumberSelect = document.getElementById("page-number");
    const filterFieldInboxSelect = document.getElementById("filter-field-inbox");
    const filterFieldOutboxSelect = document.getElementById("filter-field-outbox");
    const filterValueInput = document.getElementById("filter-value");
    const filterBtn = document.getElementById("filter-btn");
    const sortFieldSelect = document.getElementById("sort-field");
    const sortOrderSelect = document.getElementById("sort-order");
    const sortBtn = document.getElementById("sort-btn");

    const inboxBtn = document.getElementById("inbox-btn");
    const outboxBtn = document.getElementById("outbox-btn");
    const mailboxTitle = document.getElementById("mailbox-title");
    const dynamicHeader = document.getElementById("dynamic-header");
    const mailboxTableBody = document.getElementById("mailbox-table-body");
    const messageDiv = document.getElementById("message");

    let isInbox = true;
    let allMessages = [];

    inboxBtn.addEventListener("click", function () {
        isInbox = true;
        mailboxTitle.textContent = "Inbox";
        dynamicHeader.textContent = "From User";
        filterFieldInboxSelect.style.display = "block";
        filterFieldOutboxSelect.style.display = "none";
        filterField = '';
        filterValue = '';
        fetchMailbox(currentPage, pageSize);
    });

    outboxBtn.addEventListener("click", function () {
        isInbox = false;
        mailboxTitle.textContent = "Outbox";
        dynamicHeader.textContent = "To User";
        filterFieldInboxSelect.style.display = "none";
        filterFieldOutboxSelect.style.display = "block";
        filterField = '';
        filterValue = '';
        fetchMailbox(currentPage, pageSize);
    });

    pageSizeSelect.addEventListener("change", function () {
        pageSize = parseInt(this.value);
        currentPage = 0; // Reset to first page
        fetchMailbox(currentPage, pageSize);
    });

    pageNumberSelect.addEventListener("change", function () {
        currentPage = parseInt(this.value);
        fetchMailbox(currentPage, pageSize);
    });

    document.getElementById("prev-page").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            fetchMailbox(currentPage, pageSize);
        }
    });

    document.getElementById("next-page").addEventListener("click", function () {
        const totalPages = Math.ceil(totalMessages / pageSize);
        if (currentPage < totalPages - 1) {
            currentPage++;
            fetchMailbox(currentPage, pageSize);
        }
    });

    filterBtn.addEventListener("click", function () {
        filterField = isInbox ? filterFieldInboxSelect.value : filterFieldOutboxSelect.value;
        filterValue = filterValueInput.value;
        currentPage = 0; // Reset to first page
        fetchMailbox(currentPage, pageSize);
    });

    sortBtn.addEventListener("click", function () {
        displayMessages(allMessages);
    });

    function fetchMailbox(page, size) {
        const filterField = isInbox ? filterFieldInboxSelect.value : filterFieldOutboxSelect.value;
        const filterValue = filterValueInput.value;
        const xhr = new XMLHttpRequest();
        let url = isInbox
            ? `http://localhost:8080/api/v1/mailbox?type=inbox&page=${page}&size=${size}`
            : `http://localhost:8080/api/v1/mailbox?type=outbox&page=${page}&size=${size}`;

        if (filterField && filterValue) {
            url += `&field=${filterField}&value=${filterValue}`;
        }

        xhr.open("GET", url, true);
        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = async function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    const response = JSON.parse(xhr.responseText);
                    totalMessages = response.totalElements;
                    allMessages = response.content;
                    displayMessages(allMessages);
                } else if (xhr.status === 401) {
                    document.querySelector('body').style.display = 'none';
                    localStorage.removeItem("jwtToken");
                    await wait(300);
                    alert("Fail! Current user has been removed by admin");
                    window.location.href = "login.html";
                } else {
                    messageDiv.textContent = "Failed to fetch messages. Please try again.";
                    messageDiv.style.display = "block";
                }
            }
        };

        xhr.send();
    }

    function formatDate(dateString) {
        const options = { year: 'numeric', month: 'numeric', day: 'numeric', hour: '2-digit', minute: '2-digit' };
        const date = new Date(dateString);
        return date.toLocaleDateString(undefined, options);
    }

    function displayMessages(messages) {
        const sortField = sortFieldSelect.value;
        const sortOrder = sortOrderSelect.value;

        messages.sort((a, b) => {
            if (a[sortField] < b[sortField]) {
                return sortOrder === 'asc' ? -1 : 1;
            }
            if (a[sortField] > b[sortField]) {
                return sortOrder === 'asc' ? 1 : -1;
            }
            return 0;
        });

        mailboxTableBody.innerHTML = "";

        if (messages.length === 0) {
            mailboxTableBody.innerHTML = "<tr><td colspan='4'>No messages yet!</td></tr>";
        } else {
            messages.forEach((message) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${isInbox ? message.fromUser : message.toUser}</td>
                    <td>${formatDate(message.sendDate)}</td>
                    <td>${message.subject}</td>
                    <td>${message.content}</td>
                `;
                mailboxTableBody.appendChild(row);
            });
        }

        const totalPages = Math.ceil(totalMessages / pageSize);
        pageNumberSelect.innerHTML = '';
        for (let i = 0; i < totalPages; i++) {
            const option = document.createElement("option");
            option.value = i;
            option.textContent = i + 1;
            if (i === currentPage) option.selected = true;
            pageNumberSelect.appendChild(option);
        }

        document.getElementById("prev-page").disabled = currentPage === 0;
        document.getElementById("next-page").disabled = currentPage >= totalPages - 1;
    }

    inboxBtn.click();
});