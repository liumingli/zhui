<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>万能测试页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" charset="utf-8"
	href="css/style.css" />
</head>
<script type="text/javascript" language="javascript">
	function CheckFileType() {		 
		var objFileUpload = document.getElementById('file1');//FileUpload 
		var objMSG = document.getElementById('msg');//æ¾ç¤ºæç¤ºä¿¡æ¯ç¨çDIV 
		var FileName = new String(objFileUpload.value);//æä»¶å 
		var extension = new String(FileName.substring(FileName.lastIndexOf(".") + 1, FileName.length));//æä»¶æ©å±å
		extension = extension.toLowerCase();
		if (extension == "jpg" || extension == "png")//ä½ å¯ä»¥æ·»å æ©å±å 
		{
			objMSG.innerHTML="it's ok, now go to Upload!";
		} else {
			objMSG.innerHTML="oops! the file you selected not jpg or png file!";
			clearFileInput(objFileUpload);
						
		}
	}

	function clearFileInput(file){
	    var form=document.createElement('form');
	    document.body.appendChild(form);
	    //è®°ä½fileå¨æ§è¡¨åä¸­ççä½ç½®
	    var pos=file.nextSibling;
	    form.appendChild(file);
	    form.reset();
	    pos.parentNode.insertBefore(file,pos);
	    document.body.removeChild(form);
	}

	function checkBlank(){
		var objFileUpload = document.getElementById('file1');//FileUpload
		var selectedFile = new String(objFileUpload.value);//æä»¶å
					
		if(selectedFile==""){
			var objMSG = document.getElementById('msg');//æ¾ç¤ºæç¤ºä¿¡æ¯ç¨çDIV
			objMSG.innerHTML="No file selected!";
			return false;			
		}else{
			return true;
		}

							
	} 

	function clearMsg(){
		var objMSG = document.getElementById('msg');//æ¾ç¤ºæç¤ºä¿¡æ¯ç¨çDIV
		objMSG.innerHTML="";
	}
	
</script>
<body>
	<div id="content">
		<form name="uploadForm" id="uploadForm"
			onsubmit="return checkBlank();" onreset="clearMsg();"
			action="zhuiapi" method="post"  enctype="multipart/form-data">
			<ul>
				<li><input type="hidden" name="method" value="shareToTencent" />
				</li>
		<li>userId <input type="text" name="userId" value="1964124547"></li>
			<li>content <input type="text" name="content" value="测试测试"></li>
			<li>openId <input type="text" name="openId" value="5BD7FC4A8E557D8A6F985AEFBC8B67A1"></li>
			<li>openKey <input type="text" name="openKey" value="80B2F8809E13DA2222CAD2A87063A4D4 "></li>
			<li>ip <input type="text" name="ip" value="119.57.20.234"></li>
			
				<li>图片<input type="file" name="file1" id="file1" size="30"
					onchange="CheckFileType();" />
				</li>
				<li><div id="msg"></div>
				<li><input type="submit" name="submit" value="上传"> <input
					type="reset" name="reset" value="重置"><br />
				</li>
			</ul>
		</form>
	</div>
</html>