package com.icloud.model.product;

import com.icloud.model.category.Category;

public class Product {
	private String id;
	private String productName;
	private String productDesc;
	private String productPics;
	private String categoryId;
	private Category category;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductPics() {
		return productPics;
	}
	public void setProductPics(String productPics) {
		this.productPics = productPics;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	

}
