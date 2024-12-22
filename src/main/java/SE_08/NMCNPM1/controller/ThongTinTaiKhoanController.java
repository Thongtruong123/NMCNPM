package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.User;
import SE_08.NMCNPM1.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
public class ThongTinTaiKhoanController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/thong-tin-tai-khoan")
    public String getTTTK(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername);
        model.addAttribute("user", user);
        return "thong-tin-tai-khoan";
    }

    @PostMapping("/update-avatar")
    public String updateAvatar(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) throws  IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        if (currentUsername != null && !avatar.isEmpty()) {
            String base64Avatar = convertToBase64(avatar);
            User user = userRepository.findByUsername(currentUsername);
            user.setAvatar(base64Avatar);
            userRepository.save(user);
        }
        else System.out.println("controller error");
        return "redirect:/thong-tin-tai-khoan";
    }

    @PostMapping("/update-info")
    public String updateInfo(User updatedUser, RedirectAttributes redirectAttributes, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername);

        user.setUsername(updatedUser.getUsername());
        user.setFirst_name(updatedUser.getFirst_name());
        user.setLast_name(updatedUser.getLast_name());
        user.setPhone_number(updatedUser.getPhone_number());
        user.setEmail(updatedUser.getEmail());

        userRepository.save(user);

        redirectAttributes.addFlashAttribute("message", "Thông tin người dùng đã được cập nhật.");
        return "redirect:/thong-tin-tai-khoan";
    }

    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }
}
