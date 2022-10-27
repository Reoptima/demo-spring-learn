package com.example.demo.controllers;

import com.example.demo.models.Cities;
import com.example.demo.models.Work;
import com.example.demo.repo.CitiesRepository;
import com.example.demo.repo.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/work")
@PreAuthorize("hasAnyAuthority('USER')")
public class WorkController {
    @Autowired
    private WorkRepository workRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @GetMapping()
    public String WorkMain(Model model) {
        Iterable<Work> works = workRepository.findAll();
        model.addAttribute("cities", citiesRepository.findAll());
        model.addAttribute("works", works);
        return "work/work-main";
    }

    @GetMapping("/add")
    public String WorkAdd(Work work, Model model) {
        Iterable<Cities> cities = citiesRepository.findAll();
        model.addAttribute("cities", citiesRepository.findAll());
        return "work/work-add";
    }

    @PostMapping("/add")
    public String cityPostAdd(
            @ModelAttribute("work") @Valid Work work,
            Model model,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Iterable<Cities> cities = citiesRepository.findAll();
            model.addAttribute("cities", cities);
            return "work/work-add";
        }
        workRepository.save(work);
        return "redirect:/work";
    }

    @GetMapping("/show/{id}")
    public String cityShow(Work work) {
        return "work/work-details";
    }

    @GetMapping("/del/{id}")
    public String cityDel(Work work) {
        workRepository.delete(work);
        return "redirect:/work";
    }
}
