/**
 * Dec 19, 2020
 * 11:37:37 PM
 * @author LeThien
 */
package com.lethien.elearning.api;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lethien.elearning.dto.UserDto;
import com.lethien.elearning.service.CourseService;
import com.lethien.elearning.service.UserService;

@RestController
@RequestMapping("api/user")
public class ApiUserController {
	private final UserService userService;
	private final CourseService courseService;

	/**
	 * @param userService
	 * @param courseService
	 */
	public ApiUserController(UserService userService, CourseService courseService) {
		super();
		this.userService = userService;
		this.courseService = courseService;
	}

	// Tìm all
	@GetMapping("")
	public ResponseEntity<Object> getAll() {
		try {
			List<UserDto> dtos = userService.getAllUserDto();
			for (int i = 0; i < dtos.size(); i++) {
				dtos.get(i).setCourses(courseService.getAllCourseDtoByUserId(dtos.get(i).getId()));
			}
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm theo id
	@GetMapping("get/{id}")
	public ResponseEntity<Object> getById(@PathVariable("id") int id) {
		try {
			UserDto dto = userService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("get-user-by-teacher/{courseId}")
	public ResponseEntity<Object> getUserByTeacher(@PathVariable("courseId") int courseId) {
		try {
			List<UserDto> dtos = userService.getAllUserDtoOfCourseByTeacher(courseId);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	// Thêm mới
	@PostMapping("add")
	public ResponseEntity<Object> add(@RequestBody UserDto dto) {
		try {
			if (userService.getUserDtoByEmail(dto.getEmail()) == null) {
				userService.save(dto);
				return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
			}
			return new ResponseEntity<Object>("Email đã tồn tại!", HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Cập nhật
	@PutMapping("update/{id}")
	public ResponseEntity<Object> edit(
			@PathVariable("id") int id,
			@RequestBody UserDto dto) throws IOException, ServletException {
		try {
			if (userService.getById(id) == null)
				return new ResponseEntity<Object>("Id " + id + " không tồn tại", HttpStatus.BAD_REQUEST);
			userService.edit(dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Xoá
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") int id) {
		try {
			userService.remove(id);
			return new ResponseEntity<Object>("Xoá thành công", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/paging/{pageIndex}/{pageSize}")
	public ResponseEntity<Object> getUserPaging(@PathVariable("pageIndex") int pageIndex,
			@PathVariable("pageSize") int pageSize) {
		if (pageIndex < 1 || pageSize < 1)
			return new ResponseEntity<>("Invalid parameters.", HttpStatus.BAD_REQUEST);
		Page<UserDto> results = userService.getUserDtoPaging(PageRequest.of(pageIndex - 1, pageSize));
		if (results.getSize() > 0)
			return new ResponseEntity<>(results, HttpStatus.OK);
		else
			return new ResponseEntity<>("No user was found.", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("get-user-by-token")
	public Object getUserDtoByToken() {
		try {
			// Lấy thông tin user lưu trữ trong SercurityContext
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Ép kiểu về UserDetails
			UserDetails userDetails = (UserDetails) principal;
			// Lấy ra email
			String email = userDetails.getUsername();
			// Lấy ra thông tin User để trả về cho client;
			UserDto dto = userService.getUserDtoByEmail(email);
			dto.setCourses(courseService.getAllCourseDtoByUserId(dto.getId()));
			return new ResponseEntity<UserDto>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "file/upload/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> upload(
			@PathVariable("id") int userId,
			@RequestParam(name = "file") MultipartFile file) {
		try {
			System.out.println(userId);
			UserDto dto = userService.getUserDtoById(userId);
			dto.setAvatar(file.getBytes());
			dto.setPassword("");
			userService.edit(dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}	
	}
	@GetMapping(value = "file/load/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getFile(@PathVariable("id") int id) throws IOException {
		UserDto dto = userService.getUserDtoById(id);
		return new ResponseEntity<Object>(dto.getAvatar(), HttpStatus.CREATED);
	}

}
