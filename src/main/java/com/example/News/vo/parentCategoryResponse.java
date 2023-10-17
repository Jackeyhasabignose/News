package com.example.News.vo;

import java.util.List;

import com.example.News.entity.News;
import com.example.News.entity.ParentCategory;

public class parentCategoryResponse {
	
	private String message;                   // メッセージ
	private ParentCategory parentCategory;   // 親カテゴリ
	private List<newsResponse> newsList;     // ニュースリスト

    
    

	public ParentCategory getParentCategory() {
		return parentCategory;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setParentCategory(ParentCategory savedParentCategory) {
	    this.parentCategory = savedParentCategory;
	}


	 public void setNewsList(List<newsResponse> newsList) {
	        this.newsList = newsList;
	    }

	
	
	

}
