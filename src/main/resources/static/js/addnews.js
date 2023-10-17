document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    form.addEventListener("submit", function (event) {
        event.preventDefault();
        const action = form.getAttribute("action");
        const formData = new FormData(form);
        const xhr = new XMLHttpRequest();
        xhr.open("POST", action, true);

        xhr.onload = function () {
            if (xhr.status >= 200 && xhr.status < 300) {
                const urlParams = new URLSearchParams(window.location.search);
                if (urlParams.get("success") === "true") {
                    // 追加成功時、カスタム操作を実行
                    // メッセージを表示したり、他の操作を実行できる
                } else {
                    // 他のケースを処理
                }
            }
        };

        xhr.send(formData);
    });
});

function previewNews() {
    // isPreviewVisibleをtrueに設定してプレビューコンテンツを表示
    document.getElementById("isPreviewVisible").value = "true";

    // 他のフォームフィールドの値を取得してコンソールに出力
    var parentCategoryName = document.getElementById("searchParentCategory").value;
    var categoryName = document.getElementById("searchCategory").value;
    var publicTime = document.getElementById("publicTime").value;
    var title = document.getElementById("title").value;
    var subTitle = document.getElementById("subTitle").value;
    var content = document.getElementById("content").value;

    // 値をコンソールに出力
    console.log("parentCategoryName:", parentCategoryName);
    console.log("categoryName:", categoryName);
    console.log("publicTime:", publicTime);
    console.log("title:", title);
    console.log("subTitle:", subTitle);
    console.log("content:", content);
}
