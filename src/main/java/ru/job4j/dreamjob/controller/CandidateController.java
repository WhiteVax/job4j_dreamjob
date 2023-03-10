package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.dto.FileDto;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.SimpleCandidateService;
import ru.job4j.dreamjob.service.SimpleCityService;
import ru.job4j.dreamjob.util.UserSession;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/candidates")
@ThreadSafe
public class CandidateController {

    private final CandidateService candidateService;
    private final CityService cityService;

    public CandidateController(CandidateService simpleCandidateService, CityService simpleCityService) {
        this.candidateService = simpleCandidateService;
        this.cityService = simpleCityService;
    }

    @GetMapping
    public String candidates(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates/list";
    }

    @GetMapping("/create")
    public String add(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        model.addAttribute("cities", cityService.findAll());
        return "candidates/create";
    }

    @PostMapping("/create")
    public String createCandidate(@ModelAttribute Candidate candidate, Model model,
                                  @RequestParam MultipartFile file) {
        try {
            candidateService.save(candidate, new FileDto(file.getOriginalFilename(), file.getBytes()));
            return "redirect:/candidates";
        } catch (Exception exception) {
            model.addAttribute("message", exception.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{candidateId}")
    public String formUpdate(Model model, @PathVariable("candidateId") int id, HttpSession session) {
        var candidate = candidateService.findById(id);
        model.addAttribute("user", UserSession.session(session));
        if (candidate.isEmpty()) {
            model.addAttribute("message", String.format("Кандидат с id = %s не найден.", id));
            return "errors/404";
        }
        model.addAttribute("cities", cityService.findAll());
        model.addAttribute("candidate", candidate.get());
        return "candidates/one";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Candidate candidate, Model model,
                                  @RequestParam("file") MultipartFile file) throws IOException {
        if (!candidateService.update(candidate, new FileDto(file.getOriginalFilename(), file.getBytes()))) {
            model.addAttribute("message", "Ошибка при обновлении кандидата.");
            return "errors/404";
        }
        return "redirect:/candidates";
    }

    @GetMapping("/delete/{candidateId}")
    public String delete(Model model, @PathVariable("candidateId") int id, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        if (!candidateService.deleteById(id)) {
            model.addAttribute("message", String.format("Кандидат с id = %s не найден.", id));
            return "errors/404";
        }
        return "redirect:/candidates";
    }
}
