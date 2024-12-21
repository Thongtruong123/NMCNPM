function confirmDelete(event, button) {
        if (confirm('Bạn có chắc chắn muốn xóa khoản thu này không?')) {
            // Nếu người dùng chọn OK, thì tiến hành hiển thị loading
            showLoading(button);
        } else {
            // Nếu người dùng chọn Cancel, ngừng sự kiện
            event.preventDefault();
        }
    }

    function showLoading(button) {
        button.innerHTML = '';
        var spinner = document.createElement('div');
        spinner.classList.add('spinner');
        button.appendChild(spinner);
        button.classList.add('loading'); // Disable button
    }

    function changeColor(clickedElement) {
        // Xóa lớp 'clicked' khỏi tất cả các nút
        const allLinks = document.querySelectorAll('.page-link');
        allLinks.forEach(link => {
            link.style.backgroundColor = '#2ecc71';
            link.style.color = 'black';
            link.style.border = 'none';
        });

        // Thêm màu cho nút được click
        const clickedLink = clickedElement.querySelector('a');
        clickedLink.style.backgroundColor = 'white';
        clickedLink.style.color = 'black';
        clickedLink.style.border = '2px solid #3498db';
    }