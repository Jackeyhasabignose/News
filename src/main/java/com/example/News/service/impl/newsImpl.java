package com.example.News.service.impl;

import java.time.LocalDateTime;

import java.time.ZoneOffset;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.News.entity.News;

import com.example.News.repository.categoryDao;
import com.example.News.repository.newsDao;
import com.example.News.repository.parentCategoryDao;

import com.example.News.service.ifs.newsService;
import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;
import com.example.Newsl.constants.Msg;


@Service
@Transactional
public class newsImpl implements newsService {
	@Autowired
	private newsDao newsDao;

	@Autowired
	private categoryDao categoryDao;

	@Autowired
	private parentCategoryDao parentCategoryDao;

	@Override
	public newsResponse AddNews(newsRequest request) {
		newsResponse response = new newsResponse();

		if (isInvalidNewsRequest(request)) {
			response.setMessage(Msg.Empty1.getMessage());// 無効なニュースリクエストの場合、エラーメッセージを設定
			return response;
		}

		String parentCategoryName = request.getParentCategoryName();
		String categoryName = request.getCategoryName();

		Integer parentCategoryId = parentCategoryDao.findParentCategoryIdByParentCategoryName(parentCategoryName);
		Integer categoryId = categoryDao.findCategoryIdByCategoryName(categoryName);

		if (parentCategoryId == null || categoryId == null) {
			response.setMessage(Msg.Empty2.getMessage());// カテゴリが存在しない場合、エラーメッセージを設定
			return response;
		}

		LocalDateTime selectedDate = request.getPublicTime();
		if (selectedDate == null) {
			response.setMessage(Msg.Remind.getMessage());
			return response;
		}

		LocalDateTime currentDate = LocalDateTime.now();

		String status = (selectedDate.isAfter(currentDate)) ? Msg.Status.getMessage() : Msg.Status1.getMessage();// ステータスを設定

		// サブタイトルの処理ロジックを追加
		String subTitle = request.getSubTitle();

		newsDao.insertNews(request.getTitle(), subTitle, request.getContent(), selectedDate, parentCategoryId,
				categoryId, status);
		response.setMessage(Msg.SUCCESS.getMessage());

		return response;
	}

	@Override
	public List<newsResponse> FindAllNews() {
		List<Object[]> newsData = newsDao.findNewsWithCategoryIdsAndNames();
		LocalDateTime currentDate = LocalDateTime.now();

		List<newsResponse> newsResponseList = new ArrayList<>();

		for (Object[] data : newsData) {
			Integer newsId = (Integer) data[0];
			String title = (String) data[1];
			String subTitle = (String) data[2]; // サブタイトルを抽出
			String content = (String) data[3];
			LocalDateTime publicTime = (LocalDateTime) data[4];
			Integer parentCategoryId = (Integer) data[5];
			Integer categoryId = (Integer) data[6];
			String parentCategoryName = (String) data[7];
			String categoryName = (String) data[8];

			// newsResponseオブジェクトを構築し、関連するプロパティを設定
			newsResponse response = new newsResponse();
			response.setNewsId(newsId);
			response.setTitle(title);
			response.setSubTitle(subTitle);
			response.setContent(content);
			response.setPublicTime(publicTime);
			response.setParentCategoryName(parentCategoryName);
			response.setCategoryName(categoryName);

			response.setParentCategoryId(parentCategoryId);
			response.setCategoryId(categoryId);

			// 記事が公開されているかどうかを判断
			if (publicTime.isBefore(currentDate) || publicTime.isEqual(currentDate)) {
				response.setStatus(Msg.Status1.getMessage());
			} else {
				response.setStatus(Msg.Status.getMessage());
			}

			newsResponseList.add(response);
		}

		return newsResponseList;
	}

