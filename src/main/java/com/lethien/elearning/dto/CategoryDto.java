/**
 * Dec 15, 2020
 * 5:55:18 PM
 * @author LeThien
 */
package com.lethien.elearning.dto;

public class CategoryDto {

	private int id;
	private String title;
	private String icon;
	/**
	 * 
	 */
	public CategoryDto() {
		super();
	}
	/**
	 * @param id
	 * @param title
	 * @param icon
	 */
	public CategoryDto(int id, String title, String icon) {
		super();
		this.id = id;
		this.title = title;
		this.icon = icon;
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
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
