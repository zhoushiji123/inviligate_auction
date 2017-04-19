/**
 * Created by IBM on 2017/3/6.
 */
$(function () {
    function searchBySchool() {
        var school = $('#searchText-school').val();

        if(school == ''){
            window.wxc.xcConfirm("学校名字不能为空", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }

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
                "school":school,
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
                $('td').on('click','#btn-auction',function () {
                    take_part_in_auction(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }


    function searchById() {
        var _id = $('#searchText-id').val();

        if(_id == ''){
            window.wxc.xcConfirm("不能输入空值", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }
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
                "_id":_id,
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
                $('td').on('click','#btn-auction',function () {
                    take_part_in_auction(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });
    }

    function searchByDate() {
        var datetime = $('#searchText-datetime').val();

        if(datetime == ''){
            window.wxc.xcConfirm("日期不能为空", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }

        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/getInvigilatesByDate.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "pageIndex": 1,
                "pageSize": 8,
                "state":"竞拍中",
                "datetime":datetime
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
                $('td').on('click','#btn-auction',function () {
                    take_part_in_auction(this);
                });

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }
        });

    }


    function  buy(obj) {
        var userstr = $.session.get('user');
        if(!userstr){
            alert("请先登录！！！");
            window.location.href = './index.html';
            return false;
        }

        // alert("buy");
        var _id = $(obj).next('.span_id').text();
        // alert(_id);
        if(confirm("确定要购买这个监考吗") == false){
            window.wxc.xcConfirm("购买失败", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        var username = JSON.parse($.session.get('user')).username;
       // alert(username);
        $.ajax({
            url: "http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/buy.htm",
            type: "POST",
            contentType: "application/json;charset=utf-8",
            dataType: 'json',
            async: false,
            data: JSON.stringify({
                "_id":_id,
                "buyer":username
            }),
            success: function (res) {
                if(res.success == true){
                    window.wxc.xcConfirm("购买成功", window.wxc.xcConfirm.typeEnum.success);
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

                            $('td').on('click','#btn-buy',function () {
                                buy(this);
                            });
                            $('td').on('click','#btn-auction',function () {
                                take_part_in_auction(this);
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


    function take_part_in_auction(obj) {
        var ivg_id = $(obj).prev('.span_id').text();
        $.session.set('ivg_id',ivg_id);
        $('#modal-auction').modal();
    }

    $('td').on('click','#btn-auction',function () {
        take_part_in_auction(this);
    });


    $('#searchbtn-searchBySchool').click(function () {
        searchBySchool();
    });


    $('#searchbtn-searchByDate').click(function () {
        searchByDate();
    });

    $('#searchbtn-searchById').click(function () {
        searchById();
    })


});