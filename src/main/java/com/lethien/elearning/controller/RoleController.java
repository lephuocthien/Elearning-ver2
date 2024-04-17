package com.lethien.elearning.controller;

import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.service.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("admin/role")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(ModelMap modelMap, HttpSession session) {
        List<RoleDto> dtos = roleService.getAll();
        modelMap.addAttribute("roles", dtos);
        modelMap.addAttribute("userName", session.getAttribute("USER_NAME"));
        return "admin/role/index";
    }
}
