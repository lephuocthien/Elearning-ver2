/**
 * Dec 18, 2020
 * 6:28:47 PM
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
import com.lethien.elearning.dto.TargetDto;
import com.lethien.elearning.entity.Target;
import com.lethien.elearning.repository.TargetRepository;
import com.lethien.elearning.service.TargetService;

@Service
public class TargetServiceImpl implements TargetService {
	private TargetRepository targetRepository;
	/**
	 * @param targetRepository
	 */
	public TargetServiceImpl(TargetRepository targetRepository) {
		super();
		this.targetRepository = targetRepository;
	}
	@Override
	public List<TargetDto> getAll() {
		List<Target> targets = targetRepository.findAll();
		List<TargetDto> dtos = new ArrayList<TargetDto>();
		for (Target target : targets) {
			TargetDto dto = new TargetDto();
			dto.setId(target.getId());
			dto.setTitle(target.getTitle());
			dto.setCourseId(target.getCourseId());
			dtos.add(dto);
		}
		return dtos;
	}
	@Override
	public List<TargetDto> getAllTargetByCourseId (int courseId){
		return targetRepository.getAllTargetByCourseId(courseId);
	}
	@Override
	public TargetDto getById(int id) {
		Target target = targetRepository.findById(id).get();
		TargetDto dto = new TargetDto();
		dto.setId(target.getId());
		dto.setTitle(target.getTitle());
		dto.setCourseId(target.getCourseId());
		return dto;
	}
	@Override
	public void save(TargetDto dto) {
		Target target = new Target();
		target.setId(dto.getId());
		target.setTitle(dto.getTitle());
		target.setCourseId(dto.getCourseId());
		targetRepository.save(target);
	}
	@Override
	public void edit(TargetDto dto) {
		Target target = targetRepository.findById(dto.getId()).get();
		if(target!=null) {
			target.setTitle(dto.getTitle());
			target.setCourseId(dto.getCourseId());
			targetRepository.save(target);
		}
	}
	@Override
	public void remove(int id) {
		targetRepository.deleteById(id);
	}
	@Override
	public Page<TargetDto> getTargetDtoPagingByCourseId(
			Pageable pageable,
			int courseId
	){
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		Page<TargetDto> targetDtoPage;
		if (targetRepository.count() < startItem) {
			targetDtoPage = new PageImpl<TargetDto>(
					Collections.emptyList(),
					PageRequest.of(currentPage, pageSize),
					targetRepository.count()
			);
		} else {
			targetDtoPage = targetRepository.getTargetDtoPagingByCourseId(
					PageRequest.of(currentPage, pageSize),
					courseId
			);
		}
		return targetDtoPage;
	}
	@Override
	public Page<TargetDto> getTargetDtoResultPagingByCourseId(
			Pageable pageable,
			int courseId,
			String key
	){
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		Page<TargetDto> targetDtoPage;
		if (targetRepository.getTargetDtoResultCountByCourseId(courseId, key) < startItem) {
			targetDtoPage = new PageImpl<TargetDto>(
					Collections.emptyList(),
					PageRequest.of(currentPage, pageSize),
					targetRepository.getTargetDtoResultCountByCourseId(courseId, key)
			);
		} else {
			targetDtoPage = targetRepository.getTargetDtoResultPagingByCourseId(
					PageRequest.of(currentPage, pageSize),
					courseId,
					key
			);
		}
		return targetDtoPage;
	}
}
