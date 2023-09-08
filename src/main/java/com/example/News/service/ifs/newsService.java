package com.example.News.service.ifs;

import java.util.List;

import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;

public interface newsService {
	//メッセージを追加
	public newsResponse AddNews(newsRequest request);
	//すべてのメッセージを検索
	public List<newsResponse> FindAllNews();
	//IDでメッセージを検索
	public newsResponse FindNewsById(newsRequest request);
	//IDでメッセージを編集
	public newsResponse AlterNews(newsRequest request);
	//メッセージを削除
	public newsResponse DeleteNews(newsRequest request);

}
