layui.config({
	base : "js/"
}).use(['form','layer'],function(){
	var form = layui.form(),
		layer = parent.layer === undefined ? layui.layer : parent.layer,
		$ = layui.jquery;
	//video背景
	$(window).resize(function(){
		if($(".video-player").width() > $(window).width()){
			$(".video-player").css({"height":$(window).height(),"width":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}else{
			$(".video-player").css({"width":$(window).width(),"height":"auto","left":-($(".video-player").width()-$(window).width())/2});
		}
	}).resize();
	
	//登录按钮事件
	form.on("submit(login)",function(data){
		var paramObj =data.field;
		$.ajax({
			//几个参数需要注意一下
			type: "POST",//方法类型
			dataType: "json",//预期服务器返回的数据类型
			url: "/auth/login" ,//url
			data:{
				username :paramObj.username,
				password :paramObj.password
			},
			success: function (result) {

				if(result.flag =="1"){
					window.location.href = "/guide/index";
				}else{

					layer.msg(result.error, {icon: 5});
				}
			},
			error : function() {

				layer.msg("服务器出错了～～", {icon: 5});
			}
		});
		return false;
	})
})
