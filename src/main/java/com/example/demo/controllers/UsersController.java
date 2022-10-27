package com.example.demo.controllers;


import com.example.demo.models.Cities;
import com.example.demo.models.User;
import com.example.demo.models.Work;
import com.example.demo.repo.CitiesRepository;
import com.example.demo.repo.UserRepository;
import com.example.demo.repo.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class UsersController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CitiesRepository citiesRepository;

    @Autowired
    WorkRepository workRepository;

    @GetMapping("/user")
    public String userMain(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user/user-main";
    }

    @GetMapping("/user/add")
    public String userAdd(User user, Model model) {
        return getString(model);
    }

    private String getString(Model model) {
        Iterable<User> users = userRepository.findAll();
        Iterable<Cities> cities = citiesRepository.findAll();
        Iterable<Work> works = workRepository.findAll();
        model.addAttribute("works", works);
        model.addAttribute("users", users);
        model.addAttribute("cities", cities);
        return "user/user-add";
    }

    @PostMapping("/user/add")
    public String userPostAdd(@ModelAttribute("user") @Valid User user, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getString(model);
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/user/filter")
    public String userFilter(Model model) {
        return "user/user-filter";
    }

    @PostMapping("/user/filter/result")
    public String userResult(@RequestParam String title, Model model) {
        List<User> result = userRepository.findByUsernameContains(title);
        model.addAttribute("result", result);
        return "user/user-filter";
    }

    @GetMapping("/user/{id}")
    public String userDetail(@PathVariable(value = "id") long id, Model model) {
        if (userDetails(id, model)) {
            return "redirect:/user";
        }
        return "user/user-details";
    }

    @GetMapping("/user/{user}/edit")
    public String userEdit(User user, Model model) {
        Iterable<Cities> cities = citiesRepository.findAll();
        model.addAttribute("cities", cities);
        return "user/user-edit";
    }

    //    @PostMapping("/user/{id}/edit")
//    public String userPostUpdate(@PathVariable(value = "id") long id,
//                                 @RequestParam String username,
//                                 @RequestParam Date birthdate,
//                                 @RequestParam Boolean gender,
//                                 @RequestParam int height,
//                                 @RequestParam double salary, Model model) {
//        User user = userRepository.findById(id).orElseThrow();
//        user.setUsername(username);
//        user.setBirthdate(birthdate);
//        user.setGender(gender);
//        user.setHeight(height);
//        user.setSalary(salary);
//        userRepository.save(user);
//        return "redirect:/user";
//    }
    @PostMapping("/user/{id}/edit")
    public String userPostUpdate(@ModelAttribute("user") @Valid User user, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Iterable<Cities> cities = citiesRepository.findAll();
            model.addAttribute("cities", cities);
            return "user/user-edit";
        }
        userRepository.save(user);
        return "redirect:/user";
    }

    @PostMapping("/user/{id}/remove")
    public String userPostDelete(@PathVariable(value = "id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/user";
    }

    public boolean userDetails(@PathVariable(value = "id") long id, Model model) {
        if (!userRepository.existsById(id)) {
            return true;
        }
        User user = userRepository.findById(id).orElseThrow();
        model.addAttribute("user", user);
        return false;
    }
}