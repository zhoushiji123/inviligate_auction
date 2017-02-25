/**
 * Created by IBM on 2017/2/25.
 */
$(function () {
    $('#login').click(function () {
        var username = $('#username').val();
        var password = $('#password').val();
        alert("账号: "+username+"\n"+"密码: "+password);
        $.ajax({
            url:'tm',
            type:'post',
            data:{
                "username":username,
                "password":password,
                "collectionName":"users"
            },
            success:function (data) {
                alert(data.toString);
            },
            error:function () {
                alert("出错了 : "+XMLHttpRequest.status);
            }

        });
    });
})