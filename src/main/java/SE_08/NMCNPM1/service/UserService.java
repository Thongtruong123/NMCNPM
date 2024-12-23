package SE_08.NMCNPM1.service;

import SE_08.NMCNPM1.model.RegisterFormDTO;
import SE_08.NMCNPM1.model.User;
import SE_08.NMCNPM1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public void registerNewUser (RegisterFormDTO registerFormDTO) {
        User user = new User();
        user.setUsername(registerFormDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerFormDTO.getPassword()));
        user.setFirst_name(registerFormDTO.getFirst_name());
        user.setLast_name(registerFormDTO.getLast_name());
        user.setEmail(registerFormDTO.getEmail());
        user.setPhone_number(registerFormDTO.getPhone_number());
        user.setRole(registerFormDTO.getRole());

        userRepository.save(user);
    }

    public boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        else return user;
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) throws Exception {
        User user = userRepository.findByUsername(username);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new Exception("Mật khẩu cũ sai");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
