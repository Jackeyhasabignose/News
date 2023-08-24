package com.example.News.service.ifs;

import java.util.List;

import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;

public interface newsService {
	//新增消息
	public newsResponse AddNews(newsRequest request);
	//找所有消息
	public List<newsResponse> FindAllNews();
	//藉由id找消息
	public newsResponse FindNewsById(newsRequest request);
	//藉由id修改消息
	public newsResponse AlterNews(newsRequest request);
	//刪除消息
	public newsResponse DeleteNews(newsRequest request);

}
