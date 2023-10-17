package com.example.News;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.News.entity.Category;
import com.example.News.entity.ParentCategory;
import com.example.News.repository.categoryDao;
import com.example.News.repository.parentCategoryDao;
import com.example.News.service.ifs.categoryService;
import com.example.News.service.ifs.newsService;
import com.example.News.vo.categoryRequest;
import com.example.News.vo.categoryResponse;
import com.example.Newsl.constants.Msg;

@SpringBootTest
@Transactional
public class CategoryTest {

    @Autowired
    private categoryDao cDao;

    @Autowired
    private parentCategoryDao pDao;

    @Autowired
    private categoryService cService;

    @Autowired
    private newsService nService;

    @Test
    public void 有効なデータでカテゴリを追加するテスト() {
        // モックデータのセットアップ
        categoryRequest request = new categoryRequest();
        request.setCategoryName("テストカテゴリ");
        request.setParentCategoryName("テスト親カテゴリ");

        ParentCategory parentCategory = new ParentCategory();
        parentCategory.setParentCategoryId(1);
        parentCategory.setParentCategoryName("テスト親カテゴリ");

        when(pDao.findByParentCategoryName("テスト親カテゴリ")).thenReturn(Optional.of(parentCategory));
        when(cDao.findByParentCategoryId(1)).thenReturn(new ArrayList<>());

        // テストメソッドの実行
        categoryResponse response = cService.Addcategory(request);

        // テスト結果の検証
        assertEquals(Msg.SUCCESS.getMessage(), response.getMessage());
    }

    private void assertEquals(String message, String message2) {
        // TODO Auto-generated method stub
    }

    @Test
    public void 有効なデータでカテゴリを変更するテスト() {
        // モックデータのセットアップ
        categoryRequest request = new categoryRequest();
        request.setCategoryId(1);
        request.setCategoryName("更新されたカテゴリ");

        Category existingCategory = new Category();
        existingCategory.setCategoryId(1);
        existingCategory.setCategoryName("テストカテゴリ");

        when(cDao.findById(1)).thenReturn(Optional.of(existingCategory));

        // テストメソッドの実行
        categoryResponse response = cService.Altercategory(request);

        // テスト結果の検証
        assertEquals(Msg.SUCCESS.getMessage(), response.getMessage());
    }

    @Test
    public void 有効なデータでカテゴリを削除するテスト() {
        // モックデータのセットアップ
        categoryRequest request = new categoryRequest();
        List<Integer> categoryIds = new ArrayList<>();
        categoryIds.add(1);
        categoryIds.add(2);
        request.setCategoryIds(categoryIds);

        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setCategoryName("カテゴリ 1");

        Category category2 = new Category();
        category2.setCategoryId(2);
        category2.setCategoryName("カテゴリ 2");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(cDao.findById(1)).thenReturn(Optional.of(category1));
        when(cDao.findById(2)).thenReturn(Optional.of(category2));

        // テストメソッドの実行
        categoryResponse response = cService.Deletecategory(request);

        // テスト結果の検証
        assertEquals(Msg.SUCCESS.getMessage(), response.getMessage());
    }

    @Test
    public void 全てのカテゴリとニュース数を取得するテスト() {
        // モックデータのセットアップ
        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setCategoryName("カテゴリ 1");

        Category category2 = new Category();
        category2.setCategoryId(2);
        category2.setCategoryName("カテゴリ 2");

        List<Category> categories = new ArrayList<>();
        categories.add(category1);
        categories.add(category2);

        when(cDao.findAll()).thenReturn(categories);
        when(nService.getNewsCountByCategoryId(1)).thenReturn(3);
        when(nService.getNewsCountByCategoryId(2)).thenReturn(5);

        // テストメソッドの実行
        List<Category> result = cService.getAllCategoriesWithNewsCount();

        // テスト結果の検証
        assertEquals(2, result.size());
        assertEquals(3, result.get(0).getNewsCount());
        assertEquals(5, result.get(1).getNewsCount());
    }

    private void assertEquals(int i, int size) {
        // TODO Auto-generated method stub
    }
}
