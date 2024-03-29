■タイトル:自動シフト作成ツール
■概要： アルバイトが入力した希望シフトを確定シフトへ登録。

■機能
●ユーザー登録：(UsersController)
        ◦("/user/add")でユーザー登録(名前、アルバイトの時給設定、管理者アカウントの登録)。  
        ◦POSTリクエストを受け取ると、ユーザー情報がデータベースに挿入され、アルバイトかシフト作成者か管理者かを条件分岐して処理が行われます。

・ログイン機能:        
        ◦ユーザー登録した各アルバイトのログイン画面("user/login")でセッションへ格納し、ログイン。
        ◦入力されたユーザーIDとパスワードがデータベースと照合。
        ⇒管理者の場合は/admin/adminHomeへリダイレクトされます。
        ⇒通常ユーザーの場合は/home/userHomeへリダイレクトされます。

●希望シフト登録:(HomeController)
        ◦("/home/shiftAdd")でログインしたアルバイトの希望シフトを登録(日付、開始時間、終了時間、セッション内のユーザーID)。
        ◦希望シフト登録完了後、("/userHome")にて、ログインしたユーザーの希望シフト一覧が表示。
        
●確定シフト登録(AdminController)
        ◦ログイン機能時に管理者で登録したIDとパスワードでログイン後、("/adminHome")へページ遷移。
        ◦管理者ページからカレンダーの日付で各アルバイトが登録した内容を確定シフト("/addShift")へ登録。
        ◦1日毎に確定シフトへ登録可能。範囲指定日でも登録可能。
        ◦確定シフト登録後の内容確認("/ShiftList")。

■使用技術
◦Spring Boot
◦MySql
◦jquery-3.7.0,js

■開発環境
Eclipse version:2022-12

