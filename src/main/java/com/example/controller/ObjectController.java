package com.example.controller;

import com.example.model.Object;
import com.example.model.Pollution;
import com.example.repository.ObjectRepository;
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
@RequestMapping("/object")
public class ObjectController {
    private final ObjectRepository objectRepository;
    private final PollutionRepository pollutionRepository;
    private final ExcelReader excelReader;

    @GetMapping
    public String show(Model model) {
        List<Object> allObject = objectRepository.findAll();
        model.addAttribute("allObject", allObject);
        return "object_index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("object", new Object());
        return "object_add";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        if (file.getOriginalFilename().equals("")) {
            return "redirect:/object";
        }
        excelReader.readObjectData(file.getOriginalFilename());
        return "redirect:/object";
    }

    @Transactional
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        Object object = objectRepository.findById(id).orElseThrow();
        pollutionRepository.deleteByObject(object);
        objectRepository.deleteById(id);
        return "redirect:/object";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("object") @Valid Object object, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "object_add";
        }

        objectRepository.save(object);
        return "redirect:/object";
    }
}
