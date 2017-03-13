/**
 * Created by IBM on 2017/3/1.
 */

$(function () {

    function updatePassword() {
        var lastPassword = $('#update-password').val();
        var newPassword = $('#update-newPassword').val();
        var surePassword = $('#update-newSurePassword').val();


        if(lastPassword == '' || newPassword == '' || surePassword == ''){
            var txt=  "不能有空值";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        if(surePassword != newPassword){
            var txt=  "新密码跟确认密码不一致";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        var user  = JSON.parse($.session.get('user'));
        var password = user.password;
        if(lastPassword != password){
            var txt=  "原密码不正确";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        $.ajax({
            url:"http://127.0.0.1:8080/zsj/user/updatePassword.htm",
            type:"POST",
            type:'POST',
            contentType : "application/json;charset=utf-8",
            dataType:'json',
            async:false,
            data:JSON.stringify({
                "username":user.username,
                "newPassword":newPassword
            }),
            success:function (data) {
                var flag = data.success;
                if(flag == false){
                    var txt=  data.message;
                    window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
                    return false;
                }
                alert("修改成功,请重新登录");
                window.location.href = './index.html';
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了："+errorThrown);
            }
        });
    }

    $('#btn-updatePassword').click(function () {
        updatePassword();
    });

});