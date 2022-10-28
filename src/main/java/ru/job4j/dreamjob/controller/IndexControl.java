package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.util.UserSession;

import javax.servlet.http.HttpSession;

/**
 * Слой - это классы имеющую одну функциональную принадлежность.
 * Слой контроллеры - это классы для работы с клиентом. Эти классы принимают запросы и отдают ответы от клиента.
 * Клиентом может быть: браузер, мобильное приложение, или другое приложение на Java. Все они будут работать через контроллеры.
 * Слой сервисы - это классы выполняющую бизнес логику приложения. В слое сервисов используется только бизнес логика.
 * Здесь не должно быть работы с базой данных или c HTML. На качественно написанный слой сервисов легко написать модульные тесты,
 * потому что у нас нет привязки к внешним ресурсам.
 * Слой персистенции - это классы для работы с базами данных.
 * В Java приложении для организации слоев используются пакеты.
 * model - классы, описывающее модели данных. Например, пользователи, категории, вакансии. По сути - это структуры данных.
 * persistence - классы для работы с базой данных. Здесь идут коннекты к базе, запросы, вставка.
 * service - классы описывают бизнес логику.
 * control - классы для работы с клиентам.
 * Любое веб приложение должно иметь минимум три слоя: controllers, service, persistence.
 */
@Controller
@ThreadSafe
public class IndexControl {

    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        model.addAttribute("user", UserSession.session(session));
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}