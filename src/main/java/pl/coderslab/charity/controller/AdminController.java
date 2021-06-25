package pl.coderslab.charity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.model.CurrentUser;
import pl.coderslab.charity.model.Donation;
import pl.coderslab.charity.model.User;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.UserService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final DonationService donationService;

    public AdminController(UserService userService, DonationService donationService) {
        this.userService = userService;
        this.donationService = donationService;
    }

    @RequestMapping("/admin/admins")
    public String admins(Model model) {
        model.addAttribute("admins", userService.findAdmins());
        return "admins";
    }

    @GetMapping("/admin/deleteadmin/{id}")
    public String deleteAdmin(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getId().equals(id)) {
            return "deleteadmin-warning";
        } else {
            model.addAttribute("admin", userService.findById(id));
            return "deleteadmin-confirm";
        }
    }

    @PostMapping("/admin/deleteadmin-confirm")
    public String confirmDeleteAdmin(@RequestParam Long id) {
        userService.deleteById(id);
        return "redirect:/admin/admins";
    }

    @GetMapping("/admin/addadmin")
    public String addAdmin() {
        return "addadmin";
    }

    @PostMapping("/admin/addadmin")
    public String createInstitution(@RequestParam String name, @RequestParam String surname,
                                    @RequestParam String userName, @RequestParam String password,
                                    @RequestParam String repassword, Model model) {
        if ((name.isBlank()) || (surname.isBlank()) || (userName.isBlank()) || (password.isBlank()) || (repassword.isBlank())) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            return "addadmin";
        } else if (!password.equals(repassword)) {
            model.addAttribute("msg", "Hasła nie są identyczne");
            return "addadmin";
        } else if (!userService.checkUserName(userName)) {
            model.addAttribute("msg", "Użytkownik o podanym adresie juz istnieje");
            return "/addadmin";
        } else {
            userService.createAdmin(userName, name, surname, password);
            return "redirect:/admin/admins";
        }
    }

    @GetMapping("/admin/editadmin/{id}")
    public String editAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("admin", userService.findById(id));
        return "editadmin";
    }

    @PostMapping("/admin/editadmin")
    public String updateAdmin(@RequestParam String userName, @RequestParam String name,
                              @RequestParam String surname, @RequestParam Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if ((name.isBlank()) || (surname.isBlank()) || (userName.isBlank())) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            model.addAttribute("admin", userService.findById(id));

            return "editadmin";
        } else if (!userService.checkUserName(userName) && (!userName.equals(currentUser.getUser().getUsername()))) {
            model.addAttribute("msg", "Użytkownik o podanym adresie juz istnieje");
            model.addAttribute("admin", userService.findById(id));
            return "/editadmin";
        } else {
            User user = userService.findById(id);
            user.setUsername(userName);
            user.setName(name);
            user.setSurname(surname);
            userService.updateUser(user);
            return "redirect:/admin/admins";
        }
    }

    @GetMapping("/admin/changepassword/{id}")
    public String changePassword(@PathVariable Long id, Model model) {
        model.addAttribute("admin", userService.findById(id));
        return "changepassword";
    }

    @PostMapping("/admin/changepassword")
    public String updatePassword(@RequestParam String password, @RequestParam String newpassword,
                                 @RequestParam String renewpassword, @RequestParam Long id, Model model) {
        if ((password.isBlank()) || (newpassword.isBlank()) || renewpassword.isBlank()) {
            model.addAttribute("msg", "Wypełnij wszystkie pola");
            model.addAttribute("admin", userService.findById(id));
            return "changepassword";
        } else if (!userService.checkPassword(userService.findById(id), password)) {
            model.addAttribute("msg", "Niepoprawne hasło");
            model.addAttribute("admin", userService.findById(id));
            return "changepassword";
        } else if (!newpassword.equals(renewpassword)) {
            model.addAttribute("msg", "Podane hasła nie są identyczne");
            model.addAttribute("admin", userService.findById(id));
            return "changepassword";
        } else {
            userService.updatePassword(userService.findById(id), newpassword);
            return "redirect:/admin/admins";
        }
    }

    @RequestMapping("/admin/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "users";
    }

    @RequestMapping("/admin/disableuser/{id}")
    public String disableUser(@PathVariable Long id, @AuthenticationPrincipal CurrentUser currentUser) {
        if (id.equals(currentUser.getUser().getId())) {
            return "disableuser-warning";
        } else {
            userService.disableUser(userService.findById(id));
            return "redirect:/admin/users";
        }
    }

    @RequestMapping("/admin/enableuser/{id}")
    public String enableUser(@PathVariable Long id) {
        userService.enableUser(userService.findById(id));
        return "redirect:/admin/users";
    }

    @RequestMapping("/admin/deleteuser/{id}")
    public String deleteUser(@PathVariable Long id, Model model, @AuthenticationPrincipal CurrentUser currentUser) {
        if (currentUser.getUser().getId().equals(id)) {
            return "deleteuser-warning";
        } else {
            model.addAttribute("user", userService.findById(id));
            return "deleteuser-confirm";
        }
    }

    @PostMapping("/admin/deleteuser-confirm")
    public String confirmDeleteUser(@RequestParam Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/donations")
    public String donationsByUser(Model model) {

            List<Donation> donations = donationService.findAll();
            Collections.sort(donations, Comparator.comparing(Donation::getStatus).reversed()
                    .thenComparing(Donation::getUser)
                    .thenComparing(Donation::getCreated));
            model.addAttribute("donations", donations);
            return "admindonations";

    }

    @GetMapping("/admin/deletedonation/{id}")
    public String deleteDonation(@PathVariable Long id, Model model) {
        model.addAttribute("donation", donationService.findById(id));
        return "deletedonation-confirm";
    }

    @PostMapping("/admin/deletedonation-confirm")
    public String deleteDonationConfirm(@RequestParam Long id) {
        donationService.deleteById(id);
        return "redirect:/user/donations";
    }

}