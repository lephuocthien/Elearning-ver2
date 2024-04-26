package com.lethien.elearning.controller;

import com.lethien.elearning.dto.*;
import com.lethien.elearning.service.CategoryService;
import com.lethien.elearning.service.CourseService;
import com.lethien.elearning.service.VideoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("admin/course")
public class CourseController {
    private CourseService courseService;
    private CategoryService categoryService;
    private VideoService videoService;

    public CourseController (
            CourseService courseService,
            CategoryService categoryService,
            VideoService videoService){
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.videoService = videoService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(
            ModelMap modelMap,
            HttpSession session) {
        List<CourseDto> courses = courseService.getAll();
        modelMap.addAttribute("courses", courses);
        session.removeAttribute("TAB_INDEX");
        return "admin/course/index";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String add(
            ModelMap modelMap,
            HttpSession session) {
        CourseDto course = new CourseDto();
        List<CategoryDto> categories = categoryService.getAll();
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("categories", categories);
        return "admin/course/add";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public String add(
            @ModelAttribute("course") CourseDto course,
            ModelMap modelMap,
            HttpSession session) {
        course.setImage("course.jpg");
        course.setLastUpdate(new java.util.Date());
        int id = courseService.saveGetBackId(course);
        return "redirect:/admin/course/edit?id="+id;
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String edit(@RequestParam("id") int id, ModelMap modelMap, HttpSession session) {
        if (session.getAttribute("TAB_INDEX") == null){
            session.setAttribute("TAB_INDEX", 1);
        }
        List<CategoryDto> categories = categoryService.getAll();
        CourseDto course = courseService.getDtoById(id);
        List<VideoDto> videos = course.getVideos();
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("videos", videos);
        return "admin/course/edit/edit-layout";
    }

    @RequestMapping(value = {"delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam("id") int id) {
        courseService.remove(id);
        return "redirect:/admin/course";
    }

    @RequestMapping(value = {"video/add"}, method = RequestMethod.GET)
    public String addVideo(
            @RequestParam("courseId") int courseId,
            ModelMap modelMap,
            HttpSession session) {
        VideoDto videoDto = new VideoDto();
        modelMap.addAttribute("video", videoDto);
        modelMap.addAttribute("courseId", courseId);
        session.setAttribute("TAB_INDEX", 2);
        return "admin/course/video/add";
    }

    @RequestMapping(value = {"video/edit"}, method = RequestMethod.GET)
    public String editVideo(
            @RequestParam("id") int id,
            @RequestParam("courseId") int courseId,
            ModelMap modelMap,
            HttpSession session) {
        VideoDto videoDto = videoService.getById(id);
        modelMap.addAttribute("video", videoDto);
        modelMap.addAttribute("courseId", courseId);
        session.setAttribute("TAB_INDEX", 2);
        return "admin/course/video/edit";
    }
}
