package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.TK_CANHO;
import SE_08.NMCNPM1.model.TK_KHOANTHU;
import SE_08.NMCNPM1.repository.TK_CANHORepository;
import SE_08.NMCNPM1.repository.TK_KHOANTHURepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class THONGKEController {

    @Autowired
    private TK_KHOANTHURepository tkKhoanthuRepo;

    @Autowired
    private TK_CANHORepository tkCanhoRepo;

    @GetMapping("/thong-ke-dong-gop")
    public String showTHONGKE(@RequestParam(name = "id", required = false) Integer id,
                              Model model) throws JsonProcessingException {
        id = id > 0 ? id : 0;
        List <TK_KHOANTHU> records = tkKhoanthuRepo.findByFee_id(id);
        List < TK_CANHO> payments = tkCanhoRepo.findByFee_id(id);

        records.sort(Comparator.comparing(this::getStatusPriority));


        model.addAttribute("records", records);
        model.addAttribute("payments", payments);
        model.addAttribute("id", id);
        Optional<TK_KHOANTHU> recordOpt = tkKhoanthuRepo.findById(id);
        if (recordOpt.isPresent()) {
            TK_KHOANTHU record = recordOpt.get();
            model.addAttribute("tiendanop", record.getTIENDANOP());
            model.addAttribute("tongphaithu", record.getTONGPHAITHU());
            model.addAttribute("name", record.getNAME());
            model.addAttribute("hophaithu", record.getHOPHAITHU());
            model.addAttribute("hodanop", record.getHODANOP());
            model.addAttribute("hanchot", record.getHanchot());
        }
        else{
            model.addAttribute("tiendanop", 0);
            model.addAttribute("tongphaithu", 0);
            model.addAttribute("name", "");
            model.addAttribute("hophaithu", 0);
            model.addAttribute("hodanop", 0);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String recordsJson = objectMapper.writeValueAsString(records);
        model.addAttribute("recordsJson", recordsJson);
        return "thong-ke-dong-gop";
    }

    private String getStatus(TK_KHOANTHU record) {
        if (record.getHanchot() != null && record.getHanchot().before(new java.util.Date())) {
            return "Kết thúc";
        } else if (Objects.equals(record.getTIENDANOP(), record.getTONGPHAITHU())) {
            return "Hoàn thành";
        } else {
            return "Diễn ra";
        }
    }

    private int getStatusPriority(TK_KHOANTHU record) {
        String status = getStatus(record);
        return switch (status) {
            case "Diễn ra" -> 1;
            case "Hoàn thành" -> 2;
            case "Kết thúc" -> 3;
            default -> Integer.MAX_VALUE;
        };
    }
}
