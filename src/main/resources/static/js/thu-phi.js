// Lấy danh sách khoản chưa đóng
document.getElementById('roomNumber').addEventListener('keydown', async function(event) {
    if (event.key === 'Enter') {
        event.preventDefault();
        const roomNumber = this.value.trim();
        if (roomNumber) {
            try {
                const response = await fetch(`/api/family/${roomNumber}`);
                if (!response.ok) throw new Error("Không tìm thấy thông tin hộ gia đình.");
                const familyData = await response.json();
                console.log(response);

                // Hiển thị tên chủ hộ
                document.getElementById('ownerName').value = familyData.family.ownerName || "Không có thông tin chủ hộ";

                // Hiển thị danh sách các khoản chưa đóng
                const dueAmountsDiv = document.getElementById('dueAmounts');
                dueAmountsDiv.innerHTML = ''; // Xóa nội dung cũ
                if (familyData.dueAmounts && familyData.dueAmounts.length > 0) {
                    familyData.dueAmounts.forEach(due => {
                        const dueItem = document.createElement('div');
                        dueItem.className = 'due-item';

                        // Tạo thông tin khoản thu
                        //const dueInfo = document.createElement('p');
                        const label = document.createElement('label');
                        label.textContent = `Khoản thu: ${due.name} - ${due.amount.toLocaleString()} VNĐ`;

                        // Checkbox cho khoản thu
                        const dueCheckbox = document.createElement('input');
                        dueCheckbox.type = 'checkbox';
                        dueCheckbox.className = 'due-checkbox';
                        dueCheckbox.dataset.id = due.id; // ID của khoản thu
                        dueCheckbox.dataset.feeId = due.feeId; // fee_id để theo dõi khoản thu
                        dueCheckbox.value = due.amount;
                        dueCheckbox.id = due.id;

                        //Gán id duy nhất và liên kết với label
                        const uniqueId = `checkbox-${due.feeId}`;
                        dueCheckbox.id=uniqueId;
                        label.htmlFor = uniqueId;

                        // Thêm checkbox vào label
                        label.appendChild(dueCheckbox);

                        dueItem.appendChild(label);
                        //dueItem.appendChild(dueCheckbox);
                        dueAmountsDiv.appendChild(dueItem);
                    });
                } else {
                    dueAmountsDiv.innerHTML = '<p>Không có khoản thu nào chưa đóng.</p>';
                }
            } catch (error) {
                alert(error.message);
            }
        } else {
            alert("Vui lòng nhập số phòng hợp lệ.");
        }
    }
});

// Cập nhật tổng tiền
document.getElementById('dueAmounts').addEventListener('change', function() {
    const checkboxes = document.querySelectorAll('.due-checkbox');
    const totalAmount = Array.from(checkboxes)
        .filter(checkbox => checkbox.checked)
        .reduce((sum, checkbox) => {
            const amount = parseFloat(checkbox.value);
            return sum + (isNaN(amount) ? 0 : amount);
        }, 0);
    document.getElementById('paymentAmount').value = totalAmount.toLocaleString();
    console.log(totalAmount);
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
    console.log(totalAmount);

    if (!roomNumber || !totalAmount) {
        alert("Vui lòng nhập đủ thông tin và chọn khoản cần thanh toán.");
        return;
    }

    // Lấy danh sách khoản thu đã chọn
    const selectedDues = Array.from(document.querySelectorAll('.due-checkbox:checked')).map(cb => ({
        id: cb.dataset.id, // ID của khoản thu
        feeId: cb.dataset.feeId, // fee_id để theo dõi khoản thu
        amount: parseFloat(cb.value)
    }));

    const invoiceData = {
        roomNumber,
        payerName,
        phoneNumber,
        totalAmount,
        selectedDueAmounts: selectedDues
    };
    console.log("Dữ liệu gửi lên:", invoiceData);

    try {
        const response = await fetch('/api/family/invoice', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(invoiceData)
        });
        console.log(response);
       if (!response.ok) {
           try {
               const errorData = await response.json();
               console.error("Lỗi từ server:", errorData);
               alert(errorData.error || "Lỗi không xác định khi tạo hóa đơn.");
           } catch {
               alert("Lỗi không xác định từ server.");
           }
           return;
       }
        const createdInvoice = await response.json();

        // Hiển thị hóa đơn sau khi tạo thành công
        openInvoiceInNewPage(createdInvoice);

        // Xóa dữ liệu sau khi tạo hóa đơn
        document.getElementById('dueAmounts').innerHTML = '';
        document.getElementById('paymentAmount').value = '';
    } catch (error) {
        alert("Lỗi khi tạo hóa đơn. Vui lòng thử lại.");
    }
});


// Mở hóa đơn trong trang mới
function openInvoiceInNewPage(invoice) {
    console.log("Phản hồi từ server:", invoice);
    const newWindow = window.open('', '_blank');
    const dueAmounts = invoice.selectedDueAmounts || []; // Xử lý khi null/undefined
    console.log("Dữ liệu khoản thu (dueAmounts):", dueAmounts);
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
                        <p>Tổng Tiền: ${invoice.totalAmount.toLocaleString()} VNĐ</p>
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
