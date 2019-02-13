$(function(){

	var websocket = null;
	
    if ('WebSocket' in window) {
      	websocket = new WebSocket("ws://" + location.host + "/ws/server");
    }else if ('MozWebSocket' in window) {
      	websocket = new MozWebSocket("ws://" + location.host + "/ws/server");
    }
    else {
    	websocket = new SockJS("http://" + location.host + "/sockjs/server");	
    }
	
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    
    //点击导航菜单调节按钮背景颜色
    $(".crm-menu li").on('click',function(){
    	$(".crm-menu li").removeClass('click_bg');
    	$(this).addClass('click_bg');
    	
    });	
    
  //按钮触发  --> 选择或添加标签页
    $(".crm-menu li").on('click',function(){
    	var tab_name = $(this).html();
    	var tab_url = $(this).attr("href");
    	addTab(tab_name,tab_url);
    })
});

//添加标签页方法
function addTab(title, url){
	if ($('#regin-innder').tabs('exists', title)){
		$('#regin-innder').tabs('select', title);
		
		//当点击角色管理时触发  ，更新树状列表数据
		if(title == '角色管理'){
			var selTab = $('#regin-innder').tabs('getSelected');
	        var url = $(selTab.panel('options').content).attr('src');     
	        $('#regin-innder').tabs('update', {
	            tab: selTab,
	            options: {
	                url:url
	            }
	        })
		}
	} else {
		var content = '<iframe id="iframe_children" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:99%;"></iframe>';
		$('#regin-innder').tabs('add',{
			title:title,
			content:content,
			closable:true,
		});
	}
}


function onOpen(openEvt) {
	console.info(openEvt);    
}	

function onMessage(evt) {
	var json_msg = eval('(' + evt.data + ')');
	if(json_msg.data=="sessionKill"){
		 $.messager.alert("登录提醒", "登录超时，请重新登录！", 'info', function () {
			 logout();
		 });
	}
}

function onError() {
    $.messager.alert("网络提醒", "网络异常！请刷新！", 'info');
}