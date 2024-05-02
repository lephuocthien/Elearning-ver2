package com.lethien.elearning.controller;

import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.service.RoleService;
import com.lethien.elearning.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;
    @Value("${app.data.page-size}")
	private int pageSize;

    public UserController(
            UserService userService,
            RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(
            ModelMap modelMap,
            @RequestParam("page") Optional<Integer> page) {
        int currentPage = page.orElse(1);
        Page<UserDto> userDtoPage = userService.getUserDtoPaging(PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("users", userDtoPage);
        int totalPages = userDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/user/index";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String add(
            ModelMap modelMap,
            HttpSession session) {
        UserDto user = new UserDto();
        List<RoleDto> roles = roleService.getAll();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roles", roles);
        return "admin/user/add";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public String add(
            @ModelAttribute("user") UserDto user,
            ModelMap modelMap,
            HttpSession session) {
        userService.save(user);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String edit(
            @RequestParam("id") int id,
            ModelMap modelMap,
            HttpSession session) {
        UserDto user = userService.getById(id);
        List<RoleDto> roles = roleService.getAll();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roles", roles);
        return "admin/user/edit";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.POST)
    public String edit(
            @ModelAttribute("user") UserDto user,
            ModelMap modelMap,
            HttpSession session) {
        userService.edit(user);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = {"delete"}, method = RequestMethod.GET)
    public String delete(
            @RequestParam("id") int id) {
        userService.remove(id);
        return "redirect:/admin/user";
    }
}
