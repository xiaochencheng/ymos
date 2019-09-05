<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="layui-form"
      action="${pageContext.request.contextPath }/sku/list"
      method="post">
    <%--                <input type="hidden"   value="${form.invoicePath }" name="invoicePath">--%>
    <div class="layui-inline">
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">产品名称:</div>
            <div class="layui-input-inline search-width">
                <input type="text" class="layui-input" id="name" name="name" value="${param.name}"
                       placeholder="请输入产品中文名">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">SPU:</div>
            <div class="layui-input-inline search-width">
                <input type="text" class="layui-input" id="spu" name="spu" value="${param.spu}"
                       placeholder="请输入SPU">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">SKU:</div>
            <div class="layui-input-inline search-width">
                <input type="text" class="layui-input" id="skuName" name="skuName" value="${param.skuName}"
                       placeholder="请输入SKU">
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
            <div class="layui-input-inline">
                <select name="size" id="size" lay-search>
                    <option value="">每页显示数</option>
                    <option value="20">20条每页</option>
                    <option value="50">50条每页</option>
                    <option value="100">100条每页</option>
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
    // $('.search-width').css('width', '120px');
    var size = '${param.size}';
    var name = '${param.name}';
    var spuName='${param.spu}';
    var skuName='${param.skuName}';

    $('#size').find("option[value='" + size + "']").prop("selected", true);

    $('#export').click(function () {
        <%--var token = "${token}";--%>
        var title = document.title;
        var skuName = $('#skuName').val();
        var name = $('#name').val();


        var spuName = $('#spu').val();
        var dateTime = $('#dateTime').val();
        var endTime = $('#endTime').val();

        location.href = "${pageContext.request.contextPath}/sku/exportExcel/?title=" + title + "&pro_ch_name=" + name +"&skuName="+skuName+"&spuName="+spuName+ "&dateTime=" + dateTime+"&endTime="+endTime;

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