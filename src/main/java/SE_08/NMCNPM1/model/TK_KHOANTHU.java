package SE_08.NMCNPM1.model;

import jakarta.persistence.*;
import java.lang.String;

@Entity
public class TK_KHOANTHU {
    @Id
    private int fee_id;
    private int TIENDANOP;
    private String tenkhoanthu;
    private int TONGPHAITHU;

    // Getters and setters
    public int getId() {
        return fee_id;
    }

    public int getTIENDANOP() {

        return TIENDANOP;
    }

    public int getTONGPHAITHU() {

        return TONGPHAITHU;
    }

    public String getNAME() {

        return tenkhoanthu;
    }
}

