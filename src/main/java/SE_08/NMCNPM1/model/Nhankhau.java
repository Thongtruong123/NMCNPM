package SE_08.NMCNPM1.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "nhan_khau")
public class Nhankhau {

    @EmbeddedId
    private NhankhauId id;

    @Column(name = "gioitinh")
    private String gioitinh;

    @Column(name = "ngaysinh")
    private Date ngaysinh;

    @Column(name = "vaitro")
    private String vaitro;

    @Column(name = "sodienthoai")
    private String sodienthoai;

    @Column(name = "quequan")
    private String quequan;

    @Column(name = "thuongtru")
    private String thuongtru;

    @Column(name = "tamtru")
    private String tamtru;

    public Nhankhau() {}

    public Nhankhau(NhankhauId id, String gioitinh, Date ngaysinh, String vaitro, String sodienthoai, String quequan, String thuongtru, String tamtru) {
        this.id = id;
        this.gioitinh = gioitinh;
        this.ngaysinh = ngaysinh;
        this.vaitro = vaitro;
        this.sodienthoai = sodienthoai;
        this.quequan = quequan;
        this.thuongtru = thuongtru;
        this.tamtru = tamtru;
    }

    public NhankhauId getId() {
        return id;
    }

    public void setId(NhankhauId id) {
        this.id = id;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
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
