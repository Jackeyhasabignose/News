package com.example.News.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.News.entity.Category;
import com.example.News.entity.ParentCategory;
import com.example.News.service.ifs.categoryService;
import com.example.News.service.ifs.newsService;
import com.example.News.service.ifs.parentCategoryService;
import com.example.News.vo.categoryRequest;
import com.example.News.vo.newsResponse;
import com.example.News.vo.parentCategoryRequest;



@CrossOrigin
@Controller
public class categoryThymeleafController {
	
	
	@Autowired
	private categoryService categoryService;

	@Autowired
	private parentCategoryService parentCategoryService;

	@Autowired
	private newsService newsService;
	
	
	@GetMapping("/category")
	public String parentCategoryPage(Model model) {
	    // 调用获取所有大类别数据的服务方法
		 List<Category> Categories = categoryService.getAllCategoriesWithNewsCount();
	    // 将数据添加到 Model 中
	    model.addAttribute("Categories", Categories);

	    // 指定视图的名称，这里假设视图名称为 "parentCategory.html"
	    return "category";
	}
	
	@GetMapping("/findNewsByCategory")
	public String findNewsByCategory(@RequestParam String categoryName, Model model) {
	    categoryRequest request = new categoryRequest();
	    request.setCategoryName(categoryName);
	    List<newsResponse> newsList = categoryService.FindNewsByCategoryName(request);

	    // 将查询结果添加到Model中
	    model.addAttribute("newsList", newsList);
	   

	    

	    return "findNewsByCategory"; // 返回新闻列表页面 
	}

}
