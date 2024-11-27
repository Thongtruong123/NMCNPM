package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.model.KhoanthuDTO;
import SE_08.NMCNPM1.repository.KhoanthuRepository;
import SE_08.NMCNPM1.service.KhoanthuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private KhoanthuService khoanthuService;
    @Autowired
    private KhoanthuRepository repo;

        @GetMapping({"/quan-ly-khoan-thu"})
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
        return "quan-ly-khoan-thu";
    }
//    @GetMapping({"/quan-ly-khoan-thu", "/quan-ly-khoan-thu/page/{pageNo}"})
//    public String showKhoanthuList(
//            @PathVariable Optional<Integer> pageNo,
//            @RequestParam Optional<String> sortField,
//            @RequestParam Optional<String> sortDir,
//            Model model) {
//
//        // Lấy giá trị mặc định nếu không có tham số
//        int currentPage = pageNo.orElse(1);  // Mặc định trang đầu tiên
//        String sortBy = sortField.orElse("id");  // Mặc định sắp xếp theo id
//        String direction = sortDir.orElse("asc");  // Mặc định sắp xếp theo hướng tăng dần
//        int pageSize = 8;  // Kích thước trang
//
//        // Lấy dữ liệu phân trang
//        Page<Khoanthu> page = khoanthuService.findPaginated(currentPage, pageSize, sortBy, direction);
//        List<Khoanthu> dsKhoanthu = page.getContent();  // Danh sách dữ liệu của trang hiện tại
//
//        // Thêm thông tin phân trang vào model
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//
//        model.addAttribute("sortField", sortBy);
//        model.addAttribute("sortDir", direction);
//        model.addAttribute("reverseSortDir", direction.equals("asc") ? "desc" : "asc");
//
//        // Thêm danh sách Khoanthu vào model
//        model.addAttribute("khoanthu_list", dsKhoanthu);
//
//        // Log thông tin (nếu cần)
//        if (dsKhoanthu.isEmpty()) {
//            System.out.println("Không có dữ liệu trong bảng.");
//        } else {
//            System.out.println("Danh sách Khoan Thu đã được lấy từ cơ sở dữ liệu.");
//            System.out.println("Danh sách Khoanthu: " + dsKhoanthu);
//        }
//
//        return "quan-ly-khoan-thu";  // Trả về view "quan-ly-khoan-thu"
//    }

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
            Khoanthu khoanthu = repo.findById(id).orElse(null);  // Chuyển từ Optional sang Khoanthu nếu tìm thấy

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
            return "edit-qlkt";
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
            Optional<Khoanthu> optionalKhoanthu = repo.findById(id);
            if (optionalKhoanthu.isEmpty()) {
                // Nếu không tìm thấy, chuyển hướng về trang quản lý khoản thu
                return "redirect:/quan-ly-khoan-thu";
            }

            // Lấy đối tượng Khoanthu từ Optional
            Khoanthu khoanthu = optionalKhoanthu.get();

            // Kiểm tra lỗi validate
            if (result.hasErrors()) {
                // Trả lại form với lỗi
                model.addAttribute("id", id); // Đảm bảo ID vẫn có trong model
                return "edit-qlkt";
            }

            // Cập nhật dữ liệu từ DTO sang Entity
            khoanthu.setTenkhoanthu(khoanthuDto.getTenkhoanthu());
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
    ) {

        repo.deleteById(id);


        return "redirect:/quan-ly-khoan-thu";
    }
}

