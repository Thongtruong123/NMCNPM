package SE_08.NMCNPM1.controller;
import SE_08.NMCNPM1.model.Nhankhau;
import SE_08.NMCNPM1.model.NhankhauDTO;
import SE_08.NMCNPM1.model.NhankhauId;
import SE_08.NMCNPM1.service.NhanKhauService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Nhan khau controller.
 */
@Controller
public class NhanKhauController {
    @Autowired
    private NhanKhauService repo;

    /**
     * Show nhan khau list string.
     *
     * @param model   the model
     * @param keyword the keyword
     * @param sort    the sort
     * @param page    the page
     * @return the string
     */
    @GetMapping("/thong-tin-nhan-khau")
    public String showNhanKhauList(Model model,
                                   @RequestParam(value = "keyword", required = false) String keyword,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "page", defaultValue = "0") int page) {

        // Tạo Pageable với thông tin phân trang và sắp xếp
        Pageable pageable = PageRequest.of(
                page,
                7,
                Sort.by(sort != null && !sort.isEmpty() ? sort : "id.roomNumber").ascending()
        );

        Page<Nhankhau> pageResult = repo.listAll(keyword, pageable);
        List<Nhankhau> nhankhauList = pageResult.getContent();

        
        // Gửi dữ liệu vào model
        model.addAttribute("nhankhau_count", nhankhauList.size());

        model.addAttribute("nhankhau_list", nhankhauList);
        model.addAttribute("nhankhau_totalPages", pageResult.getTotalPages());
        model.addAttribute("nhankhau_currentPage", page);
        model.addAttribute("nhankhau_keyword", keyword);
        model.addAttribute("nhankhau_sort", sort);

        return "thong-tin-nhan-khau";
    }

    /**
     * Show create nhan khau page string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("/thong-tin-nhan-khau/create")
    public String showCreateNhanKhauPage(Model model) {
        System.out.println("Truy cập vào trang thêm nhân khẩu.");
        NhankhauDTO nhankhauDto = new NhankhauDTO();
        model.addAttribute("nhankhauDto", nhankhauDto);
        System.out.println("Thêm đối tượng nhankhauDto vào model: " + nhankhauDto.getHoten());
        return "create_nhankhau";
    }

    /**
     * Create nhan khau string.
     *
     * @param nhankhauDto        the nhankhau dto
     * @param result             the result
     * @param model              the model
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @PostMapping("/thong-tin-nhan-khau/create")
    public String createNhanKhau(
            @Valid @ModelAttribute("nhankhauDto") NhankhauDTO nhankhauDto,
            BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Có lỗi trong việc nhập dữ liệu.");
            result.getAllErrors().forEach(error -> {
                System.out.println("Lỗi: " + error.getDefaultMessage());
            });
            model.addAttribute("nhankhauDto", nhankhauDto);
            return "create_nhankhau";
        }
        System.out.println("Không có lỗi trong việc nhập liệu, tiếp tục thêm nhân khẩu.");

        Nhankhau nhankhau = getNhankhau(nhankhauDto);

        System.out.println("Thông tin nhân khẩu: " + nhankhau);

        repo.save(nhankhau);

        System.out.println("Nhân khẩu mới đã được lưu vào cơ sở dữ liệu.");
        redirectAttributes.addFlashAttribute("createsuccess_nhankhau", "Tạo nhân khẩu mới thành công!");
        return "redirect:/thong-tin-nhan-khau";

    }

    private static Nhankhau getNhankhau(NhankhauDTO nhankhauDto) {
        Nhankhau nhankhau = new Nhankhau();
        NhankhauId nhankhauId = new NhankhauId(nhankhauDto.getRoomNumber(), nhankhauDto.getHoten());
        nhankhau.setId(nhankhauId);
        nhankhau.setGioitinh(nhankhauDto.getGioitinh());
        nhankhau.setSodienthoai(nhankhauDto.getSodienthoai());
        nhankhau.setVaitro(nhankhauDto.getVaitro());
        nhankhau.setNgaysinh(nhankhauDto.getNgaysinh());
        nhankhau.setQuequan(nhankhauDto.getQuequan());
        nhankhau.setThuongtru(nhankhauDto.getThuongtru());
        nhankhau.setTamtru(nhankhauDto.getTamtru());
        return nhankhau;
    }

    /**
     * Show edit nhan khau page string.
     *
     * @param model       the model
     * @param room_number the room number
     * @param hoten       the hoten
     * @return the string
     */
    @GetMapping("/thong-tin-nhan-khau/edit")
    public String showEditNhanKhauPage(
            Model model,
            @RequestParam String room_number,
            @RequestParam String hoten
    ) {
        try {
            Nhankhau nhankhau = repo.findByRoomnumberAndHoten(room_number, hoten);
            if (nhankhau == null) {
                return "redirect:/thong-tin-nhan-khau";
            }

            NhankhauDTO nhankhauDto = getNhankhauDTO(nhankhau);

            model.addAttribute("nhankhau_edit", nhankhauDto);
            model.addAttribute("room_number", room_number);
            model.addAttribute("hoten", hoten);
            return "edit_nhankhau";
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/thong-tin-nhan-khau";
        }
    }

    private static NhankhauDTO getNhankhauDTO(Nhankhau nhankhau) {
        NhankhauDTO nhankhauDto = new NhankhauDTO();
        nhankhauDto.setRoomNumber(nhankhau.getId().getRoomNumber());
        nhankhauDto.setHoten(nhankhau.getId().getHoten());
        nhankhauDto.setGioitinh(nhankhau.getGioitinh());
        nhankhauDto.setSodienthoai(nhankhau.getSodienthoai());
        nhankhauDto.setVaitro(nhankhau.getVaitro());
        nhankhauDto.setNgaysinh(nhankhau.getNgaysinh());
        nhankhauDto.setQuequan(nhankhau.getQuequan());
        nhankhauDto.setThuongtru(nhankhau.getThuongtru());
        nhankhauDto.setTamtru(nhankhau.getTamtru());
        return nhankhauDto;
    }

    /**
     * Update nhan khau string.
     *
     * @param model              the model
     * @param room_number        the room number
     * @param hoten              the hoten
     * @param nhankhauDto      the nhankhau edit
     * @param result             the result
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @PostMapping("/thong-tin-nhan-khau/edit")
    @Transactional
    public String updateNhanKhau(
            Model model,
            @RequestParam String room_number,
            @RequestParam String hoten,
            @Valid @ModelAttribute("nhankhauDto") NhankhauDTO nhankhauDto,
            BindingResult result, RedirectAttributes redirectAttributes) {

        // Xử lý dữ liệu đầu vào
        room_number = room_number.trim();
        hoten = hoten.trim();

        String[] parts = hoten.split(",");
        hoten = Arrays.stream(parts)
                .map(String::trim)
                .distinct()
                .collect(Collectors.joining(","));

        System.out.println("Room number: " + room_number);
        System.out.println("Ho ten: " + hoten);

        try {
            Optional<Nhankhau> optionalNhankhau = Optional.ofNullable(repo.findByRoomnumberAndHoten(room_number, hoten));
            if (optionalNhankhau.isEmpty()) {
                System.out.print("Không tìm thấy dữ liệu.");
                redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhân khẩu!");
                return "redirect:/thong-tin-nhan-khau";
            }

            Nhankhau nhankhau = optionalNhankhau.get();

            if (result.hasErrors()) {
                System.out.println("Có lỗi khi sửa.");
                result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
                model.addAttribute("room_number", room_number);
                model.addAttribute("hoten", hoten);
                return "edit_nhankhau";
            }


            nhankhau.setGioitinh(nhankhauDto.getGioitinh());
            nhankhau.setSodienthoai(nhankhauDto.getSodienthoai());
            nhankhau.setVaitro(nhankhauDto.getVaitro());
            nhankhau.setNgaysinh(nhankhauDto.getNgaysinh());
            nhankhau.setQuequan(nhankhauDto.getQuequan());
            nhankhau.setThuongtru(nhankhauDto.getThuongtru());
            nhankhau.setTamtru(nhankhauDto.getTamtru());

            System.out.println("Đang cập nhật nhân khẩu: " + nhankhau);
            repo.save(nhankhau);
            System.out.println("Nhân khẩu đã sửa và được lưu vào cơ sở dữ liệu.");
            redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
            return "redirect:/thong-tin-nhan-khau";

        } catch (Exception e) {
            System.out.println("Lỗi khi lưu: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Có lỗi khi cập nhật!");
            return "redirect:/thong-tin-nhan-khau";
        }
    }



    /**
     * Delete khoanthu string.
     *
     * @param room_number        the room number
     * @param hoten              the hoten
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @GetMapping("/thong-tin-nhan-khau/delete")
    public String deleteKhoanthu(@RequestParam String room_number,
                                 @RequestParam String hoten,
                                 RedirectAttributes redirectAttributes) {
        Optional<Nhankhau> nhankhau = Optional.ofNullable(repo.findByRoomnumberAndHoten(room_number, hoten));
        if (nhankhau.isPresent()) {
            repo.deleteByRoomNumberAndHoten(room_number, hoten);
            redirectAttributes.addFlashAttribute("deletesuccess_nhankhau", "Nhân khẩu '" + nhankhau.get().getId().getHoten() + "' đã được xóa!");
        } else {
            redirectAttributes.addFlashAttribute("deletesuccess_nhankhau", "Không tìm thấy nhân khẩu cần xóa!");
        }
        return "redirect:/thong-tin-nhan-khau";
    }
}
