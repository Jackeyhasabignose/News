package com.example.News.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.News.entity.Category;

@Repository
public interface categoryDao extends JpaRepository<Category, Integer> {

    // カテゴリIDからカテゴリ名を検索
    @Query("SELECT c.categoryName FROM Category c WHERE c.categoryId = :categoryId")
    String findCategoryNameByCategoryId(@Param("categoryId") Integer categoryId);

    // カテゴリ名からカテゴリIDを検索
    @Query("SELECT c.categoryId FROM Category c WHERE c.categoryName = :categoryName")
    Integer findCategoryIdByCategoryName(@Param("categoryName") String categoryName);

    // 親カテゴリIDによってカテゴリを検索
    List<Category> findByParentCategoryId(Integer parentCategoryId);

}

