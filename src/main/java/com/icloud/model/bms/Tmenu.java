package com.icloud.model.bms;

import com.icloud.model.BaseModel;

public class Tmenu extends BaseModel{
    private String id;

    private String menuName;

    private String menuUrl;

    private String parentId;

    private Integer sortNum;
    
    private Boolean isHas = false;
    
    private Tmenu parent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

	public Boolean getIsHas() {
		return isHas;
	}

	public void setIsHas(Boolean isHas) {
		this.isHas = isHas;
	}

	public Tmenu getParent() {
		return parent;
	}

	public void setParent(Tmenu parent) {
		this.parent = parent;
	}
	
    
}