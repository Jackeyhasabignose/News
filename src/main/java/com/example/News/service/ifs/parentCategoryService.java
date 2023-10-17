package com.example.News.service.ifs;

import java.util.List;

import com.example.News.entity.ParentCategory;
import com.example.News.vo.newsResponse;
import com.example.News.vo.parentCategoryRequest;
import com.example.News.vo.parentCategoryResponse;

public interface parentCategoryService {

	// 親カテゴリを追加
	public parentCategoryResponse AddparentCategory(parentCategoryRequest request);

	// 親カテゴリを編集
	public parentCategoryResponse AlterparentCategory(parentCategoryRequest request);

	// 親カテゴリを削除
	public parentCategoryResponse DeleteparentCategory(parentCategoryRequest request);

	// ニュース数を含むすべての親カテゴリを取得
	public List<ParentCategory> getAllParentCategoriesWithNewsCount();

	// 親カテゴリ名による新聞を取得するメソッド
	public List<newsResponse> FindNewsByParentCategoryName(parentCategoryRequest request);

}
