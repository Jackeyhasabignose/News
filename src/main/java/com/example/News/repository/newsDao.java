package com.example.News.repository;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.News.entity.news;

@Repository
public interface newsDao extends JpaRepository<news, Integer> {
	
	// ニュースデータを挿入します
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO news (title, content, public_time, parent_category, category) VALUES (:title, :content, :public_time, :parent_category, :category)", nativeQuery = true)
	void insertNews(@Param("title") String title, @Param("content") String content, @Param("public_time") LocalDate publicTime,@Param("parent_category") String parentCategory, @Param("category") String category);

	// すべてのニュースを取得します
	List<news> findAll();
	
	// ニュースコンテンツを更新します
	@Modifying
	@Transactional
	@Query(value = "UPDATE news SET content = :content, title = :title, parent_category = :parentCategory, category = :category, public_time = CURRENT_TIMESTAMP WHERE news_id = :newsId", nativeQuery = true)
	void updateNewsContent(@Param("content") String content, @Param("title") String title, @Param("parentCategory") String parentCategory, @Param("category") String category, @Param("newsId") Integer newsId);


	// IDでニュースを検索します
	Optional<news> findById(Integer newsId);
	
	// IDでニュースを削除します
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM news WHERE news_id = :newsId", nativeQuery = true)
	void deleteNewsById(@Param("newsId") Integer newsId);

}
