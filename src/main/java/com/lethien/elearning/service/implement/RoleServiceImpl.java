/**
 * Dec 18, 2020
 * 3:42:45 PM
 * @author LeThien
 */
package com.lethien.elearning.service.implement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.entity.Role;
import com.lethien.elearning.repository.RoleRepository;
import com.lethien.elearning.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	private RoleRepository roleRepository;
	/**
	 * @param roleRepository
	 */
	public RoleServiceImpl(RoleRepository roleRepository) {
		super();
		this.roleRepository = roleRepository;
	}
	@Override
	public List<RoleDto> getAll() {
		List<Role> roles = roleRepository.findAll();
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		for(Role role:roles) {
			RoleDto dto = new RoleDto();
			dto.setId(role.getId());
			dto.setName(role.getName());
			dto.setDescription(role.getDescription());
			dtos.add(dto);
		}
		return dtos;
	}
	@Override
	public List<RoleDto> getNotAdmin() {
		return roleRepository.findAllNotAdmin();
	}
	@Override
	public RoleDto getById(int id) {
		Role role = roleRepository.findById(id).get();
		RoleDto dto = new RoleDto();
		dto.setId(role.getId());
		dto.setName(role.getName());
		dto.setDescription(role.getDescription());
		return dto;
	}
	@Override
	public void save(RoleDto dto) {
		Role role = new Role();
		role.setName(dto.getName());
		role.setDescription(dto.getDescription());
		roleRepository.save(role);	
	}
	@Override
	public void edit(RoleDto dto) {
		Role role = roleRepository.findById(dto.getId()).orElse(null);
		if(role!=null) {
			role.setName(dto.getName());
			role.setDescription(dto.getDescription());
			roleRepository.save(role);	
		}
	}
	@Override
	public void remove(int id) {
		roleRepository.deleteById(id);
	}
	@Override
	public Page<RoleDto> getRoleDtoPaging(Pageable pageable){
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		Page<RoleDto> roleDtoPage;
		if (roleRepository.count() < startItem) {
			roleDtoPage = new PageImpl<RoleDto>(
					Collections.emptyList(),
					PageRequest.of(currentPage, pageSize),
					roleRepository.count()
			);
		} else {
			roleDtoPage = roleRepository.getRoleDtoPaging(
					PageRequest.of(currentPage, pageSize)
			);
		}
		return roleDtoPage;
	}
	@Override
	public Page<RoleDto> getRoleDtoResultPaging(
			Pageable pageable,
			String key
	){
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		Page<RoleDto> roleDtoPage;
		if (roleRepository.getRoleDtoResultCount(key) < startItem) {
			roleDtoPage = new PageImpl<RoleDto>(
					Collections.emptyList(),
					PageRequest.of(currentPage, pageSize),
					roleRepository.getRoleDtoResultCount(key)
			);
		} else {
			roleDtoPage = roleRepository.getRoleDtoResultPaging(
					PageRequest.of(currentPage, pageSize),
					key
			);
		}
		return roleDtoPage;
	}
}
