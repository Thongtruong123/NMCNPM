function showSaveButton(selectElement) {
    const currentRole = selectElement.getAttribute("data-current-role");
    const selectedRole = selectElement.value;

    // Tìm nút "Lưu" trong cùng form với dropdown
    const saveButton = selectElement.closest('form').querySelector('.save-role');

    // Hiển thị/ẩn nút "Lưu" tùy thuộc vào sự thay đổi role
    if (selectedRole !== currentRole) {
        saveButton.style.display = "inline-block"; // Hiển thị nút "Lưu" nếu role thay đổi
    } else {
        saveButton.style.display = "none"; // Ẩn nút "Lưu" nếu role không thay đổi
    }
}

