/**
 * Dec 18, 2020
 * 3:38:23 PM
 * @author LeThien
 */
package com.lethien.elearning.service;

import java.util.List;

import com.lethien.elearning.dto.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
	List<RoleDto> getAll();
	List<RoleDto> getNotAdmin();
	RoleDto getById(int id);;
	void save(RoleDto dto);
	void edit(RoleDto dto);
	void remove(int id);
	Page<RoleDto> getRoleDtoPaging(Pageable pageable);
	Page<RoleDto> getRoleDtoResultPaging(Pageable pageable, String key);
}
