// Hàm hiển thị loading
function showLoading(message = "Đang tải, vui lòng đợi...") {
    const overlay = document.getElementById('loadingOverlay');
    overlay.querySelector('p').textContent = message;
    overlay.style.display = 'flex';
}

// Hàm ẩn loading
function hideLoading() {
    const overlay = document.getElementById('loadingOverlay');
    overlay.style.display = 'none';
}

// Lấy danh sách khoản chưa đóng
document.getElementById('roomNumber').addEventListener('keydown', async function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        const roomNumber = this.value.trim();
        if (!roomNumber) {
            alert("Vui lòng nhập số phòng hợp lệ.");
            return;
        }

        // Hiển thị loading
        showLoading("Đang tải danh sách khoản thu...");

        try {
            const response = await fetch(`/api/family/${roomNumber}`);
            if (!response.ok) throw new Error("Không tìm thấy thông tin hộ gia đình.");
            const familyData = await response.json();

            // Hiển thị tên chủ hộ
            document.getElementById('ownerName').value = familyData.family.ownerName || "Không có thông tin chủ hộ";

            // Hiển thị danh sách các khoản chưa đóng
            const dueAmountsDiv = document.getElementById('dueAmounts');
            dueAmountsDiv.innerHTML = ''; // Xóa nội dung cũ
            if (familyData.dueAmounts && familyData.dueAmounts.length > 0) {
                familyData.dueAmounts.forEach(due => {
                    const uniqueId = `checkbox-${due.feeId}`;
                    dueAmountsDiv.innerHTML += `
                        <div class="due-item">
                            <label for="${uniqueId}">
                                Khoản thu: ${due.name} - ${due.amount.toLocaleString()} VNĐ
                                <input type="checkbox" class="due-checkbox" id="${uniqueId}" data-id="${due.id}" data-fee-id="${due.feeId}" value="${due.amount}">
                            </label>
                        </div>`;
                });
            } else {
                dueAmountsDiv.innerHTML = '<p>Không có khoản thu nào chưa đóng.</p>';
            }
        } catch (error) {
            alert(error.message);
        } finally {
            // Ẩn loading
            hideLoading();
        }
    }
});

// Cập nhật tổng tiền
document.getElementById('dueAmounts').addEventListener('change', function() {
    const checkboxes = document.querySelectorAll('.due-checkbox');
    const totalAmount = Array.from(checkboxes)
        .filter(checkbox => checkbox.checked)
        .reduce((sum, checkbox) => sum + parseFloat(checkbox.value), 0);
    document.getElementById('paymentAmount').value = totalAmount.toLocaleString();
});

// Xử lý tạo hóa đơn
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
document.querySelector('.generate-invoice-button').addEventListener('click', async function(event) {
    event.preventDefault();

    const roomNumber = document.getElementById('roomNumber').value.trim();
    const payerName = document.getElementById('payerName').value.trim();
    const phoneNumber = document.getElementById('phoneNumber').value.trim();
    const totalAmount = parseFloat(document.getElementById('paymentAmount').value.replace(/\./g, '').replace(/,/g, '.'));

    if (!roomNumber || !payerName || !phoneNumber || totalAmount <= 0) {
        alert("Vui lòng nhập đủ thông tin và chọn khoản cần thanh toán.");
        return;
    }

    // Hiển thị loading
    showLoading("Đang tạo hóa đơn...");

    const selectedDues = Array.from(document.querySelectorAll('.due-checkbox:checked')).map(cb => ({
        id: cb.dataset.id,
        feeId: cb.dataset.feeId,
        amount: parseFloat(cb.value)
    }));

    const invoiceData = {
        roomNumber,
        payerName,
        phoneNumber,
        totalAmount,
        selectedDueAmounts: selectedDues
    };

    try {
        const response = await fetch('/api/family/invoice', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(invoiceData)
        });

        if (!response.ok) throw new Error("Không thể tạo hóa đơn.");
        const createdInvoice = await response.json();

        // Hiển thị hóa đơn sau khi tạo thành công
        openInvoiceInNewPage(createdInvoice);

        // Xóa dữ liệu sau khi tạo hóa đơn
        document.getElementById('invoiceForm').reset();
        document.getElementById('dueAmounts').innerHTML = '';
    } catch (error) {
        alert(error.message || "Lỗi không xác định.");
    } finally {
        // Ẩn loading
        hideLoading();
    }
});

// Mở hóa đơn trong trang mới
function openInvoiceInNewPage(invoice) {
    const newWindow = window.open('', '_blank');
    if (!newWindow) {
            alert("Không thể mở hóa đơn. Vui lòng kiểm tra cài đặt trình duyệt và tắt trình chặn popup.");
            return;
        }
    const dueAmounts = invoice.selectedDueAmounts || [];
    newWindow.document.write(`
        <html>
            <head>
                <title>Hóa Đơn</title>
                <style>
                    body { font-family: Arial, sans-serif; padding: 20px; }
                    .invoice-container { max-width: 800px; margin: auto; border: 1px solid #ccc; padding: 20px; background-color: #fff; }
                    h2 { text-align: center; }
                    .invoice-info, .invoice-details, .invoice-total { margin-top: 20px; }
                    table { width: 100%; border-collapse: collapse; }
                    th, td { padding: 10px; border: 1px solid #ccc; }
                    .invoice-signature { display: flex; justify-content: space-between; margin-top: 40px; }
                    .signature-block { text-align: center; width: 40%; }
                    .signature-line { border-top: 1px solid #000; margin-top: 40px; padding-top: 5px; }
                </style>
            </head>
            <body>
                <div class="invoice-container">
                    <h2>Hóa Đơn Thu Phí Chung Cư</h2>
                    <div class="invoice-info">
                        <p>Người nộp: ${invoice.payerName || "Không có"}</p>
                        <p>Số điện thoại: ${invoice.phoneNumber || "Không có"}</p>
                        <p>Số phòng: ${invoice.roomNumber}</p>
                        <p>Ngày tạo: ${new Date(invoice.createdAt).toLocaleString()}</p>
                        <p>Người tạo hóa đơn: ${invoice.createdBy}</p>
                    </div>
                    <div class="invoice-details">
                        <h3>Chi Tiết Các Khoản Thu</h3>
                        <table>
                            <thead>
                                <tr><th>Tên Khoản Thu</th><th>Số Tiền</th></tr>
                            </thead>
                            <tbody>
                                ${dueAmounts.map(due => `
                                    <tr><td>${due.name || "Không xác định"}</td><td>${(due.amount || 0).toLocaleString()} VNĐ</td></tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                    <div class="invoice-total">
                        <h3>Tổng Cộng</h3>
                        <p>Tổng Tiền: ${(invoice.totalAmount * 1000).toLocaleString()} VNĐ</p>
                    </div>
                    <div class="invoice-signature">
                        <div class="signature-block">
                            <p class="signature-line">Họ tên người nộp</p>
                        </div>
                        <div class="signature-block">
                            <p class="signature-line">Họ tên người tạo hóa đơn</p>
                        </div>
                    </div>
                </div>
                <script>
                    window.print();
                </script>
            </body>
        </html>
    `);
}
