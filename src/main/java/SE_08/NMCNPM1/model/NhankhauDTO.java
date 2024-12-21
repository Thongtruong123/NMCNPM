package SE_08.NMCNPM1.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Date;

public class NhankhauDTO {
    @NotEmpty(message = "Trường số phòng không được để trống")
    private String roomNumber;

    @NotEmpty(message = "Trường họ và tên không được để trống")
    private String hoten;

    @NotEmpty(message = "Trường bắt buộc không được để trống")
    private String gioitinh;

    @NotNull(message = "Trường ngày sinh không được để trống")
    private Date ngaysinh;

    @NotEmpty(message = "Trường vai trò không được để trống")
    private String vaitro;

    @NotEmpty(message = "Trường số điện thoại không được để trống nếu nhập.")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Trường số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0.")
    private String sodienthoai;



    @NotEmpty(message = "Trường quê quán không được để trống")
    private String quequan;

    @NotEmpty(message = "Trường thường trú không được để trống")
    private String thuongtru;

    @NotEmpty(message = "Trường tạm trú không được để trống")
    private String tamtru;

    public @NotEmpty(message = "Trường số phòng không được để trống") String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(@NotEmpty(message = "Trường số phòng không được để trống") String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public @NotEmpty(message = "Trường họ và tên không được để trống") String getHoten() {
        return hoten;
    }

    public void setHoten(@NotEmpty(message = "Trường họ và tên không được để trống") String hoten) {
        this.hoten = hoten;
    }

    public @NotEmpty(message = "Trường bắt buộc không được để trống") String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(@NotEmpty(message = "Trường bắt buộc không được để trống") String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public @NotNull(message = "Trường ngày sinh không được để trống") Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(@NotNull(message = "Trường ngày sinh không được để trống") Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public @NotEmpty(message = "Trường vai trò không được để trống") String getVaitro() {
        return vaitro;
    }

    public void setVaitro(@NotEmpty(message = "Trường vai trò không được để trống") String vaitro) {
        this.vaitro = vaitro;
    }

    public @NotEmpty(message = "Trường số điện thoại không được để trống nếu nhập.") @Pattern(regexp = "^0[0-9]{9}$", message = "Trường số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0.") String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(@NotEmpty(message = "Trường số điện thoại không được để trống nếu nhập.") @Pattern(regexp = "^0[0-9]{9}$", message = "Trường số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0.") String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public @NotEmpty(message = "Trường quê quán không được để trống") String getQuequan() {
        return quequan;
    }

    public void setQuequan(@NotEmpty(message = "Trường quê quán không được để trống") String quequan) {
        this.quequan = quequan;
    }

    public @NotEmpty(message = "Trường thường trú không được để trống") String getThuongtru() {
        return thuongtru;
    }

    public void setThuongtru(@NotEmpty(message = "Trường thường trú không được để trống") String thuongtru) {
        this.thuongtru = thuongtru;
    }

    public @NotEmpty(message = "Trường tạm trú không được để trống") String getTamtru() {
        return tamtru;
    }

    public void setTamtru(@NotEmpty(message = "Trường tạm trú không được để trống") String tamtru) {
        this.tamtru = tamtru;
    }
}
