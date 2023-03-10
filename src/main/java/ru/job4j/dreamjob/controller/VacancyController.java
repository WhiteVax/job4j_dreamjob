package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.SimpleCityService;
import ru.job4j.dreamjob.service.VacancyService;
import ru.job4j.dreamjob.util.UserSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@ThreadSafe
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;
    private final CityService cityService;

    public VacancyController(VacancyService simpleVacancyService, CityService simpleCityService) {
        this.vacancyService = simpleVacancyService;
        this.cityService = simpleCityService;
    }

    @GetMapping
    public String vacancies(Model model, HttpSession session) {
        model.addAttribute("vacancies", vacancyService.findAll());
        return "vacancies/list";
    }

    @GetMapping("/create")
    public String add(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        model.addAttribute("cities", cityService.findAll());
        return "vacancies/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Vacancy vacancy, @RequestParam MultipartFile file, Model model) {
        try {
            vacancyService.save(vacancy, new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/vacancies";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{postId}")
    public String formUpdate(Model model, @PathVariable("postId") int id, HttpSession session) {
        var vacancy = vacancyService.findById(id);
        model.addAttribute("user", UserSession.session(session));
        if (vacancy.isEmpty()) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена.");
            return "errors/404";
        }
        model.addAttribute("vacancy", vacancyService.findById(id).get());
        model.addAttribute("cities", cityService.findAll());
        return "vacancies/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Vacancy vacancy, Model model, @RequestParam MultipartFile file,
                         HttpSession session) throws IOException {
        model.addAttribute("user", UserSession.session(session));
        if (!vacancyService.update(vacancy, new FileDto(file.getOriginalFilename(), file.getBytes()))) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }

    @GetMapping("/delete/{postId}")
    public String delete(Model model, @PathVariable("postId") int id, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        if (!vacancyService.deleteById(id)) {
            model.addAttribute("message", "Вакансия с указанным идентификатором не найдена.");
            return "errors/404";
        }
        return "redirect:/vacancies";
    }
}

