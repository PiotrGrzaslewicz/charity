package pl.coderslab.charity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.charity.model.CurrentUser;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.DonationStatus;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.DonationStatusService;
import pl.coderslab.charity.service.InstitutionService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DonationController {

    private CategoryService categoryService;
    private InstitutionService institutionService;
    private DonationService donationService;
    private DonationStatusService donationStatusService;


    public DonationController(CategoryService categoryService, InstitutionService institutionService, DonationService donationService, DonationStatusService donationStatusService) {
        this.categoryService = categoryService;
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.donationStatusService = donationStatusService;

    }

    @GetMapping("/donation")
    public String donateForm(Model model, @AuthenticationPrincipal CurrentUser currentUser) {

        if (currentUser.getUser().getEnabled() == 0) {
            return "userblocked";
        } else {
            model.addAttribute("allCategories", categoryService.findAll());
            model.addAttribute("institutions", institutionService.findAll());
            model.addAttribute("donation", new Donation());
            return "form";
        }
    }

    @PostMapping("/donation")
    public String createDonation(Model model, @RequestParam(required = false) List<Long> categories,
                                 @Valid Donation donation, BindingResult result, @AuthenticationPrincipal CurrentUser currentUser) {
        if (result.hasErrors()) {
            model.addAttribute("allCategories", categoryService.findAll());
            model.addAttribute("institutions", institutionService.findAll());
            if (categories == null) {
                model.addAttribute("categoryMessage", "Musisz wybrać co najmniej jedną kategorię");
            }
            model.addAttribute("donation", donation);
            return "form";
        } else if (categories == null) {
            model.addAttribute("categoryMessage", "Musisz wybrać co najmniej jedną kategorię");
            return "form";
        }
        donation.setUser(currentUser.getUser());
        DonationStatus donationStatus = new DonationStatus();
        donationStatus.setStatus(0);
        donation.setStatus(donationStatus);
        donationStatusService.save(donationStatus);
        donationService.save(donation);
        return "form-confirmation";
    }

}
