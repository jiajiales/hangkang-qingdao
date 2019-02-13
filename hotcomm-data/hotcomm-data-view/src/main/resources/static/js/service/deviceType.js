var toolbars = {
    "deviceType-create": "创建设备类型",
    "deviceType-edit": "编辑",
    "deviceType-del": "删除",
    "deviceType_search": "检索"
};

var buttons;

var setButtons = function (v) {
	buttons = JSON.parse(v);
}

$(function () {
    var datagrid_height = $(window).height() - $("#deviceType_search").height() - 98;
    $("#deviceType_datagrid").parent().height(datagrid_height);
    $("#deviceType_datagrid").height(datagrid_height);

    var grid_columns = [
	    [{
	    	field: 'typeName', title: '设备类型名称', width: 30, align: 'center', formatter: formatTitle
	    }, {
	    	field: 'code', title: '设备类型编号', width: 30, align: 'center', formatter: formatTitle
	    }, {
	    	field: 'createTime', title: '创建时间', width: 30, align: 'center', formatter: formatDatebox
	    }, {
	    	field: 'createUser', title: '创建人', width: 30, align: 'center', formatter: formatTitle
	    }, {
	        field: 'typeId', title: '操作', width: 30, align: 'center',
	        formatter: function (val) {
	            return authToolBar({
	                "deviceType-edit": '<span data-id="' + val + '" class="ctr ctr-edit device-deviceTypeManage-updateDeviceType">' + toolbars["deviceType-edit"] + '</span>',
	                "deviceType-delete": '<span data-id="' + val + '" class="ctr ctr-delete device-deviceTypeManage-delDeviceType">' + toolbars["deviceType-del"] + '</span>'
	            }).join("");
	        }
	    }]
    ]

    var grid_data = [];
    var datagrid = {
        columns: grid_columns,
        idField: "id",
        //url:fun_urls.member.page_url,
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
        loadMsg: "正在加载设备类型数据",
        toolbar: authToolBar({
            "deviceType-create": {
            	id: 'device-deviceTypeManage-addDeviceType',
                iconCls: 'fa fa-plus-square',
                text: toolbars["deviceType-create"],
                handler: function (v) {
                    createForm();
                }
            }
        }),
        onLoadSuccess: function () {
			generateButtons(buttons);
        }
    };

    function deviceTypePage() {
        var params = $.serializeToJson($('#deviceType_search_form').serializeArray());
        $('#deviceType_datagrid').datagrid({
            url: bindToken(fun_urls.deviceType.page_url),
            queryParams: params
        });
    }

    //检索框
    $('#deviceType_datagrid').datagrid(datagrid);
    $(deviceTypePage()); // 首次进入页面自动检索一次

    $("#deviceType_search_button").on("click", function () {
    	deviceTypePage();
    })

    //重置
    $("#deviceType_reset_button").on("click", function () {
        $('#deviceType_search_form').form('clear');
        $('#deviceType_datagrid').datagrid('options').queryParams = "";
    });

    //编辑
    $("#deviceType_datagrid").datagrid("getPanel")
    .on('click', ".device-deviceTypeManage-updateDeviceType", function () { // 编辑按钮事件
        var dglog = this;
        var dgId = this.dataset.id;

        $.post(bindToken(fun_urls.deviceType.get_url), {
                "typeId": this.dataset.id
            },
            function (data) {
                if (data.code == "0") {
                    createUpdateForm.call(dglog, dgId, data.data)
                } else {
                	$.messager.alert("系统提示", data.msg, "error");
                }
            });
    }).on('click', ".device-deviceTypeManage-delDeviceType", function () { // 删除按钮事件
        var id = this.dataset.id;
        $.messager.confirm("删除提醒", "确定删除此设备类型吗？", function (r) {
            if (r) {
                $.get(bindToken(fun_urls.deviceType.del_url), {
                    typeId: id
                }, function (data) {
                    if (data.code == "0") {
                        // 数据操作成功后，对列表数据，进行刷新
                        $.messager.show({
                        	title: "删除设备类型",
                            msg: "删除成功",
                            timeout: 3000
                        });
                        $("#deviceType_datagrid").datagrid('reload');
                    } else {
                    	$.messager.alert("系统提示", "删除失败，该设备类型已被占用", "error");
                    }
                });
            }
        });
    });

    //适配屏幕宽度
    function fixWidth(percent) {
        return document.body.clientWidth * percent;
    }

    /**
     * 创建"创建设备类型"窗口
     */
    function createForm(typeId) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "创建设备类型",
            iconCls: 'fa fa-plus-square',
            height: 170,
            width: fixWidth(0.40),
            href: bindDialog('/service/deviceTypeAdd'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#deviceType-form");
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.deviceType.add_url), form.serialize(), function (res) {
                            console.log(form.serialize());
                            if (res.code == "0") {
                                $("#deviceType_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "创建设备类型",
                                    msg: "创建成功",
                                    timeout: 3000
                                });
                                dialog.dialog('close');
                            } else {
                            	$.messager.alert("系统提示", res.msg, "error");
                            }
                        })
                    }
                }
            }]
        });
    }

    /**
     * 创建"编辑设备类型"窗口
     */
    function createUpdateForm(typeId, deviceType) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "编辑设备类型",
            iconCls: 'fa fa-edit',
            height: 170,
            width: fixWidth(0.40),
            href: bindDialog('/service/deviceTypeUpdate'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#deviceType-form");
                //设备类型编号,类型名称,企业编号
                $(form).find("input[name=typeId]").val(deviceType.typeId);
                $(form).find("#typeName").textbox("setValue", deviceType.typeName);
                $(form).find("#code").textbox("setValue", deviceType.code);
            },
            onLoadSuccess: function () {
                if (device.deviceGroupId) {
                    $('#deviceGroupId').combobox('select', device.deviceGroupId);
                }
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.deviceType.update_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#deviceType_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "编辑设备类型",
                                    msg: "编辑成功",
                                    timeout: 3000
                                });
                                dialog.dialog('close');
                            } else {
                                $.messager.alert("系统提示", res.msg, "error");
                            }
                        });
                    }
                }
            }]
        });
    }

});