package SE_08.NMCNPM1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Tên template trong thư mục `templates`
    }
}
