package com.lethien.elearning.controller;

import com.lethien.elearning.dto.*;
import com.lethien.elearning.service.CategoryService;
import com.lethien.elearning.service.CourseService;
import com.lethien.elearning.service.TargetService;
import com.lethien.elearning.service.VideoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/course")
public class CourseController {
    private CourseService courseService;
    private CategoryService categoryService;
    private VideoService videoService;
    private TargetService targetService;
    @Value("${app.data.page-size}")
    private int pageSize;

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
            @RequestParam("page") Optional<Integer> page,
            ModelMap modelMap,
            HttpSession session) {
        int currentPage = page.orElse(1);
        Page<CourseDto> courseDtoPage = courseService.getCourseDtoPaging(PageRequest.of(currentPage - 1, pageSize));
        modelMap.addAttribute("courses", courseDtoPage);
        int totalPages = courseDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
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
        //course.setImage("course.jpg");
        course.setLastUpdate(new java.util.Date());
        int id = courseService.saveGetBackId(course);
        return "redirect:/admin/course/edit?id=" + id;
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String edit(
            @RequestParam("id") int courseId,
            @RequestParam("pageOfVideo") Optional<Integer> pageOfVideo,
            @RequestParam("pageOfTarget") Optional<Integer> pageOfTarget,
            ModelMap modelMap,
            HttpSession session) {
        int currentPageOfVideo = pageOfVideo.orElse(1);
        int currentPageOfTarget = pageOfTarget.orElse(1);
        if (session.getAttribute("TAB_INDEX") == null) {
            session.setAttribute("TAB_INDEX", 1);
        } else if (pageOfVideo.isPresent()) {
            session.setAttribute("TAB_INDEX", 2);
        } else if (pageOfTarget.isPresent()) {
            session.setAttribute("TAB_INDEX", 3);
        }
        List<CategoryDto> categories = categoryService.getAll();
        CourseDto course = courseService.getById(courseId);
        //Paging Video
        Page<VideoDto> videoDtoPage = videoService
                .getVideoDtoPagingByCourseId(
                        PageRequest.of(currentPageOfVideo - 1, pageSize),
                        courseId);
        int totalPages = videoDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbersOfVideo", pageNumbers);
        }
        //Paging Target
        Page<TargetDto> targetDtoPage = targetService
                .getTargetDtoPagingByCourseId(
                        PageRequest.of(currentPageOfTarget - 1, pageSize),
                        courseId);
        totalPages = targetDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbersOfTarget", pageNumbers);
        }
        //Set modelMap
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("videos", videoDtoPage);
        modelMap.addAttribute("targets", targetDtoPage);
        return "admin/course/edit/edit-layout";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute("course") CourseDto course) {
        //course.setImage("course.jpg");
        course.setLastUpdate(new java.util.Date());
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + course.getId();
    }

    @RequestMapping(value = {"edit-img"}, method = RequestMethod.POST)
    public String editAvatar(
            @RequestParam("id") int id,
            @RequestParam("image") MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            CourseDto course = courseService.getById(id);
            course.setImage(file.getBytes());
            courseService.edit(course);
        }
        return "redirect:/admin/course/edit?id=" + id;
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

    @RequestMapping(value = {"image"}, method = RequestMethod.GET)
    public void showProductImage(
            @RequestParam("id") int id,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("image/png");
        CourseDto courseDto = courseService.getById(id);
        InputStream is = new ByteArrayInputStream(courseDto.getImage());
        IOUtils.copy(is, response.getOutputStream());
    }
}
