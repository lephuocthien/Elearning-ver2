/**
 * Dec 4, 2020
 * 4:40:35 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id //Cột khoá chính
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tăng tự động
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    @Lob
    private byte[] avatar;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "role_id")
    private int roleId;

    // Quan hệ nhiều - 1 với Role
    @ManyToOne
    // Chỉ tên khoá ngoại là role_id
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    @JsonIgnore
    private Role role;

    // Quan hệ 1 - nhiều với UserCourse
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserCourse> userCourses;

    /**
     *
     */
    public User() {
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
     */
    public User(int id, String email, String fullname, String password, byte[] avatar, String phone, String address,
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
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * @return the userCourses
     */
    public List<UserCourse> getUserCourses() {
        return userCourses;
    }

    /**
     * @param userCourses the userCourses to set
     */
    public void setUserCourses(List<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }
}
