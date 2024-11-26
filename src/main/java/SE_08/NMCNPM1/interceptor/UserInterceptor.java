package SE_08.NMCNPM1.interceptor;

import SE_08.NMCNPM1.model.User;
import SE_08.NMCNPM1.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private final UserService userService;

    public UserInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {

        if (modelAndView != null && request.getUserPrincipal() != null) {
            String username = request.getUserPrincipal().getName();
            User user = userService.getUserByUsername(username);
            if (user != null) {
                modelAndView.addObject("currentUsername", user.getUsername());
                modelAndView.addObject("currentAvatar", user.getAvatar_path());
            }

        }
    }
}
