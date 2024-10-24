package SE_08.NMCNPM1.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterFormDTO {

    @NotNull
    @Size(min = 1, max = 100)
    private String first_name;

    @NotNull
    @Size(min = 1, max = 100)
    private String last_name;

    @NotNull
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotNull
    @Size(min = 10, max = 15, message = "Số điện thoại không hợp lệ")
    private String phone_number;

    @NotNull
    @Size(min = 1)
    private String password;

    @NotNull
    private String role;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
