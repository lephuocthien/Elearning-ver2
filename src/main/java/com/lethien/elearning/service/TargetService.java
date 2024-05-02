/**
 * Dec 18, 2020
 * 5:18:21 PM
 * @author LeThien
 */
package com.lethien.elearning.service;

import java.util.List;

import com.lethien.elearning.dto.TargetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TargetService {
	List<TargetDto> getAll();
	TargetDto getById(int id);
	void save (TargetDto dto);
	void edit (TargetDto dto);
	void remove (int id);
	List<TargetDto> getAllTargetByCourseId (int courseId);
	Page<TargetDto> getTargetDtoPagingByCourseId(Pageable pageable, int courseId);
}
