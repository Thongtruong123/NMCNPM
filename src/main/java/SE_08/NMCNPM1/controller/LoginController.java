package SE_08.NMCNPM1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/")
    public String login() {
        return "index";
    }

    @PostMapping("/homepage")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if (username.equals("admin") && password.equals("1")) {
            model.addAttribute("username", username);
            return "homepage";
        }
        else {
            model.addAttribute("error", "Invalid username or password");
            return "index";
        }
    }
}
