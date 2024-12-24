package SE_08.NMCNPM1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class KhoanthuDTO {
    @NotEmpty(message = "Trường tên khoản thu không được để trống")
    private String tenkhoanthu;

    @NotNull(message = "Trường số tiền không được để trống")
    @Min(value = 0, message = "Số tiền phải lớn hơn hoặc bằng 0")
    private int sotien;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Future(message = "Hạn chót phải là ngày trong tương lai")
    @NotNull(message = "Trường hạn chót không được để trống")
    private LocalDateTime hanchot;

    @NotEmpty(message = "Trường bắt buộc không được để trống")
    private String batbuoc;

    @NotEmpty(message = "Trường người tạo không được để trống")
    private String nguoitao;

    @NotEmpty(message = "Trường loaikhoanthu không được để trống")
    private String loaikhoanthu;

    // Getter và Setter
    public String getLoaikhoanthu() {
        return loaikhoanthu;
    }

    public void setLoaikhoanthu(String loaikhoanthu) {
        this.loaikhoanthu = loaikhoanthu;
    }

    public String getTenkhoanthu() {
        return tenkhoanthu;
    }

    public void setTenkhoanthu(String tenkhoanthu) {
        this.tenkhoanthu = tenkhoanthu;
    }

    public int getSotien() {
        return sotien;
    }

    public void setSotien(int sotien) {
        this.sotien = sotien;
    }

    public LocalDateTime getHanchot() {
        return hanchot;
    }

    public void setHanchot(LocalDateTime hanchot) {
        this.hanchot = hanchot;
    }

    public String getBatbuoc() {
        return batbuoc;
    }

    public void setBatbuoc(String batbuoc) {
        this.batbuoc = batbuoc;
    }

    public String getNguoitao() {
        return nguoitao;
    }

    public void setNguoitao(String nguoitao) {
        this.nguoitao = nguoitao;
    }
}
