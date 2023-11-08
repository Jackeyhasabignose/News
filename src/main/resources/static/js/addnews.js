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
                    // 追加成功時にポップアップメッセージを表示
                    alert("追加に成功しました！");
                } else {
                    // 他の処理ロジック
                }
            }
        };
        
        // リクエストを送信
        xhr.send(formData);
    });
});
