var keys;

var setKeys = function (v) {
    keys = v;
}

$(function () {
    $(get());

    $("#queue_refresh_button").on("click", function () {
        get.call(this);
        $.messager.show({
        	title: "队列信息",
            msg: "刷新完毕",
            timeout: 3000
        });
    });

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

    $("#queue_search_button").on("click", function () {
        var form = $("#queue_form");

        if (form.form('validate')) {
            var queueName = $(form).find("#queueName").val();

            if (queueName != "") {
                if (timer == 0) {
                    buttonCountdown($(this), 1000 * 30 * 1, "ss"); // 查询间隔:30秒

                    $.get(bindToken(fun_urls.queue.rabbit_view_url), {
                        queueName: queueName
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
            } else {
                $.messager.alert("系统提示", "队列名称不能为空！", "");
            }
        }
    });

    function getStatusText(queueStatus) {
        switch (queueStatus) {
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

    /**
     * 适配屏幕宽度
     */
    function fixWidth(percent) {
        return document.body.clientWidth * percent;
    }

    /**
     * 获取企业信息
     */
    function get() {
    	var form = $("#queue_search_form");

        $.post(bindToken(fun_urls.queue.queue_view_url), form.serialize(), function (data) {
            if (data.code == "0") {
                var queue = data.data;
                var form = $("#queue_form");

                form.find("#queueName").textbox('textbox').attr('readonly', false);
                form.find("#queueStatus").textbox('textbox').attr('readonly', false);
                form.find("#holeTime").textbox('textbox').attr('readonly', false);
                form.find("#receiveDataNum").textbox('textbox').attr('readonly', false);
                form.find("#waitSendNum").textbox('textbox').attr('readonly', false);
                form.find("#vhost").textbox('textbox').attr('readonly', false);
                form.find("#vhostAccount").textbox('textbox').attr('readonly', false);
                form.find("#vhostPassword").textbox('textbox').attr('readonly', false);

                form.find("#queueName").textbox("setValue", queue.queueName);
                form.find("#queueStatus").textbox("setValue", getStatusText(queue.queueStatus));
                form.find("#holeTime").textbox("setValue", formatDatebox(queue.holeTime));
                form.find("#receiveDataNum").textbox("setValue", queue.receiveDataNum);
                form.find("#waitSendNum").textbox("setValue", queue.waitSendNum);
                form.find("#vhost").textbox("setValue", queue.vhost);
                form.find("#vhostAccount").textbox("setValue", queue.vhostAccount);
                form.find("#vhostPassword").textbox("setValue", queue.vhostPassword);

                form.find("#queueName").textbox('textbox').attr('readonly', true);
                form.find("#queueStatus").textbox('textbox').attr('readonly', true);
                form.find("#holeTime").textbox('textbox').attr('readonly', true);
                form.find("#receiveDataNum").textbox('textbox').attr('readonly', true);
                form.find("#waitSendNum").textbox('textbox').attr('readonly', true);
                form.find("#vhost").textbox('textbox').attr('readonly', true);
                form.find("#vhostAccount").textbox('textbox').attr('readonly', true);
                form.find("#vhostPassword").textbox('textbox').attr('readonly', true);
            } else {
                $.messager.alert("系统提示", "获取队列信息失败", "error");
            }
        });
    }

});