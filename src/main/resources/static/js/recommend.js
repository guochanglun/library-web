/**
 * Created by 14557 on 2017/4/28.
 */
$(function () {

    $.get('/books/recommendLocalBook', function (data) {

        eles = '';
        data.forEach(function (book, index) {
            eles += '<a href="javascript:;" class="collection-item">'
                + '<span class="title">' + (index + 1) + '&rightarrow;' + book.bookName + '</span>'
                + '<p>'
                + book.num + '<br>'
                + book.author + '<br>'
                + book.publishInfo + '<br>'
                + book.count + '<br>'
                + book.available + '<br>'
                + '</p>'
                + '</a>';
        });

        if (data.length == 0) {
            eles = '<li class="collection-item">呀，关于你的搜索信息太少了，没办法做出推荐呀！去<a href="/home">搜索</a>一下吧</li>';
        }

        $('#local').append(eles);

    });

    $.get('/books/recommendMachineBook', function (data) {

        eles = '';
        data.forEach(function (book, index) {
            eles += '<a href="javascript:;" class="collection-item">'
                + '<span class="title">' + (index + 1) + '&rightarrow;' + book.title + '</span>'
                + '<p>'
                + 'ISBN: ' + book.isbn + '<br>'
                + '出版时间: ' + book.pubtime + '<br>'
                + '出版商: ' + book.publishInfo + '<br>'
                + '</p>'
                + '</a>';
        });

        if (data.length == 0) {
            eles = '<li class="collection-item">我们是在凌晨为您做出智能推荐的，明天来看看吧！</li>';
        }

        $('#ml').append(eles);

    });

    // 退出登录
    $('.logout').click(function () {
        $.get('/user/logout', function (data) {
            if (data) {
                Materialize.toast('安全退出', 1000)
                location = '/'
            } else {
                Materialize.toast('退出失败，请重试', 2000)
            }
        })
    });

    // 提交建议
    $('#btn-advice').click(function () {
        var advice = $('#advice').val();
        if (advice == '') return;
        $.post('/advice', {advice: advice}, function (data) {
            if (data) {
                $('#advice-modal').closeModal();
                Materialize.toast('感谢您的建议', 2000)
            }
        })
    });
});