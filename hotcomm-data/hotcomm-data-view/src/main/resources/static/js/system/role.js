var toolbars = {
    "role-search": "检索",
    "role-create": "创建",
    "role-pwd-reset": "重置密码",
    "role-edit": "编辑",
    "role-del": "删除",
    "role-search": "检索"
};
var keys;

var setKeys = function (v) {
    keys = v;
}

$(function () {
    var datagrid_height = $(window).height() - $("#role_search").height() - 98;
    $("#role_dg").parent().height(datagrid_height);
    $("#role_dg").height(datagrid_height);

    var resourceTree = $("#role-resource-tree");
    var resourcePanel = $("#role-resource-panel");
    var currentRoleId;

    var grid_columns = [
        [{
            field: 'roleName', title: '姓名', width: 30, align: 'center'
        }, {
            field: 'desc', title: '描述', width: 40, align: 'center'
        }, {
            field: 'status', title: '状态', width: 30, align: 'center',
            formatter: function (val, row) {
                return val == "1" ? "可用" : "禁用";
            }
        }]
    ];

    var grid_data = [];
    var datagrid = {
        columns: grid_columns,
        idField: "id",
        data: grid_data,
        fit: true,
        rownumbers: true,
        fitColumns: true,
        border: false,
        singleSelect: true,
        loadMsg: "正在加载角色数据",
        toolbar: authToolBar({
            "role-search": {
                iconCls: 'fa fa-search',
                text: toolbars["role-search"],
                handler: function () {
                    //		        	if(filterFunCode("role-search",keys)){
                    $.post(bindToken(fun_urls.role.list_url), function (data) {
                        $('#role_dg').datagrid({
                            data: data.data,
                        });
                    });
                    //	        			sessionStorage.setItem('isShow', true);	
                    //		            }
                }
            },
            "role-create": {
                iconCls: 'fa fa-plus-square',
                text: toolbars["role-create"],
                handler: function (v) {
                    //		        	if(filterFunCode("role-create",keys)){
                    createForm();
                    //		            }
                }
            },
            "role-edit": {
                iconCls: 'ctr ctr-edit',
                text: toolbars["role-edit"],
                handler: function (v) {
                    //		        	if(filterFunCode("role-edit",keys)){
                    if (currentRoleId) {
                        var dglog = this;
                        var dgId = currentRoleId;
                        $.post(bindToken(fun_urls.role.get_url), {
                            "id": currentRoleId
                        }, function (data) {
                            if (data.code == "0") {
                                var role = data.data;
                                createUpdateForm.call(dglog, dgId, role)
                            } else {
                                $.messager.alert("系统提示", data.msg, "error");
                            }
                        });
                    } else {
                        $.messager.alert("系统提示", "请选择修改对象", "warning");
                    }
                    //		            }
                }
            },
            "role-del": {
                iconCls: 'ctr ctr-delete',
                text: toolbars["role-del"],
                handler: function (v) {
                    //		        	if(filterFunCode("role-delete",keys)){
                    if (currentRoleId) {
                        $.post(bindToken(fun_urls.role.checkRole_url), {roleId: currentRoleId}, function (data_check) {
                            if (!data_check.data) {
                                $.messager.confirm("删除提醒", "确定删除此角色？", function (r) {
                                    if (r) {
                                        $.get(bindToken(fun_urls.role.del_url), {
                                            id: currentRoleId
                                        }, function (data) {
                                            if (data.code == "0") {
                                                // 数据操作成功后，对列表数据，进行刷新
                                                $.messager.show({
                                                	title: "删除角色",
                                                    msg: "删除成功",
                                                    timeout: 3000
                                                });
                                                //重新渲染
                                                $.post(bindToken(fun_urls.role.list_url), function (data) {
                                                    $('#role_dg').datagrid({
                                                        data: data.data
                                                    });
                                                });
                                            } else {
                                                $.messager.alert("系统提示", data.msg, "error");
                                            }
                                        });
                                    }
                                });
                            } else {
                                $.messager.alert("系统提示", "仍有使用该角色的用户，无法删除", "warning");
                            }
                        })
                    } else {
                        $.messager.alert("系统提示", "请选择删除对象", "warning");
                    }
                    //		            }
                }
            }
        }),
        onError: function (index, data) {
            // 操作请求发送错误
            console.error(data);
        },
        onSelect: function (index, row) {
            if (row.roleName) {
                currentRoleId = row.id;
                //清除上一个已经选中的
                var checked = resourceTree.tree('getChecked');
                $.each(checked, function () {
                    resourceTree.tree('uncheck', this.target);
                });

                // 选中已有节点
                $.post(bindToken(fun_urls.role.getRes_url), {
                    "roleId": currentRoleId
                }, function (data) {
                    //同步请求
                    $.ajaxSetup({
                        async: false
                    });
                    //获取全部父类各ID的儿子数量  a【】
                    var a = [];
                    $.post(bindToken(fun_urls.resource.menus_url), function (data_menus) {
                        $(data_menus.data).each(function (k, v) {
                            var node = resourceTree.tree('find', v.id);
                            if (node.children) {
                                var nlength = node.children.length;
                                var nchildren = node.children;
                                a.push({
                                    "id": v.id,
                                    "children_num": node.children.length
                                })
                            }
                        })
                    })
                    //-----------------------------------------------
                    //获取数据库已选节点
                    var identity = data.data.resourceIds.toString().split(',');
                    var num;
                    //获取数据库中已分配角色各资源ID的父类和自身id  b【】
                    var b = [];
                    $(identity).each(function (k, v) {
                        var node = resourceTree.tree('find', v);
                        if (node != null && node.pid) {
                            b.push({
                                "id": node.id,
                                "pid": node.pid
                            })
                        }
                    })
                    //获取b【】父类id中与a【】ID相同的儿子的数量
                    var c = [];
                    for (var i = 0; i < a.length; i++) {
                        var sum = 0;
                        for (var j = 0; j < b.length; j++) {
                            if (a[i].id == b[j].pid) {
                                sum++;
                            }
                        }
                        c.push({
                            "id": a[i].id,
                            "sum": sum
                        })
                    }
                    //	            	console.log(a);
                    //	            	console.log(b);
                    //	            	console.log(identity)
                    //	            	console.log(c);

                    //判断、过滤identity
                    $(c).each(function (kc, vc) {
                        //	            		console.log(c);
                        $(a).each(function (ka, va) {
                            if (vc.id == va.id) {
                                if (vc.sum <= va.children_num) {
                                    removeByValue(identity, vc.id.toString());
                                }
                            }
                        })
                    })

                    //	            	console.log(identity)
                    $(identity).each(function (k, v) {
                        var node = resourceTree.tree('find', v);
                        if (node !== null) {
                            // 判断叶子节点才执行check方法
                            resourceTree.tree('check', node.target);
                        }
                    });
                })
            }
        }
    };
    //渲染空页面
    $('#role_dg').datagrid(datagrid);

    /**
     * 操作按钮绑定事件
     */
    //实例化权限树
    resourceTree.tree({
        url: bindToken(fun_urls.resource.list_url),
        loadFilter: function (data) {
            var tree_data = transData(data.data, 'id', 'pid', 'children');
            return tree_data;
        },
        idField: 'id',
        //	    treeField:'name',
        checkbox: true,
        cascadeCheck: true,
        //	    onlyLeafCheck:true,    //叶节点加复选框
        animate: true,
        selectOnCheck: true
    });

    //	var isShow = sessionStorage.getItem('isShow');
    //	console.log(isShow);
    //	if(isShow){
    //		$.post(bindToken(fun_urls.role.list_url),function(data){
    //			$('#role_dg').datagrid({
    //				data:data.data,
    //			});
    //		});
    //	}
    //	

    //重置按钮
    //	$("#role_reset_button").on("click",function(){
    //		$('#role_search_from').form('clear');  
    //		$('#role_dg').datagrid('options').queryParams="";
    //	});

    $("#role-resource-save").on("click", function () {
        if (currentRoleId) {
            // 获取需要关联的资源节点
            var nodes = resourceTree.tree('getChecked', ['checked', 'indeterminate']);
            // 获取节点的ID列表
            var resourceId = "";
            var resourceId_list = "";
            $.each(nodes, function (k, v) {
                resourceId = resourceId + v.id + ',';
            });
            var final_resourceId = resourceId.slice(0, -1)
            $.post(bindToken(fun_urls.role.addRoleResource_url), {
                id: currentRoleId,
                resourceIds: final_resourceId
            }, function (data) {
                if (data.code == "0") {
                    //            		console.log(final_resourceId);
                    // 数据操作成功后，对列表数据，进行刷新
                    $.messager.show({
                    	title: "分配资源",
                        msg: "分配成功",
                        timeout: 3000
                    });
                } else {
                    $.messager.alert("系统提示", data.msg, "error");
                    console.log(data);
                }
            });
        } else {
            $.messager.alert("系统提示", "请选择一个角色", "warning");
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
            title: (id ? "编辑" : "创建") + "角色",
            iconCls: 'fa ' + (id ? "fa-edit" : "fa-plus-square"),
            height: 230,
            width: fixWidth(0.35),
            left: 260,
            top: 10,
            href: bindDialog('/sys/roleAdd'),
            modal: true,
            onBeforeOpen:onBeforeOpenDialog,
            onClose: function () {
                $(this).dialog("destroy");
            },
            onLoad: function () {
                //窗口表单加载成功时执行
                form = $("#role-form");
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.role.add_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                //重新渲染
                                $.post(bindToken(fun_urls.role.list_url), function (data) {
                                    $('#role_dg').datagrid({
                                        data: data.data
                                    });
                                });
                                $.messager.show({
                                	title: "创建角色",
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

    /**
     * 创建表单窗口
     *
     * @returns
     */
    function createUpdateForm(id, role) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: ("编辑") + "角色",
            iconCls: 'fa fa-edit',
            height: 230,
            width: fixWidth(0.35),
            left: 260,
            top: 10,
            href: bindDialog('/sys/roleUpdate'),
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
                form = $("#role-form");
                $(form).find("input[name=id]").val(role.id);
                $(form).find("#roleName").textbox("setValue", role.roleName);
                $(form).find("#desc").textbox("setValue", role.desc);
                if (role.status == 1) {
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
                        $.post(bindToken(fun_urls.role.update_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                //重新渲染
                                $.post(bindToken(fun_urls.role.list_url), function (data) {
                                    $('#role_dg').datagrid({
                                        data: data.data,
                                    });
                                });
                                $.messager.show({
                                	title: "修改角色",
                                    msg: "修改成功",
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

    //格式化树状列表数据
    function transData(a, idStr, pidStr, childrenStr) {
        var r = [],
            hash = {},
            id = idStr,
            pid = pidStr,
            children = childrenStr,
            i = 0,
            j = 0,
            len = a.length;
        //a为数据库返回的data数据，
        //获取id为1到a.length的所有对象1：{···}，2：{···}，3：{···}，···
        for (; i < len; i++) {
            hash[a[i][id]] = a[i];
        }
        for (; j < len; j++) {
            //aVal:获取a对象的数据0:{···}，1：{···}，2：{···}，···
            //hashVP:获取各个父类对象
            var aVal = a[j],
                hashVP = hash[aVal[pid]];
            //判断如果存在hashVP（该父类对象），则在其父类对象添加其子对象aVal
            if (hashVP) {
                //如果不存在children？？  为其创建children？先执行前面代码，如果为ture则执行后面代码，反之不执行？
                !hashVP[children] && (hashVP[children] = []);
                //在hashVP对象的children键中添加子对象
                hashVP[children].push(aVal);
                //如果不存在父对象hashVP，则将子对象aVal存入最终的数组中    
            } else {
                r.push(aVal);
            }
        }
        return r;
    }

    //删除数组元素
    function removeByValue(arr, val) {
        for (var i = 0; i < arr.length; i++) {
            if (arr[i] == val) {
                arr.splice(i, 1);
                break;
            }
        }
    }
});