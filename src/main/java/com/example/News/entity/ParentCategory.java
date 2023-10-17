package com.example.News.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "parent_category")
public class ParentCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 親カテゴリID
	@Column(name = "parent_category_id")
	private Integer parentCategoryId;

	// 親カテゴリ名
	@Column(name = "parent_category_name")
	private String parentCategoryName;

	// ニュース数
	@Column(name = "news_count")
	private Integer newsCount;
	
	

	
	public Integer getNewsCount() {
		return newsCount;
	}

	public void setNewsCount(Integer newsCount) {
		this.newsCount = newsCount;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}

	public ParentCategory() {
		super();
		// TODO Auto-generated constructor stub
	}



	
	
	

}
