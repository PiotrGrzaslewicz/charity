package pl.coderslab.charity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.model.CurrentUser;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.DonationStatusService;
import pl.coderslab.charity.service.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final DonationService donationService;
    private final DonationStatusService donationStatusService;

    public UserController(UserService userService, DonationService donationService, DonationStatusService donationStatusService) {
        this.userService = userService;
        this.donationService = donationService;
        this.donationStatusService = donationStatusService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "/register";
    }

    @PostMapping("/register")
    public String createUser(@RequestParam String repassword, @RequestParam String password,
                             @RequestParam String userName, @RequestParam String name,
                             @RequestParam String surname, Model model) {
        if (!(password.equals(repassword))) {
            model.addAttribute("msg", "Hasła nie są identyczne");
            return "/register";
        } else if (!userService.checkUserName(userName)) {
            model.addAttribute("msg", "Użytkownik o podanym adresie juz istnieje");
            return "/register";
        } else if ((repassword.isBlank()) || (password.isBlank()) || (name.isBlank()) || (surname.isBlank()) || (userName.isBlank())) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            return "/register";
        } else {

            User user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            user.setName(name);
            user.setSurname(surname);
            userService.createUser(user);

            return "redirect:/login";
        }

    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String validLogin(Model model, @RequestParam String username, @RequestParam String password) {

        User user = userService.findByUserName(username);
        if (user == null) {
            model.addAttribute("msg", "Niepoprawne dane");
            return "/login";
        } else if (!userService.checkPassword(user, password)) {
            model.addAttribute("msg", "Niepoprawne dane");
            return "/login";
        } else {


            return "redirect:/";
        }
    }

    @GetMapping("/user/edit")
    public String editUser(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getEnabled() == 0) {
            return "userblocked";
        } else {
            model.addAttribute("user", userService.findById(currentUser.getUser().getId()));
            return "edituser";
        }
    }

    @PostMapping("/user/edit")
    public String updateUser(@RequestParam String userName, @RequestParam String name,
                             @RequestParam String surname, @RequestParam Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if ((name.isBlank()) || (surname.isBlank()) || (userName.isBlank())) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            model.addAttribute("user", userService.findById(id));

            return "edituser";
        } else if (!userService.checkUserName(userName) && (!userName.equals(currentUser.getUser().getUsername()))) {
            model.addAttribute("msg", "Użytkownik o podanym adresie juz istnieje");
            model.addAttribute("user", userService.findById(id));
            return "/edituser";
        } else {
            User user = userService.findById(id);
            user.setUsername(userName);
            user.setName(name);
            user.setSurname(surname);
            userService.updateUser(user);
            return "redirect:/";
        }
    }

    @GetMapping("/user/changepassword/{id}")
    public String changePassword(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getEnabled() == 0) {
            return "userblocked";
        } else {
            model.addAttribute("user", userService.findById(id));
            return "userchangepassword";
        }
    }

    @PostMapping("/user/changepassword")
    public String updatePassword(@RequestParam String password, @RequestParam String newpassword,
                                 @RequestParam String renewpassword, @RequestParam Long id, Model model) {
        if ((password.isBlank()) || (newpassword.isBlank()) || renewpassword.isBlank()) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            model.addAttribute("user", userService.findById(id));
            return "userchangepassword";
        } else if (!userService.checkPassword(userService.findById(id), password)) {
            model.addAttribute("msg", "Niepoprawne hasło");
            model.addAttribute("user", userService.findById(id));
            return "userchangepassword";
        } else if (!newpassword.equals(renewpassword)) {
            model.addAttribute("msg", "Podane hasła nie są identyczne");
            model.addAttribute("user", userService.findById(id));
            return "userchangepassword";
        } else {
            userService.updatePassword(userService.findById(id), newpassword);
            return "redirect:/";
        }
    }

    @GetMapping("/user/donations")
    public String donationsByUser(Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getEnabled() == 0) {
            return "userblocked";
        } else {
            List<Donation> donations = donationService.getAllDonationsByUser(currentUser.getUser());
            Collections.sort(donations, Comparator.comparing(Donation::getStatus).reversed()
            .thenComparing(Donation::getCreated));
            model.addAttribute("donations", donations);
            return "userdonations";
        }
    }

    @GetMapping("/user/changedonationstatus/{id}")
    public String changeDonationStatus(@PathVariable Long id) {
        donationStatusService.changeStatus(id);
        return "redirect:/user/donations";
    }

    @GetMapping("/user/deletedonation/{id}")
    public String deleteDonation(@PathVariable Long id, Model model) {
        model.addAttribute("donation", donationService.findById(id));
        return "deletedonation-confirm";
    }

    @PostMapping("/user/deletedonation-confirm")
    public String deleteDonationConfirm(@RequestParam Long id) {
        donationService.deleteById(id);
        return "redirect:/user/donations";
    }

    @GetMapping("/user/donationdetails/{id}")
    public String donationDetails(@PathVariable Long id, Model model) {
        model.addAttribute("donation", donationService.findById(id));
        return "donationdetails";
    }
}