package SE_08.NMCNPM1.interceptor;

import SE_08.NMCNPM1.model.User;
import SE_08.NMCNPM1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public UserInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String currentUsername = (String) request.getSession().getAttribute("username");

        if (currentUsername != null) {
            User user = userService.getUserByUsername(currentUsername);

            String avatarUrl;
            if (user.getAvatar() == null) {
                avatarUrl = "/image/default-avatar.jpg";
            } else {
                avatarUrl = user.getAvatar();
            }

            request.setAttribute("currentAvatar", avatarUrl);
            request.setAttribute("currentUsername", currentUsername);
        }
    }

}
