package com.example.News.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.News.entity.Category;

import com.example.News.entity.ParentCategory;
import com.example.News.service.ifs.newsService;
import com.example.News.service.ifs.categoryService;
import com.example.News.service.ifs.parentCategoryService;
import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@Controller
public class newsThymeleafController {

	private static final Logger logger = LoggerFactory.getLogger(newsThymeleafController.class);

	@Autowired
	private categoryService categoryService;

	@Autowired
	private parentCategoryService parentCategoryService;

	@Autowired
	private newsService newsService;

	@GetMapping("/home")
	public String showNewsPage(Model model,
	        @RequestParam(defaultValue = "1") int page,  // 默认页数为1
	        @RequestParam(defaultValue = "10") int pageSize) {  // 默认每页显示10条数据

	    // 获取所有新闻
	    List<newsResponse> newsResponseList = newsService.FindAllNews();

	    // 计算总项目数
	    int totalItems = newsResponseList.size();

	    // 讦算总页数
	    int totalPages = (int) Math.ceil((double) totalItems / pageSize);

	    // 限制页数，确保它不超出范围
	    page = Math.max(1, Math.min(page, totalPages));

	    // 计算实际的起始索引和结束索引
	    int fromIndex = (page - 1) * pageSize;
	    int toIndex = Math.min(fromIndex + pageSize, totalItems);

	    // 获取当前页的项目
	    List<newsResponse> pagedNews = newsResponseList.subList(fromIndex, toIndex);

	    // 假设你遍历pagedNews
	    for (newsResponse item : pagedNews) {
	        LocalDateTime publicTime = item.getPublicTime();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = publicTime.format(formatter);
	        item.setFormattedDate(formattedDate); // 将格式化后的日期添加到newsResponse对象中
	    }

	    model.addAttribute("news", pagedNews); // 使用分页后的新闻列表
	    model.addAttribute("totalPages", totalPages); // 添加总页数到Model中
	    model.addAttribute("currentPage", page); // 添加当前页数到Model中
	    model.addAttribute("pageSize", pageSize); // 添加每页显示的项数到Model中

	    return "home";
	}



	@GetMapping("/content/{newsId}")
	public String showNewsContent(@PathVariable Long newsId, Model model) {
		// 创建一个 newsRequest 对象，设置需要的信息
		newsRequest request = new newsRequest();
		request.setNewsId(newsId.intValue());

		// 调用 newsService 的 FindNewsById 方法，传递 request 作为参数
		newsResponse newsItem = newsService.FindNewsById(request);

		// 格式化日期，仅保留日期部分
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = newsItem.getPublicTime().format(formatter);

		// 将格式化后的日期字符串添加到模型中
		model.addAttribute("formattedDate", formattedDate);

		// 将新闻内容添加到模型中，以便在 Thymeleaf 模板中使用
		model.addAttribute("newsItem", newsItem);

		// 返回视图名称，Thymeleaf 将使用该视图渲染页面
		return "content";
	}

	@GetMapping("/admincontent/{newsId}")
	public String showNewsContent1(@PathVariable Long newsId, Model model) {
		// 创建一个 newsRequest 对象，设置需要的信息
		newsRequest request = new newsRequest();
		request.setNewsId(newsId.intValue());

		// 调用 newsService 的 FindNewsById 方法，传递 request 作为参数
		newsResponse newsItem = newsService.FindNewsById(request);

		// 格式化日期，仅保留日期部分
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = newsItem.getPublicTime().format(formatter);

		// 将格式化后的日期字符串添加到模型中
		model.addAttribute("formattedDate", formattedDate);

		// 将新闻内容添加到模型中，以便在 Thymeleaf 模板中使用
		model.addAttribute("newsItem", newsItem);

		// 返回视图名称，Thymeleaf 将使用该视图渲染页面
		return "admincontent";
	}

	@GetMapping("/alternews/{newsId}")
	public String showUpdateNewsForm(@PathVariable Long newsId, Model model) {
		newsRequest request = new newsRequest();
		request.setNewsId(newsId.intValue());
		newsResponse newsItem = newsService.FindNewsById(request);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		String formattedDateTime = newsItem.getPublicTime().format(formatter);

		model.addAttribute("formattedDateTime", formattedDateTime);

		List<Category> categoriesWithNewsCount = categoryService.getAllCategoriesWithNewsCount();
		model.addAttribute("categoriesWithNewsCount", categoriesWithNewsCount);

		List<ParentCategory> parentCategoriesWithNewsCount = parentCategoryService
				.getAllParentCategoriesWithNewsCount();
		model.addAttribute("parentCategoriesWithNewsCount", parentCategoriesWithNewsCount);

		model.addAttribute("newsRequest", request); // 添加 newsRequest 到模型
		model.addAttribute("newsItem", newsItem); // 添加 newsItem 到模型

		return "alternews";
	}

