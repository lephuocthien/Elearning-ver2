/**
 * Dec 15, 2020
 * 9:33:23 PM
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

import com.lethien.elearning.dto.RoleDto;
import com.lethien.elearning.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    @Query("SELECT new com.lethien.elearning.dto.RoleDto" +
            "(id, " +
            "name, " +
            "description) " +
            "FROM Role " +
            "WHERE name <> 'ROLE_ADMIN'")
    List<RoleDto> findAllNotAdmin();

    @Query("SELECT new com.lethien.elearning.dto.RoleDto" +
            "(id, " +
            "name, " +
            "description) " +
            "FROM Role")
    Page<RoleDto> getRoleDtoPaging(Pageable pageable);

    @Query("SELECT new com.lethien.elearning.dto.RoleDto" +
            "(id, " +
            "name, " +
            "description) " +
            "FROM Role " +
            "WHERE name LIKE :key")
    Page<RoleDto> getRoleDtoResultPaging(
            Pageable pageable,
            @Param("key") String key
    );

    @Query("SELECT COUNT(*) " +
            "FROM Role " +
            "WHERE name LIKE :key")
    int getRoleDtoResultCount(@Param("key") String key);
}
