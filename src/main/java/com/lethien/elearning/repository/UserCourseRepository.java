/**
 * Dec 15, 2020
 * 10:20:58 PM
 * @author LeThien
 */
package com.lethien.elearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lethien.elearning.entity.UserCourse;
import com.lethien.elearning.entity.UserCourseId;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
	@Transactional
	@Modifying
	@Query(value = "insert into user_courses (user_id, course_id, role_id) values (:userId, :courseId, :roleId)", nativeQuery = true)
	void saveUserCourse(@Param("userId") int userId, @Param("courseId") int courseId, @Param("roleId") int roleId);
}
