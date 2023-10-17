package com.example.News.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.News.entity.ParentCategory;
import com.example.News.repository.parentCategoryDao;
import com.example.News.repository.newsDao;

import com.example.News.service.ifs.parentCategoryService;
import com.example.News.vo.newsResponse;
import com.example.News.vo.parentCategoryRequest;
import com.example.News.vo.parentCategoryResponse;
import com.example.Newsl.constants.Msg;

import com.example.News.service.ifs.newsService;

@Service
public class parentCategoryImpl implements parentCategoryService {

    @Autowired
    private parentCategoryDao parentCategoryDao;
    
    @Autowired
    private newsDao newsDao;

    @Autowired
    private newsService newsService;

    @Override
    public parentCategoryResponse AddparentCategory(parentCategoryRequest request) {
        parentCategoryResponse response = new parentCategoryResponse();

        if (request == null || request.getParentCategoryName() == null
                || request.getParentCategoryName().trim().isEmpty()) {
            response.setMessage(Msg.Empty.getMessage()); // リクエストが空またはカテゴリ名が指定されていない場合のエラーメッセージを設定
            return response;
        }

        String categoryName = request.getParentCategoryName().trim();
        if (categoryName.length() > 10) {
            response.setMessage(Msg.Empty.getMessage()); // カテゴリ名が10文字を超える場合のエラーメッセージを設定
            return response;
        }

        ParentCategory parentCategory = new ParentCategory();
        parentCategory.setParentCategoryName(categoryName);

        try {
            ParentCategory savedParentCategory = parentCategoryDao.save(parentCategory); // 親カテゴリを保存
            response.setParentCategory(savedParentCategory);
            response.setMessage(Msg.SUCCESS.getMessage()); // 成功メッセージを設定
        } catch (DataIntegrityViolationException e) {
            response.setMessage(Msg.InvalidCategory.getMessage()); // データベースの一意性制約エラーをキャッチ
        } catch (Exception e) {
            response.setMessage(Msg.Fail.getMessage()); // その他の例外の場合のエラーメッセージを設定
        }

        return response;
    }

    @Override
    public parentCategoryResponse AlterparentCategory(parentCategoryRequest request) {
        parentCategoryResponse response = new parentCategoryResponse();

        Optional<ParentCategory> optionalParentCategory = parentCategoryDao.findById(request.getParentCategoryId());

        if (optionalParentCategory.isEmpty()) {
            response.setMessage(Msg.Empty.getMessage()); // 見つからない場合のエラーメッセージを設定
            return response;
        }

        ParentCategory parentCategoryToUpdate = optionalParentCategory.get();

        String newCategoryName = request.getParentCategoryName(); // 新しい名前をトリムしない

        if (newCategoryName == null || newCategoryName.trim().isEmpty()) {
            response.setMessage(Msg.Empty.getMessage()); // 空のカテゴリ名が指定された場合のエラーメッセージを設定
            return response;
        }

        if (newCategoryName.equals(parentCategoryToUpdate.getParentCategoryName())) {
            response.setMessage(Msg.Fail1.getMessage()); // 新しい名前と古い名前が同じ場合のエラーメッセージを設定
            return response;
        }

        if (newCategoryName.length() > 10) {
            response.setMessage(Msg.Empty.getMessage()); // カテゴリ名が10文字を超える場合のエラーメッセージを設定
            return response;
        }

        parentCategoryToUpdate.setParentCategoryName(newCategoryName); // 大カテゴリの名前を更新

        try {
            ParentCategory updatedParentCategory = parentCategoryDao.save(parentCategoryToUpdate); // 更新後の大カテゴリを保存
            response.setParentCategory(updatedParentCategory);
            response.setMessage(Msg.SUCCESS.getMessage()); // 成功メッセージを設定
        } catch (Exception e) {
            response.setMessage(Msg.Fail.getMessage()); // その他の例外の場合のエラーメッセージを設定
        }

        return response;
    }

    @Override
    public parentCategoryResponse DeleteparentCategory(parentCategoryRequest request) {
        parentCategoryResponse response = new parentCategoryResponse();

        List<Integer> parentCategoryIds = request.getParentCategoryIds();

        try {
            for (Integer parentCategoryId : parentCategoryIds) {
                Optional<ParentCategory> optionalParentCategory = parentCategoryDao.findById(parentCategoryId);

                if (optionalParentCategory.isPresent()) {
                    ParentCategory parentCategoryToDelete = optionalParentCategory.get();
                    parentCategoryDao.delete(parentCategoryToDelete); // 親カテゴリを削除
                }
            }
            response.setMessage(Msg.SUCCESS.getMessage()); // 成功メッセージを設定
        } catch (Exception e) {
            response.setMessage(Msg.Fail.getMessage()); // その他の例外の場合のエラーメッセージを設定
        }

        return response;
    }

    @Override
    public List<ParentCategory> getAllParentCategoriesWithNewsCount() {
        List<ParentCategory> parentCategories = parentCategoryDao.findAll(); // すべての親カテゴリを取得

        for (ParentCategory parentCategory : parentCategories) {
            int parentCategoryId = parentCategory.getParentCategoryId();
            int newsCount = newsService.getNewsCountByParentCategoryId(parentCategoryId); // ニュースの数を取得
            parentCategory.setNewsCount(newsCount);
            parentCategoryDao.save(parentCategory); // 更新された親カテゴリを保存
        }

        return parentCategories;
    }

    @Override
    public List<newsResponse> FindNewsByParentCategoryName(parentCategoryRequest request) {
        List<Object[]> newsData = newsDao.findNewsWithParentCategoryIdsAndNames();
        LocalDateTime currentDate = LocalDateTime.now();

        List<newsResponse> newsResponseList = new ArrayList<>();
        String parentcategoryName = request.getParentCategoryName();
        for (Object[] data : newsData) {
            // データから大カテゴリ名を取得
            String parentCategoryName = (String) data[7];

            // 大カテゴリ名が目標と一致しない場合はスキップ
            if (!parentCategoryName.equals(parentcategoryName)) {
                continue;
            }

            // 他のコードは変更しない
            Integer newsId = (Integer) data[0];
            String title = (String) data[1];
            String subTitle = (String) data[2];
            String content = (String) data[3];
            LocalDateTime publicTime = (LocalDateTime) data[4];
            Integer parentCategoryId = (Integer) data[5];
            Integer categoryId = (Integer) data[6];
            String categoryName = (String) data[8];

            newsResponse response = new newsResponse();
            // 他の属性を設定...
            response.setNewsId(newsId);
            response.setTitle(title);
            response.setSubTitle(subTitle);
            response.setContent(content);
            response.setPublicTime(publicTime);
            response.setParentCategoryName(parentCategoryName);
            response.setCategoryName(categoryName);
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
