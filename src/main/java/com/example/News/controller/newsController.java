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
	//�s�W����
	@PostMapping(value = "Add_news")
	public newsResponse AddNews(@RequestBody newsRequest request) {
		return newsService.AddNews(request);
	}
	//�R������
	@PostMapping(value = "Delete_news")
	public newsResponse DeleteNews(@RequestBody newsRequest request) {
		return newsService.DeleteNews(request);
	}
	//�ǥ�id�����
	@PostMapping(value = "Find_news_by_id")
	public newsResponse FindNewsById(@RequestBody newsRequest request) {
		return newsService.FindNewsById(request);
	}
	//��Ҧ�����
	@GetMapping(value = "Find_all_news")
	public List<newsResponse> FindAllNews() {
		return newsService.FindAllNews();
	}
	//�ǥ�id�ק����
	@PostMapping(value = "Alter_news")
	public newsResponse AlterNews(@RequestBody newsRequest request) {
		return newsService.AlterNews(request);
	}
	

}
