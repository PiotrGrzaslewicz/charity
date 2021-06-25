package pl.coderslab.charity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

@Controller
public class HomeController {

    private InstitutionService institutionService;
    private DonationService donationService;


    public HomeController(InstitutionService institutionService, DonationService donationService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
    }

    @RequestMapping("/")
    public String indexAction(Model model){

        model.addAttribute("institutions", institutionService.findAll());
        model.addAttribute("totalQuantity", donationService.getTotalQuantity());
        model.addAttribute("donationCount", donationService.getCount());
        return "index";
    }

}
