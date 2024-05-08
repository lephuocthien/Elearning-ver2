/**
 * Dec 30, 2020
 * 2:18:44 AM
 * @author LeThien
 */
package com.lethien.elearning.api;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lethien.elearning.dto.UserCourseDto;
import com.lethien.elearning.entity.UserCourseId;
import com.lethien.elearning.service.UserCourseService;
import com.lethien.elearning.service.UserService;

@RestController
@RequestMapping("api/user-course")
public class ApiUserCourseController {
	private final UserCourseService userCourseService;

	/**
	 * @param userCourseService
	 */
	public ApiUserCourseController(UserCourseService userCourseService) {
		super();
		this.userCourseService = userCourseService;
	}

	// Tìm theo id
	@GetMapping("get-course-by-user-id/{userId}/{courseId}")
	public ResponseEntity<Object> getUserCourseById(@PathVariable("userId") int userId,
			@PathVariable("courseId") int courseId) {
		try {
			UserCourseId id = new UserCourseId(userId, courseId);
			UserCourseDto dto = userCourseService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//Thêm mới
	@PostMapping("add")
	public ResponseEntity<Object> add(@RequestBody UserCourseDto userCourseDto){
		try {
			userCourseService.save(userCourseDto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

}
