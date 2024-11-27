function checkCredentials() {
    event.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (username === '1' && password === '1') {
        window.location.href = 'homepage.html';
    } else {
        document.getElementById('error').textContent = "Tên tài khoản hoặc mật khẩu không đúng!";
    }
}

document.getElementById('login-form').addEventListener('submit', checkCredentials);



