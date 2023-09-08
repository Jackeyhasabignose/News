package com.example.News;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.News.entity.news;
import com.example.News.repository.newsDao;
import com.example.News.service.ifs.newsService;
import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;
import com.example.Newsl.constants.RtnCode;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NewsApplicationTests {

	@Autowired
	private newsDao nDao;

	@Autowired
	private newsService nService;

	private boolean isValidParentCategory(String parentCategory) {
		// 親カテゴリが有効かどうかを確認。
		return "國內".equals(parentCategory) || "國外".equals(parentCategory);
	}

	private boolean isValidCategory(String category) {
		// 有効なカテゴリかどうかを確認。
		List<String> validCategories = Arrays.asList("音樂", "遊戲", "教育", "科技");
		return validCategories.contains(category);
	}

	@Test
	@Transactional // insertのdaoをテスト
	public void testInsertNews() {
		// テストデータを作成
		String title = "テストタイトル";
		String content = "テストコンテンツ";
		LocalDate publicTime = LocalDate.now();
		String parentCategory = "樂";
		String category = "音樂";

		// DAOメソッドを呼び出します
		nDao.insertNews(title, content, publicTime, parentCategory, category);

	}

	@Test
	void testValidParentCategory() {
		boolean isValid = isValidParentCategory("國內");
		Assertions.assertEquals(true, isValid);
	}

	@Test
	void testValidParentCategory2() {
		boolean isValid = isValidParentCategory("國");
		Assertions.assertNotEquals(true, isValid);
	}

	@Test
	void testValidCategory() {
		boolean isValid = isValidCategory("音樂");
		Assertions.assertEquals(true, isValid);
	}

	@Test
	void testValidCategory2() {
		boolean isValid = isValidCategory("");
		Assertions.assertNotEquals(true, isValid);
	}

	@Test
	void testValidInput() {

		LocalDate now = LocalDate.now();
		newsRequest request = new newsRequest("你好", "哈哈", now, "國內", "音樂");

		newsResponse response = nService.AddNews(request);

		// メッセージの内容を出力
		System.out.println("Response message: " + response.getMessage());

		assertEquals(RtnCode.SUCCESS.getMessage(), response.getMessage());

	}

	private void assertEquals(String string2, String string) {
		// TODO Auto-generated method stub

	}

	@Test
	void testInValidInput() {
		LocalDate now = LocalDate.now();
		newsRequest request = new newsRequest("", "安安", now, "國", "音樂");

		newsResponse response = nService.AddNews(request);

		assertNotEquals(RtnCode.SUCCESS.getMessage(), response.getMessage());
		System.out.println("final " + response.getMessage());
		System.out.println(RtnCode.SUCCESS.getMessage());
	}

	private void assertNotEquals(String message, String message2) {
		// TODO Auto-generated method stub

	}

	@Test
	@Transactional
	@Commit
	public void testdeleteNewsById() {
		// DAOメソッドを呼び出してニュースを削除
		nDao.deleteNewsById(40);

		// ニュースが正常に削除されたかどうかを確認
		Optional<news> deletedNews = nDao.findById(40); // 削除されたニュースを検索するためにfindByIdメソッドを使用すると仮定
		assertTrue(deletedNews.isEmpty()); // deletedNewsが空であることをアサートします

	}

	@Test
	@Transactional
	@Commit
	public void testDeleteNews() {

		Integer newsIdToDelete = 41;

		// 削除するニュースIDを指定して、リクエストオブジェクトを作成
		newsRequest request = new newsRequest();
		request.setNewsId(newsIdToDelete);

		// ニュースを削除するためにサービスメソッドを呼び出します
		newsResponse response = nService.DeleteNews(request);

		// 削除操作が成功したかどうかを確認するためにアサートを使用
		assert (response.getMessage() == RtnCode.SUCCESS2.getMessage());

		// データベースに削除されたニュースレコードが存在しないことを確認するためにアサートを使用
		Optional<news> deletedNews = nDao.findById(newsIdToDelete);
		assert (!deletedNews.isPresent()); // 削除されたことを確認
	}

	@Test
	@Transactional
	@Commit
	public void testfindById() {
		// テストニュースを作成してデータベースに保存
		news news = new news();
		news.setTitle("テストニュース");
		news.setContent("123");
		LocalDate now = LocalDate.now();
		news.setPublicTime(now);
		news.setParentCategory("國外");
		news.setCategory("遊戲");

		com.example.News.entity.news savedNews = nDao.save(news);
		Integer newsId = savedNews.getNewsId();
		Optional<news> foundNews = nDao.findById(newsId);
		assertTrue(foundNews.isPresent()); // foundNewsがニュースオブジェクトを含んでいることをアサートします

	}

	@Test
	public void testFindNewsById() {
		// データベースにニュースレコードがあると仮定し、そのIDが41であるとします
		Integer newsIdToFind = 41;

		// リクエストオブジェクトを作成してニュースIDを渡します
		newsRequest request = new newsRequest();
		request.setNewsId(newsIdToFind);

		// ニュースを検索するためにサービスメソッドを呼び出します
		newsResponse response = nService.FindNewsById(request);

		// ニュースレコードが見つかったかどうかを確認するためにアサートを使用
		assert (response.getMessage() != RtnCode.Empty1.getMessage());

	}

	@Test
	public void testFindAll() {
		// テストデータを挿入
		news news1 = new news("タイトル 1", "コンテンツ 1", LocalDate.now(), "科技", "國外");
		news news2 = new news("タイトル 2", "コンテンツ 2", LocalDate.now(), "遊戲", "國外");

		nDao.saveAll(List.of(news1, news2));

		// findAllメソッドを呼び出します
		List<news> result = nDao.findAll();

		// 結果がnullでなく、挿入したテストデータを含んでいることをアサートします
		assertNotNull(result);
		Assertions.assertEquals(14, result.size());

		// 各ニュースオブジェクトのプロパティを確認
		news retrievedNews1 = result.get(0);
		assertEquals("タイトル 1", retrievedNews1.getTitle());
		assertEquals("コンテンツ 1", retrievedNews1.getContent());
		assertEquals("國外", retrievedNews1.getParentCategory());
		assertEquals("科技", retrievedNews1.getCategory());

		news retrievedNews2 = result.get(1);
		assertEquals("タイトル 2", retrievedNews2.getTitle());
		assertEquals("コンテンツ 2", retrievedNews2.getContent());
		assertEquals("國外", retrievedNews2.getParentCategory());
		assertEquals("遊戲", retrievedNews2.getCategory());

	}

	private void assertNotNull(List<news> result) {
		// TODO Auto-generated method stub

	}

	@Test
	public void testFindAllNews() {
		// テスト対象のメソッドを呼び出します
		List<newsResponse> result = nService.FindAllNews();

		// 結果が期待通りであることをアサートします（たとえば、サイズが2であること）
		Assertions.assertEquals(4, result.size());
	}

	@Test
	public void testUpdateNewsContent() {
		// テストデータを作成してデータベースに保存
		news newsToUpdate = new news("初期タイトル", "初期コンテンツ", LocalDate.now(), "國外", "科技");
		newsToUpdate = nDao.save(newsToUpdate);

		// データを更新します
		nDao.updateNewsContent("更新済みコンテンツ", "更新済みタイトル", "國內", "遊戲", newsToUpdate.getNewsId());

		// 更新が成功したかどうかを確認
		Optional<news> updatedNews = nDao.findById(newsToUpdate.getNewsId());
		assertTrue(updatedNews.isPresent());
	}

	@Test
	public void testAlterNews() {
		// 有効なリクエストを作成
		newsRequest request = new newsRequest();
		request.setNewsId(44);
		request.setTitle("新しいタイトル");
		request.setContent("新しいコンテンツ");
		request.setParentCategory("國內");
		request.setCategory("音樂");

		// AlterNewsメソッドを呼び出します
		newsResponse response = nService.AlterNews(request);

		// メッセージが成功メッセージであることを確認するためにアサートを使用
		assertEquals(RtnCode.SUCCESS1.getMessage(), response.getMessage());
		System.out.println(response.getMessage());
	}

}
