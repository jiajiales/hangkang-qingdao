var toolbars = {
    "data-get": "查阅具体数据"
};

var buttons;

var setButtons = function (v) {
	buttons = JSON.parse(v);
}

$(function () {
    var datagrid_height = $(window).height() - $("#data_search").height() - 98;
    $("#data_datagrid").parent().height(datagrid_height);
    $("#data_datagrid").height(datagrid_height);

    var grid_columns = [
        [{
            field: 'dataId', hidden: 'true'
        }, {
            field: 'deviceCode', title: '设备硬件编号', width: 20, align: 'center', formatter: formatTitle
        }, {
        	field: 'createTime', title: '录入时间', width: 15, align: 'center', formatter: formatDatebox
        }, {
            field: 'receiveData', title: '数据', width: 20, align: 'center',
            formatter: function (val, row) {
                return authToolBar({
                    "data-get": '<a data-id="' + row.dataId + '" class="ctr" title="点击查看详细数据">' + row.receiveData + '</a>'
                }).join("");
            }
        }, {
            field: 'frameCnt', title: 'FrameCnt', width: 10, align: 'center', formatter: formatTitle
        }, {
            field: 'rssiSnr', title: 'Rssi / Snr', width: 10, align: 'center', formatter: formatTitle
        }, {
            field: 'channel', title: 'Channel', width: 10, align: 'center', formatter: formatTitle
        }, {
            field: 'sf', title: 'SF', width: 10, align: 'center', formatter: formatTitle
        }, {
            field: 'gwip', title: '基站IP', width: 15, align: 'center', formatter: formatTitle
        }, {
        	field: 'dataSource', title: '数据来源', width: 10, align: 'center',
            formatter: function (val) {
                switch (val) {
                case 1:
                    return "AMQP";
                    break;
                case 2:
                    return "MQTT";
                    break;
                case 3:
                    return "TCP";
                    break;
                case 4:
                    return "HTTP";
                    break;
                default:
                    return "";
                }
            }
        }, {
            field: 'sendStatus', title: '发送状态', width: 10, align: 'center',
            formatter: function (val) {
                switch (val) {
                case 1:
                    return "未发送";
                    break;
                case 2:
                    return "等待发送";
                    break;
                case 3:
                    return "发送成功";
                    break;
                case 4:
                    return "发送失败";
                    break;
                default:
                    return "";
                }
            }
        }, {
            field: 'queueName', title: '队列名称', width: 20, align: 'center', formatter: formatTitle
        }]
    ]

    var grid_data = [];
    var datagrid = {
        columns: grid_columns,
        idField: "dataId",
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
        loadMsg: "正在加载企业数据",
        onLoadSuccess: function () {
			generateButtons(buttons);
        }
    };

    function dataPage() {
        var params = $.serializeToJson($('#data_search_form').serializeArray());
        $('#data_datagrid').datagrid({
            url: bindToken(fun_urls.data.page_url),
            queryParams: params
        });
    }

    $('#data_datagrid').datagrid(datagrid);
    $(dataPage()); // 首次进入页面自动检索一次

    $("#data_search_button").on("click", function () {
    	dataPage();
    });

    $("#data_reset_button").on("click", function () {
        $('#data_search_form').form('clear');
        $('#data_datagrid').datagrid('options').queryParams = "";
    });

    /**
     * 操作按钮绑定事件
     */
    $("#data_datagrid").datagrid("getPanel")
    .on('click', "a.ctr", function () { // 查看按钮事件
        var dglog = this;
        var dgId = this.dataset.id;

        $.post(bindToken(fun_urls.data.get_receive_data_url), {
            "dataId": dgId
        }, function (data) {
            if (data.code == "0") {
                createGetDataForm.call(dglog, data.data);
            } else {
            	$.messager.alert("系统提示", data.msg, "error");
            }
        });
    });

    /**
     * 适配屏幕宽度
     */
    function fixWidth(percent) {
        return document.body.clientWidth * percent;
    }

    /**
     * 创建窗口(查阅具体数据)
     */
    function createGetDataForm(data) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "查阅具体数据",
            iconCls: 'fa fa-search',
            height: 208,
            width: fixWidth(0.30),
            href: bindDialog('/data/view'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                // 窗口表单加载成功时执行
                form = $("#data-form");
                $(form).find("#receiveData").textbox("setValue", data.receiveData);
            }
        });
    }

});