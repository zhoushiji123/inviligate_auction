/**
 * Created by IBM on 2017/3/5.
 */

$(function () {

    function  buy(obj) {
     //   alert("buy");
        var _id = $(obj).next('.span_id').text();
     //   alert(_id);
        if(confirm("确定要购买这个监考吗") == false){
            window.wxc.xcConfirm("购买失败", window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        var username = JSON.parse($.session.get('user')).username;
        alert(username);
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

                            $('td').on('click','#btn-auction',function () {
                                take_part_in_auction(this);
                            });
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




    function addInvigilate() {
        var school = $('#addText-school').val();
        var address  = $('#addText-address').val();
        var price = $('#addText-price').val();
        var current_price = $('#addText-current_price').val();
        var subject = $('#addText-subject').val();
        var content = $('#addText-content').val();

        var datetime = $('#addText-datetime').val();

        if(school == '' || address == '' || price == '' || subject == '' || datetime == '' || current_price == '' ){
            var txt=  "除了备注,不能有空值";
            window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.error);
            return false;
        }

        $.ajax({
            url:"http://123.206.219.49:8080/inviligate_auction/zsj/invigilate/addInvigilate.htm",
            type:"POST",
            contentType : "application/json;charset=utf-8",
            dataType:'json',
            async:false,
            data:JSON.stringify({
                "school":school,
                "address":address,
                "price":price,
                "subject":subject,
                "datetime":datetime,
                "current_price":current_price,
                "content":content,
                "seller":JSON.parse($.session.get('user')).username,
                "state":"待审核",
                "buyer":""
            }),
            success:function (data) {
                var flag = data.success;
                if(flag == false){
                    var msg = data.message;
                    window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.error);
                    return false;
                }
                var txt2=  "提交成功,管理员审核批准后，监考将被发布到主页上";
                window.wxc.xcConfirm(txt2, window.wxc.xcConfirm.typeEnum.success);
                $('#modal-addInvigilate').modal('hide');
                getIngivilates();

            },
            error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了："+errorThrown);
            }
        });



    }

    $('#btn-submitAddInvigilate').click(function () {
        addInvigilate();
    });

    function take_part_in_auction(obj) {
        var ivg_id = $(obj).prev('.span_id').text();
        $.session.set('ivg_id',ivg_id);
        $('#modal-auction').modal();
    }

    $('td').on('click','#btn-auction',function () {
        take_part_in_auction(this);
    });



});
