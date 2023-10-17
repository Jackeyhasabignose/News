package com.example.News.vo;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class newsRequest {
	
	private String title;                   // タイトル
	private String content;                 // コンテンツ
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime publicTime;      // 公開日時
	private Integer newsId;                // ニュースID
	private List<Integer> newsIds;         // ニュースIDのリスト
	private String status;                 // ステータス
	private String categoryName;           // カテゴリ名
	private String parentCategoryName;     // 親カテゴリ名
	private String subTitle;               // サブタイトル

	
	
	
	
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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
	public Integer getNewsId() {
		return newsId;
	}
	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	public List<Integer> getNewsIds() {
		return newsIds;
	}
	public void setNewsIds(List<Integer> newsIds) {
		this.newsIds = newsIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getParentCategoryName() {
		return parentCategoryName;
	}
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
	public newsRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isIsPreviewVisible() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	

	

}
