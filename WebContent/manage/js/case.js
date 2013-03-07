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


function createCase(){
	if(checkCaseNull()){
		$('#load').show().html('<img src="imgs/loading.gif">');
		var name = 	$("#name").val();
		var swf = $("#swfPath").val();
		var thumbnail = $("#thumbnailPath").val();
		var description =  $("#description").val();
		
		$.post('/zhui/zhuiapi',{
			"method" : "saveCase",
			'name' : name,
			'swfPath' : swf,
			'thumbnailPath' : thumbnail,
			'description' : description
		},
		function(result){
			$('#load').attr("style","display:none");
			if(result == 'false'){
				$('#prompt').show().html("上传案例有误，请点击取消按钮重试");
			}else{
				$('#prompt').show().html('<font color="red" size="2">提示：上传案例成功，现在<a href="javascript:emptyForm();">继续上传</a></font>');
			}
		});
	}
}

function checkCaseNull(){
	var name = 	$("#name").val();
	var swf = $("#swfPath").val();
	var thumbnail = $("#thumbnailPath").val();
	var description =  $("#description").val();
	if(name != null && name != "" && swf != null && swf != "" 
		&& thumbnail != null && thumbnail != "" && description != null && description != ""){
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
	$("#description").val("");
	$('#prompt').hide();
}


//------------------------------------管理

//管理案例
function getAllCase(){
	$.post("/zhui/zhuiapi",{
		'method' : 'getCaseCount'
	},
	function (result) {
		if(result > 0){
			var total = 0;
			if(parseInt(result%10) == 0){
				total = parseInt(result / 10);
			}else{
				 total = parseInt(result / 10)+1;
			}
			
			$('#total').html(total);
			$('#current').html(1);
			
			getCaseByPage(1);
		}else{
			$('#total').html(0);
			$('#current').html(0);
			$('#caseList').children().remove();
		}
		
	});
}

function generateCaseTr(key){
	$('<tr></tr>').appendTo($('#caseList'))
	.attr("id","line"+key);
}

//生成缩略图
function generateCaseTd(thumbnial,swf,id,key){
	var tr = document.getElementById("line"+key);
	var para = document.createElement("td");
	var a = document.createElement("a");
	para.appendChild(a);
	var img = document.createElement("img");
	var local = '/zhui/zhuiapi?method=getAssetFile&relativePath=';
	img.setAttribute("src",local+thumbnial);
	img.setAttribute("height",30);
	a.appendChild(img);
	//getAssetFile
	a.setAttribute("href",local+swf);
	a.setAttribute("target", "_blank");
	tr.appendChild(para);
}

function generateCaseOperate(id,key){
	
	$('<td></td>').appendTo($('#line'+key))
	.append($('<a></a>')
		.append($('<img>').attr("src","imgs/del.png"))
		.attr("href","javascript:deleteCaseById('"+key+"');"))
	.append($('<input type="hidden"></input>')
		.attr("id","caseId"+key)
		.attr("value",id));
}

//生成各td
function generateTd(txt,key){
	$('<td></td>').appendTo($('#line'+key))
	.text(txt);
}

function confirmDel(){
  if(confirm("确定要删除吗？删除将不能恢复！"))
 		 return true;
  else
 		 return false;
}

function deleteCaseById(key){
	if(confirmDel()){
		var caseId=$("#caseId"+key).attr("value");
		$.post('/zhui/zhuiapi', {
			'method'  : 'deleteCase',
			'caseId' : caseId
		}, 
		//回调函数
		function (result) {
			if(result.trim() == 'false'){
				 alert("操作有误，请重试！");
		     }
		});
	    //操作后刷新列表
		var num = $('#current').text();
		getCaseByPage(num);
	}
}

function getCaseByPage(pageNum){
	$('#current').html(pageNum);
	$('#caseList').children().remove();
	$.post("/zhui/zhuiapi",{
		'method' : 'getCase',
		'pageNum' : pageNum,
		'pageSize' : "10"
	},
	function (result) {
		if(result.length >0){
			for(key in result){
				generateCaseTr(key);
				
				generateTd(parseInt(key)+1,key);
				
				generateCaseTd(result[key].thumbnail,result[key].swf,result[key].id,key);
				
				generateTd(result[key].description,key);
		
				generateTd(result[key].deliverTime,key);
				
				generateCaseOperate(result[key].id,key);
			}
		}
	},"json");
}


function getFirstpage(){
	var num = $('#current').text();
	//最后一页，如果当前是最后一页
	if(parseInt(num) == 1){
		return;
	}else{
		getCaseByPage(1);
	}
}


function getPrevpageImg(){
	var num = $('#current').text();
	var pageNum = parseInt(num) - 1;
	//前一页，如果当时是第一页
	if(parseInt(num) == 1){
		return;
	}else{
		getCaseByPage(pageNum);
	}
}


function getNextpage(){
	var sum = $('#total').text();
	var num = $('#current').text();
	var pageNum = parseInt(num) + 1;
	//后一页，如果当前是最后一页
	if(sum == num){
		return;
	}else{
		getCaseByPage(pageNum);
	}

}

function getLastpage(){
	var sum = $('#total').text();
	var num = $('#current').text();
	//最后一页，如果当前是最后一页
	if( sum == num){
		return;
	}else{
		getCaseByPage(sum);
	}
}


	



