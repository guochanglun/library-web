/**
 * Created by gcl on 2016/12/16.
 */

$(function () {
    $('.modal-trigger').leanModal();
    $(".button-collapse").sideNav();

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

    // 登录
    $('#btn-login').click(function () {
        $.post(
            '/user/login',
            $('#login_form').serialize(),
            function (data) {
                if (data) {
                    Materialize.toast('登录成功', 1000)
                    window.location = '/home'
                } else {
                    Materialize.toast('用户名或密码错误', 3000)
                }
            }
        )
    })

    // 注册
    $('#register').click(function () {
        var pwd = $('#password-new').val();
        var repwd = $('#password-check').val();
        if (pwd != repwd) {
            Materialize.toast('两次输入的密码不一致', 2000)
            return;
        }
        $.post(
            '/user/register',
            $('#register_form').serialize(),
            function (data) {
                if (data) {
                    $('#register_modal').closeModal()
                    $('#login').openModal()
                    Materialize.toast('注册成功，请登录', 1000)
                } else {
                    Materialize.toast('邮箱已注册', 2000)
                }
            }
        )
    })

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