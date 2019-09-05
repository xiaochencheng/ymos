<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<%@ include file="../common/common.jsp" %>
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <!-- base href="https://www.dianxiaomi.com/" -->
    <%@ page contentType="text/html" pageEncoding="UTF-8" %>
    <title>创建多属性SKU</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/jsandcss/layui/layui/css/layui.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/SKU_files/product.css" type="text/css">
    <link href="${pageContext.request.contextPath}/SKU_files/grid.css" rel="stylesheet">

    <script src="${pageContext.request.contextPath}/SKU_files/frame.js"></script>
    <script src="${pageContext.request.contextPath}/SKU_files/myj.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/SKU_files/handlebars-v4.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/SKU_files/productVariant.js"></script>
</head>
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
    .layui-elem-field{
        height:100%;
    }
</style>
<body class="productAddVarient bg-light-blue" style="">
<div class="content">
    <c:import url="../common/errors.jsp"></c:import>
    <fieldset class="layui-elem-field">
        <legend>${empty form.id?'新增SKU':'修改SKU'}</legend>
        <div class="layui-field-box" style="">
            <form class="layui-form" id="myform" name="myform" action="" method="post" enctype="multipart/form-data">
                <input type="hidden" value="${form.id }" name="id">
                <input type="hidden" value="${form.pro_url }" name="pro_url">
                <input type="hidden" id="hiddenInfo" value="" />
                <div class="layui-form-item layui-form-pro-list">
                    <label class="layui-form-label">产品SPU分类:</label>
                    <div class="layui-input-block">
                        <select id="spuName" name="spuName" lay-verify="required" class="spuName" lay-filter="demo"
                                onchange="test()" style='display: none' lay-search>

                            <option value="">----请选择产品SPU分类----</option>

                        </select>
                        <input type="hidden" value="" id="spu" name="spu">
                    </div>
                </div>

                <div class="head-tab-pane m-top10 headTabPane" id="goodsInfo" style="float:left">

                    <div class="product-info-module ">
                        <div class="product-info-module-tab">
                            <div class="product-info-module-tab-text">变种信息</div>
                        </div>
                        <div class="product-info-module-content">
                            <div class="module-content-box">
                                <div class="module-content-title">
                                    <span class="through-line"></span>
                                    <span class="through-con">生成变种</span>
                                </div>
                                <div class="variation_add variationAdd m-left35 m-bottom10" id="varListDiv">
                                    <table class="variation_add_table variationAddTable">
                                        <tbody>
                                        <tr class="varation_add_content varationAddContent" data-name="">
                                            <td class="w250">
                                                <input id="one" type="text" class="variationName form-component"
                                                       autocomplete="off" placeholder="属性名称，如：Color">
                                            </td>
                                            <td>
                                                <div class="variation_attr_add w450 active" data-id="variationAttrAdd">
                                                    <input class="variationValue " type="text"
                                                           placeholder="属性值，如：Red、Blue">
                                                    <a class="variation_value_delect gray-b removeVariation"
                                                       href="javascript:">
                                                        <i class="layui-icon layui-icon-delete"
                                                           style="font-size: 22px;"></i>
                                                    </a>
                                                </div>
                                                <a href="javascript:" class="remove_option removeOption"> <i
                                                        class="layui-icon layui-icon-close-fill"
                                                        style="font-size: 30px;"></i></a>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <a class="module-content-btn-txt m-bottom10 addAnotherOption" href="javascript:"><i
                                            class="layui-icon layui-icon-add-1"
                                            style="font-size: 18px;"></i><span>添加新属性</span></a>
                                </div>
                            </div>
                            <div class="module-content-box">
                                <div class="module-content-title">
                                    <span class="through-line"></span>
                                    <span class="through-con">编辑变种</span>
                                </div>


                                <table class="m-left35  variation_list_table variationListTable "
                                       id="variantProductTable">
                                    <tbody>
                                    </tbody>
                                </table>

                                <table class="sku-info-table skuInfoTable" id="skuGoodsList" style="display: none;">
                                    <thead>
                                    <tr>
                                        <th name="skus" style="width: 16%">SKU</th>
                                        <th>中文名称</th>
                                        <th style="">英文名称</th>
                                        <th style="">来源URL</th>
                                        <th style="width: 12%">识别码</th>
                                        <th style="width: 10%">默认采购价</th>
                                        <th style="width: 10%">重量(g)</th>
                                        <th style="width: 5%;">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>

                                <table class="sku-info-table skuInfoTable" id="skuBgList" style="display: none">
                                    <thead>
                                    <tr>
                                        <th style="width: 16%;">SKU</th>
                                        <th>中文报关名</th>
                                        <th>英文报关名</th>
                                        <th style="width: 14%">报关重量(g)</th>
                                        <th style="width: 10%">报关金额</th>
                                        <th style="width: 10%">海关编码</th>
                                        <th style="width: 13%">危险运输品</th>
                                        <th style="width: 5%">操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>


                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">操作:</label>
                    <div class="layui-input-inline" id="create-button">
                        <button type="button" class="layui-btn  layui-btn-normal layui-btn-sm dw-dailog" id="btnId"
                                lay-submit=""
                                lay-filter="loading">
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


</div>

</body>

