var toolbars = {
    "device-create": "创建设备",
    "device-batch-allot-deviceGroup": "批量分配设备组",
    "device-list-import-temp-download": "下载OTAA设备导入模板",
    "device-list-import": "导入设备列表",
    "device-list-export": "导出设备列表",
    "device-get": "查看",
    "device-edit": "编辑",
    "device-del": "删除"
};

var buttons;

var setButtons = function (v) {
	buttons = JSON.parse(v);
}

$(function () {
	var curDeviceCode;

    //设置搜索表单样式
    var datagrid_height = $(window).height() - $("#device_search").height() - 98;
    $("#device_datagrid").parent().height(datagrid_height);
    $("#device_datagrid").height(datagrid_height);

//    $('#selectMemberId').combobox({
//        url: bindToken(fun_urls.customer.list_url),
//        editable: false,
//        valueField: 'id',
//        panelMaxHeight: 200,
//        panelHeight: 'auto',
//        textField: 'memberName',
//        loadFilter: function (data) {
//            return data.data;
//        },
//        onSelect: function (data) {
//            var queryParams = new Object();
//            queryParams.selectMemberId = data.id;
//            $('#groupId').combobox({
//                textField: 'text',
//                url: bindToken(fun_urls.deviceGroup.list_url),
//                queryParams: queryParams,
//                editable: false,
//                valueField: 'devGroupId',
//                panelMaxHeight: 200,
//                panelHeight: 'auto',
//                textField: 'devGroupName',
//                loadFilter: function (data) {
//                    return data.data;
//                }
//            });
//        }
//    });

//    $('#groupId').combobox({width:352});

    $('#groupId').combobox({
        textField: 'text',
        url: bindToken(fun_urls.deviceGroup.list_url),
        editable: false,
        valueField: 'devGroupId',
        panelMaxHeight: 200,
        panelHeight: 'auto',
        textField: 'devGroupName',
        loadFilter: function (data) {
            return data.data;
        }
    });

    $('#typeId').combobox({
        textField: 'text',
        url: bindToken(fun_urls.deviceType.list_url),
        editable: false,
        valueField: 'typeId',
        panelMaxHeight: 200,
        panelHeight: 'auto',
        textField: 'typeName',
        loadFilter: function (data) {
            return data.data;
        }
    });

    var grid_columns = [
        [{
            field: 'ck', checkbox: true
        }, {
            field: 'code', title: 'DevEUI', width: 25, align: 'center',
	        formatter: function (val, row) {
		        return authToolBar({
		        	"data-get": '<a data-code="' + row.code + '" class="ctr ctr-view" title="点击查看相关数据">' + row.code + '</a>'
		        }).join("");
            }
        }, {
            field: 'mac', title: 'DevAddr', width: 25, align: 'center', formatter: formatTitle
        }, {
            field: 'appSKey', title: 'AppSKey', width: 25, align: 'center', formatter: formatTitle
        }, {
            field: 'appEUI', title: 'AppEUI', width: 25, align: 'center', formatter: formatTitle
        }, {
            field: 'groupName', title: '关联设备组', width: 25, align: 'center', formatter: formatTitle
        }, {
            field: 'typeName', title: '设备类型', width: 15, align: 'center', formatter: formatTitle
        }, {
        	field: 'protocol', title: '接入协议', width: 15, align: 'center',
            formatter: function (val) {
                switch (val) {
                case 1:
                    return "AMQP";
                    break;
                case 2:
                    return "MQTT";
                    break;
                case 3:
                    return "TCP/IP";
                    break;
                case 4:
                    return "HTTP";
                    break;
                default:
                    return "";
                }
            }
        }, {
        	field: 'iotTech', title: '通信技术', width: 15, align: 'center',
            formatter: function (val) {
                switch (val) {
                case 1:
                    return "Lora";
                    break;
                case 2:
                    return "NB-Iot";
                    break;
                case 3:
                    return "GPRS";
                    break;
                default:
                    return "";
                }
            }
//        }, {
//            field: 'receiveNum', title: '接收数据数量', width: 20, align: 'center', formatter: formatTitle
        }, {
            field: 'createTime', title: '创建时间', width: 30, align: 'center', formatter: formatDatebox
        }, {
            field: 'createUser', title: '录入人', width: 15, align: 'center', formatter: formatTitle
        }, {
            field: 'desc', title: '设备备注', width: 15, align: 'center', formatter: formatTitle
        }, {
            field: 'status', title: '状态', width: 10, align: 'center',
            formatter: function (val, row) {
                return val == "1" ? "启用" : "禁用";
            }
        }, {
            field: 'deviceId', title: '操作', width: 35, align: 'center',
            formatter: function (value, row, index) {
                return authToolBar({
                	"device-get": '<span data-code="' + row.code + '" class="ctr fa-info-circle device-lora-otaa-get-receiveNum">' + toolbars["device-get"] + '</span>',
                    "device-edit": '<span data-id="' + row.deviceId + '" class="ctr ctr-edit device-lora-otaa-update">' + toolbars["device-edit"] + '</span>',
                    "device-delete": '<span data-id="' + row.deviceId + '" class="ctr ctr-delete device-lora-otaa-del">' + toolbars["device-del"] + '</span>'
                }).join("");
            }
        }]
    ]

    var grid_data = [];
    var datagrid = {
        columns: grid_columns,
        idField: "deviceId",
        data: grid_data,
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        pagination: true,
        singleSelect: false,
        pageSize: 20,
        method: "POST",
        pageList: [10, 20, 30, 40, 50],
        loadMsg: "正在加载设备数据",
        toolbar: authToolBar({
            "device-create": {
            	id: 'device-lora-otaa-add',
                iconCls: 'fa fa-plus-square',
                text: toolbars["device-create"],
                handler: function (v) {
                    createForm();
                }
            },
            "device-batch-allot-deviceGroup": {
            	id: 'device-lora-otaa-ba',
                iconCls: 'fa fa-th-large',
                text: toolbars["device-batch-allot-deviceGroup"],
                handler: function () {
                	var checkedItems = $('#device_datagrid').datagrid('getChecked');
                	var deviceIdsArr = [];

                	$.each(checkedItems, function(index, item){
                		deviceIdsArr.push(item.deviceId);
                	});

                	var deviceIds = deviceIdsArr.join(",");
                	var deviceIdsNum = deviceIdsArr.length;

                	if (deviceIds.length > 0 && deviceIdsNum > 0) {
                		createBaDeviceGroupForm(deviceIds, deviceIdsNum);
                	} else {
                		$.messager.alert('系统提示', "请选择一个或多个设备", "warning");
                	}
                }
            },
            "device-list-import-temp-download": {
                iconCls: 'fa fa-file-excel-o',
                text: toolbars["device-list-import-temp-download"],
                handler: function () {
                	$(location).attr('href', "static/download/device/OTAA.xlsx");
                }
            },
            "device-list-import": {
            	id: 'device-lora-otaa-import',
                iconCls: 'fa fa-upload',
                text: toolbars["device-list-import"],
                handler: function () {
                    createDevImportForm();
                }
            },
            "device-list-export": {
            	id: 'device-lora-otaa-export',
                iconCls: 'fa fa-download',
                text: toolbars["device-list-export"],
                handler: function () {
                    createDevExportForm();
                }
            }
        }),
        onLoadSuccess: function () {
			generateButtons(buttons);
        },
        onSelect: function (index, row) {
            if (row.code) {
            	curDeviceCode = row.code;
            }
        }
    };

    /**
     * 适配屏幕宽度
     */
    function fixWidth(percent) {
        return document.body.clientWidth * percent;
    }

    /**
     * 适配屏幕高度
     */
    function fixHeight(percent) {
        return document.body.clientHeight * percent;
    }

    function devicePage() {
        var params = $.serializeToJson($('#device_search_from').serializeArray());
        $('#device_datagrid').datagrid({
            url: bindToken(fun_urls.device.lora_otaa_page_url),
            queryParams: params
        });
    }

    $('#device_datagrid').datagrid(datagrid);
    $(devicePage()); // 首次进入页面自动检索一次

    $("#device_search_button").on("click", function () {
    	devicePage();
    });

    $("#device_reset_button").on("click", function () {
        $('#device_search_from').form('clear');
        $('#device_datagrid').datagrid('options').queryParams = "";
    });

    /**
     * 操作按钮绑定事件
     */
    $("#device_datagrid").datagrid("getPanel")
    .on('click', "a.ctr-view", function () { // code单击事件
        var dglog = this;
        createViewForm.call(dglog, curDeviceCode);
    }).on('click', ".device-lora-otaa-get-receiveNum", function () { // 查看按钮事件
        var dglog = this;
        var dgCode = this.dataset.code;
        $.post(bindToken(fun_urls.device.lora_get_receiveNum_url), {"code": dgCode}, function (data) {
            if (data.code == "0") {
                var receiveNum = data.data;
                $.messager.alert("查看", "设备接收数量：" + receiveNum, "info");
            } else {
            	$.messager.alert("系统提示", data.msg, "error");
            }
        });
    }).on('click', ".device-lora-otaa-update", function () { // 编辑按钮事件
        var dglog = this;
        var dgId = this.dataset.id;
        $.post(bindToken(fun_urls.device.lora_otaa_get_url), {"deviceId": this.dataset.id}, function (data) {
            if (data.code == "0") {
                var device = data.data;
                createUpdateForm.call(dglog, dgId, device)
            } else {
            	$.messager.alert("系统提示", "所选设备不存在，或存在网络延迟，请刷新页面", "warning");
            }
        });
    }).on('click', ".device-lora-otaa-del", function () { // 删除按钮事件
        var id = this.dataset.id;
        $.messager.confirm("删除提醒", "确定删除此设备？", function (r) {
            if (r) {
                $.get(bindToken(fun_urls.device.del_url), {
                    deviceId: id
                }, function (res) {
                    if (res.code == "0") {
                        $.messager.show({
                        	title: "删除设备",
                            msg: "删除成功",
                            timeout: 3000
                        });
                        $("#device_datagrid").datagrid("reload");
                    } else {
                    	$.messager.alert("系统提示", res.msg, "error");
                    }
                });
            }
        });
    });

    /**
     * 创建“创建设备”窗口
     */
    function createForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "创建设备",
            iconCls: 'fa fa-plus-square',
            height: 430,
            width: fixWidth(0.35),
            href: bindDialog('/service/deviceOTAAAdd'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#device-form");

                $(form).find('#groupId').combobox({
                    label: "关联设备组：",
                    textField: 'text',
                    url: bindToken(fun_urls.deviceGroup.list_url),
                    editable: false,
                    valueField: 'devGroupId',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'devGroupName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });

                $(form).find('#type').combobox({
                    textField: 'text',
                    label: "设备类型：",
                    url: bindToken(fun_urls.deviceType.list_url),
                    editable: false,
                    valueField: 'typeId',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'typeName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.device.lora_otaa_add_url), form.serialize(), function (res) {
                            console.log(form.serialize());
                            if (res.code == "0") {
                                $("#device_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "创建设备",
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
     * 创建“编辑设备信息”窗口
     */
    function createUpdateForm(id, device) {
        var userType;
        var buttonStatus;
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "编辑设备信息",
            iconCls: 'fa fa-edit',
            height: 475,
            width: fixWidth(0.35),
            href: bindDialog('/service/deviceOTAAUpdate'),
            queryParams: {
                id: id
            },
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#device-update-form");
                $(form).find('#deviceId').val(id);
                $(form).find('#code').textbox("setValue", device.code);
                $(form).find('#mac').textbox("setValue", device.mac);
                $(form).find('#appSKey').textbox("setValue", device.appSKey);
                $(form).find('#appEUI').textbox("setValue", device.appEUI);
                $(form).find('#desc').textbox("setValue", device.desc);
                $(form).find('#protocol').combobox('select', device.protocol);

                $(form).find('#groupId').combobox({
                    label: "关联设备组：",
                    disabled: false,
                    textField: 'text',
                    url: bindToken(fun_urls.deviceGroup.list_url),
                    editable: false,
                    valueField: 'devGroupId',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'devGroupName',
                    loadFilter: function (data) {
                        return data.data;
                    },
                    onLoadSuccess: function (dataOfGroupList) {
                        if (device.groupId) {
                        	// 遍历返回的设备组数组里是否有此设备的组,有则设置选择框的默认值为此设备的组
                            for (var i = 0; i < dataOfGroupList.data.length; i++) {
                                if (dataOfGroupList.data[i].devGroupId == device.groupId) {
                                	$(form).find('#groupId').combobox('select', device.groupId);
                                }
                            }
                        }
                    }
                });

                $(form).find('#type').combobox({
                    disabled: false,
                    textField: 'text',
                    label: "设备类型：",
                    url: bindToken(fun_urls.deviceType.list_url),
                    editable: false,
                    valueField: 'typeId',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'typeName',
                    loadFilter: function (data) {
                        return data.data;
                    },
                    onLoadSuccess: function (dataType) {
                        if (device.type) {
                        	// 遍历返回的设备类型数组里是否有此设备的类型,有则设置选择框的默认值为此设备的设备类型
                            for (var i = 0; i < dataType.data.length; i++) {
                                if (dataType.data[i].typeId == device.type) {
                                	$(form).find('#type').combobox('select', device.type);
                                }
                            }
                        }
                    }
                });

                if (device.status == 1) {
                    $(form).find("#status").switchbutton("check");
                    buttonStatus = 1;
                } else {
                    $(form).find("#status").switchbutton("uncheck");
                    buttonStatus = 2;
                }

                $(form).find('#status').switchbutton({
                    onChange: function (checked) {
                        if (checked == true) {
                            buttonStatus = 1;
                        } else {
                            buttonStatus = 2
                        }
                    }
                });
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    var paramToUpdate = form.serialize();
                    if (buttonStatus == 2) {
                        paramToUpdate += "&status=2";
                    }
                    paramToUpdate.replace('&status=on', '');
                    if (form.form('validate')) {
                    	// console.log(paramToUpdate);
                        $.post(bindToken(fun_urls.device.lora_otaa_update_url), paramToUpdate, function (res) {
                            if (res.code == "0") {
                                $("#device_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "编辑设备信息",
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

    /**
     * 创建“导入设备列表”窗口
     */
    function createDevImportForm() {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "导入设备列表",
            iconCls: 'fa fa-upload',
            height: 210,
            width: fixWidth(0.40),
            href: bindDialog('/service/deviceImport'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#group-dev-import-form");
                $('#deviceGroupIdForImport').combobox({
                    label: "选择设备组：",
                    textField: 'text',
                    url: bindToken(fun_urls.deviceGroup.list_url),
                    editable: false,
                    valueField: 'devGroupId',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'devGroupName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });
            },
            buttons: [{
                iconCls: 'fa fa-upload',
                text: '导入',
                handler: function () {
                    if (form.form('validate')) {
                        var formData = new FormData($("#group-dev-import-form")[0]);
                        $.ajax({
                            url: bindToken(fun_urls.device.lora_otaa_import_url),
                            type: 'POST',
                            data: formData,
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            success: function (returndata) {
                                if (returndata == true) {
                                    $("#device_datagrid").datagrid('reload');
                                    $.messager.show({
                                    	title: "导入设备列表",
                                        msg: "导入成功",
                                        timeout: 3000
                                    });
                                    dialog.dialog('close');
                                } else {
                                    $.messager.alert("系统提示", returndata.msg, "error");
                                    dialog.dialog('close');
                                }
                            }
                        });
                    }
                }
            }]
        });
    }

    /**
     * 创建“导出设备列表”窗口
     */
    function createDevExportForm() {
        var groupIdForExport = "";
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "导出设备列表",
            iconCls: 'fa fa-download',
            height: 160,
            width: fixWidth(0.30),
            href: bindDialog('/service/deviceExport'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#group-dev-export-form");

                $(form).find('#deviceGroupIdForExport').combobox({
                    label: "选择设备组：",
                    textField: 'text',
                    url: bindToken(fun_urls.deviceGroup.list_url),
                    editable: false,
                    valueField: 'devGroupId',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'devGroupName',
                    loadFilter: function (data) {
                    	var tempJsonStr = JSON.stringify(data.data);
                    	tempJsonStr = tempJsonStr.replace('[', '[{"devGroupId":"","devGroupName":"（全部设备组）"},');
                        return JSON.parse(tempJsonStr);
                    }, onSelect: function (data) {
                        groupIdForExport = data.devGroupId;
                    }
                })
            },
            buttons: [{
                iconCls: 'fa fa-download',
                text: '导出',
                handler: function () {
                	$(location).attr('href', bindToken(fun_urls.device.lora_otaa_export_url) + "&devGroupId=" + groupIdForExport);
                    $.messager.show({
                    	title: "导出设备列表",
                        msg: "导出成功",
                        timeout: 3000
                    });
                    dialog.dialog('close');
                }
            }]
        });
    }

    /**
     * 创建“批量分配设备组”窗口
     */
    function createBaDeviceGroupForm(deviceIds, deviceIdsNum) {
        var groupIdForExport;
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "批量分配设备组",
            iconCls: 'fa fa-th-large',
            height: 160,
            width: fixWidth(0.30),
            href: bindDialog('/service/baDeviceGroup'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#device-ba-deviceGroup-form");

                $(form).find('#devGroupIdList').combobox({
                    label: "设备组：",
                    disabled: false,
                    textField: 'text',
                    url: bindToken(fun_urls.deviceGroup.list_url),
                    editable: false,
                    valueField: 'devGroupId',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'devGroupName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });

                $(form).find("input[name=deviceIds]").val(deviceIds);
                $(form).find("#customText").text("您确定要将已选择的 " + deviceIdsNum + " 个设备分配到一个设备组下吗？");
            },
            buttons: [{
                iconCls: 'fa fa-check',
                text: '确定',
                handler: function () {
    	    		if (form.form('validate')) {
    	    			$.post(bindToken(fun_urls.device.ba_devGroup_url), form.serialize(), function(res) {
    	    				if (res.code == "0") {
                                $("#device_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "批量分配设备组",
                                    msg: "分配成功",
                                    timeout: 3000
                                });
                                dialog.dialog('close');
    	    				} else {
    	    					$.messager.alert("系统提示", res.msg, "error");
    	    				}
    	    			});
    	    		}
                }
            }, {
                iconCls: 'fa fa-close',
                text: '取消',
                handler: function () {
                	dialog.dialog('close');
                }
            }]
        });
    }

    /**
     * 创建“查看相关数据”窗口
     */
    function createViewForm(code) {
    	var tempHeight = fixHeight(0.90);

        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "查看相关数据【设备硬件编号 - DevEUI : " + code + "】",
            iconCls: 'fa fa-search',
            height: tempHeight,
            width: fixWidth(0.95),
            href: bindDialog('/service/deviceDataView'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                // 窗口表单加载成功时执行
                tempForm = $("#device_data_search_form");
                $(tempForm).find("#deviceCode").val(code);

                var temp_datagrid_height = tempHeight - $("#temp_data_search").height() - 86;
                $("#device_data_datagrid").parent().height(temp_datagrid_height);
                $("#device_data_datagrid").height(temp_datagrid_height);

                // 窗口表单加载成功时执行
                var temp_grid_columns = [
                    [{
                        field: 'dataId', hidden: 'true'
                    }, {
                        field: 'deviceCode', title: '设备硬件编号', width: 20, align: 'center', formatter: formatTitle
                    }, {
                    	field: 'createTime', title: '录入时间', width: 20, align: 'center', formatter: formatDatebox
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

                var temp_grid_data = [];
                var temp_datagrid = {
                    columns: temp_grid_columns,
                    idField: "dataId",
                    data: temp_grid_data,
                    fit: true,
                    rownumbers: true,
                    fitColumns: true,
                    border: false,
                    pagination: true,
                    singleSelect: true,
                    pageSize: 20,
                    method: "POST",
                    pageList: [10, 20, 30, 40, 50],
                    loadMsg: "正在加载企业数据"
                };

                /**
                 * 查询企业数据
                 */
                function dataPage() {
                    var params = $.serializeToJson(tempForm.serializeArray());

                    $('#device_data_datagrid').datagrid({
                        url: bindToken(fun_urls.data.page_url),
                        queryParams: params
                    });
                }

                $('#device_data_datagrid').datagrid(temp_datagrid);

                $(tempForm).find("#temp_data_search_button").on("click", function () {
                	dataPage();
                });

                $(tempForm).find("#temp_data_reset_button").on("click", function () {
                	tempForm.form('clear');
                    $('#device_data_datagrid').datagrid('options').queryParams = "";
                    $(tempForm).find("#deviceCode").val(code);
                });

                /**
                 * 接收数据单击事件
                 */
                $("#device_data_datagrid").datagrid("getPanel")
                .on('click', "a.ctr", function () {
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

                $(dataPage()); // 首次进入页面自动检索一次
            }
        });
    }

});