/**
 * Dec 19, 2020
 * 11:13:14 PM
 * @author LeThien
 */
package com.lethien.elearning.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.service.RoleService;

@RestController
@RequestMapping("api/role")
public class ApiRoleController {
	private RoleService roleService;

	/**
	 * @param roleService
	 */
	public ApiRoleController(RoleService roleService) {
		super();
		this.roleService = roleService;
	}

	// Tìm tất cả
	@GetMapping("")
	public ResponseEntity<Object> getAll() {
		try {
			List<RoleDto> dtos = roleService.getAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm tất cả trừ ROLE_ADMIN
	@GetMapping("get-all-not-admin")
	public ResponseEntity<Object> getAllNotAdmin() {
		try {
			List<RoleDto> dtos = roleService.getNotAdmin();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Tìm theo id
	@GetMapping("get/{id}")
	public ResponseEntity<Object> getById(@PathVariable("id") int id) {
		try {
			RoleDto dto = roleService.getById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Sai ID", HttpStatus.BAD_REQUEST);
		}
	}

	// Thêm mới
	@PostMapping("add")
	public ResponseEntity<Object> add(@RequestBody RoleDto dto) {
		try {
			roleService.save(dto);
			return new ResponseEntity<Object>("Thêm thành công!", HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>("Thêm thất bại!", HttpStatus.BAD_REQUEST);
		}
	}

	// Cập nhật
	@PutMapping("update/{id}")
	public ResponseEntity<Object> edit(@PathVariable("id") int id, @RequestBody RoleDto dto) {
		try {
			if (roleService.getById(id) == null)
				return new ResponseEntity<Object>("Id " + id + " không tồn tại", HttpStatus.CREATED);
			roleService.edit(dto);
			return new ResponseEntity<Object>("Cập nhật thành công!", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}

	// Xoá
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> remove(@PathVariable("id") int id) {
		try {
			roleService.remove(id);
			return new ResponseEntity<Object>("Xoá thành công", HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
