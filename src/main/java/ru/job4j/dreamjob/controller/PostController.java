package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.SimpleCityService;
import ru.job4j.dreamjob.service.PostService;
import ru.job4j.dreamjob.util.UserSession;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
@Deprecated
@ThreadSafe
public class PostController {

    private final PostService postService;
    private final SimpleCityService simpleCityService;

    public PostController(PostService postService, SimpleCityService simpleCityService) {
        this.postService = postService;
        this.simpleCityService = simpleCityService;
    }

    @GetMapping("/posts")
    public String posts(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        model.addAttribute("post", new Post(0, "Название вакансии",
                "Описание вакансии", LocalDateTime.now(),
                new City(0, "Название города"), false));
        model.addAttribute("cities", simpleCityService.findAll());
        return "addPost";
    }

    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post,
                             @RequestParam(value = "visible", defaultValue = "false") boolean visible) {
        post.setCity(simpleCityService.findById(post.getCity().getId()));
        post.setVisible(visible);
        postService.addPost(post);
        return "redirect:/posts";
    }

    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("cities", simpleCityService.findAll());
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public String updatePost(@ModelAttribute Post post,
                             @RequestParam(value = "visible", defaultValue = "false") boolean visible) {
        post.setCity(simpleCityService.findById(post.getCity().getId()));
        post.setVisible(visible);
        postService.update(post);
        return "redirect:/posts";
    }
}
