package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.model.KhoanthuDTO;
import SE_08.NMCNPM1.repository.KhoanthuRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/quan-ly-khoan-thu/list")
public class KhoanThuController {

    @Autowired
    private KhoanthuRepository repo;

    @GetMapping({"", "/"})
    public String showKhoanthuList(Model model) {
        // Lấy danh sách Khoanthu từ database
        List<Khoanthu> ds_khoanthu = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // Kiểm tra và log kết quả
        if (ds_khoanthu.isEmpty()) {
            System.out.println("Không có dữ liệu trong bảng ");
        } else {
            System.out.println("Danh sách Khoan Thu đã được lấy từ cơ sở dữ liệu.");
            System.out.println("Danh sách Khoanthu: " + ds_khoanthu);  // Log toàn bộ danh sách
        }

        model.addAttribute("khoanthu_list", ds_khoanthu);
        return "khoanthu";
    }

    @GetMapping("/form-qlkt")
    public String showCreatePage(Model model) {
        System.out.println("Truy cập vào trang tạo mới Khoan Thu.");

        // Tạo đối tượng khoanthuDto mới và thêm vào model
        KhoanthuDTO khoanthuDto = new KhoanthuDTO();
        model.addAttribute("khoanthuDto", khoanthuDto);
        System.out.println("Thêm đối tượng khoanthuDto vào model: " + khoanthuDto);

        return "form-qlkt";
    }

    @PostMapping("/create")
    public String createKhoanThu(
            @Valid @ModelAttribute("khoanthuDto") KhoanthuDTO khoanthuDto,  // Đảm bảo tên khớp với model
            BindingResult result,
            Model model) {

        // Kiểm tra lỗi nhập liệu và log thông tin
        if (result.hasErrors()) {
            System.out.println("Có lỗi trong việc nhập liệu.");
            result.getAllErrors().forEach(error -> {
                System.out.println("Lỗi: " + error.getDefaultMessage());
            });
            model.addAttribute("khoanthuDto", khoanthuDto);
            return "form-qlkt";
        }

        System.out.println("Không có lỗi trong việc nhập liệu, tiếp tục tạo Khoan Thu.");
        LocalDateTime ngaytao = LocalDateTime.now();

        Khoanthu khoanthu = new Khoanthu();
        khoanthu.setTenkhoanthu(khoanthuDto.getTenkhoanthu());
        khoanthu.setSotien(khoanthuDto.getSotien());
        khoanthu.setBatbuoc(khoanthuDto.getBatbuoc());
        khoanthu.setHanchot(khoanthuDto.getHanchot());
        khoanthu.setNguoitao(khoanthuDto.getNguoitao());
        khoanthu.setNgaytao(ngaytao);

        // Log thông tin khoản thu sẽ được lưu
        System.out.println("Thông tin Khoan Thu: " + khoanthu);

        repo.save(khoanthu);

        System.out.println("Khoan Thu đã được lưu vào cơ sở dữ liệu.");
        return "redirect:/quan-ly-khoan-thu/list";
    }

    // Xử lý nút Cancel
    @GetMapping("/cancel")
    public String cancelCreate() {
        System.out.println("Người dùng hủy việc tạo Khoan Thu.");
        // Chuyển hướng về danh sách khoản thu
        return "redirect:/quan-ly-khoan-thu/list";
    }
}
