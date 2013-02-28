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
				$('#prompt').show().html("上传分镜头有误，请重试");
			}else{
				//$('#prompt').show().html('<font color="red" size="2">提示：上传结局成功，点击<a href="javascript:addEnding'+param+';">继续添加</a></font>');
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
	alert(bubbleSize);
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
	}else{
		var r = /^\+?[1-9][0-9]*$/;//正整数 
		if(r.test(val)){
			 $('#frameInfo').hide();
		}else{
			 $('#frameInfo').show().html('<font color="red" size="2">*帧数必须大于0</font>');
		}
	}
}

function cancelFrame(){
	$("#frame").val("");
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
	$("#frame").val("");
	$("#frameInfo").hide();
	$("#bubbleSize").val("");
	$('#bubbleInfo').hide();
	$('#prompt').hide();
}

function checkTemplate(){
	var template = $('#template').val();
	if(template == null || template ==""){
		alert("请先上传模板再传分镜头");
	}
}
