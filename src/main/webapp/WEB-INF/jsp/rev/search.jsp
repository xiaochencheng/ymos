<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="layui-form"
      action="${pageContext.request.contextPath }/rev/list"
      method="post">

    <div class="layui-inline">
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">产品中文名称:</div>
            <div class="layui-input-inline search-width">
               <input type="text"  class="layui-input" id="pro_ch_name" name="pro_ch_name" value="${param.pro_ch_name}" placeholder="请输入产品中文名">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">产品英文名称:</div>
            <div class="layui-input-inline search-width">
               <input type="text"  class="layui-input" id="pro_en_name" name="pro_en_name" value="${param.pro_en_name}" placeholder="请输入产品英文名">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">SPU:</div>
            <div class="layui-input-inline search-width">
                <input type="text"  class="layui-input" id="spu" name="spu" value="${param.spu}" placeholder="请输入SPU">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">创建人:</div>
            <div class="layui-input-inline search-width">
                <input type="text"  class="layui-input" id="creator" name="creator" value="${param.creator}" placeholder="请输入创建人">
            </div>
        </div>


        <div class="layui-inline">
            <div class="search-name" id="dateDiv">日期:</div>
            <div class="layui-input-inline" style="width: 120px">
                <input type="text" class="layui-input" id="dateTime" name="dateTime"
                       value="${param.dateTime}" placeholder="请选择时间">
            </div>
        </div>
        <div class="layui-inline">
            -
        </div>
        <div class="layui-input-inline" style="width: 120px">
            <input type="text" class="layui-input" id="endTime" name="endTime"
                   value="${param.endTime}" placeholder="请选择结束时间">
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 40px">状态:</div>
            <div class="layui-input-inline">
                <select name="status" id="status" lay-search>
                    <option value="">请选择状态</option>
                    <option value="1">已通过</option>
                    <option value="2">不通过</option>
                    <option value="3">待审核</option>
                </select>
            </div>
        </div>

        <button class="layui-btn layui-btn-normal layui-btn-sm" type="submit">
            <i class="layui-icon">&#xe615;</i>查询
        </button>
        <button class="layui-btn  layui-btn-sm" id="export" type="button">导出Excel</button>
    </div>
</form>
<script>
    var status="${param.status}";
    $('#status').find("option[value='"+status+"']").prop("selected","selected");
    var size = '${param.size}';
    var creator='${param.creator}';
    var pro_ch_name = '${param.pro_ch_name}';
    var pro_en_name = '${param.pro_en_name}';
    var spu = '${param.spu}';
    var dateTime= '${param.dateTime}';
    var endTime= '${param.endTime}';
    $('#size').find("option[value='" + size + "']").prop("selected", true);

    $('#export').click(function () {
        var title = document.title;
        var pro_ch_name = $('#pro_ch_name').val();
        var pro_en_name = $('#pro_en_name').val();
        var dateTime = $('#dateTime').val();
        var endTime = $('#endTime').val();
        location.href = "${pageContext.request.contextPath}/rev/exportExcel/?title=" + title
            + "&pro_ch_name=" + pro_ch_name  +"&pro_en_name="+pro_en_name+ "&spu="+spu+"&dateTime=" + dateTime +"&endTime="+endTime+"&creator="+creator;
    })


    layui.use(['form', 'laydate'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
        form.render();
        laydate.render({
            elem: '#dateTime',
            /* ready和change函数是为了实现选择年月时不用点确定直接关闭  */
            ready: function (date) { // 控件在打开时触发，回调返回一个参数：初始的日期时间对象
                initYear = date.year;
            },
            change: function (value, date, endDate) { // 年月日时间被切换时都会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
                var selectYear = date.year;
                var differ = selectYear - initYear;
                if (differ == 0) {
                    if ($(".layui-laydate").length) {
                        $("#dateTime").val(value);
                        $(".layui-laydate").remove();
                    }
                }
                initYear1 = selectYear;
            }
        });
         laydate.render({
             elem: "#endTime",
             ready: function (date) {
                 initYear = date.year;
             },
             change: function (value, date, endDate) {
                 var selectYear = date.year;
                 var differ = selectYear - initYear;
                 if (differ == 0) {
                     if ($(".layui-laydate").length) {
                         $("#endTime").val(value);
                         $(".layui-laydate").remove();
                     }
                 }
                 initYear1 = selectYear;
             }
         });
    })
</script>