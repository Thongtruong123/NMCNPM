package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.TK_KHOANTHU;
import SE_08.NMCNPM1.repository.THONGKE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class THONGKEController {

    @Autowired
    private THONGKE repo;

    @GetMapping({"/thong-ke-dong-gop"})
    public String showTHONGKE(Model model) {

        int id = 1;

        List <TK_KHOANTHU> records = repo.findByFee_id(id);
        for(TK_KHOANTHU record:records){
            if(id == record.getId()){
                model.addAttribute("tiendanop", record.getTIENDANOP());
                model.addAttribute("tongphaithu", record.getTONGPHAITHU());
                model.addAttribute("name", record.getNAME());
            }
        }
        return "thong-ke-dong-gop";
    }
}