	@PostMapping("/alternews")
	public String updateNews(@ModelAttribute newsRequest request, Model model) {
		newsResponse response = newsService.AlterNews(request);

		if (response.isSuccessful(response)) {
			model.addAttribute("message", response.getMessage());
		} else {
			model.addAttribute("message", response.getMessage());
		}

		// 确保将 newsRequest 对象添加到模型中
		model.addAttribute("newsRequest", request);

		// 添加 newsItem 对象到模型
		newsResponse newsItem = newsService.FindNewsById(request);
		model.addAttribute("newsItem", newsItem);

		return "redirect:/back";
	}

	@PostMapping("/back/deleteNews")
	public String deleteNews(@RequestParam(name = "newsIds", required = false) List<Integer> selectedNewsIds) {
		if (selectedNewsIds != null && !selectedNewsIds.isEmpty()) {
			for (Integer newsId : selectedNewsIds) {
				// 调用 DeleteNews 方法来删除选中的新闻
				newsRequest request = new newsRequest();
				request.setNewsId(newsId);
				newsService.DeleteNews(request);
			}
		} else {
			System.out.println("No newsIds provided in the request.");
		}
		// 重定向回管理页面或其他操作
		return "redirect:/back";
	}

	@GetMapping("/back")
	public String adminPage(Model model, @RequestParam(required = false) String searchTitle,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) String searchParentCategory,
			@RequestParam(required = false) String searchCategory,
			@RequestParam(defaultValue = "desc") String sortOrder, @RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "10", required = false) int itemsPerPage) {

		// 首先获取所有新闻
		List<newsResponse> newsResponseList = newsService.FindAllNews();

		// 然后根据筛选条件进行过滤
		if (searchTitle != null && !searchTitle.isEmpty()) {
			newsResponseList = newsResponseList.stream()
					.filter(news -> news.getTitle().toLowerCase().contains(searchTitle.toLowerCase()))
					.collect(Collectors.toList());
		}

		if ((startDate != null && !startDate.isEmpty()) || (endDate != null && !endDate.isEmpty())) {
			newsResponseList = newsResponseList.stream().filter(news -> {
				// 初始化日期范围
				LocalDateTime parsedStartDate = null;
				LocalDateTime parsedEndDate = null;

				// 解析开始日期字符串
				if (startDate != null && !startDate.isEmpty()) {
					parsedStartDate = LocalDateTime.parse(startDate + "T00:00:00"); // 添加时间部分
				}

				// 解析结束日期字符串
				if (endDate != null && !endDate.isEmpty()) {
					parsedEndDate = LocalDateTime.parse(endDate + "T23:59:59"); // 添加时间部分
				}

				// 检查日期范围，如果开始日期为空，则只检查结束日期，反之亦然
				return (parsedStartDate == null || news.getPublicTime().compareTo(parsedStartDate) >= 0)
						&& (parsedEndDate == null || news.getPublicTime().compareTo(parsedEndDate) <= 0);
			}).collect(Collectors.toList());
		}

		if (searchParentCategory != null && !searchParentCategory.isEmpty()) {
			newsResponseList = newsResponseList.stream()
					.filter(news -> news.getParentCategoryName().equals(searchParentCategory))
					.collect(Collectors.toList());
		}

		if (searchCategory != null && !searchCategory.isEmpty()) {
			newsResponseList = newsResponseList.stream().filter(news -> news.getCategoryName().equals(searchCategory))
					.collect(Collectors.toList());
		}

		// 检查 sortOrder 参数，如果提供了排序顺序参数，则根据参数进行排序
		if ("asc".equalsIgnoreCase(sortOrder)) {
			newsResponseList.sort(Comparator.comparing(news -> news.getPublicTime()));
		} else if ("desc".equalsIgnoreCase(sortOrder)) {
			newsResponseList.sort(Comparator.comparing(news -> news.getPublicTime(), Comparator.reverseOrder()));
		}

		// 计算总项目数
		int totalItems = newsResponseList.size();

		// 计算总页数
		int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

		// 限制页数，确保它不超出范围
		page = Math.max(1, Math.min(page, totalPages));

		// 计算实际的起始索引和结束索引
		int fromIndex = (page - 1) * itemsPerPage;
		int toIndex = Math.min(fromIndex + itemsPerPage, totalItems);

		// 获取当前页的项目
		List<newsResponse> pagedNews = newsResponseList.subList(fromIndex, toIndex);

		// 假设你遍历pagedNews
		for (newsResponse item : pagedNews) {
			LocalDateTime publicTime = item.getPublicTime();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = publicTime.format(formatter);
			item.setFormattedDate(formattedDate); // 将格式化后的日期添加到newsResponse对象中
		}

		// 获取所有类别数据（包括小分類）
		List<Category> categoriesWithNewsCount = categoryService.getAllCategoriesWithNewsCount();

		// 添加类别数据到Model中
		model.addAttribute("categoriesWithNewsCount", categoriesWithNewsCount);

		// 获取所有大类别数据（父类别）包括新闻数量
		List<ParentCategory> parentCategoriesWithNewsCount = parentCategoryService
				.getAllParentCategoriesWithNewsCount();

		// 添加大类别数据到 Model 中
		model.addAttribute("parentCategoriesWithNewsCount", parentCategoriesWithNewsCount);

		// 将数据添加到Model中，以便在Thymeleaf模板中使用
		model.addAttribute("news", pagedNews); // 使用分页后的新闻列表
		model.addAttribute("totalPages", totalPages); // 添加总页数到Model中
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("itemsPerPage", itemsPerPage); // 传递每页显示的项数

		// 返回Thymeleaf模板的名称，例如 "back"，以渲染页面
		return "back";
	}

	// 渲染添加新闻的页面或处理添加新闻的提交
	@GetMapping("/addnews")
	public String showAddNewsPage(@ModelAttribute newsRequest request, Model model) {
		// 获取所有类别数据（包括大类别和小类别）
		List<Category> categoriesWithNewsCount = categoryService.getAllCategoriesWithNewsCount();

		// 添加类别数据到模型中
		model.addAttribute("categoriesWithNewsCount", categoriesWithNewsCount);

		List<ParentCategory> parentCategoriesWithNewsCount = parentCategoryService
				.getAllParentCategoriesWithNewsCount();
		// 添加大类别数据到 Model 中
		model.addAttribute("parentCategoriesWithNewsCount", parentCategoriesWithNewsCount);

		// 如果 request 中包含新闻数据，则处理添加新闻的提交
		if (request != null) {
			newsResponse response = newsService.AddNews(request);
			model.addAttribute("message", response.getMessage());
		}

		return "addnews"; // 渲染添加新闻的页面
	}

	@PostMapping("/addnews")
	public String addNews(@ModelAttribute newsRequest request, Model model) {
		// 调用获取所有类别数据的服务方法
		List<Category> categoriesWithNewsCount = categoryService.getAllCategoriesWithNewsCount();
		// 添加类别数据到 Model 中
		model.addAttribute("categoriesWithNewsCount", categoriesWithNewsCount);

		// 调用获取所有大类别数据的服务方法
		List<ParentCategory> parentCategoriesWithNewsCount = parentCategoryService
				.getAllParentCategoriesWithNewsCount();
		// 添加大类别数据到 Model 中
		model.addAttribute("parentCategoriesWithNewsCount", parentCategoriesWithNewsCount);

		// 处理添加新闻请求并返回视图名称
		newsResponse response = newsService.AddNews(request);
		model.addAttribute("message", response.getMessage());

		return "redirect:/back"; 

	}
	
	

	// 显示所有新闻的Web页面
	@GetMapping("/all")
	public String showAllNews(Model model) {
		List<newsResponse> newsResponseList = newsService.FindAllNews();
		model.addAttribute("news", newsResponseList);
		return "all-news"; // 返回用于显示所有新闻的Thymeleaf视图
	}

	// 显示删除新闻的Web页面
	@GetMapping("/delete/{newsId}")
	public String showDeleteNewsForm(@PathVariable Long newsId, Model model) {
		newsRequest request = new newsRequest();
		request.setNewsId(newsId.intValue());
		newsResponse newsItem = newsService.FindNewsById(request);
		model.addAttribute("newsItem", newsItem);
		return "delete-news"; // 返回用于删除新闻的Thymeleaf视图
	}

	// 处理删除新闻的提交
	@PostMapping("/delete")
	public String deleteNews(@ModelAttribute newsRequest request, Model model) {
		newsResponse response = newsService.DeleteNews(request);
		model.addAttribute("message", response.getMessage());
		return "delete-news-result"; // 返回用于显示结果的Thymeleaf视图
	}

}
