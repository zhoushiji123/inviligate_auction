/**
 * Created by IBM on 2017/3/5.
 */
$(function () {

    function getIngivilates() {

        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/getInvigilates.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "pageIndex": 1,
                "pageSize": 8,
                "state":"竞拍中",
                "datetime":"1"  //获得当前时间 之后的
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }

    getIngivilates();



});