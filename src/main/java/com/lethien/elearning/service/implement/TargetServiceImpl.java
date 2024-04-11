/**
 * Dec 18, 2020
 * 6:28:47 PM
 * @author LeThien
 */
package com.lethien.elearning.service.implement;

import java.util.ArrayList;
import java.util.List;

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

}
