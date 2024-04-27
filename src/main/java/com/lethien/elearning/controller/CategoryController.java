package com.lethien.elearning.controller;

import com.lethien.elearning.dto.CategoryDto;
import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("admin/category")
public class CategoryController {
    private CategoryService categoryService;
    public  CategoryController (CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @RequestMapping(value="", method = RequestMethod.GET)
    public String index(ModelMap modelMap, HttpSession session) {
        List<CategoryDto> categories = categoryService.getAll();
        modelMap.addAttribute("categories", categories);
        return "admin/category/index";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String add(ModelMap modelMap, HttpSession session) {
        CategoryDto category = new CategoryDto();
        modelMap.addAttribute("category", category);
        return "admin/category/add";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public String add(@ModelAttribute("category") CategoryDto category) {
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String edit(@RequestParam("id") int id, ModelMap modelMap, HttpSession session) {
        CategoryDto category = categoryService.getById(id);
        modelMap.addAttribute("category", category);
        return "admin/category/edit";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute("category") CategoryDto category) {
        categoryService.edit(category);
        return "redirect:/admin/category";
    }

    @RequestMapping(value = {"delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam("id") int id) {
        categoryService.remove(id);
        return "redirect:/admin/category";
    }
}
