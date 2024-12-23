package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.RegisterFormDTO;
import SE_08.NMCNPM1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/quan-ly-tai-khoan/tao-tai-khoan")
    public String registerForm(Model model) {
        model.addAttribute("RegisterFormDTO", new RegisterFormDTO());

        return "tao-tai-khoan";
    }

    @PostMapping("/quan-ly-tai-khoan/register")
    public String register(@Valid @ModelAttribute("RegisterFormDTO") RegisterFormDTO registerFormDTO,
                            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "tao-tai-khoan";
        }
        if (userService.usernameExist(registerFormDTO.getUsername())) {
            model.addAttribute("usernameError", "Tên tài khoản đã tồn tại");
            return "tao-tai-khoan";
        }
        userService.registerNewUser(registerFormDTO);
        model.addAttribute("registrationSuccess", "Đăng ký tài khoản mới thành công!");
        return "tao-tai-khoan";
    }
}
