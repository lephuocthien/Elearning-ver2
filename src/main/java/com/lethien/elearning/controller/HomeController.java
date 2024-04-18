package com.lethien.elearning.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin/home")
public class HomeController {
    public HomeController() {
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(ModelMap modelMap, HttpSession session) {
        modelMap.addAttribute("authName", session.getAttribute("AUTH_NAME"));
        return "admin/home/index";
    }
}
