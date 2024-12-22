package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.Invoice;
import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.repository.InvoiceRepository;
import SE_08.NMCNPM1.repository.KhoanThuRepository;
import SE_08.NMCNPM1.service.NewsService;
import SE_08.NMCNPM1.service.WeatherService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class HomepageController {

    private final WeatherService weatherService;

    @Autowired
    private KhoanThuRepository khoanThuRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    public HomepageController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/homepage")
    public String getHomepage(Model model, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        request.getSession().setAttribute("username", username);

        model.addAttribute("temperature", weatherService.getTemperature());
        model.addAttribute("description", weatherService.getDescription());
        model.addAttribute("weatherID", weatherService.getWeatherId());
        model.addAttribute("backgroundColor", weatherService.getBackgroundColor());
        model.addAttribute("icon", weatherService.getIcon());

        List<Khoanthu> top5khoanthu = khoanThuRepository.findTop10ByHanchotGreaterThanOrderByHanchotAsc(LocalDateTime.now());
        model.addAttribute("top5khoanthu", top5khoanthu);

        List<Invoice> top5hoadon = invoiceRepository.findTop6ByOrderByCreatedAtDesc();
        model.addAttribute("top5hoadon", top5hoadon);

        List<Khoanthu> allkhoanthu = khoanThuRepository.findAll(Sort.by(Sort.Direction.DESC, "ngaytao"));
        allkhoanthu.removeIf(khoanthu -> khoanthu.getNgaytao() == null || khoanthu.getHanchot() == null);
        for (Khoanthu khoanthu : allkhoanthu) {
            System.out.println("abc " + khoanthu.getNgaytao());
        }
        model.addAttribute("allkhoanthu", allkhoanthu);
        return "homepage";
    }

}
