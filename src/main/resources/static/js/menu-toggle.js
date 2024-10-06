let btn = document.querySelector('#btn');
let sidebar = document.querySelector('.sidebar');

if (localStorage.getItem('sidebarState') === 'active') {
    sidebar.classList.add('active');
}

btn.onclick = function() {
    sidebar.classList.toggle('active');

    if (sidebar.classList.contains('active')) {
        localStorage.setItem('sidebarState', 'active');
    }
    else {
        localStorage.setItem('sidebarState', 'inactive');
    }
} ;