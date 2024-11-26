package SE_08.NMCNPM1.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/index")
    public String login() {
        return "index";
    }

    @GetMapping("/homepage")
    public String loginSuccess(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        request.getSession().setAttribute("username", username);
        model.addAttribute("username", username);

        return "homepage";
    }
}

