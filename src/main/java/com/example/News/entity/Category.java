package com.example.News.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// カテゴリID
	@Column(name = "category_id")
	private Integer categoryId;

	// 親カテゴリID
	@Column(name = "parent_category_id")
	private Integer parentCategoryId;

	// カテゴリ名
	@Column(name = "category_name")
	private String categoryName;

	// ニュース数
	@Column(name = "news_count")
	private Integer newsCount;


	
	
	public Integer getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
	    this.categoryId = categoryId; // 将参数值赋给实体的属性
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
	    this.categoryName = categoryName; // 将参数值赋给实体的属性
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setParentCategory(ParentCategory parentCategory) {
		// TODO Auto-generated method stub
		
	}
	
	

}