	public newsResponse AlterNews(newsRequest request) {
		Integer newsId = request.getNewsId();
		String newTitle = request.getTitle();
		String newContent = request.getContent();
		String newParentCategoryName = request.getParentCategoryName();
		String newCategoryName = request.getCategoryName();
		String newSubTitle = request.getSubTitle();
		LocalDateTime newPublicTime = request.getPublicTime();

		try {
			// parentCategoryDaoを使用して親カテゴリを検索
			Integer parentCategoryId = parentCategoryDao
					.findParentCategoryIdByParentCategoryName(newParentCategoryName);

			// findCategoryIdByCategoryNameを使用してカテゴリIDを検索
			Integer newCategoryId = categoryDao.findCategoryIdByCategoryName(newCategoryName);

			// クエリ結果がnullであるかどうかをチェックし、カテゴリが存在しないことを示す
			if (newTitle == null || newTitle.isEmpty() || newContent == null || newContent.isEmpty()
					|| parentCategoryId == null || newCategoryId == null) {
				newsResponse response = new newsResponse();
				response.setMessage(Msg.InvalidCategory.getMessage());
				return response;
			}

			// 更新するフィールドを持つNewsオブジェクトを構築（他のフィールドはnullのまま）
			News updatedNews = new News();
			updatedNews.setNewsId(newsId);
			updatedNews.setTitle(newTitle);
			updatedNews.setContent(newContent);
			updatedNews.setParentCategoryId(parentCategoryId);
			updatedNews.setCategoryId(newCategoryId);
			updatedNews.setSubTitle(newSubTitle);
			updatedNews.setPublicTime(newPublicTime); // 设置新的 publicTime

			// 現在のローカル時間を取得し、UTC時間に変換
			LocalDateTime currentLocalTime = LocalDateTime.now();
			LocalDateTime currentUtcTime = currentLocalTime.atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();

			// "alter_time" 列の値を現在のUTC時間に設定
			updatedNews.setAlterTime(currentUtcTime);

			// 新しい属性、カテゴリID、およびalter_timeでニュースを更新
			newsDao.updateNews(updatedNews);

			newsResponse response = new newsResponse();
			response.setMessage(Msg.SUCCESS1.getMessage());// 成功メッセージを設定
			return response;
		} catch (IllegalArgumentException e) {
			// カテゴリが存在しない場合の処理
			newsResponse response = new newsResponse();
			response.setMessage("カテゴリが存在しません");
			return response;
		} catch (DataIntegrityViolationException e) {
			// データ整合性の違反に関連する例外の処理（一意制約など）
			newsResponse response = new newsResponse();
			response.setMessage("データ整合性違反: " + e.getMessage());
			return response;
		} catch (Exception e) {
			// その他の予期しない例外の処理
			e.printStackTrace();
			newsResponse response = new newsResponse();
			response.setMessage(Msg.Fail.getMessage());
			return response;
		}
	}

	@Override
	public newsResponse DeleteNews(newsRequest request) {
		List<Integer> newsIds = request.getNewsIds();

		if (newsIds == null || newsIds.isEmpty()) {
			// 個別のニュースを削除する場合の処理
			Integer newsId = request.getNewsId();
			if (newsId != null) {
				newsDao.deleteNewsById(newsId);
			}
		} else {
			// 複数のニュースを削除する場合の処理
			for (Integer newsId : newsIds) {
				newsDao.deleteNewsById(newsId);
			}
		}

		newsResponse response = new newsResponse();
		response.setMessage(Msg.SUCCESS2.getMessage());
		return response;
	}

	@Override
	public newsResponse FindNewsById(newsRequest request) {
		Integer newsId = request.getNewsId();

		// newsDaoを使用してニュースを検索
		Optional<News> optionalNews = newsDao.findById(newsId);

		if (optionalNews.isPresent()) {
			News newsItem = optionalNews.get();
			newsResponse response = new newsResponse();
			response.setNewsId(newsItem.getNewsId());
			response.setTitle(newsItem.getTitle());
			response.setContent(newsItem.getContent());
			response.setPublicTime(newsItem.getPublicTime());
			response.setSubTitle(newsItem.getSubTitle()); // 设置 subTitle 字段的值

			// categoryDaoを使用してカテゴリ名を検索
			Integer categoryId = newsItem.getCategoryId();
			String categoryName = categoryDao.findCategoryNameByCategoryId(categoryId);
			response.setCategoryName(categoryName);

			// parentCategoryDaoを使用して親カテゴリ名を検索
			Integer parentCategoryId = newsItem.getParentCategoryId();
			String parentCategoryName = parentCategoryDao.findParentCategoryNameByParentCategoryId(parentCategoryId);
			response.setParentCategoryName(parentCategoryName);

			return response;
		} else {
			newsResponse response = new newsResponse();
			response.setMessage(Msg.Empty1);
			return response;
		}
	}

	private boolean isInvalidNewsRequest(newsRequest request) {
		return request == null || isEmpty(request.getTitle()) || isEmpty(request.getContent())
				|| isEmpty(request.getParentCategoryName()) || isEmpty(request.getCategoryName());
	}

	private boolean isEmpty(String value) {
		return value == null || value.isEmpty();
	}

	public int getNewsCountByParentCategoryId(int parentCategoryId) {
		Integer newsCount = newsDao.getNewsCountByParentCategoryId(parentCategoryId);
		return newsCount != null ? newsCount : 0;
	}

	@Override
	public int getNewsCountByCategoryId(Integer categoryId) {
		// categoryDaoを使用して指定されたサブカテゴリIDに関連するニュースの数を検索
		List<News> newsList = newsDao.findByCategoryId(categoryId);

		// ニュースの数を返す
		return newsList.size();
	}

}
