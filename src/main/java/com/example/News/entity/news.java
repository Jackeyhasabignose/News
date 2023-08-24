package com.example.News.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class news {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//新聞id
	@Column(name = "news_id")
	private Integer newsId;
	//新聞標題
	@Column(name = "title")
	private String title;
	//新聞內容
	@Column(name = "content")
	private String content;
	//新聞發布時間
	@Column(name = "public_time")
	private LocalDate publicTime;
	//新聞類別
	@Column(name = "category")
	private String category; 

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public news() {
		super();
		// TODO Auto-generated constructor stub
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

}
