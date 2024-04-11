/**
 * Dec 15, 2020
 * 5:53:59 PM
 * @author LeThien
 */
package com.lethien.elearning.dto;

public class VideoDto {
	
	private int id;
	private String title;
	private String url;
	private int timeCount;
	private int courseId;
	private String courseTitle;
	/**
	 * 
	 */
	public VideoDto() {
		super();
	}
	/**
	 * @param id
	 * @param title
	 * @param url
	 * @param timeCount
	 * @param courseId
	 * @param courseTitle
	 */
	public VideoDto(int id, String title, String url, int timeCount, int courseId, String courseTitle) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.timeCount = timeCount;
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
