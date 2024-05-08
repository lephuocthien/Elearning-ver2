/**
 * Dec 17, 2020
 * 1:33:30 AM
 * @author LeThien
 */
package com.lethien.elearning.service;

import java.util.List;

import com.lethien.elearning.dto.UserCourseDto;
import com.lethien.elearning.entity.UserCourseId;

public interface UserCourseService {
	List<UserCourseDto> getAll();
	UserCourseDto getById(UserCourseId userCourseId);
	void save(UserCourseDto dto);
	void remove(int userId, int courseId);
}
