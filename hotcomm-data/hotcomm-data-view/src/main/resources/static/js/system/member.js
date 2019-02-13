var toolbars = {
    "member-create": "创建用户",
    "member-pwd-reset": "重置密码",
    "member-edit": "编辑",
    "member-del": "删除",
    "member-search": "检索",
    "member-add-cm": "分配客户数据权限"
};
var keys;

var setKeys = function (v) {
    keys = v;
}

$(function () {
    var datagrid_height = $(window).height() - $("#member_search").height() - 98;
    $("#member_datagrid").parent().height(datagrid_height);
    $("#member_datagrid").height(datagrid_height);

    var curMemberId;
    var curMemberName;

    var grid_columns = [
        [{
            field: 'memberName', title: '账号', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'realName', title: '姓名', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'telephone', title: '手机', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'email', title: '邮箱', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'customerIds', hidden: 'true'
        }, {
            field: 'createUser', title: '创建人', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'createTime', title: '创建时间', width: 30, align: 'center', formatter: formatDatebox
        }, {
            field: 'status', title: '状态', width: 20, align: 'center', 
            formatter: function (val, row) {
                return val == "1" ? "有效" : "无效";
            }
        }, {
            field: 'roles', title: '角色', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'id', title: '操作', width: 30, align: 'center',
            formatter: function (value, row, index) {
                return authToolBar({
                    "member-edit": '<a data-id="' + row.id + '" class="ctr ctr-edit">' + toolbars["member-edit"] + '</a>',
                    "member-delete": '<a data-id="' + row.id + '" class="ctr ctr-delete">' + toolbars["member-del"] + '</a>'
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
        loadMsg: "正在加载用户数据",
        toolbar: authToolBar({
            "member-create": {
                iconCls: 'fa fa-user-plus',
                text: toolbars["member-create"],
                handler: function (v) {
                    /*if(filterFunCode("member-create",keys)){
	            		 createForm()
	            	}*/
                    createForm();
                }
            },
            "member-reset-password": {
                iconCls: 'fa fa-repeat',
                text: toolbars["member-pwd-reset"],
                handler: function () {
                    if (curMemberId) {
                        $.messager.confirm('系统提示', '确定要将用户【' + curMemberName + "】的密码重置为“00000”吗？", function (r) {
                            if (r) {
                                $.get(bindToken(fun_urls.member.pwd_reset_url), {id: curMemberId}, function (res) {
                                    if (res.code == "0") {
                                        $.messager.show({
                                        	title: "重置密码",
                                        	msg: "用户【" + curMemberName + "】的密码已被重置成功",
                                            width: 350,
                                            timeout: 3000
                                        });
                                    } else {
                                        $.messager.alert("系统提示", "用户【" + curMemberName + "】密码重置失败", "error");
                                    }
                                })
                            }
                        })
                    } else {
                    	$.messager.alert("系统提示", "请先选择一个用户", "warning");
                    }
                }
            },
            "member-add-cm": {
                iconCls: 'fa fa-database',
                text: toolbars["member-add-cm"],
                handler: function (v) {
                	if (curMemberId) {
                		var row = $('#member_datagrid').datagrid('getSelected');
                		createAddCMForm(row.customerIds);
                	} else {
                    	$.messager.alert("系统提示", "请先选择一个用户", "warning");
                    }
                }
            }
        }),
        onSelect: function (index, row) {
            if (row.id) {
            	curMemberId = row.id;
                curMemberName = row.memberName;
            }
        }
    };

    $('#member_datagrid').datagrid(datagrid);

    $("#member_search_button").on("click", function () {
        var params = $.serializeToJson($('#member_search_from').serializeArray());

        $('#member_datagrid').datagrid({
            url: bindToken(fun_urls.member.page_url),
            queryParams: params
        });
    })

    $("#member_reset_button").on("click", function () {
        $('#member_search_from').form('clear');
        $('#member_datagrid').datagrid('options').queryParams = "";
    });

    /**
     * 操作按钮绑定事件
     */
    $("#member_datagrid").datagrid("getPanel")
    .on('click', "a.ctr-edit", function () { // 编辑按钮事件
        var dglog = this;
        var dgId = this.dataset.id;

        $.post(bindToken(fun_urls.member.get_url), {"id": dgId}, function (data) {
            if (data.code == "0") {
                var member = data.data;
                createUpdateForm.call(dglog, dgId, member);
            } else {
            	$.messager.alert("系统提示", data.msg, "error");
            }
        });
    }).on('click', "a.ctr-delete", function () { // 删除按钮事件
        var dglog = this;
        var dgId = this.dataset.id;

        $.messager.confirm("删除提醒", "确定要删除用户【" + curMemberName + "】吗？", function (r) {
            if (r) {
                $.get(bindToken(fun_urls.member.del_url), {"id": dgId}, function (data) {
                    if (data.code == "0") {
                        // 数据操作成功后，对列表数据，进行刷新
                        $.messager.show({
                        	title: "删除用户",
                            msg: "删除成功",
                            timeout: 3000
                        });
                        $("#member_datagrid").datagrid("reload");
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
     * 创建"创建用户"窗口
     */
    function createForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "创建用户",
            iconCls: 'fa fa-user-plus',
            height: 400,
            width: fixWidth(0.35),
            href: bindDialog('/sys/memberAdd'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#member-form");
                $('#roles').combobox({
                    url: bindToken(fun_urls.role.list_url),
                    editable: false,
                    multiple: true,
                    valueField: 'id',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'roleName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });
                //这个字段比较特殊，有比较多的校验，所以单独拿出来实例化
                $("#member_userName").textbox({
                    label: '账号：',
                    required: true,
                    validType: ['memberName', 'length[6, 10]', "remote['/system/member/check','memberName']"]
                })
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    var roles = $("#roles").val();
                    var roles_str = roles + "";
                    $("#roles").val(roles_str);
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.member.add_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#member_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "创建用户",
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
     * 创建"编辑用户信息"窗口
     */
    function createUpdateForm(id, member) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "编辑用户信息",
            iconCls: 'fa fa-edit',
            height: 350,
            width: fixWidth(0.40),
            href: bindDialog('/sys/memberUpdate'),
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
                form = $("#member-form");
                $('#roles').combobox({
                    valueField: 'id',
                    textField: 'text',
                    url: bindToken(fun_urls.role.list_url),
                    required: true,
                    editable: false,
                    multiple: true,
                    valueField: 'id',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'roleName',
                    loadFilter: function (data) {
                        return data.data;
                    },
                    onLoadSuccess: function () {
                        if (member.roles) {
                            var role_array = member.roles.split(",");
                            $('#roles').combobox('setValues', role_array);
                        }
                    }
                });
                $(form).find("input[name=id]").val(member.id);
                $(form).find("#realName").textbox("setValue", member.realName);
                $(form).find("#memberName").textbox("setValue", member.memberName);
                $(form).find("#telephone").textbox("setValue", member.telephone);
                $(form).find("#email").textbox("setValue", member.email);
                if (member.status == 1) {
                    $(form).find("#status").switchbutton("check");
                } else {
                    $(form).find("#status").switchbutton("uncheck");
                }
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.member.update_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#member_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "编辑用户信息",
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
     * 创建"分配客户数据权限"窗口
     */
    function createAddCMForm(curCustomerIds) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "分配客户数据权限",
            iconCls: 'fa fa-database',
            height: 220,
            width: fixWidth(0.40),
            href: bindDialog('/sys/memberAddCm'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#member-add-cm-form");

                $(form).find('#customerIds').combobox({
                    label: "",
                    editable: false,
                    url: bindToken(fun_urls.customer.list_url),
                    queryParams: {"source":1},
                    multiple: true, //多选功能
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    valueField: 'id',
                    textField: 'memberName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });

                var strs = new Array();
                if (curCustomerIds != null && curCustomerIds != "") {
                    strs = curCustomerIds.split(",");
                    for (i = 0; i < strs.length; i++) {
                    	$(form).find('#customerIds').combobox('select', strs[i]);
                    }
                }
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                    	var selectCustomerIds = $(form).find('#customerIds').val();
                    	var params = "memberId=" + curMemberId + "&customerIds=" + selectCustomerIds;

                        $.post(bindToken(fun_urls.member.add_cm_url), params, function (res) {
                            if (res.code == "0") {
                                $("#member_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "分配客户数据权限",
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
            }]
        });
    }

});