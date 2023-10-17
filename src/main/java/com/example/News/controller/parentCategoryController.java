package com.example.News.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.News.entity.ParentCategory;
import com.example.News.service.ifs.parentCategoryService;

import com.example.News.vo.newsResponse;
import com.example.News.vo.parentCategoryRequest;
import com.example.News.vo.parentCategoryResponse;

@CrossOrigin
@RestController
public class parentCategoryController {

	@Autowired
	public parentCategoryService parentCategoryService;

	// 親カテゴリを追加
	@PostMapping(value = "Add_parent_category")
	public parentCategoryResponse AddparentCategory(@RequestBody parentCategoryRequest request) {
		return parentCategoryService.AddparentCategory(request);
	}

	// 親カテゴリを編集
	@PostMapping(value = "Alter_parent_category")
	public parentCategoryResponse AlterparentCategory(@RequestBody parentCategoryRequest request) {
		return parentCategoryService.AlterparentCategory(request);
	}

	// 親カテゴリを削除
	@PostMapping(value = "Delete_parent_category")
	public parentCategoryResponse DeleteparentCategory(@RequestBody parentCategoryRequest request) {
		return parentCategoryService.DeleteparentCategory(request);
	}

	// ニュース数を含むすべての親カテゴリを取得
	@GetMapping(value = "get_all_parent_categories_with_news_count")
	public List<ParentCategory> getAllParentCategoriesWithNewsCount() {
		return parentCategoryService.getAllParentCategoriesWithNewsCount();
	}

	// 親カテゴリ名に基づいてニュースを検索
	@PostMapping(value = "Find_news_by_parent_categoryName")
	public List<newsResponse> FindNewsByParentCategoryName(@RequestBody parentCategoryRequest request) {
	    return parentCategoryService.FindNewsByParentCategoryName(request);
	}


}
