document.addEventListener('DOMContentLoaded', function () {
    // data-category-name 属性を含むすべての要素を取得
    var categoryElements = document.querySelectorAll('[data-category-name]');

    // 各要素にクリックイベントハンドラを追加
    categoryElements.forEach(function (element) {
        element.addEventListener('click', function () {
            // data-category-name の値を取得
            var categoryName = element.getAttribute('data-category-name');

            // categoryName を次のページのコントローラメソッドに渡す
            window.location.href = '/findNewsByCategory?categoryName=' + categoryName;
        });
    });
});
