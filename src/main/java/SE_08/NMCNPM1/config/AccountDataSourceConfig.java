//@GetMapping("/form/{id}")
//public String showForm(@PathVariable("id") Optional<Long> id, Model model) {
//    khoanthu k;
//    if (id.isPresent()) {
//        k = userService.findById(id.get());
//    } else {
//        k = new khoanthu();
//    }
//    model.addAttribute("khoanthu", k);
//    return "form-qlkt";
//}