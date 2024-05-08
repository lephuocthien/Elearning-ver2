/**
 * Dec 15, 2020
 * 5:54:56 PM
 *
 * @author LeThien
 */
package com.lethien.elearning.dto;

public class TargetDto {
    private int id;
    private String title;
    private int courseId;
    private String courseTitle;

    /**
     *
     */
    public TargetDto() {
        super();
    }

    /**
     * @param id
     * @param title
     * @param courseId
     * @param courseTitle
     */
    public TargetDto(int id, String title, int courseId, String courseTitle) {
        super();
        this.id = id;
        this.title = title;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
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
}
