var language_pack = {  
    now_lang : 0, // 0:ch,1:en  
    loadProperties : function(new_lang){  
        var self = this;  
        var tmp_lang = '';  
        if(new_lang == 0){  
            tmp_lang = 'zh';  
            $('body').removeClass('en').addClass('zh');  
        }else{  
            tmp_lang = 'en';  
            $('body').removeClass('zh').addClass('en');  
        }  
        jQuery.i18n.properties({//加载资浏览器语言对应的资源文件  
            name: 'strings', //资源文件名称  
            path:'/js/language/', //资源文件路径  
            language: tmp_lang,  
            cache: false,  
            mode:'map', //用Map的方式使用资源文件中的值  
            callback: function() {//加载成功后设置显示内容  
                for(var i in $.i18n.map){  
                    $('[data-lang="'+i+'"]').text($.i18n.map[i]);
                }
                document.title = $.i18n.map['string_title'];
                		
            }  
        });  
        self.now_lang = new_lang;  
    }  
}  
var isChange;

$(function(){
	isChange = sessionStorage.getItem("isChange");
	console.log(isChange);
	if(isChange == null){
		sessionStorage.setItem("isChange",0);
		isChange = sessionStorage.getItem("isChange");
		console.log(isChange);
	}
	
    language_pack.loadProperties(0);  
    $('#public_change_lan').click(function(){
    	if(isChange == 0){
    		isChange = 1;
    	}else{
    		isChange = 0;
    	}
    	isChange == 0 ? $('#caidan').panel('setTitle','菜单') : $('#caidan').panel('setTitle','Menu');
    	sessionStorage.setItem("isChange",isChange);
    	var new_lang;
        if(language_pack.now_lang == 0){  
            new_lang = 1;  
        }else{  
            new_lang = 0;  
        }  
        language_pack.loadProperties(new_lang); 
        
//        如果切换语言则把全部tab清除，目前未实现tab中英切换
        var tabs = $('#regin-innder').tabs('tabs');
        $(tabs).each(function(index, obj) {  
            //获取所有可关闭的选项卡  
        	var t = obj.panel('options').title
            $("#regin-innder").tabs('close', t);  
        });
    });  
}); 