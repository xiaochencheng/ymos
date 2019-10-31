<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="layui-form"
      action="${pageContext.request.contextPath }/wuliuInfo/list"
      method="post">
    <%--                <input type="hidden"   value="${form.invoicePath }" name="invoicePath">--%>
    <div class="layui-inline">
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">运单号:</div>
            <div class="layui-input-inline search-width">
                <input type="text"  class="layui-input" id="ydh" name="ydh" value="${param.ydh}" placeholder="请输入运单号">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" id="dateDiv">日期:</div>
            <div class="layui-input-inline" style="width: 120px">
                <input type="text" class="layui-input" id="fksj" name="fksj"
                       value="${param.fksj}" placeholder="请选择付款时间">
            </div>
        </div>
        <div class="layui-input-inline" style="width: 120px">
            <input type="text" class="layui-input" id="fhsj" name="fhsj"
                   value="${param.fhsj}" placeholder="请选择发货时间">
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 40px">订单状态:</div>
            <div class="layui-input-inline">
                <select name="orderStatus" id="orderStatus" lay-search>
                    <option value="">请选择订单状态</option>
                    <option value="已发货">已发货</option>
                    <option value="查询中">查询中</option>
                    <option value="查询不到">查询不到</option>
                    <option value="运输途中">运输途中</option>
                    <option value="到达待取">到达待取</option>
                    <option value="成功签收">成功签收</option>
                    <option value="投递失败">投递失败</option>
                    <option value="可能异常">可能异常</option>
                    <option value="运输过久">运输过久</option>
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
    var orderStatus="${param.orderStatus}";
    $('#orderStatus').find("option[value='"+orderStatus+"']").prop("selected","selected");
    var size = '${param.size}';
    var ydh = '${param.ydh}';
    var fksj= '${param.fksj}';
    var fhsj= '${param.fhsj}';
    $('#size').find("option[value='" + size + "']").prop("selected", true);

    $('#export').click(function () {
        <%--var token = "${token}";--%>
        var title = document.title;
        var ydh=$('#ydh').val();
        var fksj = $('#fksj').val();
        var fhsj = $('#fhsj').val();
        var orderStatus = $('#orderStatus').val();

        location.href = "${pageContext.request.contextPath}/wuliuInfo/exportExcel/?title=" + title + "&fhsj=" + fhsj +"&fksj=" +fksj+ "&ydh=" + ydh+"&orderStatus="+ orderStatus;
    });


    layui.use(['form', 'laydate','upload'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
        var upload = layui.upload;
        form.render();


        laydate.render({
            elem: '#fksj',
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
            elem: '#fhsj',
            /* ready和change函数是为了实现选择年月时不用点确定直接关闭  */
            ready: function (date) { // 控件在打开时触发，回调返回一个参数：初始的日期时间对象
                initYear = date.year;
            },
            change: function (value, date, endDate) { // 年月日时间被切换时都会触发。回调返回三个参数，分别代表：生成的值、日期时间对象、结束的日期时间对象
                var selectYear = date.year;
                var differ = selectYear - initYear;
                if (differ == 0) {
                    if ($(".layui-laydate").length) {
                        $("#fhsj").val(value);
                        $(".layui-laydate").remove();
                    }
                }
                initYear1 = selectYear;
            }
        });
    })
</script>