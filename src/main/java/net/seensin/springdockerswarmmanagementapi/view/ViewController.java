package net.seensin.springdockerswarmmanagementapi.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping("/login")
    public String loginRedirect() {
        return "redirect:/";
    }

    @RequestMapping("/home/**")
    public String homeRedirect() {
        return "redirect:/";
    }
}
