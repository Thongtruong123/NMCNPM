package SE_08.NMCNPM1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

public class NhankhauDTO {
    @NotEmpty(message = "Trường số phòng không được để trống")
    private String roomNumber;

    @NotEmpty(message = "Trường họ và tên không được để trống")
    private String hoten;

    @NotEmpty(message = "Trường bắt buộc không được để trống")
    private String gioitinh;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Trường ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDateTime ngaysinh;

    @NotEmpty(message = "Trường vai trò không được để trống")
    private String vaitro;

    @NotEmpty
    @Pattern(regexp = "^0[0-9]{9}$", message = "Trường số điện thoại (nếu nhập) phải gồm 10 chữ số và bắt đầu bằng số 0.")
    private String sodienthoai;

    @NotEmpty(message = "Trường quê quán không được để trống")
    private String quequan;

    @NotEmpty(message = "Trường thường trú không được để trống")
    private String thuongtru;

    @NotEmpty(message = "Trường tạm trú không được để trống")
    private String tamtru;

    // Getter và Setter
    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public LocalDateTime getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(LocalDateTime ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getThuongtru() {
        return thuongtru;
    }

    public void setThuongtru(String thuongtru) {
        this.thuongtru = thuongtru;
    }

    public String getTamtru() {
        return tamtru;
    }

    public void setTamtru(String tamtru) {
        this.tamtru = tamtru;
    }
}
