package com.example.News.vo;

import java.util.List;

public class parentCategoryRequest {
	
	
	
	private String parentCategoryName;      // 親カテゴリ名
	private Integer parentCategoryId;       // 親カテゴリID
	private List<Integer> parentCategoryIds; // 親カテゴリIDのリスト

	
	

	public List<Integer> getParentCategoryIds() {
		return parentCategoryIds;
	}

	public void setParentCategoryIds(List<Integer> parentCategoryIds) {
		this.parentCategoryIds = parentCategoryIds;
	}

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
	
	
	
	

}
