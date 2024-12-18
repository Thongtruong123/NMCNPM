package SE_08.NMCNPM1.model;

import jakarta.persistence.*;
import java.lang.String;

@Entity
public class TK_KHOANTHU {
    @Id
    private int fee_id;
    private Integer TIENDANOP;
    private String tenkhoanthu;
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

}

