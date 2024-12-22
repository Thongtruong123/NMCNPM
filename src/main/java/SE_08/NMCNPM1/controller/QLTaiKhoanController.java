package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.User;
import SE_08.NMCNPM1.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class QLTaiKhoanController {

    @Autowired
    private UserAuthorizationService userAuthorizationService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/quan-ly-tai-khoan")
    public String showAccount(Model model) {
        List<User> userList = userAuthorizationService.getAllUsers();
        model.addAttribute("userList", userList);
        return "quan-ly-tai-khoan";
    }

    @PostMapping("/quan-ly-tai-khoan")
    public String changeUserRole(@RequestParam Long userId, @RequestParam String role) {
        userAuthorizationService.updateUserRole(userId, role);
        return "redirect:/quan-ly-tai-khoan";
    }
}

