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

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO news (title, content, public_time, category) VALUES (:title, :content, :public_time, :category)", nativeQuery = true)
	void insertNews(@Param("title") String title, @Param("content") String content, @Param("public_time") LocalDate publicTime, @Param("category") String category);


	List<news> findAll();
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE news SET content = :content WHERE news_id = :newsId", nativeQuery = true)
	void updateNewsContent(@Param("content") String content, @Param("newsId") Integer newsId);


	Optional<news> findById(Integer newsId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM news WHERE news_id = :newsId", nativeQuery = true)
	void deleteNewsById(@Param("newsId") Integer newsId);

}
