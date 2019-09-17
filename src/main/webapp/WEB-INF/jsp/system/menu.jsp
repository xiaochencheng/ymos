<%@ include file="../common/common.jsp"%>
<%@ page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/cc-html.tld" prefix="cc" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style>
        .layui-form-select{width: 290px;}
        .layui-edge{left: 267px;}
        .layui-show li{height: 21px;}
        i.layui-icon.layui-tree-spread{height: 21px;}
        #menuTree a{height: 21px;}
    </style>

    <script type="text/javascript">

        $().ready(function() {
            getTree();
            $.getJSON('${pageContext.request.contextPath }/menu/queryFatherMenu', function(result){
                if(result){
                    $.each(result,function(index,obj){
                        $('#fatherMenu').append("<option value='"+obj.id+"'>"+obj.name+"</option>");
                    })
                    layui.use('form', function(){
                        var form = layui.form;
                        form.render();
                    })
                }
            })

        });


        function getTree(){

            layui.use(['tree', 'layer'], function(){
                var layer = layui.layer;
                $.getJSON('${pageContext.request.contextPath }/menu/getMenuTree', function(result){
                    if(result){
                        layui.tree({
                            elem: '#menuTree' //指定元素
                            ,nodes:result
                            ,click: function(item){ //点击节点回调
                                operateMenu(item.pid,item.id,item.name,item.seq,item.url);
                                layer.closeAll();
                            }
                        })
                    }
                })
            })

        }

        function operateMenu(pid,id,name,seq,url){
            var form='';
            layui.use('form', function(){
                form = layui.form;
            });
            var value =pid;
            $('option[value='+value+']').prop('selected','selected');
            form.render('select');
            $('#id').attr('value',id);
            $('#name').attr('value',name);
            $('#seq').attr('value',seq);
            $('#url').attr('value',url);
        }

        layui.use('form', function(){
            var form = layui.form;
            form.verify({
                menu: function(value, item){
                    if(value=='' && value.trim()==''){
                        return '父菜单不能为空';
                    }
                },
                name: function(value, item){
                    if(value=='' && value.trim()==''){
                        return '菜单名称不能为空';
                    }
                },seq: function(value, item){
                    if(value=='' && value.trim()==''){
                        return '菜单序列号不能为空';
                    }
                }
            });
            //新增菜单
            form.on('submit(addForm)', function (data) {
                create();
                return false;
            });

            //修改菜单
            form.on('submit(updateForm)', function (data) {
                update();
                return false;
            })
        })

        function create(){
            var pid=$('#fatherMenu').val();
            $.post('${pageContext.request.contextPath }/menu/create'
                ,{"pid":pid,"name":$('#name').val()
                    ,"seq":$('#seq').val(),"url":$('#url').val()}
                ,function(reply){
                    if(reply.flag){
                        layer.msg("新增成功!",{time:500},function(){
                            window.location.reload();
                        })
                    }else{
                        layer.msg("新增失败!");
                        getTree();
                    }
                }
            );
        }

        function update(){
            var pid=$('#fatherMenu').val();
            if($('#id').val()=="" || $('#id').val()==0 ){
                layer.msg("请选择新增保存!");
                return false;
            }
            $.post('${pageContext.request.contextPath }/menu/modify'
                ,{"pid":pid,"id":$('#id').val(),"name":$('#name').val()
                    ,"seq":$('#seq').val(),"url":$('#url').val()}
                ,function(reply){
                    if(reply.flag){
                        //layer.msg("修改成功!");
                        layer.msg("修改成功!",{time:500},function(){
                            window.location.reload();
                        })
                    }else{
                        layer.msg("修改失败!");
                        getTree();
                    }
                }
            );
        }
        //删除菜单
        function del(){
            if($('#id').val()==''){
                layer.msg('请选择要删除的菜单!')
                return;
            }
            layer.confirm('你确认删除此菜单吗?', {icon: 3, title:'信息'}, function(index){
                $.getJSON('${pageContext.request.contextPath }/menu/delete/'+$('#id').val(),function(reply){
                        if(reply.flag){
                            layer.msg("删除成功!",{time:500},function(){
                                window.location.reload();
                            })
                        }else{
                            layer.msg(reply.prompt);
                        }
                    }
                )
                layer.close(index);
            });
        }
    </script>
</head>


<body>
<div id="menu">
    <fieldset class="layui-elem-field" style="background:#fff;border:1px solid #C2D1D8;height: 40%;width: 30%;float: left;overflow-x:auto;">
        <legend>菜单列表</legend>
        <div style="margin: 3px">
            <ul id="menuTree"></ul>
        </div>
    </fieldset>
</div>

<div>
    <fieldset class="layui-elem-field" style="background:#fff;border:1px solid #C2D1D8;>
            <legend>菜单维护</legend>
            <div class="layui-field-box">
    <form class="layui-form layui-form-pane" id="menuForm">
        <input type="text" value="${form.id }" name="id" style="display: none">
        <div class="layui-form-item">
            <label class="layui-form-label">父菜单名称:</label>
            <div class="layui-input-block">
                <label id="pname" class="col-form-label col-form-label-lg" style="display: none"></label>
                <input type="hidden" id="pid" name="pid"  />
                <select style="width: 260px" id="fatherMenu" name="menu" lay-verify="menu" lay-search>
                    <option value="">----请选择父菜单名称----</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称:</label>
            <div class="layui-input-inline">
                <input type="text"  name="name" id="name" placeholder="请输入菜单名称"  class="layui-input create-input" lay-verify="name">
            </div>
        </div>
        <input type="hidden" id="id" name="id" />
        <div class="layui-form-item">
            <label class="layui-form-label">菜单序列号:</label>
            <div class="layui-input-inline">
                <input type="text"  name="seq" id="seq"  placeholder="请输入菜单序号"  class="layui-input create-input" lay-verify="seq">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">URL:</label>
            <div class="layui-input-inline">
                <input type="text" name="url" id="url" placeholder="请输入URL"  class="layui-input create-input">
            </div>
        </div>
        <div class="layui-form-item" style="margin-left: 105px">
            <button class="layui-btn layui-btn-sm layui-btn-normal" lay-submit="" lay-filter="addForm">
                <i class="layui-icon">&#xe654;</i>新增
            </button>
            <button class="layui-btn layui-btn-sm "  lay-submit="" lay-filter="updateForm">
                <i class="layui-icon">&#xe642;</i>修改
            </button>
            <button class="layui-btn layui-btn-sm layui-btn-danger" type="button" onclick="del()">
                <i class="layui-icon">&#xe640;</i>删除
            </button>
        </div>
    </form>
</div>
</fieldset>
</div>
<script>
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