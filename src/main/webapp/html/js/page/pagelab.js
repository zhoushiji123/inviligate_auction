/**
 * Created by IBM on 2017/3/5.
 */

var current_page = 1 ;
var pageSize =8 ;


$(function () {

    function  buy(obj) {
        //alert("buy");
        var _id = $(obj).next('.span_id').text();
        //alert(_id);
        if(confirm("确定要购买这个监考吗") == false){
            window.wxc.xcConfirm("购买失败", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        var username = JSON.parse($.session.get('user')).username;
        alert(username);
        $.ajax({
            url: "http://127.0.0.1:8080/zsj/invigilate/buy.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "queryParam":{"_id":_id},
                "updateParam":{"buyer":username,"state":"未完成"}
            }),
            success: function (res) {
                if(res.success == true){
                    window.wxc.xcConfirm("购买成功", window.wxc.xcConfirm.typeEnum.success);
                    $.ajax({
                        url: "http://127.0.0.1:8080/zsj/invigilate/getInvigilates.htm",
                        type: "POST",
                        contentType: "application/json;charset=utf-8",
                        dataType: 'json',
                        async: false,
                        data: JSON.stringify({
                            "pageIndex": 1,
                            "pageSize": 8,
                            "state":"未拍下",
                            "datetime":"1"  //获得当前时间 之后的
                        }),
                        success: function (res) {
                            var table = res.data;
                            var tabledata = {"table": table};
                            // 设置模板
                            $("#result").setTemplateElement("template");
                            // 给模板加载数据
                            $("#result").processTemplate(tabledata);

                            $('td').on('click','#btn-buy',function () {
                                buy(this);
                            });

                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            alert("出错了：" + errorThrown);
                        }

                    });
                }else{
                    window.wxc.xcConfirm("购买失败: "+res.message, window.wxc.xcConfirm.typeEnum.error);
                    return false;
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }


    function findByPage(obj) {
        current_page = parseInt($(obj).text());
      //  alert(current_page);
        $.ajax({
            url: "http://127.0.0.1:8080/zsj/invigilate/getInvigilates.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            // async: false,
            data: JSON.stringify({
                "pageIndex": current_page,
                "pageSize": 8,
                "state":"未拍下",
                "datetime":"1"  //获得当前时间 之后的
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

                $('td').on('click','#btn-buy',function () {
                    buy(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }

    function last() {
        if(current_page ==1 ){
            window.wxc.xcConfirm("当前已经是第一页了", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }

        current_page = current_page - 1;
      //  alert(current_page);
        $.ajax({
            url: "http://127.0.0.1:8080/zsj/invigilate/getInvigilates.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            // async: false,
            data: JSON.stringify({
                "pageIndex": current_page,
                "pageSize": 8,
                "state":"未拍下",
                "datetime":"1"  //获得当前时间 之后的
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

                $('td').on('click','#btn-buy',function () {
                    buy(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }

    function next() {
        current_page = current_page +1 ;
        //alert(current_page);
        $.ajax({
            url: "http://127.0.0.1:8080/zsj/invigilate/getInvigilates.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            // async: false,
            data: JSON.stringify({
                "pageIndex": current_page,
                "pageSize": 8,
                "state":"未拍下",
                "datetime":"1"  //获得当前时间 之后的
            }),
            success: function (res) {
                var table = res.data;
                var tabledata = {"table": table};
                // 设置模板
                $("#result").setTemplateElement("template");
                // 给模板加载数据
                $("#result").processTemplate(tabledata);

                $('td').on('click','#btn-buy',function () {
                    buy(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }

    $('#pagebtn-last').click(function () {
        last();
    });

    $('#pagebtn-next').click(function () {
        next();
    });

    $('.page-pageNo').click(function () {
        findByPage(this);
    });

});
