/**
 * Dec 20, 2020
 * 9:52:20 PM
 * @author LeThien
 */
package com.lethien.elearning.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lethien.elearning.dto.CategoryDto;
import com.lethien.elearning.service.CategoryService;

@RestController
@RequestMapping("api/category")
public class ApiCategoryController {

	private CategoryService categoryService;

	/**
	 * @param_categotyService
	 */
	public ApiCategoryController(CategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	// Tìm tất cả
	@GetMapping("")
	public ResponseEntity<Object> getAll() {
		try {
			List<CategoryDto> dtos = categoryService.getAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm theo id
	@GetMapping("get/{id}")
	public ResponseEntity<Object> getById(@PathVariable("id") int id) {
		try {
			CategoryDto dto = categoryService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Sai ID", HttpStatus.BAD_REQUEST);
		}
	}

	// Thêm mới
	@PostMapping("add")
	public ResponseEntity<Object> add(@RequestBody CategoryDto dto) {
		try {
			categoryService.save(dto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>("Thêm thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	// Cập nhật
	@PutMapping("update/{id}")
	public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody CategoryDto dto) {
		try {
			if (categoryService.getById(id) == null)
				return new ResponseEntity<Object>("Id " + id + " không tồn tại", HttpStatus.CREATED);
			categoryService.edit(dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Xoá
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") int id) {
		try {
			categoryService.remove(id);
			return new ResponseEntity<Object>("Xoá thành công", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
