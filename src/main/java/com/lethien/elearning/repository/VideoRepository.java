/**
 * Dec 15, 2020
 * 10:20:05 PM
 * @author LeThien
 */
package com.lethien.elearning.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lethien.elearning.dto.VideoDto;
import com.lethien.elearning.entity.Video;
@Repository
public interface VideoRepository extends JpaRepository<Video, Integer>{

	@Query("SELECT new com.lethien.elearning.dto.VideoDto"
			+ "(v.id, "
			+ "v.title, "
			+ "v.url, "
			+ "v.timeCount, "
			+ "v.courseId, "
			+ "c.title) "
			+ "FROM Course c JOIN Video v "
			+ "ON c.id = v.courseId "
			+ "WHERE v.courseId = :courseId")
	List<VideoDto> getAllVideoByCourseId(@Param("courseId") int courseId);

	@Query("SELECT new com.lethien.elearning.dto.VideoDto"
			+ "(v.id, "
			+ "v.title, "
			+ "v.url, "
			+ "v.timeCount, "
			+ "v.courseId, "
			+ "c.title) "
			+ "FROM Course c JOIN Video v "
			+ "ON c.id = v.courseId "
			+ "WHERE v.courseId = :courseId")
	Page<VideoDto> getVideoDtoPagingByCourseId(Pageable pageable, @Param("courseId") int courseId);
}
