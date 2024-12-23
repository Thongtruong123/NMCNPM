package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.User;
import SE_08.NMCNPM1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class ForgetPasswordService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String generateTemporaryPassword() {
        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        return password.toString();
    }

    public void sendTemporaryPasswordEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String newPassword = generateTemporaryPassword();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Mật khẩu mới của tài khoản Quản lý chung cư BlueMoon");
            message.setText("Mật khẩu mới của bạn là: " + newPassword);
            mailSender.send(message);

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        }
    }

    public boolean emailFound(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
