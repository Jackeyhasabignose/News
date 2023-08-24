package com.example.News.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import com.example.News.service.ifs.newsService;
import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;


@CrossOrigin
@RestController
public class newsController {
	@Autowired 
	public newsService newsService;
	//新增消息
	@PostMapping(value = "Add_news")
	public newsResponse AddNews(@RequestBody newsRequest request) {
		return newsService.AddNews(request);
	}
	//刪除消息
	@PostMapping(value = "Delete_news")
	public newsResponse DeleteNews(@RequestBody newsRequest request) {
		return newsService.DeleteNews(request);
	}
	//藉由id找消息
	@PostMapping(value = "Find_news_by_id")
	public newsResponse FindNewsById(@RequestBody newsRequest request) {
		return newsService.FindNewsById(request);
	}
	//找所有消息
	@GetMapping(value = "Find_all_news")
	public List<newsResponse> FindAllNews() {
		return newsService.FindAllNews();
	}
	//藉由id修改消息
	@PostMapping(value = "Alter_news")
	public newsResponse AlterNews(@RequestBody newsRequest request) {
		return newsService.AlterNews(request);
	}
	

}
