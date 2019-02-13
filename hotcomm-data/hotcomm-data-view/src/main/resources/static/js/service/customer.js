var toolbars = {
    "customer-create": "创建客户",
    "member-pwd-reset": "重置密码",
    "vhost-config": "配置虚拟主机信息",
    "vhost-author": "虚拟主机授权通过",
    "vhost-update": "变更虚拟主机信息",
    "customer-status-update": "变更客户状态",
    "customer-edit": "编辑",
    "customer-del": "删除",
    "customer-force-del": "强制删除"
};

var buttons;

var setButtons = function (v) {
	buttons = JSON.parse(v);
}

$(function () {
    var datagrid_height = $(window).height() - $("#customer_search").height() - 98;
    $("#customer_datagrid").parent().height(datagrid_height);
    $("#customer_datagrid").height(datagrid_height);

    var curMemberId;
    var curMemberName;
    var curVhostStatus;

    var grid_columns = [
        [{
            field: 'memberName', title: '账号', width: 20, align: 'center', formatter: formatTitle
        }, {
            field: 'realName', title: '姓名', width: 20, align: 'center', formatter: formatTitle
        }, {
            field: 'telephone', title: '手机', width: 20, align: 'center', formatter: formatTitle
        }, {
            field: 'email', title: '邮箱', width: 20, align: 'center', formatter: formatTitle
        }, {
            field: 'vhost', title: '虚拟主机地址', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'vhostAccount', title: '虚拟主机账号', width: 20, align: 'center', formatter: formatTitle
        }, {
            field: 'vhostPassword', hidden: 'true'
        }, {
            field: 'vhostStatus', title: '虚拟主机状态', width: 20, align: 'center',
            formatter: function (val) {
                switch (val) {
                case 1:
                    return "未分配";
                    break;
                case 2:
                    return "已分配";
                    break;
                case 3:
                    return "授权通过";
                    break;
                default:
                    return "";
                }
            }
        }, {
            field: 'status', title: '客户状态', width: 15, align: 'center', 
            formatter: function (val, row) {
                return val == "1" ? "有效" : "无效";
            }
        }, {
            field: 'createUser', title: '创建人', width: 15, align: 'center', formatter: formatTitle
        }, {
            field: 'createTime', title: '创建时间', width: 30, align: 'center', formatter: formatDatebox
        }, {
            field: 'id', title: '操作', width: 35, align: 'center',
            formatter: function (value, row, index) {
                return authToolBar({
                    "customer-edit": '<span data-id="' + row.id + '" class="ctr ctr-edit customer-update">' + toolbars["customer-edit"] + '</span>',
                    "customer-delete": '<span data-id="' + row.id + '" class="ctr ctr-delete customer-del">' + toolbars["customer-del"] + '</span>',
                    "customer-force-delete": '<span data-id="' + row.id + '" class="ctr ctr-delete customer-forceDel">' + toolbars["customer-force-del"] + '</span>'
                }).join("");
            }
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
        loadMsg: "正在加载客户数据",
        toolbar: authToolBar({
            "customer-create": {
            	id:"customer-add",
            	iconCls: 'fa fa-user-plus',
                text: toolbars["customer-create"],
                handler: function (v) {
                    /*if(filterFunCode("customer-create",keys)){
	            		 createForm()
	            	}*/
                    createForm();
                }
            },
            "customer-status-update": {
            	id:"customer-update-status",
                iconCls: 'fa fa-exchange',
                text: toolbars["customer-status-update"],
                handler: function (v) {
                    if (curMemberId) {
                    	var row = $('#customer_datagrid').datagrid('getSelected');
                    	var customerStatus = row.status;

                    	if (customerStatus == 1) {
                            $.messager.confirm('系统提示', '当客户状态变更为无效时，会同步暂停队列，并停止发送数据，您确定要变更吗？', function (r) {
                            	if (r) {
                                    $.post(bindToken(fun_urls.customer.update_status_url), {id: curMemberId}, function (res) {
                                        if (res.code == "0") {
                                            $("#customer_datagrid").datagrid('reload');
                                            $.messager.show({
                                            	title: "变更客户状态",
                                            	msg: "客户【" + curMemberName + "】状态已变更为无效",
                                                timeout: 3000
                                            });
                                            dialog.dialog('close');
                                        } else {
                                            $.messager.alert("系统提示", res.msg, "error");
                                        }
                                    });
                            	}
                            });
                    	} else {
                            $.post(bindToken(fun_urls.customer.update_status_url), {id: curMemberId}, function (res) {
                                if (res.code == "0") {
                                    $("#customer_datagrid").datagrid('reload');
                                    $.messager.show({
                                    	title: "变更客户状态",
                                        msg: "客户【" + curMemberName + "】状态已变更为有效",
                                        timeout: 3000
                                    });
                                    dialog.dialog('close');
                                } else {
                                    $.messager.alert("系统提示", res.msg, "error");
                                }
                            });
                    	}
                    } else {
                        $.messager.alert("系统提示", "请先选择一个客户", "warning");
                    }
                }
            },
            "member-reset-password": {
            	id:"customer-password-reset",
                iconCls: 'fa fa-repeat',
                text: toolbars["member-pwd-reset"],
                handler: function () {
                    if (curMemberName) {
                        $.messager.confirm('系统提示', '确定要将客户【' + curMemberName + '】的密码重置为“00000”吗？', function (r) {
                            if (r) {
                                $.get(bindToken(fun_urls.member.pwd_reset_url), {id: curMemberId}, function (res) {
                                    if (res.code == "0") {
                                        $.messager.show({
                                        	title: "重置密码",
                                            msg: "客户【" + curMemberName + "】密码重置成功",
                                            width: 350,
                                            timeout: 3000
                                        });
                                    } else {
                                        $.messager.alert("系统提示", "客户【" + curMemberName + "】密码重置失败", "error");
                                    }
                                })
                            }
                        })
                    } else {
                    	$.messager.alert("系统提示", "请先选择一个客户", "warning");
                    }
                }
            },
            "vhost-config": {
            	id:"customer-vhost-config",
                iconCls: 'fa fa-cog',
                text: toolbars["vhost-config"],
                handler: function (v) {
                    if (curMemberId) {
                        if (curVhostStatus == 1) {
                            var dglog = this;
                            var dgId = this.dataset.id;
                            createVhostConfigForm.call(dglog, dgId);
                        } else {
                            $.messager.alert("系统提示", "只能在虚拟主机“未分配”的状态下配置", "warning");
                        }
                    } else {
                        $.messager.alert("系统提示", "请先选择一个客户", "warning");
                    }
                }
            },
            "vhost-update": {
            	id:"customer-vhost-config-update",
                iconCls: 'fa fa-wrench',
                text: toolbars["vhost-update"],
                handler: function (v) {
                    if (curMemberId) {
                    	if (curVhostStatus == 1) {
                    		$.messager.alert("系统提示", "请先配置虚拟主机信息", "warning");
                    	} else if (curVhostStatus == 2) {
                            var dglog = this;
                            var dgId = this.dataset.id;
                            var row = $('#customer_datagrid').datagrid('getSelected');
                            createVhostUpdateForm.call(dglog, dgId, row);
                    	} else {
                    		$.messager.alert("系统提示", "该虚拟主机已授权通过，不能再进行更改", "warning");
                    	}
                    } else {
                        $.messager.alert("系统提示", "请先选择一个客户", "warning");
                    }
                }
            },
            "vhost-author": {
            	id:"customer-vhost-config-author",
                iconCls: 'fa fa-check-square',
                text: toolbars["vhost-author"],
                handler: function (v) {
                    if (curMemberId) {
                        if (curVhostStatus == 2) {
                            $.post(bindToken(fun_urls.customer.vhost_author_url), {memberId: curMemberId}, function (res) {
                                if (res.code == "0") {
                                    $("#customer_datagrid").datagrid('reload');
                                    curVhostStatus = 3;
                                    $.messager.show({
                                    	title: "虚拟主机授权通过",
                                        msg: "客户【" + curMemberName + "】虚拟主机授权通过成功！",
                                        timeout: 3000
                                    });
                                    dialog.dialog('close');
                                } else {
                                    $.messager.alert("系统提示", res.msg, "error");
                                }
                            });
                        } else if (curVhostStatus == 3) {
                            $.messager.alert("系统提示", "该虚拟主机已经处于“授权通过”的状态", "warning");
                        } else {
                            $.messager.alert("系统提示", "该客户的虚拟主机信息未配置，不能授权通过", "warning");
                        }
                    } else {
                        $.messager.alert("系统提示", "请先选择一个客户", "warning");
                    }
                }
            }
        }),
        onLoadSuccess: function () {
			generateButtons(buttons);
        },
        onSelect: function (index, row) {
            if (row.id) {
                curMemberId = row.id;
                curMemberName = row.memberName;
                curVhostStatus = row.vhostStatus;
            }
        }
    };

    function customerPage() {
        var params = $.serializeToJson($('#customer_search_from').serializeArray());
        $('#customer_datagrid').datagrid({
            url: bindToken(fun_urls.customer.page_url),
            queryParams: params
        });
    }

    $('#customer_datagrid').datagrid(datagrid);
    $(customerPage()); // 首次进入页面自动检索一次

    $("#customer_search_button").on("click", function () {
    	customerPage();
    });

    $("#customer_reset_button").on("click", function () {
        $('#customer_search_from').form('clear');
        $('#customer_datagrid').datagrid('options').queryParams = "";
    });

    /**
     * 操作按钮绑定事件
     */
    $("#customer_datagrid").datagrid("getPanel")
    .on('click', ".customer-update", function () { // 编辑按钮事件
        var dglog = this;
        var dgId = this.dataset.id;

        $.post(bindToken(fun_urls.customer.get_url), {
            "id": this.dataset.id
        },
        function (data) {
            if (data.code == "0") {
                var member = data.data;
                createUpdateForm.call(dglog, dgId, member)
            } else {
            	$.messager.alert("系统提示", data.msg, "error");
            }
        });
    }).on('click', ".customer-del", function () { // 删除按钮事件
        $.messager.confirm("删除提醒", "确定要删除客户【" + curMemberName + "】吗？", function (r) {
            if (r) {
                $.get(bindToken(fun_urls.customer.del_url), {id: curMemberId}, function (data) {
                    if (data.code == "0") {
                        // 数据操作成功后，对列表数据，进行刷新
                        $.messager.show({
                        	title: "删除客户",
                        	msg: "删除成功",
                            timeout: 3000
                        });
                        $("#customer_datagrid").datagrid("reload");
                    } else {
                    	$.messager.alert("系统提示", data.msg, "error");
                    }
                });
            }
        });
    }).on('click', ".customer-forceDel", function () { // 强制删除按钮事件
        $.messager.confirm("删除提醒", "确定要强制删除客户【" + curMemberName + "】吗？（该操作不作任何条件的检验，将直接删除客户）", function (r) {
            if (r) {
                $.get(bindToken(fun_urls.customer.force_del_url), {id: curMemberId}, function (data) {
                    if (data.code == "0") {
                        // 数据操作成功后，对列表数据，进行刷新
                        $.messager.show({
                        	title: "强制删除客户",
                        	msg: "强制删除成功",
                            timeout: 3000
                        });
                        $("#customer_datagrid").datagrid("reload");
                    } else {
                    	$.messager.alert("系统提示", data.msg, "error");
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
     * 创建"创建客户"窗口
     */
    function createForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "创建客户",
            iconCls: 'fa fa-user-plus',
            height: 290,
            width: fixWidth(0.35),
            href: bindDialog('/service/customerAdd'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#customer-form");
                //这个字段比较特殊，有比较多的校验，所以单独拿出来实例化
                $("#customer_userName").textbox({
                    label: '账号：',
                    required: true,
                    validType: ['memberName', 'length[6, 10]', "remote['/system/member/check','memberName']"]
                })
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.customer.add_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#customer_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "创建客户",
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
     * 创建"编辑客户信息"窗口
     */
    function createUpdateForm(id, member) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "编辑客户信息",
            iconCls: 'fa fa-edit',
            height: 250,
            width: fixWidth(0.35),
            href: bindDialog('/service/customerUpdate'),
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
                form = $("#customer-form");
                $(form).find("input[name=id]").val(member.id);
                $(form).find("#realName").textbox("setValue", member.realName);
                $(form).find("#memberName").textbox("setValue", member.memberName);
                $(form).find("#telephone").textbox("setValue", member.telephone);
                $(form).find("#email").textbox("setValue", member.email);
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.member.update_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#customer_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "编辑客户信息",
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
     * 创建"配置虚拟主机信息"窗口
     */
    function createVhostConfigForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "配置虚拟主机信息",
            iconCls: 'fa fa-cog',
            height: 240,
            width: fixWidth(0.35),
            href: bindDialog('/service/customerVhostConfig'),
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
                form = $("#vhost-config-form");
                $(form).find("input[name=memberId]").val(curMemberId);
                $(form).find("#customText").text("正在为客户【" + curMemberName + "】配置虚拟主机信息");
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.customer.vhost_config_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#customer_datagrid").datagrid('reload');
                                curVhostStatus = 2;
                                $.messager.show({
                                	title: "配置虚拟主机信息",
                                	width: "300px",
                                	height: "135px",
                                    msg: "客户【" + curMemberName + "】配置虚拟主机信息成功！",
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
     * 创建"变更虚拟主机信息"窗口
     */
    function createVhostUpdateForm(id, row) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "变更虚拟主机信息",
            iconCls: 'fa fa-wrench',
            height: 240,
            width: fixWidth(0.35),
            href: bindDialog('/service/customerVhostConfig'),
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
                form = $("#vhost-config-form");
                $(form).find("input[name=memberId]").val(row.id);
                $(form).find("#vhost").textbox("setValue", row.vhost);
                $(form).find("#vhostAccount").textbox("setValue", row.vhostAccount);
                $(form).find("#vhostPassword").textbox("setValue", row.vhostPassword);
                $(form).find("#customText").text("正在为客户【" + row.memberName + "】变更虚拟主机信息");
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.customer.vhost_update_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#customer_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "变更虚拟主机信息",
                                	width: "300px",
                                	height: "135px",
                                    msg: "客户【" + curMemberName + "】变更虚拟主机信息成功！",
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