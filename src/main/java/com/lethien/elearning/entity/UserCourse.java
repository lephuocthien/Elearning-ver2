/**
 * Dec 14, 2020
 * 9:19:23 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_courses")
public class UserCourse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserCourseId userCourseId;

    // Quan hệ nhiều - 1 với User
    @ManyToOne(cascade = CascadeType.ALL)
    // Chỉ tên khoá ngoại là user_id
    @MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    // Quan hệ nhiều - 1 với Course
    @ManyToOne(cascade = CascadeType.ALL)
    // Chỉ tên khoá ngoại là course_id
    @MapsId("courseId")
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    @JsonIgnore
    private Course course;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "date_create")
    private Date dateCreate;

    /**
     *
     */
    public UserCourse() {
        super();
    }

    /**
     * @param userCourseId
     * @param roleId
     */
    public UserCourse(UserCourseId userCourseId, int roleId, Date dateCreate) {
        super();
        this.userCourseId = userCourseId;
        this.roleId = roleId;
        this.dateCreate = dateCreate;
    }

    /**
     * @return the userCourseId
     */
    public UserCourseId getUserCourseId() {
        return userCourseId;
    }

    /**
     * @param userCourseId the userCourseId to set
     */
    public void setUserCourseId(UserCourseId userCourseId) {
        this.userCourseId = userCourseId;
    }

    /**
     * @return the roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    /**
     * @return the dateCreate
     */
    public Date getDateCreate() {
        return dateCreate;
    }

    /**
     * @param dateCreate the dateCreate to set
     */
    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }
    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @param course the course to set
     */
    public void setCourse(Course course) {
        this.course = course;
    }
}
