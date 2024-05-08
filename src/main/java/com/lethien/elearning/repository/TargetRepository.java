/**
 * Dec 15, 2020
 * 9:36:02 PM
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

import com.lethien.elearning.dto.TargetDto;
import com.lethien.elearning.entity.Target;

@Repository
public interface TargetRepository extends JpaRepository<Target, Integer> {

    @Query("SELECT new com.lethien.elearning.dto.TargetDto" +
            "(t.id, " +
            "t.title, " +
            "t.courseId, " +
            "c.title) " +
            "FROM Course c JOIN Target t " +
            "ON c.id = t.courseId " +
            "WHERE t.courseId = :courseId")
    List<TargetDto> getAllTargetByCourseId(@Param("courseId") int courseId);

    @Query("SELECT new com.lethien.elearning.dto.TargetDto" +
            "(t.id, " +
            "t.title, " +
            "t.courseId, " +
            "c.title) " +
            "FROM Course c JOIN Target t " +
            "ON c.id = t.courseId " +
            "WHERE t.courseId = :courseId")
    Page<TargetDto> getTargetDtoPagingByCourseId(
            Pageable pageable,
            @Param("courseId") int courseId
    );

    @Query("SELECT new com.lethien.elearning.dto.TargetDto" +
            "(t.id, " +
            "t.title, " +
            "t.courseId, " +
            "c.title) " +
            "FROM Course c JOIN Target t " +
            "ON c.id = t.courseId " +
            "WHERE t.courseId = :courseId " +
            "AND t.title LIKE :key")
    Page<TargetDto> getTargetDtoResultPagingByCourseId(
            Pageable pageable,
            @Param("courseId") int courseId,
            @Param("key") String key
    );

    @Query("SELECT COUNT(*) " +
            "FROM Course c JOIN Target t " +
            "ON c.id = t.courseId " +
            "WHERE t.courseId = :courseId " +
            "AND t.title LIKE :key")
    int getTargetDtoResultCountByCourseId(
            @Param("courseId") int courseId,
            @Param("key") String key
    );
}

