package SE_08.NMCNPM1.controller;

import SE_08.NMCNPM1.model.Khoanthu;
import SE_08.NMCNPM1.model.KhoanthuDTO;
import SE_08.NMCNPM1.repository.KhoanThuRepository;
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

/**
 * The type Khoan thu controller.
 */
@Controller
public class KhoanThuController {

    @Autowired
    private KhoanThuService repo;

    /**
     * Show khoanthu list string.
     *
     * @param model   the model
     * @param keyword the keyword
     * @param sort    the sort
     * @param page    the page
     * @return the string
     */
    @GetMapping("/quan-ly-khoan-thu")
    public String showKhoanthuList(Model model,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "page", defaultValue = "0") int page) { // Nhận số trang từ yêu cầu

        Pageable pageable = PageRequest.of(
                page,
                7,
                Sort.by(sort != null && !sort.isEmpty() ? sort : "id").ascending()
        );

        Page<Khoanthu> pageResult = repo.listAll(keyword, pageable);
        List<Khoanthu> khoanthuList = pageResult.getContent();

        model.addAttribute("khoanthu_list", khoanthuList);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);

        return "quan-ly-khoan-thu";
    }

    /**
     * Show create page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        System.out.println("Truy cập vào trang tạo mới Khoan Thu.");

        // Tạo đối tượng khoanthuDto mới và thêm vào model
        KhoanthuDTO khoanthuDto = new KhoanthuDTO();
        model.addAttribute("khoanthuDto", khoanthuDto);
        System.out.println("Thêm đối tượng khoanthuDto vào model: " + khoanthuDto);


        return "form-qlkt";
    }

    /**
     * Create khoan thu string.
     *
     * @param khoanthuDto        the khoanthu dto
     * @param result             the result
     * @param model              the model
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @PostMapping("/create")
    public String createKhoanThu(
            @Valid @ModelAttribute("khoanthuDto") KhoanthuDTO khoanthuDto,
            BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {

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
        System.out.println("Thông tin Khoan Thu: " + khoanthu);

        repo.save(khoanthu);

        System.out.println("Khoan Thu đã được lưu vào cơ sở dữ liệu.");
        redirectAttributes.addFlashAttribute("createsuccess", "Tạo khoản thu thành công!");
        return "redirect:/quan-ly-khoan-thu";

    }

    /**
     * Show edit page string.
     *
     * @param model the model
     * @param id    the id
     * @return the string
     */
    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id
    ) {
        try {
            Khoanthu khoanthu = repo.findById(id);

            if (khoanthu == null) {
                return "redirect:/quan-ly-khoan-thu";
            }

            KhoanthuDTO khoanthuDto = new KhoanthuDTO();
            khoanthuDto.setTenkhoanthu(khoanthu.getTenkhoanthu());
            khoanthuDto.setSotien(khoanthu.getSotien());
            khoanthuDto.setBatbuoc(khoanthu.getBatbuoc());
            khoanthuDto.setHanchot(khoanthu.getHanchot());
            khoanthuDto.setNguoitao(khoanthu.getNguoitao());

            model.addAttribute("khoanthu_edit", khoanthuDto);
            model.addAttribute("id", id);
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
            Optional<Khoanthu> optionalKhoanthu = Optional.ofNullable(repo.findById(id));
            if (optionalKhoanthu.isEmpty()) {
                return "redirect:/quan-ly-khoan-thu";
            }

            Khoanthu khoanthu = optionalKhoanthu.get();


            if (result.hasErrors()) {
                model.addAttribute("id", id);
                model.addAttribute("khoanthu_edit", khoanthu_edit);
                return "edit-qlkt";
            }

            khoanthu.setTenkhoanthu(khoanthu_edit.getTenkhoanthu());
            khoanthu.setSotien(khoanthu_edit.getSotien());
            khoanthu.setBatbuoc(khoanthu_edit.getBatbuoc());
            khoanthu.setHanchot(khoanthu_edit.getHanchot());
            khoanthu.setNguoitao(khoanthu_edit.getNguoitao());
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
    public String deleteKhoanthu(
            @RequestParam int id,
            RedirectAttributes redirectAttributes
    ) {
        repo.deleteById(id);
        redirectAttributes.addFlashAttribute("deletesuccess", "Xóa khoản thu thành công!");
        return "redirect:/quan-ly-khoan-thu";
    }
}
