/**
 * Created by IBM on 2017/3/5.
 */
$(function () {
    $('#addbtn-datetime').click(function () {
        $('#add-datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:00',
            locale: moment.locale('zh-cn')
        });
    });

    $('#updatebtn-datetime').click(function () {
        $('#update-datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:00',
            locale: moment.locale('zh-cn')
        });
    });


    $('#searchbtn-datetime').click(function () {
        $('#search-datetimepicker').datetimepicker({
            format: 'yyyy-mm-dd',
            locale: moment.locale('zh-cn'),
            minView: "month"
        });
    });
});
