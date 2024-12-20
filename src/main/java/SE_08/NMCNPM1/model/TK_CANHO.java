package SE_08.NMCNPM1.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TK_CANHO {
    @Embeddable
    public static class TKCANHOKey implements Serializable {
        private int fee_id;
        private String room_number;

        public int getFee_id() {
            return fee_id;
        }
        public void setFee_id(int fee_id) {
            this.fee_id = fee_id;
        }
        public String getRoom_number() {
            return room_number;
        }
        public void setRoom_number(String room_number) {
            this.room_number = room_number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TKCANHOKey that = (TKCANHOKey) o;
            if (fee_id != that.fee_id) return false;
            return Objects.equals(room_number, that.room_number);
        }

        @Override
        public int hashCode() {
            int result = fee_id;
            result = 31 * result + (room_number != null ? room_number.hashCode() : 0);
            return result;
        }
    }

    @EmbeddedId
    private TKCANHOKey id;

    private String owner_name;
    private String fee_name;
    private int paid;
    private int required;
    private LocalDateTime created_at;
    private LocalDateTime hanchot;

    public TKCANHOKey getId() {
        return id;
    }

    public int getPaid() {
        return paid;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public String getFee_name() {
        return fee_name;
    }

    public int getRequired() {
        return required;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getHanchot() {
        return hanchot;
    }



}
