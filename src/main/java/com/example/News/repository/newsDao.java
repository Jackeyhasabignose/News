package com.example.News.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.News.entity.News;


@Repository
public interface newsDao extends JpaRepository<News, Integer> {

	// ニュースデータを挿入
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO news (title, sub_title, content, public_time, parent_category_id, category_id, status) VALUES (:title, :subTitle, :content, :publicTime, :parentCategoryId, :categoryId, :status)", nativeQuery = true)
	void insertNews(@Param("title") String title, @Param("subTitle") String subTitle, @Param("content") String content,
			@Param("publicTime") LocalDateTime publicTime, @Param("parentCategoryId") Integer parentCategoryId,
			@Param("categoryId") Integer categoryId, @Param("status") String status);

	// すべてのニュースを取得
	List<News> findAll();

	@Query("SELECT n.newsId, n.title, n.subTitle, n.content, n.publicTime, pc.parentCategoryId, c.categoryId, pc.parentCategoryName, c.categoryName "
			+ "FROM com.example.News.entity.News n "
			+ "JOIN com.example.News.entity.ParentCategory pc ON n.parentCategoryId = pc.parentCategoryId "
			+ "JOIN com.example.News.entity.Category c ON n.categoryId = c.categoryId")
	List<Object[]> findNewsWithParentCategoryIdsAndNames();

	@Query("SELECT n.newsId, n.title, n.subTitle, n.content, n.publicTime, pc.parentCategoryId, c.categoryId, pc.parentCategoryName, c.categoryName "
			+ "FROM com.example.News.entity.News n "
			+ "JOIN com.example.News.entity.ParentCategory pc ON n.parentCategoryId = pc.parentCategoryId "
			+ "JOIN com.example.News.entity.Category c ON n.categoryId = c.categoryId")
	List<Object[]> findNewsWithCategoryIdsAndNames();

	@Query("SELECT n.newsId, n.title, n.subTitle, n.content, n.publicTime, pc.parentCategoryId, c.categoryId, pc.parentCategoryName, c.categoryName "
	        + "FROM com.example.News.entity.News n "
	        + "JOIN com.example.News.entity.Category c ON n.categoryId = c.categoryId "
	        + "JOIN com.example.News.entity.ParentCategory pc ON c.parentCategoryId = pc.parentCategoryId "
	        + "WHERE c.categoryId = :categoryId") // 使用小分类ID进行过滤
	List<Object[]> findNewsWithCategoryIds(@Param("categoryId") Integer categoryId);
	
	



	// IDでニュースを検索
	Optional<News> findById(Integer newsId);

	// IDでニュースを削除
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM news WHERE news_id = :newsId", nativeQuery = true)
	void deleteNewsById(@Param("newsId") Integer newsId);
	 // ニュースを更新（更新新聞）
	@Modifying
	@Query("UPDATE News n SET n.content = :#{#news.content}, n.title = :#{#news.title}, n.publicTime = :#{#news.publicTime}, n.parentCategoryId = :#{#news.parentCategoryId}, n.categoryId = :#{#news.categoryId}, n.subTitle = :#{#news.subTitle}, n.alterTime = :#{T(java.time.LocalDateTime).now()} WHERE n.newsId = :#{#news.newsId}")
	void updateNews(@Param("news") News news);
	 // カテゴリIDによる新聞の数を取得
	@Query("SELECT COUNT(n) FROM News n WHERE n.parentCategoryId = :parentCategoryId")
	int getNewsCountByParentCategoryId(@Param("parentCategoryId") int parentCategoryId);
	// カテゴリIDによる新聞を取得
	List<News> findByCategoryId(Integer categoryId);
	// 親カテゴリIDによる新聞を取得
	@Query("SELECT n FROM News n WHERE n.parentCategoryId = :parentCategoryId")
	List<News> findByParentCategoryId(@Param("parentCategoryId") Integer parentCategoryId);

}