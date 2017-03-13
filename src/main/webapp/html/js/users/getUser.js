/**
 * Created by IBM on 2017/3/1.
 */

var current_user ;

$(function () {

    function getUser() {
        var user = $.session.get('user');
        alert(user);
    }

    // $.ajax({
    //     url:"http://127.0.0.1:8080/zsj/user/getCurrentUser.htm",
    //     type:"POST",
    //     data:{},
    //     dataType:'json',
    //     success:function (data) {
    //         current_user = data;
    //         alert(JSON.stringify(current_user));
    //     },
    //     error:function (XMLHttpRequest, textStatus, errorThrown) {
    //         alert("出错了："+errorThrown);
    //     }
    //
    // });

    getUser();

});