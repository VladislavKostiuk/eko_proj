package com.example.controller;

import com.example.dto.PollutionDTO;
import com.example.model.Object;
import com.example.model.Pollutant;
import com.example.model.Pollution;
import com.example.repository.ObjectRepository;
import com.example.repository.PollutantRepository;
import com.example.repository.PollutionRepository;
import com.example.service.ExcelReader;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PollutionController {
    private final PollutionRepository pollutionRepository;
    private final ObjectRepository objectRepository;
    private final PollutantRepository pollutantRepository;
    private final ExcelReader excelReader;

    @GetMapping()
    public String show(Model model) {
        List<Pollution> allPollution = pollutionRepository.findAll();
        model.addAttribute("allPollution", allPollution);
        return "pollution_index";
    }

    @GetMapping("/pollution/add")
    public String add(Model model) {
        model.addAttribute("pollutionDTO", new PollutionDTO());
        model.addAttribute("allObject", objectRepository.findAll());
        model.addAttribute("allPollutant", pollutantRepository.findAll());
        return "pollution_add";
    }

    @PostMapping("/pollution/uploadFile")
    public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
        if (file.getOriginalFilename().equals("")) {
            return "redirect:/";
        }

        excelReader.readPollutionData(file.getOriginalFilename());
        return "redirect:/";
    }

    @PostMapping("/pollution/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        pollutionRepository.deleteById(id);
        return "redirect:/";
    }
    @PostMapping("/pollution/add")
    public String add(@ModelAttribute("pollutionDTO") @Valid PollutionDTO pollutionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/pollution/add";
        }

        Pollution pollution = new Pollution();
        Pollutant pollutant = pollutantRepository.findByName(pollutionDTO.getPollutantName()).orElseThrow();
        Object object = objectRepository.findByName(pollutionDTO.getObjectName()).orElseThrow();
        pollution.setObject(object);
        pollution.setPollutant(pollutant);
        pollution.setValue(pollutionDTO.getValue());
        pollution.setYear(pollutionDTO.getYear());
        pollution.setConcentration(pollutionDTO.getConcentration());
        pollution.setCr(pollutionDTO.getCr());
        pollution.setHq(pollutionDTO.getHq());
        pollution.setAddLadd(pollutionDTO.getAddLadd());
        pollution.setCompensation(pollutionDTO.getCompensation());
        pollutionRepository.save(pollution);
        return "redirect:/";
    }
}
