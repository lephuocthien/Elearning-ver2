/**
 * Dec 18, 2020
 * 6:21:19 PM
 * @author LeThien
 */
package com.lethien.elearning.service;

import java.util.List;

import com.lethien.elearning.dto.VideoDto;

public interface VideoService {
	List<VideoDto> getAll();
	VideoDto getById(int id);
	void save(VideoDto dto);
	void edit(VideoDto dto);
	void remove(int id);
	List<VideoDto> getAllVideoByCourseId(int courseId);
}
