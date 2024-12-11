package SE_08.NMCNPM1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "khoan_thu", schema = "account")


public class Khoanthu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tenkhoanthu")
    private String tenkhoanthu;
    @Column(name = "sotien")
    private int sotien;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH-mm-ss SSSSSS", shape = JsonFormat.Shape.STRING)
    @Column(name = "ngaytao")
    private LocalDateTime ngaytao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH-mm-ss SSSSSS", shape = JsonFormat.Shape.STRING)
    @Column(name = "hanchot")
    private LocalDateTime hanchot;

    @Column(name = "batbuoc")
    private String batbuoc;

    @Column(name = "nguoitao")
    private String nguoitao;

    @Column(name = "loaikhoanthu")
    private String loaikhoanthu;

    public String getLoaikhoanthu() {
        return loaikhoanthu;
    }

    public void setLoaikhoanthu(String loaikhoanthu) {
        this.loaikhoanthu = loaikhoanthu;
    }

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
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

    public LocalDateTime getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(LocalDateTime ngaytao) {
        this.ngaytao = ngaytao;
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

