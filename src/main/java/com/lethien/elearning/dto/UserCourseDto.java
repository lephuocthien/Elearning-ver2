/**
 * Dec 15, 2020
 * 5:55:42 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.dto;

import java.util.Date;

public class UserCourseDto {
    private int userId;
    private String userName;
    private int courseId;
    private String courseTitle;
    private int roleId;
    private String roleName;
    private Date dateCreate;

    /**
     *
     */
    public UserCourseDto() {
        super();
    }

    /**
     * @param userId
     * @param userName
     * @param courseId
     * @param courseTitle
     * @param roleId
     * @param roleName
     * @param dateCreate
     */
    public UserCourseDto(
            int userId,
            String userName,
            int courseId,
            String courseTitle,
            int roleId,
            String roleName,
            Date dateCreate
    ) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.roleId = roleId;
        this.roleName = roleName;
        this.dateCreate = dateCreate;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * @param courseId the courseId to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * @return the courseTitle
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * @param courseTitle the courseTitle to set
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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
     * @return the roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName the roleName to set
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
