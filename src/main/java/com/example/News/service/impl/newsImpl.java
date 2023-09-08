package com.example.News.service.impl;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.News.entity.news;
import com.example.News.repository.newsDao;
import com.example.News.service.ifs.newsService;
import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;
import com.example.Newsl.constants.RtnCode;

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

		// 大分類と小分類が有効かどうかを確認
		if (!isValidParentCategory(request.getParentCategory()) || !isValidCategory(request.getCategory())) {
			response.setMessage(RtnCode.InvalidCategory.getMessage());
			return response;
		}

		// 防空チェック
		if (request.getTitle() == null || request.getTitle().isEmpty() || request.getContent() == null
				|| request.getContent().isEmpty() || request.getParentCategory() == null
				|| request.getParentCategory().isEmpty() || request.getCategory() == null
				|| request.getCategory().isEmpty()) {
			response.setMessage(RtnCode.Empty.getMessage());
			return response;
		}

		LocalDate now = LocalDate.now();
		request.setPublicTime(now);

		// insertNewsメソッドを呼び出して挿入操作を実行
		try {
			newsDao.insertNews(request.getTitle(), request.getContent(), now, request.getParentCategory(),
					request.getCategory());
			response.setMessage(RtnCode.SUCCESS.getMessage());
		} catch (Exception e) {
			// 挿入操作の例外を処理
			response.setMessage(RtnCode.Fail.getMessage());
			// 例外情報を含むログを追加
			e.printStackTrace();
		}

		return response;
	}

	private boolean isValidParentCategory(String parentCategory) {

		return "國內".equals(parentCategory) || "國外".equals(parentCategory);
	}

	private boolean isValidCategory(String category) {

		List<String> validCategories = Arrays.asList("音樂", "遊戲", "教育", "科技");
		return validCategories.contains(category);
	}

	@Override
	public List<newsResponse> FindAllNews() {
		List<news> newsList = newsDao.findAll();// ニュースの複数のリストを検索

		List<newsResponse> newsResponseList = new ArrayList<>();
		// 各リストに複数の属性を追加
		for (news news : newsList) {
			newsResponse response = new newsResponse();
			response.setNewsId(news.getNewsId());
			response.setTitle(news.getTitle());
			response.setContent(news.getContent());
			response.setPublicTime(news.getPublicTime());
			response.setParentCategory(news.getParentCategory());
			response.setCategory(news.getCategory());
			newsResponseList.add(response);
		}

		return newsResponseList;
	}

	@Override
	public newsResponse AlterNews(newsRequest request) {
		Integer newsId = request.getNewsId();
		String newTitle = request.getTitle();
		String newContent = request.getContent();
		String newParentCategory = request.getParentCategory();
		String newCategory = request.getCategory();

		// データが空または無効でないようにします
		if (!isValidParentCategory(newParentCategory) || !isValidCategory(newCategory) || newTitle == null
				|| newTitle.isEmpty() || newContent == null || newContent.isEmpty()) {
			newsResponse response = new newsResponse();
			response.setMessage(RtnCode.InvalidCategory.getMessage());
			return response;
		}

		try {
			// 更新メソッドを呼び出し、新しい属性値を渡します
			newsDao.updateNewsContent(newContent, newTitle, newParentCategory, newCategory, newsId);

			newsResponse response = new newsResponse();
			response.setMessage(RtnCode.SUCCESS1.getMessage());
			return response;
		} catch (Exception e) {
			// 例外状況を処理し、失敗のメッセージを返します
			newsResponse response = new newsResponse();
			response.setMessage(RtnCode.Fail.getMessage());
			return response;
		}
	}

	@Override
	public newsResponse DeleteNews(newsRequest request) {
		Integer newsId = request.getNewsId();
		// IDでこのメッセージを検索して削除
		newsDao.deleteNewsById(newsId);

		newsResponse response = new newsResponse();
		response.setMessage(RtnCode.SUCCESS2.getMessage());
		return response;
	}

	@Override
	public newsResponse FindNewsById(newsRequest request) {
		Integer newsId = request.getNewsId();
		// IDで特定の最新のメッセージを見つけ
		Optional<news> optionalNews = newsDao.findById(newsId);
		// 見つかった場合は多くの属性を返し
		if (optionalNews.isPresent()) {
			news news = optionalNews.get();
			newsResponse response = new newsResponse();
			response.setNewsId(news.getNewsId());
			response.setTitle(news.getTitle());
			response.setContent(news.getContent());
			response.setPublicTime(news.getPublicTime());
			response.setParentCategory(news.getParentCategory());
			response.setCategory(news.getCategory()); // 回傳該news的資料

			return response;
		} else {
			// 見つからなかった場合は失敗メッセージを返します
			newsResponse response = new newsResponse();
			response.setMessage(RtnCode.Empty1);
			return response;
		}
	}

}
