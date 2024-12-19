function enableEditing() {
    document.getElementById("username").removeAttribute("disabled");
    document.getElementById("first_name").removeAttribute("disabled");
    document.getElementById("last_name").removeAttribute("disabled");
    document.getElementById("phone_number").removeAttribute("disabled");
    document.getElementById("email").removeAttribute("disabled");

    document.getElementById("editButton").style.display = "none";
    document.getElementById("saveButton").style.display = "inline-block";
}

//function updateAvatarName() {
//    var fileInput = document.getElementById("avatar");
//    if (fileInput.files.length > 0) {
//        document.getElementById("update-avatar").submit();
//    }
//}