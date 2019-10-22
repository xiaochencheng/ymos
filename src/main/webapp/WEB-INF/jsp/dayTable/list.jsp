<%@ include file="../common/common.jsp" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="${pageContext.request.contextPath }/jsandcss/js/layer/layer.js"></script>




<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsandcss/layui/layui/css/layui.css">
</head>
<body>


<div class="mainbox">
    <div>
        <div>
            <fieldset class="layui-elem-field fieldset">
                <legend>销售报表
                </legend>
                <div class="layui-field-box">
                    <div id="">
                        <c:import url="search.jsp">
                            <c:param name="action" value="${pageContext.request.contextPath }/dayTable/list"/>
                        </c:import>
                    </div>
                    <form class="layui-form list-form" id="download" action="">
                        <input type="hidden" value="" name="invoicePath" id="invoicePath">
                        <input type="hidden" value="" name="filePath" id="filePath">

                        <table class="layui-table" lay-size="sm">
                            <thead>
                            <tr>

                                <th>日期</th>
                                <th>店铺账号</th>
                                <th>订单数量</th>
                                <th>订单金额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${datas}" var="data">
                                <tr>

                                    <td>${data.fksj}</td>
                                    <td>${data.dpzh}</td>
                                    <td class="status">${data.bgh}</td>
                                    <td> <c:if test="${not empty data.orderMoney && not empty data.orderMoney}">
                                        <fmt:formatNumber type="number" value="${data.orderMoney}" maxFractionDigits="2"/>
                                    </c:if></td>

                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </form>
                </div>

            </fieldset>
        </div>
    </div>
    <div class="operator">

        <c:import url="../common/button.jsp">
            <%--<c:param name="create">${pageContext.request.contextPath }/pro/create</c:param>--%>
            <%--<c:param name="review">${pageContext.request.contextPath }/order/modify</c:param>--%>
            <%--<c:param name="delete">${pageContext.request.contextPath }/order/delete</c:param>--%>
            <c:param name="deleteMsg">确认删除产品数据？</c:param>
        </c:import>
        <c:import url="../common/page.jsp">
            <c:param name="jump">true</c:param>
        </c:import>
    </div>
</div>
</body>
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
    function mobile() {
        $("body").css({"position": "absolute", left: "0px"});
        $(".layui-elem-field").css({"width": "96%"});
        var operator = $(".operator");
        $(".operator").remove();
        $(".layui-elem-field").append(operator);
    }


    if (!IsPC()) {
        mobile();
    }

</script>
</html>
