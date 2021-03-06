<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>登录（请使用chrome,firefox浏览器）</title>
<style type="text/css">
	
body {
	height:738px;
	width:780px;
	background: url("imgs/first.png") no-repeat ;
 	background-position: top; 
}

#content{
	padding-top:300px;
	padding-left:400px;
}

#line{
	width:200px;
	padding:5px;
	text-align:center;
}

</style>

<script type="text/javascript" src="js/jquery.js" charset="utf-8"></script>

<script type="text/javascript">
window.onload = function(){

	if(navigator.userAgent.indexOf("MSIE")>0){
	    alert('系统检测出你使用了 ie 或 360 浏览器,请改用chrom（或firefox）等再行访问本站，谢谢合作！');
	    document.execCommand("stop");
	    //注:把location前面的//号去掉后,把后面的网址改成你的网站,弹出窗口后就会跳到你指定的网址.
	    location.href="http://www.produ.cn";
	}
	
     if (window != top) top.location.href = location.href;
};
</script>

<script type="text/javascript">
document.onkeydown=function(event){
    var e = event || window.event || arguments.callee.caller.arguments[0];
     if(e && e.keyCode==13){ // enter
    	 loginSystem();
    }
};

function checkNull(){
	var account = $("#account").attr("value");
	var password =  $("#password").attr("value");
	if(account == null || password == null || account == "" || password == ""){
		return false;
	}
	return true;
}

function loginSystem(){
	var flag = checkNull();
	if(flag){
		var account = $("#account").attr("value");
		var password =  $("#password").attr("value");
		$.post('/zhui/zhuiapi', {
			'method'  : 'loginSystem',
			'account'	: account,
			'password' : password 
		}, 
		//回调函数
		function (result) {
			if(result == "true"){
				window.location.href = "index.html";
			}else{
				$('#msg').html('<font color="red" size="2">提示：账户或密码输入错误</font>');
			}
		});
	}else{
		$('#msg').html('<font color="red" size="2">提示：账户和密码不能为空</font>');
	}
}

function clearMsg(){
	$('#msg').text("");
}
	
</script>

</head>
<body>
	<div id="content">
		<form id="contact">
			<div id ="line">
				账户 <input type="text" name="account" id="account" class="input" onfocus="clearMsg()" autofocus/>
			</div>
			<div id ="line">
				密码 <input type="password" name="password" id="password" class="input" />
			</div>
			<div id ="line">
				<input type="button" value="登录" name="submit" class="button" id="submit" onclick="loginSystem()"/> 
				<input type="reset" value="重置" name="reset" class="button" id="reset" onclick="clearMsg()"/>
			</div>
			<div id ="line"><span id="msg" ></span></div>
		</form>
	</div>
</body>
</html>