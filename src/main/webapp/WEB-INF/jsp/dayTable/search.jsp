<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="layui-form"
      action="${pageContext.request.contextPath }/dayTable/list"
      method="post">
    <%--                <input type="hidden"   value="${form.invoicePath }" name="invoicePath">--%>
    <div class="layui-inline">
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">店铺账号:</div>
            <div class="layui-input-inline search-width">
                <input type="text"  class="layui-input" id="dpzh" name="dpzh" value="${param.dpzh}" placeholder="请输入店铺账号">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" id="dateDiv">日期:</div>
            <div class="layui-input-inline" style="width: 120px">
                <input type="text" class="layui-input" id="fksj" name="fksj"
                       value="${param.fksj}" placeholder="请选择付款时间">
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
    var dpzh = '${param.dpzh}';
    var fksj= '${param.fksj}';
    $('#size').find("option[value='" + size + "']").prop("selected", true);

    $('#export').click(function () {
        <%--var token = "${token}";--%>
        var title = document.title;
        var dpzh = $('#dpzh').val();
        var fksj = $('#fksj').val();

        location.href = "${pageContext.request.contextPath}/dayTable/exportExcel/?title=" + title + "&dpzh=" + dpzh+"&fksj="+fksj;
    });


    layui.use(['form', 'laydate'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
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
                        $("#fksj").val(value);
                        $(".layui-laydate").remove();
                    }
                }
                initYear1 = selectYear;
            }
        });

    })
</script>