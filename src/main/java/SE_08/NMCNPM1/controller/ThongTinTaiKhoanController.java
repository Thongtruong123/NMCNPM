package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.PasswordChangeDTO;
import SE_08.NMCNPM1.model.User;
import SE_08.NMCNPM1.repository.UserRepository;
import SE_08.NMCNPM1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Base64;

@Controller
public class ThongTinTaiKhoanController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/thong-tin-tai-khoan")
    public String getTTTK(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername);
        model.addAttribute("user", user);
        model.addAttribute("PasswordChangeDTO", new PasswordChangeDTO());
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
    public String updateInfo(User updatedUser, RedirectAttributes redirectAttributes){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User user = userRepository.findByUsername(currentUsername);
        try {
            user.setUsername(updatedUser.getUsername());
            user.setFirst_name(updatedUser.getFirst_name());
            user.setLast_name(updatedUser.getLast_name());
            user.setPhone_number(updatedUser.getPhone_number());
            user.setEmail(updatedUser.getEmail());

            userRepository.save(user);

            redirectAttributes.addFlashAttribute("message1", "Thông tin người dùng đã được cập nhật.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error1", "Đã có lỗi xảy ra khi cập nhật.");
        }
        return "redirect:/thong-tin-tai-khoan";
    }

    private String convertToBase64(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        return Base64.getEncoder().encodeToString(bytes);
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute PasswordChangeDTO passwordChangeDTO, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try {
            userService.changePassword(username, passwordChangeDTO.getOld_password(), passwordChangeDTO.getNew_password());
            redirectAttributes.addFlashAttribute("message2", "Đổi mật khẩu thành công.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error2", e.getMessage());
        }
        return "redirect:/thong-tin-tai-khoan";
    }
}
