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
        <legend>${empty form.id?'新增产品数据':'修改产品数据'}</legend>
        <div class="layui-field-box" style="">
            <form class="layui-form" id="myform" name="myform" action="" method="post" enctype="multipart/form-data">
                <input type="hidden" value="${form.id }" name="id">
                <input type="hidden" value="${form.pro_url }" name="pro_url">

                <div class="layui-form-item layui-form-pro-list">
                    <label class="layui-form-label">产品分类:</label>
                    <div class="layui-input-block">
                        <select id="pro_list" name="pro_list" lay-verify="pro_list" lay-search>
                            <option value="">----请选择产品分类----</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">产品中文名:</label>
                    <div class="layui-input-block">
                        <input type="text" id="pro_ch_name" name="pro_ch_name" class="layui-input"
                               value="${form.pro_ch_name}" placeholder="请输入产品中文名" lay-verify="required"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">产品英文名:</label>
                    <div class="layui-input-block">
                        <input type="text" id="pro_en_name" name="pro_en_name" class="layui-input"
                               value="${form.pro_en_name}" placeholder="请输入产品英文名" lay-verify="required"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关中文名:</label>
                    <div class="layui-input-block">
                        <input type="text" name="cus_ch_name" class="layui-input" value="${form.cus_ch_name}"
                               placeholder="请输入报关中文名" lay-verify="required" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关英文名:</label>
                    <div class="layui-input-block">
                        <input type="text" name="cus_en_name" class="layui-input" value="${form.cus_en_name}"
                               placeholder="请输入报关英文名" lay-verify="required" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关价格(USD):</label>
                    <div class="layui-input-block">
                        <input type="text" name="cus_price" class="layui-input" value="${form.cus_price}"
                               placeholder="请输入报关价格" lay-verify="required|number" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">报关重量(g):</label>
                    <div class="layui-input-block">
                        <input type="text" name="cus_weight" class="layui-input" value="${form.cus_weight}"
                               placeholder="请输入报关重量" lay-verify="required|number" style="float: left;width: 290px">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">上传图片:</label>
                    <div class="layui-input-block">
                        <input type="file" id="test1" name="file" multiple="multiple"/>
                        <div class="layui-form-item">
                            <c:if test="${not empty form.pro_url}">
                                <a href="${form.pro_url }"
                                   target="${form.pro_url=='' or form.pro_url==null?'':'_blank'}"><img
                                        src="${form.pro_url }" style="width: 92px;height: 92px;"></a>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">建议采购价:</label>
                    <div class="layui-input-block">
                        <input type="text" name="pro_purchase_price" class="layui-input"
                               value="${form.pro_purchase_price}" placeholder="请输入建议采购价"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">重量:</label>
                    <div class="layui-input-block">
                        <input type="text" name="weight" class="layui-input" value="${form.weight}" placeholder="请输入重量"
                               lay-verify="required" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">采购网址:</label>
                    <div class="layui-input-block">
                        <input type="text" name="url" class="layui-input" value="${form.url}" placeholder="请输入采购网址"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">采购网址2（选填）:</label>
                    <div class="layui-input-block">
                        <input type="text" name="url2" class="layui-input" value="${form.url2}"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">采购网址3（选填）:</label>
                    <div class="layui-input-block">
                        <input type="text" name="url3" class="layui-input" value="${form.url3}"
                               style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">预售价:</label>
                    <div class="layui-input-block">
                        <input type="text" name="presale_price" lay-verify="required|number" class="layui-input" value="${form.presale_price}"
                               placeholder="请输入预售价" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">运费:</label>
                    <div class="layui-input-block">
                        <input type="text" name="freight" class="layui-input" placeholder="请输入运费" lay-verify="required|number"  value="${form.freight}" style="float: left;width: 290px">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">备注:</label>
                    <div class="layui-input-inline">
                        <input type="text" name="remark" value="${form.remark}" placeholder="请输入备注" lay-verify="remark"
                               class="layui-input create-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">操作:</label>
                    <div class="layui-input-inline" id="create-button">
                        <button class="layui-btn  layui-btn-normal layui-btn-sm dw-dailog" id="btnId" lay-submit=""
                                lay-filter="offerForm" >
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
    var pro_list = "${form.pro_list}";
    // pro_list.substringAfter(pro_list,";");
    pro_list.replace(pro_list, "&nbsp;", "");
    $.getJSON('${pageContext.request.contextPath }/proList/queryName', function (result) {
        if (result) {
            $.each(result, function (index, obj) {
                $('#pro_list').append("<option value='" + obj.id + obj.adName + "'>" + obj.name + "</option>");
            })
            $('#pro_list').find("option[value='" + pro_list + "']").prop("selected", "selected");
            layui.use('form', function () {
                var form = layui.form;
                form.render();
            })
        }
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
            var formSelects = '';
            layui.use('formSelects', function () {
                formSelects = layui.formSelects;
            })

            var id = "${form.id}";
            var url = '';
            if (id == '') {
                url = 'create1';
                var file = $('input[type=file]').value;
                if ((file != null && file != "") && !(file.endsWith(".jpg") || file.endsWith(".png") || file.endsWith(".gif") ||
                    file.endsWith(".JPEG") || file.endsWith(".PNG") || file.endsWith(".GIF") || file.endsWith(".xlsx"))) {
                    $('#test1').focus();
                    layer.tips('图片文件格式不对!(后缀名如:jpg,png,jpeg,gif)', '#picSrc', {tips: [2, '#3595CC'], time: 3000});
                    return false;
                }

            } else {
                url = 'review';
            }

           var index=layer.load(2, {shade:[0.5,'#fff']});
            var queryStr = "${queryStr}";
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/rev/" + url,
                data: formData,
                traditional: true,//必须加上设置为true
                contentType: false ,
                processData: false,
                dataType: "json",
                success: function (reply) {
                    if (reply.flag) {
                        layer.close(index);
                        location.href = "${pageContext.request.contextPath }/rev/list?" + queryStr;
                    }
                }
            });
            return false;
        });

        upload.render({
            elem: '#test1'
            , url: '${pageContext.request.contextPath }/rev/create1'
            , auto: false //选择文件后不自动上传
            , bindAction: '#uploadinvoice' //指向一个按钮触发上传
            , accpet: "file"
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    /* 	$('#demo1').attr('src', result);  */
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code > 0) {
                    alert('上传失败！')
                }
                //上传成功
                alert('上传成功！')
            }
        });


    });

</script>
