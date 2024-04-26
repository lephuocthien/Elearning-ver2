/**
 * Dec 18, 2020
 * 5:18:21 PM
 * @author LeThien
 */
package com.lethien.elearning.service;

import java.util.List;

import com.lethien.elearning.dto.TargetDto;

public interface TargetService {
	List<TargetDto> getAll();
	TargetDto getById(int id);
	void save (TargetDto dto);
	void edit (TargetDto dto);
	void remove (int id);
	List<TargetDto> getAllTargetByCourseId (int courseId);
}
