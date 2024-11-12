// Lấy các khoản chưa đóng khi nhập số phòng và nhấn Enter
document.getElementById('roomNumber').addEventListener('keydown', async function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        const roomNumber = this.value.trim();
        if (roomNumber) {
            try {
                const response = await fetch(`/api/family/${roomNumber}`);
                if (!response.ok) throw new Error("Không tìm thấy thông tin hộ gia đình.");

                const contentType = response.headers.get("content-type");
                if (!contentType || !contentType.includes("application/json")) {
                    throw new Error("Phản hồi không đúng định dạng JSON.");
                }

                const familyData = await response.json();
                document.getElementById('ownerName').value = familyData.ownerName || "Không có thông tin chủ hộ";

                const dueAmountsDiv = document.getElementById('dueAmounts');
                dueAmountsDiv.innerHTML = '';

                if (familyData.dueAmounts && familyData.dueAmounts.length > 0) {
                    familyData.dueAmounts.forEach(due => {
                        dueAmountsDiv.innerHTML += `
                            <div class="due-item">
                                <span>${due.name} - ${due.amount}</span>
                                <input type="checkbox" data-id="${due.id}" value="${due.amount}" class="due-checkbox">
                            </div>
                        `;
                    });
                } else {
                    dueAmountsDiv.innerHTML = '<p>Không có khoản thu nào chưa đóng.</p>';
                }
            } catch (error) {
                console.error("Error fetching family data:", error);
                alert(error.message);
            }
        } else {
            alert("Vui lòng nhập số phòng hợp lệ.");
        }
    }
});

// Cập nhật tổng tiền khi tích chọn
document.getElementById('dueAmounts').addEventListener('change', function() {
    const checkboxes = document.querySelectorAll('.due-checkbox');
    let totalAmount = 0;
    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            totalAmount += parseFloat(checkbox.value);
        }
    });
    document.getElementById('paymentAmount').value = totalAmount;
});

// Xử lý khi nhấn nút tạo hóa đơn
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

document.querySelector('.generate-invoice-button').addEventListener('click', async function(event) {
    event.preventDefault();

    const roomNumber = document.getElementById('roomNumber').value.trim();
    const ownerName = document.getElementById('payerName').value.trim() || null;
    const phoneNumber = document.getElementById('phoneNumber').value.trim() || null;
    const totalAmount = parseFloat(document.getElementById('paymentAmount').value);

    if (!roomNumber || !totalAmount) {
        alert("Vui lòng nhập đủ thông tin và chọn khoản cần thanh toán.");
        return;
    }

    const selectedDues = Array.from(document.querySelectorAll('.due-checkbox:checked')).map(cb => ({
        id: cb.dataset.id,
        amount: parseFloat(cb.value)
    }));

    const invoiceData = {
        roomNumber,
        ownerName,
        phoneNumber,
        totalAmount,
        selectedDueAmounts: selectedDues
    };
    console.log(invoiceData);

    try {
        const response = await fetch('/api/family/invoice', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(invoiceData),
            credentials: 'include'
        });

        if (!response.ok) throw new Error("Không thể tạo hóa đơn.");

        const contentType = response.headers.get("content-type");
        if (!contentType || !contentType.includes("application/json")) {
            throw new Error("Phản hồi không đúng định dạng JSON.");
        }

        const createdInvoice = await response.json();
        openInvoiceInNewPage(createdInvoice); // Mở hóa đơn trong trang mới
    } catch (error) {
        console.error("Error creating invoice:", error);
        alert("Lỗi khi tạo hóa đơn. Vui lòng kiểm tra lại.");
    }
});

// Mở hóa đơn trong trang mới
function openInvoiceInNewPage(invoice) {
    const newWindow = window.open('', '_blank');
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
                        <p>Người nộp: ${invoice.ownerName || "Không có"}</p>
                        <p>Số phòng: ${invoice.roomNumber}</p>
                        <p>Ngày tạo: ${new Date(invoice.createdAt).toLocaleString()}</p>
                        <p>Người tạo hóa đơn: ${invoice.createdBy}</p>
                    </div>
                    <div class="invoice-details">
                        <h3>Chi Tiết Các Khoản Chưa Đóng</h3>
                        <table>
                            <thead>
                                <tr><th>Tên Khoản Thu</th><th>Số Tiền</th></tr>
                            </thead>
                            <tbody>
                                ${invoice.selectedDueAmounts.map(due => `
                                    <tr><td>${due.name}</td><td>${due.amount}</td></tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </div>
                    <div class="invoice-total">
                        <h3>Tổng Cộng</h3>
                        <p>Tổng Tiền: ${invoice.totalAmount}</p>
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
