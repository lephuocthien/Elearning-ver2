package com.lethien.elearning.controller;

import com.lethien.elearning.common.Common;
import com.lethien.elearning.dto.CategoryDto;
import com.lethien.elearning.dto.CourseDto;
import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.service.CategoryService;
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
@RequestMapping("admin/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(
            ModelMap modelMap,
            @RequestParam("page") Optional<Integer> page
    ) {
        int currentPage = page.orElse(1);
        Page<CategoryDto> categoryDtoPage = categoryService.getCategoryDtoPaging(
                PageRequest.of(currentPage - 1, Common.PAGE_SIZE)
        );
        modelMap.addAttribute("categories", categoryDtoPage);
        int totalPages = categoryDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
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
    public String edit(
            @RequestParam("id") int id,
            ModelMap modelMap
    ) {
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

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String searchCategory(
            ModelMap modelMap,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("key") String key
    ) {
        if (!key.isEmpty()) {
            int currentPage = page.orElse(1);
            Page<CategoryDto> categoryDtoPage = categoryService.getCategoryDtoResultPaging(
                    PageRequest.of(currentPage - 1, Common.PAGE_SIZE),
                    "%" + key + "%");
            modelMap.addAttribute("categories", categoryDtoPage);
            modelMap.addAttribute("key", key);
            int totalPages = categoryDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
            return "/admin/category/result";
        }
        return "redirect:/admin/category";
    }
}
