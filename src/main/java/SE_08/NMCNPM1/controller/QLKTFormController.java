package SE_08.NMCNPM1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QLKTFormController {
    @GetMapping("/form-qlkt")
    public String QLKTForm() {
        return "form-qlkt";
    }
}
