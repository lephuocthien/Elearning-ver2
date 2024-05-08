/**
 * Dec 4, 2020
 * 4:41:52 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;

    @Column(name = "time_count")
    private int timeCount;

    @Column(name = "course_id")
    private int courseId;

    //Quan hệ nhiều -1 với Course
    @ManyToOne
    //Chỉ tên khoá ngoại là course_id
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    @JsonIgnore
    private Course course;

    /**
     *
     */
    public Video() {
        super();
    }

    /**
     * @param id
     * @param title
     * @param url
     * @param timeCount
     * @param courseId
     */
    public Video(int id, String title, String url, int timeCount, int courseId) {
        super();
        this.id = id;
        this.title = title;
        this.url = url;
        this.timeCount = timeCount;
        this.courseId = courseId;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the timeCount
     */
    public int getTimeCount() {
        return timeCount;
    }

    /**
     * @param timeCount the timeCount to set
     */
    public void setTimeCount(int timeCount) {
        this.timeCount = timeCount;
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
}
