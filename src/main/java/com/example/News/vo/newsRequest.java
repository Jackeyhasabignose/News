package com.example.News.vo;

import java.time.LocalDate;

public class newsRequest {
	private String title;
	private String content;
	private LocalDate publicTime;
	private Integer newsId;
	private String category;
	private String parentCategory;
	
	
	
	
	
	public newsRequest(String title, String content, LocalDate publicTime, String category, String parentCategory) {
		super();
		this.title = title;
		this.content = content;
		this.publicTime = publicTime;
		this.category = category;
		this.parentCategory = parentCategory;
	}
	public String getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public LocalDate getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(LocalDate publicTime) {
		this.publicTime = publicTime;
	}
	
	
	public newsRequest(String string, String string2, String string3, String string4) {
		super();
		// TODO Auto-generated constructor stub
	}
	public newsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
