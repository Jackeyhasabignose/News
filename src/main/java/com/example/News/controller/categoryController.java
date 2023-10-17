package com.example.News.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.News.entity.Category;
import com.example.News.service.ifs.categoryService;
import com.example.News.vo.categoryRequest;
import com.example.News.vo.categoryResponse;
import com.example.News.vo.newsResponse;
import com.example.News.vo.parentCategoryRequest;

@CrossOrigin
@RestController
public class categoryController {

	@Autowired
	public categoryService categoryService;

	// カテゴリを追加
	@PostMapping(value = "Add_category")
	public categoryResponse Addcategory(@RequestBody categoryRequest request) {
		return categoryService.Addcategory(request);
	}

	// カテゴリを編集
	@PostMapping(value = "Alter_category")
	public categoryResponse Altercategory(@RequestBody categoryRequest request) {
		return categoryService.Altercategory(request);
	}

	// カテゴリを削除
	@PostMapping(value = "Delete_category")
	public categoryResponse Deletecategory(@RequestBody categoryRequest request) {
		return categoryService.Deletecategory(request);
	}

	// ニュース数を含むすべてのカテゴリを取得
	@GetMapping(value = "get_all_categories_with_news_count")
	public List<Category> getAllCategoriesWithNewsCount() {
		return categoryService.getAllCategoriesWithNewsCount();
	}

	// カテゴリ名に基づいてニュースを検索
	@PostMapping(value = "Find_news_by_category_name")
	public List<newsResponse> FindNewsByCategoryName(@RequestBody categoryRequest request) {
		return categoryService.FindNewsByCategoryName(request);
	}

}
