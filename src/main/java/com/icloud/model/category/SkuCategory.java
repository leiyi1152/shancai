package com.icloud.model.category;

/**
 * sku品类关联表
 * @author leiyi
 *
 */
public class SkuCategory {
    private String userId;
    private String categoryId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }
}