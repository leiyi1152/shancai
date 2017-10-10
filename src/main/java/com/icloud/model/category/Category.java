package com.icloud.model.category;

public class Category {
    private String id;

    private String categoryName;

    private String parentId;
    
    private String deletStatus;
    
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

	public String getDeletStatus() {
		return deletStatus;
	}

	public void setDeletStatus(String deletStatus) {
		this.deletStatus = deletStatus;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
    
    
}