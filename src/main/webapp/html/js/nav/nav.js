/**
 * Created by IBM on 2017/2/28.
 */
$(function () {

    function setvalue() {
        // $('#dp1').text("个人中心");
    }

    function logout() {
        $.session.remove('user');
        window.location.href = './index.html';

    }

    $('#btn-logout').click(function () {
        logout();
    });

    $('#btn-addInvigilate').click(function () {
        $('#modal-addInvigilate').modal();
    });

});
