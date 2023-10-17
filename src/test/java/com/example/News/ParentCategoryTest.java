package com.example.News;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.News.entity.ParentCategory;
import com.example.News.repository.parentCategoryDao;
import com.example.News.service.ifs.newsService;
import com.example.News.service.ifs.parentCategoryService;
import com.example.News.vo.parentCategoryRequest;
import com.example.News.vo.parentCategoryResponse;
import com.example.Newsl.constants.Msg;

@SpringBootTest
@Transactional
public class ParentCategoryTest {

    @Autowired
    private parentCategoryService pService;
    @Autowired
    private parentCategoryDao pDao;
    
    @Autowired
    private newsService nService;

    @Test
    public void 有効なリクエストで親カテゴリを追加するテスト() {
        // テストデータ
        parentCategoryRequest request = new parentCategoryRequest();
        request.setParentCategoryName("有効なカテゴリ");
        ParentCategory parentCategory = new ParentCategory();

        // parentCategoryDao の振る舞いをモック
        Mockito.when(pDao.save(Mockito.any(ParentCategory.class))).thenReturn(parentCategory);

        // テストメソッドの実行
        parentCategoryResponse response = pService.AddparentCategory(request);

        // テスト結果の検証
        assertEquals(Msg.SUCCESS.getMessage(), response.getMessage());
    }

    private void assertEquals(String message, String message2) {
        // TODO Auto-generated method stub
        
    }

    @Test
    public void 有効でないリクエストで親カテゴリを追加するテスト() {
        // テストデータ: リクエストがnull
        parentCategoryRequest request = null;

        // テストメソッドの実行
        parentCategoryResponse response = pService.AddparentCategory(request);

        // テスト結果の検証
        assertEquals(Msg.Empty.getMessage(), response.getMessage());
    }

    @Test
    public void 有効なリクエストで親カテゴリを変更するテスト() {
        // テストデータ
        parentCategoryRequest request = new parentCategoryRequest();
        request.setParentCategoryId(1); // 存在する親カテゴリID
        request.setParentCategoryName("更新されたカテゴリ");

        // parentCategoryDao のID検索の結果をモック
        when(pDao.findById(1)).thenReturn(Optional.of(new ParentCategory()));

        // parentCategoryDao の振る舞いをモック
        when(pDao.save(any(ParentCategory.class))).thenReturn(new ParentCategory());

        // テストメソッドの実行
        parentCategoryResponse response = pService.AlterparentCategory(request);

        // テスト結果の検証
        assertEquals(Msg.SUCCESS.getMessage(), response.getMessage());
    }

    @Test
    public void 有効なリクエストで親カテゴリを削除するテスト() {
        // テストデータ
        parentCategoryRequest request = new parentCategoryRequest();
        request.setParentCategoryIds(Arrays.asList(1, 2, 3)); // 存在する親カテゴリIDのリスト

        // parentCategoryDao のID検索の結果をモック
        when(pDao.findById(any(Integer.class))).thenReturn(Optional.of(new ParentCategory()));

        // テストメソッドの実行
        parentCategoryResponse response = pService.DeleteparentCategory(request);

        // テスト結果の検証
        assertEquals(Msg.SUCCESS.getMessage(), response.getMessage());
    }

    @Test
    public void 全ての親カテゴリとニュース数を取得するテスト() {
        // テストデータ
        ParentCategory parentCategory1 = new ParentCategory();
        parentCategory1.setParentCategoryId(1);
        ParentCategory parentCategory2 = new ParentCategory();
        parentCategory2.setParentCategoryId(2);
        List<ParentCategory> parentCategories = Arrays.asList(parentCategory1, parentCategory2);

        // parentCategoryDao の全検索の結果をモック
        when(pDao.findAll()).thenReturn(parentCategories);

        // newsService.getNewsCountByParentCategoryId メソッドのモック
        when(nService.getNewsCountByParentCategoryId(1)).thenReturn(5);
        when(nService.getNewsCountByParentCategoryId(2)).thenReturn(10);

        // テストメソッドの実行
        List<ParentCategory> result = pService.getAllParentCategoriesWithNewsCount();

        // テスト結果の検証
        assertNotNull(result);
        assertEquals(2, result.size());

        ParentCategory resultCategory1 = result.get(0);
        assertEquals(1, resultCategory1.getParentCategoryId());
        assertEquals(5, resultCategory1.getNewsCount());

        ParentCategory resultCategory2 = result.get(1);
        assertEquals(2, resultCategory2.getParentCategoryId());
        assertEquals(10, resultCategory2.getNewsCount());
    }

    private void assertEquals(int i, int size) {
        // TODO Auto-generated method stub
        
    }

    private void assertNotNull(List<ParentCategory> result) {
        // TODO Auto-generated method stub
        
    }

}
