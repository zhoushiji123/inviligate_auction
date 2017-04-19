/**
 * Created by IBM on 2017/4/19.
 */
$(function () {

    function getMyAuction() {
        var username = JSON.parse($.session.get('user')).username;

        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/auction/getMyAuction.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "pageIndex": 1,
                "pageSize": 200,
                "username":username
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

                $('td').on('click','#btnAuction-auction',function () {
                    take_part_in_auction(this);
                });
                $('td').on('click','#btnAuction-del',function () {
                    del(this);
                });

                // $('td').on('click','#btnMyBuy-giveup',function () {
                //     giveup(this);
                // });
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }

    function take_part_in_auction(obj) {
        var ivg_id = $(obj).next('.span_id').text();
        $.session.set('ivg_id',ivg_id);
        $('#modal-auction').modal();
    }

    function submit_auction() {
        var ivg_id = $.session.get('ivg_id');
        var price = $('#auction-price').val();

        if(price == ''){
            window.wxc.xcConfirm("竞价失败: 不能输入空值！！", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/auction/takePartInAuction.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "username":JSON.parse($.session.get('user')).username,
                "invigilate_id":ivg_id,
                "my_price":price,
                "current_price":price,
                "state":"竞拍领先"
            }),
            success: function (res) {
                if(res.success == true){
                    window.wxc.xcConfirm("竞价成功", window.wxc.xcConfirm.typeEnum.success);
                    getMyAuction();
                    $('#modal-auction').modal('hide');
                }else{
                    window.wxc.xcConfirm("竞价失败: "+res.message, window.wxc.xcConfirm.typeEnum.error);
                    return false;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }

    function del(obj) {
        var state = $(obj).prev('.span_state').text();
        if(state == '竞拍领先' || state == '竞拍落后'){
            window.wxc.xcConfirm("竞拍尚未完成，不能删除！", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        var _id = $(obj).next('.span_id').text();

        if(confirm("确定要删除吗")){
            $.ajax({
                url: "http://123.206.219.49:8080/inviligate_auction/zsj/auction/delAuction.htm",
                type: "POST",
                contentType: "application/json;charset=utf-8",
                dataType: 'json',
                async: false,
                data: JSON.stringify({
                    "_id":_id,
                }),
                success: function (res) {
                    if(res.success == true){
                        window.wxc.xcConfirm("删除成功", window.wxc.xcConfirm.typeEnum.success);
                        getMyAuction();
                        $('#modal-auction').modal('hide');
                    }else{
                        window.wxc.xcConfirm("删除失败: "+res.message, window.wxc.xcConfirm.typeEnum.error);
                        return false;
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("出错了：" + errorThrown);
                }
            });

        }

    }
    
    
    $("#btn-submitAuction").click(function () {
        submit_auction();
    });


    getMyAuction();
});