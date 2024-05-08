/**
 * Dec 15, 2020
 * 5:53:19 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.dto;

import java.util.List;

public class UserDto {
    private int id;
    private String email;
    private String fullname;
    private String password;
    private byte[] avatar;
    private String phone;
    private String address;
    private int roleId;
    private String roleName;
    private String roleDescription;
    private List<CourseDto> courses;

    /**
     *
     */
    public UserDto() {
        super();
    }

    /**
     * @param id
     * @param email
     * @param fullname
     * @param password
     * @param avatar
     * @param phone
     * @param address
     * @param roleId
     * @param roleName
     * @param roleDescription
     */
    public UserDto(int id, String email, String fullname, String password, byte[] avatar, String phone, String address,
                   int roleId, String roleName, String roleDescription) {
        super();
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public UserDto(int id, String email, String fullname, String password, byte[] avatar, String phone, String address,
                   int roleId, String roleName) {
        super();
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public UserDto(int id, String email, String fullname, String password, byte[] avatar, String phone, String address,
                   int roleId) {
        super();
        this.id = id;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.avatar = avatar;
        this.phone = phone;
        this.address = address;
        this.roleId = roleId;
    }

    public UserDto(String email, String password, String roleName) {
        super();
        this.email = email;
        this.password = password;
        this.roleName = roleName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the avatar
     */
    public byte[] getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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

    /**
     * @return the roleDescription
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    /**
     * @param roleDescription the roleDescription to set
     */
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    /**
     * @return the courses
     */
    public List<CourseDto> getCourses() {
        return courses;
    }

    /**
     * @param courses the courses to set
     */
    public void setCourses(List<CourseDto> courses) {
        this.courses = courses;
    }
}
