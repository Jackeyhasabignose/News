package com.example.News.vo;

import java.util.List;

public class categoryRequest {
	
	private String categoryName;             // カテゴリ名
	private Integer CategoryId;              // カテゴリID
	private List<Integer> categoryIds;       // カテゴリIDのリスト
	private String parentCategoryName;       // 親カテゴリ名

	
	
	
	
	
	public List<Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

	private Integer parentCategoryId;
	

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(Integer categoryId) {
		CategoryId = categoryId;
	}
	
	

}
