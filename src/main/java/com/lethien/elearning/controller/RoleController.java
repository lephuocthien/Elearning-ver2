package com.lethien.elearning.controller;

import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.service.RoleService;
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
@RequestMapping("admin/role")
public class RoleController {

    private RoleService roleService;
	@Value("${app.data.page-size}")
	private int pageSize;
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(
			ModelMap modelMap,
			@RequestParam("page") Optional<Integer> page) {
		int currentPage = page.orElse(1);
		Page<RoleDto> roleDtoPage = roleService.getRoleDtoPaging(PageRequest.of(currentPage - 1, pageSize));
		modelMap.addAttribute("roles", roleDtoPage);
		int totalPages = roleDtoPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			modelMap.addAttribute("pageNumbers", pageNumbers);
		}
        return "admin/role/index";
    }
    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
	public String add(ModelMap modelMap, HttpSession session) {
		// Tạo đối tượng rỗng (ko có dữ liệu)
		RoleDto dto = new RoleDto();
		// Truyền đối tượng vừa tạo qua cho add.html
        modelMap.addAttribute("role", dto);
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
