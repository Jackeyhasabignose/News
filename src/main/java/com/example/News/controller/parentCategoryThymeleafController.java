package com.example.News.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.News.entity.News;
import com.example.News.entity.ParentCategory;
import com.example.News.service.ifs.categoryService;
import com.example.News.service.ifs.newsService;
import com.example.News.service.ifs.parentCategoryService;
import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;
import com.example.News.vo.parentCategoryRequest;
import com.example.News.vo.parentCategoryResponse;

@CrossOrigin
@Controller
public class parentCategoryThymeleafController {
	
	@Autowired
	private categoryService categoryService;

	@Autowired
	private parentCategoryService parentCategoryService;

	@Autowired
	private newsService newsService;
	
	
	@GetMapping("/parentCategory")
	public String parentCategoryPage(Model model) {
	    // 调用获取所有大类别数据的服务方法
		 List<ParentCategory> parentCategories = parentCategoryService.getAllParentCategoriesWithNewsCount();
	    // 将数据添加到 Model 中
	    model.addAttribute("parentCategories", parentCategories);

	    // 指定视图的名称，这里假设视图名称为 "parentCategory.html"
	    return "parentCategory";
	}
	
	@GetMapping("/findNewsByParentCategory")
	public String findNewsByParentCategory(@RequestParam String parentCategoryName, Model model) {
	    parentCategoryRequest request = new parentCategoryRequest();
	    request.setParentCategoryName(parentCategoryName);
	    List<newsResponse> newsList = parentCategoryService.FindNewsByParentCategoryName(request);

	    // 将查询结果添加到Model中
	    model.addAttribute("newsList", newsList);

	    List<newsResponse> newsItem = newsService.FindAllNews();

	    // 对newsItem中的日期进行格式化
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    for (newsResponse item : newsItem) {
	        LocalDateTime publicTime = item.getPublicTime();
	        String formattedDate = publicTime.format(formatter);
	        item.setFormattedDate(formattedDate); // 将格式化后的日期添加到newsResponse对象中
	    }

	    model.addAttribute("newsItem", newsItem);

	    return "findNewsByParentCategory"; // 返回新闻列表页面 
	}





}
