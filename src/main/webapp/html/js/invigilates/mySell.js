/**
 * Created by IBM on 2017/3/8.
 */

  var _id ;
$(function () {


    function del(obj) {
        var state = $(obj).prev('.span_state').text();
        if(state == '未完成'){
            window.wxc.xcConfirm("该监考已被拍下,处于执行阶段,不能删除", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }

        if(confirm("确定要删除吗??")){
            _id = $(obj).next('.span_id').text();
            $.ajax({
                url: "http://127.0.0.1:8080/zsj/invigilate/delete.htm",
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                async: false,
                data: JSON.stringify({
                    "_id":_id
                }),
                success: function (res) {
                    if(res.success == true){
                        window.wxc.xcConfirm("删除成功", window.wxc.xcConfirm.typeEnum.success);
                        getMySellIvg();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("出错了：" + errorThrown);
                }
            });
        }
    }


    function btnUpdate(obj) {
        var state = $(obj).prev('.span_state').text();
        if(state == '已完成'){
            window.wxc.xcConfirm("已经完成的监考不能再修改", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }
        if(state == '未完成'){
            window.wxc.xcConfirm("该监考已被拍下,处于执行阶段,不能修改内容", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }
        _id = $(obj).next('.span_id').text();
        $('#modal-updateInvigilate').modal();

    }


    function subUpdate() {
        var school = $('#updateText-school').val();
        var address  = $('#updateText-address').val();
        var price = $('#updateText-price').val();
        var subject = $('#updateText-subject').val();
        var content = $('#updateText-content').val();

        var datetime = $('#updateText-datetime').val();

        if(school == '' && address == '' && price == '' && subject == '' && datetime == '' && content == '' ){
            var txt=  "修改时不能全部为空值";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }


        var updateParam = {};
        if(school != ''){
            updateParam.school = school;
        }
        if(address != ''){
            updateParam.address = address;
        }
        if(price != ''){
            updateParam.price = price;
        }
        if(subject != ''){
            updateParam.subject = subject;
        }
        if(datetime != ''){
            updateParam.datetime = datetime;
        }

        if(content!= ''){
            updateParam.content = content;
        }


        updateParam.state = "待审核";


        $.ajax({
            url: "http://127.0.0.1:8080/zsj/invigilate/update.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "queryParam":{"_id":_id},
                "updateParam":updateParam
            }),
            success: function (res) {
                if(res.success == true){
                    window.wxc.xcConfirm("修改成功,待管理员审核之后,监考会重新发布到主页面", window.wxc.xcConfirm.typeEnum.success);
                    $('#modal-updateInvigilate').modal('hide');

                    getMySellIvg();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });




    }

    function getMySellIvg() {
        $.ajax({
            url: "http://127.0.0.1:8080/zsj/invigilate/getInvigilates.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "pageIndex": 1,
                "pageSize": 200,
                "seller":JSON.parse($.session.get('user')).username
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

                $('td').on('click','#btnMySell-update',function () {
                    btnUpdate(this);
                });

                $('td').on('click','#btnMySell-del',function () {
                    del(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }

    getMySellIvg();


    $('#btn-submitUpdateInvigilate').click(function () {
        subUpdate();
    });
});
