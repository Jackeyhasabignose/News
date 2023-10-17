function submitForm(event) {
    event.preventDefault(); // デフォルトのフォーム送信を防止
    var selectedDateInput = document.getElementById('selectedDate');
    var startDateInput = document.getElementById('startDate');
    selectedDateInput.value = startDateInput.value; // 日付フィールドの値を非表示フィールドにコピー
    document.querySelector('form').submit(); // フォームを送信
}

function navigateToNewsDetail(newsId) {
    // ここでJavaScript関数のロジックを記述
    // ニュース詳細ページへのナビゲーションなどの操作を実行できる
    console.log("ニュース詳細ページへのナビゲーション、newsId：" + newsId);

    // カスタム操作を実行するためのロジックを追加
    // たとえば、ニュース詳細ページのURLが/content/{newsId}の場合、次のコードを使用してそのページにナビゲーションできます
    window.location.href = "/admincontent/" + newsId;
}

function deleteSelectedNews() {
    // 選択したニュースのIDを収集するためのJavaScriptを使用
    var selectedNewsIds = [];
    var checkboxes = document.getElementsByName('newsIds');
    for (var i = 0; i < checkboxes.length; i++) {
        if (checkboxes[i].checked) {
           selectedNewsIds.push(parseInt(checkboxes[i].value));
        }
    }
    
    // デバッグ用に選択したニュースのIDを出力
    console.log('選択したニュースのID:', selectedNewsIds);
    console.log('削除ボタンがクリックされました！'); // この行のコードを追加
    
    // 選択したニュースのIDをnewsIdsパラメータの値として設定
    document.getElementById('selectedNewsIds').value = selectedNewsIds.join(',');
}

document.getElementById("addNewsLink").addEventListener("click", function(event) {
  event.preventDefault(); // デフォルトのリンク動作を阻止

  // 隠しフォームを作成
  var form = document.createElement("form");
  form.method = "POST";
  form.action = "/addnews";
  
  // 送信ボタンを追加
  var submitButton = document.createElement("input");
  submitButton.type = "submit";
  submitButton.style.display = "none";
  
  // フォームとボタンをドキュメントに追加
  form.appendChild(submitButton);
  document.body.appendChild(form);
  
  // フォームを送信
  form.submit();
});

document.addEventListener("DOMContentLoaded", function () {
    const editButton = document.getElementById("editButton");
    if (editButton) {
        editButton.addEventListener("click", function () {
            // ニュースIDを取得します。ニュースの一意の識別子を格納するnewsId変数を持っていると仮定して
            const newsId = 123; // 実際のニュースIDに置き換える必要があります
            
            // 編集ページのURLを構築し、newsIdをクエリパラメータとして渡する
            const editURL = `/alternews/${newsId}`;
            
            // 編集ページにリダイレクト
            window.location.href = editURL;
        });
    }
});

// DOMContentLoaded イベントでイベントハンドラをバインド
document.addEventListener("DOMContentLoaded", function() {
  // ドロップダウン要素を取得
  const itemsPerPageSelect = document.querySelector('#itemsPerPage1');
  
  // ドロップダウンにイベントハンドラを追加
  itemsPerPageSelect.addEventListener('change', submitForm2);
});

// ドロップダウンの値の変更を処理する関数
function submitForm2(event) {
  console.log('submitForm2 が呼ばれた'); // 関数内でログメッセージを追加

  // デフォルトのフォーム送信を防止
  event.preventDefault();
  
  // ユーザーが選択したニュースの数を取得
  const selectedItemsPerPage = document.querySelector('#itemsPerPage1').value;

  // 選択されたニュースの数をクエリパラメータとして含むリクエストURLを構築
  const requestURL = '/back/itemsPerPage?itemsPerPage=' + selectedItemsPerPage;

  // バックエンドにリクエストを送信する（fetchまたは他のAjaxメソッドを使用できる）
  fetch(requestURL)
    .then(response => response.json())
    .then(data => {
      // バックエンドから返されたデータを処理する部分
      // ページを更新するためのロジックをここに追加できる
    })
    .catch(error => {
      console.error('エラー:', error);
    });
}
