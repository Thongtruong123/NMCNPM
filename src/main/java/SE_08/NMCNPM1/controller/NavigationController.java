package SE_08.NMCNPM1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController {

//    @GetMapping("/quan-ly-khoan-thu")
//    public String getQLKT() {
//        return "quan-ly-khoan-thu";
//    }

    @GetMapping("/thu-phi")
    public String getTP() {
        return "thu-phi";
    }

    @GetMapping("/thong-ke-dong-gop")
    public String getTKDG() {
        return "thong-ke-dong-gop";
    }

    @GetMapping("/thong-tin-nhan-khau")
    public String getTTNK() {
        return "thong-tin-nhan-khau";
    }

    @GetMapping("/thong-tin-tai-khoan")
    public String getTTTK() {
        return "thong-tin-tai-khoan";
    }

    @GetMapping("/logout")
    public String getDX() {
        return "index";
    }
}
