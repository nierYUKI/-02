// グローバル変数として年と月を定義
var selectedYear;
var selectedMonth;

const week = ["日", "月", "火", "水", "木", "金", "土"];
const today = new Date();
var year;
var month;
console.log(year);
console.log(month);
// 月末だとずれる可能性があるため、1日固定で取得
var showDate = new Date(today.getFullYear(), today.getMonth(), 1);

// 初期表示
window.onload = function () {
    showProcess(today);
};

// 前の月を表示
function prev(){
    showDate.setMonth(showDate.getMonth() - 1);
    showProcess(showDate);
}

// 次の月を表示
function next(){
    showDate.setMonth(showDate.getMonth() + 1);
    showProcess(showDate);
}

// カレンダー表示
function showProcess(date) {
    year = date.getFullYear();
    month = date.getMonth();
    selectedYear = year; // グローバル変数に年を設定
    selectedMonth = month; // グローバル変数に月を設定
    console.log(year);
    console.log(month);
    document.querySelector('#header').innerHTML = year + "年 " + (month + 1) + "月";
    var calendar = createProcess(year, month);
    document.querySelector('#calendar').innerHTML = calendar;

    // 日付ボタンのクリックイベントを設定
    $('.button-num').off('click').on('click', function(){
        var selectedDate = this.textContent; // クリックされたボタンのテキストを取得

        // 日付のフォーマットを変換（例：2024-02-24）
        var formattedDate = formatDate(selectedDate);

        // フォームの値を変更
        $('input[name="selectDate"]').val(formattedDate);

        // フォームを送信
        $('#dateForm').submit(); // 修正箇所: フォームを送信するためにフォームのセレクタを正しく指定します
    });
}

// カレンダー作成
function createProcess(year, month) {
    // 曜日
    var calendar = "<table><tr class='dayOfWeek'>";
    for (var i = 0; i < week.length; i++) {
        calendar += "<th>" + week[i] + "</th>";
    }
    calendar += "</tr>";

    var count = 0;
    var startDayOfWeek = new Date(year, month, 1).getDay();
    var endDate = new Date(year, month + 1, 0).getDate();
    var lastMonthEndDate = new Date(year, month, 0).getDate();
    var row = Math.ceil((startDayOfWeek + endDate) / week.length);

    // 1行ずつ設定
    for (var i = 0; i < row; i++) {
        calendar += "<tr>";
        // 1colum単位で設定
        for (var j = 0; j < week.length; j++) {
            if (i == 0 && j < startDayOfWeek) {
                // 1行目で1日まで先月の日付を設定
                calendar += "<td class='disabled'><button class='button-num'>" + (lastMonthEndDate - startDayOfWeek + j + 1) + "</button></td>";
            } else if (count >= endDate) {
                // 最終行で最終日以降、翌月の日付を設定
                count++;
                calendar += "<td class='disabled'><button class='button-num'>" + (count - endDate) + "</button></td>";
            } else {
                // 当月の日付を曜日に照らし合わせて設定
                count++;
                if(year == today.getFullYear() && month == today.getMonth() && count == today.getDate()){
                    calendar += "<td class='today'><button class='button-num'>" + count + "</button></td>";
                } else {
                    calendar += "<td><button class='button-num'>" + count + "</button></td>";
                }
            }
        }
        calendar += "</tr>";
    }
    return calendar;
}

// 日付のフォーマットを変換する関数
function formatDate(selectedDate) {
    var year = selectedYear; // グローバル変数から年を取得
    var month = ('0' + (selectedMonth + 1)).slice(-2); // グローバル変数から月を取得し、ゼロパディング
    var day = ('0' + selectedDate).slice(-2); // 日のゼロパディング
    var formattedDate = year + "-" + month + "-" + day; // フォーマット変換
    return formattedDate;
};
