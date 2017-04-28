/**
 * Created by gcl on 2016/12/16.
 */
$(function () {
    $('.modal-trigger').leanModal();
    $(".button-collapse").sideNav();
    $('.progress').hide();
    var config = {
        vx: 4,
        vy: 4,
        w: $('.search').width(),
        h: $('.search').height(),
        height: 1,
        width: 2,
        count: 100,
        color: "250, 250, 250",
        stroke: "150,200,180",
        dist: 6000,
        e_dist: 20000,
        max_conn: 10
    }
    CanvasParticle("search", config);

    $('#btn-search').click(function () {
        var name = $('#search-name').val();
        if (name.length == 0) return;
        // 打开加载框
        $('.progress').show();
        $.get('/books?name=' + name, function (data) {

            if (data.length == 0) {
                Materialize.toast('搜不到啊，换个姿势试试', 1500);
            }
            var str = "";
            data.forEach(function (ele, index) {
                str += content(name, index, ele.bookName, ele.num, ele.count, ele.available, ele.author, ele.publishInfo)
            });
            $('#book-list').html(str)

            // 绑定事件
            $('.subscribe').click(function () {
                var key = $(this).attr('data-key');
                var id = $(this).attr('data-id');
                var name = $(this).attr('data-name').replace(/[0-9]+./, "");
                $.get('/books/subscription/' + key + '/' + id + '/' + name, function (data) {
                    if (data) {
                        Materialize.toast('订阅成功', 1500);
                    } else {
                        Materialize.toast('您订阅过了', 1500);
                    }
                })
            });

            // 关闭加载框
            $('.progress').hide();
        });

        function content(key, id, bookName, num, count, available, author, publishInfo) {
            return '<div><div class="card blue-grey darken-1 z-depth-2">' +
                '<div class="card-content white-text"><span class="card-title">' + bookName + '</span><p>' +
                '<span>' + count + '</span></p><p><span>' + available + '</span></p><p>' + author + '</p><p>' + num + '</p><p>' + publishInfo + '</p></div>' +
                '<div class="card-action"> <a href="javascript:;" class="subscribe" data-key="' + key + '" data-id="' + id + '" data-name="' + bookName + '">订阅</a></div></div></div>';
        }
    })

    $('.borrow').click(function () {
        $.get('/user/loginLib', function (data) {
            if (data) {
                window.location = '/borrow';
            } else {
                Materialize.toast('请先登录到图书馆', 3000);
                $('#loginLibrary').openModal();
            }
        })
    });

    $('#btn-login').click(function () {
        $.post(
            '/user/library',
            $('#login_form').serialize(),
            function (data) {
                if (data) {
                    window.location = '/borrow'
                } else {
                    Materialize.toast('用户名或密码错误', 3000)
                }
            }
        )
    });

    // 退出登录
    $('.logout').click(function () {
        $.get('/user/logout', function (data) {
            if (data) {
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
})