/**
 * Created by IBM on 2017/2/25.
 */




$(document).ready(function () {

    function userLogin() {
        var username = $('#username').val();
        var password = $('#password').val();

        if(username == '' || password == ''){
            var txt=  "用户名密码不能为空";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        var jsonData = JSON.stringify({
            "username":username,
            "password":password,
            "collectionName":"users"
        });
        $.ajax({
            url:"http://127.0.0.1:8080/"+"zsj/user/login.htm",
            type:'POST',
            contentType : "application/json;charset=utf-8",
            dataType:'json',
            data:jsonData,
            async:false,
            success:function (data) {
                var flag = data.success ;
                if(flag == false){
                    var msg = data.message;
                    window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.error);
                    return false;
                }
                var user =  data.data[0];
                var jsuser = JSON.stringify(user)
               // alert(jsuser);
                $.session.set('user',jsuser);

                if(user.username == 'admin'){
                    window.location.href = './admin.html';
                    return true ;
                }

                // current_user = user;
                window.location.href = './main.html';
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了："+errorThrown);
            }
        });
    }

    function test() {
        alert("test");
    }

    $('#btn-login').click(function () {
       userLogin();
    });



    $('#btn-goRegister').click(function () {
        window.location.href = './register.html';
    });



});