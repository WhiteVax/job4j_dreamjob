package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;
import ru.job4j.dreamjob.util.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@ThreadSafe
public class UserControl {

    private final UserService userService;

    public UserControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formAddUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.addUser(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        return "successReg";
    }

    @GetMapping("/fail")
    public String fail(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        return "failReg";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        var session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }
}
