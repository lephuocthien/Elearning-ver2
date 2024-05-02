/**
 * Dec 18, 2020
 * 6:24:42 PM
 * @author LeThien
 */
package com.lethien.elearning.service;

import java.util.List;

import com.lethien.elearning.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
	List<CourseDto> getAll();
	List<CourseDto> getAllCourseDto();
	List<CourseDto> getAllCourseDtoByUserId(int id);
	List<CourseDto> getAllCourseDtoByTitle(String key);
	CourseDto getById(int id);
	CourseDto getDtoById(int id);
	void save(CourseDto dto);
	Integer saveGetBackId(CourseDto dto);
	void edit(CourseDto dto);
	void remove(int id);
	Page<CourseDto> getCourseDtoPaging(Pageable pageable);
}
