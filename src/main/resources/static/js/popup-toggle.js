function showPopup() {
    const popup = document.getElementById("popup");
    if (popup) {
        popup.style.display = "block";
    }
}

document.addEventListener("DOMContentLoaded", showPopup);