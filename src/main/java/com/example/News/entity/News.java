package com.example.News.entity;


import java.time.LocalDateTime;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "news")
public class News {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// ニュースID
	@Column(name = "news_id")
	private Integer newsId;

	// ニュースサブタイトル
	@Column(name = "sub_title")
	private String subTitle;

	// ニュースタイトル
	@Column(name = "title")
	private String title;

	// ニュースコンテンツ
	@Column(name = "content")
	private String content;

	// ニュース発表時間
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@Column(name = "public_time")
	private LocalDateTime publicTime;

	// ニュースの変更時間
	@Column(name = "alter_time")
	private LocalDateTime alterTime;

	// 親カテゴリのID
	@Column(name = "parent_category_id")
	private Integer parentCategoryId;

	// カテゴリID
	@Column(name = "category_id")
	private Integer categoryId;

	// ステータス
	@Column(name = "status")
	private String status;

	
	
	
	
	public LocalDateTime getAlterTime() {
		return alterTime;
	}
	public void setAlterTime(LocalDateTime alterTime) {
		this.alterTime = alterTime;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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
	public LocalDateTime getPublicTime() {
		return publicTime;
	}
	public void setPublicTime(LocalDateTime publicTime) {
		this.publicTime = publicTime;
	}
	public Integer getParentCategoryId() {
		return parentCategoryId;
	}
	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	
	

}
