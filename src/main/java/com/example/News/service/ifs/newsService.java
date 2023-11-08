package com.example.News.service.ifs;


import java.util.List;


import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;

public interface newsService {
	// ニュースを追加
	public newsResponse AddNews(newsRequest request);

	// すべてのニュースを検索
	public List<newsResponse> FindAllNews();

	// IDでニュースを検索
	public newsResponse FindNewsById(newsRequest request);

	// IDでニュースを編集
	public newsResponse AlterNews(newsRequest request);

	// ニュースを削除
	public newsResponse DeleteNews(newsRequest request);

	// 親カテゴリIDによるニュースのカウントを取得
	public int getNewsCountByParentCategoryId(int parentCategoryId);

	// カテゴリIDによるニュースのカウントを取得
	public int getNewsCountByCategoryId(Integer categoryId);
	
	


	
	
	
	
}
