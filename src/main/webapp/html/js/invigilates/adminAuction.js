/**
 * Created by IBM on 2017/4/19.
 */
$(function () {

    function  getMessageAuction() {
        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/result/getResult.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "pageIndex": 1,
                "pageSize": 200
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

                $('td').on('click','#btnAdminAuction-del',function () {
                    del(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }

    function  del(obj) {
        var _id = $(obj).next('.span_id').text();

        if(confirm("确定要删除吗??")){
            $.ajax({
                url: "http://123.206.219.49:8080/inviligate_auction/zsj/result/delResult.htm",
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
                        getMessageAuction();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("出错了：" + errorThrown);
                }
            });
        }

    }

    getMessageAuction();
});
