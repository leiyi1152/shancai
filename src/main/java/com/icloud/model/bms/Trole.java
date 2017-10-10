package com.icloud.model.bms;

import com.icloud.model.BaseModel;

public class Trole extends BaseModel {
    private String id;

    private String roleName;
    
    private Boolean isHas = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

	public Boolean getIsHas() {
		return isHas;
	}

	public void setIsHas(Boolean isHas) {
		this.isHas = isHas;
	}
    
    
}