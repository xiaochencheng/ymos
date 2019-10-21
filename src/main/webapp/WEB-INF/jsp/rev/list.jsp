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
                <legend>产品列表
                </legend>
                <div class="layui-field-box">
                    <div id="">
                        <c:import url="../rev/search.jsp">
                            <c:param name="action" value="${pageContext.request.contextPath }/rev/list"/>
                        </c:import>
                    </div>
                    <form class="layui-form list-form" id="download" action="">
                        <input type="hidden" value="" name="invoicePath" id="invoicePath">
                        <input type="hidden" value="" name="filePath" id="filePath">

                        <table class="layui-table" lay-size="sm">
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" lay-skin="primary" lay-filter="allChoose"/>
                                </th>
                                <th>id</th>
                                <th>SPU</th>
                                <th>产品中文名</th>
                                <th>产品英文名</th>
                                <th>报关中文名</th>
                                <th>报关英文名</th>
                                <th>报关价格</th>
                                <th>报关重量</th>
                                <th>产品分类</th>
                                <th>图片</th>
                                <th>建议采购价</th>
                                <th>重量</th>
                                <th>采购网址</th>
                                <th>预售价</th>
                                <th>运费</th>
                                <th>创建人</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${datas}" var="data">
                                <tr>
                                    <td>
                                        <c:if test="${data.status==1}"> <input type="checkbox" lay-skin="primary"
                                                                               lay-filter="itemChoose" id="ids"
                                                                               name="ids" value="${data.id }"/></c:if>
                                        <c:if test="${data.status==2}"> <input class="layui-input layui-disabled"
                                                                               type="checkbox" lay-skin="primary"
                                                                               lay-filter="itemChoose" id="ids"
                                                                               name="ids" value="${data.id }"/></c:if>
                                        <c:if test="${data.status==3}"> <input class="layui-input layui-disabled"
                                                                               type="checkbox" lay-skin="primary"
                                                                               lay-filter="itemChoose" id="ids"
                                                                               name="ids" value="${data.id }"/></c:if>
                                    </td>
                                    <td>${data.id }</td>
                                    <td>
                                        <c:if test="${data.status ==1}">
                                              ${data.spu}</c:if></td>
                                    <td>${data.pro_ch_name}</td>
                                    <td>${data.pro_en_name}</td>
                                    <td>${data.cus_ch_name}</td>
                                    <td>${data.cus_en_name}</td>
                                    <td>${data.cus_price}</td>
                                    <td>${data.cus_weight}</td>
                                    <td>${data.listName}</td>
                                    <td>
                                        <c:if test="${empty data.pro_url}"></c:if>
                                        <c:if test="${not empty data.pro_url}"><img src="${data.pro_url}" width="100%" height="50px"></c:if>
                                    </td>
                                    <td>${data.pro_purchase_price}</td>
                                    <td>${data.weight}</td>
                                    <td><a href="${data.url}" target="_blank" style="color:deepskyblue;">采购链接</a></td>
                                    <td>${data.presale_price}</td>
                                    <td>${data.freight}</td>
                                    <td>${data.creator}</td>
                                    <td>
                                        <c:if test="${data.status ==1}">
                                            <button type="button" class="layui-btn layui-btn-xs" type="button"
                                                    style="width: 62px">
                                                通过
                                            </button>
                                        </c:if>
                                        <c:if test="${data.status ==2}">
                                            <button type="button" class="layui-btn layui-btn-xs layui-btn-normal"
                                                    type="button"
                                                    style="width: 62px">未通过
                                            </button>
                                        </c:if>
                                        <c:if test="${data.status ==3}">
                                            <button type="button" class="layui-btn layui-btn-xs layui-btn-warm"
                                                    type="button"
                                                    style="width: 62px">待审核
                                            </button>
                                        </c:if>
                                    </td>
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
            <c:param name="create">${pageContext.request.contextPath }/rev/create</c:param>
            <c:param name="review">${pageContext.request.contextPath }/rev/modify</c:param>
            <%--<c:param name="delete">${pageContext.request.contextPath }/pro/delete</c:param>--%>
            <%--<c:param name="deleteMsg">确认删除产品数据？</c:param>--%>
        </c:import>
        <c:import url="../common/page.jsp">
            <c:param name="jump">true</c:param>
        </c:import>
    </div>
</div>
</body>
<script>
    /*proListName*/


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
