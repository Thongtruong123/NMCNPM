package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.KhoanThu;
import SE_08.NMCNPM1.model.KhoanthuDTO;
import SE_08.NMCNPM1.repository.KhoanThuRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller

public class KhoanThuController {

    @Autowired
    private KhoanThuRepository repo;

    @GetMapping({"/quan-ly-khoan-thu"})
    public String showKhoanthuList(Model model) {
        // Lấy danh sách Khoanthu từ database
        List<KhoanThu> ds_khoanthu = repo.findAll(Sort.by(Sort.Direction.ASC, "id"));

        // Kiểm tra và log kết quả
        if (ds_khoanthu.isEmpty()) {
            System.out.println("Không có dữ liệu trong bảng ");
        } else {
            System.out.println("Danh sách Khoan Thu đã được lấy từ cơ sở dữ liệu.");
            System.out.println("Danh sách Khoanthu: " + ds_khoanthu);  // Log toàn bộ danh sách
        }

        model.addAttribute("khoanthu_list", ds_khoanthu);
        return "quan-ly-khoan-thu";
    }

    @GetMapping("/create")
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

        KhoanThu khoanthu = new KhoanThu();

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
        return "redirect:/quan-ly-khoan-thu";

    }

    // Xử lý nút Cancel
//    @GetMapping("/cancel")
//    public String cancelCreate() {
//        System.out.println("Người dùng hủy việc tạo Khoan Thu.");
//        // Chuyển hướng về danh sách khoản thu
//        return "redirect:/khoanthu";
//    }
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {
        try {
            // Tìm khoản thu theo ID sử dụng Optional
            KhoanThu khoanthu = repo.findById(id).orElse(null);  // Chuyển từ Optional sang Khoanthu nếu tìm thấy

            if (khoanthu == null) {
                // Nếu không tìm thấy, chuyển hướng về trang quản lý khoản thu
                return "redirect:/quan-ly-khoan-thu";
            }

            // Tạo KhoanthuDTO từ Entity để sử dụng cho form
            KhoanthuDTO khoanthuDto = new KhoanthuDTO();
            khoanthuDto.setTenkhoanthu(khoanthu.getTenkhoanthu());
            khoanthuDto.setSotien(khoanthu.getSotien());
            khoanthuDto.setBatbuoc(khoanthu.getBatbuoc());
            khoanthuDto.setHanchot(khoanthu.getHanchot());
            khoanthuDto.setNguoitao(khoanthu.getNguoitao());

            // Đưa DTO vào model để render form
            model.addAttribute("khoanthuDto", khoanthuDto);
            model.addAttribute("id", id); // Truyền ID để sử dụng trong form
            return "form-qlkt";
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/quan-ly-khoan-thu";
        }
    }

    @PostMapping("/edit")
    public String updateKhoanthu(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute("khoanthuDto") KhoanthuDTO khoanthuDto,
            BindingResult result) {
        try {
            // Tìm khoản thu theo ID
            Optional<KhoanThu> optionalKhoanthu = repo.findById(id);
            if (optionalKhoanthu.isEmpty()) {
                // Nếu không tìm thấy, chuyển hướng về trang quản lý khoản thu
                return "redirect:/quan-ly-khoan-thu";
            }

            // Lấy đối tượng Khoanthu từ Optional
            KhoanThu khoanthu = optionalKhoanthu.get();

            // Kiểm tra lỗi validate
            if (result.hasErrors()) {
                // Trả lại form với lỗi
                model.addAttribute("id", id); // Đảm bảo ID vẫn có trong model
                return "form-qlkt";
            }

            // Cập nhật dữ liệu từ DTO sang Entity
            khoanthu.setTenKhoanThu(khoanthuDto.getTenkhoanthu());
            khoanthu.setSotien(khoanthuDto.getSotien());
            khoanthu.setBatbuoc(khoanthuDto.getBatbuoc());
            khoanthu.setHanchot(khoanthuDto.getHanchot());
            khoanthu.setNguoitao(khoanthuDto.getNguoitao());

            // Lưu vào cơ sở dữ liệu
            repo.save(khoanthu);
            System.out.println("Khoan Thu đã được lưu vào cơ sở dữ liệu.");

            return "redirect:/quan-ly-khoan-thu";  // Chuyển hướng về trang quản lý khoản thu
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/quan-ly-khoan-thu";
        }
    }

    @GetMapping("/delete")
    public String deleteKhoanthu(
        @RequestParam int id
    ){

            repo.deleteById(id);


        return "redirect:/quan-ly-khoan-thu";
    }

}
