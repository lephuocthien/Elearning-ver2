package com.lethien.elearning.controller;

import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.service.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
        modelMap.addAttribute("authName", session.getAttribute("AUTH_NAME"));
        return "admin/role/index";
    }
    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
	public String add(ModelMap modelMap, HttpSession session) {
		// Tạo đối tượng rỗng (ko có dữ liệu)
		RoleDto dto = new RoleDto();
		// Truyền đối tượng vừa tạo qua cho add.html
        modelMap.addAttribute("role", dto);
        modelMap.addAttribute("authName", session.getAttribute("AUTH_NAME"));
		return "admin/role/add";
	}

	@RequestMapping(value = {"add"}, method = RequestMethod.POST)
	public String add(@ModelAttribute("role") RoleDto role) {
		roleService.save(role);
		return "redirect:/admin/role";
	}

	@RequestMapping(value = {"edit"}, method = RequestMethod.GET)
	public String edit(@RequestParam("id") int id, ModelMap modelMap, HttpSession session) {
		RoleDto dto = roleService.getById(id);
        modelMap.addAttribute("role", dto);
        modelMap.addAttribute("authName", session.getAttribute("AUTH_NAME"));
		return "admin/role/edit";
	}

	@RequestMapping(value = {"edit"}, method = RequestMethod.POST)
	public String edit(@ModelAttribute("role") RoleDto role) {
		roleService.edit(role);
		return "redirect:/admin/role";
	}

	@RequestMapping(value = {"delete"}, method = RequestMethod.GET)
	public String delete(@RequestParam("id") int id) {
		roleService.remove(id);
		return "redirect:/admin/role";
	}
}
