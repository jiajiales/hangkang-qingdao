$(function(){
	//按钮触发  --> 修改管理员信息
	$("#public_change_info").on("click", function() {
		var log = this;
		var id =  $("#memberId_hidden").val();
		$.post(bindToken(fun_urls.member.get_url),{"id":id},function(data){
			if(data.code=="0"){
				var member = data.data;
				//console.log(member.realName);
				createUpdateForm.call(log,id,member)
			}else{
				$.messager.alert("系统提示", data.msg, "error");
			}
		});
	});
	
	//按钮触发  --> 注销
	$("#public_logout").on("click",function(){
		$.ajax({
			type : 'post',
			dataType : 'json',
			url : "/logout",
			async:false,
			success : function(json) {
			   	if(json.code==0){
			   		window.location.href="/login";
			   	}
			}
		});
	});
	
	//按钮触发  --> 修改密码
	$("#public_change_password").on('click', function() {
		var log = this;
		var id = $("#memberId_hidden").val();
		createChangePswForm.call(log, id);
	});
	
	//加载时，为对应元素添加data-lang属性（实现中英切换）
    $('#caidan .panel-title').each(function(index,item){
		$(item).attr("data-lang","string_mk"+index)
	});
	$(".easyui-accordion div ul li").each(function(index,item){
		var a = $(item).attr('href').split('/')
		$(item).attr('data-lang',"string_"+a[a.length-1])
	});
})


//编辑管理员信息方法
function createUpdateForm(id,member) {
	var buttonStatus;
	var dialog = $("<div/>", {
		class : 'noflow'
	}).dialog({
		title : "编辑管理员信息",
		iconCls : 'fa fa-edit',
		height : 260,
		width : 420,
		href : bindDialog('/sys/memberUpdate'),
		modal : true,
		onBeforeOpen:onBeforeOpenDialog,
		onClose : function() {
			$(this).dialog("destroy");
		},
		onLoad : function() {
			form = $("#member-form");
			$("input[name='id']").val(id);
			$(form).find("#realName").textbox("setValue", member.realName);
			$(form).find("#memberName").textbox("setValue", member.memberName);
			$(form).find("#memberName").textbox('textbox').attr('disabled', 'disabled');
			$(form).find("#telephone").textbox("setValue", member.telephone);
			$(form).find("#email").textbox("setValue", member.email);
			$("#rolesParent").css("display", "none");
			$("#statusParent").css("display", "none");
		},
		buttons : [{
			iconCls : 'fa fa-save',
			text : '保存',
			handler : function() {
				var paramToUpdate = form.serialize();
				if (form.form('validate')) {
					//console.log(paramToUpdate);
					$.post(bindToken(fun_urls.member.update_url),paramToUpdate, function(res) {
						if (res.code == "0") {
							$.messager.show({
								title: "编辑管理员信息",
								msg : "编辑成功",
								timeout : 3000
							});
							dialog.dialog('close');
						} else {
							$.messager.alert("系统提示", "修改失败", "error");
						}
					});
				}
			}
		}]		
	});
}

//注销方法
function logout() {
	$.ajax({
		type : 'post',
		dataType : 'json',
		url : "/logout",
		async : false,
		success : function(json) {
			if (json.code == 0) {
				window.location.href = "/login";
			}
		}
	});
}

//修改密码方法
function createChangePswForm(id) {
    var dialog = $("<div/>", {class: 'noflow'}).dialog({
    	title: "修改密码",
	    iconCls: 'fa fa-lock',
	    height: 220,
	    width: 420,
	    href: bindDialog('/sys/memberPswSet'),
	    modal: true,
	    onBeforeOpen:onBeforeOpenDialog,
	    onClose: function () {
	        $(this).dialog("destroy");
	    },
	    onLoad: function () {
	        //窗口表单加载成功时执行
	        form = $("#member-psw-form");
	        $(form).find("input[name=id]").val(id);
	    },
	    buttons: [{
	    	iconCls: 'fa fa-repeat',
	    	text: '确认修改',
	    	handler: function () {
	    		if (form.form('validate')) {
	    			$.post(bindToken(fun_urls.member.pwd_set_url), form.serialize(), function(res) {
	    				if(res.code=="0"){
	    					$.messager.alert("系统提示", "修改密码成功，请重新登录！", 'info', function () {
	    						logout();
	    					});
	    				}else{
	  					  	$.messager.alert("系统提示", res.msg, "error");
	    				}
	    			});
	    		}
	    	}
	    }]
	});
}