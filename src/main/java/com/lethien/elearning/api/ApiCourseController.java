/**
 * Dec 21, 2020
 * 5:08:31 PM
 * @author LeThien
 */
package com.lethien.elearning.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lethien.elearning.dto.CourseDto;
import com.lethien.elearning.service.CourseService;

@RestController
@RequestMapping("api/course")
public class ApiCourseController {
	private final String UPLOAD_FOLDER = "/src/main/resources/upload/course/";
	private CourseService courseService;

	/**
	 * @param_courseService
	 */
	public ApiCourseController(CourseService courseService) {
		super();
		this.courseService = courseService;
	}

	// Tìm all
	@GetMapping("")
	public ResponseEntity<Object> getAll() {
		try {
			List<CourseDto> dtos = courseService.getAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm all dto
	@GetMapping("get-dto")
	public ResponseEntity<Object> getAllDto() {
		try {
			List<CourseDto> dtos = courseService.getAllCourseDto();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm theo id
	@GetMapping("get/{id}")
	public ResponseEntity<Object> getById(@PathVariable("id") int id) {
		try {
			CourseDto dto = courseService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm dto theo id
	@GetMapping("get-dto/{id}")
	public ResponseEntity<Object> getDtoById(@PathVariable("id") int id) {
		try {
			CourseDto dto = courseService.getDtoById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm tất cả dto theo title
	@GetMapping("search-dto/{key}")
	public ResponseEntity<Object> getDtoByTitle(@PathVariable("key") String key) {
		try {
			key = "%"+key+"%";
			List<CourseDto> dtos = courseService.getAllCourseDtoByTitle(key);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm theo userId
	@GetMapping("get-course-by-user-id/{id}")
	public ResponseEntity<Object> getAllByUserId(@PathVariable("id") int id) {
		try {
			List<CourseDto> dtos = courseService.getAllCourseDtoByUserId(id);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Thêm mới
	@PostMapping("add")
	public ResponseEntity<Object> add(@RequestBody CourseDto dto) {
		try {
			courseService.save(dto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Cập nhật
	@PutMapping("update/{id}")
	public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody CourseDto dto) {
		try {
			if (courseService.getById(id) == null)
				return new ResponseEntity<Object>("Id " + id + " không tồn tại", HttpStatus.CREATED);
			courseService.edit(dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Xoá
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") int id) {
		try {
			courseService.remove(id);
			return new ResponseEntity<Object>("Xoá thành công", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "file/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object upload(@RequestParam() MultipartFile file) {
		try {
			String pathName = System.getProperty("user.dir");
			pathName += UPLOAD_FOLDER;
			File dir = new File(pathName);
			if (!dir.exists())
				dir.mkdirs();

			String pathSource = pathName + file.getOriginalFilename();
			File serverFile = new File(pathSource);
			FileOutputStream stream;
			try {
				stream = new FileOutputStream(serverFile);
				stream.write(file.getBytes());
				stream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ResponseEntity<Object>(file.getOriginalFilename(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "file/load/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public FileSystemResource getFile(@PathVariable("fileName") String fileName) throws IOException {
		String pathName = System.getProperty("user.dir");
		pathName += UPLOAD_FOLDER + fileName;
		return new FileSystemResource(pathName);
	}
}
