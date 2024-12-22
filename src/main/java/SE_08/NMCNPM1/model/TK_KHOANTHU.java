package SE_08.NMCNPM1.model;

import jakarta.persistence.*;
import java.lang.String;
import java.util.Date;

@Entity
public class TK_KHOANTHU {
    @Id
    private int fee_id;
    private Long TIENDANOP;
    private String tenkhoanthu;
    private String ngaytao;
    private Date hanchot;
    private String loaikhoanthu;
    private Long TONGPHAITHU;
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

    public Long getTIENDANOP() {

        return TIENDANOP;
    }

    public Long getTONGPHAITHU() {

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

    public Date getHanchot() {
        return hanchot;
    }

}

