package com.example.News.vo;

import java.util.List;

import com.example.News.entity.Category;
import com.example.News.entity.News;

public class categoryResponse {
	
	private String message;  // メッセージ


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCategory(Category savedCategory) {
		// TODO Auto-generated method stub
		
	}

	public void setCategories(List<Category> categories) {
		// TODO Auto-generated method stub
		
	}

	public void setNewsList(List<News> newsList) {
		// TODO Auto-generated method stub
		
	}
	
	

}
