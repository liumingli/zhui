<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>追你妹</title>
<meta name="google" value="notranslate" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<style type="text/css" media="screen"> 
	    html, body  { height:100%; }
	    body { margin:0; padding:0; overflow:auto; text-align:center; 
	           background-color: #ffffff; }   
	    object:focus { outline:none; }
	    #flashContent { display:none; }
	</style>
     <script type="text/javascript" src="jquery.js"></script>   
	 <script type="text/javascript" src="swfobject.js"></script>
	 
	 <script type="text/javascript">
	 
    
		function getHttpParams(name)
		 {
		      var r = new RegExp("(\\?|#|&)"+name+"=([^&#]*)(&|#|$)");
		      var m = location.href.match(r);
		      return decodeURIComponent(!m?"":m[2]);
		 }
		
	 	 window.onload = function(){
	 		 
	 		//取客户端真实IP
	    		<%
	 		 	String ip = request.getHeader("X-Forwarded-For");   
	 	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	 	            ip = request.getHeader("Proxy-Client-IP");   
	 	        }   
	 	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	 	            ip = request.getHeader("WL-Proxy-Client-IP");   
	 	        }   
	 	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	 	            ip = request.getHeader("HTTP_CLIENT_IP");   
	 	        }   
	 	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	 	            ip = request.getHeader("HTTP_X_FORWARDED_FOR");   
	 	        }   
	 	        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
	 	            ip = request.getRemoteAddr();   
	 	        }   
	 		%>
	 		
	 		var source = "<%=ip%>";
	 		 
	 		var openid = getHttpParams("openid");
			var openkey = getHttpParams("openkey");
	 	 	var pf = getHttpParams("pf");
	 	 	
	 	 	if(openid != null && openid !=""&& openkey != null && openkey !=""){
	 	 		//操作用户，正常后返回id，否则返回false
	 	 		var userId = openid.substring(0,16);
	 	 	  	createZhuinimei(userId,pf,openid,openkey,source);
	 	 	}
	 	 };
		
		function createZhuinimei(userId,pf,openid,openkey,source){
			
			var flashvars = {};
			
			flashvars.userId = userId;
			flashvars.pf = pf;
			flashvars.openId = openid;
			flashvars.openKey = openkey;
			flashvars.ip = source;
			
			var swfVersionStr = "11.1.0";
			var xiSwfUrlStr = "playerProductInstall.swf";
			var params = {};
			params.quality = "high";
			params.bgcolor = "#ffffff";
			params.allowscriptaccess = "always";
			params.allowfullscreen = "true";
			params.wmode = "opaque";
			var attributes = {};
			attributes.id = "flashContent";
			attributes.name = "flashContent";
			attributes.align = "middle";
			swfobject.embedSWF("Nimei.swf?v=1.02&date=0412", "flashContent", 760, 600, swfVersionStr, xiSwfUrlStr,
					flashvars, params, attributes);
			swfobject.createCSS("#flashContent", "display:block;text-align:left;");
		}
		
    </script>
</head>

<body>
      
    <div id="flashContent">
        <p>
            To view this page ensure that Adobe Flash Player version need flash player11...
        </p>
       
    </div>
        
</body>
</html>
