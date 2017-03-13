/**
 * Created by IBM on 2017/2/28.
 */
$(document).ready(function () {

    function isPhoneNo(phone) {
        var pattern = /^1[34578]\d{9}$/;
        return pattern.test(phone);
    }

    function register() {
        var username = $("#reg-username").val();
        var telephone = $("#reg-telphone").val();
        var password = $("#reg-password").val();
        var surePassword = $("#reg-surePassword").val();

        if(username == '' || telephone == '' || password == '' || surePassword == ''){
           // alert("不能输入空值");
            var txt=  "不能输入空值";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        if(isPhoneNo(telephone) == false){
            var txt=  "电话号码输入格式不对";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        if(password != surePassword){
            var txt=  "密码跟确认密码不一致";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        $.ajax({
            url:"http://127.0.0.1:8080/zsj/user/register.htm",
            data:JSON.stringify({
                "collectionName":"users",
                "username":username,
                "password":password,
                "telephone":telephone,
                "role":"user"
            }),
            type:'POST',
            contentType : "application/json;charset=utf-8",
            dataType:'json',
            async:false,
            success:function (data) {
                var flag = data.success;
                if(flag == false){
                    var txt=  data.message;
                    window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
                    return false;
                }
                var txt=  "注册成功";
                //window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.success);
                alert(txt);
                window.location.href = './index.html';
            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了："+errorThrown);
            }
        });
    }


    $("#btn-register").click(function () {
        register();
    });
    
});
