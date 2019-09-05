<%@ include file="../common/common.jsp"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<script type="text/javascript" src="${pageContext.request.contextPath }/jsandcss/layui/layui/layui-xtree.js"></script>
<style>
.layui-unselect{height: 30px;}
 #privilegeTree .layui-xtree-item{margin-top: 0px;}
 .left-fieldset{padding: 0 10px 0 10px;}
</style> 
<script type="text/javascript">
var privilegeTree='';
var roleId='';
$().ready(function() {
	getTree(); 
	//角色列表的行绑定点击事件
	$('.role-tr').click(function(){
		roleId=this.id;
		$(this).css("background-color", "coral").siblings().css("background-color", "");
		$.getJSON('${pageContext.request.contextPath }/menu/getPrivilegeTree?roleId='+roleId,function(result){
			 if(result){
				 var json=result;
					layui.use(['form'], function () {
					var form = layui.form;
					privilegeTree = new layuiXtree({
		     	    elem: 'privilegeTree'   
		          , form: form    
		          , data: json    
					});
				});
			 }
		})
	})
});
function getTree(){
	$.getJSON('${pageContext.request.contextPath }/menu/getPrivilegeTree', function(result){
		if(result){
			var json=result;
			layui.use(['form'], function () {
			var form = layui.form
			privilegeTree = new layuiXtree({
     	    elem: 'privilegeTree'   //(必填) 放置xtree的容器id，不要带#号
          , form: form     //(必填) layui 的 from
          , data: json     //(必填) json数组（数据格式在下面）
          , click: function (data){
        	   var nodes = privilegeTree.GetAllCheckBox();
        	    var arr = new Array();
        	    for (var i = 0; i < nodes.length; i++) {
        	        if (nodes[i].checked) {
        	        	arr.push(nodes[i].value);
        	        }
        	    } 
         		} 	
			});
			});
		}
	})
}



function save(){
	if(roleId==''){
		layer.msg('请选择角色!');
		return ;
	}
	var nodes = privilegeTree.GetAllCheckBox();
    var arr = new Array();
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].checked) {
        	arr.push(nodes[i].value);
        }
    }
    /*if(arr==''){
    	layer.msg('请为角色选择权限!'); 
		return ;
    }*/
    $.getJSON('${pageContext.request.contextPath }/privilege/savePrivilege/'+roleId
			,{'menu_ids':arr.join(',')}
		,function(reply){
			layer.msg(reply.flag?'保存成功!':reply.prompt);
	    })
} 	
	
</script>
</head>


<body>
<div>
<fieldset class="layui-elem-field" style="background:#fff;border:1px solid #C2D1D8;height: 360px;width:400px;float: left;">
<legend>角色列表</legend>
<div class="left-fieldset">
   <form class="layui-form" >
      <table class="layui-table" lay-size="sm">
      	<tr>
      	<th>ID</th>
		<th>角色名称</th>
      	</tr>
      	<c:forEach items="${roles}" var="obj">
       		<tr class="role-tr" id="${obj.id}">
	          <td>${obj.id}</td>
	          <td>${obj.name}</td>
	        </tr>
       </c:forEach>
      </table>
	</form>
	<div>
		<button class="layui-btn layui-btn-sm layui-btn-normal" type="button" onclick="save();">
      	 <i class="layui-icon">&#xe654;</i>保存权限
	</button>
	</div>
</div>
   </fieldset>
</div>

<div id="menu">
<fieldset class="layui-elem-field" style="background:#fff;border:1px solid #C2D1D8; height: 100%;">
<legend>权限列表</legend>
<div class="left-fieldset">
    <form class="layui-form">
      <div id="privilegeTree"></div>
	</form> 
</div>  
   </fieldset>
</div>
<script type="text/javascript">
    function IsPC() {
        var userAgentInfo = navigator.userAgent;
        var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
        var flag = true;
        for (var v = 0; v < Agents.length; v++) {
            if (userAgentInfo.indexOf(Agents[v]) > 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }
    //手机适配
    function mobile(){
        $(selectBtn).insertAfter($("#menu"));
        $('#selectBtn').on("click",function () {
            layer.open({
                type: 1,
                title: false,
                closeBtn: 0,
                area: ["100%", "100%"],
                shadeClose: true,
                skin: 'yourclass',
                content: $("#menu")
            });

            console.log($(".layui-layer-content").parent().css({"postion":"absolute","top":"30px"}))

        })
    }
    if(!IsPC()){
        mobile();
    }
</script>
</body>
</html>