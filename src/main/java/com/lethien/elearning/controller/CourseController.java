package com.lethien.elearning.controller;

import com.lethien.elearning.dto.*;
import com.lethien.elearning.entity.Video;
import com.lethien.elearning.service.CategoryService;
import com.lethien.elearning.service.CourseService;
import com.lethien.elearning.service.TargetService;
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
    private TargetService targetService;

    public CourseController(
            CourseService courseService,
            CategoryService categoryService,
            VideoService videoService,
            TargetService targetService) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.videoService = videoService;
        this.targetService = targetService;
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
        return "redirect:/admin/course/edit?id=" + id;
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String edit(@RequestParam("id") int id, ModelMap modelMap, HttpSession session) {
        if (session.getAttribute("TAB_INDEX") == null) {
            session.setAttribute("TAB_INDEX", 1);
        }
        List<CategoryDto> categories = categoryService.getAll();
        CourseDto course = courseService.getDtoById(id);
        List<VideoDto> videos = course.getVideos();
        List<TargetDto> targets = course.getTargets();
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("videos", videos);
        modelMap.addAttribute("targets", targets);
        return "admin/course/edit/edit-layout";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute("course") CourseDto course) {
        course.setImage("course.jpg");
        course.setLastUpdate(new java.util.Date());
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + course.getId();
    }

    @RequestMapping(value = {"delete"}, method = RequestMethod.GET)
    public String delete(@RequestParam("id") int id) {
        courseService.remove(id);
        return "redirect:/admin/course";
    }

    @RequestMapping(value = {"video/add"}, method = RequestMethod.GET)
    public String addVideoGet(
            @RequestParam("courseId") int courseId,
            ModelMap modelMap,
            HttpSession session) {
        VideoDto videoDto = new VideoDto();
        videoDto.setCourseId(courseId);
        modelMap.addAttribute("video", videoDto);
        session.setAttribute("TAB_INDEX", 2);
        return "admin/course/video/add";
    }

    @RequestMapping(value = {"video/add"}, method = RequestMethod.POST)
    public String addVideoPost(@ModelAttribute("video") VideoDto video) {
        videoService.save(video);
        int courseId = video.getCourseId();
        //Update hourCourse of course BEGIN
        List<VideoDto> videos = videoService.getAllVideoByCourseId(courseId);
        CourseDto course = courseService.getById(courseId);
        int hourCourse = 0;
        for (VideoDto item : videos) {
            hourCourse += item.getTimeCount();
        }
        course.setLeturesCount(videos.size());
        course.setLastUpdate(new java.util.Date());
        course.setHourCount(hourCourse);
        courseService.edit(course);
        //Update hourCourse of course END
        return "redirect:/admin/course/edit?id=" + courseId;
    }

    @RequestMapping(value = {"video/edit"}, method = RequestMethod.GET)
    public String editVideoGet(
            @RequestParam("id") int id,
            ModelMap modelMap,
            HttpSession session) {
        VideoDto videoDto = videoService.getById(id);
        modelMap.addAttribute("video", videoDto);
        session.setAttribute("TAB_INDEX", 2);
        return "admin/course/video/edit";
    }

    @RequestMapping(value = {"video/edit"}, method = RequestMethod.POST)
    public String editVideoPost(@ModelAttribute("video") VideoDto video) {
        videoService.edit(video);
        int courseId = video.getCourseId();
        //Update hourCourse of course BEGIN
        List<VideoDto> videos = videoService.getAllVideoByCourseId(courseId);
        CourseDto course = courseService.getById(courseId);
        int hourCourse = 0;
        for (VideoDto item : videos) {
            hourCourse += item.getTimeCount();
        }
        course.setLeturesCount(videos.size());
        course.setLastUpdate(new java.util.Date());
        course.setHourCount(hourCourse);
        courseService.edit(course);
        //Update hourCourse of course END
        return "redirect:/admin/course/edit?id=" + courseId;
    }

    @RequestMapping(value = {"video/delete"}, method = RequestMethod.GET)
    public String deleteVideo(
            @RequestParam("id") int id,
            @RequestParam("courseId") int courseId) {
        videoService.remove(id);
        return "redirect:/admin/course/edit?id=" + courseId;
    }

    @RequestMapping(value = {"target/add"}, method = RequestMethod.GET)
    public String addTargetGet(
            @RequestParam("courseId") int courseId,
            ModelMap modelMap,
            HttpSession session) {
        TargetDto targetDto = new TargetDto();
        targetDto.setCourseId(courseId);
        modelMap.addAttribute("target", targetDto);
        session.setAttribute("TAB_INDEX", 3);
        return "admin/course/target/add";
    }
    @RequestMapping(value = {"target/add"}, method = RequestMethod.POST)
    public String addTargetPost(@ModelAttribute("target") TargetDto target) {
        targetService.save(target);
        int courseId = target.getCourseId();
        return "redirect:/admin/course/edit?id=" + courseId;
    }

    @RequestMapping(value = {"target/edit"}, method = RequestMethod.GET)
    public String editTargetGet(
            @RequestParam("id") int id,
            ModelMap modelMap,
            HttpSession session) {
        TargetDto targetDto = targetService.getById(id);
        modelMap.addAttribute("target", targetDto);
        session.setAttribute("TAB_INDEX", 3);
        return "admin/course/target/edit";
    }

    @RequestMapping(value = {"target/edit"}, method = RequestMethod.POST)
    public String editTargetPost(@ModelAttribute("target") TargetDto target) {
        targetService.edit(target);
        int courseId = target.getCourseId();
        return "redirect:/admin/course/edit?id=" + courseId;
    }

    @RequestMapping(value = {"target/delete"}, method = RequestMethod.GET)
    public String deleteTarget(
            @RequestParam("id") int id,
            @RequestParam("courseId") int courseId) {
        targetService.remove(id);
        return "redirect:/admin/course/edit?id=" + courseId;
    }
}
