package com.lethien.elearning.controller;

import com.lethien.elearning.common.Common;
import com.lethien.elearning.dto.*;
import com.lethien.elearning.service.*;
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
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final VideoService videoService;
    private final TargetService targetService;
    private final UserService userService;
    private final UserCourseService userCourseService;

    public CourseController(
            CourseService courseService,
            CategoryService categoryService,
            VideoService videoService,
            TargetService targetService,
            UserService userService,
            UserCourseService userCourseService
    ) {
        this.courseService = courseService;
        this.categoryService = categoryService;
        this.videoService = videoService;
        this.targetService = targetService;
        this.userService = userService;
        this.userCourseService = userCourseService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(
            @RequestParam("page") Optional<Integer> page,
            ModelMap modelMap,
            HttpSession session) {
        int currentPage = page.orElse(1);
        Page<CourseDto> courseDtoPage = courseService.getCourseDtoPaging(
                PageRequest.of(currentPage - 1, Common.PAGE_SIZE)
        );
        modelMap.addAttribute("courses", courseDtoPage);
        int totalPages = courseDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/course/index";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.GET)
    public String add(ModelMap modelMap) {
        CourseDto course = new CourseDto();
        List<CategoryDto> categories = categoryService.getAll();
        modelMap.addAttribute("course", course);
        modelMap.addAttribute("categories", categories);
        return "admin/course/add";
    }

    @RequestMapping(value = {"add"}, method = RequestMethod.POST)
    public String add(
            @ModelAttribute("course") CourseDto course) {
        course.setLastUpdate(new java.util.Date());
        int id = courseService.saveGetBackId(course);
        return "redirect:/admin/course/edit?id=" + id;
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.GET)
    public String edit(
            @RequestParam("id") int courseId,
            @RequestParam("tabIndex") Optional<Integer> tabIndex,
            @RequestParam("pageOfVideo") Optional<Integer> pageOfVideo,
            @RequestParam("pageOfTarget") Optional<Integer> pageOfTarget,
            @RequestParam("pageOfMember") Optional<Integer> pageOfMember,
            @RequestParam("keyOfVideo") Optional<String> keyOfVideo,
            @RequestParam("keyOfTarget") Optional<String> keyOfTarget,
            ModelMap modelMap,
            HttpSession session
    ) {
        int currentTabIndex = tabIndex.orElse(1);
        int currentPageOfVideo = pageOfVideo.orElse(1);
        int currentPageOfTarget = pageOfTarget.orElse(1);
        int currentPageOfMember = pageOfMember.orElse(1);
        String currentKeyOfVideo = keyOfVideo.orElse("");
        String currentKeyOfTarget = keyOfTarget.orElse("");
        List<CategoryDto> categories = categoryService.getAll();
        CourseDto course = courseService.getById(courseId);
        //Paging Video
        if (!currentKeyOfVideo.isEmpty()) {
            Page<VideoDto> videoDtoPage = videoService
                    .getVideoDtoResultPagingByCourseId(
                            PageRequest.of(currentPageOfVideo - 1, Common.PAGE_SIZE),
                            courseId,
                            "%" + currentKeyOfVideo + "%"
                    );
            int totalPages = videoDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbersOfVideo", pageNumbers);
            }
            modelMap.addAttribute("keyOfVideo", currentKeyOfVideo);
            modelMap.addAttribute("videos", videoDtoPage);
        } else {
            Page<VideoDto> videoDtoPage = videoService
                    .getVideoDtoPagingByCourseId(
                            PageRequest.of(currentPageOfVideo - 1, Common.PAGE_SIZE),
                            courseId
                    );
            int totalPages = videoDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbersOfVideo", pageNumbers);
                modelMap.addAttribute("videos", videoDtoPage);
            }
        }
        //Paging Target
        if (!currentKeyOfTarget.isEmpty()) {
            Page<TargetDto> targetDtoPage = targetService
                    .getTargetDtoResultPagingByCourseId(
                            PageRequest.of(currentPageOfTarget - 1, Common.PAGE_SIZE),
                            courseId,
                            "%" + currentKeyOfTarget + "%"
                    );
            int totalPages = targetDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbersOfTarget", pageNumbers);
            }
            modelMap.addAttribute("keyOfTarget", currentKeyOfTarget);
            modelMap.addAttribute("targets", targetDtoPage);
        } else {
            Page<TargetDto> targetDtoPage = targetService
                    .getTargetDtoPagingByCourseId(
                            PageRequest.of(currentPageOfTarget - 1, Common.PAGE_SIZE),
                            courseId
                    );
            int totalPages = targetDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbersOfTarget", pageNumbers);
            }
            modelMap.addAttribute("targets", targetDtoPage);
        }
        //Paging member
        Page<UserDto> userDtoPage = userService.getUserDtoPagingByCourseId(
                PageRequest.of(currentPageOfMember - 1, Common.PAGE_SIZE),
                courseId
        );
        modelMap.addAttribute("users", userDtoPage);
        int totalPages = userDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbersOfMember", pageNumbers);
        }
        //Set modelMap
        modelMap.addAttribute("tabIndex", currentTabIndex);
        modelMap.addAttribute("categories", categories);
        modelMap.addAttribute("course", course);
        return "admin/course/edit/edit-layout";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.POST)
    public String edit(@ModelAttribute("course") CourseDto course) {
        course.setViewCount(userService.getUserDtoCountByCourseId(course.getId()));
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + course.getId();
    }

    @RequestMapping(value = {"edit-img"}, method = RequestMethod.POST)
    public String editAvatar(
            @RequestParam("id") int id,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        if (!file.isEmpty()) {
            CourseDto course = courseService.getById(id);
            course.setImage(file.getBytes());
            courseService.edit(course);
        }
        return "redirect:/admin/course/edit?id=" + id + "&tabIndex=2";
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
            HttpSession session
    ) {
        VideoDto videoDto = new VideoDto();
        videoDto.setCourseId(courseId);
        modelMap.addAttribute("video", videoDto);
        return "admin/course/video/add";
    }

    @RequestMapping(value = {"video/add"}, method = RequestMethod.POST)
    public String addVideoPost(@ModelAttribute("video") VideoDto video) {
        videoService.save(video);
        int courseId = video.getCourseId();
        CourseDto course = courseService.getById(courseId);
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + courseId + "&tabIndex=3";
    }

    @RequestMapping(value = {"video/edit"}, method = RequestMethod.GET)
    public String editVideoGet(
            @RequestParam("id") int id,
            ModelMap modelMap,
            HttpSession session
    ) {
        VideoDto videoDto = videoService.getById(id);
        modelMap.addAttribute("video", videoDto);
        return "admin/course/video/edit";
    }

    @RequestMapping(value = {"video/edit"}, method = RequestMethod.POST)
    public String editVideoPost(@ModelAttribute("video") VideoDto video) {
        videoService.edit(video);
        int courseId = video.getCourseId();
        CourseDto course = courseService.getById(courseId);
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + courseId + "&tabIndex=3";
    }

    @RequestMapping(value = {"video/delete"}, method = RequestMethod.GET)
    public String deleteVideo(
            @RequestParam("id") int id,
            @RequestParam("courseId") int courseId
    ) {
        videoService.remove(id);
        CourseDto course = courseService.getById(courseId);
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + courseId + "&tabIndex=3";
    }

    @RequestMapping(value = {"target/add"}, method = RequestMethod.GET)
    public String addTargetGet(
            @RequestParam("courseId") int courseId,
            ModelMap modelMap,
            HttpSession session
    ) {
        TargetDto targetDto = new TargetDto();
        targetDto.setCourseId(courseId);
        modelMap.addAttribute("target", targetDto);
        return "admin/course/target/add";
    }

    @RequestMapping(value = {"target/add"}, method = RequestMethod.POST)
    public String addTargetPost(@ModelAttribute("target") TargetDto target) {
        targetService.save(target);
        int courseId = target.getCourseId();
        CourseDto course = courseService.getById(courseId);
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + courseId + "&tabIndex=4";
    }

    @RequestMapping(value = {"target/edit"}, method = RequestMethod.GET)
    public String editTargetGet(
            @RequestParam("id") int id,
            ModelMap modelMap,
            HttpSession session) {
        TargetDto targetDto = targetService.getById(id);
        modelMap.addAttribute("target", targetDto);
        return "admin/course/target/edit";
    }

    @RequestMapping(value = {"target/edit"}, method = RequestMethod.POST)
    public String editTargetPost(@ModelAttribute("target") TargetDto target) {
        targetService.edit(target);
        int courseId = target.getCourseId();
        CourseDto course = courseService.getById(courseId);
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + courseId + "&tabIndex=4";
    }

    @RequestMapping(value = {"target/delete"}, method = RequestMethod.GET)
    public String deleteTarget(
            @RequestParam("id") int id,
            @RequestParam("courseId") int courseId) {
        targetService.remove(id);
        CourseDto course = courseService.getById(courseId);
        courseService.edit(course);
        return "redirect:/admin/course/edit?id=" + courseId + "&tabIndex=4";
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

    @RequestMapping(value = {"search"}, method = RequestMethod.GET)
    public String searchCourse(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("key") String key,
            ModelMap modelMap
    ) {
        if (!key.isEmpty()) {
            int currentPage = page.orElse(1);
            Page<CourseDto> courseDtoPage = courseService.getCourseDtoResultPaging(
                    PageRequest.of(currentPage - 1, Common.PAGE_SIZE),
                    "%" + key + "%"
            );
            modelMap.addAttribute("courses", courseDtoPage);
            modelMap.addAttribute("key", key);
            int totalPages = courseDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
            return "admin/course/result";
        }
        return "redirect:/admin/course";
    }

    @RequestMapping(value = {"delete-user-course"}, method = RequestMethod.GET)
    public String deleteCourseByUserId(
            @RequestParam("userId") int userId,
            @RequestParam("courseId") int courseId
    ) {
        userCourseService.remove(userId, courseId);
        CourseDto courseDto = courseService.getById(courseId);
        courseService.edit(courseDto);
        return "redirect:/admin/course/edit?id=" + courseId + "&tabIndex=5";
    }

    @RequestMapping(value = {"member/add"}, method = RequestMethod.GET)
    public String addMember(
            @RequestParam("courseId") int courseId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("key") Optional<String> key,
            ModelMap modelMap
    ) {
        int currentPage = page.orElse(1);
        String currentKey = key.orElse("");
        if (!currentKey.isEmpty()) {
            Page<UserDto> userDtoPage = userService.getUserDtoPagingWithoutCourseIdByKey(
                    PageRequest.of(currentPage - 1, Common.PAGE_SIZE),
                    courseId,
                    "%" + currentKey + "%"
            );
            modelMap.addAttribute("users", userDtoPage);
            int totalPages = userDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
        } else{
            Page<UserDto> userDtoPage = userService.getUserDtoPagingWithoutCourseId(
                    PageRequest.of(currentPage - 1, Common.PAGE_SIZE),
                    courseId
            );
            modelMap.addAttribute("users", userDtoPage);
            int totalPages = userDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
        }
        modelMap.addAttribute("courseTitle", courseService.getById(courseId).getTitle());
        modelMap.addAttribute("key", currentKey);
        modelMap.addAttribute("courseId", courseId);
        return "admin/course/member/add";
    }

    @RequestMapping(value = {"member/add-perform"}, method = RequestMethod.GET)
    public String addMemberPerform(
            @RequestParam("courseId") int courseId,
            @RequestParam("userId") int userId
    ) {
        UserCourseDto userCourseDto = new UserCourseDto();
        userCourseDto.setCourseId(courseId);
        userCourseDto.setUserId(userId);
        userCourseDto.setRoleId(userService.getById(userId).getRoleId());
        userCourseService.save(userCourseDto);
        CourseDto courseDto = courseService.getById(courseId);
        courseService.edit(courseDto);
        return "redirect:/admin/course/member/add?courseId=" + courseId;
    }
}