<script type="text/javascript">
$("#spuName").change(function(){
var spuVal =  $(this).val();
$("#spu").val(spuVal);
/*    initVariation();
variationListBuild('');*/
});
</script>


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
    var spuName = "${form.spuName}";
    // pro_list.substringAfter(pro_list,";");
    $.getJSON('${pageContext.request.contextPath }/sku/spu', function (result) {
        if (result) {
            $.each(result, function (index, obj) {
                $('#spuName').append("<option value='" + obj.spu + "'>" + obj.spu + "</option>");
            })
            $('#spuName').find("option[value='" + spuName + "']").prop("selected", "selected");
            layui.use('form', function () {
                var form = layui.form;
                form.render();
            })
        }
    });


    layui.use(['form', 'jquery', 'upload', 'table'], function () {
        var laydate = layui.laydate;
        var form = layui.form;
        var upload = layui.upload;
        var table = layui.table;
        form.render();
        form.on('submit(loading)', function (data) {
            var ids=[];
            var formData = customsClearanceFn.getPageData();
            ids=formData;
            console.info(JSON.stringify(formData.data));
            if(formData.length>0){
                for(var i in formData){
                    ids.push(formData[i].ids);
                }
               alert(ids);
            }
          /*console.log(formData.data);*/
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
                     file.endsWith(".JPEG") || file.endsWith(".PNG") || file.endsWith(".GIF"))) {
                     $('#test1').focus();
                     layer.tips('图片文件格式不对!(后缀名如:jpg,png,jpeg,gif)', '#picSrc', {tips: [2, '#3595CC'], time: 3000});
                     return false;
                 }
            } else {
                url = 'update';
            }
          /*  {ids:JSON.stringify(formData.data)}*/
            var queryStr = "${queryStr}";
           var ids= JSON.stringify(formData.data);
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/sku/" + url,
                data:JSON.stringify(formData.data),
                processData: false,
                traditional:true,//必须加上设置为true
                dataType: "json",
                contentType:'application/json;charset=utf-8',
                success: function (reply) {
                    if (reply.flag) {
                        location.href = "${pageContext.request.contextPath }/sku/list?" + queryStr;
                    }
                }
            });
            return false;
        });

            upload.render({
                elem: '#test1'
                ,url: '${pageContext.request.contextPath }/sku/create1'
                ,auto: true //选择文件后不自动上传
                , bindAction: '#loading' //指向一个按钮触发上传
                ,before: function(obj){
                    layui.load;
                    //预读本地文件示例，不支持ie8
                    obj.preview(function(index, file, result){
                        $('#imgCss').attr('src', result);
                    });
                }
                ,done: function(res){
                    //如果上传失败
                    if(res.code > 0){
                        alert('上传失败！')
                    }
                    //上传成功
                    alert('上传成功！')
                }
            });




    });

</script>

<script id="variationListTable-template"  type="text/x-handlebars-template">
    <tr id="{{sku}}" data-ucSku="{{ucSku}} "
        {{#if quote}}
        data-quote="{{quote}}"
        {{/if}}>
    <td class="f-left p-left15" style="word-break:break-all;">
        <p class="m-bottom10">SKU：
            <input class="form-component w249 sku" type="text" value="{{sku}}" page-sku="{{sku}}" init-sku="{{sku}}" page-sourceurl="{{sourceUrl}}" onkeyup="value=value.replace(/[^a-zA-Z0-9-_+xX*#]*/g,'');" onblur="customsClearanceFn.skuInfoTableUpdete(this);">
        </p>
        <p page-name="{{proName}}">
            {{#if proName}}
            {{proName}}
            {{/if}}
            {{#unless proName}}
            --
            {{/unless}}
        </p>
    </td>
    <td style="width:150px;min-width:150px;max-width:150px;">
        <p class="gray-c variation-item-text" style="word-break:break-all;">
            {{#each variationListItem}}
            <span variation-name="{{name}}" variation-value="{{value}}">{{text}}</span><span>*</span>
            {{/each}}
        </p>
    </td>
    <td class="action" style="width:30px;min-width:30px;max-width:30px;">
        <a class="removeVariationListItem" href="javascript:"><i class="layui-icon">&#xe640;</i></a>
    </td>
    </tr>
</script>

<script id="variationListTable-template" type="text/x-handlebars-template">
    <tr id="{{sku}}" data-ucSku="{{ucSku}}"
        {{#if quote}}
        data-quote="{{quote}}"
        {{/if}}>

    <td class="f-left p-left15" style="word-break:break-all;">
        <p class="m-bottom10">SKU：
            <input class="form-component w249 sku" type="text" value="{{sku}}" page-sku="{{sku}}" init-sku="{{sku}}"
                   page-sourceurl="{{sourceUrl}}" onkeyup="value=value.replace(/[^a-zA-Z0-9-_+xX*#]*/g,'');"
                   onblur="customsClearanceFn.skuInfoTableUpdete(this);">
        </p>
        <p page-name="{{proName}}">
            {{#if proName}}
            {{proName}}
            {{/if}}
            {{#unless proName}}
            --
            {{/unless}}
        </p>
    </td>
    <td style="width:150px;min-width:150px;max-width:150px;">
        <p class="gray-c variation-item-text" style="word-break:break-all;">
            {{#each variationListItem}}
            <span variation-name="{{name}}" variation-value="{{value}}">{{text}}</span><span>*</span>
            {{/each}}
        </p>
    </td>
    <td class="action" style="width:30px;min-width:30px;max-width:30px;">
        <a class="removeVariationListItem" href="javascript:"><i class="layui-icon layui-icon-close-fill"
                                                                 style="font-size: 30px;"></i></a>
    </td>
    </tr>
</script>


</html>