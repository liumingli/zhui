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


function createTemplate(){
	if(checkTemplateNull()){
		$('#load').show().html('<img src="imgs/loading.gif">');
		var name = 	$("#name").val();
		var swf = $("#swfPath").val();
		var thumbnail = $("#thumbnailPath").val();
		var type =  $("#type").val();
		
		$.post('/zhui/zhuiapi',{
			"method" : "saveTemplate",
			'name' : name,
			'swfPath' : swf,
			'thumbnailPath' : thumbnail,
			'type' : type
		},
		function(result){
			$('#load').attr("style","display:none");
			if(result == 'false'){
				$('#prompt').show().html("上传模板有误，请点击取消按钮重试");
			}else{
				var param ="('"+name+"','"+result+"')";
				$('#prompt').show().html('<font color="red" size="2">提示：上传模板成功，现在去<a href="javascript:addShotPanel'+param+';">添加分镜头</a></font>');
			}
		});
	}
}

function checkTemplateNull(){
	var name = 	$("#name").val();
	var swf = $("#swfPath").val();
	var thumbnail = $("#thumbnailPath").val();
	var type =  $("#type").val();
	if(name != null && name != "" && swf != null && swf != "" 
		&& thumbnail != null && thumbnail != "" && type != null && type != ""){
		return true;
	}else{
		return false;
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
	$('#prompt').hide();
}

function addShotPanel(nameParam,templateParam){
	window.parent.frames['topFrame'].document.getElementById("shotUpload").style.color = "#ff9966";
	window.parent.frames['topFrame'].document.getElementById("templateUpload").style.color = "#eee";
	staticVal="shotUpload";
	window.parent.frames['mainFrame'].location.href = "shotUpload.html?name="+nameParam+"&id="+templateParam;
}


