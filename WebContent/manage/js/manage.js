//打开模板页
function uploadTemplate(){
	window.parent.frames['topFrame'].document.getElementById("templateUpload").style.color = "#ff9966";
	window.parent.frames['topFrame'].document.getElementById("manage").style.color = "#eee";
	staticVal = "templateUpload";
	window.parent.frames['mainFrame'].location.href = "templateUpload.html";
}

//打开上传新分镜头页面
function uploadShot(){
//	window.open("assetsUpload.html", 'new','');
    var id = $("#template").val();
    var name = $("#parent").val();
    window.parent.frames['topFrame'].document.getElementById("shotUpload").style.color = "#ff9966";
	window.parent.frames['topFrame'].document.getElementById("manage").style.color = "#eee";
	staticVal = "shotUpload";
	window.location.href = encodeURI("shotUpload.html?id="+id+"&name="+name);
}

//修改引入的css文件
function changeCss() {
	 var o = document.getElementById('funsion');
	 o.setAttribute('href', 'css/style.css');
}

function getTemplate(){
	$('#template').attr("value","");
	$.post('/zhui/zhuiapi', {
		'method'  : 'getTemplateCount',
		'type' : 'all'
	}, 
	function (result) {
		if(result > 0){
			var total = 0;
			if(parseInt(result%12) == 0){
				total = parseInt(result / 12);
			}else{
				 total = parseInt(result / 12)+1;
			}
			$('#total').html(total);
			$('#current').html(1);
			
			getTemplateByPage(1);
		
		}else{
			$('#total').html(0);
			$('#current').html(0);
			$('#templateList').children().remove();
		}
	});
}

function getTemplateByPage(pageNum){

	$.post('/zhui/zhuiapi', {
		'method'  : 'getTemplate',
		'pageSize' : 12,
		'pageNum' : pageNum
	}, 
	function (result) {
		$('#current').html(pageNum);
		generateTemplate(result);
		
	},"json");
}

function clearTable(){
	for(var j=0;j<12;j++){
		for(var i=1;i<4;i++){
			var num = Math.ceil((j+1)/4);
			if(num == i){
				$('#ele'+String(i)+String(j)).children().remove();
			}
		}
	}
}


function generateTemplate(result){
	//清空列表内容
	clearTable();
	
	for(key in result){
		var id= result[key].id;
		var thumbnail = result[key].thumbnail;
		var name = result[key].name;
		var swf = result[key].swf;
//		var frame = result[key].frame;
		var time = result[key].createTime;
		//行数 现在显示12个，3行4列
		var num = Math.ceil((parseInt(key)+1)/4);
		//generateTr(num);
		generateTemplateTd(id,name,thumbnail,swf,num,time,key);
	}
}

//function generateTr(num){
//	$('<tr></tr>').appendTo($('#yonkomaList'))
//	.attr("id","line"+num);
//}

//生成主动画
function generateTemplateTd(id,name,thumbnail,swf,num,time,key){
	
	var td = document.getElementById("ele"+num+key);
	var a = document.createElement("a");
	td.appendChild(a);
	var img = document.createElement("img");
	var local = '/zhui/zhuiapi?method=getThumbnail&relativePath=';
	img.setAttribute("src",local+thumbnail);
	img.setAttribute("title", "模板名称："+name);
	a.appendChild(img);
	var path =  '/zhui/zhuiapi?method=getAssetFile&relativePath=';
	a.setAttribute("href", path+swf);
	a.setAttribute("target", "_blank");
	
	//生成编辑、删除和查看结局三个按钮
	generateTemplateEditTd(id,name,time,td);
}


function generateTemplateEditTd(id,name,time,td){
	var div = document.createElement("div");
	td.appendChild(div);
	
	//加上上传时间
	var timeSpan = document.createElement("span");
	timeSpan.setAttribute("style","padding-right:5px;");
	timeSpan.innerHTML = time.substring(0,10);
	div.appendChild(timeSpan);
	
	var aCheck = document.createElement("a");
	var imgCheck = document.createElement("img");
	imgCheck.setAttribute("src","imgs/eye.png");
	imgCheck.setAttribute("title","查看分镜头");
	aCheck.appendChild(imgCheck);
	aCheck.setAttribute("href","javascript:getShot('"+id+"','"+name+"')");
	div.appendChild(aCheck);
	
	var aDel = document.createElement("a");
	var imgDel = document.createElement("img");
	imgDel.setAttribute("src", "imgs/del.png");
	imgDel.setAttribute("title", "删除");
	aDel.appendChild(imgDel);
	aDel.setAttribute("href", "javascript:delTemplate('"+id+"')");
	div.appendChild(aDel);
}


