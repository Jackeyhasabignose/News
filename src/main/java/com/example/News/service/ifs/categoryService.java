package com.example.News.service.ifs;

import java.util.List;

import com.example.News.entity.Category;

import com.example.News.vo.categoryRequest;
import com.example.News.vo.categoryResponse;
import com.example.News.vo.newsResponse;


public interface categoryService {

	// カテゴリを追加
	public categoryResponse Addcategory(categoryRequest request);

	// カテゴリを編集
	public categoryResponse Altercategory(categoryRequest request);

	// カテゴリを削除
	public categoryResponse Deletecategory(categoryRequest request);

	// ニュース数を含むすべてのカテゴリを取得
	public List<Category> getAllCategoriesWithNewsCount();

	// カテゴリ名による新聞を取得するメソッド
	public List<newsResponse> FindNewsByCategoryName(categoryRequest request);

}
