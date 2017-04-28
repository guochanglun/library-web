/**
 * Created by gcl on 2016/12/18.
 */
$(function () {
    $('.modal-trigger').leanModal();
    $(".button-collapse").sideNav();
    // 显示登录框
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


    // 保存信息
    $('#save').click(function () {
        var data = $('#save-form').serialize();
        $.post('/user/change-info', data, function (data) {
            if (data) {
                Materialize.toast('修改成功', 2000)
            } else {
                Materialize.toast('修改失败', 2000)
            }
        })
    });

    // 修改密码
    $('#changepwd').click(function () {
        var op = $('#old_pwd').val();
        var np = $('#new_pwd').val();
        var cp = $('#confirm').val();
        if (cp == '' || np == '' || op == '') {
            Materialize.toast('请填写完整信息', 2000)
            return;
        }
        if (cp != np) {
            Materialize.toast('两次输入的密码不一致', 2000)
            return;
        }
        $.post('/user/change-pwd/' + op + '/' + np, function (data) {
            if (data) {
                Materialize.toast('修改成功', 2000)
                $('#changePassword').closeModal();
            } else {
                Materialize.toast('修改失败', 2000)
            }
        });
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