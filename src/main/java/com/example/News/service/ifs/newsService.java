package com.example.News.service.ifs;

import java.util.List;

import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;

public interface newsService {
	//�s�W����
	public newsResponse AddNews(newsRequest request);
	//��Ҧ�����
	public List<newsResponse> FindAllNews();
	//�ǥ�id�����
	public newsResponse FindNewsById(newsRequest request);
	//�ǥ�id�ק����
	public newsResponse AlterNews(newsRequest request);
	//�R������
	public newsResponse DeleteNews(newsRequest request);

}
