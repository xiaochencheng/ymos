<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="layui-form"
      action="${pageContext.request.contextPath }/pro/list"
      method="post">
    <%--                <input type="hidden"   value="${form.invoicePath }" name="invoicePath">--%>
    <div class="layui-inline">
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">产品名称:</div>
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

        <button class="layui-btn layui-btn-normal layui-btn-sm" type="submit">
            <i class="layui-icon">&#xe615;</i>查询
        </button>
        <button class="layui-btn  layui-btn-sm" id="export" type="button">导出Excel</button>
    </div>
</form>
<script>
    // $('.search-width').css('width', '120px');
    var status="${param.status}";
    $('#status').find("option[value='"+status+"']").prop("selected","selected");
    var size = '${param.size}';
    var pro_ch_name = '${param.pro_ch_name}';
    var pro_en_name = '${param.pro_en_name}';
    $('#size').find("option[value='" + size + "']").prop("selected", true);

    $('#export').click(function () {
        <%--var token = "${token}";--%>
        var title = document.title;
        var spId = $('#spId').val();
        var country = $('#country').val();
        // var operator=$('#operator').val();
        var dateTime = $('#dateTime').val();
        var endTime = $('#endTime').val();

        location.href = "${pageContext.request.contextPath}/pro/exportExcel/?title=" + title + "&pro_ch_name=" + pro_ch_name +"&pro_en_name=" +pro_en_name+ "&dateTime=" + dateTime ;


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