package SE_08.NMCNPM1.model;

import jakarta.persistence.*;
import java.lang.String;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class TK_KHOANTHU {
    @Id
    private int fee_id;
    private Integer TIENDANOP;
    private String tenkhoanthu;
    private String ngaytao;
    private String loaikhoanthu;
    private Integer TONGPHAITHU;
    private int HODANOP;
    private int HOPHAITHU;

    // Getters and setters
    public int getHODANOP() {

        return HODANOP;
    }

    public int getHOPHAITHU() {

        return HOPHAITHU;
    }

    public int getId() {
        return fee_id;
    }

    public Integer getTIENDANOP() {

        return TIENDANOP;
    }

    public Integer getTONGPHAITHU() {

        return TONGPHAITHU;
    }

    public String getNAME() {

        return tenkhoanthu;
    }

    public String getNgaytao() {
        return ngaytao;
    }

    public String getLoaikhoanthu() {
        return loaikhoanthu;
    }

}

