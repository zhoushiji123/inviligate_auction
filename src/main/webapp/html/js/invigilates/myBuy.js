/**
 * Created by IBM on 2017/3/8.
 */
$(function () {

    function giveup(obj) {
        var state = $(obj).prev('.span_state').text();
        if(state == '已完成'){
            window.wxc.xcConfirm("该监考已经完成了", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }

        var _id = $(obj).next('.span_id').text();

        if(confirm("确定要放弃吗??")){
            _id = $(obj).next('.span_id').text();
            $.ajax({
                url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/giveup.htm",
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                async: false,
                data: JSON.stringify({
                    "queryParam":{"_id":_id},
                    "updateParam":{"state":"未拍下","buyer":""}
                }),
                success: function (res) {
                    if(res.success == true){
                        window.wxc.xcConfirm("已放弃", window.wxc.xcConfirm.typeEnum.success);
                        getMyBuy();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("出错了：" + errorThrown);
                }
            });
        }
    }



    function finish(obj) {
        var state = $(obj).prev('.span_state').text();
        if(state == '已完成'){
            window.wxc.xcConfirm("该监考已经完成", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }
        if(state == '竞拍中'){
            window.wxc.xcConfirm("该监考还在竞拍中", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }

        var _id = $(obj).next('.span_id').text();

        if(confirm("确定完成了吗??")){
            _id = $(obj).next('.span_id').text();
            $.ajax({
                url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/finish.htm",
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                async: false,
                data: JSON.stringify({
                    "queryParam":{"_id":_id},
                    "updateParam":{"state":"已完成"}
                }),
                success: function (res) {
                    if(res.success == true){
                        window.wxc.xcConfirm("成功完成", window.wxc.xcConfirm.typeEnum.success);
                        getMyBuy();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("出错了：" + errorThrown);
                }
            });
        }

    }


    function  getMyBuy() {
        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/getInvigilates.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "pageIndex": 1,
                "pageSize": 200,
                "buyer":JSON.parse($.session.get('user')).username
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

                $('td').on('click','#btnMyBuy-finish',function () {
                    finish(this);
                });

                $('td').on('click','#btnMyBuy-giveup',function () {
                    giveup(this);
                });
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }

    getMyBuy();



});