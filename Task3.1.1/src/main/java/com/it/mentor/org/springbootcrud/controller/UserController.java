package com.it.mentor.org.springbootcrud.controller;

import com.it.mentor.org.springbootcrud.model.*;
import com.it.mentor.org.springbootcrud.service.*;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "pages/users";
    }

    @GetMapping("delete")
    public String deleteUserById(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("new")
    public String newUser(@ModelAttribute("user") User user) {
        return "pages/new";
    }

    @PostMapping("save")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/new";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("edit")
    public String editUser(@RequestParam("id") Long id, Model model) {
        User user = userService.getOne(id);
        model.addAttribute("user", user);
        return "pages/edit";
    }

    @PostMapping(value = "update")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pages/edit";
        }
        userService.update(user);
        return "redirect:/users";
    }

    @GetMapping("search")
    public ModelAndView searchUser(@RequestParam("keyword") String keyword) {
        List<User> result = userService.search(keyword);
        ModelAndView modelAndView = new ModelAndView("pages/search");
        modelAndView.addObject("result", result);
        return modelAndView;
    }
}