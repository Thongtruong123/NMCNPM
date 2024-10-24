let btn = document.querySelector('#btn');
let sidebar = document.querySelector('.sidebar');

if (localStorage.getItem('sidebarState') === 'active') {
    sidebar.classList.add('active');
}

let canToggle = true;
let sidebarReactivate = 0;

function checkViewportWidth() {
    if (window.innerWidth < 800) {
        sidebar.classList.remove('active');
        localStorage.setItem('sidebarState', 'inactive');
        sidebarReactivate = 1;
        canToggle = false;
    } else {
        if (sidebarReactivate === 1) {
            sidebar.classList.add('active');
            localStorage.setItem('sidebarState', 'active');
            sidebarReactivate = 0;
        }
        canToggle = true;
    }
}

btn.onclick = function() {
    if (canToggle) {
        sidebar.classList.toggle('active');

        if (sidebar.classList.contains('active')) {
            localStorage.setItem('sidebarState', 'active');
        } else {
            localStorage.setItem('sidebarState', 'inactive');
        }
    }
};

window.addEventListener('resize', checkViewportWidth);

checkViewportWidth();