function confirmDel(){
    if(confirm("确定要删除吗？删除将不能恢复！"))
   		 return true;
    else
   		 return false;
}


function delTemplate(id){
	if(confirmDel()){
		$.post('/zhui/zhuiapi', {
			'method'  : 'deleteTemplate',
			'templateId' : id
		}, 
		//回调函数
		function (result) {
			if(result.trim() == 'false'){
				 alert("操作有误，请重试！");
		     }
		    //操作后刷新列表
			var num = $('#current').text();
			getTemplateByPage(num);
		});
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
			 $("#frameInfo").focus();
		}
	}
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


function checkNull(){
	 $('#prompt').show().html('');
	var val = $("#frame").val();
	var r = /^\+?[1-9][0-9]*$/;//正整数 
	var size = $("#bubbleSize").val();
	var s = size.indexOf("*");
	if((s != -1 || size == "0")&&  r.test(val)){
		return true;
	}else{
		$('#prompt').show().html('<font color="red" size="2">提示：</font>');
		var size = $("#bubbleSize").val();
		if(size != "0"){
			var s = size.indexOf("*");
			if(s==-1){
				$("#bubbleSize").focus();
				$('#prompt').append('<font color="red" size="2">气泡格式如200*150</font><br>');
			}
		}
		if(!r.test(val)){
			 $('#prompt').append('<font color="red" size="2">帧数需为正整数</font><br>');
		}
		return false;
	}
}

function getShot(id,name){
		$("#caption").html('<b>分镜头素材列表</b> (所属模板：'+name+')'+
				'<div id="banner"><input type="button" value="添加分镜头" onclick="uploadShot();">'+
				'<input type="button" value="返回模板" onclick="window.location.reload();"></div>');
		//主模板的id
		$('#template').attr("value",id);
		$('#parent').attr("value",name);
		
		getShotByTemplateAndPage(1,id);
}


function getShotByTemplateAndPage(pageNum,id){
	$.post('/zhui/zhuiapi', {
		'method'  : 'getShotByTemplate',
		'templateId' : id
	}, 
	function (result) {
		$('#current').html(pageNum);
		generateShot(result);
	},"json");
}

function refactorTable(){
	//修改引入的css文件
	changeCss();
	//清除table内容
	$('#templateTable').children().remove();
	
	//重新构造thead
	$('#templateTable').append("<thead><tr><td>序号</td><td>缩略图</td><td>帧数</td>" +
	"<td>气泡大小</td><td>上传时间</td><td>编辑</td><td>删除</td></tr></thead>");
	//重新构造tbody
	$('#templateTable').append("<tbody id='templateList'></tbody>");
	
	//清除分页
	$('#page').attr("style","display: none");
}	

function generateShot(result){
	//clearTable();
	refactorTable();
	for(key in result){
		//generateShotTd(id,name,thumbnail,swf,frame,bubbleSize,num,key);
		generateTr(key);
		
		generateTd(parseInt(key)+1,key);
		
		generateImgTd(result[key].thumbnail,result[key].swf,key);
		
		generateTd(result[key].frame,key);

		generateTd(result[key].bubbleSize,key);
		
		generateTd(result[key].createTime,key);
		
		generateEditOperate(result[key].id,result[key].bubbleSize,result[key].frame,key);
		
		generateDelOperate(result[key].id,key);
	}
}

function generateTr(key){
	$('<tr></tr>').appendTo($('#templateList'))
	.attr("id","line"+key);
}

//生成各td
function generateTd(txt,key){
	$('<td></td>').appendTo($('#line'+key))
	.text(txt);
}

function generateEditOperate(id,bubbleSize,frame,key){
	$('<td></td>').appendTo($('#line'+key))
	.append($('<a></a>')
		.append($('<img>').attr("src","imgs/edit.png"))
		.attr("href","javascript:editShot('"+id+"','"+bubbleSize+"','"+frame+"');"));
}

function generateDelOperate(id,key){
	$('<td></td>').appendTo($('#line'+key))
	.append($('<a></a>')
		.append($('<img>').attr("src","imgs/del.png"))
		.attr("href","javascript:delShot('"+id+"');"));
}

