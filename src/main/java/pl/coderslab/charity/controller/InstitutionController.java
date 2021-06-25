package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.service.InstitutionService;

@Controller
public class InstitutionController {

    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @RequestMapping("/admin/institutions")
    public String institutions(Model model) {
        model.addAttribute("institutions", institutionService.findAll());
        return "institutions";
    }

    @GetMapping("/admin/addinstitution")
    public String addInstitution() {
        return "addinstitution";
    }

    @PostMapping("/admin/addinstitution")
    public String createInstitution(@RequestParam String name, @RequestParam String description, Model model) {
        if ((name.isBlank()) || (description.isBlank())) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            return "addinstitution";
        } else {
            institutionService.createInstitution(name, description);
            return "redirect:/admin/institutions";
        }
    }

    @GetMapping("/admin/editinstitution/{id}")
    public String editInstitution(@PathVariable Long id, Model model) {
        model.addAttribute("institution", institutionService.findById(id));
        return "editinstitution";
    }

    @PostMapping("/admin/editinstitution")
    public String editInstitution(@RequestParam String name, @RequestParam String description, @RequestParam Long id, Model model) {

        if ((name == null) || (description == null)) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            model.addAttribute("institution", institutionService.findById(id));
            return "addinstitution";
        } else {

            institutionService.updateInstitution(id, name, description);
            return "redirect:institutions";
        }
    }

    @GetMapping("/admin/deleteinstitution/{id}")
    public String deleteInstitution(@PathVariable Long id, Model model) {
        model.addAttribute("institution", institutionService.findById(id));
        return "deleteinstitution-confirm";
    }

    @PostMapping("/admin/deleteinstitution-confirm")
    public String confirmDeleteInstitution(@RequestParam Long id) {
        institutionService.deleteById(id);
        return "redirect:/admin/institutions";
    }
}
