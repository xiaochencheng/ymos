/*
* [project] @新版商品管理添加多变种
* [describe] @SKU列表生成 addVerationFn
*         -- @sku高级生成 SKUAdvancedCreate
*         -- @商品报关列表批量应用 customsClearanceFn
* [Author] @songpengyuan & penglei
* [Date] @2017/12/15
*
*
* */
var requestUrl = gitWinLocHref();

// 常用dom节点及数据
var pageDom = {
    varaitionObj : {},
    varaitionTab : $('table.variationListTable'),
    varaitionListOut : $('div.variationListOut'),
    varaitionAddTab : $('table.variationAddTable'),
    varaitionAdd : $('div.content div.variationAdd'),
    priceModule : $('div[uid="priceModule"]'),
    uniqueType :$('#uniqueType')
};



/*
 *  [One]多变种商品创建 ↓
 */
var htmlStrObj = {
    varationAddContent : '<tr class="varation_add_content varationAddContent" data-name="">'+
        '<td class="w250"><input type="text" class="variationName form-component " autocomplete="off" placeholder="属性名称，如：Color"></td>'+
        '<td>'+
        '<div class="variation_attr_add w450" data-id="variationAttrAdd">'+
        '<input class="variationValue " type="text" placeholder="属性值，如：Red、Blue">'+
        '<a class="variation_value_delect gray-b removeVariation" href="javascript:" >'+
        '<i class="layui-icon layui-icon-delete" style="font-size: 22px;"></i>'+
        '</a>'+
        '</div>'+
        '<a href="javascript:" class="remove_option removeOption">'+
        '<i class="layui-icon layui-icon-close-fill" style="font-size: 30px;"></i>'+
        '</a>'+
        '</td>'+
        '</tr>',
    tagHtml : '<span class="one_tag oneTag">' +
        '<span class="tag_name" data-uid="tagName" data-value=""></span>' +
        '<a href="javascript:" class="remove_tag" data-uid="removeTag">	<i class="layui-icon layui-icon-close" style="font-size: 14px;"></i></a>' +
        '</span>'
};
var addVerationFn = (function ($,W,D) {
    var verationData,
        spu,
        masterImg,
        proName;
    var imgAllData = [];//重新生成变种信息是替换imgSrc

    $(D).on('blur','#varListDiv input.variationName',function () {
        var n = 0,
            thisVal = $.trim($(this).val());
        $('#varListDiv input.variationName').each(function(){
            if (thisVal && $.trim($(this).val()) == thisVal) {
                n++;
            }
        });
        if (thisVal && n>1) {
            $(this).val('');
            $.fn.message({type:'error',msg:'属性名[ '+ thisVal +' ]已存在'});
        }
    });

    //中文名输入框失去焦点时
    $(D).off('blur','#proName');
    $(D).on('blur','#proName',function () {
        var val =  $.trim($(this).val());
        $('#variantProductTable [page-name]').html(val ?  val  : '--' );
    });



    //初始化变种
    var initVariation =  function () {
        $('table.variationListTable>tbody tr:not([data-quote="true"])').remove();//清空列表
        masterImg = $('#masterImg').attr('src');
        // proName = $('#proName').val().trim();
        spu = $.trim($('#spuName').val());

    };

    $(D).off('keyup','input.variationName');
    $(D).on('keyup','input.variationName',function () {
        var $this = $(this);
        $this.attr('data-name',$this.val());
    });
    //添加属性btn点击
    $(D).off('click','a.addAnotherOption');
    $(D).on('click','a.addAnotherOption',function(){
        var table = $(this).closest('.variationAdd').find('.variationAddTable');
        var num = table.find('tr.varationAddContent').length;
        if(num < 5){
            var newTr = $(htmlStrObj.varationAddContent);
            table.append(newTr);
            //addAutocomplete(newTr.find('input.variationName'));
            newTr.find('input.variationName').focus();
        }else{
            $.fn.message({type:"error",msg:"最多添加五个变种属性"});
        }
    });
    //删除属性a点击
    $(D).off('click','a.removeOption');
    $(D).on('click','a.removeOption',function(){
        $(this).closest('tr').remove();
        initVariation();
        variationListBuild('');
    });
    //变种option外层Div点击事件
    $(D).off('click','[data-id="variationAttrAdd"]');
    $(D).on('click','[data-id="variationAttrAdd"]',function(){
        var $this = $(this);
        $(this).find('input[type="text"]').focus();
        !$this.hasClass('active') && $this.addClass('active');

    });

    var variationOptionValGenerate = function(iptVal,obj){
        var hasTag = [],repeatTag = '',arr = [],newTagHtml = '';
        $(obj).find('[data-uid="tagName"]').each(function(){
            hasTag.push(ucToStr($(this).attr('data-value')));
        });
        var newArr = iptVal.replace('，',',').split(',');
        if(newArr.length > 0){
            $.each(newArr,function(i,j){
                if(j){
                    if($.inArray(j,hasTag) == -1){
                        arr.push(j);
                    }else{
                        repeatTag = strMosaic(repeatTag,j)
                    }
                }
            });
        }
        if(repeatTag){
            $.fn.message({type:"error",msg:"该属性值[ "+repeatTag+" ]已存在, 请输入其他属性值"});
            return;
        }
        if(arr.length > 0){
            newTagHtml = tagHtmlBuild(arr);
        }
        if(newTagHtml){
            $(obj).find('input[type="text"]').before(newTagHtml);
        }
    };
    // $(D).off('blur','[data-id="variationAttrAdd"] input');
    // $(D).on('blur','[data-id="variationAttrAdd"] input',function(event){
    // getPageAllUrl();

    // var iptVal = $.trim($(this).val());
    // var outDiv = $(this).closest('[data-id="variationAttrAdd"]');
    // if(iptVal){
    // if(isContainChinese(iptVal)){
    // $.fn.message({type:'error',msg:'属性值不能为中文'});
    // return;
    // }
    // var skuLength = 1;
    // $('#varListDiv [data-id="variationAttrAdd"]').each(function(){
    // var $this = $(this),
    // oneTagLength = $this.find('.oneTag').length;
    // if(oneTagLength != 0)skuLength = skuLength*oneTagLength;
    // });
    // if(skuLength > 200){
    // $.fn.message({type:'error',msg:'多属性sku一次最多支持创建200个'});
    // $(this).val('');
    // return;
    // }
    // variationOptionValGenerate(iptVal,outDiv);
    // $(this).val('');
    // initVariation();
    // variationListBuild('');
    // }
    // $('[data-id="variationAttrAdd"]').removeClass('active');
    // });
    //变种属性值输入框回车和‘,’号事件
    // $(D).off('change','#spuName');
    // $(D).on('change','#spuName',function(event){
    // $.fn.message({type:'error',msg:'不能为中文'});
    // // getPageAllUrl();
    // // variationOptionValGenerate(iptVal,outDiv);
    // // $(this).val('');
    // // initVariation();
    // // variationListBuild('');

    // });

    // $("#spuName").change(function(){
    // $.fn.message({type:'error',msg:'不能为中文'});
    // });

    // //变种属性值输入框回车和‘,’号事件
    // $(D).off('onchange','.spuName select');
    // $(D).on('onchange','.spuName select',function(event){
    // $.fn.message({type:'error',msg:'属性值不能为中文'});

    // });

    //变种属性值输入框回车和‘,’号事件
    $(D).off('keyup','[data-id="variationAttrAdd"] input');
    $(D).on('keyup','[data-id="variationAttrAdd"] input',function(event){
        var keyCode = event.which || W.event.which;
        var iptVal = $.trim($(this).val());
        var outDiv = $(this).closest('[data-id="variationAttrAdd"]');
        /*if(keyCode == 27){//esc键
         $(this).blur();
         }*/

        if(iptVal){
            // if(isContainChinese(iptVal)){
            //     $.fn.message({type:'error',msg:'属性值不能为中文'});
            //     return;
            // }
            var skuLength = 1;
            $('#varListDiv [data-id="variationAttrAdd"]').each(function(){
                var $this = $(this),
                    oneTagLength = $this.find('.oneTag').length;
                if(oneTagLength != 0)skuLength = skuLength*oneTagLength;
            });
            if(skuLength > 200){
                $.fn.message({type:'error',msg:'多属性sku一次最多支持创建200个'});
                $(this).val('');
                return;
            }
            if(keyCode == 13 || keyCode == 188){//回车，‘,’键
                var  spu = $('#spuName').val();
                var  vName = $('.variationName').val();
                if(spu==''||spu==null) {
                    $.fn.message({type: "error", msg: "请选择SPU"});
                    return;
                }
                if(vName==''||vName==null) {
                    $.fn.message({type: "error", msg: "请填写属性名"});
                    return;
                }
                // if(isContainChinese(iptVal)){
                //     $.fn.message({type:'error',msg:'属性值不能为中文'});
                //     return;
                // }
                var skuLength = 1;
                $('#varListDiv [data-id="variationAttrAdd"]').each(function(){
                    var $this = $(this),
                        oneTagLength = $this.find('.oneTag').length;
                    if(oneTagLength != 0)skuLength = skuLength*oneTagLength;
                });
                if(skuLength > 200){
                    $.fn.message({type:'error',msg:'多属性sku一次最多支持创建200个'});
                    $(this).val('');
                    return;
                }
                getPageAllUrl();
                variationOptionValGenerate(iptVal,outDiv);
                $(this).val('');
                initVariation();
                variationListBuild('');
            }
        }
    });

    //回退键事件
    $(D).off('keydown','[data-id="variationAttrAdd"] input');
    $(D).on('keydown','[data-id="variationAttrAdd"] input',function(event){
        var keyCode = event.which || W.event.which;
        var iptVal = $.trim($(this).val());
        if(!iptVal && keyCode == 8){
            getPageAllUrl();

            $(this).prev().remove();
            initVariation();
            variationListBuild('');
        }
    });

    $(D).on('click','[data-uid="removeTag"]',function(){
        getPageAllUrl();
        $(this).closest('.oneTag').remove();
        initVariation();
        variationListBuild('');
    });

    //属性值清空
    $(D).on('click','.removeVariation',function(){
        $(this).closest('[data-id="variationAttrAdd"]').find('.oneTag').remove();
        initVariation();
        variationListBuild('');
    });

    //生成的变种列表中的tr移除
    $(D).off('click','.removeVariationListItem');
    $(D).on('click','.removeVariationListItem',function () {
        var variationOptionData =  getVariationOption(),
            $itemTr = $(this).closest('tr'),
            trId = $itemTr.attr('id'),
            $itemVariationName = $itemTr.find('[variation-name]');

        $itemTr.remove();
        $itemVariationName.each(function () {
            var itemVariationName = $(this).attr('variation-name');
            var itemVariationValue = $(this).attr('variation-value');

            var $variationListTable = $('#variantProductTable');
            var itemVariationNameLength = $variationListTable.find('[variation-name="'+ itemVariationName +'"]').length,
                itemVariationValueLength = $variationListTable.find('[variation-value="'+ itemVariationValue +'"][variation-name="'+ itemVariationName +'"]').length;

            var varationAddContentTr = $('#varListDiv').find('input.variationName[data-name="'+ itemVariationName +'"]').closest('tr');

            if (itemVariationNameLength == 0) {
                // 清空生成变种列表
                varationAddContentTr.find('.oneTag').remove();
            }
            // 变种列表 value 是否以不存在
            if( itemVariationValueLength == 0){
                varationAddContentTr.find('.oneTag [data-value="'+ itemVariationValue +'"]').closest('.oneTag').remove();
            }

        });

        // 商品信息&报关信息列表移除操作
        $('#skuGoodsList [listid="'+ trId +'"] , #skuBgList [listid="'+ trId +'"]').remove();
        if($('#skuBgList>tbody').find('tr').length == 0){
            $('#skuGoodsList , #skuBgList').hide();
        }

    });

    //生成返回属性值tagHtml
    var tagHtmlBuild = function(arr){
        var tagDom = $('<div></div>');
        $.each(arr,function(i,j){
            if(j){
                var newstr = $(htmlStrObj .tagHtml);
                newstr.find('[data-uid="tagName"]').attr('data-value',strToUc(j)).html(j);
                newstr.find('[data-uid="tagName"]').attr('data-text', j).html(j);
                tagDom.append(newstr);
            }
        });
        return tagDom.html();
    };
    var strMosaic = function(str1,str2){
        str1 == '' ? str1 = str2 : str1 += ',' +str2;
        return str1;
    };
    var getVariationOption = function(){
        var variationObj = $('tr.varationAddContent');
        var obj = {};
        $(variationObj).each(function(){
            var name = $.trim($(this).find('input.variationName').val());
            if(name && !obj[name]){
                var arr = [];
                $(this).find('.oneTag').each(function(){
                    arr.push($(this).find('[data-uid="tagName"]').attr('data-text'));
                });
                if(arr.length > 0){
                    obj[name] = arr;
                }
            }
        });
        pageDom.varaitionObj = obj;
        return obj;
    };

    //取变种数据
    var getInventoryVariationData = function(){
        var arr = [];
        var table = pageDom.varaitionTab;
        table.find('tr[class="oneVariationInfo"]').each(function(){
            var obj = {};
            var trType = $(this).attr('data-type');
            // if(trType && trType == 'newAdd' && !$(this).attr('variationid'))return;
            // obj.trId = $(this).attr('trId');
            // var imgUrls = $(this).find('td[data-name="img"]').data('value');
            // obj.imgUrls = imgUrls ? imgUrls : '';
            // obj.sku = $.trim($(this).find('td[data-name="sku"] input').val());
            // obj.cost = $.trim($(this).find('td[data-name="cost"] input').val());
            // obj.retailPrice = $.trim($(this).find('td[data-name="retailPrice"] input').val());
            // obj.quantity = $.trim($(this).find('td[data-name="quantity"] input').val());
            // obj.id = $(this).attr('variationId') ? $(this).attr('variationId') : 0;
            // var optionAndValueObj = {};
            $(this).find('td[data-name="options"]').each(function(){
                if($(this).find('input').length > 0){
                    optionAndValueObj[$(this).attr('variationName')] = $(this).find('input').val();
                }else {
                    optionAndValueObj[$(this).attr('variationName')] = ucToStr($(this).attr('variationValue'));
                }
            });
            obj.optionAndValue = JSON.stringify(optionAndValueObj);
            arr.push(obj);
        });
        return arr;
    };

    //变种信息渲染到页面
    var variationListHtml = function(str,k){
        var variationListData = {
            proName: proName,
            masterImg: masterImg,
            spu: spu,
            sku: '',
            ucSku:'' ,
            variationListItem: []
        };
        var arr = str.split('$^$');
        var textArr = [];
        $.each(arr,function(i,j){
            var obj = j.split('%^%');
            var item = {
                name : obj[0],
                value :  obj[1],
                text :  ucToStr(obj[1])
            };
            textArr.push(item.text);
            variationListData.variationListItem.push(item);
        });
        if(spu){
            var itemsku = spu +'-'+ (k+1);
            variationListData.sku = itemsku ? itemsku.replace(/[^a-zA-Z0-9-_+xX*#]*/g,'') : '';
            variationListData.ucSku = strToUc(itemsku);
        }

        var table = $('.variationListTable>tbody');
        var myTemplate = Handlebars.compile($("#variationListTable-template").html());
        //将json对象用刚刚注册的Handlebars模版封装，得到最终的html，插入到基础table中。
        table.append(myTemplate(variationListData));
    };

    //递归解二维数组
    var recursiveSolutionArr = function(arr,idx,node,len){
        var str = '', i,obj;
        $.each(arr[idx],function(i,l){
            obj = l;
            if(idx + 1 == len){
                if (node == ''){
                    variationListHtml(obj.name+'%^%'+obj.value,i);
                }else{
                    obj = node+'$^$'+obj.name+'%^%'+obj.value;
                    variationListHtml(obj,i);
                }
            }else{
                if (node == ''){
                    recursiveSolutionArr(arr,idx+1,(obj.name+'%^%'+obj.value),len);
                }else{
                    recursiveSolutionArr(arr, idx+1,(node+'$^$'+obj.name+'%^%'+obj.value),len);
                }
            }
        });
    };

    //生成变种属性列表
    var variationListBuild = function(sourceUrl){
        var obj = getVariationOption();
        if(!isEmptyObject(obj)){//判断是不是空对象
            var arr = [];
            $.each(obj,function(i,j){
                var listArr = [];
                $.each(j,function(k,l){
                    listArr.push({name:i,value:l});
                });
                arr.push(listArr);
            });
            if(arr.length > 0){
                // 走递归 渲染数据到页面
                recursiveSolutionArr(arr,0,'',arr.length);

                //图片地址回填
                $.each(imgAllData,function (i,j) {
                    $('[data-ucSku="'+ j.ucSku +'"]').find('img').attr('src',j.url).closest('.imgDivIn').attr('page-imgurl',j.url);
                })
            }
        }else{
            // pageDom.priceModule.show();
            // variationList.hide();
        }
        //生成报关信息列表
        customsClearanceFn.buildBgTable(sourceUrl);
    };

    // 获取页面变种信息sku对应的的图片url
    var getPageAllUrl = function () {
        $('#variantProductTable tr').each(function (i,j) {
            var $this = $(this);
            var item = {
                ucSku: $this.attr('data-ucSku'),
                url : $this.find('img').attr('src')
            };
            imgAllData.push(item);
        });
    };

    //通过平台查询店铺
    function queryShop(platform){
        var url = 'product/queryShopByPlatform.json';
        $("#quoteModal").modal("show");
        $.ajax({
            type: "POST",
            url: url,
            data: {
                "platform":platform
            },
            dataType: "json",
            success: function(data){
                var shop = data.shop;
                var optVal ='';
                if(shop!=''){
                    $.each(shop,function (index,value) {
                        if(index>=0){
                            optVal = "<option value='"+value.id+"'>"+value.name+"</option>";
                            $('#shopId').append(optVal);
                        }
                    });
                }
            }
        });
    }

    //根据平台查询商品信息
    function queryProduct(platform){
        var url = 'product/queryShopByPlatformList.htm';
        $("#quoteModal").modal("show");
        $.ajax({
            type: "POST",
            url: url,
            data: {
                "platform":platform,
                "pageNo":1,
                "pageSize":50,
                "shopId":$('#shopId').val(),
                "searchType":$('#searchType').val(),
                "searchValue":$('#searchValue').val()
            },
            dataType: "html",
            success: function(data){
                $('#pageList').empty();
                $('#pageList').html(data);
                document.getElementById('pageList').scrollTop = 0;
                $('#loading').modal('hide');
            }
        });
    }

    //通过平台查询站点
    function querySite(platform){
        $('#site').empty();
        var opt= "<option value='-1' selected='selected'>选择站点</option>";
        $('#site').append(opt);
        var url = 'product/querySiteByPlatform.json';
        $("#quoteModal").modal("show");
        $.ajax({
            type: "POST",
            url: url,
            data: {
                "platform":platform
            },
            dataType: "json",
            success: function(data){
                var site = data.site;
                var optVal ='';
                if(site!=''){
                    $.each(site,function (index,value) {
                        if(index>=0){
                            optVal = "<option value='"+value.siteId+"'>"+value.siteName+"</option>";
                            $('#site').append(optVal);
                        }
                    });
                }
            }
        });
    }

    //通过平台查询站点
    function queryAmazonSite(platform){
        $('#amazonSite').empty();
        var opt= "<option value='-1' selected='selected'>选择站点</option>";
        $('#amazonSite').append(opt);
        var url = 'product/queryAmazonSite.json';
        $("#quoteModal").modal("show");
        $.ajax({
            type: "POST",
            url: url,
            data: {
                "platform":platform
            },
            dataType: "json",
            success: function(data){
                var site = data.site;
                var optVal ='';
                if(site!=''){
                    $.each(site,function (key) {
                        optVal = "<option value='"+key+"'>"+site[key]+"</option>";
                        $('#amazonSite').append(optVal);
                    });
                }
            }
        });
    }
    return{
        //(引用产品)模块事件,默认是wish平台产品信息
        addProduct:function (){
            $('#wishProduct').addClass('form-control btn-myj-primary');
            $('#smtProduct').removeClass('btn-myj-primary');
            $('#ebayProduct').removeClass('btn-myj-primary');
            $('#amazonProduct').removeClass('btn-myj-primary');
            $('#shopId').empty();
            var optVal = "<option value='-1' selected='selected'>全部店铺</option>";
            $('#shopId').append(optVal);
            $('#searchValue').val('');
            $('#searchValue').attr('placeholder','按 parentSKU 搜索');
            $('#searchType').css('display','');
            $('#site').css('display','none');
            $('#ebayTitle').css('display','none');
            $('#amazonTitle').css('display','none');
            $('#amazonSite').css('display','none');
            $('#AmazonStock').css('display','none');
            $('#skuCode').css('display','none');
            $('#yunShuType').css('display','none');
            $('#onlineType').css('display','none');
            $('#productStatusType').css('display','none');
            $('#sellType').css('display','none');
            var platform = 'wish';
            queryShop(platform);
            $("#quoteModal").modal("show");
            var url = "product/productList.htm";
            $.ajax({
                type: "POST",
                url: url,
                data: {
                    "pageNo":1,
                    "pageSize":50,
                    "platform":platform,
                    "searchType":$('#searchType').val(),
                    "searchValue":$('#searchValue').val()
                },
                dataType: "html",
                success: function(data){
                    $('#pageList').empty();
                    $('#pageList').html(data);
                    document.getElementById('pageList').scrollTop = 0;
                    $('#loading').modal('hide');
                }
            });
        },
        //单击事件
        dj: function(dom,platform) {
            var btn = $('button[name="product"]');
            $.each(btn, function () {
                $(this).removeClass("form-control btn-myj-primary");
                $(this).addClass("form-control");
            });
            $(dom).removeClass("form-control");
            $(dom).addClass("form-control btn-myj-primary");
            if(platform=='wish'){
                $('#searchValue').val('');
                $('#searchValue').attr('placeholder','按 parentSKU 搜索');
                $('#searchType').css('display','');
                $('#shopId').empty();
                $('#searchType').empty();
                $('#shopId').append("<option value='-1' selected='selected'>全部店铺</option>");
                $('#searchType').append("<option value='0' selected='selected'>ParentSKU</option>+<option value='1'>SKU</option>+<option value='2'>标题</option>+<option value='3'>产品ID</option>");
                $('#site').css('display','none');
                $('#ebayTitle').css('display','none');
                $('#amazonTitle').css('display','none');
                $('#skuCode').css('display','none');
                $('#amazonSite').css('display','none');
                $('#AmazonStock').css('display','none');
                $('#yunShuType').css('display','none');
                $('#onlineType').css('display','none');
                $('#productStatusType').css('display','none');
                $('#sellType').css('display','none');
                $('#platform').val(platform);
                queryShop(platform);
                queryProduct(platform);
            }else if(platform=='smt'){
                $('#searchValue').val('');
                $('#searchValue').attr('placeholder','按商品编码搜索');
                $('#skuCode').css('display','');
                $('#productStatusType').css('display','');
                $('#shopId').empty();
                $('#skuCode').empty();
                $('#productStatusType').empty();
                $('#shopId').append("<option value='-1' selected='selected'>全部店铺</option>");
                $('#skuCode').append("<option value='0' selected='selected'>商品编码</option>+<option value='1'>标题</option>+<option value='2'>产品ID</option>");
                $('#productStatusType').append("<option value='' selected='selected'>在线状态</option>+<option value='onSelling'>在线</option>+<option value='offline'>已下架</option>+<option value='auditing'>审核中</option>+<option value='editingRequired'>审核不通过</option>");
                $('#site').css('display','none');
                $('#sellType').css('display','none');
                $('#ebayTitle').css('display','none');
                $('#searchType').css('display','none');
                $('#amazonSite').css('display','none');
                $('#yunShuType').css('display','none');
                $('#AmazonStock').css('display','none');
                $('#onlineType').css('display','none');
                $('#amazonTitle').css('display','none');
                $('#platform').val(platform);
                queryShop(platform);
                queryProduct(platform);
            }else if(platform=='ebay'){
                $('#searchValue').val('');
                $('#searchValue').attr('placeholder','按标题搜索');
                $('#site').css('display','');
                $('#sellType').css('display','');
                $('#ebayTitle').css('display','');
                $('#onlineType').css('display','');
                $('#site').empty();
                $('#shopId').empty();
                $('#sellType').empty();
                $('#ebayTitle').empty();
                $('#onlineType').empty();
                $('#shopId').append("<option value='-1' selected='selected'>全部店铺</option>");
                $('#site').append("<option value='-1' selected='selected'>选择站点</option>");
                $('#sellType').append("<option value='0'>售卖形式</option>+<option value='1' >拍卖</option>+<option value='2' >固定</option>+ <option value='3' >多属性</option>");
                $('#ebayTitle').append("<option value='1' selected='selected'>标题</option>+<option value='2'>ItemID</option>+<option value='3'>SKU</option>+<option value='4'>subSKU</option>");
                $('#onlineType').append("<option value=''>在线状态</option>+<option value='Active'>在线</option>+<option value='Completed'>已售</option>+<option value='End'>未卖出</option>");
                $('#amazonTitle').css('display','none');
                $('#skuCode').css('display','none');
                $('#AmazonStock').css('display','none');
                $('#searchType').css('display','none');
                $('#yunShuType').css('display','none');
                $('#amazonSite').css('display','none');
                $('#productStatusType').css('display','none');
                $('#platform').val(platform);
                queryShop(platform);
                querySite(platform);
                queryProduct(platform);
            }else if(platform=='amazon'){
                $('#searchValue').val('');
                $('#searchValue').attr('placeholder','按标题搜索');
                $('#amazonSite').css('display','');
                $('#yunShuType').css('display','');
                $('#amazonTitle').css('display','');
                $('#AmazonStock').css('display','');
                $('#shopId').empty();
                $('#amazonSite').empty();
                $('#yunShuType').empty();
                $('#amazonTitle').empty();
                $('#AmazonStock').empty();
                $('#shopId').append("<option value='-1' selected='selected'>全部店铺</option>");
                $('#amazonSite').append("<option value='-1' selected='selected'>选择站点</option>");
                $('#yunShuType').append("<option value=''>运输方式</option> + <option value='AFN' >FBA</option> + <option value='MFN' >FBM</option>");
                $('#amazonTitle').append("<option value='1'>标题</option> + <option value='2'>SKU</option> + <option value='3'>ASIN</option>");
                $('#AmazonStock').append("<option value=''>在线状态</option> + <option value=' Active '>有库存</option> + <option value='Completed'>无库存</option>");
                $('#site').css('display','none');
                $('#onlineType').css('display','none');
                $('#ebayTitle').css('display','none');
                $('#skuCode').css('display','none');
                $('#searchType').css('display','none');
                $('#sellType').css('display','none');
                $('#productStatusType').css('display','none');
                $('#platform').val(platform);
                queryShop(platform);
                queryAmazonSite(platform);
                queryProduct(platform);
            }
        },
        //搜索功能
        searchItn: function (){
            var platform = $('#platform').val();
            var searchType ='';
            var site = '';
            var amazonSite = '';
            var AmazonStock = '';
            var onlineType ='';
            var yunshuType ='';
            var sellType ='';
            var productStatusType = '';
            if(platform=='wish'){
                searchType = $('#searchType').val();
            }else if(platform=='smt'){
                searchType = $('#skuCode').val();
                productStatusType = $('#productStatusType').val();
            }else if(platform=='ebay'){
                site = $('#site').val();
                onlineType = $('#onlineType').val();
                searchType = $('#ebayTitle').val();
                sellType = $('#sellType').val();
            }else if(platform=='amazon'){
                amazonSite = $('#site').val();
                yunshuType = $('#yunShuType').val();
                searchType = $('#amazonTitle').val();
                AmazonStock = $('#AmazonStock').val();
            }
            var shopId = $('#shopId').val();
            var searchValue = $('#searchValue').val();
            var url= "product/queryShopByPlatformList.htm";
            $('#loading').modal('show');
            $.ajax({
                type:"POST",
                url:url,
                data:{
                    "pageNo":1,
                    "pageSzie":100,
                    "site":site,
                    "shopId":shopId,
                    "platform":platform,
                    "onlineType":onlineType,
                    "sellType":sellType,
                    "amazonSite":amazonSite,
                    "AmazonStock":AmazonStock,
                    "searchType":searchType,
                    "searchValue":searchValue,
                    "switchFulFillMentTo":yunshuType,
                    "productStatusType":productStatusType
                },
                dataType:"html",
                success:function(data){
                    $('#pageList').html(data);
                    document.getElementById('pageList').scrollTop = 0;
                    $('#loading').modal('hide');
                }
            });
        },
        //引用产品时触发生成变种信息事件
        triggrtQuoteProductPushData:function(sourceUrl){
            initVariation();
            variationListBuild(sourceUrl);
        },
        //引用产品数据回填
        quoteProductPushData: function (quoteData) {

            var myTemplate = Handlebars.compile($("#variationListTable-template").html());

            $.each(quoteData.dxmCommodityProductList,function (i,v) {
                var ucSku = strToUc(v.sku);
                if($('#variantProductTable tbody').find('[data-ucSku="'+ ucSku +'"]').length == 0){
                    var itemObj = {
                        quote : true,
                        sku: v.sku ? v.sku.replace(/[^a-zA-Z0-9-_+xX*#]*/g,'') : '',
                        ucSku: ucSku,
                        masterImg: (v.imgUrl.indexOf('http://') > -1 || v.imgUrl.indexOf('https://') > -1) ? v.imgUrl : 'http://' + v.imgUrl ,
                        name : v.name
                    };
                    //将json对象用刚刚注册的Handlebars模版封装，得到最终的html，插入到基础table中。
                    $('#variantProductTable tbody').append(myTemplate(itemObj));
                }
            });
        },

        //关闭pageOrModal
        closePageOrModal: function () {
            // window.open('about:blank', '_self');
            // window.location.href='dxmCommodityProduct/index.htm';
            W.close();
        },

        //验证输入字符长度
        checkInputNum: function (obj) {
            var $this = $(obj),
                char = $this.val(),
                charLen = char.length,
                maxNum = $this.attr('maxlength');
            if(charLen > maxNum){
                $this.val(char.substr(0,maxNum));
            }
            $this.closest('span').find('[uid="inputCharNum"]').html(charLen);

        },

        //保存继续创建
        isGoOnCreate: function (that) {
            if ($(that).is(':checked')) {
                $('input[name^=isGoOnCreate]').each(function () {
                    var _self = $(this);
                    _self.prop('checked', true);
                });
                myjStorage.set('is_go_on_create', 1)();
            } else {
                $('input[name^=isGoOnCreate]').each(function () {
                    var _self = $(this);
                    _self.prop('checked', false);
                });
                myjStorage.set('is_go_on_create', 0)();
            }
        }
    }
})(jQuery,window,document);

/*
 *  [Two]多变种商品编辑 ↓
 */
var editVarientFn = (function ($,D,W) {
    return {
        init :function (initData) {
            this.dataPush(initData);
        },
        //接收子页面的数据
        subpageRequests:function (subpageData) {
            var dataIndex = $('#editHiddenInfo').attr('data-index');
            subpageData.dxmCommodityProduct.idStr = subpageData.dxmCommodityProduct.productId;
            initData[dataIndex] = subpageData;

            $('.variationListTable>tbody').empty();
            editVarientFn.dataPush(initData);
        },

        //页面渲染
        dataPush :function (initData) {
            $('#spuInfo').text(initData[0].spu);
            // var variationListData = initData;
            $.each(initData, function (i, value) {
                if (!value.dxmCommodityProduct.imgUrl) {
                    value.dxmCommodityProduct.imgUrl = '/static/img/kong.png';
                } else {
                    value.dxmCommodityProduct.imgUrl = getTinyPicRealUrl(value.dxmCommodityProduct.imgUrl);
                }
            });
            var table = $('.variationListTable>tbody');
            var myTemplate = Handlebars.compile($("#variationListTable-template").html());
            //将json对象用刚刚注册的Handlebars模版封装，得到最终的html，插入到基础table中。
            table.append(myTemplate(initData));
        },

        //编辑当前变种
        editVariationListItem:function (obj) {
            var $this = $(obj),
                id = ($this.closest('tr').attr('id')),
                trIndex = $this.closest('tr').attr('data-index');
            $('#editHiddenInfo').attr('data-index', trIndex);
            if (id) {
                // var openUrl = 'dxmCommodityProduct/openAddModal.htm?id='+id+'&type=1&editOrCopy=0&fromWhere=variantEdit';
                // W.open(openUrl);
                comOpenWinPost('dxmCommodityProduct/openAddModal.htm', {id: id, type: 1, editOrCopy: 0, fromWhere: 'variantEdit'});
            }
        },

        //移除当前变种
        removeVariationListItem:function (obj) {
            var $this = $(obj),
                id = $this.closest('tr').attr('id');
            if (id) {
                myjAlert.confirm({
                    content: '确定删除?',
                    callback: function (v) {
                        if (v) {
                            $.get(requestUrl + 'dxmCommodityProduct/delCommPro.json', {id: id, delFlag: 0}, function (data) {
                                if(data.code == 0){
                                    $.fn.message({type:"success",msg:"删除成功!"});
                                    var $tr =  $this.closest('tr'),
                                        dataIndex = $tr.attr('data-index');

                                    initData.splice(dataIndex,1);
                                    $('.variationListTable>tbody').empty();
                                    editVarientFn.dataPush(initData);

                                }else{
                                    $.fn.message({type:"error",msg:data.msg});
                                }
                            })
                        }
                    }
                })
            }
        },

        getData :function(){
            // 变种属性列表
            $('#variantProductTable').find('tr').each(function (i,j) {
                var pageUrl  = $(this).find('img').attr('src');
                initData[i].dxmCommodityProduct.imgUrl = pageUrl.indexOf('kong.png') == -1 ? getTinyPicRealUrl(pageUrl) : '';
                initData[i].dxmCommodityProduct.name = $(this).find('[page-name]').attr('page-name');
            });
            return initData;
        },

        saveData:function(){
            var pageData = editVarientFn.getData();
            $.each(pageData, function (i, j) {
                if(!j.dxmCommodityProduct){
                    delete j.dxmCommodityProduct;
                }else{
                    j.dxmCommodityProduct = JSON.stringify(j.dxmCommodityProduct);
                }

                if(!j.dxmProductCustoms){
                    delete j.dxmProductCustoms;
                }else{
                    j.dxmProductCustoms = JSON.stringify(j.dxmProductCustoms);
                }

                if (!j.dxmQualityCheckTemplate) {
                    delete j.dxmQualityCheckTemplate;
                } else {
                    j.dxmQualityCheckTemplate = JSON.stringify(j.dxmQualityCheckTemplate);
                }

                if( !j.warehouseIdList){
                    delete j.warehouseIdList;
                }else{
                    j.warehouseIdList = JSON.stringify(j.warehouseIdList);
                }

                // if (!j.dxmWarehoseList) {
                //     delete j.dxmWarehoseList;
                // } else {
                //     j.dxmWarehoseList = JSON.stringify(j.dxmWarehoseList);
                // }

                if( !j.supplierProductRelationMapList){
                    delete j.supplierProductRelationMapList;
                }else{
                    j.supplierProductRelationMapList = JSON.stringify(j.supplierProductRelationMapList);
                }

                // if( !j.dxmCommodityProductList){
                //     delete  j.dxmCommodityProductList;
                // }else{
                //     j.dxmCommodityProductList = JSON.stringify(j.dxmCommodityProductList);
                // }

                if (!j.dxmProductPacks) {
                    delete j.dxmProductPacks;
                } else {
                    j.dxmProductPacks = JSON.stringify(j.dxmProductPacks);
                }

            });
            $('#loading').modal('show');
            $.post(requestUrl + 'dxmCommodityProduct/editVariant.json', {obj:JSON.stringify(pageData)}, function (data) {
                if (data.msg) {
                    $.fn.message({type: 'error', msg: data.msg});
                } else {
                    $("#productEditVarientModel").customModal('hide');
                    $.fn.message({type: 'success', msg: '编辑多属性成功'});
                    dxmCommodityProduct.reloadPage();
                }
            }).always(function () {
                $('#loading').modal('hide');
            });
        },

        //本地图片选择
        onSelect: function (files) {
            var _this = this;
            for (var i = 0, len = files.length; i < len; i++) {
                var file = files[i],
                    index = file.index,
                    id = files[i].filesId,
                    size = (file.size / 1024),
                    sizeIn = parseFloat(size) > 1024 ? (parseFloat(size) / 1024).toFixed(2) + 'MB' : parseFloat(size).toFixed(2) + 'KB';

                if(parseFloat(size) > 2048) {
                    basicData.localFiles.splice(i, 1);
                    alert('文件'+files[i].name+'大小超出限制！文件最大不能超过2048KB！');
                    continue;
                }
                var URL = window.URL || window.webkitURL,
                    imgURL = URL.createObjectURL(file);
                skuImg()
            }
        },


        //关闭pageOrModal
        closePageOrModal: function () {
            // setTimeout(function () {
            //     W.open('about:blank', '_self');
            //     W.location.href='dxmCommodityProduct/index.htm';
            // },500)
            // W.close();
        }
    }
})(jQuery,document,window);

/*
 *  [Three]生成sku规则 ↓
 */
var SKUAdvancedCreate = (function ($,D,W) {
    //点击高级生成按钮
    $(D).on('click','#setSkuRule',function () {
        SKUAdvancedCreate.expertGenerateSku();
    });

    //折叠菜单
    var zhankai='展开',shouqile='收起';
    $(D).on('click','.setVarietas a.setVarietas-a',function(){
        var dataVal = $(this).attr('data-value');
        var aObj = $('.setVarietas a.setVarietas-a');
        var divId = $(this).data('name');
        var divObj = $('.setVarietas div.setVarietas-word');
        divObj.hide();
        aObj.attr('data-value',0).html(zhankai);
        if(!+dataVal){
            $(this).attr('data-value',1).html(shouqile);
            $('#'+divId).show();
        }
    });

    $(D).on('keyup','#SKUdropdown input',function () {
        SKUAdvancedCreate.spu = $('#SKUdropdown .spuIpt').val();
        SKUAdvancedCreate.previewGeneratingSku();
    });

    return{
        attrArrData: [],//属性名 & 属性值(UC值) 集合
        spu: $('#spu').text().trim(),
        // 获得属性名和相应属性值
        getAttrArrData: function () {
            var _self = this;
            _self.attrArrData = [];
            $('.variationAddTable tr.varationAddContent').each(function (i,j) {
                var name  =  $(this).find('td:first input').val();
                if(name){
                    var $this = $(this);
                    var itemObj = {
                        vName : $(this).find('td:first input').val(),
                        vVal  : []
                    };

                    $this.find('td:last [data-id="variationAttrAdd"] span[data-uid="tagName"]').each(function () {
                        itemObj['vVal'].push({
                            ucVal: $(this).attr('data-value'),
                            val: $(this).text().trim()
                        });
                    });

                    if(itemObj['vVal']){
                        _self.attrArrData.push(itemObj);
                    }
                }
            })
        },
        // 初始化模态层
        expertGenerateSku: function () {
            var _self  =  this;
            var parentSku = $.trim($("#spu").html());
            if(parentSku == null || $('#variantProductTable').find('tr[data-ucsku]').length == 0){
                $.fn.message({type:"error",msg:"变种信息SKU为空，无法使用高级生成"});
                return false;
            }
            // [1]初始化页面
            $('#skuDrag').empty();
            $('.setVarietas').empty();
            $('#SKUdropdown .spuIpt').val(_self.spu);

            // [2] SKU 生成预览(可拖动排序)
            this.getAttrArrData(); //获取属性名和值
            var n = 1;
            $('#skuDrag').append('<li><div class="pcs" number="0" >SKU</div></li>');
            $.each(_self.attrArrData,function (i,j) {
                $('#skuDrag').append('<li><div class="pcs" number="'+ n +'" >'+ j.vName +'</div></li>');
                var  vHtml =
                    '<div class="seting">'
                    +'<div class="vName" data-vname = "'+ j.vName  +'">'+ j.vName  +'<a href="javascript:" class="packUp setVarietas-a" data-name="'+ j.vName + n +'" data-value="0">展开</a></div>'
                    +'<div class="word setVarietas-word" id="'+ j.vName + n +'" style="overflow-y: scroll;">'
                    +'</div>'
                    +'</div>';

                $('.setVarietas').append(vHtml);

                $.each(j.vVal,function(k,l){
                    var wordHtml =
                        '<div class="gainword">'
                        +'<div class="worddfd"><span class="wordCend" title="" data-toggle="tooltip" data-placement="top" data-original-title="'+ l.val  +'">'
                        + l.val
                        +'</span></div>'
                        +'<input type="text" onmessage="" class="colorSku" value="'+ l.val  +'" oldval="'+ l.val +'">'
                        +'</div>';
                    $('#' + j.vName + n).append(wordHtml);
                });
                n++;
            });

            // [3] 设置变种代码
            //拖拽功能
            $('#skuDrag .pcs').off();
            $('#skuDrag').sortable({callback:function () {
                    SKUAdvancedCreate.previewGeneratingSku();
                }});
            //打开设置变种代码 第一个模态层
            $('.seting .vName:first>.packUp').trigger('click');

            _self.previewGeneratingSku();
            $('#SKUdropdown').modal('show');
        },
        // 生成sku主体顺序
        sx: function(){
            var _self = this;
            var arr = [];
            $('#skuDrag .pcs').each(function(){
                if($.inArray(name,arr) == -1){arr.push($(this).attr('number'))}
            });
            if (!_self.spu) {
                $.each(arr,function (i,j) {
                    if( j == '0' ){ arr.splice(i, 1) }
                })
            }
            return arr;
        },
        // 生成 SKU 预览
        previewGeneratingSku: function () {
            var _self = this;
            var newObj = {
                'prdfixVal' : $('.prdfixSkuIpt').val(),//前缀
                'connectorVal' : $('.connectorIpt').val(),//连接符
                'suffixVal' : $('.suffixIpt').val()//后缀
            };

            // 0 获取变种代码数组
            var oldPreViewArr = [ _self.spu ];
            $('.seting input[oldval]').each(function () {
                var $this = $(this),
                    val = $this.val();
                if(val){
                    oldPreViewArr.push(val);
                }else{
                    oldPreViewArr.push($this.attr('oldval'));
                }
            });

            // if(!newObj.connectorVal){newObj.connectorVal = '-';}

            var sxArr = _self.sx(),
                slSku = '';
            // 1 拼接前缀
            if ( newObj.prdfixVal ){ slSku += (newObj.prdfixVal + newObj.connectorVal) }
            // 2 拼接连接符
            $.each(sxArr,function(i,j){
                slSku +=  oldPreViewArr[j];
                if(i+1 != sxArr.length){
                    slSku += newObj.connectorVal;
                }
            });
            // 3 拼接后缀
            if ( newObj.suffixVal ){ slSku += (newObj.connectorVal + newObj.suffixVal) }
            // 4 去除空格
            slSku =slSku.replace(/&quot;/g,'"');
            // 5 生成预览
            $('.showSkuCent').val(unescape(slSku));
        },
        //批量生成高级sku
        batchGeneration: function () {
            var _self = this,
                newObj = { // 高级设置的规则
                    'prdfixVal' : $('.prdfixSkuIpt').val(), //前缀
                    'connectorVal' : $('.connectorIpt').val(),  //连接符
                    'suffixVal' : $('.suffixIpt').val() //后缀
                };

            // 变种代码对应的值
            var variantCodeArr = [];
            $('.seting').each(function () {
                var variantCodeItem = {
                    'vName' : $(this).find('[data-vname]').attr('data-vname'),
                    'vVaule' : []
                };
                $('input[oldval]').each(function () {
                    var $this = $(this);
                    if($this.val()){
                        variantCodeItem.vVaule.push({
                            'oldval' : $this.attr('oldval'),
                            'val' : $this.val()
                        })
                    }
                });
                variantCodeArr.push(variantCodeItem);
            });

            // 获取sku的顺序
            var sxArr = _self.sx();

            // 获取页面每个tr的所有变种属性值
            $('#variantProductTable > tbody > tr').each(function () {
                var $this = $(this),
                    oldVariantCodeArr = [],//原始名字对应的值
                    newSku = '';//sku字符串

                if ( newObj.prdfixVal ) {
                    newSku += (newObj.prdfixVal + newObj.connectorVal);
                }

                $this.find('[variation-name]').each(function (i,j) {
                    var name = $(j).attr('variation-name');
                    oldVariantCodeArr.push({
                        oldName: name,
                        oldVal: ucToStr($(j).attr('variation-value'))
                    });
                });

                // [A]按照高级规则 变种代码替换
                $.each(oldVariantCodeArr,function (m,n) {
                    $.each(variantCodeArr,function(a,b) {
                        if(n.oldName == b.vName){
                            $.each(b.vVaule,function (h,g) {
                                if (n.oldVal == g.oldval) {
                                    n.oldVal = g.val;
                                }
                            })
                        }
                    })
                });

                if(oldVariantCodeArr){
                    var newVariantCodeArr = [];
                    oldVariantCodeArr.unshift( { oldVal: _self.spu } );

                    // [B]按照高级规则 改变顺序
                    $.each(sxArr,function(i,j){
                        newVariantCodeArr[i] = oldVariantCodeArr[j];
                    });
                    // [C]最终生成Sku
                    var flag = true;
                    $.each(newVariantCodeArr,function (i,j) {
                        if(j){
                            newSku += j.oldVal;
                            if(i+1 != newVariantCodeArr.length){
                                newSku += newObj.connectorVal;
                            }
                        }else{
                            flag = false;
                        }
                    });
                    if(newObj.suffixVal){
                        newSku += (newObj.connectorVal + newObj.suffixVal);
                    }
                    if(flag){
                        $this.find('input.sku').val(newSku);
                    }
                }
            });

            // 商品列表&报关列表sku更新
            customsClearanceFn.skuInfoTableUpdete();

            $('#SKUdropdown').modal('hide');
        },
        //关闭模态层
        closeModal: function () {
            $('#skuDrag').empty();
            $('.setVarietas').empty();
            $('#SKUdropdown').modal('hide');
        }
    }
})(jQuery,document,window);

/*
 * [Four]多变种属性生成报关信息列表 ↓
 */
var customsClearanceFn = (function($,D,W){
    return{
        skuArr: [],  // 原始sku与最新的sku数组

        // 获取原始sku与最新的sku数组
        getSkuArr : function(){
            var _self = this;
            _self.skuArr.length = 0;// 数组置空
            $('#variantProductTable tbody tr').each(function(){
                var $this = $(this),
                    $input = $this.find('input.sku');
                var itemObj = {
                    initSku : $input.attr('init-sku'),
                    sku : $.trim($input.val())
                };
                _self.skuArr.push(itemObj);
            });
            return _self.skuArr;
        },

        // 生成sku产品&报关信息列表
        buildBgTable: function (sourceUrl) {
            var _self = this;
            var beforeSkuArr = []; //更新前的sku数组

            //取变化之前的sku
            $('#skuGoodsList tbody tr').each(function () {
                beforeSkuArr.push($(this).attr('listid'));
            });

            var skuArr = _self.getSkuArr();
            if (skuArr.length != 0) {
                $.each(skuArr, function (i, j) {
                    var subI = $.inArray(j.initSku, beforeSkuArr);
                    if(subI == -1){
                        var tableTrHtml = {
                            goodsTrHtml: '<tr listid="' + j.initSku + '">' +
                                '<td class="p5 f-center wrap" data-name="sku">' + j.sku + '</td>' +
                                '<td data-name="name"><textarea class="form-component" rows="2" onblur="customsClearanceFn.changesku(this)"></textarea></td>' +
                                '<td data-name="nameEn"><textarea class="form-component" rows="2" onkeyup="clearCJK(this)"></textarea></td>' +
                                '<td data-name="sourceUrl"><textarea class="form-component" rows="2" value="'+sourceUrl+'">'+sourceUrl+'</textarea></td>' +
                                '<td data-name="sbm"><textarea class="form-component" rows="2" maxlength="50" onkeyup="value=value.replace(/[^0-9a-zA-Z-_]*/g,\'\');"></textarea></td>' +
                                '<td data-name="price"><input class="form-component" type="text" onkeyup="clearNoNum(this);"></td>' +
                                '<td data-name="weight"><input class="form-component" type="text"  onkeyup="clearNoNum(this);"></td>' +
                                '<td class="f-center"><a class="gray-b" href="javascript:" data-toggle="tooltip" data-placement="top"   onclick="customsClearanceFn.copyToTable(this);"><i class="layui-icon layui-icon-form" style="font-size: 30px; " title="应用到所有"></i></a></td></tr>',// 报关tr htmlStr
                            bgTrHtml: '<tr listid="' + j.initSku + '"><td class="p5 f-center wrap " data-name="sku">' + j.sku + '</td>' +
                                '<td data-name="nameCnBg" ><textarea class="form-component" rows="2" data-required></textarea></td>' +
                                '<td data-name="nameEnBg"><textarea class="form-component" rows="2" data-required onkeyup="clearCJK(this)"></textarea></td>' +
                                '<td data-name="weightBg"><input class="form-component" type="text" data-required onkeyup="clearNoNum(this);"></td>' +
                                '<td data-name="priceBg"><input class="form-component" type="text" data-required onkeyup="clearNoNumFour(this);"></td>' +
                                '<td data-name="hgbmBg"><input class="form-component" type="text"></td>' +
                                '<td data-name="dangerDesBg"><select style="display:block;"><option value="0">无</option><option value="1">含电</option><option value="2">液体</option><option value="3">粉末</option><option value="4">纯电</option><option value="5">膏体</option><option value="6">带磁</option></select></td>' +
                                '<td class="f-center"><a class="gray-b" href="javascript:" data-toggle="tooltip" data-placement="top"   onclick="customsClearanceFn.copyToTable(this);"><i class="layui-icon layui-icon-form" style="font-size: 30px;" title="应用到所有"></i></a></td></tr>'
                        };
                        $('#skuGoodsList>tbody').append(tableTrHtml.goodsTrHtml);
                        $('#skuBgList>tbody').append(tableTrHtml.bgTrHtml);
                    }else{
                        $('table.skuInfoTable tbody tr[listid="'+ j.initSku +'"] td[data-name="sku"]').html(j.sku);
                        beforeSkuArr[subI] = null;
                    }
                });
            }

            // 新sku不存在的删除
            if(beforeSkuArr.length != 0){
                $.each(beforeSkuArr,function(k,l){
                    if( l != null){
                        $('table.skuInfoTable tbody tr[listid="'+ l +'"]').remove();
                    }
                });
            }


            $('#skuGoodsList , #skuBgList').css('display', _self.skuArr.length == 0 ? "none" : "block");

            $('[data-toggle="tooltip"]').tooltip();


            //报关信息( 报关中文名 报关英文名 报关价格 报关重量 )有一项有值 其他三项必填
            $(D).off('keyup','#skuBgList [data-required]');
            $(D).on('keyup','#skuBgList [data-required]',function () {
                var $this = $(this),
                    $tr = $this.closest('tr'),
                    $nameCnBg = $tr.find('[data-name="nameCnBg"] textarea'),
                    $nameEnBg = $tr.find('[data-name="nameEnBg"] textarea'),
                    $weightBg = $tr.find('[data-name="weightBg"] input'),
                    $priceBg = $tr.find('[data-name="priceBg"] input');

                if( $nameCnBg.val() || $nameEnBg.val() || $weightBg.val() || $priceBg.val() ){
                    $nameCnBg.attr({
                        'data-required' : ($nameCnBg.val() ? '0' : '1') ,
                        'placeholder' :  ($nameCnBg.val() ? '' : '必填项')
                    });
                    $nameEnBg.attr({
                        'data-required' : ($nameEnBg.val() ? '0' : '1'),
                        'placeholder' :  ($nameEnBg.val() ? '' : '必填项')
                    });
                    $weightBg.attr({
                        'data-required' : ($weightBg.val() ? '0' : '1'),
                        'placeholder' :  ($weightBg.val() ? '' : '必填项')
                    });
                    $priceBg.attr({
                        'data-required' : ($priceBg.val() ? '0' : '1'),
                        'placeholder' :  ($priceBg.val() ? '' : '必填项')
                    });
                }else{
                    $tr.find('[data-required]').attr({
                        'data-required' : '0',
                        'placeholder' : ''
                    });
                }
            });
        },

        changesku:function(obj){
            var $this = $(obj),
                initsku = $this.closest('tr').attr('listid'),
                objVal = $.trim($this.val());
            $('#'+ initsku +' [page-name]').text(objVal ? objVal : '--');
        },
        // 本行应用到表格操作
        copyToTable: function (obj) {
            var $this = $(obj),
                $thisTr = $this.closest('tr'),
                $skuInfoTabletable = $this.closest('table');

            $thisTr.find('td').each(function(){
                var name = $(this).attr('data-name');
                var val = name != 'dangerDesBg' ? $.trim($(this).find('input,textarea').val()) : $(this).find('select option:selected').val();

                $skuInfoTabletable.find('tbody > tr > td').each(function () {
                    if(name && name != 'sku' ){
                        if($(this).attr('data-name') == name){
                            if(name == 'dangerDesBg'){//危险品下拉框处理
                                $(this).find('option[value="'+ val +'"]').prop('selected',true);
                            }else{
                                $(this).find('input,textarea').val(val);
                            }
                        }
                    }
                })
            });
            $('#skuBgList [data-required]').keyup();
        },

        // 更新sku
        skuInfoTableUpdete: function (obj) {
            var _self = this;
            if(obj){
                // [1]失去焦点时 更新sku
                var $this = $(obj),
                    initSku = $this.attr('init-sku'), // 初始sku值
                    newSku =  $.trim($this.val()); // 当前sku
                $('.skuInfoTable > tbody > tr[listid="'+ initSku +'"] > td[data-name="sku"]').html(newSku);
            }else{
                // [2]高级生成时更新sku
                $.each(_self.getSkuArr(),function (i,j) {
                    $('.skuInfoTable tr[listid="'+ j.initSku +'"] td[data-name="sku"]').text(j.sku);
                })
            }
        },

        // 获取两表数据
        getPageData: function () {
            var productInfo = [];
            var flag = true;//true 为验证通过
            if($('table.skuInfoTable [data-required="1"]').length  != 0){
                $.fn.message({type:'error',msg:'必填项请填写完整'});
                flag = false;
                return {flag:flag};
            }
            // Get a list of sku goods information and customs declaration information
            var $skuInfoTable =  $('table.skuInfoTable tbody tr[listid]'),
                skuInfoTrlength = $skuInfoTable.length; //两个表格tr总个数

            $skuInfoTable.each(function(i,j){
                var $this = $(this);

                var imgUrl = $('#variantProductTable tr[id="'+ $this.attr('listid') +'"] img').attr('src');
                if(!imgUrl || imgUrl.indexOf('kong.png') > -1){
                    imgUrl = '';
                }
                var sourceUrl =  $.trim($this.find('[data-name="sourceUrl"] textarea').val());
                if (!sourceUrl || sourceUrl.indexOf('kong.png') > -1) {
                    sourceUrl = '';
                } else {
                    var reg = /(https?):\/\/[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]/;
                    if(!reg.test(sourceUrl)){
                        $.fn.message({type:'error',msg:'来源URL网址不符合要求，输入的网址不是以http:// 或 https:// 开头的URL地址或者包含了特殊字符！'});
                        flag = false;
                        return {flag: flag};
                    }
                    if (sourceUrl.split('|').length > 1) {
                        $.fn.message({type:'error',msg:'来源URL只能填写一个！'});
                        flag = false;
                        return {flag: flag};
                    }
                }
                if(flag){
                    if(skuInfoTrlength/2 > i){  // 商品信息
                        var itemObj = {
                            sku: $this.find('[data-name="sku"]').text(),
                            name: $.trim($this.find('[data-name="name"] textarea').val()),           //"中文名称",
                            nameEn: $.trim($this.find('[data-name="nameEn"] textarea').val()),       //"英文名称",
                            sourceUrl: sourceUrl,  //"来源url",
                            sbm: $.trim($this.find('[data-name="sbm"] textarea').val()),             //"识别码",
                            price: $.trim($this.find('[data-name="price"] input').val()),       //"默认采购价",
                            weight: $.trim($this.find('[data-name="weight"] input').val()),       //"商品重量",
                            imgUrl:  imgUrl   //"",
                        };
                        productInfo.push(itemObj);
                    }else{  //报关列表信息
                        var m = (i - skuInfoTrlength/2 ) == 0 ? 0: (i - skuInfoTrlength/2 );
                        productInfo[m]["nameCnBg"] = $.trim($this.find('[data-name="nameCnBg"] textarea').val());    //"报关中文名",
                        productInfo[m]["nameEnBg"] = $.trim($this.find('[data-name="nameEnBg"] textarea').val());   //"报关英文名",
                        productInfo[m]["weightBg"] = $.trim($this.find('[data-name="weightBg"] input').val());   //"报关重量",
                        productInfo[m]["priceBg"] = $.trim($this.find('[data-name="priceBg"] input').val());    //"报关金额",
                        productInfo[m]["hgbmBg"] = $.trim($this.find('[data-name="hgbmBg"] input').val());     //"海关编码",
                        productInfo[m]["dangerDesBg"] = $this.find('[data-name="dangerDesBg"] select>option:selected').val();//"危险运输品"
                    }
                }
            });
            return {
                flag: flag,
                data: productInfo
            };
        }
    }
})(jQuery,document,window);

/*
 * [five]仓库缓存 local
 * */
var repositoryLocal = (function ($,D,W) {

    // 设置初始化仓库localStroage缓存
    $(D).on('click','.initRepository input[name="warehouse"]',function () {
        var $this = $(this);
        var selectedAll; // 是否全部选中

        // 点击全部仓库
        if($this.val() == 'all'){
            $('.initRepository input[name="warehouse"]').prop('checked',$this.prop('checked') ? true : false);
        }

        if($('.initRepository input[name="warehouse"]:not([value="all"])').length == $('.initRepository input[name="warehouse"]:not([value="all"]):checked').length){
            selectedAll = true
        }else{
            selectedAll = false;
        }
        $('.initRepository input[name="warehouse"][value="all"]').prop('checked', selectedAll);

        //存入缓存
        var repositoryArr = [];
        $('.initRepository input[name="warehouse"]:checked').each(function () {
            repositoryArr.push($(this).val());
        });
        myjStorage.objSet('init_repository',repositoryArr)();
    });

    // 获取初始化仓库localStroage缓存 并渲染页面
    var repositoryData = myjStorage.objGet('init_repository')();
    if(repositoryData){
        $.each(repositoryData,function (i,j) {
            $('.initRepository input[name="warehouse"][value="'+ j +'"]').prop('checked',true);
        });
    }
})(jQuery,document,window);

var IMAGE = (function ($, D, W) {
    $(D).off("change",".uploadFromLocalFiles")
        .on("change",".uploadFromLocalFiles",function(){   //图片上传处理

            var type = $('#hiddenInfo').val(),
                sku = $(this).closest('tr').attr('id'),
                files = this.files;
            if (!type && !sku) {
                return;
            }
            wxPicUpload.uploadFile(files[0],{
                type: 'product',
                isQc: 0,
                productId: 0,
                callback: function(e,d){
                    var imgUrl = getTinyPicRealUrl(d.url);
                    skuImg(sku, imgUrl);
                    //填充变种图片为该图
                    fillImg(imgUrl);
                }
            });

            if(files.length === 0) return;
            // 验证是否容量超限
        });

    // 选中图片回显
    function skuImg(skuId,url){
        var type = $('#hiddenInfo').val();
        if (type) {
            var tplj = getPicRealUrl(url),
                tpl = "<img src='"+tplj+"'  id='masterImg' rel='"+tplj+"' class='imgCss' data-names='auxiliaryImg'>",
                div = $(tpl),
                $addPicImgObj = $('#addPicImg');
            $addPicImgObj.children().remove();
            $addPicImgObj.append(div);
        } else {
            var obj = $('#variantProductTable tr[id="'+skuId+'"]');
            obj.find('img').attr('src',getPicRealUrl(url));
        }
    }
    return {
        uploadImgFromLocal: function (obj, type) {
            $(obj).closest('li').find('.uploadFromLocalFiles').click();
            $('#hiddenInfo').val(type);
        }
    }

})(jQuery, document, window);

// 设置销售方式至少勾选一项
$('.saleStyle').on('click',function(e){
    if($(this).find('input:checked').length < 1){
        $(e.target).prop('checked',true);
    }
});

// 获取页面数据
var getPageData = function () {
    // 销售方式
    var saleProduct = $('#saleProduct').prop('checked') ? '1' : '0',
        giftProduct = $('#giftProduct').prop('checked') ? '1' : '0',
        packProduct = $('#packProduct').prop('checked') ? '1' : '0';

    // 仓库id
    var warehouseIdList = [];
    $('input[name^=warehouse]:checked').each(function () {
        if($(this).val() != 'all'){
            warehouseIdList.push($(this).val());
        }
    });

    var pageData = {
        spu: $.trim($('#spu').val()),
        productType: saleProduct + giftProduct + packProduct,
        fullCid: !$('#variFullIdz').val() ? "" : $('#variFullIdz').val(),
        name: $.trim($('#proName').val()),
        nameEn: $.trim($('#proNameEn').val()),
        productInfo: [],
        warehouseIdList: warehouseIdList.join()
    };

    var flag = false;
    var chineseFlag = false;
    var skuArr = [];
    // 变种属性列表
    $('.variationListTable').find('tr').each(function () {
        var name = !$(this).find('[page-name]').attr('page-name') ? '' : $.trim($(this).find('[page-name]').attr('page-name')),
            imgUrl = $(this).find('[page-imgUrl]').find('img').attr('src');
        if (!imgUrl || imgUrl.indexOf('kong.png') > -1) {
            imgUrl = '';
        }

        var inputSku = $(this).find('[page-sku]').val();
        if (!inputSku) {
            flag |= true;
        }

        if (inputSku && isContainChinese(inputSku)) {
            chineseFlag |= true;
        }

        if (!flag && !chineseFlag) {
            skuArr.push(inputSku);
            var productInfoItem ={
                sku: $(this).find('[page-sku]').val(),
                name: name,
                imgUrl: imgUrl
            };
            pageData.productInfo.push(productInfoItem);
        }
    });
    if (flag) {
        return '请填写SKU';
    }
    if (chineseFlag) {
        return 'SKU不能有中文';
    }
    if (isRepeatInArray(skuArr)) {
        return 'SKU不能重复';
    }
    return pageData;
};

// 保存创建多变种属性
var saveVarientProductData = function () {
    var obj = getPageData();
    if (obj == '请填写SKU' || obj == 'SKU不能有中文' || obj == 'SKU不能重复') {
        $.fn.message({type: 'error', msg: obj});
        return;
    }
    if (obj.productInfo.length == 0) {
        $.fn.message({type: 'error', msg: '没有变种信息'});
        return;
    }

    var twoTableData = customsClearanceFn.getPageData(); //获取商品、报关两表数据
    if(twoTableData.flag){
        obj.productInfo = twoTableData.data;
    }else{
        return;
    }

    $('#loading').modal('show');
    $.post(requestUrl + "dxmCommodityProduct/productAddVarient.json", {obj: JSON.stringify(obj)}, function (data) {
        if (!data.msg) {
            $.fn.message({type: 'success', msg: '添加成功'});
            if ($('input[name="isGoOnCreate"]:checked').length == 2) {
                var groupornot = $('#hiddenInfo').attr('data-groupornot');
                window.open('dxmCommodityProduct/jumpAddProduct.htm', '_self');
            }
        } else {
            $.fn.message({type: 'error', msg: data.msg});
        }
    }).always(function () {
        $('#loading').modal('hide');
    })
};

/*===================== 图片操作 ↓ ===========================*/
// 显示从空间选择图片的窗口
var showImgSpaceFrame = function (that, type){
    var skuId = $(that).closest('tr').attr('id');
    if (type) {
        $('#hiddenInfo').val(type);
    }

    $("#myFrame2").attr("src", "album/selectOneImg.htm?skuImgId="+strToUc(skuId));
    $('#loading').modal('show');
};

var hideImgSpaceFrame2 = function (){
    $('#loading').modal('hide');
    $("#selectImgFromSpace2").modal("show");
};

// 把下面有变种新且图片为空的替换掉
function fillImg(tplj) {
    var trObj = $('#variantProductTable').find('tr');
    if (trObj.length > 0) {
        alert(trObj.length+"trObj.length");
        trObj.each(function() {
            var img = $(this).find('img').attr('src');
            if (!img || img.indexOf('kong.png') != -1) {
                $(this).find('img').attr('src', tplj);
            }
        })
    }
}

// 空间图片处理，从子窗口中得到选中的图片地址
var showSelectImgFromSpace = function (that){
    var selImg = $(that).find('img').data("rel"),
        skuId = document.getElementById('myFrame2').contentWindow.getSkuImgId(),
        type = $('#hiddenInfo').val();
    skuId= ucToStr(skuId);
    if (type) {
        var tplj = getPicRealUrl(selImg);
        var tpl = "<img src='"+tplj+"' id='masterImg' rel='"+tplj+"' class='imgCss' data-names='auxiliaryImg'>";
        var div = $(tpl);
        $('#addPicImg').children().remove();
        $('#addPicImg').append(div);

        fillImg(tplj);
    } else {
        var obj = $('#variantProductTable tr[id="'+skuId+'"]');
        obj.find('img').attr('src',getPicRealUrl(selImg));
    }
    $('#selectImgFromSpace2').modal('hide');
};

// var imgUpOption = {
//     uploadCall : function(files,data,formData,wxBucket){},
//     onSelectCall : function(files,formData){}
// };

// $('#upload').click(function(){
//     imgUpOption.onSelectCall = function(files,formData){};
//     imgUpOption.uploadCall = function(files,data,formData,wxBucket){
//         $('#canCloseLoading').modal('hide');
//         data = $.parseJSON(data);
//         if(data != null){
//             if(data.code == 0){
//                 // 生成展示图片
//                 showImg(getPicRealUrl(data.data.fileid), data.data.fileid);
//             }else{
//                 $.fn.message({type:"error",msg:data.msg, existTime:1000*60});
//             }
//         }
//     };
//     myjUploadImg(imgUpOption);
// });

// 本地图片上传
var s = function(that, type) {
    var skuId = $(that).closest('tr').attr('id');
    if (type) {
        $('#hiddenInfo').val(type);
    } else {
        $('#hiddenInfo').val('');
    }
    // skuImgUp(skuId);
};

var skuImgUp = function(cid){
    imgUpOption.fullcid = '';
    imgUpOption.isNeedTree = 0;
    imgUpOption.cid = cid;
    imgUpOption.onSelectCall = function(files,formData){
        var skuImgId = formData.cid;
        if($.trim(skuImgId)!=""){
            var url = '<%=basePath%>/static/img/loadingSm.gif';
            skuImg(skuImgId,url);
        }
        return true;
    };
    imgUpOption.uploadCall = function(files,data,formData,wxBucket){
        data = $.parseJSON(data);
        if(data != null){
            if(data.code == 0){
                var skuImgId = formData.cid;
                if($.trim(skuImgId)!=""){
                    //sku图片上传
                    skuImg(skuImgId,getPicRealUrl(data.data.fileid));

                    //填充变种图片为该图
                    fillImg(getPicRealUrl(data.data.fileid));
                }
            }else{
                $.fn.message({type:"error",msg:data.msg, existTime:1000*60});
            }
        }
    };
    myjUploadImg(imgUpOption);
};

// 网络图片
var getImgFromNet = function (that, type) {
    var cid = $(that).closest('tr').attr('id');
    if (type) {
        $('#hiddenInfo').val(type);
    }
    $("#skuPropertyCid").val(cid);
    $("#imgModal2").modal('show');
};

// 网络图片处理-sku图片
function downImgFromUrl2(){
    var url = $.trim($("#netImgUrl2").val()),
        type = $('#hiddenInfo').val();
    if(url == null || url == ""){
        $.fn.message({type:"error",msg:"图片地址不能为空！"});
        return false;
    }
    if (type) {
        var tplj = getPicRealUrl(url);
        var tpl = "<img src='"+tplj+"'  id='masterImg' rel='"+tplj+"' class='imgCss' data-names='auxiliaryImg'>";
        var div = $(tpl);
        $('#addPicImg').children().remove();
        $('#addPicImg').append(div);
        fillImg(tplj);
    } else {
        // 生成展示图片
        var skuPropertyCid = $.trim($("#skuPropertyCid").val());
        if($.trim(skuPropertyCid)!=""){
            skuImg(skuPropertyCid, url);
        }
    }
    $("#skuPropertyCid").val("");

    $("#imgModal2").modal("hide");
    $("#netImgUrl2").val("");
}

// 变种添加图片 penglin 处理处理（sku图片回显）
function skuImg(skuId,url){
    var type = $('#hiddenInfo').val();
    if (type) {
        var tplj = getPicRealUrl(url);
        var tpl = "<img src='"+tplj+"'  id='masterImg' rel='"+tplj+"' class='imgCss' data-names='auxiliaryImg'>";
        var div = $(tpl);
        $('#addPicImg').children().remove();
        $('#addPicImg').append(div);
    } else {
        var obj = $('#variantProductTable tr[id="'+skuId+'"]');
        obj.find('img').attr('src',getPicRealUrl(url));
    }
}
/*==================== [图片操作 ↑]====================== */


/***********************  wish引用  ***********************/
function addVarientById(id) {
    $('#loading').modal('show');
    //通过id查询后台数据
    $.ajax({
        type:"GET",
        url:"dxmCommodityProduct/productList.json",
        data:{
            "platform":$('#platform').val(),
            "id":id
        },
        dataType:"json",
        success:function (data){
            $('#loading').modal('hide');
            if(data.product!=null){
                var mainImg = data.product.mainImage,
                    name = data.product.name,
                    variantList = data.variantList,
                    pageSpu = $.trim($('#spu').val()),
                    sourceUrl = data.product.sourceUrl ? data.product.sourceUrl : '';

                if(variantList != null){
                    // 引用产品变种信息回显
                    var newObj = {};
                    var newValueObj = {};
                    $.each(variantList,function(i){
                        var variantListObj = variantList[i];  //重组集合
                        var colorName = variantListObj.color,
                            sizeName = variantListObj.size,
                            sku = variantListObj.sku ? variantListObj.sku.replace(/[^a-zA-Z0-9-_+xX*#]*/g,'') : '',
                            image = variantListObj.mainImage,
                            variantId = '';

                        if(colorName){
                            if(!newObj.color){
                                newObj.color = [];
                            }
                            if($.inArray(colorName,newObj.color) == -1){ //$.inArray()方法类似于JavaScript的原生.indexOf()方法，没有找到匹配元素时它返回-1
                                newObj.color.push(colorName);
                            }
                            variantId += colorName;
                        }
                        if(sizeName){
                            if(!newObj.size){
                                newObj.size = [];
                            }
                            if($.inArray(sizeName,newObj.size) == -1){
                                newObj.size.push(sizeName);
                            }
                            if(variantId){
                                variantId += '_'+sizeName;
                            }else{
                                variantId += sizeName;
                            }
                        }
                        if(sku){
                            if(!newObj.sku){
                                newObj.sku = [];
                            }
                            newObj.sku.push(sku);
                        }
                        if(!newObj.image){
                            newObj.image = [];
                        }
                        newObj.image.push(image);
                        newValueObj[variantId] = variantList[i];
                    });

                    if(!newObj.color && !newObj.size ){
                        $.fn.message({type:"error",msg:"您引用的产品无任何变种信息!"});
                        return;
                    }

                    //标题 主图回填
                    mainImg && $('#masterImg').attr('src',mainImg);
                    $('#proName').val(name ? name : '');

                    // [A]属性值&属性值回填
                    $('.variationAddTable > tbody').empty();
                    $.each(newObj,function(name,valueArr){
                        if(name=='color' || name=='size'){
                            $('.variationAddTable > tbody').append($(htmlStrObj.varationAddContent));
                            // [A-2]属性名
                            var $variationName = $('.variationAddTable tr:last-child td input.variationName');
                            $variationName.val( name=='color' ? 'color' : name );
                            $variationName.attr('data-name', name=='color' ? 'color' : name);
                            // [A-3]属性值
                            $.each(valueArr,function(i,j){
                                var $variationValue = $('.variationAddTable tr:last-child td>[data-id="variationAttrAdd"] .variationValue');

                                var newstr = $(htmlStrObj .tagHtml);

                                newstr.find('[data-uid="tagName"]').attr('data-value',strToUc(j));
                                newstr.find('[data-uid="tagName"]').attr('data-text', j);
                                newstr.find('[data-uid="tagName"]').text(j);

                                $variationValue.before(newstr);
                            });
                        }
                    });

                    //调用生成变种列表方法
                    addVerationFn.triggrtQuoteProductPushData(sourceUrl ? sourceUrl.split('|')[0] : '');

                    // [B] 变种图片sku 回填
                    $.each(variantList,function(key,val){
                        var listId = '', color = '', size = '';
                        var c = val.color,
                            s = val.size,
                            skuVal = val.sku ? val.sku.replace(/[^a-zA-Z0-9-_+xX*#]*/g,'') : '',
                            imageVal = val.image;
                        if((c != null && c !='')){
                            color = c.replace(' ','M_Y_J');
                            if(s != null && s !=''){

                                size = s.replace(' ','M_Y_J');
                                listId = color + '-' + size;
                            }else {
                                listId = color;
                            }
                        }else {
                            if(s != null && s !=''){
                                size = s.replace(' ','M_Y_J');
                                listId = size;
                            }
                        }
                        //显示变种sku
                        if(listId){
                            if(skuVal && skuVal != null){
                                $('tr[id="' + pageSpu + '-' + listId +'"] input.sku').val(skuVal);
                                $('.skuInfoTable tr[listid="' + pageSpu + '-' + listId +'"] td[data-name="sku"]').text(skuVal);
                            }
                            //显示变种图片
                            if(imageVal && imageVal != null){
                                $('tr[id="' + pageSpu + '-' + listId +'"] img.imgCss').prop('src',getPicRealUrl(imageVal));
                            }else {
                                $('tr[id="' + pageSpu + '-' + listId +'"] img.imgCss').prop('src',getPicRealUrl(mainImg));
                            }
                        }
                    });

                }else {
                    $.fn.message({type:"error",msg:"您引用的产品无任何变种信息!"});
                    return;
                }
            }else{
                $.fn.message({type:"error",msg:"您引用的产品无任何变种信息!"});
                return;
            }
            $("#quoteModal").modal("hide");
        }
    });
}

/***********************  Smt引用  ***********************/
function addSmtVarient(id){
    $('#loading').modal('show');
    //通过id查询后台数据
    $.ajax({
        type:"GET",
        url:"dxmCommodityProduct/productList.json",
        data:{
            "platform":$('#platform').val(),
            "id":id
        },
        dataType:"json",
        success:function (data){
            $('#loading').modal('hide');
            var variantList = data.variantList; //变种sku信息列表
            var variantAttr = data.variantAttr; //属性名&值列表
            var platform = data.platform;
            var spu = data.product.spu;
            var subject = data.product.subject; //名称title
            var pageSpu = $.trim($('#spuName').val());
            var sourceUrl = data.product.sourceUrl;

            if(!variantAttr){
                $.fn.message({type:"error",msg:"您引用的产品无任何变种信息!"});
                return;
            }

            $('#platform').val(platform);
            if(data.image){
                var imgArr = new Array();
                imgArr = data.image.split(";");
                var img = imgArr[0];
            }

            //主图 中文名称回填
            img && $('#addPicImg img').attr('src',getPicRealUrl(img));
            $('#proName').val(subject ? subject : '');

            if(variantList && !isEmptyObject(variantList)){
                // [A] 属性名&属性值 回填
                if(variantAttr){
                    $('.variationAddTable > tbody').empty();
                    $.each(variantAttr,function(m,n){
                        // [A-2]属性名
                        $('.variationAddTable > tbody').append($(htmlStrObj.varationAddContent));
                        var $variationName = $('.variationAddTable tr:last-child td input.variationName');
                        $variationName.val(m).attr('data-name',m);

                        // [A-3]属性值
                        $.each(n,function (i,j) {
                            var $variationValue = $('.variationAddTable tr:last-child td>[data-id="variationAttrAdd"] .variationValue');
                            var newstr = $(htmlStrObj .tagHtml);
                            newstr.find('[data-uid="tagName"]').attr('data-value',strToUc(j));
                            newstr.find('[data-uid="tagName"]').attr('data-text', j);
                            newstr.find('[data-uid="tagName"]').text(j);

                            $variationValue.before(newstr);
                        })

                    });
                    //调用生成变种列表方法
                    addVerationFn.triggrtQuoteProductPushData(sourceUrl ? sourceUrl.split('|')[0] : '');
                }
                // [B] 变种属性图片 sku 回填
                var isFirst = $('.varationAddContent:first .variationName').attr('data-name');
                $.each(variantList,function(key,val){
                    var listid = '',color='',size='';
                    if(key != 'attrList'){
                        var c = val.Color,
                            s = val.Size,
                            skuVal = val.sku,
                            imageVal = val.image;
                        if(c != null && c != ''){
                            color = c.replace(' ','M_Y_J');
                            if(s!= null && s !=''){
                                size = s.replace(' ','M_Y_J');
                                listid = isFirst.toLocaleLowerCase() === 'size' ? size+'-'+color : color+'-'+size;
                            }else {
                                listid = color;
                            }
                        }else {
                            if(s != null && s !=''){
                                size = s.replace(' ','M_Y_J');
                                listid = size;
                            }
                        }

                        //显示变种sku
                        if(skuVal && skuVal != null){
                            $('tr[id="' + pageSpu + '-' + listid +'"] input.sku').val(skuVal);
                            $('.skuInfoTable tr[listid="' + pageSpu + '-' + listid +'"] td[data-name="sku"]').text(skuVal);
                        }
                        //显示变种图片
                        $('tr[id="' + pageSpu + '-' + listid +'"] img.imgCss').attr('src',imageVal ? getPicRealUrl(imageVal) : img);
                    }
                });

            }else {
                $.fn.message({type:"error",msg:"您引用的产品无任何变种信息!"});
                return;
            }
            $('#quoteModal').modal('hide');
        }
    });
}

/***********************  eBay引用  ***********************/
function addEbyVarient(id){
    $('#loading').modal('show');
    $("#quoteModal").modal("hide");
    //通过id查询后台数据
    $.ajax({
        type:"GET",
        url:"dxmCommodityProduct/productList.json",
        data:{
            "platform":$('#platform').val(),
            "id":id
        },
        dataType:"json",
        success:function (data){
            $('#loading').modal('hide');
            var variantList = data.variantList,
                variantSkuList = data.variantSkuList,
                platform = data.platform,
                image = getPicRealUrl(data.product.mainImage),
                spu = data.product.spu,
                title = data.product.title,
                pageSpu = $.trim($('#spu').val()),
                sourceUrl = data.product.sourceUrl;

            $('#platform').val(platform);
            if(variantList != null){
                //主图显示
                $('#addPicImg img').attr('src',image);
                //标题显示
                $('#proName').val(title ? title : '');

                $('.variationAddTable > tbody').empty();
                $.each(variantList,function(i,j){
                    // [A]属性名
                    $('.variationAddTable > tbody').append($(htmlStrObj.varationAddContent));
                    var $variationName = $('.variationAddTable tr:last-child td input.variationName');
                    $variationName.val(i).attr('data',i);

                    // [A]属性值
                    var $variationValue = $('.variationAddTable tr:last-child td>[data-id="variationAttrAdd"] .variationValue');
                    $.each(j,function(m,n){
                        var newstr = $(htmlStrObj .tagHtml);
                        newstr.find('[data-uid="tagName"]').attr('data-value',strToUc(n));
                        newstr.find('[data-uid="tagName"]').attr('data-text', n);
                        newstr.find('[data-uid="tagName"]').text(n);

                        $variationValue.before(newstr);
                    });
                });

                //调用生成变种列表方法
                addVerationFn.triggrtQuoteProductPushData(sourceUrl ? sourceUrl.split('|')[0] : '');

            }else {
                $.fn.message({type:"error",msg:"您引用的产品无任何变种信息!"});
                return;
            }
            // [B]变种列表SKU 图片回填
            if(variantSkuList!=null){
                //通过listid给列表赋值（sku、image)
                $.each(variantSkuList,function(i){
                    var listid = '';
                    var variantSkuListObj = variantSkuList[i];
                    var sku = variantSkuListObj.sku;
                    var color = variantSkuListObj.specifics.Color[0];

                    if(color != null && color !=''){
                        listid = color.replace(' ','M_Y_J');
                        $('tr[id="' + pageSpu + '-' + listid +'"] input.sku').val(sku);
                        $('.skuInfoTable tr[listid="' + pageSpu + '-' + listid +'"] td[data-name="sku"]').text(sku);
                    }
                    if(image!=null && image!=''){
                        $('tr[id="' + pageSpu + '-' + listid +'"] img.imgCss').attr('src',getPicRealUrl(image));
                    }
                });
            }
            $("#quoteModal").modal("hide");
        }
    });
}

/***********************  Amazon引用  ***********************/
function addAmazonVarient(id){
    $('#loading').modal('show');
    //通过id查询后台数据
    $.ajax({
        type:"GET",
        url:"dxmCommodityProduct/productList.json",
        data:{
            "platform":$('#platform').val(),
            "id":id
        },
        dataType:"json",
        success:function (data){
            $('#loading').modal('hide');
            var variantList = data.variantList,
                amazonProductMap = data.amazonProductMap,
                image = data.product.mainImage,
                mainImage = data.image,
                variantSkuList = data.sku,
                sourceUrl = data.product.sourceUrl,
                spu = data.product.spu,
                platform = data.platform,
                title = data.product.description,
                pageSpu = $.trim($('#spuName').val());
            $('#platform').val(platform);

            //变种属性名&变种属性值
            if(isEmptyObject(variantList)){
                $.fn.message({type:"error",msg:"您引用的产品无任何变种信息!"});
                return;
            }else {
                //图片名称显示
                image && $('#addPicImg img').attr('src',image);
                $('#proName').val(title ? title : '');

                var variantSourceUrl = data.product.variations ? data.product.variations[0].sourceUrl.split('|')[0] : '';

                //变种属性名 属性值显示
                $('.variationAddTable > tbody').empty();
                $.each(variantList,function(key,value){
                    if(value.length>0){
                        $('.variationAddTable > tbody').append($(htmlStrObj.varationAddContent));
                        var $variationName = $('.variationAddTable tr:last-child td input.variationName');
                        $variationName.val(key).attr('data-name',key);

                        var $variationValue = $('.variationAddTable tr:last-child td>[data-id="variationAttrAdd"] .variationValue');
                        $.each(value,function (i,j) {
                            var newstr = $(htmlStrObj .tagHtml);
                            newstr.find('[data-uid="tagName"]').attr('data-value',strToUc(j));
                            newstr.find('[data-uid="tagName"]').attr('data-text', j);
                            newstr.find('[data-uid="tagName"]').text(j);
                            $variationValue.before(newstr);
                        })
                    }
                });
                //调用生成变种列表方法
                addVerationFn.triggrtQuoteProductPushData(sourceUrl ? sourceUrl.split('|')[0] : variantSourceUrl);
            }

            // 变种属性sku img 回填
            $.each(amazonProductMap,function (key,val) {
                var listid = '';
                var sku = val.sku;
                var mainImage = val.mainImage;
                var color = val.specifics && val.specifics.Color ? val.specifics.Color.value : '';
                if(color != null && color !=''){
                    listid = color.replace(' ','M_Y_J');
                    //sku
                    if(sku!=null && sku!=''){
                        $('tr[id="' + pageSpu + '-' + listid +'"] input.sku').val(sku);
                        $('.skuInfoTable tr[listid="' + pageSpu + '-' + listid +'"] td[data-name="sku"]').text(sku);
                    }
                    //img
                    if(mainImage!=null && mainImage!=''){
                        $('tr[id="' + pageSpu + '-' + listid +'"] img.imgCss').attr('src',getPicRealUrl(mainImage));
                    }
                }
            });

            $("#quoteModal").modal("hide");
        }
    });
}