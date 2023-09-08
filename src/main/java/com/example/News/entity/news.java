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
	// ニュースid
	@Column(name = "news_id")
	private Integer newsId;
	// ニュースタイトル
	@Column(name = "title")
	private String title;
	// ニュースコンテンツ
	@Column(name = "content")
	private String content;
	// ニュース発表時間
	@Column(name = "public_time")
	private LocalDate publicTime;
	// カテゴリ
	@Column(name = "parent_category")
	private String parentCategory;
	// サブカテゴリ
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

//	public news(String string, String string2, LocalDate now, String string3, String string4) {
//		// TODO Auto-generated constructor stub
//	}
	public news(String title, String content, LocalDate publicTime, String category, String parentCategory) {
	    this.title = title;
	    this.content = content;
	    this.publicTime = publicTime;
	    this.category = category; // 设置 category 属性
	    this.parentCategory = parentCategory;
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

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}
	

}
