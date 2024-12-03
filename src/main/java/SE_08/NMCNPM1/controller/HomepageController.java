package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {
    private final WeatherService weatherService;

    @Autowired
    public HomepageController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/homepage")
    public String getHomepage(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        request.getSession().setAttribute("username", username);
        model.addAttribute("username", username);

        model.addAttribute("temperature", weatherService.getTemperature());
        model.addAttribute("description", weatherService.getDescription());
        model.addAttribute("weatherID", weatherService.getWeatherId());
        model.addAttribute("backgroundColor", weatherService.getBackgroundColor());
        model.addAttribute("icon", weatherService.getIcon());

        return "homepage";
    }
}
