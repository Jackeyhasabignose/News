package com.example.News.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.News.entity.news;
import com.example.News.repository.newsDao;
import com.example.News.service.ifs.newsService;
import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;

@Service
public class newsImpl implements newsService {

	private newsDao newsDao;

	@Autowired
	public newsImpl(newsDao newsDao) {
		this.newsDao = newsDao;

	}

	@Override
	public newsResponse AddNews(newsRequest request) {
	    newsResponse response = new newsResponse();

	    // 防空檢查
	    if (request.getTitle() == null || request.getContent() == null || request.getCategory() == null) {
	        response.setMessage("Title, content, and category cannot be empty.");
	        return response;
	    }

	    // 設置 publicTime 為當前時間
	    LocalDate now = LocalDate.now();
	    request.setPublicTime(now);

	    // 插入數據，包括類別字段
	    newsDao.insertNews(request.getTitle(), request.getContent(), now, request.getCategory());

	    // 回傳訊息

	    response.setMessage("News added successfully.");
	    return response;
	}


	@Override
	public List<newsResponse> FindAllNews() {
	    List<news> newsList = newsDao.findAll();//尋找news的多張list

	    List<newsResponse> newsResponseList = new ArrayList<>();
	    for (news news : newsList) {
	        newsResponse response = new newsResponse();
	        response.setNewsId(news.getNewsId());
	        response.setTitle(news.getTitle());
	        response.setContent(news.getContent());
	        response.setPublicTime(news.getPublicTime());
	        response.setCategory(news.getCategory()); // 回傳以上資訊
	        newsResponseList.add(response);
	    }

	    return newsResponseList;
	}


	@Override
	public newsResponse AlterNews(newsRequest request) {
	    String newContent = request.getContent();
	    Integer newsId = request.getNewsId(); 
	    
	    try {
	        newsDao.updateNewsContent(newContent, newsId);

	        newsResponse response = new newsResponse();
	        response.setContent(newContent);
	        response.setMessage("News altered successfully.");
	        return response;
	    } catch (Exception e) {
	        // 找異常，返回失败的訊息
	        newsResponse response = new newsResponse();
	        response.setMessage("Failed to alter news: " + e.getMessage());
	        return response;
	    }
	}



	@Override
	public newsResponse DeleteNews(newsRequest request) {
	    Integer newsId = request.getNewsId();
	    newsDao.deleteNewsById(newsId);

	    newsResponse response = new newsResponse();
	    response.setMessage("News deleted successfully.");
	    return response;
	}


	@Override
	public newsResponse FindNewsById(newsRequest request) {
	    Integer newsId = request.getNewsId();
	    Optional<news> optionalNews = newsDao.findById(newsId);
	    
	    if (optionalNews.isPresent()) {
	        news news = optionalNews.get();
	        newsResponse response = new newsResponse();
	        response.setNewsId(news.getNewsId());
	        response.setTitle(news.getTitle());
	        response.setContent(news.getContent());
	        response.setPublicTime(news.getPublicTime());
	        response.setCategory(news.getCategory()); // 回傳該news的資料

	        return response;
	    } else {
	        return new newsResponse("no news");
	    }
	}



	

}
