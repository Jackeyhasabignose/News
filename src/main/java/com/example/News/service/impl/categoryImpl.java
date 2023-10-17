package com.example.News.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.News.entity.Category;
import com.example.News.entity.ParentCategory;
import com.example.News.repository.categoryDao;
import com.example.News.repository.newsDao;
import com.example.News.repository.parentCategoryDao;
import com.example.News.service.ifs.categoryService;
import com.example.News.service.ifs.newsService;
import com.example.News.vo.categoryRequest;
import com.example.News.vo.categoryResponse;
import com.example.News.vo.newsResponse;
import com.example.Newsl.constants.Msg;

@Service
public class categoryImpl implements categoryService {

	@Autowired
	private categoryDao categoryDao;

	@Autowired
	private parentCategoryDao parentcategoryDao;

	@Autowired
	private newsService newsService;

	@Autowired
	private newsDao newsDao;

	@Override
	public categoryResponse Addcategory(categoryRequest request) {
		categoryResponse response = new categoryResponse();

		if (request == null || request.getCategoryName() == null || request.getCategoryName().trim().isEmpty()) {
			response.setMessage(Msg.Empty.getMessage()); // 空のリクエストまたはカテゴリ名が指定されていない場合のエラーメッセージを設定
			return response;
		}

		String categoryName = request.getCategoryName().trim();
		if (categoryName.length() > 10) {
			response.setMessage(Msg.Empty.getMessage()); // カテゴリ名が10文字を超える場合のエラーメッセージを設定
			return response;
		}

		String parentCategoryName = request.getParentCategoryName();
		Optional<ParentCategory> parentCategoryOptional = parentcategoryDao.findByParentCategoryName(parentCategoryName);

		if (parentCategoryOptional.isPresent()) {
			ParentCategory parentCategory = parentCategoryOptional.get();

			List<Category> existingCategories = categoryDao.findByParentCategoryId(parentCategory.getParentCategoryId());

			boolean categoryNameExists = false;
			for (Category category : existingCategories) {
				if (category.getCategoryName().equalsIgnoreCase(categoryName)) {
					categoryNameExists = true;
					break;
				}
			}

			if (categoryNameExists) {
				response.setMessage("子カテゴリ名が重複しています"); // 同じ名前の子カテゴリが存在する場合のエラーメッセージを設定
			} else {
				Category category = new Category();
				category.setCategoryName(categoryName);
				category.setParentCategoryId(parentCategory.getParentCategoryId());

				try {
					Category savedCategory = categoryDao.save(category);
					response.setCategory(savedCategory);
					response.setMessage(Msg.SUCCESS.getMessage());
				} catch (Exception e) {
					response.setMessage(Msg.Fail.getMessage());
				}
			}
		} else {
			response.setMessage(Msg.Empty.getMessage()); // 親カテゴリが見つからない場合のエラーメッセージを設定
		}

		return response;
	}

	@Override
	public categoryResponse Altercategory(categoryRequest request) {
		categoryResponse response = new categoryResponse();

		Optional<Category> optionalCategory = categoryDao.findById(request.getCategoryId());

		if (optionalCategory.isEmpty()) {
			response.setMessage(Msg.Empty.getMessage());
			return response;
		}

		Category categoryToUpdate = optionalCategory.get();

		if (request.getCategoryName() == null || request.getCategoryName().trim().isEmpty()) {
			response.setMessage(Msg.Empty.getMessage());
			return response;
		}

		String newCategoryName = request.getCategoryName().trim();

		if (newCategoryName.length() > 10) {
			response.setMessage(Msg.Empty.getMessage());
			return response;
		}

		categoryToUpdate.setCategoryName(newCategoryName);

		try {
			Category updatedCategory = categoryDao.save(categoryToUpdate);
			response.setCategory(updatedCategory);
			response.setMessage(Msg.SUCCESS.getMessage());
		} catch (Exception e) {
			response.setMessage(Msg.Fail.getMessage());
		}

		return response;
	}

	@Override
	public categoryResponse Deletecategory(categoryRequest request) {
		categoryResponse response = new categoryResponse();

		List<Integer> categoryIds = request.getCategoryIds();

		if (categoryIds == null || categoryIds.isEmpty()) {
			response.setMessage(Msg.Empty.getMessage());
			return response;
		}

		try {
			for (Integer categoryId : categoryIds) {
				Optional<Category> optionalCategory = categoryDao.findById(categoryId);

				if (optionalCategory.isPresent()) {
					Category categoryToDelete = optionalCategory.get();
					categoryDao.delete(categoryToDelete);
				}
			}
			response.setMessage(Msg.SUCCESS.getMessage());
		} catch (Exception e) {
			response.setMessage(Msg.Fail.getMessage());
		}

		return response;
	}

	@Override
	public List<Category> getAllCategoriesWithNewsCount() {
		List<Category> categories = categoryDao.findAll();

		for (Category category : categories) {
			int categoryId = category.getCategoryId();
			int newsCount = newsService.getNewsCountByCategoryId(categoryId);
			category.setNewsCount(newsCount);
			categoryDao.save(category);
		}

		return categories;
	}

	@Override
	public List<newsResponse> FindNewsByCategoryName(categoryRequest request) {
	    String categoryName = request.getCategoryName(); // フィルタリングするカテゴリ名を取得
	    Integer categoryId = categoryDao.findCategoryIdByCategoryName(categoryName); // カテゴリ名に対応するカテゴリIDをcategoryDaoを使用して検索
	    if (categoryId == null) {
	        return Collections.emptyList(); // 一致するカテゴリが見つからない場合は空のリストを返す
	    }

	    List<Object[]> newsData = newsDao.findNewsWithCategoryIds(categoryId); // カテゴリIDを使用してニュースデータを検索
	    LocalDateTime currentDate = LocalDateTime.now();

	    List<newsResponse> newsResponseList = new ArrayList<>();

	    for (Object[] data : newsData) {
	        // データからカテゴリ名を取得
	        String categoryNameFromData = (String) data[8];

	        // カテゴリ名が対象と一致しない場合、スキップ
	        if (!categoryNameFromData.equals(categoryName)) {
	            continue;
	        }

	        // データから親カテゴリ名を取得
	        String parentCategoryName = (String) data[7];

	        // その他のコードはそのまま
	        Integer newsId = (Integer) data[0];
	        String title = (String) data[1];
	        String subTitle = (String) data[2];
	        String content = (String) data[3];
	        LocalDateTime publicTime = (LocalDateTime) data[4];
	        Integer parentCategoryId = (Integer) data[5];
	        categoryId = (Integer) data[6]; // データからcategoryIdを再設定

	        newsResponse response = new newsResponse();
	        // 他のプロパティを設定...
	        response.setNewsId(newsId);
	        response.setTitle(title);
	        response.setSubTitle(subTitle);
	        response.setContent(content);
	        response.setPublicTime(publicTime);
	        response.setParentCategoryName(parentCategoryName); // 親カテゴリ名を設定
	        response.setCategoryName(categoryNameFromData);
	        response.setParentCategoryId(parentCategoryId);
	        response.setCategoryId(categoryId);

	        if (publicTime.isBefore(currentDate) || publicTime.isEqual(currentDate)) {
	            response.setStatus(Msg.Status1.getMessage());
	        } else {
	            response.setStatus(Msg.Status.getMessage());
	        }

	        newsResponseList.add(response);
	    }

	    return newsResponseList;
	}
}
