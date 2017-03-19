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
        var userstr = $.session.get('user');
        if(!userstr){
            alert("请先登录！！！");
            window.location.href = './index.html';
            return false;
        }
        $('#modal-addInvigilate').modal();
    });

    $('#dp1').click(function(){
        var userstr = $.session.get('user');
        if(!userstr){
            alert("请先登录！！！");
            window.location.href = './index.html';
            return false;
        }
        return true ;
    });

});
