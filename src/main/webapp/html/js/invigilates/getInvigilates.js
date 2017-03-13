/**
 * Created by IBM on 2017/3/5.
 */
$(function () {

    function getIngivilates() {

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

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert("出错了：" + errorThrown);
            }

        });
    }

        // var data = {
        //     "table":[
        //         {"school": "浙江大学", "address": "科技楼202" ,"datetime": "2017-03-06 10:00:00", "subject":"数学",
        //             "price": 100, "seller": "zsj"},
        //         {"school": "浙江大学城市学院", "address": "理科4号楼,310" ,"datetime": "2017-03-07 11:00:00","subject":"英语",
        //             "price": 150, "seller": "傻逼"},
        //         {"school": "浙江大学城市学院", "address": "理科4号楼,310" ,"datetime": "2017-03-07 11:00:00","subject":"英语",
        //             "price": 150, "seller": "傻逼"},
        //         {"school": "浙江大学城市学院", "address": "理科4号楼,310" ,"datetime": "2017-03-07 11:00:00","subject":"英语",
        //             "price": 150, "seller": "傻逼"},
        //         {"school": "浙江大学城市学院", "address": "理科4号楼,310" ,"datetime": "2017-03-07 11:00:00","subject":"英语",
        //             "price": 150, "seller": "傻逼"},
        //         {"school": "浙江大学城市学院", "address": "理科4号楼,310" ,"datetime": "2017-03-07 11:00:00","subject":"英语",
        //             "price": 150, "seller": "傻逼"},
        //         {"school": "浙江大学城市学院", "address": "理科4号楼,310" ,"datetime": "2017-03-07 11:00:00","subject":"英语",
        //             "price": 150, "seller": "傻逼"},
        //         {"school": "浙江大学城市学院", "address": "理科4号楼,310" ,"datetime": "2017-03-07 11:00:00","subject":"英语",
        //             "price": 150, "seller": "傻逼"},
        //
        //     ]
        // };

        // $("#result").setTemplateElement("template");
        // // 给模板加载数据
        // $("#result").processTemplate(data);



    getIngivilates();



});