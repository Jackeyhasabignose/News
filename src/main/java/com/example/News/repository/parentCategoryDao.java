package com.example.News.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.News.entity.ParentCategory;

@Repository
public interface parentCategoryDao extends JpaRepository<ParentCategory, Integer> {

    // 親カテゴリIDから親カテゴリ名を検索
    @Query("SELECT pc.parentCategoryName FROM ParentCategory pc WHERE pc.parentCategoryId = :parentCategoryId")
    String findParentCategoryNameByParentCategoryId(@Param("parentCategoryId") Integer parentCategoryId);

    // 親カテゴリ名から親カテゴリIDを検索
    @Query("SELECT p.parentCategoryId FROM ParentCategory p WHERE p.parentCategoryName = :parentCategoryName")
    Integer findParentCategoryIdByParentCategoryName(@Param("parentCategoryName") String parentCategoryName);

    // 親カテゴリ名で親カテゴリを検索
    Optional<ParentCategory> findByParentCategoryName(String parentCategoryName);

}
