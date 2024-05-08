/**
 * Dec 17, 2020
 * 1:35:08 AM
 * @author LeThien
 */
package com.lethien.elearning.service.implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lethien.elearning.dto.UserCourseDto;
import com.lethien.elearning.entity.UserCourse;
import com.lethien.elearning.entity.UserCourseId;
import com.lethien.elearning.repository.UserCourseRepository;
import com.lethien.elearning.service.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {
	private final UserCourseRepository userCourseRepository;
	public UserCourseServiceImpl(UserCourseRepository userCourseRepository) {
		this.userCourseRepository = userCourseRepository;
	}
	@Override
	public List<UserCourseDto> getAll(){
		List<UserCourse> userCourses = userCourseRepository.findAll();
		List<UserCourseDto> dtos = new ArrayList<UserCourseDto>();
		for(UserCourse userCourse : userCourses) {
			UserCourseDto dto = new UserCourseDto();
			dto.setUserId(userCourse.getUserCourseId().getUserId());
			dto.setCourseId(userCourse.getUserCourseId().getCourseId());
			dto.setRoleId(userCourse.getRoleId());
			dtos.add(dto);
		}
		return dtos;
	}
	@Override
	public UserCourseDto getById(UserCourseId userCourseId) {
		UserCourse userCourse = userCourseRepository.findById(userCourseId).orElse(null);
		UserCourseDto dto = new UserCourseDto();
		if(userCourse != null){
			dto.setUserId(userCourse.getUserCourseId().getUserId());
			dto.setCourseId(userCourse.getUserCourseId().getCourseId());
			dto.setRoleId(userCourse.getRoleId());
		}
		return dto;
	}
	@Override
	public void save(UserCourseDto dto) {
		userCourseRepository.saveUserCourse(
				dto.getUserId(),
				dto.getCourseId(),
				dto.getRoleId()
		);
	}
}
