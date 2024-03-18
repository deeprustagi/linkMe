document.addEventListener("DOMContentLoaded", function() {

    // To avoid direct opeing profile page
    if (localStorage.getItem("username") === null) {
        window.location.href = "index.html";
    } else {
        showWelcome();
        displayFiles();
    }

    const fileCreationForm = document.getElementById("fileCreationForm");
    const fileList = document.getElementById("fileList");
    const logout = document.getElementById("logout");

    // user welcome
    function showWelcome() {
        const welcome = document.getElementById('welcome');
        welcome.innerHTML = "Welcome " + localStorage.getItem("username");
    }

    // logout button
    logout.addEventListener("click", function(event) {
        localStorage.removeItem("username");
        window.location.href = "index.html";
    });

    // New File form submission
    fileCreationForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const fileName = document.getElementById("fileName").value;
        const fileContent = document.getElementById("fileContent").value;

        const textBytes = new TextEncoder().encode(fileContent);
        const base64Text = btoa(String.fromCharCode.apply(null, textBytes));

        const url = "/files/" + localStorage.getItem("username");

        const file = {
            fileName: fileName,
            content: base64Text
        }

        const jsonData = JSON.stringify(file);

        fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: jsonData
        })
        .then(response => {
            if (response.ok) {
                alert("File created successfully");
                document.getElementById("fileName").value = "";
                document.getElementById("fileContent").value = "";
                displayFiles();
            } else {
                throw new Error("File creation failed");
            }
        })
        .catch(error => {
            console.error("Error during file creation:", error);
            alert("File creation failed. Please try again.");
        });
    });

    // Share or Unshare the files
    function shareButtonClick(id) {
        const url = "/files/share/" + id;

        fetch(url, {
            method: "POST",
            headers: {
            }
        })
        .then(response => {
            if (response.ok) {
                displayFiles();
            } else {
                throw new Error("File sharing failed");
            }
        })
        .catch(error => {
            console.error("Error during file sharing:", error);
            alert("File sharing failed. Please try again.");
        });
    }

    // display all files of login user
    function displayFiles() {

        const url = "/files/" + localStorage.getItem("username");

        fetch(url, {
            method: "GET",
            headers: {
            }
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error("Failed to fetch files");
            }
        })
        .then(files => {
            fileList.innerHTML = "";
            files.forEach(file => {
                const listItem = document.createElement("li");
                const anchor = document.createElement("a");
                anchor.href = `fileContent.html?id=${file.id}`;
                anchor.target = "_blank";
                anchor.textContent = file.fileName;
                const button = document.createElement("button");
                if (file.share == false) {
                    button.textContent = "Share";
                } else {
                    button.textContent = "Unshare";
                }
                button.classList.add("shareButton");
                button.addEventListener("click", function() {
                    shareButtonClick(file.id);
                });
                listItem.appendChild(anchor);
                listItem.appendChild(button);
                fileList.appendChild(listItem);
            });
        })
        .catch(error => {
            console.error("Error fetching files:", error);
            alert("Failed to fetch files. Please try again.");
        });
        
    }
});