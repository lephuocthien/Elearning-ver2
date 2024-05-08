/**
 * Dec 21, 2020
 * 4:59:50 PM
 * @author LeThien
 */
package com.lethien.elearning.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lethien.elearning.dto.VideoDto;
import com.lethien.elearning.service.VideoService;

@RestController
@RequestMapping("api/video")
public class ApiVideoController {
	private final VideoService videoService;

	/**
	 * @param videoService
	 */
	public ApiVideoController(VideoService videoService) {
		super();
		this.videoService = videoService;
	}

	// Tìm all
	@GetMapping("")
	public ResponseEntity<Object> getAll() {
		try {
			List<VideoDto> dtos = videoService.getAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm theo id
	@GetMapping("get/{id}")
	public ResponseEntity<Object> getById(@PathVariable("id") int id) {
		try {
			VideoDto dto = videoService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Thêm mới
	@PostMapping("add")
	public ResponseEntity<Object> add(@RequestBody VideoDto dto) {
		try {
			videoService.save(dto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Cập nhật
	@PutMapping("update/{id}")
	public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody VideoDto dto) {
		try {
			if (videoService.getById(id) == null)
				return new ResponseEntity<Object>("Id " + id + " không tồn tại", HttpStatus.CREATED);
			videoService.edit(dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Xoá
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") int id) {
		try {
			videoService.remove(id);
			return new ResponseEntity<Object>("Xoá thành công", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
