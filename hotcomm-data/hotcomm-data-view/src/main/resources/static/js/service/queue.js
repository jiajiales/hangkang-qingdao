var toolbars = {
    "queue-run": "队列运行",
    "queue-pause": "队列暂停",
    "queue-rename": "变更队列名称",
    "queue-holeTime-update": "变更队列有效期限",
    "queue-filternums-update": "变更队列拦截基数",
    "queue-get": "查看",
    "queue-edit": "编辑"
};

var buttons;

var setButtons = function (v) {
	buttons = JSON.parse(v);
}

$(function () {
    var datagrid_height = $(window).height() - $("#queue_search").height() - 98;
    $("#queue_datagrid").parent().height(datagrid_height);
    $("#queue_datagrid").height(datagrid_height);

    $('#MemberIdList').combobox({
        disabled: false,
        url: bindToken(fun_urls.customer.list_url),
        editable: false,
        valueField: 'id',
        panelMaxHeight: 200,
        panelHeight: 'auto',
        textField: 'memberName',
        loadFilter: function (data) {
            return data.data;
        }
    });

    var curQueueId;
    var curQueueName;
    var curQueueStatus;
    var curHoleTime;
    var curRemark;

    var grid_columns = [
        [{
            field: 'queueName', title: '队列名称', width: 30, align: 'center', formatter: formatTitle
        }, {
            field: 'memberName', title: '关联客户', width: 15, align: 'center', formatter: formatTitle
        }, {
            field: 'queueStatus', title: '队列状态', width: 10, align: 'center',
            formatter: function (val) {
                switch (val) {
                case 1:
                    return "队列运行";
                    break;
                case 2:
                    return "队列暂停";
                    break;
                default:
                    return "";
                }
            }
        }, {
            field: 'type', title: '队列类型', width: 10, align: 'center',
            formatter: function (val) {
                switch (val) {
                case 1:
                    return "上行";
                    break;
                case 2:
                    return "下行";
                    break;
                default:
                    return "";
                }
            }
        }, {
            field: 'holeTime', title: '队列有效期限', width: 15, align: 'center', formatter: formatDatebox
        }, {
            field: 'createTime', title: '创建时间', width: 15, align: 'center', formatter: formatDatebox
        }, {
            field: 'remark', title: '备注', width: 10, align: 'center', formatter: formatTitle
        }, {
            field: 'queueId', title: '操作', width: 15, align: 'center',
            formatter: function (val) {
                return authToolBar({
                    "queue-get": '<span data-id="' + val + '" class="ctr fa-info-circle queue-get">' + toolbars["queue-get"] + '</span>',
                    "queue-edit": '<span data-id="' + val + '" class="ctr ctr-edit queue-update">' + toolbars["queue-edit"] + '</span>'
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
        loadMsg: "正在加载队列数据",
        toolbar: authToolBar({
            "queue-run": {
            	id: 'queue-run',
                iconCls: 'fa fa-play-circle',
                text: toolbars["queue-run"],
                handler: function (v) {
                    if (curQueueId) {
                        if (curQueueStatus == 2) {
                            $.post(bindToken(fun_urls.queue.run_url), {queueId: curQueueId}, function (res) {
                                if (res.code == "0") {
                                    $("#queue_datagrid").datagrid('reload');
                                    curQueueStatus = 1;
                                    $.messager.show({
                                    	title: "队列运行",
                                        msg: "队列【" + curQueueName + "】已运行",
                                        width: 380,
                                        timeout: 3000
                                    });
                                } else {
                                    $.messager.alert("系统提示", res.msg, "error");
                                }
                            });
                        } else {
                        	$.messager.alert("系统提示", "该队列已经处于运行状态", "warning");
                        }
                    } else {
                        $.messager.alert("系统提示", "请先选择一个队列", "warning");
                    }
                }
            },
            "queue-pause": {
            	id: 'queue-pause',
                iconCls: 'fa fa-pause-circle',
                text: toolbars["queue-pause"],
                handler: function (v) {
                    if (curQueueId) {
                        if (curQueueStatus == 1) {
                            $.post(bindToken(fun_urls.queue.pause_url), {queueId: curQueueId}, function (res) {
                                if (res.code == "0") {
                                    $("#queue_datagrid").datagrid('reload');
                                    curQueueStatus = 2;
                                    $.messager.show({
                                    	title: "队列暂停",
                                        msg: "队列【" + curQueueName + "】已暂停",
                                        width: 380,
                                        timeout: 3000
                                    });
                                } else {
                                    $.messager.alert("系统提示", res.msg, "error");
                                }
                            });
                        } else {
                        	$.messager.alert("系统提示", "该队列已经处于暂停状态", "warning");
                        }
                    } else {
                        $.messager.alert("系统提示", "请先选择一个队列", "warning");
                    }
                }
            },
            "queue-rename": {
            	id: 'queue-rename',
                iconCls: 'fa fa-key',
                text: toolbars["queue-rename"],
                handler: function (v) {
                    if (curQueueId) {
                        if (curQueueStatus == 2) {
                            var dglog = this;
                            var dgId = this.dataset.id;
                            createQueueRenameForm.call(dglog, dgId);
                        } else {
                        	$.messager.alert("系统提示", "只能在“队列暂停”时修改队列名称", "warning");
                        }
                    } else {
                        $.messager.alert("系统提示", "请先选择一个队列", "warning");
                    }
                }
            },
            "queue-holeTime-update": {
            	id: 'queue-update-holeTime',
                iconCls: 'fa fa-calendar-check-o',
                text: toolbars["queue-holeTime-update"],
                handler: function (v, row) {
                    if (curQueueId) {
                        if (curQueueStatus == 2) {
                            var dglog = this;
                            var dgId = this.dataset.id;
                            createUpdateHoleTimeForm.call(dglog, dgId);
                        } else {
                        	$.messager.alert("系统提示", "只能在“队列暂停”时修改队列有效期限", "warning");
                        }
                    } else {
                        $.messager.alert("系统提示", "请先选择一个队列", "warning");
                    }
                }
            },
            "queue-filternums-update": {
            	id: 'queue-update-filternums',
                iconCls: 'fa fa-truck',
                text: toolbars["queue-filternums-update"],
                handler: function (v) {
                    if (curQueueId) {
                        if (curQueueStatus == 2) {
                            var dglog = this;
                            var dgId = this.dataset.id;
                            createUpdateFilternumsForm.call(dglog, dgId);
                        } else {
                        	$.messager.alert("系统提示", "只能在“队列暂停”时修改队列拦截基数", "warning");
                        }
                    } else {
                        $.messager.alert("系统提示", "请先选择一个队列", "warning");
                    }
                }
            }
        }),
        onLoadSuccess: function () {
			generateButtons(buttons);
        },
        onSelect: function (index, row) {
            if (row.queueId) {
                curQueueId = row.queueId;
                curQueueName = row.queueName;
                curQueueStatus = row.queueStatus;
                curHoleTime = row.holeTime;
                curRemark = row.remark;
            }
        }
    };

    function queuePage() {
        var params = $.serializeToJson($('#queue_search_form').serializeArray());
        $('#queue_datagrid').datagrid({
            url: bindToken(fun_urls.queue.page_url),
            queryParams: params
        });
    }

    $('#queue_datagrid').datagrid(datagrid);
    $(queuePage()); // 首次进入页面自动检索一次

    $("#queue_search_button").on("click", function () {
    	queuePage();
    });

    $("#queue_reset_button").on("click", function () {
        $('#queue_search_form').form('clear');
        $('#queue_datagrid').datagrid('options').queryParams = "";
    });

    /**
     * 操作按钮绑定事件
     */
    $("#queue_datagrid").datagrid("getPanel")
    .on('click', ".queue-get", function () { // 查看按钮事件
        var dglog = this;
        var dgId = this.dataset.id;

        $.post(bindToken(fun_urls.queue.get_url), {"queueId": dgId}, function (data) {
            if (data.code == "0") {
                createQueueGetForm.call(dglog, dgId, data.data)
            } else {
            	$.messager.alert("系统提示", data.msg, "error");
            }
        });
    })
    .on('click', ".queue-update", function () { // 编辑按钮事件
        var dglog = this;
        var dgId = this.dataset.id;
        createQueueUpdateForm.call(dglog, dgId);
    });

    /**
     * 适配屏幕宽度
     */
    function fixWidth(percent) {
        return document.body.clientWidth * percent;
    }

    /**
     * 创建"变更队列名称"窗口
     */
    function createQueueRenameForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "变更队列名称",
            iconCls: 'fa fa-key',
            height: 120,
            width: fixWidth(0.35),
            href: bindDialog('/service/queueRename'),
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
                form = $("#queue-rename-form");
                $(form).find("input[name=queueId]").val(curQueueId);
                $(form).find("#queueName").textbox("setValue", curQueueName);
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.queue.rename_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#queue_datagrid").datagrid('reload');
                                curQueueName = $(form).find("#queueName").val();
                                $.messager.show({
                                	title: "变更队列名称",
                                    msg: "变更成功",
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

    function formatDateboxWithoutHM(value) {
        if (value == null || value == '') {
            return '';
        }

        var dt;

        if (value instanceof Date) {
            dt = value;
        } else {
            dt = new Date(value);
        }

        return dt.format("yyyy-MM-dd"); // 扩展的Date的format方法(上述插件实现)
    }

    /**
     * 创建"变更队列有效期限"窗口
     */
    function createUpdateHoleTimeForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "变更队列有效期限",
            iconCls: 'fa fa-calendar-check-o',
            height: 120,
            width: fixWidth(0.35),
            href: bindDialog('/service/queueUpdateHoleTime'),
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
                form = $("#queue-holeTime-form");
                $(form).find("input[name=queueId]").val(curQueueId);
                $(form).find("#holeTime").textbox("setValue", formatDateboxWithoutHM(curHoleTime));
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.queue.update_holeTime_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#queue_datagrid").datagrid('reload');
                                curHoleTime = $(form).find("#holeTime").val();
                                $.messager.show({
                                	title: "变更队列有效期限",
                                    msg: "变更成功",
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
     * 创建"变更队列拦截基数"窗口
     */
    function createUpdateFilternumsForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "变更队列拦截基数",
            iconCls: 'fa fa-truck',
            height: 120,
            width: fixWidth(0.35),
            href: bindDialog('/service/queueUpdateFilternums'),
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
                form = $("#queue-filternums-form");
                $(form).find("input[name=queueId]").val(curQueueId);
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.queue.update_filternums_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#queue_datagrid").datagrid('reload');
                                $.messager.show({
                                	title: "变更队列拦截基数",
                                    msg: "变更成功",
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

    // jQuery倒计时按钮
    var timer = 0;

    function buttonCountdown($el, msNum, timeFormat) {
        clearTimeout(timer);
        var text = $el.data("text") || $el.text();
        $el.prop("disabled", true).addClass("disabled")
            .on("bc.clear", function () {
                clearTime();
            });

        (function countdown() {
            var time = showTime(msNum)[timeFormat];
            $el.text(time + '后再查询');
            $el.css({
                "font-size": "12px",
                "display": "inline-block",
                "vertical-align": "top",
                "width": "auto",
                "line-height": "24px",
                "font-size": "12px",
                "padding": "0 4px",
                "margin": "0 4px",
                "outline": "none",
            });
            $el.addClass("fa fa-search")
            if (msNum <= 0) {
                msNum = 0;
                timer = 0;
                clearTime();
            } else {
                msNum -= 1000;
                timer = setTimeout(arguments.callee, 1000);
            }
        })();

        return this;

        function clearTime() {
            clearTimeout(timer);
            $el.prop("disabled", false).removeClass("disabled").text(text);
        }

        function showTime(ms) {
            var d = Math.floor(ms / 1000 / 60 / 60 / 24),
                h = Math.floor(ms / 1000 / 60 / 60 % 24),
                m = Math.floor(ms / 1000 / 60 % 60),
                s = Math.floor(ms / 1000 % 60),
                ss = Math.floor(ms / 1000);

            return {
                d: d + "天",
                h: h + "小时",
                m: m + "分",
                ss: ss + "秒",
                "d:h:m:s": d + "天" + h + "小时" + m + "分" + s + "秒",
                "h:m:s": h + "小时" + m + "分" + s + "秒",
                "m:s": m + "分" + s + "秒"
            };
        }
    }

    /**
     * 创建"查看队列信息"窗口
     */
    function createQueueGetForm(id, queue) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "查看队列信息",
            iconCls: 'fa fa-info-circle',
            height: 255,
            width: fixWidth(0.45),
            href: bindDialog('/service/queueGet'),
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
                form = $("#queue-get-form");
                $(form).find("#queueName").textbox("setValue", queue.queueName);
                $(form).find("#holeTime").textbox("setValue", formatDatebox(queue.holeTime));
                $(form).find("#sendFilterInterval").textbox("setValue", queue.sendFilterInterval);
                $(form).find("#receiveDataNum").textbox("setValue", queue.receiveDataNum);
                $(form).find("#waitSendNum").textbox("setValue", queue.waitSendNum);
            },
            buttons: [{
                iconCls: 'fa fa-search',
                text: '查询',
                handler: function () {
                    if (form.form('validate')) {
                        if (timer == 0) {
                            buttonCountdown($(this), 1000 * 30 * 1, "ss"); // 查询间隔:30秒

                            $.get(bindToken(fun_urls.queue.rabbit_view_url), {
                            	queueName: curQueueName
                            }, function (res) {
                                if (res.code == "0") {
                                    var data = res.data;
                                    form.find("#messages").textbox('textbox').attr('readonly', false);
                                    form.find("#consumers").textbox('textbox').attr('readonly', false);
                                    form.find("#messages").textbox("setValue", data.messages);
                                    form.find("#consumers").textbox("setValue", data.consumers);
                                    form.find("#messages").textbox('textbox').attr('readonly', true);
                                    form.find("#consumers").textbox('textbox').attr('readonly', true);
                                } else {
                                    $.messager.alert("系统提示", res.msg, "error");
                                }
                            });
                        }
                    }
                }
            }]
        });
    }

    /**
     * 创建"编辑队列信息"窗口
     */
    function createQueueUpdateForm(id) {
        var dialog = $("<div/>", {
            class: 'noflow'
        }).dialog({
            title: "编辑队列信息",
            iconCls: 'fa fa-edit',
            height: 120,
            width: fixWidth(0.45),
            id:"edit_queue_message",
            href: bindDialog('/service/queueUpdate'),
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
                form = $("#queue-update-form");
                $(form).find("input[name=queueId]").val(id);
                $(form).find("#remark").textbox("setValue", curRemark);
            },
            buttons: [{
                iconCls: 'fa fa-save',
                text: '保存',
                handler: function () {
                    if (form.form('validate')) {
                        $.post(bindToken(fun_urls.queue.update_url), form.serialize(), function (res) {
                            if (res.code == "0") {
                                $("#queue_datagrid").datagrid('reload');
                                curRemark = $(form).find("#remark").val();
                                $.messager.show({
                                	title: "编辑队列信息",
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