let currentIndex = 0;
let itemWidth;
let itemMargin;

function updateItemDimensions() {
    const item = document.querySelector('.item');
    if (!item) return;

    itemWidth = item.offsetWidth; // Lấy chiều rộng của item
    const styles = getComputedStyle(item);
    itemMargin = parseInt(styles.marginLeft) + parseInt(styles.marginRight); // Lấy tổng margin
}

window.addEventListener('resize', updateItemDimensions);
updateItemDimensions(); // Cập nhật giá trị ban đầu

function moveLeft() {
    const sliderList = document.querySelector('.slider .slider-list');
    if (!sliderList || itemWidth === 0) return;

    if (currentIndex > 0) {
        currentIndex--;
        const offset = (itemWidth + itemMargin) * currentIndex;
        sliderList.style.transform = `translateX(-${offset}px)`;
    }
}

function moveRight() {
    const sliderList = document.querySelector('.slider .slider-list');
    const totalItems = document.querySelectorAll('.item').length;
    if (!sliderList || itemWidth === 0 || totalItems === 0) return;

    const visibleItems = 3; // Hiển thị tối đa 3 phần tử
    if (currentIndex < totalItems - visibleItems) {
        currentIndex++;
        const offset = (itemWidth + itemMargin) * currentIndex;
        sliderList.style.transform = `translateX(-${offset}px)`;
    }
}

function setBackgroundImage(item) {
    const loaikhoanthu = item.getAttribute('data-loaikhoanthu');

    let imageUrl = '';
    switch (loaikhoanthu) {
        case 'Dịch vụ':
            imageUrl = '/image/dich-vu.jpg';
            break;
        case 'Quản lý':
            imageUrl = '/image/quan-ly.jpg';
            break;
        case 'Điện nước':
            imageUrl = '/image/dien-nuoc.png';
            break;
        case 'Quyên góp':
            imageUrl = '/image/quyen-gop.jpg';
            break;
        case 'Gửi xe':
            imageUrl = '/image/gui-xe.jpeg';
            break;
        default:
                    console.log('Không tìm thấy loại khoản thu hợp lệ'); // Kiểm tra loại không hợp lệ

    }
    item.style.backgroundImage = `url('${imageUrl}')`;
    item.style.backgroundSize = 'cover';
    item.style.backgroundPosition = 'center';
}

document.querySelectorAll('.item').forEach(item => {
    setBackgroundImage(item);
});
