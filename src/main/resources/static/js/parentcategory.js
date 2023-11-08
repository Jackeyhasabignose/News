document.addEventListener('DOMContentLoaded', function () {
    // data-parent-category-name 属性を含むすべての要素を取得
    var categoryElements = document.querySelectorAll('[data-parent-category-name]');

    // 各要素にクリックイベントハンドラを追加
    categoryElements.forEach(function (element) {
        element.addEventListener('click', function () {
            // data-parent-category-name の値を取得
            var parentCategoryName = element.getAttribute('data-parent-category-name');

            // parentCategoryName を次のページのコントローラメソッドに渡す
            window.location.href = '/findNewsByParentCategory?parentCategoryName=' + parentCategoryName;
        });
    });
});
