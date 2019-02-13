(function(w){
	var resourceKey = {};
	/**
	 * 工具栏的权限过滤
	 * @param tools
	 * @returns
	 */
	function authToolBar(tools) {
		var toolbars = [];

		$.each(tools, function(key, btn) {
			toolbars.push(btn);
		});
		if(arguments.length > 1){
			for(var i = 1;i < arguments.length; i++){
				toolbars.push(arguments[i])
			}
		}
		return toolbars;
	}
	w.authToolBar = authToolBar;
})(window);


jQuery.extend({
	// 设置 apDiv
	serializeToJson : function(serializeArray) {
		var values = {};
		if(serializeArray instanceof  Array){
			for (var item in serializeArray) {
			   values[serializeArray[item].name] = serializeArray[item].value;
			}
			return values;
		}
	}
});

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }

    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));

    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));

    return format;
}

function formatDatebox(value) {
    if (value == null || value == '') {
        return '';
    }
    var dt;
    if (value instanceof Date) {
        dt = value;
    } else {
        dt = new Date(value);
    }
    return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)
}

// 鼠标悬停显示提示
function formatTitle(value) {
	if(value != 0 && !value){
		value = "";
	}
    var format = '<div title="' + value + '">' + value + '</div>';
    return format;
}

$.extend($.fn.validatebox.defaults.rules, {
	// 验证手机号
    phoneNum: {
    	validator: function(value, param) {
        	return /^1[3-8]+\d{9}$/.test(value);
        },
        message: '请输入正确的手机号码'
    },

    // 既验证手机号，又验证座机号  
    telNum:{
    	validator: function(value, param) {  
        	return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3)|(\d{3}\-))?(1[358]\d{9})$)/.test(value);  
        },     
        message: '请输入正确的电话号码'  
    },

    // 验证是否包含空格和非法字符
    notSpecial: {
    	validator: function (value) {
        	var reg = new RegExp("[`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~@#￥%……&*（）——+|{}【】‘；：”“’。，、？]");
            return !reg.test(value);
    	},
    	message: '该输入不能含有特殊字符'
    },

    // 验证用户名
    memberName: {
    	validator: function (value) {
    		return /^[a-zA-Z][a-zA-Z0-9]+$/i.test(value);
    	},
    	message: '请输入正确格式的用户名（以字母开头，允许字母数字）'
    },

    // 验证两次确认密码是否一致
    comfirmNewPassWord: {
        validator: function(value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入的密码不一致'
    },

    // 验证姓名，可以是中文或英文
    realName: {
    	validator: function (value) {
    		return /^[\u4e00-\u9fa5]+$/i.test(value) | /^[a-zA-Z·\s]+$/i.test(value);
    	},
    	message: '请输入正确的中文姓名或英文姓名'
    },

    // 验证资源路径
    resourcePath: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9-/]+$/i.test(value);
    	},
    	message: '请输入正确格式的资源路径（允许字母数字，减号“-”和正斜杠“/”）'
    },

    // 验证资源KEY值
    resourceKey: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9-]+$/i.test(value);
    	},
    	message: '请输入正确格式的资源KEY值（允许字母数字和减号“-”）'
    },

    // 验证队列名称
    queueName: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9._]+$/i.test(value);
    	},
    	message: '请输入正确格式的队列名称（允许字母数字，小数点“.”和下划线“_”）'
    },

    // 验证虚拟主机地址
    vhost: {
    	validator: function (value) {
    		return /^[/][a-zA-Z0-9._]+$/i.test(value);
    	},
    	message: '请输入正确格式的虚拟主机地址（以正斜杠“/”开头，允许字母数字，小数点“.”和下划线“_”）'
    },

    // 验证虚拟主机账号
    vhostAccount: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9./_]+$/i.test(value);
    	},
    	message: '请输入正确格式的虚拟主机账号（允许字母数字，小数点“.”，正斜杠“/”和下划线“_”）'
    },

    // 验证虚拟主机密码
    vhostPassword: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9]+$/i.test(value);
    	},
    	message: '请输入正确格式的虚拟主机密码（允许字母数字）'
    },

    // 验证设备硬件编号
    deviceCode: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9]+$/i.test(value);
    	},
    	message: '请输入正确格式的设备硬件编号（允许字母数字）'
    },

    // 验证MAC地址
    macAddress: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9-:]+$/i.test(value);
    	},
    	message: '请输入正确格式的MAC地址（允许字母数字，减号“-”和冒号“:”）'
    },

    // 验证AppSKey
    appSKey: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9]+$/i.test(value);
    	},
    	message: '请输入正确格式的AppSKey（允许字母数字）'
    },

    // 验证AppEUI
    appEUI: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9]+$/i.test(value);
    	},
    	message: '请输入正确格式的AppEUI（允许字母数字）'
    },

    // 验证NwkSKey
    nwkSKey: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9]+$/i.test(value);
    	},
    	message: '请输入正确格式的NwkSKey（允许字母数字）'
    },

    // 验证设备类型名称
    deviceTypeName: {
    	validator: function (value) {
    		return /^[\u4e00-\u9fa5a-zA-Z0-9.()（）]+$/i.test(value);
    	},
    	message: '请输入正确格式的设备类型名称（不能含有特殊字符）'
    },

    // 验证设备类型Code
    deviceTypeCode: {
    	validator: function (value) {
    		return /^[a-zA-Z0-9]+$/i.test(value);
    	},
    	message: '请输入正确格式的设备类型编号（允许字母数字）'
    },

    // 验证设备数量阀值
    maxDeviceNums: {
    	validator: function (value) {
    		return value >= 1 && value <= 30000;
    	},
    	message: '设备数量阀值必须介于1~30000之间'
    }

});


function filterFunCode(code,keys){
	console.info(code);
	console.info(keys);
	var index = false;
	if(!index){
		  $.messager.alert("系统提示", "您未获得该按钮权限");
	}
	return index;
}

function bindToken(url){
	if(url){
		var host = localStorage.getItem("host");
		url = url.replace("host",host);
		var token = localStorage.getItem("token");
		url = url +"?token="+token;
	}
	return url;
}

// 生成按钮（根据权限隐藏按钮）
function generateButtons(buttons) {
	for(var b in buttons) {
		if (buttons[b] == false) {
			$('#' + b).hide(); // 工具栏按钮
			$('.' + b).hide(); // 表格操作按钮
		}
	}
}

function getCookie(name){
	var arr = document.cookie.split(";");

	for(var i=0; i<arr.length; i++) {
		var arr2 = arr[i].split("=");
		if(arr2[0] == name){
			return arr2[1];
		}
	}

	return "";
}

// 判断用户登录状态
function isMemberLogin() {
	var sessionid = getCookie("JSESSIONID");

	if (sessionid == null || sessionid == "") {
		return false;
	} else {
		return true;
	}
}

function onBeforeOpenDialog() {
//	if (!isMemberLogin()) {
//		$.messager.alert("登录提醒", "登录超时，请重新登录！", 'info', function () {
//			logout();
//		});
//		$(this).dialog("close");
//		return false;
//	}
	return true;
}

function bindDialog(url) {
	if(url){
		url = url + "?dialog=true";
	}
	return url;
}
