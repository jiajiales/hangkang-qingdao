var toolbars = {
    "resource-search": "检索",
    "resource-create": "创建",
    "resource-pwd-reset": "重置密码",
    "resource-edit": "编辑",
    "resource-del": "删除",
    "resource-search": "检索"
};
var keys;

var setKeys = function (v) {
    keys = v;
}

$(function () {
    var datagrid_height = $(window).height() - $("#resource_search").height() - 65;
    $("#resource_dg").parent().height(datagrid_height);
    // $("#resource_dg").height(datagrid_height);

    var form; // 表单对象，当表单窗口打开的时，被赋值

    var resource_tree = [];
    var treegrid = {
        data: resource_tree,
        fit: true,
        animate: true,
        idField: 'id',
        treeField: 'name',
        fitColumns: true,
        border: false,
        loadMsg: "正在加载资源数据",
        columns: [
            [{
                field: 'name', title: '资源名称', align: 'left', width: 60
            }, {
                field: 'type', title: '资源类型', align: 'left', width: 50
            }, {
                field: 'path', title: '资源路径', align: 'left', width: 60
            }, {
                field: 'key', title: '资源KEY值', align: 'left', width: 60
            }, {
                field: 'status', title: '状态', width: 30, align: 'left',
                formatter: function (val, row) {
                    return val == 1 ? "可用" : "禁用";
                }
            }, {
                field: 'pname', title: '父资源名称', align: 'left', width: 40
            }, {
                field: 'weight', title: '权重', align: 'left', width: 30
            }, {
                field: 'id', title: '操作', width: 50, align: 'center',
                formatter: function (value, row, index) {
                    return authToolBar({
                        "resource-edit": '<a data-id="' + value + '" class="ctr ctr-edit">编辑</a>',
                        "resource-delete": '<a data-id="' + value + '" class="ctr ctr-delete">删除</a>'
                    }).join("");
                }
            }]
        ],
        toolbar: authToolBar({
            "resource-search": {
                iconCls: 'fa fa-search',
                text: "检索",
                handler: function (v) {
                    //					if(filterFunCode("resource-search",keys)){
                    $.post(bindToken(fun_urls.resource.list_url), function (data) {
                        $(data.data).each(function (k, v) {
                            if (v.type == "1") {
                                v.type = "菜单";
                            } else if (v.type == "2") {
                                v.type = "API";
                            } else if (v.type == "3") {
                                v.type = "API和按钮";
                            } else if (v.type == "4") {
                                v.type = "按钮";
                            }
                        })

                        var resource_tree = transData(data.data, 'id', 'pid', 'children');
                        $('#resource_dg').treegrid({
                            data: resource_tree,
                        });
                    })
                        //		            }
                }
            },
            "resource-create": {
                iconCls: 'fa fa-plus-square',
                text: "创建资源",
                handler: function (v) {
                    //					if(filterFunCode("role-create",keys)){
                    createForm();
                    //		            }
                }
            }

        }),
        onError: function (index, data) {
            // 操作请求发送错误
            console.error(data);
        }
    };

    //渲染空页面
    $('#resource_dg').treegrid(treegrid);

    $('#resource_search_button').on('click', function () {

    });

    //重置按钮
    //	$("#resource_reset_button").on("click",function(){
    //		$('#resource_search_from').form('clear');  
    //		$('#resource_dg').datagrid('options').queryParams="";
    //	});

    $('#resource_dg').treegrid("getPanel").on('click', "a.ctr-edit", function () {
        // 编辑按钮事件
        var dglog = this;
        var dgId = this.dataset.id;
        $.post(bindToken(fun_urls.resource.get_url), {
            "id": this.dataset.id
        }, function (data) {
            if (data.code == "0") {
                var member = data.data;
                createUpdateForm.call(dglog, dgId, member)
            } else {
                $.messager.alert("系统提示", data.msg, "error");
            }
        });
    }).on('click', "a.ctr-delete", function () { // 删除按钮事件
        var id = this.dataset.id;
        if (id) {
            $.messager.confirm("删除提醒", "确定删除此资源？", function (r) {
                if (r) {
                    $.get(bindToken(fun_urls.resource.del_url), {
                        id: id
                    }, function (data) {
                        if (data.code == "0") {
                            // 数据操作成功后，对列表数据，进行刷新
                            $.messager.show({
                            	title: "删除资源",
                                msg: "删除成功",
                                timeout: 3000
                            });
                            //重新渲染
                            $.post(bindToken(fun_urls.resource.list_url), function (data) {
                                var resource_tree = transData(data.data, 'id', 'pid', 'children');
                                $('#resource_dg').treegrid({
                                    data: resource_tree
                                });
                            });
                        } else {
                            $.messager.alert("系统提示", data.msg, "error");
                        }
                    });
                }
            });
        } else {
            $.messager.alert("系统提示", "请选择删除对象", "warning");
        }
    });

    /**
     * 创建表单窗口
     *
     * @returns
     */

    //适配屏幕宽度
    function fixWidth(percent) {
        return document.body.clientWidth * percent;
    }

    function createForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: (id ? "编辑" : "创建") + "资源",
            iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
            height: 400,
            width: fixWidth(0.35),
            left: 260,
            top: 10,
            href: bindDialog('/sys/resourceAdd'),
            //			queryParams : {
            //				id : id
            //			},
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                // 窗口表单加载成功时执行
                form = $("#resource-form");
                $('#pname').combobox({
                    label: "父资源名称：",
                    url: bindToken(fun_urls.resource.menus_url),
                    editable: false,
                    multiple: false,
                    valueField: 'id',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'menuName',
                    loadFilter: function (data) {
                        return data.data;
                    }
                });

                $('#type').combobox({ //下拉框
                    label: "资源类型：",
                    data: [{
                        "id": 1,
                        "text": "菜单"
                    }, {
                        "id": 2,
                        "text": "API"
                    }, {
                        "id": 3,
                        "text": "API和按钮"
                    }, {
                        "id": 4,
                        "text": "按钮"
                    }],
                    editable: false,
                    multiple: false,
                    valueField: 'id',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'text',
                });
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    var form_info = form.serialize();
                    if (form.form('validate')) {
                        var status = $("input[name='status']").is(':checked');
                        if (!status) {
                            form_info = form_info + "&status=2";
                        }

                        $.post(bindToken(fun_urls.resource.add_url), form_info, function (res) {
                            if (res.code == "0") {
                                //重新渲染
                                $.post(bindToken(fun_urls.resource.list_url), function (data) {
                                    var i = 0;
                                    $(data.data).each(function (k, v) {
                                        if (v.type == "1") {
                                            data.data[i].type = "菜单";
                                        } else if (v.type == "2") {
                                            v.type = "API";
                                        } else if (v.type == "3") {
                                            v.type = "API和按钮";
                                        } else if (v.type == "4") {
                                            v.type = "按钮";
                                        }
                                        i++;
                                    })

                                    var resource_tree = transData(data.data, 'id', 'pid', 'children');
                                    $('#resource_dg').treegrid({
                                        data: resource_tree
                                    });
                                });
                                $.messager.show({
                                	title: "创建资源",
                                    msg: "创建成功",
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

    function createUpdateForm(id, resource) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "编辑资源",
            iconCls: 'fa fa-edit',
            height: 400,
            width: fixWidth(0.35),
            left: 260,
            top: 10,
            href: bindDialog('/sys/resourceUpdate'),
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
                form = $("#resource-form");
                $('#pname').combobox({ //下拉框
                    valueField: 'id',
                    label: "父资源名称",
                    url: bindToken(fun_urls.resource.menus_url),
                    editable: false,
                    multiple: false,
                    valueField: 'id',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'menuName',
                    loadFilter: function (data) {
                        return data.data;
                    },
                    onLoadSuccess: function () {
                        if (resource.pid) {
                            $('#pname').combobox('select', resource.pid);
                        }
                    }
                });

                $('#type').combobox({ //下拉框
                    label: "资源类型",
                    data: [{
                        "id": 1,
                        "text": "菜单"
                    }, {
                        "id": 2,
                        "text": "API"
                    }, {
                        "id": 3,
                        "text": "API和按钮"
                    }, {
                        "id": 4,
                        "text": "按钮"
                    }],
                    editable: false,
                    multiple: false,
                    valueField: 'id',
                    panelMaxHeight: 200,
                    panelHeight: 'auto',
                    textField: 'text',
                    onLoadSuccess: function () {
                        if (resource.type) {
                            $('#type').combobox('select', resource.type);
                        }
                    }
                });

                $(form).find("input[name=id]").val(resource.id);
                $(form).find("#name").textbox("setValue", resource.name);
                $(form).find("#path").textbox("setValue", resource.path);
                $(form).find("#key").textbox("setValue", resource.key);
                if (resource.status == 1) {
                    $(form).find("#status").switchbutton("check");
                } else {
                    $(form).find("#status").switchbutton("uncheck");
                }
                $(form).find("#weight").textbox("setValue", resource.weight);
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.resource.update_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                //重新渲染
                                $.post(bindToken(fun_urls.resource.list_url), function (data) {
                                    var i = 0;
                                    $(data.data).each(function (k, v) {
                                        if (v.type == "1") {
                                            data.data[i].type = "菜单";
                                        } else if (v.type == "2") {
                                            v.type = "API";
                                        } else if (v.type == "3") {
                                            v.type = "API和按钮";
                                        } else if (v.type == "4") {
                                            v.type = "按钮";
                                        }
                                        i++;
                                    })

                                    var resource_tree = transData(data.data, 'id', 'pid', 'children');
                                    $('#resource_dg').treegrid({
                                        data: resource_tree
                                    });
                                });
                                $.messager.show({
                                	title: "编辑资源",
                                    msg: "编辑成功",
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

    // 格式化树状列表数据
    function transData(a, idStr, pidStr, childrenStr) {
        var r = [], hash = {}, id = idStr, pid = pidStr, children = childrenStr, i = 0, j = 0, len = a.length;
        for (; i < len; i++) {
            hash[a[i][id]] = a[i];
        }
        for (; j < len; j++) {
            var aVal = a[j],
                hashVP = hash[aVal[pid]];
            if (hashVP) {
                !hashVP[children] && (hashVP[children] = []);
                hashVP[children].push(aVal);
            } else {
                r.push(aVal);
            }
        }
        return r;
    }

});