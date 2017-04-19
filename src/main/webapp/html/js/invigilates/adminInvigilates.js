/**
 * Created by IBM on 2017/3/6.
 */
$(function () {

    function getInvigilates() {
        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/getInvigilates.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "pageIndex": 1,
                "pageSize": 200,
                "state":"待审核"
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);



                $('td').on('click','#btnAdmin-approve',function () {
                    approve(this);
                });

                $('td').on('click','#btnAdmin-deny',function () {
                    deny(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }


    function approve(obj) {
        var _id = $(obj).next('.span_id').text();
     //   alert(_id);
        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/approve.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "queryParam":{"_id":_id},
                "updateParam":{"state":"竞拍中"}
            }),
            success: function (res) {
                if(res.success == true){
                    window.wxc.xcConfirm("审核成功", window.wxc.xcConfirm.typeEnum.success);
                    getInvigilates();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }


    function deny(obj) {
        var _id = $(obj).next('.span_id').text();
    //    alert(_id);
        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/deny.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "_id":_id
            }),
            success: function (res) {

                if(res.success == true){
                    window.wxc.xcConfirm("审核成功", window.wxc.xcConfirm.typeEnum.success)
                    getInvigilates();
                }

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }

    getInvigilates();








});