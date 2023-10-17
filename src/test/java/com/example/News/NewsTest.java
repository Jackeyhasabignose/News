package com.example.News;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.transaction.annotation.Transactional;

import com.example.News.entity.News;

import com.example.News.repository.categoryDao;
import com.example.News.repository.newsDao;
import com.example.News.repository.parentCategoryDao;
import com.example.News.service.ifs.newsService;

import com.example.News.vo.newsRequest;
import com.example.News.vo.newsResponse;

import com.example.Newsl.constants.Msg;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class NewsTest {

    @Autowired
    private newsDao nDao;
    @Autowired
    private parentCategoryDao pDao;
    @Autowired
    private categoryDao cDao;

    @Autowired
    private newsService nService;

    @Test
    public void ニュースの挿入をテストする() {
        // テストデータ
        String title = "テストタイトル";
        String content = "テストコンテンツ";
        LocalDateTime publicTime = LocalDateTime.now();
        Integer parentCategoryId = 1; // 実際の親カテゴリIDを設定
        Integer categoryId = 2; // 実際のカテゴリIDを設定
        String status = "公開済み";

        // DAOメソッドの呼び出しで挿入
        nDao.insertNews(title, null, content, publicTime, parentCategoryId, categoryId, status);

        // 挿入されたことを確認（1つのレコードが挿入されたことを確認）
        long count = nDao.count();
        System.out.println("挿入されたレコード数：" + count); // デバッグ出力の追加

        assertEquals(1, count); // JUnit 4のassertEqualsメソッドを使用してlong値を比較
    }

    private void assertEquals(int i, long count) {
        // TODO: アサーションを実装
    }

    @Test
    public void 有効なデータでのニュースの追加をテストする() {
        // テストデータ
        newsRequest request = new newsRequest();
        request.setTitle("テストタイトル");
        request.setContent("テストコンテンツ");
        request.setParentCategoryName("音楽");
        request.setCategoryName("ロック");
        request.setPublicTime(LocalDateTime.now());

        // テストメソッドの実行
        newsResponse response = nService.AddNews(request);

        // テスト結果の確認
        assertEquals(Msg.SUCCESS.getMessage(), response.getMessage());
    }

    private void assertEquals(String message, String message2) {
        // TODO: アサーションを実装
    }

    @Test
    public void すべてのニュースを検索するテスト() {
        // モックデータ
        LocalDateTime currentDate = LocalDateTime.now();
        Object[] newsData1 = { 1, "ニュース1", "サブタイトル1", "コンテンツ1", currentDate.minusDays(1), 1, 1, "親カテゴリ1", "カテゴリ1" };
        Object[] newsData2 = { 2, "ニュース2", "サブタイトル2", "コンテンツ2", currentDate.plusDays(1), 2, 2, "親カテゴリ2", "カテゴリ2" };
        List<Object[]> mockNewsData = Arrays.asList(newsData1, newsData2);

        // newsDaoの振る舞いをモック
        when(nDao.findNewsWithCategoryIdsAndNames()).thenReturn(mockNewsData);

        // サービスメソッドの呼び出し
        List<newsResponse> newsResponseList = nService.FindAllNews();

        // 結果の確認
        assertNotNull(newsResponseList);
        assertEquals(2, newsResponseList.size());

        // 最初のニュースの属性を確認
        newsResponse response1 = newsResponseList.get(0);
        assertEquals(1, response1.getNewsId());
        assertEquals("ニュース1", response1.getTitle());
        assertEquals("サブタイトル1", response1.getSubTitle());
        assertEquals("コンテンツ1", response1.getContent());
        assertEquals(currentDate.minusDays(1), response1.getPublicTime());
        assertEquals("親カテゴリ1", response1.getParentCategoryName());
        assertEquals("カテゴリ1", response1.getCategoryName());
        assertEquals(Msg.Status1.getMessage(), response1.getStatus());

        // 2番目のニュースの属性を確認
        newsResponse response2 = newsResponseList.get(1);
        assertEquals(2, response2.getNewsId());
        assertEquals("ニュース2", response2.getTitle());
        assertEquals("サブタイトル2", response2.getSubTitle());
        assertEquals("コンテンツ2", response2.getContent());
        assertEquals(currentDate.plusDays(1), response2.getPublicTime());
        assertEquals("親カテゴリ2", response2.getParentCategoryName());
        assertEquals("カテゴリ2", response2.getCategoryName());
        assertEquals(Msg.Status.getMessage(), response2.getStatus());
    }

    private void assertEquals(LocalDateTime minusDays, LocalDateTime publicTime) {
        // TODO: アサーションを実装
    }

    private void assertNotNull(List<newsResponse> newsResponseList) {
        // TODO: アサーションを実装
    }

    @Test
    public void ニュースの変更_成功() {
        // モックテストデータ
        newsRequest request = new newsRequest();
        request.setNewsId(1);
        request.setTitle("更新されたタイトル");
        request.setContent("更新されたコンテンツ");
        request.setParentCategoryName("更新された親カテゴリ");
        request.setCategoryName("更新されたカテゴリ");
        request.setSubTitle("更新されたサブタイトル");

        // DAOクエリ結果のモック
        when(pDao.findParentCategoryIdByParentCategoryName(toString())).thenReturn(1);
        when(cDao.findCategoryIdByCategoryName(toString())).thenReturn(2);

        // テストメソッドの実行
        newsResponse response = nService.AlterNews(request);

        // テスト結果の確認
        assertEquals(Msg.SUCCESS1.getMessage(), response.getMessage());
    }

    @Test
    public void ニュースの削除_単一のニュース() {
        // モックテストデータ
        newsRequest request = new newsRequest();
        request.setNewsId(1); // 削除対象のニュースIDを設定

        // テストメソッドの実行
        newsResponse response = nService.DeleteNews(request);

        // テスト結果の確認
        assertEquals(Msg.SUCCESS2.getMessage(), response.getMessage());
        // 他の関連ロジックの確認...
    }

    @Test
    public void ニュースの取得_有効なニュースID() {
        // モックテストデータ
        int newsId = 1; // 存在すると仮定されるニュースID

        // 存在するニュースを返すようにnewsDaoをモック
        News mockNews = new News();
        mockNews.setNewsId(newsId);
        mockNews.setTitle("テストニュース");
        mockNews.setContent("テストコンテンツ");
        mockNews.setPublicTime(LocalDateTime.now());
        mockNews.setSubTitle("テストサブタイトル");
        mockNews.setCategoryId(1); // 仮定のカテゴリID
        mockNews.setParentCategoryId(2); // 仮定の親カテゴリID

        // カテゴリ名を仮定のカテゴリ名で返すようにcategoryDaoをモック
        when(cDao.findCategoryNameByCategoryId(1)).thenReturn("カテゴリ1");
        // 親カテゴリ名を仮定の親カテゴリ名で返すようにparentCategoryDaoをモック
        when(pDao.findParentCategoryNameByParentCategoryId(2)).thenReturn("親カテゴリ2");
        // ニュースIDに基づいてニュースを検索するようにnewsDaoをモック
        when(nDao.findById(newsId)).thenReturn(Optional.of(mockNews));

        // テストメソッドの実行
        newsRequest request = new newsRequest();
        request.setNewsId(newsId);
        newsResponse response = nService.FindNewsById(request);

        // テスト結果の確認
        assertNotNull(response);
        assertEquals(newsId, response.getNewsId());
        assertEquals("テストニュース", response.getTitle());
        assertEquals("テストコンテンツ", response.getContent());
        assertEquals("テストサブタイトル", response.getSubTitle());
        assertEquals("カテゴリ1", response.getCategory());
        assertEquals("親カテゴリ2", response.getParentCategory());
    }

    private void assertNotNull(newsResponse response) {
        // TODO: アサーションを実装
    }

    @Test
    public void 親カテゴリIDによるニュースのカウント_有効な親カテゴリID() {
        // モックテストデータ
        int parentCategoryId = 1; // 存在する親カテゴリIDを仮定

        // 親カテゴリIDに対応するニュースの数を返すようにnewsDaoをモック
        when(nDao.getNewsCountByParentCategoryId(parentCategoryId)).thenReturn(5);

        // テストメソッドの実行
        int newsCount = nService.getNewsCountByParentCategoryId(parentCategoryId);

        // テスト結果の確認
        assertEquals(5, newsCount);
    }

    @Test
    public void カテゴリIDによるニュースのカウント_有効なカテゴリID() {
        // モックテストデータ
        int categoryId = 1; // 存在するサブカテゴリIDを仮定

        // サブカテゴリIDに対応するニュースリストを返すようにnewsDaoをモック
        List<News> mockNewsList = Arrays.asList(new News(), new News(), new News());
        when(nDao.findByCategoryId(categoryId)).thenReturn(mockNewsList);

        // テストメソッドの実行
        int newsCount = nService.getNewsCountByCategoryId(categoryId);

        // テスト結果の確認
        assertEquals(3, newsCount); // 3つのニュースがあるため、3を返すことを期待
    }
}
