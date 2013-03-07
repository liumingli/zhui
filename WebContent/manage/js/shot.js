window.onload = function(){
	$('#swfUpload').fileupload({
		add : function(e, data) {
			//因为现在只传一个文件，所以可以用files[0]来取到
			var fileName = data.files[0].name;
			var regexp = /\.(swf)$/i;
		    if (!regexp.test(fileName)) {
		    	  $('#swfInfo').show().html('<img src="imgs/no.png">');
		    }else{
		    	 $('#swfInfo').show().html('<img src="imgs/loading.gif">');
				var jqXHR = data.submit().success(
						function(result, textStatus, jqXHR) {
							if(result == "reject"){
							    $('#swfInfo').show().html('<img src="imgs/no.png">');
							}else{
								$('#swfPath').val(result);
							    $('#swfInfo').show().html('<img src="imgs/ok.png">');
							    $('#swfUpload').attr('disabled','disabled');
							}
						}).error(
						function(jqXHR, textStatus, errorThrown) {
							 $('#swfInfo').show().html('<img src="imgs/no.png">');
						}).complete(
						function(result, textStatus, jqXHR) {
						});
		    }
		}
	});

	$('#thumbnailUpload').fileupload({
		add : function(e, data) {
			var fileName = data.files[0].name;
			var regexp = /\.(png)|(jpg)|(gif)$/i;
		    if (!regexp.test(fileName)) {
		    	  $('#thumbnailInfo').show().html('<img src="imgs/no.png">');
		    }else{
		    	 $('#thumbnailInfo').show().html('<img src="imgs/loading.gif">');
				var jqXHR = data.submit().success(
						function(result, textStatus, jqXHR) {
							if(result == "reject"){
							    $('#thumbnailInfo').show().html('<img src="imgs/no.png">');
							}else{
								$('#thumbnailPath').val(result);
								$('#thumbnailInfo').show().html('<img src="imgs/ok.png">');
								$('#thumbnailUpload').attr('disabled','disabled');
							}
						}).error(
						function(jqXHR, textStatus, errorThrown) {
							$('#thumbnailInfo').show().html('<img src="imgs/no.png">');
						}).complete(
						function(result, textStatus, jqXHR) {
						});
		    }
		}
	});
	
	//检查是否已经上传了主模板，若无跳转
	 checkTemplate();
	
};


function createShot(){
	if(checkShotNull()){
		$('#load').show().html('<img src="imgs/loading.gif">');
		var name = 	$("#name").val();
		var swf = $("#swfPath").val();
		var thumbnail = $("#thumbnailPath").val();
		var frame = $('#frame').val();
		var bubble = "";
	    var radio = document.getElementsByName("radiobutton");
		var template = $("#template").val();
		for(var i=0;i<radio.length;i++)
		{
		     if(radio.item(i).checked){
		    	 bubble=radio.item(i).getAttribute("value");  
		         break;
		     }else{
		    	 continue;
		     }
		}
		var bubbleSize = $('#bubbleSize').val();
		
		$.post('/zhui/zhuiapi',{
			"method" : "saveShot",
			'name' : name,
			'swfPath' : swf,
			'thumbnailPath' : thumbnail,
			'frame' : frame,
			'bubble' : bubble,
			'bubbleSize' : bubbleSize,
			'template' : template
		},
		function(result){
			$('#load').attr("style","display:none");
			if(result == 'false'){
				$('#prompt').show().html("上传分镜头有误，请点击取消按钮重试");
			}else{
				$('#prompt').show().html('<font color="red" size="2">提示：上传分镜头成功，点击<a href="javascript:emptyForm();">继续添加</a></font>');
			}
		});
	}
}

function checkShotNull(){
	var name = 	$("#name").val();
	var swf = $("#swfPath").val();
	var thumbnail = $("#thumbnailPath").val();
	var frame =  $("#frame").val();
	var bubbleSize = $("#bubbleSize").val();
	var template = $("#template").val();
	if(name != null && name != "" && swf != null && swf != "" && frame != null && frame != ""
		&& thumbnail != null && thumbnail != "" && bubbleSize !=null && bubbleSize !=""
		&& template != null && template != "" 	){
		return true;
	}else{
		return false;
	}
}

function checkNum(){
	var val = $("#frame").val();
	if(isNaN(val)){
		 $('#frameInfo').show().html('<font color="red" size="2">*请输入数字</font>');
		 $("#frame").focus();
	}else{
		var r = /^\+?[1-9][0-9]*$/;//正整数 
		if(r.test(val)){
			 $('#frameInfo').hide();
		}else{
			 $('#frameInfo').show().html('<font color="red" size="2">*帧数必须大于0</font>');
			 $("#frame").focus();
		}
	}
}

function hasBubbleClick(){
	$('#bubbleInfo').hide();
	$("#bubbleSize").removeAttr("disabled");
	$("#bubbleSize").val("");
}

function noBubbleClick(){
	$('#bubbleInfo').hide();
	$("#bubbleSize").val(0);
	$("#bubbleSize").attr('disabled','disabled');
}

function checkBubbleSize(){
	var size = $("#bubbleSize").val();
	var s = size.indexOf("*");
	if(s==-1){
		 $('#bubbleInfo').show().html('<font color="red" size="2">如200*150</font>');
		 $("#bubbleSize").focus();
	}else{
		$('#bubbleInfo').hide();
	}
}

function emptyForm(){
	$("#name").val("");
	$("#name").focus();
	$("#swfPath").val("");
	$('#swfUpload').removeAttr('disabled');
	$("#thumbnailPath").val("");
	$('#thumbnailUpload').removeAttr('disabled');
	$("#swfInfo").hide();
	$("#thumbnailInfo").hide();
	$("#frame").val("");
	$("#frameInfo").hide();
	$("#bubbleSize").val("");
	$('#bubbleInfo').hide();
	$('#prompt').hide();
}

function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
 }


function checkTemplate(){
	var templateName = getQueryString("name");
	var templateId = getQueryString("id");
	if(templateId == null || templateId ==""){
		//alert("请先上传模板再传分镜头");
		window.parent.frames['topFrame'].document.getElementById("templateUpload").style.color = "#ff9966";
		window.parent.frames['topFrame'].document.getElementById("shotUpload").style.color = "#eee";
		window.parent.frames['mainFrame'].location.href = "templateUpload.html";
	}else{
		$('#template').attr("value",templateId);
		$("#caption").html("<b>分镜头上传</b> (模板名称："+templateName+")");
	}
}
