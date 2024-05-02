/**
 * Dec 15, 2020
 * 5:55:06 PM
 * @author LeThien
 */
package com.lethien.elearning.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CourseDto {

	private int id;
	private String title;
	private String image;
	private int leturesCount;
	private int hourCount;
	private int viewCount;
	private BigDecimal price;
	private int discount;
	private BigDecimal promotionPrice;
	private String description;
	private String content;
	private int categoryId;
	private Date lastUpdate;
	private String categoryTitle;
	private List<TargetDto> targets;
	private List<VideoDto> videos;

	/**
	 * 
	 */
	public CourseDto() {
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
	 * @param categoryTitle
	 */
	public CourseDto(int id, String title, String image, int leturesCount, int hourCount, int viewCount,
			BigDecimal price, int discount, BigDecimal promotionPrice, String description, String content,
			 int categoryId, Date lastUpdate, String categoryTitle) {
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
		this.categoryTitle = categoryTitle;
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
	public CourseDto(int id, String title, String image, int leturesCount, int hourCount, int viewCount,
					 BigDecimal price, int discount, BigDecimal promotionPrice, String description, String content,
					 int categoryId, Date lastUpdate) {
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
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
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
	 * @return the categoryName
	 */
	public String getCategoryTitle() {
		return categoryTitle;
	}

	/**
	 * @param categoryTitle the categoryName to set
	 */
	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	/**
	 * @return the targets
	 */
	public List<TargetDto> getTargets() {
		return targets;
	}

	/**
	 * @param targets the targets to set
	 */
	public void setTargets(List<TargetDto> targets) {
		this.targets = targets;
	}

	/**
	 * @return the videos
	 */
	public List<VideoDto> getVideos() {
		return videos;
	}

	/**
	 * @param videos the videos to set
	 */
	public void setVideos(List<VideoDto> videos) {
		this.videos = videos;
	}

	
}
