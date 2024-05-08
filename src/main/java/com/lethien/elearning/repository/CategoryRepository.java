/**
 * Dec 15, 2020
 * 9:56:27 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.repository;

import com.lethien.elearning.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lethien.elearning.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT new com.lethien.elearning.dto.CategoryDto"
            + "(id, "
            + "title, "
            + "icon) "
            + "FROM Category")
    Page<CategoryDto> getCategoryDtoPaging(Pageable pageable);

    @Query("SELECT new com.lethien.elearning.dto.CategoryDto" +
            "(id, " +
            "title, " +
            "icon) " +
            "FROM Category " +
            "WHERE title LIKE :key")
    Page<CategoryDto> getCategoryDtoResultPaging(
            Pageable pageable,
            @Param("key") String key
    );

    @Query("SELECT COUNT(*) " +
            "FROM Category " +
            "WHERE title LIKE :key")
    int getCategoryDtoResultCount(@Param("key") String key);
}
