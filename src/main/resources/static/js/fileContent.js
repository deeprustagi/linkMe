document.addEventListener("DOMContentLoaded", function() {
  
    const requestedFileContent = document.getElementById("requestedFileContent");

    // Display file content for input id
    function displayFileContent() {

        const url = "/files/file/" + localStorage.getItem("username") + "/" + getUrlParameter("id");

        fetch(url, {
            method: "GET",
            headers: {
            }
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error("Failed to fetch file content");
            }
        })
        .then(content => {
            const decodedText = atob(content);
            requestedFileContent.innerHTML = decodedText;
        })
        .catch(error => {
            console.error("Error fetching files:", error);
            alert("No file exists with given id or file is private");
        });
        
    }

    function getUrlParameter(name) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(name);
    }

    displayFileContent();
    
});