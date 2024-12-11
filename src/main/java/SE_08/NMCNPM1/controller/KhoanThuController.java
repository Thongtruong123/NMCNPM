package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.model.KhoanthuDTO;
import SE_08.NMCNPM1.service.KhoanThuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller

public class KhoanThuController {

    @Autowired
    private KhoanThuService repo;




    @GetMapping("/quan-ly-khoan-thu")
    public String showKhoanthuList(Model model,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "page", defaultValue = "0") int page) { // Nhận số trang từ yêu cầu

        // Tạo Pageable với thông tin phân trang và sắp xếp
        Pageable pageable = PageRequest.of(
                page,
                7,
                Sort.by(sort != null && !sort.isEmpty() ? sort : "id").ascending()
        );


        // Lấy danh sách Khoanthu theo keyword và phân trang
        Page<Khoanthu> pageResult = repo.listAll(keyword, pageable);
        List<Khoanthu> khoanthuList = pageResult.getContent();

        // Gửi dữ liệu vào model
        model.addAttribute("khoanthu_list", khoanthuList);             // Danh sách khoản thu
        model.addAttribute("totalPages", pageResult.getTotalPages());  // Tổng số trang
        model.addAttribute("currentPage", page);                      // Trang hiện tại
        model.addAttribute("keyword", keyword);                       // Từ khóa tìm kiếm (nếu có)
        model.addAttribute("sort", sort);                             // Sắp xếp (nếu có)

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
            Model model, RedirectAttributes redirectAttributes) {

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
        redirectAttributes.addFlashAttribute("createsuccess", "Tạo khoản thu thành công!");
        return "redirect:/quan-ly-khoan-thu";

    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {
        try {
            // Tìm khoản thu theo ID sử dụng Optional
            Khoanthu khoanthu = repo.findById(id);  // Chuyển từ Optional sang Khoanthu nếu tìm thấy

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
            model.addAttribute("khoanthu_edit", khoanthuDto);
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
            @Valid @ModelAttribute("khoanthu_edit") KhoanthuDTO khoanthu_edit,
            BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            // Tìm khoản thu theo ID
            Optional<Khoanthu> optionalKhoanthu = Optional.ofNullable(repo.findById(id));
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
                model.addAttribute("khoanthu_edit", khoanthu_edit);  // Đảm bảo DTO cũng có trong model
                return "edit-qlkt";
            }

            // Cập nhật dữ liệu từ DTO sang Entity
            khoanthu.setTenkhoanthu(khoanthu_edit.getTenkhoanthu());
            khoanthu.setSotien(khoanthu_edit.getSotien());
            khoanthu.setBatbuoc(khoanthu_edit.getBatbuoc());
            khoanthu.setHanchot(khoanthu_edit.getHanchot());
            khoanthu.setNguoitao(khoanthu_edit.getNguoitao());

            // Lưu vào cơ sở dữ liệu
            repo.save(khoanthu);
            System.out.println("Khoan Thu đã sửa va và được lưu vào cơ sở dữ liệu.");
            redirectAttributes.addFlashAttribute("editsuccess", "Sửa khoản thu thành công!");
            return "redirect:/quan-ly-khoan-thu";
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/quan-ly-khoan-thu";
        }
    }


    @GetMapping("/delete")
    public String deleteKhoanthu(@RequestParam int id, RedirectAttributes redirectAttributes) {
        Optional<Khoanthu> khoanthu = Optional.ofNullable(repo.findById(id)); // Tìm khoản thu trước khi xóa
        if (khoanthu.isPresent()) {
            repo.deleteById(id);
            // Thêm thông báo xóa thành công
            redirectAttributes.addFlashAttribute("deletesuccess", "Khoản thu '" + khoanthu.get().getTenkhoanthu() + "' đã được xóa!");
        } else {
            redirectAttributes.addFlashAttribute("deletesuccess", "Không tìm thấy khoản thu cần xóa!");
        }
        return "redirect:/quan-ly-khoan-thu";
    }


}
