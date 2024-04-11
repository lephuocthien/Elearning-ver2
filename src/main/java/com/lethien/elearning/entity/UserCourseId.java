/**
 * Dec 15, 2020
 * 3:00:03 AM
 * @author LeThien
 */
package com.lethien.elearning.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserCourseId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Column(name = "user_id")
	private int userId;

	
	@Column(name = "course_id")
	private int courseId;


	/**
	 * 
	 */
	public UserCourseId() {
		super();
	}


	/**
	 * @param userId
	 * @param courseId
	 */
	public UserCourseId(int userId, int courseId) {
		super();
		this.userId = userId;
		this.courseId = courseId;
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
