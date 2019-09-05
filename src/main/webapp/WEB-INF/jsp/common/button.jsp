<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="button_form" method="post" style="display: none;">
    <c:forEach items="${paginator.query.qstrs }" var="qstrs">
        <input name="${qstrs.name }" value="${qstrs.value }" type="hidden"/>
    </c:forEach>
</form>
<div style="float: left;margin-top: 7px;margin-left: 12px;">
    <c:if test="${param.create !=null }">
        <button class="layui-btn layui-btn-normal layui-btn-sm dw-dailog" onclick="javascript:location.href='${param.create}'">
            <i class="layui-icon">&#xe654;</i>新增
        </button>
    </c:if>
    <c:if test="${param.modify !=null }">
        <button class="layui-btn layui-btn-sm dw-dailog" onclick="javascript:doWithSigleSelect('${param.modify}')">
            <i class="layui-icon">&#xe642;</i>审核
        </button>
    </c:if>
    <c:if test="${param.review !=null }">
        <button class="layui-btn layui-btn-sm dw-dailog" onclick="javascript:doWithSigleSelect1('${param.review}')">
            <i class="layui-icon">&#xe642;</i>编辑
        </button>
    </c:if>

    <c:if test="${param.delete !=null }">
        <button class="layui-btn layui-btn-sm layui-btn-danger dw-batch-delete" onclick="javascript:doWithMutilSelect('${param.delete}','${param.deleteMsg}');">
            <i class="layui-icon">&#xe640;</i>删除
        </button>
    </c:if>
</div>
<script>
    function getIds(){
        var ids=document.getElementsByName("ids");
        var id="";
        for(i=0;i<ids.length;i++){
            if(ids[i].checked){
                id+=ids[i].value+'-';
            }
        }
        if(id!=""){
            id=id.substring(0,id.length-1);
        }
        return id;
    }
    function getId(){
        var ids=document.getElementsByName("ids");
        var id="-1";
        for(i=0;i<ids.length;i++){
            if(ids[i].checked){
                if(id=='-1'){
                    id=ids[i].value;
                }else{
                    return '-2';
                }
            }
        }

        return id;
    }
    function sigleSelect(){
        var ids=getId();
        if(ids=='-1'){
            layer.msg('请勾选要操作的行！');
            return;
        }else if(ids=='-2'){
            layer.msg('只能选择一行操作！');
            return;
        }
        return ids;
    }
    function mutilSelect(){
        var ids=getIds();
        if(ids==""){
            layer.msg('请勾选要操作的行！');
            return;
        }
        return ids;
    }
    function setQueryString(){
        var queryString="";
        $.each($("#button_form input"), function(i,param){
            if(param.type!="submit" && param.value!="")
                queryString+=param.name+"="+param.value+"&";
        });
        $.each($("#button_form select"), function(i,param){
            if(param.value!="")
                queryString+=param.name+"="+param.value+"&";
        });
        queryString+="_page="+$("#paginator .active").html();
        $('<input />').attr('type', 'hidden')
            .attr('name', "_queryStr")
            .attr('value', encodeURI(queryString))
            .appendTo('#button_form');
    }
    var button_form=document.getElementById("button_form");
    function doWithSigleSelect(path){
        var id=sigleSelect();
        if(id){
            button_form.action=path+"/"+id;
            setQueryString();
            button_form.submit();
        }
    }
    function doWithSigleSelect1(path){
        var id=sigleSelect();
        if(id){
            button_form.action=path+"/"+id;
            setQueryString();
            button_form.submit();
        }
    }

    function doWithMutilSelect(path,msg){
        var ids=mutilSelect();
        if(ids){
            layer.confirm(msg, {icon: 3, title:'信息'}, function(index){
                button_form.action=path+"/"+ids;
                //button_form.action="/user/delete?ids="+ids
                setQueryString();
                button_form.submit();
                layer.close(index);
            });
        }
    }
</script>