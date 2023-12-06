package com.example.controller;

import com.example.model.Pollutant;
import com.example.repository.PollutantRepository;
import com.example.repository.PollutionRepository;
import com.example.service.ExcelReader;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pollutant")
public class PollutantController {
    private final PollutantRepository pollutantRepository;
    private final PollutionRepository pollutionRepository;
    private final ExcelReader excelReader;

    @GetMapping
    public String show(Model model) {
        List<Pollutant> allPollutant = pollutantRepository.findAll();
        model.addAttribute("allPollutant", allPollutant);
        return "pollutant_index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("pollutant", new Pollutant());
        return "pollutant_add";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        if (file.getOriginalFilename().equals("")) {
            return "redirect:/pollutant";
        }

        excelReader.readPollutantData(file.getOriginalFilename());
        return "redirect:/pollutant";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Pollutant pollutant = pollutantRepository.findById(id).orElseThrow();
        pollutionRepository.deleteByPollutant(pollutant);
        pollutantRepository.deleteById(id);
        return "redirect:/pollutant";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("pollutant") @Valid Pollutant pollutant, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pollutant_add";
        }

        pollutantRepository.save(pollutant);
        return "redirect:/pollutant";
    }
}
