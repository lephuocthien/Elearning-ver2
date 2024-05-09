package com.lethien.elearning.controller;

import com.lethien.elearning.common.Common;
import com.lethien.elearning.dto.CourseDto;
import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.dto.UserCourseDto;
import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.service.CourseService;
import com.lethien.elearning.service.RoleService;
import com.lethien.elearning.service.UserCourseService;
import com.lethien.elearning.service.UserService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("admin/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final CourseService courseService;
    private final UserCourseService userCourseService;

    public UserController(
            UserService userService,
            RoleService roleService,
            CourseService courseService,
            UserCourseService userCourseService
    ) {
        this.userService = userService;
        this.roleService = roleService;
        this.courseService = courseService;
        this.userCourseService = userCourseService;
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String index(
            ModelMap modelMap,
            @RequestParam("page") Optional<Integer> page
    ) {
        int currentPage = page.orElse(1);
        Page<UserDto> userDtoPage = userService.getUserDtoPaging(
                PageRequest.of(currentPage - 1, Common.PAGE_SIZE)
        );
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
            @RequestParam("tabIndex") Optional<Integer> tabIndex,
            @RequestParam("pageOfCourse") Optional<Integer> pageOfCourse,
            ModelMap modelMap
    ) {
        int currentTabIndex = tabIndex.orElse(1);
        int currentPageOfCourse = pageOfCourse.orElse(1);
        Page<CourseDto> courseDtoPage = courseService.getCourseDtoPagingByUserId(
                PageRequest.of(currentPageOfCourse - 1, Common.PAGE_SIZE),
                id
        );
        modelMap.addAttribute("courses", courseDtoPage);
        int totalPages = courseDtoPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        UserDto user = userService.getById(id);
        List<RoleDto> roles = roleService.getAll();
        modelMap.addAttribute("tabIndex", currentTabIndex);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roles", roles);
        return "admin/user/edit/edit-layout";
    }

    @RequestMapping(value = {"edit"}, method = RequestMethod.POST)
    public String edit(
            @ModelAttribute("user") UserDto user,
            ModelMap modelMap,
            HttpSession session) {
        UserDto auth = (UserDto) session.getAttribute("AUTH");
        userService.edit(user);
        if (auth.getId() == user.getId()) {
            UserDto dto = userService.getUserDtoById(user.getId());
            session.setAttribute("AUTH", dto);
        }
        return "redirect:/admin/user/edit?id=" + user.getId();
    }

    @RequestMapping(value = {"edit-avatar"}, method = RequestMethod.POST)
    public String editAvatar(
            @RequestParam("id") int id,
            @RequestParam("image") MultipartFile file,
            HttpSession session) throws IOException {
        if (!file.isEmpty()) {
            UserDto auth = (UserDto) session.getAttribute("AUTH");
            UserDto user = userService.getUserDtoById(id);
            user.setAvatar(file.getBytes());
            user.setPassword("");
            userService.edit(user);
            if (auth.getId() == user.getId()) {
                session.setAttribute("AUTH", user);
            }
        }
        return "redirect:/admin/user/edit?id=" + id + "&tabIndex=3";
    }

    @RequestMapping(value = {"delete"}, method = RequestMethod.GET)
    public String delete(
            @RequestParam("id") int id) {
        userService.remove(id);
        return "redirect:/admin/user";
    }

    @RequestMapping(value = {"image"}, method = RequestMethod.GET)
    public void showProductImage(
            @RequestParam("id") int id,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("image/png");
        UserDto user = userService.getById(id);
        InputStream is = new ByteArrayInputStream(user.getAvatar());
        IOUtils.copy(is, response.getOutputStream());
    }

    @RequestMapping(value = {"search"}, method = RequestMethod.GET)
    public String searchUser(
            @RequestParam("key") String key,
            @RequestParam("page") Optional<Integer> page,
            ModelMap modelMap) {
        if (!key.isEmpty()) {
            int currentPage = page.orElse(1);
            Page<UserDto> userDtoPage = userService.getUserDtoResultPaging(
                    PageRequest.of(currentPage - 1, Common.PAGE_SIZE),
                    "%" + key + "%");
            modelMap.addAttribute("users", userDtoPage);
            modelMap.addAttribute("key", key);
            int totalPages = userDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream
                        .rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
            return "admin/user/result";
        }
        return "redirect:/admin/user";
    }

    @RequestMapping(value = {"delete-user-course"}, method = RequestMethod.GET)
    public String deleteCourseByUserId(
            @RequestParam("userId") int userId,
            @RequestParam("courseId") int courseId
    ) {
        userCourseService.remove(userId, courseId);
        CourseDto courseDto = courseService.getById(courseId);
        courseService.edit(courseDto);
        return "redirect:/admin/user/edit?id=" + userId + "&tabIndex=3";
    }

    @RequestMapping(value = {"course/add"}, method = RequestMethod.GET)
    public String addMember(
            @RequestParam("userId") int userId,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("key") Optional<String> key,
            ModelMap modelMap
    ) {
        int currentPage = page.orElse(1);
        String currentKey = key.orElse("");
        if (!currentKey.isEmpty()) {
            Page<CourseDto> courseDtoPage = courseService.getCourseDtoPagingWithoutUserIdByKey(
                    PageRequest.of(currentPage - 1, Common.PAGE_SIZE),
                    userId,
                    "%" + currentKey + "%"
            );
            modelMap.addAttribute("courses", courseDtoPage);
            int totalPages = courseDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
        } else {
            Page<CourseDto> courseDtoPage = courseService.getCourseDtoPagingWithoutUserId(
                    PageRequest.of(currentPage - 1, Common.PAGE_SIZE),
                    userId
            );
            modelMap.addAttribute("courses", courseDtoPage);
            int totalPages = courseDtoPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                modelMap.addAttribute("pageNumbers", pageNumbers);
            }
        }
        modelMap.addAttribute("userFullname", userService.getById(userId).getFullname());
        modelMap.addAttribute("key", currentKey);
        modelMap.addAttribute("userId", userId);
        return "admin/user/course/add";
    }

    @RequestMapping(value = {"course/add-perform"}, method = RequestMethod.GET)
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
        return "redirect:/admin/user/course/add?userId=" + userId;
    }
}
