package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.service.ForgetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgetPasswordController {
    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @GetMapping("/forgot-password")
    public String showForgotPassword() {
        return "quen-mat-khau";
    }

    @PostMapping("/forgot-password")
    public String processPassword(@RequestParam("email") String email, Model model) {
        if (!forgetPasswordService.emailFound(email)) {
            model.addAttribute("error", "Email không tồn tại.");
        }
        else {
            forgetPasswordService.sendTemporaryPasswordEmail(email);
            model.addAttribute("message", "Success");
        }
        return "quen-mat-khau";
    }
}