function generateImgTd(thumbnail,swf,key){
	var tr = document.getElementById("line"+key);
	var para = document.createElement("td");
	var a = document.createElement("a");
	para.appendChild(a);
	var img = document.createElement("img");
	var local = '/zhui/zhuiapi?method=getAssetFile&relativePath=';
	img.setAttribute("src",local+thumbnail);
	img.setAttribute("height",30);
	a.appendChild(img);
	//getAssetFile
	a.setAttribute("href",local+swf);
	a.setAttribute("target", "_blank");
	tr.appendChild(para);
}

function generateShotTd(id,name,thumbnail,swf,frame,bubbleSize,num,key){
	var td = document.getElementById("ele"+num+key);
	var a = document.createElement("a");
	td.appendChild(a);
	var img = document.createElement("img");
	var local = '/zhui/zhuiapi?method=getThumbnail&relativePath=';
	img.setAttribute("src",local+thumbnail);
	img.setAttribute("title", "分镜头名称："+name);
	a.appendChild(img);
	var path =  '/zhui/zhuiapi?method=getAssetFile&relativePath=';
	a.setAttribute("href", path+swf);
	a.setAttribute("target", "_blank");
	
	generateShotOptTd(id,frame,bubbleSize,num,td);
}

//生成结局动画
function generateShotOptTd(id,frame,bubbleSize,num,td){
	
	var div = document.createElement("div");
	td.appendChild(div);
	
	//加上帧数
	var frameSpan = document.createElement("span");
	frameSpan.setAttribute("style","padding-right:5px;");
	frameSpan.innerHTML = "帧数："+frame;
	div.appendChild(frameSpan);
	
	var aEdit = document.createElement("a");
	var imgEdit = document.createElement("img");
	imgEdit.setAttribute("src", "imgs/edit.png");
	imgEdit.setAttribute("title", "编辑");
	aEdit.appendChild(imgEdit);
	aEdit.setAttribute("href","javascript:editShot('"+id+"','"+bubbleSize+"','"+frame+"')");
	div.appendChild(aEdit);
	
	var aDel = document.createElement("a");
	var imgDel = document.createElement("img");
	imgDel.setAttribute("src", "imgs/del.png");
	imgDel.setAttribute("title", "删除");
	aDel.appendChild(imgDel);
	aDel.setAttribute("href", "javascript:delShot('"+id+"')");
	div.appendChild(aDel);
}

function editShot(id,bubbleSize,frame){
	centerPopup();
	loadPopup();
	$("#shot").val(id);
	$("#bubbleSize").val(bubbleSize);
//	if(bubbleSize == "0"){
//		$("#bubbleSize").attr('disabled','disabled');
//	}else{
//		$("#bubbleSize").removeAttr('disabled');
//	}
	$("#frame").val(frame);
	$("#prompt").hide();
	$("#bubbleInfo").hide();
	$("#frameInfo").hide();
}

function delShot(id){
	if(confirmDel()){
		$.post('/zhui/zhuiapi', {
			'method'  : 'deleteShot',
			'shotId' : id
		}, 
		//回调函数
		function (result) {
			if(result.trim() == 'false'){
				 alert("操作有误，请重试！");
		     }
		    //操作后刷新列表
			var num = $('#current').text();
			var template = $("#template").val();
			getShotByTemplateAndPage(num,template);
		});
	}
}


function updateShot(){
	if(checkNull()){
		var id = $("#shot").val();
		var bubbleSize = $("#bubbleSize").val();
		var frame= $("#frame").val();
		$.post('/zhui/zhuiapi', {
			'method'  : 'updateShot',
			'shotId' : id,
			'bubbleSize' : bubbleSize,
			'frame' : frame
		}, 
		//回调函数
		function (result) {
			if(result.trim() == 'false'){
				 alert("操作有误，请重试！");
		     }
		    //操作后刷新列表
			disablePopup();
			var num = $('#current').text();
			var template = $("#template").val();
			getShotByTemplateAndPage(num,template);
		});
	}
}


function getPrevpage(){
	var num = $('#current').text();
	var pageNum = parseInt(num) - 1;
	//前一页，如果当时是第一页
	if(parseInt(num) <= 1){
		return;
	}else{
		getYonkoma(pageNum);
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
		getYonkoma(pageNum);
	}
}

function getYonkoma(pageNum){
	var parent = $("#template").val();
	if(parent != null && parent != ""){
		getShotByTemplateAndPage(pageNum,parent);
	}else{
		getTemplateByPage(pageNum);
	}
}
