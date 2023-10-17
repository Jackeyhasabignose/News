package com.example.News.vo;


import java.time.LocalDateTime;

import com.example.Newsl.constants.Msg;

public class newsResponse {
	
	
	
	
	private String message;                   // メッセージ
	private String title;                     // タイトル
	private String content;                   // コンテンツ
	private LocalDateTime publicTime;        // 公開日時
	private Integer newsId;                  // ニュースID
	private String category;                 // カテゴリ
	private String parentCategory;           // 親カテゴリ
	private String status;                   // ステータス
	private String parentCategoryName;       // 親カテゴリ名
	private String categoryName;             // カテゴリ名
	private Integer parentCategoryId;        // 親カテゴリID
	private Integer categoryId;              // カテゴリID
	private String subTitle;                 // サブタイトル
	private String formattedDate;
	
	

    
    
    
	
	
	
	
	

	

	public String getFormattedDate() {
		return formattedDate;
	}

	

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
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

	public String getStatus() {
		return status;
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

	public LocalDateTime getPublicTime() {
		return publicTime;
	}

	public void setPublicTime(LocalDateTime publicTime) {
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

	public void setMessage(Msg empty) {
		// TODO Auto-generated method stub
		
	}

	public void setStatus(String status) {
	    this.status = status;
	}



	public String setFormattedDate(String formattedDate) {
	    this.formattedDate = formattedDate;
	    return formattedDate; // 返回设置的日期值
	}



	

	

}
