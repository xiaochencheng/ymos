<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../common/common.jsp" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%--<%@ page deferredSyntaxAllowedAsLiteral="true"%>--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta name="viewport"
      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/jsandcss/layui/layui/css/formSelects-v4.css">
<style>
    .layui-form-select, .xm-select-parent {
        width: 290px;
    }

    .layui-edge {
        left: 267px;
    }

    .xm-select-title {
        width: 288px;
    }

    .layui-upload-choose {
        margin-top: 10px;
    }
</style>
<body>
<div class="content">
    <c:import url="../common/errors.jsp"></c:import>
    <fieldset class="layui-elem-field">
        <legend>${empty form.id?'产品数据':'修改SKU产品数据'}</legend>
        <div class="layui-field-box" style="">
            <form class="layui-form" id="myform" name="myform" action="" method="post" enctype="multipart/form-data">
                <input type="hidden" value="${form.id }" name="id">

                <div class="layui-form-item">
                    <label class="layui-form-label">SKU:</label>
                    <div class="layui-input-block">
                        <input type="text" id="skuName" name="skuName" class="layui-input"
                               value="${form.skuName}"  lay-verify="required" readonly="readonly"
                               style="float: left;width: 290px" >
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">SPU:</label>
                    <div class="layui-input-block">
                        <input type="text" id="spu" name="spu" class="layui-input" readonly="readonly"
                               value="${form.spu}"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">产品中文名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" class="layui-input" value="${form.name}"
                               placeholder="产品中文名称" lay-verify="required" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关中文名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="nameCnBg" class="layui-input" value="${form.nameCnBg}"
                               placeholder="报关中文名称" lay-verify="required" style="float: left;width: 290px">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">产品英文名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="nameEn" class="layui-input" value="${form.nameEn}"
                               placeholder="请输入产品英文名称" lay-verify="required" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关英文名称:</label>
                    <div class="layui-input-block">
                        <input type="text" name="nameEnBg" class="layui-input" value="${form.nameEnBg}"
                               placeholder="请输入报关英文名称" lay-verify="required" style="float: left;width: 290px">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">SKU中文属性:</label>
                    <div class="layui-input-block">
                        <input type="text" name="attributes" class="layui-input" value="${form.attributes}"
                               placeholder="请输入SKU中文属性" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">价格:</label>
                    <div class="layui-input-block">
                        <input type="text" name="price" class="layui-input" value="${form.price}"
                               placeholder="请输入价格" lay-verify="required|number" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关价格:</label>
                    <div class="layui-input-block">
                        <input type="text" name="priceBg" class="layui-input" value="${form.priceBg}"
                               placeholder="请输入报关价格" lay-verify="required|number" style="float: left;width: 290px">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">资源URL:</label>
                    <div class="layui-input-block">
                        <input type="text" name="sourceUrl" class="layui-input" value="${form.sourceUrl}"
                               placeholder="请输入资源URL"  style="float: left;width: 290px">
                    </div>
                </div><div class="layui-form-item">
                    <label class="layui-form-label">重量(g):</label>
                    <div class="layui-input-block">
                        <input type="text" name="weight" class="layui-input" value="${form.weight}"
                               placeholder="请输入重量" lay-verify="required|number" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关重量(g):</label>
                    <div class="layui-input-block">
                        <input type="text" name="weightBg" class="layui-input" value="${form.weightBg}"
                               placeholder="请输入报关重量" lay-verify="required|number" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item layui-form-pro-list">
                    <label class="layui-form-label">是否危险运输品:</label>
                    <div class="layui-input-block">
                        <select id="dangerDesBg" name="dangerDesBg" lay-verify="dangerDesBg" lay-search>
                            <option value="0">无</option>
                            <option value="1">含电</option>
                            <option value="2">液体</option>
                            <option value="3">粉末</option>
                            <option value="4">纯电</option>
                            <option value="5">膏体</option>
                            <option value="6">带磁</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">海关编码:</label>
                    <div class="layui-input-block">
                        <input type="text" name="hgbmBg" class="layui-input" value="${form.hgbmBg}"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">操作:</label>
                    <div class="layui-input-inline" id="create-button">
                        <button class="layui-btn  layui-btn-normal layui-btn-sm dw-dailog" id="btnId" lay-submit=""
                                lay-filter="offerForm">
                            <i class="layui-icon">&#xe654;</i>保存
                        </button>
                        <button class="layui-btn  layui-btn-warm layui-btn-sm dw-dailog" type="button"
                                onclick="javascript:history.go(-1)">
                            <i class="layui-icon">&#x1006;</i>取消
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </fieldset>
</div>
</body>
<script type="text/javascript">
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
        $("form").css({"overflow": "scroll"});
        $(".layui-input").css({"height": "30px", "margin-top": "2px", "margin-bottom": "2px"});
        $("body").css({"transform": "scale(.7)", "position": "relative", "bottom": "150px", "right": "140px"});
        $(".layui-elem-field").css({"overflow": "scroll", "height": "1000px"});
    }

    if (!IsPC()) {
        mobile();
    }

    $('.create-textarea').css('width', '400px');
    layui.config({
        base: '${pageContext.request.contextPath}/jsandcss/layui/layui/'
    }).extend({
        formSelects: 'formSelects-v4'
    });


    layui.use(['form', 'jquery', 'upload', 'laydate'], function () {
        var laydate = layui.laydate;
        var form = layui.form;
        var upload = layui.upload;
        form.render();
        // laydate.render({
        //     elem: '#dateTime'
        // });

        form.on('submit(offerForm)', function (data) {
            var formData = new FormData($('#myform')[0]);
            console.info(formData);
            var formSelects = '';
            layui.use('formSelects', function () {
                formSelects = layui.formSelects;
            })

            var id = "${form.id}";
            var url = '';

            var queryStr = "${queryStr}";
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/sku/update",
                data: formData,
                contentType: false,
                processData: false,
                dataType: "json",
                success: function (reply) {
                    if (reply.flag) {
                        location.href = "${pageContext.request.contextPath }/sku/list?" + queryStr;
                    }
                }
            });
            return false;
        });

    });

</script>
