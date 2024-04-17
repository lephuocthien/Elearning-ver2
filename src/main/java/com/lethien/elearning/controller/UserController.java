package com.lethien.elearning.controller;

import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("admin/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(ModelMap modelMap, HttpSession session) {
        List<UserDto> dtos = userService.getAllUserDto();
        modelMap.addAttribute("users", dtos);
        modelMap.addAttribute("userName", session.getAttribute("USER_NAME"));
        return "admin/user/index";
    }
}
