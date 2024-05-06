/**
 * Dec 4, 2020
 * 4:41:08 PM
 * @author LeThien
 */
package com.lethien.elearning.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "courses")
public class Course implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Lob
	@Column(name = "image")
	private byte[] image;

	@Column(name = "letures_count")
	private int leturesCount;

	@Column(name = "hour_count")
	private int hourCount;

	@Column(name = "view_count")
	private int viewCount;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "discount")
	private int discount;

	@Column(name = "promotion_price")
	private BigDecimal promotionPrice;

	@Column(name = "description")
	private String description;

	@Column(name = "content")
	private String content;

	@Column(name = "category_id")
	private int categoryId;

	@Column(name = "last_update")
	private Date lastUpdate;

	// Quan hệ 1 - nhiều với UserCourse
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<UserCourse> userCourses;
	
	@ManyToOne
	@JoinColumn(name = "category_id", insertable = false, updatable = false)
	@JsonIgnore
	private Category category;

	/**
	 * 
	 */
	public Course() {
		super();
	}

	/**
	 * @param id
	 * @param title
	 * @param image
	 * @param leturesCount
	 * @param hourCount
	 * @param viewCount
	 * @param price
	 * @param discount
	 * @param promotionPrice
	 * @param description
	 * @param content
	 * @param categoryId
	 * @param lastUpdate
	 */
	public Course(int id, String title, byte[] image, int leturesCount, int hourCount, int viewCount, BigDecimal price,
			int discount, BigDecimal promotionPrice, String description, String content, int categoryId, Date lastUpdate) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.leturesCount = leturesCount;
		this.hourCount = hourCount;
		this.viewCount = viewCount;
		this.price = price;
		this.discount = discount;
		this.promotionPrice = promotionPrice;
		this.description = description;
		this.content = content;
		this.categoryId = categoryId;
		this.lastUpdate = lastUpdate;
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
	 * @return the image
	 */
	public byte[] getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	/**
	 * @return the leturesCount
	 */
	public int getLeturesCount() {
		return leturesCount;
	}

	/**
	 * @param leturesCount the leturesCount to set
	 */
	public void setLeturesCount(int leturesCount) {
		this.leturesCount = leturesCount;
	}

	/**
	 * @return the hourCount
	 */
	public int getHourCount() {
		return hourCount;
	}

	/**
	 * @param hourCount the hourCount to set
	 */
	public void setHourCount(int hourCount) {
		this.hourCount = hourCount;
	}

	/**
	 * @return the viewCount
	 */
	public int getViewCount() {
		return viewCount;
	}

	/**
	 * @param viewCount the viewCount to set
	 */
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the discount
	 */
	public int getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(int discount) {
		this.discount = discount;
	}

	/**
	 * @return the promotionPrice
	 */
	public BigDecimal getPromotionPrice() {
		return promotionPrice;
	}

	/**
	 * @param promotionPrice the promotionPrice to set
	 */
	public void setPromotionPrice(BigDecimal promotionPrice) {
		this.promotionPrice = promotionPrice;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the categoryId
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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

//	/**
//	 * @return the targets
//	 */
//	public List<Target> getTargets() {
//		return targets;
//	}
//
//	/**
//	 * @param targets the targets to set
//	 */
//	public void setTargets(List<Target> targets) {
//		this.targets = targets;
//	}
//
//	/**
//	 * @return the videos
//	 */
//	public List<Video> getVideos() {
//		return videos;
//	}
//
//	/**
//	 * @param videos the videos to set
//	 */
//	public void setVideos(List<Video> videos) {
//		this.videos = videos;
//	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	

}
