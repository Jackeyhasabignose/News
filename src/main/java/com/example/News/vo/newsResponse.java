package com.example.News.vo;

import java.time.LocalDate;

public class newsResponse {
	
	private String message;
	private String title;
	private String content;
	private LocalDate publicTime;
	private Integer newsId;
	private String category;
	

	
	
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

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public newsResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public newsResponse(String string) {
		// TODO Auto-generated constructor stub
	}
	

}
