package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;
import ru.job4j.dreamjob.util.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
@RequestMapping("/users")
public class UserControl {

    private final UserService userService;

    public UserControl(UserService simpleUserService) {
        this.userService = simpleUserService;
    }

    @GetMapping("/registration")
    public String addUser(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        return "users/reg";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user, HttpSession session) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("user", UserSession.session(session));
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "errors/404";
        }
        return "redirect:/success";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        var session = request.getSession();
        if (userOptional.isEmpty()) {
            model.addAttribute("user", UserSession.session(session));
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        session.setAttribute("user", userOptional.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

//    @GetMapping("/success")
//    public String success(Model model, HttpSession session) {
//        model.addAttribute("user", UserSession.session(session));
//        return "successReg";
//    }

//    @GetMapping("/fail")
//    public String fail(Model model, HttpSession session) {
//        model.addAttribute("user", UserSession.session(session));
//        return "failReg";
//    }

//    @GetMapping("/loginPage")
//    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
//        model.addAttribute("fail", fail != null);
//        return "login";
//    }

//    @PostMapping("/login")
//    public String login(@ModelAttribute User user, HttpServletRequest req) {
//        Optional<User> userDb = simpleUserService.findUserByEmailAndPassword(
//                user.getEmail(), user.getPassword()
//        );
//        if (userDb.isEmpty()) {
//            return "redirect:/loginPage?fail=true";
//        }
//        var session = req.getSession();
//        session.setAttribute("user", userDb.get());
//        return "redirect:/index";
//    }
}
