<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form class="layui-form"
      action="${pageContext.request.contextPath }/order/list"
      method="post">
    <%--                <input type="hidden"   value="${form.invoicePath }" name="invoicePath">--%>
    <div class="layui-inline">
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">包裹号:</div>
            <div class="layui-input-inline search-width">
               <input type="text"  class="layui-input" id="bgh" name="bgh" value="${param.bgh}" placeholder="请输入包裹号">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">订单号:</div>
            <div class="layui-input-inline search-width">
                <input type="text"  class="layui-input" id="orderId" name="orderId" value="${param.orderId}" placeholder="请输入订单号">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 30px">运单号:</div>
            <div class="layui-input-inline search-width">
                <input type="text"  class="layui-input" id="ydh" name="ydh" value="${param.ydh}" placeholder="请输入运单号">
            </div>
        </div>
        <div class="layui-inline">
            <div class="search-name" style="width: 40px">状态:</div>
            <div class="layui-input-inline">
                <select name="orderStatus" id="orderStatus" lay-search>
                    <option value="">请选择订单状态</option>
                    <option value="已付款">已付款</option>
                    <option value="已发货">已发货</option>
                    <option value="已退款">已退款</option>
                    <option value="待审核">待审核</option>
                    <option value="已审核">已审核</option>
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
        <button class="layui-btn  layui-btn-sm" id="improt" type="button"><i class="layui-icon"></i>导入Excel</button>
    </div>
</form>
<script>
    // $('.search-width').css('width', '120px');
    var orderStatus="${param.orderStatus}";
    $('#orderStatus').find("option[value='"+orderStatus+"']").prop("selected","selected");
    var size = '${param.size}';
    var orderId = '${param.orderId}';
    var bgh = '${param.bgh}';
    var ydh = '${param.ydh}';
    var fksj= '${param.fksj}';
    $('#size').find("option[value='" + size + "']").prop("selected", true);

    $('#export').click(function () {
        <%--var token = "${token}";--%>
        var title = document.title;
        var orderId = $('#orderId').val();
        var bgh = $('#bgh').val();
        var ydh=$('#ydh').val();
        var fksj = $('#fksj').val();
        var orderStatus = $('#orderStatus').val();

        location.href = "${pageContext.request.contextPath}/order/exportExcel/?title=" + title + "&orderId=" + orderId +"&bgh=" +bgh+ "&ydh=" + ydh+"&fksj="+fksj+"&orderStatus="+ orderStatus;
    })


    layui.use(['form', 'laydate','upload'], function () {
        var form = layui.form;
        var laydate = layui.laydate;
        var upload = layui.upload;
        form.render();

        //指定允许上传的文件类型
        upload.render({
            elem: '#improt'
            ,url: '${pageContext.request.contextPath}/order/import'
            ,accept: 'file' //普通文件
            ,exts: 'xls'
            ,done: function(res){
               if(res.flag==true){
                   location.reload();
               }else{
                   alert("导入失败！");
               }
            }
        });

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