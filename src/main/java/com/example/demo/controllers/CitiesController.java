package com.example.demo.controllers;

import com.example.demo.models.Cities;
import com.example.demo.models.Work;
import com.example.demo.repo.CitiesRepository;
import com.example.demo.repo.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CitiesController {
    @Autowired
    CitiesRepository citiesRepository;

    @Autowired
    WorkRepository workRepository;

    @GetMapping("/city")
    public String citiesMain(Model model) {
        Iterable<Cities> cities = citiesRepository.findAll();
        model.addAttribute("cities", cities);
        return "city/city-main";
    }

    @GetMapping("/city/add")
    public String citiesAdd(Cities cities, Model model) {
        Iterable<Work> works = workRepository.findAll();
        model.addAttribute("work", works);
        return "city/city-add";
    }

    @PostMapping("/city/add")
    public String citiesPostAdd(@ModelAttribute("cities") @Valid Cities cities, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "city/city-add";
        }
        citiesRepository.save(cities);
        return "redirect:/city";
    }

    @GetMapping("/city/filter")
    public String citiesFilter() {
        return "city/city-filter";
    }

    @GetMapping("/city/filterStrong")
    public String citiesFilterStrong() {
        return "city/city-filter";
    }


    @PostMapping("/city/filter/result")
    public String citiesResult(@RequestParam String title, Model model) {
        List<Cities> result = citiesRepository.findBynameContains(title);
        model.addAttribute("result", result);
        return "city/city-filter";
    }

    @PostMapping("/city/filterStrong/result")
    public String citiesResultStrong(@RequestParam String title, Model model) {
        List<Cities> result = citiesRepository.findByname(title);
        model.addAttribute("result", result);
        return "city/city-filter";
    }

    @GetMapping("/city/{id}")
    public String citiesView(@PathVariable(value = "id") long id, Model model) {
        if (citiesDetails(id, model)) return "redirect:/city";
        return "city/city-details";
    }

    @GetMapping("/city/{id}/edit")
    public String citiesEdit(@PathVariable(value = "id") long id, Model model) {
        if (citiesDetails(id, model)) return "redirect:/city";
        return "city/city-edit";
    }

    @PostMapping("/city/{id}/edit")
    public String citiesPostUpdate(@ModelAttribute("cities") @Valid Cities cities, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "city/city-edit";
        }
        citiesRepository.save(cities);
        return "redirect:/city";
    }

    @PostMapping("/city/{id}/remove")
    public String citiesPostDelete(@PathVariable(value = "id") long id) {
        Cities cities = citiesRepository.findById(id).orElseThrow();
        citiesRepository.delete(cities);
        return "redirect:/city";
    }

    private boolean citiesDetails(@PathVariable(value = "id") long id, Model model) {
        if (!citiesRepository.existsById(id)) {
            return true;
        }
        Cities cities = citiesRepository.findById(id).orElseThrow();
//        Work works = workRepository.findById(id).orElseThrow();
        model.addAttribute("cities", cities);
//        model.addAttribute("work", works);
        return false;
    }

}
