/**
 * Created by gcl on 2016/12/16.
 */
$(function () {
    $('.modal-trigger').leanModal();
    $(".button-collapse").sideNav();
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

})