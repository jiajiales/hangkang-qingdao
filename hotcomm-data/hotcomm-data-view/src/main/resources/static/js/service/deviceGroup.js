var toolbars = {
    "deviceGroup-upload": "导入",
    "deviceGroup-create": "创建设备组",
    "deviceGroup-pwd-reset": "重置密码",
    "deviceGroup-use-members": "分配客户",
    "deviceGroup-edit": "编辑",
    "deviceGroup-del": "删除",
    "deviceGroup-search": "检索",
    "deviceGroup-output": "导出",
    "deviceGroup-list-export": "导出设备列表"
};

var memberIds;
var buttons;

var setButtons = function (v) {
	buttons = JSON.parse(v);
}

$(function () {
    var datagrid_height = $(window).height() - $("#deviceGroup_search").height() - 98;
    $("#deviceGroup_datagrid").parent().height(datagrid_height);
    $("#deviceGroup_datagrid").height(datagrid_height);

    $('#deviceGroupId').combobox({
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

    $('#selectMemberId').combobox({
        url: bindToken(fun_urls.customer.list_url),
        editable: false,
        valueField: 'id',
        panelMaxHeight: 200,
        panelHeight: 'auto',
        textField: 'memberName',
        loadFilter: function (data) {
            return data.data;
        },
        onSelect: function (data) {
            // var str = JSON.stringify(data);
            // data.memberName=data.memberName+"(当前客户)";
            // alert(data.memberName);
            // $('#MemberIdList').combobox('setValue',1111);//一定要先value后text,否则text与value值会相同全为value值
            // $('#MemberIdList').combobox('setText', 1111);
            var queryParams = new Object();
            queryParams.selectMemberId = data.id;
            $('#deviceGroupId').combobox({
                textField: 'text',
                url: bindToken(fun_urls.deviceGroup.list_url),
                queryParams: queryParams,
                editable: false,
                valueField: 'devGroupId',
                panelMaxHeight: 200,
                panelHeight: 'auto',
                textField: 'devGroupName',
                loadFilter: function (data) {
                    return data.data;
                }
            });
        }
    });

    var grid_columns = [
        [{
            field: 'devGroupName', title: '设备组名称', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'memberIds', hidden: 'true'
        }, {
            field: 'memberNames', title: '关联客户', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'devNum', title: '设备数量', width: 15, align: 'center', formatter: formatTitle
        }, {
            field: 'devMax', title: '设备数量阀值', width: 15, align: 'center',
            formatter: function (value, row) {
                var content = '<div title="' + '该设备组最多只能关联' + value + '个设备">' + value + '</div>';
                return content;
            }
        }, {
            field: 'remark', title: '设备组备注', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'groupStatus', title: '状态', width: 15, align: 'center',
            formatter: function (val, row) {
                return val == "1" ? "有效" : "无效";
            }
        }, {
            field: 'devGroupId', title: '操作', width: 30, align: 'center',
            formatter: function (value, row, index) {
                return authToolBar({
                    "deviceGroup-use-members": '<span data-id="' + row.devGroupId + '" class="ctr fa-group device-group-use_members">' + toolbars["deviceGroup-use-members"] + '</span>',
                    "deviceGroup-edit": '<span data-id="' + row.devGroupId + '" class="ctr ctr-edit device-group-update">' + toolbars["deviceGroup-edit"] + '</span>',
                    "deviceGroup-delete": '<span data-id="' + row.devGroupId + '" class="ctr ctr-delete device-group-del">' + toolbars["deviceGroup-del"] + '</span>'
                }).join("");
            }
        }]
    ]

    var grid_data = [];
    var datagrid = {
        columns: grid_columns,
        idField: "devGroupId",
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
        loadMsg: "正在加载设备组数据",
        toolbar: authToolBar({
            "deviceGroup-create": {
            	id: 'device-group-add',
                iconCls: 'fa fa-plus-square',
                text: toolbars["deviceGroup-create"],
                handler: function (v) {
                    createForm();
                }
            },
            "deviceGroup-list-export": {
            	id: 'device-export',
                iconCls: 'fa fa-download',
                text: toolbars["deviceGroup-list-export"],
                handler: function () {
                    var row = $('#deviceGroup_datagrid').datagrid('getSelected');
                    if (row) {
                        $.messager.confirm("导出设备提醒", "将导出与组" + row.devGroupName + "关联的设备,确定吗？", function (r) {
                            if (r) {
                                $(location).attr('href', bindToken(fun_urls.device.output_dev) + "&devGroupId=" + row.devGroupId);
                            }
                        })
                    } else {
                        $.messager.confirm("导出设备提醒", "你未选中需要导出设备的关联设备组,此举将会导出所有设备,确定吗？", function (r) {
                            if (r) {
                                $(location).attr('href', bindToken(fun_urls.device.output_dev));
                            }
                        })
                    }
                }
            }
        }),
        onLoadSuccess: function () {
			generateButtons(buttons);
        }
    };

    function fixWidth(percent) {
        return document.body.clientWidth * percent;
    }

    function deviceGroupPage() {
        var params = $.serializeToJson($('#deviceGroup_search_from').serializeArray());
        //console.log(params);
        $('#deviceGroup_datagrid').datagrid({
            url: bindToken(fun_urls.deviceGroup.page_url),
            queryParams: params
        });
    }

    $('#deviceGroup_datagrid').datagrid(datagrid);
    $(deviceGroupPage()); // 首次进入页面自动检索一次

    ////////////////////////////////////////////////////////////////////////////////////点击上传
    //	$("#deviceGroup_upload_button").on("click",function(){
    //		var formData = new FormData($( "#deviceGroup_search_from" )[0]); 
    //		
    //		if(jQuery("input[type='file']").val()==""){
    //		    //未选择文件
    //			alert("未选中文件");
    //		}else{
    //	     $.ajax({  
    //	    	  url: bindToken("http://localhost:8085/deviceGroup/deviceGroupTypeManage/addListdeviceGroup"),
    //	          type: 'POST',  
    //	          data: formData,  
    //	          async: false,  
    //	          cache: false,  
    //	          contentType: false,  
    //	          processData: false,  
    //	          success: function (returndata) {  
    //	        	  $("#deviceGroup_datagrid").datagrid("reload");
    //	              alert("上传成功");  
    //	          },  
    //	          error: function (returndata) {  
    //	        	  alert("上传失败!"); 
    //	        	  console.log(formData);
    //	          }  
    //	     });  
    //		}
    //	});

    $("#deviceGroup_search_button").on("click", function () {
    	deviceGroupPage();
    });

    $("#deviceGroup_reset_button").on("click", function () {
        $('#deviceGroup_search_from').form('clear');
        $('#deviceGroup_datagrid').datagrid('options').queryParams = "";
    });

    /**
     * 操作按钮绑定事件
     */
    $("#deviceGroup_datagrid").datagrid("getPanel")
    .on('click', ".device-group-use_members", function () {
        var dglog = this;
        var dgId = this.dataset.id;
        var row = $('#deviceGroup_datagrid').datagrid('getSelected');
        memberIds = row.memberIds;
        creatMemberGroupFrom.call(dglog, dgId);
    })
    .on('click', ".device-group-update", function () { // 编辑按钮事件
        var dglog = this;
        var dgId = this.dataset.id;
        $.post(bindToken(fun_urls.deviceGroup.get_url), {"groupId": dgId}, function (data) {
            if (data.code == "0") {
                var deviceGroup = data.data;
                createUpdateForm.call(dglog, dgId, deviceGroup)
            } else {
                $.messager.alert("系统提示", data.msg, "error");
            }
        });
    }).on('click', ".device-group-del", function () { // 删除按钮事件
        var id = this.dataset.id;
        var row = $('#deviceGroup_datagrid').datagrid('getSelected');

        if (row.devNum > 0) {
        	$.messager.alert("系统提示", "该设备组与设备有关联，不能删除", "warning");
        } else {
            $.messager.confirm("删除提醒", "确定删除此设备组？", function (r) {
                if (r) {
                    $.post(bindToken(fun_urls.deviceGroup.del_url), {groupId: id}, function (data) {
                        if (data.code == "0") {
                            $.messager.show({
                            	title: "删除设备组",
                                msg: "删除成功",
                                timeout: 2000
                            });
                            // 刷新设备组列表
                            $('#deviceGroupId').combobox({
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
                            $("#deviceGroup_datagrid").datagrid("reload");
                        } else {
                            $.messager.alert("系统提示", data.msg, "error");
                        }
                    });
                }
            });
        }
    });

    /**
     * 创建"创建设备组"窗口
     */
    function createForm(id) {
        var buttonStatus;
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "创建设备组",
            iconCls: 'fa fa-plus-square',
            height: 265,
            width: fixWidth(0.35),
            href: bindDialog('/service/deviceGroupAdd'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#deviceGroup-form");

                $('#groupStatus').switchbutton({
                    onChange: function (checked) {
                        if (checked == false) {
                            $(form).find("#groupStatus").val(2);
                            buttonStatus = 2;
                        } else {
                            buttonStatus = 1;
                        }
                    }
                });
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        var paramsToInsert = form.serialize();
                        if (buttonStatus == 2) {
                            paramsToInsert += "&groupStatus=2";
                        }
                        $.post(bindToken(fun_urls.deviceGroup.add_url), paramsToInsert, function (res) {
                            if (res.code == 0) {
                                console.log(res);
                                $("#deviceGroup_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "创建设备组",
                                    msg: "创建成功",
                                    timeout: 3000
                                });
                                dialog.dialog('close');
                            } else {
                                console.log(res);
                                $.messager.alert("系统提示", res.msg, "error");
                            }
                        });
                    }
                }
            }]
        });
    }

    /**
     * 创建"设备组分配客户"窗口
     */
    function creatMemberGroupFrom(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "设备组分配客户",
            iconCls: "fa fa-edit",
            height: 220,
            width: 420,
            href: bindDialog('/service/deviceGroupMember'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#deviceGroup-member-form");
                $(form).find("#groupId").val(id);

                $('#groupMembers').combobox({
                    label: "",
                    editable: false,
                    url: bindToken(fun_urls.customer.list_url),
                    multiple: true,
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    valueField: 'id',
                    textField: 'memberName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });

                var strs = new Array();

                if (memberIds != null && memberIds != "") {
                    strs = memberIds.split(",");

                    for (i = 0; i < strs.length; i++) {
                        $('#groupMembers').combobox('select', strs[i]);
                    }
                }
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    var paramsToGroupMember = form.serialize();
                    re = new RegExp("&groupMembers=", "g");
                    paramsToGroupMember = paramsToGroupMember.replace(re, ",");
                    $.post(bindToken(fun_urls.deviceGroup.member_group), paramsToGroupMember, function (res) {
                        if (res.code == "0") {
                            $("#deviceGroup_datagrid").datagrid('reload');
                            $.messager.show({
                            	title: "设备组分配客户",
                                msg: "分配成功",
                                timeout: 3000
                            });
                            // 刷新设备组列表
                            $('#deviceGroupId').combobox({
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
                            dialog.dialog('close');
                        } else {
                            console.log(res);
                            $.messager.alert("系统提示", res.msg, "error");
                        }
                    });
                }
            }]
        });
    }

    /**
     * 创建"编辑设备组信息"窗口
     */
    function createUpdateForm(id, deviceGroup) {
        var buttonStatus;
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "编辑设备组信息",
            iconCls: 'fa fa-edit',
            height: 260,
            width: 420,
            href: bindDialog('/service/deviceGroupUpdate'),
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
                form = $("#deviceGroup-form");
                $(form).find("#groupName").textbox("setValue", deviceGroup.groupName);
                $(form).find("#remark").textbox("setValue", deviceGroup.remark);
                $(form).find("#maxNums").textbox("setValue", deviceGroup.maxNums);
                if (deviceGroup.groupStatus == 1) {
                    $(form).find("#groupStatus").switchbutton("check");
                    $(form).find("#groupStatus").val(1);
                } else {
                    $(form).find("#groupStatus").switchbutton("uncheck");
                    $(form).find("#groupStatus").val(2);
                }
                $('#groupStatus').switchbutton({
                    onChange: function (checked) {
                        if (checked == true) {
                            $(form).find("#groupStatus").val(1);
                        } else {
                            $(form).find("#groupStatus").val(2);
                        }
                    }
                });
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    var paramToUpdate = form.serialize();
                    if ($(form).find("#groupStatus").val() == 2) {
                        paramToUpdate += "&groupStatus=2";
                    }
                    if (deviceGroup.groupStatus != 1) {
                        paramToUpdate += "&groupStatus=2";
                    }
                    if (form.form('validate')) {
                        console.log(paramToUpdate);
                        $.post(bindToken(fun_urls.deviceGroup.update_url), paramToUpdate + "&groupId=" + id, function (res) {
                            if (res.code == "0") {
                                $("#deviceGroup_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "编辑设备组信息",
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