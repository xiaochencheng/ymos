<%@ include file="../common/common.jsp" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script src="${pageContext.request.contextPath }/jsandcss/js/layer/layer.js"></script>
<style>
   input {
       width:70%;
       border:none;
   }

</style>

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
                        <table class="layui-table"  id="Order" lay-filter="Order" lay-size="sm">
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" lay-skin="primary" lay-filter="allChoose"/>
                                </th>
                                <th>日期</th>
                                <th>店铺账号</th>
                                <th>订单数量</th>
                                <th>订单金额($)</th>
                                <th>汇率</th>
                                <th>销售金额</th>
                                <th>通道费</th>
                                <th>物流运费</th>
                                <th>物流成本占比%</th>
                                <th>采购合计</th>
                                <th>采购成本占比%</th>
                                <th>投放花费($)</th>
                                <th>投放成本占比%</th>
                                <th>预估利润</th>
                                <th>成本利润率%</th>
                                <th>销售利润率%</th>
                            </tr>
                            </thead>
                            <tbody>
                             <c:forEach items="${datas}" var="data">
                            <tr>
                                <td>
                                    <input type="checkbox" lay-skin="primary"
                                           lay-filter="itemChoose" id="id"
                                           name="id" value="${data.id }"/>
                                </td>
                                <td>${data.fksj}<input type="hidden" value="${data.fksj}" name="fksj" id="fksj"></td>
                                <td>${data.dpzh}<input type="hidden" value="${data.dpzh}" name="dpzh" id="dpzh"></td>
                                <td ><input type="text" class='bgh' name="bgh"  value="${data.bgh}"  /></td>
                                <td><input type="text" class='orderMoney' name="orderMoney"  value="${data.orderMoney}"  /></td>
                                <td><input type="text" class='rate' name="rate" value="${data.rate}" /></td>
                                <td><input type="text" class='salesMoney' name="salesMoney" value="${data.salesMoney}" /></td>
                                <td><input type="text" class='channelFeel'  name="channelFeel" value="${data.channelFeel}"/></td>
                                <td><input type="text" class='wuliuMoney'  name="wuliuMoney" value="${data.wuliuMoney}"/></td>
                                <td><input type="text" class='wuliuChen'  name="wuliuChen" value="<fmt:formatNumber type='number' value='${data.wuliuChen}'  pattern='#0.00' />%"/></td>
                                <td><input type="text" class='caiguo'  name="caiguo" value="${data.caiguo}"/></td>
                                <td><input type="text" class='caiguoChen'  name="caiguoChen" value="<fmt:formatNumber type='number' value='${data.caiguoChen}'  pattern='#0.00' />%"/></td>
                                <td><input type="text" class='toufang'  name="toufang" value="${data.toufang}"/></td>
                                <td><input type="text" class='toufangChen' name="toufangChen" value="<fmt:formatNumber type='number' value='${data.toufangChen}'  pattern='#0.00' />%"/></td>
                                <td><input type="text" class='yugulirun'  name="yugulirun" value="${data.yugulirun}"/></td>
                                <td><input type="text" class='chenBen'  name="chenBen" value="<fmt:formatNumber type='number' value='${data.chenBen}'  pattern='#0.00' />%"/></td>
                                <td><input type="text" class='salesLirun'  name="salesLirun" value="<fmt:formatNumber type='number' value='${data.salesLirun}'  pattern='#0.00' />%"/></td>
                            </tr>
                             </c:forEach>
                            </tbody>
                        </table>
                        <button type="button" class="layui-btn layui-btn-warm" id="save" lay-filter="save">保存</button>
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


    $("#save").click(function(){
        var tr = $("#Order tr"); // 获取table中每一行内容
        var result = []; // 数组
        for (var i = 0; i < tr.length; i++) {// 遍历表格中每一行的内容
            var tds = $(tr[i]).find("td");
            if (tds.length > 0) {

                var wuliu=$(tds[9]).find("input").val();
                if(wuliu.indexOf("%")!=-1){
                    wuliu=wuliu.substring(0,wuliu.length-1);
                }else{
                    wuliu=wuliu.substring(0,wuliu.length);
                }

                var caig=$(tds[11]).find("input").val();
                if(caig.indexOf("%")!=-1){
                    caig=caig.substring(0,caig.length-1);
                }else{
                    caig=caig.substring(0,caig.length);
                }

                var tou=$(tds[13]).find("input").val();
                if(tou.indexOf("%")!=-1){
                    tou=tou.substring(0,tou.length-1);
                }else{
                    tou=tou.substring(0,tou.length);
                }

                var ch=$(tds[15]).find("input").val();
                if(ch.indexOf("%")!=-1){
                    ch=ch.substring(0,ch.length-1);
                }else{
                    ch=ch.substring(0,ch.length);
                }

                var sal=$(tds[16]).find("input").val();
                if(sal.indexOf("%")!=-1){
                    sal=sal.substring(0,sal.length-1);
                }else{
                    sal=sal.substring(0,sal.length);
                }

                var itemObj = {
                    id:$(tds[0]).find("input").val(),
                    fksj:$(tds[1]).find("input").val(),
                    dpzh:$(tds[2]).find("input").val(),
                    rate : $(tds[5]).find("input").val(),
                    salesMoney : $(tds[6]).find("input").val(),
                    channelFeel : $(tds[7]).find("input").val(),
                    wuliuMoney : $(tds[8]).find("input").val(),
                    wuliuChen : wuliu,
                    caiguo : $(tds[10]).find("input").val(),
                    caiguoChen : caig,
                    toufang : $(tds[12]).find("input").val(),
                    toufangChen : tou,
                    yugulirun : $(tds[14]).find("input").val(),
                    chenBen : ch,
                    salesLirun : sal
                };
            }
            result.push(itemObj);
        }


        var jsonData = { // json数据
            "dayTable" : result
        }
        var str= JSON.stringify(result);

        $.ajax({
            type : "post",
            async: false,
            dataType: "json",
            contentType:"application/json",
            url : "${pageContext.request.contextPath }/dayTable/update"+"?name="+encodeURIComponent(str),
            data :JSON.stringify(result),
            success : function(data) {
                 if(data.flag==true){
                     location.reload();
                     }
            }
        });

        });



    /*计算销售金额  通道费4.4%+2%*/
    $("#Order").on('input', '.rate', function () {
        var self = $(this);
        var tr = self.closest("tr");

        var quantity = self.val();

        var Price = tr.find(".orderMoney").val();
        var amount = 0;
        var channlFeel=0;
        if ($.isNumeric(quantity) && $.isNumeric(Price)) {
            amount =parseFloat( quantity * Price);
          var pri=  amount.toFixed(2);
            channlFeel= parseFloat(quantity * Price*0.064);
           var feel= channlFeel.toFixed(2);
        }
        tr.find(".salesMoney").val(pri);
        tr.find(".channelFeel").val(feel);
    });

    /*物流花费（$） 物流成本占比=物流运费/销售金额*/
    $("#Order").on('input', '.wuliuMoney', function () {
        var self = $(this);
        var tr = self.closest("tr");

        var quantity = self.val();
        /* alert("toufang:"+quantity);*/
        var Price = tr.find(".orderMoney").val();
        var rate = tr.find(".rate").val();
        /* alert("Price:"+Price);
         alert("rate:"+rate);*/
        var amount = 0
        if ($.isNumeric(quantity) && $.isNumeric(Price)&& $.isNumeric(rate)) {
            amount =quantity/(rate * Price);
           var wuliu= amount.toFixed(2);
        }
        tr.find(".wuliuChen").val(wuliu);

    });

    /*采购花费（$） 采购成本占比=采购合计/销售金额  预估利润*/
    $("#Order").on('input', '.caiguo', function () {
        var self = $(this);
        var tr = self.closest("tr");

        var quantity = self.val();
        var Price = tr.find(".orderMoney").val();
        var rate = tr.find(".rate").val();
        var amount = 0;

        if ($.isNumeric(quantity) && $.isNumeric(Price)&& $.isNumeric(rate)) {
            amount =parseFloat(quantity/ (rate * Price));
            var coun= amount.toFixed(2);
        }
        tr.find(".caiguoChen").val(coun);

    });


    /*投放花费（$ 投放成本占比=投放花费/销售金额*/
    $("#Order").on('input', '.toufang', function () {
        var self = $(this);
        var tr = self.closest("tr");

        var quantity = self.val();
        var Price = tr.find(".orderMoney").val();
        var rate = tr.find(".rate").val();
        var channlFeel=tr.find(".channelFeel").val();
        var wuliu=tr.find(".wuliuMoney").val();
        var caigou=tr.find(".caiguo").val();
        var toufang=tr.find(".toufang").val();
        var amount = 0;
        var yugulirun=0;
        var chenBens=0;
        var salesLirun=0;
        var chen=0;
        var yungu=0;
        if ($.isNumeric(quantity) && $.isNumeric(Price) && $.isNumeric(rate)) {
            amount =parseFloat((rate * quantity)/(Price * rate));
            var cc= amount.toFixed(2);
            /*预估利润=销售金额-通道费-物流运费-采购合计-投放花费
            * 成本利润率=（物流运费+采购合计+（投放花费*汇率）+通道费）/销售金额
            * 销售利润率=预估利润/销售金额*/
            yugulirun=(rate * Price)-channlFeel-wuliu-caigou-(quantity * rate);
             yungu= yugulirun.toFixed(2);
             var s1=(quantity*rate);
             var one=channlFeel+wuliu+caigou+(quantity*rate);
             var two=parseFloat(rate * Price);
             var num=Number(channlFeel*100)+Number(wuliu*100)+Number(caigou*100)+Number(s1*100);

            /* alert(num/100);*/
            /* alert(two);*/
             chenBens=((num/100)/parseFloat(rate * Price));
             chen= chenBens.toFixed(2);
             salesLirun=parseFloat(yugulirun)/parseFloat(rate * Price);
            var sale= salesLirun.toFixed(2);

        }
        tr.find(".toufangChen").val(cc);
        tr.find(".yugulirun").val(yungu);
       tr.find(".chenBen").val(chen);
          tr.find(".salesLirun").val(sale);

    });







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
