package com.example.controller;

import com.example.model.Object;
import com.example.model.Tax;
import com.example.repository.TaxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tax")
public class TaxController {
    private final TaxRepository taxRepository;

    @GetMapping
    public String show(Model model) {
        List<Tax> allTaxes = taxRepository.findAll();
        model.addAttribute("allTaxes", allTaxes);
        return "tax_index";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        taxRepository.deleteById(id);
        return "redirect:/tax";
    }
}
