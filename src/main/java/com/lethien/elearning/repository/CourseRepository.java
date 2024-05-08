/**
 * Dec 15, 2020
 * 9:35:17 PM
 *
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

import com.lethien.elearning.dto.CourseDto;
import com.lethien.elearning.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT new com.lethien.elearning.dto.CourseDto" +
            "(c.id, " +
            "c.title, " +
            "c.image, " +
            "c.leturesCount, " +
            "c.hourCount, " +
            "c.viewCount, " +
            "c.price, " +
            "c.discount, " +
            "c.promotionPrice, " +
            "c.description, " +
            "c.content, " +
            "c.categoryId, " +
            "c.lastUpdate, " +
            "ca.title) " +
            "FROM Course c JOIN Category ca " +
            "ON c.categoryId = ca.id")
    List<CourseDto> findAllDtoCourse();

    @Query("SELECT new com.lethien.elearning.dto.CourseDto" +
            "(c.id, " +
            "c.title, " +
            "c.image, " +
            "c.leturesCount, " +
            "c.hourCount, " +
            "c.viewCount, " +
            "c.price, " +
            "c.discount, " +
            "c.promotionPrice, " +
            "c.description, " +
            "c.content, " +
            "c.categoryId, " +
            "c.lastUpdate, " +
            "ca.title) " +
            "FROM Course c " +
            "JOIN Category ca " +
            "ON c.categoryId = ca.id " +
            "JOIN UserCourse uc " +
            "ON c.id = uc.userCourseId.courseId " +
            "WHERE uc.userCourseId.userId = :userId")
    List<CourseDto> findAllDtoCourseByUserId(@Param("userId") int userId);

    @Query("SELECT new com.lethien.elearning.dto.CourseDto" +
            "(c.id, " +
            "c.title, " +
            "c.image, " +
            "c.leturesCount, " +
            "c.hourCount, " +
            "c.viewCount, " +
            "c.price, " +
            "c.discount, " +
            "c.promotionPrice, " +
            "c.description, " +
            "c.content, " +
            "c.categoryId, " +
            "c.lastUpdate, " +
            "ca.title) " +
            "FROM Course c JOIN Category ca " +
            "ON c.categoryId = ca.id " +
            "WHERE c.title LIKE :key")
    List<CourseDto> findAllDtoCourseByTitle(@Param("key") String key);

    @Query("SELECT new com.lethien.elearning.dto.CourseDto" +
            "(c.id, " +
            "c.title, " +
            "c.image, " +
            "c.leturesCount, " +
            "c.hourCount, " +
            "c.viewCount, " +
            "c.price, " +
            "c.discount, " +
            "c.promotionPrice, " +
            "c.description, " +
            "c.content, " +
            "c.categoryId, " +
            "c.lastUpdate, " +
            "ca.title) " +
            "FROM Course c JOIN Category ca " +
            "ON c.categoryId = ca.id " +
            "WHERE c.id = :id")
    CourseDto findDtoCourseById(@Param("id") int id);

    @Query("SELECT new com.lethien.elearning.dto.CourseDto" +
            "(c.id, " +
            "c.title, " +
            "c.image, " +
            "c.leturesCount, " +
            "c.hourCount, " +
            "c.viewCount, " +
            "c.price, " +
            "c.discount, " +
            "c.promotionPrice, " +
            "c.description, " +
            "c.content, " +
            "c.categoryId, " +
            "c.lastUpdate) " +
            "FROM Course c")
    Page<CourseDto> getCourseDtoPaging(Pageable pageable);

    @Query("SELECT new com.lethien.elearning.dto.CourseDto" +
            "(c.id, " +
            "c.title, " +
            "c.image, " +
            "c.leturesCount, " +
            "c.hourCount, " +
            "c.viewCount, " +
            "c.price, " +
            "c.discount, " +
            "c.promotionPrice, " +
            "c.description, " +
            "c.content, " +
            "c.categoryId, " +
            "c.lastUpdate) " +
            "FROM Course c " +
            "WHERE c.title LIKE :key")
    Page<CourseDto> getCourseDtoResultPaging(
            Pageable pageable,
            @Param("key") String key
    );

    @Query("SELECT COUNT(*) " +
            "FROM Course " +
            "WHERE title LIKE :key")
    int getCourseDtoResultCount(@Param("key") String key);
}
