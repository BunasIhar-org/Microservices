package com.it.mentor.org.springbootsecuritydemo.controllers;

import com.it.mentor.org.springbootsecuritydemo.models.*;
import com.it.mentor.org.springbootsecuritydemo.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import javax.validation.*;
import java.util.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/people")
    public String people(Model model) {
        model.addAttribute("people", adminService.findAllPeople());
        return "admin/people";
    }

    @GetMapping("/edit")
    public String editPerson(@RequestParam("id") Integer id, Model model) {
        Person person = adminService.findPersonById(id);
        model.addAttribute("person", person);
        model.addAttribute("allRoles", Role.values());
        return "admin/edit";
    }

    @PostMapping("/update")
    public String updateUser(Model model, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", Role.values());
            return "admin/edit";
        }
        adminService.updatePerson(person);
        return "redirect:/admin/people";
    }

    @GetMapping("/delete")
    public String deletePerson(Integer id) {
        adminService.deletePerson(id);
        return "redirect:/admin/people";
    }
}
