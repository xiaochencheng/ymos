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
                <legend>SKU列表
                </legend>
                <div class="layui-field-box">
                    <div id="">
                        <c:import url="../skulist/search.jsp">
                            <c:param name="action" value="${pageContext.request.contextPath }/skulist/list"/>
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
                                <th>SKU</th>
                                <th>平台SKU</th>
                                <th>识别码</th>
                                <th>商品编码</th>
                                <th>商品名称</th>
                                <th>图片URL</th>
                                <th>商品重量</th>
                                <th>采购价</th>
                                <th>采购员</th>
                                <th>来源URL</th>
                                <th>英文报关名</th>
                                <th>中文报关名</th>
                                <th>申报重量</th>
                                <th>申报金额</th>
                                <th>危险运输品</th>
                                <th>海关编码</th>
                                <th>开发员</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${datas}" var="data">
                                <tr>
                                    <td><input type="checkbox" lay-skin="primary" lay-filter="itemChoose" id="ids" name="ids" value="${data.id }"/></td>
                                    <td>${data.id }</td>
                                    <td>${data.name }</td>
                                    <td>${data.sku }</td>
                                    <td>${data.nameCnBg }</td>
                                    <td>${data.nameEn }</td>
                                    <td>${data.nameEnBg}</td>
                                    <td>${data.price}</td>
                                    <td>${data.priceBg}</td>
                                    <td>${data.sbm}"></td>
                                    <td>${data.sku}"></td>
                                    <td>${data.sourceUrl}"></td>
                                    <td>${data.weight}"></td>
                                    <td>${data.weightBg}"></td>
                                    <td>${data.spu}"></td>
                                    <td>${data.create_date}"></td>
                                    <td>${data.imgUrl}"></td>
                                    <td>${data.hgbmBg}"></td>
                                    <td>${data.dangerDesBg}"></td>
                                    <td></td>
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
            <c:param name="create">${pageContext.request.contextPath }/skulist/create</c:param>
            <c:param name="review">${pageContext.request.contextPath }/skulist/modify</c:param>
            <c:param name="delete">${pageContext.request.contextPath }/skulist/delete</c:param>
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
