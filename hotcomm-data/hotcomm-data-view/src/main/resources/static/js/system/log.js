var keys;

var setKeys = function (v) {
    keys = v;
}

$(function () {
    var datagrid_height = $(window).height() - $("#log_search").height() - 98;
    $("#log_datagrid").parent().height(datagrid_height);
    $("#log_datagrid").height(datagrid_height);

    var grid_columns = [
        [{
            field: 'recordUser', title: '操作人', width: 40, align: 'center', formatter: formatTitle
        }, {
            field: 'recordEvent', title: '记录-事件', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'recordTime', title: '记录时间', width: 42, align: 'center', formatter: formatDatebox
        }, {
            field: 'recordIp', title: '用户IP地址', width: 50, align: 'center', formatter: formatTitle
        }]
    ]

    var grid_data = [];
    var datagrid = {
        columns: grid_columns,
        idField: "id",
        data: grid_data,
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        pagination: true,
        singleSelect: true,
        pageSize: 20,
        method: "POST",
        pageList: [10, 20, 30, 40, 50],
        loadMsg: "正在加载日志数据",
    };

    $('#log_datagrid').datagrid(datagrid);

    $("#log_search_button").on("click", function () {
        var params = $.serializeToJson($('#log_search_from').serializeArray());
        //		if(params.recordUser.length==0&&params.startTime.length==0&&params.endTime.length==0){
        //			params.startTime="2001-01-01";
        //			params.endTime="2099-01-01";
        //			console.log(params);
        //			$('#log_datagrid').datagrid({
        //				url:bindToken(fun_urls.log.page_url),
        //				queryParams:params
        //			});	
        //		}else if(params.startTime.length==0&&params.endTime.length==0){
        //			params.startTime="2001-01-01";
        //			params.endTime="2099-01-01";
        //		$('#log_datagrid').datagrid({
        //			url:bindToken(fun_urls.log.page_url),
        //			queryParams:params
        //		});	
        //		}
        //		else{
        $('#log_datagrid').datagrid({
            url: bindToken(fun_urls.log.page_url),
            queryParams: params
        });
        //		}
    });

    $("#log_reset_button").on("click", function () {
        $('#log_search_from').form('clear');
        $('#log_datagrid').datagrid('options').queryParams = "";
    });

});