//alert
(function($) {
    $.fn.extend( {
        "message" : function(options) {
            //className 可以有alert,alert-success,alert-info, alert-error 等选项，对应不同的样式

           switch (options.type){
                /*2017.2.8 byzym 修改系统提示信息*/
                case "warning" :
                        options.className = "alert-warningYellow";
                        options.title = "提示：";
					    options.class = "font-size: 26px;position: absolute;right: 20px;color:#434649;";
                    break;
                case "success" :
                        options.className = "alert-successGreen";
                        options.title = "";
						options.class = "font-size: 26px;position: absolute;right: 10px;";
                    break;
                case "error":
                        options.className = "alert-dangerRed";
                        options.title = "错误：";
						options.class = "font-size: 40px;position: absolute;right: 20px;";
                    break;

            }
            options = $.extend( {
                type : options.type,
                msg : options.msg,
                existTime: options.existTime || 5000
            }, options);
			/*2017.1.14 byzym 修改alert*/
            var div = $('<div data-name="warMing" class="' +options.className+ '" style="min-width: 350px;text-align: center;z-index:9999999;display:none;min-height:25px;line-height:25px;position:fixed;box-shadow: 1px 2px 3px #AAAAAA;">'
                +'<div style="display:inline-block;"><div class="pull-left mLeft10"><strong>'+ options.title+'</strong></div>'
                +'<div data-name="optionMsg" style="text-align: left;max-width: 1200px"  class="pull-left mLeft10"><span>'+options.msg.replace(/%#W7Ox0Ze%#/g,'<a href=\"').replace(/%#uIGOykJW%#/g,'" target="_black" >').replace(/%#ulvupSta%#/g,'</a>')+'</span></div></div>'
				+'<a href="javascript:;" class="closeAlert" style="line-height: 25px;color: #FFF;'+options.class+'">×</a>'
                +'</div>');
			var divObj=$(div);
			var types=options.type;
			var marginLeft,left,height,borderRai;
			divObj.find(".closeAlert").click(function(){
				$(this).closest("div[data-name='warMing']").remove();
			})
            $('body').append(divObj);
			var width=divObj.width();
			if(types=="success"){
				marginLeft="-"+width/2+"px";
				left="50%";
				borderRai="3px";
			}else{
				marginLeft="0";
				left="0";
				borderRai="0";
			}
			divObj.css({'margin-left':marginLeft,'left':left,'border-bottom-left-radius':borderRai,'border-bottom-right-radius':borderRai});
			//divObj.css({"top":0,"left":0});
			var height=divObj.height();
			//divObj.css({"position":"fixed"});
			height = 100;
			divObj.css('top','-'+height+'px');
			divObj.show();
			if(divObj.show()){
				divObj.animate({"top":"0"},800);
			}

            if(options.type != "error"){
				setTimeout(
					function(){
						divObj.animate({"top":-height},400,function(){
							divObj.remove();
						})
					},
					options.existTime

				);
                /* //隐藏对象
                setTimeout(function(){divObj.toggle(options.speed);},options.existTime);*/

				/*!//移除对象
                setTimeout(function(){divObj.remove();},options.existTime+5000);*/
            }else{
				setTimeout(
					function(){
						divObj.animate({"top":-height},400,function(){
							divObj.remove();
						})
					},
					options.existTime
				);

            	 /*!//隐藏对象
                setTimeout(function(){divObj.toggle(options.speed);},options.existTime);*/

                /*!//移除对象
                setTimeout(function(){divObj.remove();},options.existTime+8000);*/
            }

        }
    });
    return this;
})(jQuery);

var poverError = [
	{
		bgColor: "#ff0000",
		type : "错误信息",
		content : "网络链接超时"
	},
	{
		bgColor: "green",
		type : "友情信息",
		content : "顶戴顶替超时"
	},
	{
		bgColor: "blue",
		type : "blue",
		content : "blue"
	}
];

function pover(selector,options){
	var node = document.createElement("span");
		options = $.extend({
			bgColor: "#ff0000",
			type : "错误信息",
	    	content : "网络链接超时"
		},options);
	$(node).css("padding","3px 4px")
		   .css("cursor","pointer")
		   .css("line-height","18px")
		   .css("height","18px")
		   .css("color","#fff")
		   //.css("display","block")
		   .css("background-color",options.bgColor)
		   .css("font-weight","600");
	$(node).attr("data-html","true")
		   .attr("data-toggle","popover")
		   .attr("data-trigger","hover")
		   .attr("data-placement","right")
		   .attr("data-content",options.content);
	$(node).html(options.type);
	$(selector).append(node);
	$('[data-toggle=popover]').popover();
}

// 关闭当前窗口
function closeWin(){
	window.opener=null;window.open('','_self');window.close();
}


//倒计时  （使用方法：addTimer("timer1", 604800); ）
var addTimer = function () {
        var list = [],
            interval;

        return function (id, time,type) {
            if (!interval)
                interval = setInterval(go, 1000);
            list.push({ 'id':id, 'time': time,'type':type });
        };

        function go() {
        	if(list.length==0){
        		clearTimeout(interval);
        		interval = "";
        	}

            for (var i = 0; i < list.length; i++) {
            	var obj = document.getElementById( list[i].id );
            	if(obj==undefined){
            		 list.splice(i--, 1);
            		 continue;
            	}

               obj.innerHTML = getTimerString(list[i].time ? list[i].time -= 1 : 0,list[i].type);

               if (list[i].time<0){
                   list.splice(i--, 1);
                   continue;
               }
            }
        }

        function getTimerString(time,type) {
                d = Math.floor(time / 86400),
                h = Math.floor((time % 86400) / 3600),
                m = Math.floor(((time % 86400) % 3600) / 60),
                s = Math.floor(((time % 86400) % 3600) % 60);
         /*
                if(h<10)
                	h = "0" + h;
                if(m<10)
                	m= "0" + h;
                if(s<10)
                	s = "0" + s;
           */
            if (time>0 && type == 'shipping'){
				return "剩余发货：<span style=\"color:red;\">" + d + "</span>" + "天" + "<span style=\"color:red;\">" + h + "</span>" + "小时" + "<span style=\"color:red;\">" + m + "</span>" + "分<br/>";
			}else if(time>0 && type == 'tracking'){
				if(d>=3){
					return "<span style=\"color: #888;\">单号有效：" + d + "天</span>"  + "<span style=\"color:#888;\">" + h + "小时" + "</span>" + "<span style=\"color:#888;\">" + m + "分</span><br/>" ;
				}else{
					return "<span style=\"color:red;\">单号有效：" + d + "天</span>"  + "<span style=\"color:red;\">" + h + "小时" + "</span>" + "<span style=\"color:red;\">" + m + "分</span><br/>" ;
				}
			}else if(time <= 0 && type == 'shipping'){
				return "<span style=\"color:red;\">剩余发货：已到期</span><br/>";
			}else if(time <= 0 && type == 'tracking'){
				return "<span style=\"color:red;\">单号有效：已到期</span><br/>";
			}else if(time > 0 && type == 'issue'){
				return "<span style=\"color:red;\">" + d + "</span>" + "天" + "<span style=\"color:red;\">" + h + "</span>" + "小时" + "<span style=\"color:red;\">" + m + "</span>" + "分" + "<span style=\"color:red;\">" + s + "</span>" + "秒";
			}
        }
} ();


//倒计时  （使用方法：addTimer("timer1", 604800); ）
var addProductTimer = function () {
      var list = [],
          interval;

      return function (id, date,now) {
          if (!interval)
              interval = setInterval(go, 1000);
          var d1 = new Date(date);
          var d2 = new Date(now);
          var time = (d1-d2)/1000;

          list.push({ 'id':id, 'time': time });
      };

      function go() {
      	if(list.length==0){
      		clearTimeout(interval);
      		interval = "";
      	}

          for (var i = 0; i < list.length; i++) {
          	var obj = document.getElementById( list[i].id );
          	if(obj==undefined){
          		 list.splice(i--, 1);
          		 continue;
          	}

             obj.innerHTML = getTimerString(list[i].time ? list[i].time -= 1 : 0);

             if (list[i].time<0){
                 list.splice(i--, 1);
                 continue;
             }
          }
      }

      function getTimerString(time) {
              d = Math.floor(time / 86400),
              h = Math.floor((time % 86400) / 3600),
              m = Math.floor(((time % 86400) % 3600) / 60),
              s = Math.floor(((time % 86400) % 3600) % 60);
       /*
              if(h<10)
              	h = "0" + h;
              if(m<10)
              	m= "0" + h;
              if(s<10)
              	s = "0" + s;
         */
          if (time>0)
              return "<p class='m0'>剩余：</p><p class='m0'><span style=\"color:red;\">" + d + "</span>" + "天" + "<span style=\"color:red;\">" + h + "</span>" + "小时" + "<span style=\"color:red;\">" + m + "</span>" + "分</p>";
          else return "<p class='m0' style=\"color:red;\">已到期</p>";
      }
} ();
//方法可能重复
//倒计时  （使用方法：addTimer("timer1", 604800); ）
//采购单
var addProductTimerS = function () {
	var list = [],
		interval;

	return function (id, date,now) {
		if (!interval)
			interval = setInterval(go, 1000);
		var d1 = new Date(date);
		var d2 = new Date(now);
		var time = (d1-d2)/1000;

		list.push({ 'id':id, 'time': time });
	};

	function go() {
		if(list.length==0){
			clearTimeout(interval);
			interval = "";
		}

		for (var i = 0; i < list.length; i++) {
			var obj = document.getElementById( list[i].id );
			if(obj==undefined){
				list.splice(i--, 1);
				continue;
			}

			obj.innerHTML = getTimerString(list[i].time ? list[i].time -= 1 : 0);

			if (list[i].time<0){
				list.splice(i--, 1);
				continue;
			}
		}
	}

	function getTimerString(time) {
		d = Math.floor(time / 86400),
			h = Math.floor((time % 86400) / 3600),
			m = Math.floor(((time % 86400) % 3600) / 60),
			s = Math.floor(((time % 86400) % 3600) % 60);
		/*
		 if(h<10)
		 h = "0" + h;
		 if(m<10)
		 m= "0" + h;
		 if(s<10)
		 s = "0" + s;
		 */
		if (time>0)
			return "<p class='m0'>剩余：<span style=\"color:red;\">" + d + "</span>" + "天" + "<span style=\"color:red;\">" + h + "</span>" + "小时"/* + "<span style=\"color:red;\">" + m + "</span>" + "分</p>"*/;
			else return "<p class='m0' style=\"color:red;\">已到期</p>";
	}
} ();


// 检查后台处理状态(场景：后台处理时间较长，前台定时请求后台的执行状态)
var checkProcessStatus = function (){
	var interval;
	var uuid;

	return function (uuid, callback) {
        if (!interval)
            interval = setInterval(go, 3000);

        this.callback  = callback;
        this.uuid = uuid;
	};

	function go() {
		 $.ajax({
		        type: "POST",
		        url: "checkProcess.json",
		        data: {"uuid" : this.uuid},
		        dataType: "json",
		        success: function(data){
		        	var pm = data.processMsg;

		            if(pm==undefined || pm.code!=0){
		            	clearTimeout(interval);
		            	interval = "";
		            }
	            	callback(pm);
		        }
		    });
	 }
}();

/**
 * 根据name，得到某组checkbox选中的值(多个用逗号分隔)
 */
function getCheckBoxValByName(name){
	var s = "";
	$("input[name='"+name+"']:checked").each(function(){
		var v = $(this).val();
		if (v.indexOf(",")>-1){
			v = v.replace(/,/g ,"");
		}
		if(s!=""){
			s = s + ",";
		}
		s = s + v;
	});
	return s;
}

/**
 *  用于店小秘与插件的交互
 * 根据name，得到某组checkbox选中的值(多个用逗号分隔)
 * @param name
 * @returns {string}
 */
function getCheckBoxValByNames(name){
    var s = "";
    $("input[name='"+name+"']:checked").each(function(){
        var v = $(this).val();
        if (v.indexOf(",")>-1){
            v = v.replace(/,/g ,"");
        }
        if(s!=""){
            s = s + "!@#";
        }
        s = s + v;
    });
    return s;
}

/**
 * 根据name，得到所有checkbox的值（多个用逗号分隔）
 */
var getAllCheckBoxValByName = function(name){
	var s = "";
	$("input[name='"+name+"']").each(function(){
		var v = $(this).val();
		if(s!=""){
			s = s + ",";
		}
		s = s + v;
	});
	return s;
};

/**
 * 选中当前页复选框
 * @param obj
 * @param name
 */
function selAllCurrPage(obj, name){
	var isChecked = $(obj).is(':checked');
	if(isChecked){
		$("input[name='"+name+"']").prop("checked", true);
	}else{
		$("input[name='"+name+"']").prop("checked", false);
	}
}
/**
 * 选中当前页复选框new nohead
 * @param obj
 * @param name
 */
function selAllCurrPageNoHead(obj, name){
    var isChecked = $(obj).is(':checked');
    if(isChecked){
        $("input[name='"+name+"']").prop("checked", true).closest('tr.content').addClass('has-check');
    }else{
        $("input[name='"+name+"']").prop("checked", false).closest('tr.content').removeClass('has-check');
    }
}
/**
 * 选中当前页复选框new
 * @param obj
 * @param name
 */
function selAllCurrPageHasContent(obj, name){
    var isChecked = $(obj).is(':checked');
    if(isChecked){
        $("input[name='"+name+"']").prop("checked", true).closest('tr').next('tr.content').addClass('has-check');
    }else{
        $("input[name='"+name+"']").prop("checked", false).closest('tr').next('tr.content').removeClass('has-check');
    }
}
/**
 * 选中当前复选框new nohead
 * @param obj
 * @param name
 */
function selCurrPageNoHead(obj){
    var isChecked = $(obj).is(':checked');
    if(isChecked){
        $(obj).prop("checked", true).closest('tr.content').addClass('has-check');
    }else{
        $(obj).prop("checked", false).closest('tr.content').removeClass('has-check');
    }
}
/**
 * 选中当前复选框new
 * @param obj
 * @param name
 */
function selCurrPageHasContent(obj){
    var isChecked = $(obj).is(':checked');
    if(isChecked){
        $(obj).prop("checked", true).closest('tr').next('tr.content').addClass('has-check');
    }else{
        $(obj).prop("checked", false).closest('tr').next('tr.content').removeClass('has-check');
    }
}
/**
 * 字符串格式化
 * 用法1：
 * 	var s = "成功删除&{successNum}个";
 * 	s.format({successNum:20});
 * 用法2：
 * 	var s = "成功删除&{0}个,错误&{1}个";
 * 	s.format(20, "abc");
 *
 */
var reg = null;
var formatStrReplace = function(data,result){
    Object.keys(data).forEach(function(key){
        reg = new RegExp ("&{"+key+"}","g");
        result = result.replace(reg, data[key]);
        reg = null;
    });
    return result;
};
String.prototype.format = function(args) {
    if (arguments.length>0) {
        var result = this,
            html = result,
            htmlList = '',
            i,ind,len;
        if (arguments.length === 1 && typeof (args) === "object") {
            if(Array.isArray(args)){
                for(ind = 0,len = args.length;ind < len;ind++){
                    result = html;
                    htmlList += formatStrReplace(args[ind],result);
                }
            }else{
                htmlList = formatStrReplace(args,result);
            }
        }else{
            for(i = 0; i < arguments.length; i++){
                if(Array.isArray(arguments[i])){
                    for(ind = 0,len = arguments[i].length; ind < len;ind++){
                        result = html;
                        htmlList += formatStrReplace(arguments[i][ind],result);
                    }
                }else{
                    result = html;
                    htmlList += formatStrReplace(arguments[i],result);
                }

            }
        }
        return htmlList;
    }else{
        return this;
    }
};

//模板替换
var htmlTemplateChange = function(id,data){
	var str  = $('#'+id).html();
	str = str.replace(/[\r\n]/g,"").replace(/(^\s{2,})|(\s{2,}$)|(\s{2,})/g,"");
	str = str.format(data);
	return str;
};

/**
 * 日期格式化
 * 用法：
 * new Date(毫秒数).format("yyyy-MM-dd hh:mm:ss");
 */
Date.prototype.format = function(format) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */
    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
        // millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
                        - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                            ? o[k]
                            : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

/**
 * 比较两个字符串日期大小
 */
function compareDate(startTime, endTime){
    var start=new Date(startTime.replace("-", "/").replace("-", "/"));
    var end=new Date(endTime.replace("-", "/").replace("-", "/"));
    if(start.getTime() == end.getTime()){ //时间戳转换成毫秒数比较 原无法比较 2018.3.21 byzym
        return 0;
    }else if(start > end){
    	return 1;
    }else if(start < end){
    	return -1;
    }
}

/**
 * 判断数组中是否有重复
 * @param arr 传入的数组
 * @returns
 * 如果有重复返回重复的值，如果没有重复返回空串
 *
 */
function isRepeatInArray(arr){
	for(var i = 0; i < arr.length; i++){
		for(var j=i+1; j<arr.length; j++){
			if(arr[i] == arr[j]){
				return arr[i];
			}
		}
	}
	return "";
}

/**
 * 判断字符串中是否包含中文
 * @param str
 * @returns {Boolean}
 * 如果包含中文返回true，否则返回false
 */
function isContainChinese(str){
	return /.*[\u4e00-\u9fa5]+.*/.test(str);
}

/**
 * 百度翻译
 * @param str
 */
var BAIDU_TRANSLATE_URL = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id=f2iK6uIQm8YcOqZO0WcSmVxm&from=zh&to=en&q=";

// 店小秘帮助URL地址
var DXM_HELP_URL = "http://help.dianxiaomi.com";

function baiDuTranslate(str, callback, options,from,to,platform){
	dxmTranslate(str, callback, options,from,to,platform);
}

function baiDuTranslateBak(str, callback){
	var s = "";
	if(str != undefined && str != ""){
		str = $.trim(str);
		if(str.length > 5000){
			$.fn.message({type:"error",msg:"超过了翻译的最大字数限制"});
			return callback(s);
		}
		// 包含中文的时候才进行翻译
		if(!isContainChinese(str)){
			return callback(s);
		}
		str = encodeURIComponent(str);
		var url = BAIDU_TRANSLATE_URL + str;

		$.ajax({
			async : false,
	        type : "get",
	        url : url,
	        dataType : "jsonp",
	        jsonp : "callback",
	        data : {},
	        success : function(data){
	        	if(data != null){
	        		// 如果error_code属性，证明请求失败
	        		if(data.hasOwnProperty("error_code")){
	        			$.fn.message({type:"error",msg:data.error_msg});
	        		}else if(data.hasOwnProperty("trans_result")){
	        			for(var i in data.trans_result){
	        				if(s == ""){
	        					s = data.trans_result[i].dst;
	        				}else{
	        					s += "\n" + data.trans_result[i].dst;
	        				}
	        			}
	        			callback(s);
	        		}else{
	        			$.fn.message({type:"error",msg:"网络连接超时，请稍后再试！"});
	        		}
	        	}
	        },
	        error:function(err){
	        	$.fn.message({type:"error",msg:"网络连接超时，请稍后再试！"});
	        }
	    });
	}
}

var DXM_TRANSLATE_URL = "//translate.dianxiaomi.com/translate.json";
function dxmTranslate(str, callback, options,from,to,platform){
	var s = "";
	if(str != undefined && str != ""){
		str = $.trim(str);
		if(str.length > 5000){
			$.fn.message({type:"error",msg:"超过了翻译的最大字数限制"});
			return callback(s, options);
		}
		// 包含中文的时候才进行翻译
		if(!isContainChinese(str)){
			return callback(s, options);
		}
		//str = encodeURIComponent(str);
		var url = DXM_TRANSLATE_URL;

		$.ajax({
	        type : "post",
	        url : url,
	        dataType : "json",
	        data : {q:str,from:from,to:to,platform:platform},
	        success : function(data){
	        	if(data != null){
	        		data = eval(data);
	        		// 如果属性，证明请求失败
	        		if(data.code == 0){
	        			data.data && callback(data.data, options);
	        		}else{
	        			$.fn.message({type:"error",msg:data.msg});
	        		}
	        	}
	        },
	        error:function(err){
	        	$.fn.message({type:"error",msg:"网络连接超时，请稍后再试！"});
	        }
	    });
	}
}

/**fuyi add**/
(function ($, window, location, undefined) {
    var ie = window.xiuxiu;
    window.imgEdit = function (cosUrl, imageUrl, wcb, selectPhotoFunc) {
        ie.params.allowFullscreen = false;
        ie.params.movie = "";
        ie.params.wmode = "transparent";
        ie.setLaunchVars('file_name', 'filedata');
        ie.setLaunchVars('file_type', 'jpg');
        if (selectPhotoFunc && typeof selectPhotoFunc == 'function'){
            ie.setLaunchVars('preventBrowseDefault', 1);
        }
        ie.setLaunchVars('cropPresets', [{'主图': '800x800'}, {'eBay主图': '1000x1000'}]);
        ie.setLaunchVars('customMenu', [{'decorate': ['basicEdit', 'inpaint', 'trinket', 'text', 'particle', 'effect', 'border', 'magic', 'localFixes']}]);
        ie.setLaunchVars('nav', 'decorate/basicEdit');
        ie.setLaunchVars('customMaterial', location.protocol + '//' + location.host + '/imageditor/custom_material.xml');
        ie.setUploadType(2);
        ie.setUploadURL(location.protocol + '//' + location.host + '/imageditor/file/upload.json');
        ie.onInit = function () {
            imageUrl && ie.loadPhoto(imageUrl);
        };
        ie.onBeforeUpload = function (data, id) {
            ie.setUploadArgs({filetype: data.type, type: 'image', url: imageUrl, filename: data.name});
        };
        ie.onUploadResponse = function (data) {
            var result = eval('(' + data + ')');
            wcb && typeof wcb == 'function' && wcb(result);
        };
        ie.onBrowse = function (channel, multipleSelection, canClose, id) {
            selectPhotoFunc && typeof selectPhotoFunc == 'function' && selectPhotoFunc(multipleSelection, canClose, function (src) {
                ie.loadPhoto(src, false, ie.defaultID, {loadImageChannel: channel});
            });
            return false;
        };
        ie.embedSWF('editor-embeded', 3, '100%', '100%');
        //修正360 flash遮挡
        var o = $('object');
		if(o.params){
			o.params.movie = "";
		}
    };
    window.watermarkEdit = function (watermarkUrl, watermarkId, wcb) {
        ie.params.allowFullscreen = false;
        ie.params.movie = "";
        ie.params.wmode = "transparent";
        ie.setLaunchVars('file_type', 'png');
        ie.setLaunchVars('file_name', 'filedata');
        ie.setLaunchVars('customMenu', [{'decorate': ['basicEdit', 'effect', 'text', 'border', 'magic', 'localFixes', 'particle', 'inpaint']}]);
        ie.setLaunchVars('nav', 'decorate/basicEdit');
        ie.setUploadType(2);
        ie.setUploadURL(location.protocol + '//' + location.host + '/watermark/file/upload.json');
        ie.onInit = function () {
            watermarkUrl && ie.loadPhoto(watermarkUrl);
        };
        ie.onBeforeUpload = function (data, id) {
            ie.setUploadArgs({
                filetype: data.type,
                type: 'image',
                url: watermarkUrl,
                id: watermarkId,
                filename: data.name
            });
        };
        ie.onUploadResponse = function (data) {
            var result = eval('(' + data + ')');
            wcb && typeof wcb == 'function' && wcb(result);
        };
        ie.embedSWF('editor-embeded', 3, '100%', '100%');
        //修正360 flash遮挡
        var o = $('object');
		o.params.movie = "";
    };
})(jQuery, window, window.location);

/**fuyi add end**/
//简单验证
function validateUser(obj){
	var validateStr = $(obj).val(),
	validateType = $(obj).attr('name'),
	//validateEmail = /^([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+\.(?:com|cn)$/;
	//validateEmail = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9_]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	// validateEmail = /^([a-zA-Z0-9]+[_|\_|\.|\-]?)*[a-zA-Z0-9_-]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9_-]+\.[a-zA-Z]{2,3}$/;
	validateEmail = /^([a-zA-Z0-9]|[\_|\.|\-])+@([a-zA-Z0-9]|[\_|\.|\-])*\.[a-zA-Z0-9]+$/;
	//validateEmail =  /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	var validateName = /^[a-zA-Z0-9][a-zA-Z0-9_-]{3,29}$/;
	var validatePassword = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,20}$/;
	var validateQQ = /^[1-9][0-9]{4,16}$/;

	switch (validateType)
	{
	case "account":
		msgTitle = "请输入您的用户名！";
		if (validateStr != '')
		{
			if (!validateStr.match(validateName))
			{
				//$.fn.message({type:"error",msg:"用户名格式不正确！格式：字母数字下划线,不能以下划线开头,长度4-30字符"});
				$('.verificode').html('用户名格式不正确！');
				return false;
			}
		}
		break;
	case "password":
		msgTitle = "密码不能为空！";
		if (validateStr != '')
		{
			if (!validateStr.match(validatePassword))
			{
				$('.verificode').html('密码格式不正确！不能含有空格,必须包含大小写字母、数字,支持特殊字符,长度为8-20个字符！');
				return false;
			}
		}
		break;
	case "password2":
		msgTitle = "确认密码不能为空！";
		if (validateStr != '')
		{
			if (!validateStr.match(validatePassword))
			{
				$.fn.message({type:"error",msg:"确认密码格式不正确！不能含有空格,必须包含大小写字母、数字,支持特殊字符,长度为8-20个字符！"});
				return false;
			}
		}
		break;
	case "shopName":
		msgTitle = "请填写有效的店铺名称";
		break;
	case "merchantId":
		msgTitle = "请填写有效的商户ID";
		break;
	case "content":
		msgTitle = "反馈内容不能为空";
		break;
	case "email":
		msgTitle = "邮箱不能为空";
		if (validateStr != '')
		{
			if (!validateStr.match(validateEmail))
			{
				//$.fn.message({type:"error",msg:"邮箱格式不正确！"});
				//$('.userEmall').html("邮箱格式不正确！");
				$('.verificode').html("邮箱格式不正确！");
				return false;
			}
		}
		break;
	case "qq":
		msgTitle = "QQ不能为空";
		if (validateStr != '')
		{
			if (!validateStr.match(validateQQ))
			{
				//$.fn.message({type:"error",msg:"QQ格式不正确！"});
				$('.verificode').html("QQ格式不正确！");
				return false;
			}
		}else{
			//$.fn.message({type:"error",msg:msgTitle});
			$('.verificode').html(msgTitle);
			return false;
		}
		break;

	}

	if (validateStr == ''){
	   $('.verificode').html(msgTitle);
		//$.fn.message({type:"error",msg:msgTitle});
		return false;
	}

	return true;
}
//首字母大写
String.prototype.firstUpperCase =  function (){
	var str = this.toLowerCase(),
		reg = /^[a-z]/i;
	return str.replace(reg,function(str){
		return str.toUpperCase();
	});
};

// 判断是否是图片
function isPic(name){
    var strFilter =".jpeg|.gif|.jpg|.png|.bmp|.pic|";
    if(name.indexOf(".")>-1){
		var p = name.lastIndexOf(".");
		var strPostfix = name.substring(p, name.length) + "|";
		strPostfix = strPostfix.toLowerCase();
		if(strFilter.indexOf(strPostfix)>-1){
		  return true;
		}
    }
    return false;
}

// 兼容图片访问地址
function getPicRealUrl(url){
    var realUrl = "";
    if(url){
        if(url.indexOf("http") == -1 && url.indexOf("HTTP") == -1){
            // cos地址
            var cosImgUrl="http://cos.myqcloud.com/11000460/";
            // 万象图片地址
            var wxImgUrlPrefix="https://XXXXX-10001658.image.myqcloud.com/",
				productImgPrefix="https://X-1251220924.picgz.myqcloud.com";
            // 万象图片项目ID(10001658)
            //var wxAppId = "10001658";

            // 判断图片是cos的还是万象的
			var bucket;
            if(url.startWith("wx")){
                bucket = url.split("/")[0];
                realUrl = wxImgUrlPrefix.replace("XXXXX", bucket) + url;
            }else if (url.startWith("/productimage")) {
				bucket = url.split("/")[1];
				realUrl = productImgPrefix.replace("X", bucket) + url;
			}
			else if (url.startWith("//")) {
				realUrl = "http:" + url;
			} else {
				realUrl = cosImgUrl + url;
			}
        }else{
            realUrl = url;
        }
    }

    return realUrl;
}

function getTinyPicRealUrl(url){
	var realUrl = "";
	if(url){
		if(url.indexOf("http") == -1 && url.indexOf("HTTP") == -1){
			realUrl = getPicRealUrl(url);
		}else{
			realUrl = url;
		}
	}

	return realUrl;
}
/**
 * 客户端上传图片成功后回调处理
 * @param bucket
 * @param fileId
 * @param fullCid
 * @param fileName
 */
function clientPicCallBack(bucket, fileId, fullCid, fileName, isNeedTree, callBack){
	$.ajax({
		type:'POST',
		url: "album/clientPicCallBack.json",
		data:{
			"bucket":bucket,
			"fileId":fileId,
			"fullCid":fullCid,
			"fileName":fileName,
			"isNeedTree":isNeedTree
		},
		dataType:'json',
		async : true,
		success:function(data){
			//console.log(data);
			if(data.code == 0){
				if(callBack){
					callBack(data.treeJson);
				}
			}else{
				$.fn.message({type:"error", msg:data.msg});
			}
		}
	});
}

/**
 * 判断字符串是否以某字符开头
 */
String.prototype.startWith=function(str){
    if(str==null||str==""||this.length==0||str.length>this.length)
      return false;
    if(this.substr(0,str.length)==str)
      return true;
    else
      return false;
    return true;
};

/**
 * 判断字符串是否以某字符结尾
 */
String.prototype.endWith=function(str){
    if(str==null||str==""||this.length==0||str.length>this.length)
      return false;
    if(this.substring(this.length-str.length)==str)
      return true;
    else
      return false;
    return true;
};


// icon 添加Tooltip

/*
 * form不提交
 * add by fuyi 2015.5.6
 */
$(document).off('submit','form.doNotSubmit');
$(document).on('submit','form.doNotSubmit',function(){
	return false;
});


/*
* 输入框计数
* input外层class(enumerationDiv);
* $(#).iptToEnumeration(maxNum,isTruncation);
* maxNum/最大输入数
* isTruncation/是否截断 0或不传 不截断  1 截断
*
*
*/
(function($){
	$.fn.iptToEnumeration = function(maxNum,isTruncation){
		if(!isTruncation){
			isTruncation = 0;
		}
		var str = '<span class="enumeration myj-hide"><span class="num">0</span>/<span class="maxNum">'+maxNum+'</span></span>';
		this.closest('.enumerationDiv').append(str);
		this.closest('.enumerationDiv').css('position','relative');
		this.closest('.enumerationDiv').attr('maxNum',maxNum);
		if(isTruncation){
			this.attr('maxlength',maxNum);
		}
		var $span = $(this.closest('.enumerationDiv').find('.enumeration'));
		$span.css('position','absolute').css('right','5px').css('top','10px');
	}
})(jQuery);
$(document).on('keyup','.enumerationDiv input[type="text"]',function(){
	var maxNum = $(this).closest('.enumerationDiv').attr('maxNum'),
		num = $(this).val().length;
	$(this).closest('.enumerationDiv').find('.num').text(num);
});
$(document).on('focus','.enumerationDiv input[type="text"]',function(){
	$(this).css('padding-right','40px');
	$(this).closest('.enumerationDiv').find('.enumeration').show();
});
$(document).on('blur','.enumerationDiv input[type="text"]',function(){
	$(this).css('padding-right','12px');
	$(this).closest('.enumerationDiv').find('.enumeration').hide();
});


/*
* add by fuyi at 2016.3.19 with edit window alert and confirm
* myjAlert
* myjAlert.alert({'title':'aaa','content':'你现在测试的是alert!'})
* myjAlert.confirm({'content':'你现在测试的是confirm!','callback':function(v){alert(v)}})
* myjAlert.prompt({'content':'你现在测试的是prompt!','defaultValue':"说点什么吧，亲！",'callback':function(v){alert(v)}})
* myjAlert.loadIframe({'content':'http://www.baidu.com','height':300,'width':500,'callback':function(v){alert('iframe内容加载完成！')}})
*
* options //参数
* typeModal //新版样式
* typeHead //true 没有 false 有 头部
* carriageReturnDetermin //true支持 false不支持 支持回车确认
*
*/
(function(win){
	var tips = {
			'title':'提示',
			'enter':'确定',
			'cancel':'取消',
			'close':'<button type="button" class="close"><span>×</span></button>'
		},
		isIE6 = !window.XMLHttpRequest,
		cssLoaded = false,
		isOpen = false,
		loadCss = function(){
			if(cssLoaded) return;
			var style = document.createElement('link');
			style.type = 'text/css';
			style.rel = 'stylesheet';
			document.getElementsByTagName('head')[0].appendChild(style);
			cssLoaded = true;
		};

	/*************************************对外提供的接口****************************************************/
	var dialog = function(opts){
		return new dialog.prototype.init(opts);
	};

	dialog.prototype = {
		constructor:dialog,
		init:function(opts){
			loadCss();
		},
		alert:function(opts){
			var _this = this;
			var set = extend({
				width:400,
				height:100
			},opts||{});
			if(isOpen) this.close();
			isOpen = true;
			this.doms = createElements(set);
			//this.doms.contentBox.appendChild(document.createElement("<span>"+opts.content+"</span>"));
			var spanTxt = document.createElement("span");
				spanTxt.innerHTML = opts.content;
			this.doms.contentBox.appendChild(spanTxt);
			//this.doms.contentBox.appendChild("<span>");
			setCenter(this.doms);
			this.doms.btnEnter.onclick = function(){
				_this.close();
                typeof opts.callback === 'function' && opts.callback(true);
			};
			if(opts.carriageReturnDetermin){
				this.doms.overlayer.focus();
				this.doms.overlayer.onkeydown = function(e){
					var key = window.event ? e.keyCode : e.which;
					if(+key === 13){
						_this.close();
						opts.callback && opts.callback(true);
					}
				};
				this.doms.contentOuter.onkeydown = function(e){
					var key = window.event ? e.keyCode : e.which;
					if(+key === 13){
						_this.close();
						opts.callback && opts.callback(true);
					}
				};
			}
			/*this.doms.contentTitle.onmousedown = function(e){
				var mousePos= getMousePos(e),pos = getElementPos(_this.doms.contentOuter);
				var _move = move(mousePos,_this.doms.contentOuter,pos.y,pos.x);
				addEvent(document,'mousemove',_move);
				addEvent(document,'mouseup',function(){
					removeEvent(document,'mousemove',_move)
				});
			};*/
			if(this.doms.contentTitle) this.doms.contentTitle.ondragstart = function(){ return false;};
			if(this.doms.close) this.doms.close.onclick = function(){
				_this.close();
                typeof opts.closeCall === 'function' && opts.closeCall();
			};

			addEvent(window,'resize',function(){setCenter(_this.doms);})
		},
		confirm:function(opts){
			var _this = this;
			this.alert(opts);
			this.doms.btnBox.appendChild(this.doms.btnCancel);
			this.doms.btnCancel.onclick = function(){
				_this.close();
                typeof opts.callback === 'function' && opts.callback(false);
			}
		},
		prompt:function(opts){
			var _this = this,input;
			this.alert(opts);
			input = createEl('<input type="text" name="" value="'+opts.defaultValue+'" id="diaglo_prompt_input"/>',this.doms.contentBox);
			input.select();
			this.doms.btnBox.appendChild(this.doms.btnCancel);
			this.doms.btnEnter.onclick = function(){
				_this.close();
                typeof opts.callback === 'function' && opts.callback(input.value);
			};
			this.doms.btnCancel.onclick = function(){
				_this.close();
                typeof opts.callback === 'function' && opts.callback(null);
			};
			this.doms.close.onclick = function(){
				_this.close();
                typeof opts.closeCall === 'function' && opts.closeCall();
			};
		},
		/*load:function(opts){
		 var _this = this;
		 this.alert(opts);
		 this.doms.contentOuter.removeChild(this.doms.btnBox);
		 this.doms.btnEnter.onclick = null;
		 ajax({
		 url:opts.content,
		 success:function(data){
		 _this.doms.contentBox.innerHTML = data;
		 opts.callback && opts.callback(data);
		 }
		 })
		 },*/
		loadIframe:function(opts){
			var _this = this,iframe = document.createElement('iframe');
			this.alert(opts);
			this.doms.contentOuter.removeChild(this.doms.btnBox);
			this.doms.btnEnter.onclick = null;
			iframe.src = opts.content;
			iframe.style.width = '100%';
			iframe.style.height = '100%';
			iframe.frameborder = '0';
			_this.doms.contentBox.innerHTML = '';
			_this.doms.contentBox.appendChild(iframe);
			iframe.attachEvent ? (iframe.attachEvent = _load) : (iframe.onload = _load);
			function _load(){
				opts.callback && opts.callback(iframe);
			}
		},
		close:function(){
			var db = document.body;
			db.removeChild(this.doms.overlayer);
			db.removeChild(this.doms.contentOuter);
			isIE6 && db.removeChild(this.doms.overlayIframe);
			if(this.doms.contentTitle) this.doms.contentTitle.onmousedown = null;
			if(this.doms.close) this.doms.close = null;
			this.doms.btnEnter.onclick = this.doms.btnCancel.onclick = null;
			this.doms = null;
			isOpen = false;
		}
	};

	dialog.prototype.init.prototype = dialog.prototype;
	win.regMethod = function(scope,handler){
		return scope[handler]= dialog();
	};

	/**********************************私有方法*******************************************************/
	function extend(subClass,superClass){
		for(var key in superClass) subClass[key] = superClass[key];
		return subClass;
	}
	function createElements(opts){
		var db = document.body,
			h = Math.max(document.documentElement.clientHeight,document.body.offsetHeight),
			width = opts.width,
			height = opts.height,
			overlayer = null,
			overlayIframe = null,
			contentOuter = null,
			contentTitle = null,
			close = null,
			contentBox = null,
			btnBox = null,
			btnEnter = null,
			btnCancel = null;

		//是否使用新的弹层
		if(opts && opts.typeModal === 'newModal'){
			overlayer = createEl('<div class="modal-alert-overlayer" tabindex="0"></div>', db);
			overlayIframe = isIE6 && createEl('<iframe class="dialog_HideSelect" marginwidth="0" marginheight="0" align="top" scrolling="no" frameborder="0" src=""' +
				' style="position:absolute;top:0;left:0;width:100%;height:'+h+'px;filter: Alpha(Opacity=0);"></iframe>', db);
			contentOuter = createEl('<div class="modal-alert" style="width:'+width+'px;" tabindex="0"></div>', db);
			//是否有头部
			if(!(opts && opts.typeHead)){
				contentTitle = createEl('<div class="modal-alert-head"><h3>'+ (opts.title || tips.title) +'</h3></div>', contentOuter);
				close = createEl('<a class="modal-alert-btn-close" href="javascript:">'+ (opts.close || tips.close) +'</a>', contentTitle);
			}else{
				contentTitle = createEl('<div class="modal-alert-head"></div>', contentOuter);
			}
			contentBox = createEl('<div class="modal-alert-content"></div>', contentOuter);
			btnBox = createEl('<div class="modal-alert-footer"></div>', contentOuter);
			btnEnter = createEl('<button class="button btn-determine" type="button">'+ (opts.enter||tips.enter) +'</button>', btnBox);
			btnCancel = createEl('<button class="button btn-gray" type="button">'+(opts.cancel|| tips.cancel) +'</button>');
		}else{
			overlayer = createEl('<div id="dialog_overlayer" style="height:'+h+'px;"></div>',db);
			overlayIframe = isIE6 && createEl('<iframe marginwidth="0" marginheight="0" align="top" scrolling="no" frameborder="0" class="dialog_HideSelect" src="" style="position:absolute;top:0;left:0;width:100%;height:'+h+'px;filter: Alpha(Opacity=0);"></iframe>',db);
			contentOuter = createEl('<div id="dialog_window" style="width:'+width+'px;"></div>', db);
			contentTitle = createEl('<div id="dialog_title"><h3>'+ (opts.title || tips.title) +'</h3></div>', contentOuter);
			close = createEl('<a href="javascript:" id="dialog_btn_close" >'+ (opts.close || tips.close) +'</a>', contentTitle);
			contentBox = createEl('<div id="dialog_Content"></div>', contentOuter);
			btnBox = createEl('<div id="dialog_btnBox"></div>', contentOuter);
			btnEnter = createEl('<button type="button" class="btn btn-primary" id="dialog_btn_enter">'+ (opts.enter||tips.enter) +'</button>',btnBox);
			btnCancel = createEl('<button type="button" class="btn btn-default" id="dialog_btn_cancel">'+(opts.cancel|| tips.cancel) +'</button>');
		}

		return {
			overlayer: overlayer,
			overlayIframe: overlayIframe,
			contentOuter: contentOuter,
			contentTitle: contentTitle,
			close: close,
			contentBox: contentBox,
			btnBox: btnBox,
			btnEnter: btnEnter,
			btnCancel: btnCancel
		};
	}
	function createEl(str,parent){
		var div = document.createElement('div'),el;
		div.innerHTML = str;
		el = div.firstChild;
		return parent ? parent.appendChild(el) : el;
	}
	function setCenter(doms){
		if(doms && doms.contentOuter){
			var T = doms.contentOuter,w = T.offsetWidth,h = T.offsetHeight,timer = null;
			var dd = document.documentElement,W = dd.clientWidth,H = dd.clientHeight;
			//T.style.top = (H-h)/2+'px';
			T.style.left = (W-w)/2+'px';
			if(isIE6){
				window.onscroll = function(){
					if(timer) clearTimeout(timer);
					timer = setTimeout(function(){
						var t = Math.max(document.body.scrollTop,document.documentElement.scrollTop);
						T.style.top = (t+H-h)/2+'px';
					},100);
				}
			}
		}
	}
	/*function getMousePos(e){
		e = e || window.event;
		if(e.pageX || e.pageY) return { left:e.pageX,top:e.pageY};
		return {
			left:e.clientX + document.documentElement.scrollLeft - document.body.clientLeft,
			top:e.clientY + document.documentElement.scrollTop - document.body.clientTop
		};
	};*/
	function addEvent(el,type,fn){
		if(el.addEventListener != undefined){
			el.addEventListener(type,fn,false);
		}else if(el.attachEvent != undefined){
			el.attachEvent('on'+type,fn)
		}else{
			el['on'+type] = fn;
		}
	}
	function removeEvent(el,type,fn){
		if(el.removeEventListener != undefined){
			el.removeEventListener(type,fn,false);
		}else if(el.detachEvent != undefined){
			el.detachEvent('on'+type,fn);
		}else{
			el['on'+type] = function(){};
		}
	}
	/*function move(oldPos,target,t,l){
		return function(e){
			var newPos = getMousePos(e);
			target.style.top = t + (newPos.top - oldPos.top) + 'px';
			target.style.left = l + (newPos.left - oldPos.left) + 'px';
		};
	};*/
	function getElementPos(el){
		var x = 0,y=0;
		if(el.getBoundingClientRect){
			var pos = el.getBoundingClientRect();
			var d_root = document.documentElement,db = document.body;
			x = pos.left + Math.max(d_root.scrollLeft,db.scrollLeft) - d_root.clientLeft;
			y = pos.top + Math.max(d_root.scrollTop,db.scrollTop) - d_root.clientTop;
		}else{
			while(el != db){
				x += el.offsetLeft;
				y += el.offsetTop;
				el = el.offsetParent;
			}
		}
		return {
			x:x,
			y:y
		};
	}
	function ajax(opts){
		var xhr = null;
		var set = extend({
			type:'GET',
			url:''
		},opts||{});
		if(typeof window.XMLHttpRequest != 'undefined'){
			xhr = new window.XMLHttpRequest();
		}else{
			xhr = new ActiveXObject('MSXML2.XmlHttp.6.0');
		}
		xhr.open(set.type,set.url);
		xhr.onreadystatechange = function(){
			if(xhr.readyState == 4){
				if(xhr.status >= 200 && xhr.status <= 304 ){
					set.success && set.success(xhr.responseText);
				}else{
					set.failure && set.failure(xhr.status);
				}
			}
		};
		xhr.send(null);
	}
})(window);
regMethod(window,'myjAlert');


//获取设置图片真实宽高的方法
function getImgRealPX(url,id){
	var $IMG = new Image();
    $IMG.src = url;
    $IMG.onload = function(){
        var width = $IMG.width;
        var height = $IMG.height;
        var imgDom = $('body img[src="'+url+'"]');
        if(imgDom.length > 0){
            var text = width + ' X ' + height;
            imgDom.closest('.out').find('.imgSize').html(text);
        }
    }
}
//（通用）获取设置图片真实宽高的方法
function generalGetImgRealPX(url,call){
    var $i = new Image();
    $i.src = url;
    $i.onload = function(){
        call && call({w:$i.width,h:$i.height});
    };
}
//获取当前location
var gitWinLocHref = function(){
	var location = (window.location.href).split('/');
	var basePath = location[0]+'//'+location[2]+'/';
	return basePath;
};
//获取url所带参数
var getQueryString = function(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  r[2]; return null;
};
//获取传入的url所带参数
var getUrlParameter = function(url, name){
	var r = null,
		urlArr = url.indexOf('?') !== -1 ? url.split('?') : [];

	if(urlArr && urlArr.length > 1){
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		r = urlArr[1].match(reg);
	}
	if(r != null) return r[2]; return null;
};
//判断obj是不是空的 空的返回true,不空返回false;
var isEmptyObject = function(e){
	var t;
	for(t in e)
		return !1;
	return !0;
};
//产品列表sku展开收起共用
$(document).on('click','.productListSkuShow',function(){
	var value = $(this).attr('data-value'),
		td = $(this).closest('td'),
		table = $(this).closest('table');
	if(!+value){//==0 状态为隐藏 方案为‘+ 展开SKU’
		/*table.find('tr.otherSkuTr').hide();
        table.find('a.productListSkuShow').attr('data-value',0).html('+ 展开');*/
		td.find('tr.otherSkuTr').show();
		$(this).attr('data-value',1).html('- 收起');
	}else{//==1 状态为隐藏 方案为‘- 收起SKU’
		td.find('tr.otherSkuTr').hide();
		$(this).attr('data-value',0).html('+ 展开');
	}
});

/****价格类输入框限制输入数字、负数、小数点以外的字符****/
function  validateNumeric(strValue) {
	var objRegExp  =  /(^-?\d\d*\.\d{0,2}$)|(^-?\d\d*$)/;
	return objRegExp.test(strValue);
}

//onkeyup事件
function clearNoNum(obj){
	var ipt = $(obj);
	if(!validateNumeric(ipt.val()))
	{
        ipt.val(ipt.val().replace("-.","%$%").replace(/\-\./g,"").replace("%$%","-"));
        ipt.val(ipt.val().replace(".-","$#$").replace(/\.\-/g,"").replace("$#$","."));
        ipt.val(ipt.val().replace(/[^-\d.]/g,"")); //清除"数字"和"."以外的字符
        ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
        // ipt.val(ipt.val().replace(/\d{7,}./g,"."));
        ipt.val(ipt.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));//只保留第一个. 清除多余的
        ipt.val(ipt.val().replace("-","$%$").replace(/\-/g,"").replace("$%$","-"));//只保留第一个- 清除多余的
        ipt.val(ipt.val().replace(/\d{1,}-|\d{1,}\.\d{1,2}-/,""));//不能在数字后面输入-
        ipt.val(ipt.val().replace(/^(-)?(\d+)\.(\d\d).*$/,'$1$2.$3')); //只能输入两个小数
	}
}
/****价格类输入框限制输入end****/
/****价格类输入框限制输入负数可以输小数点，保留一位小数****/
var validateNumMinusOne = function(strValue) {
    var objRegExp  =  /(^\d\d*\.\d{0,1}$)|(^\d\d*$)/;
    return objRegExp.test(strValue);
};
var clearNoNumAndMinusOne = function (obj) {
    var ipt = $(obj);
    if(!validateNumMinusOne(ipt.val()))
    {
        ipt.val(ipt.val().replace(/[^\d.]/g,"")); //清除"数字"和"."以外的字符
        ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
        ipt.val(ipt.val().replace(/\.{2,}/g,".")); //只保留第一个. 清除多余的
        // ipt.val(ipt.val().replace(/\d{7,}./g,".")); //只保留第一个. 清除多余的
        ipt.val(ipt.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
        ipt.val(ipt.val().replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3')); //只能输入两个小数
    }
};
/****价格类输入框限制输入负数可以输小数点，保留一位小数END****/
/****价格类输入框限制输入负数可以输小数点，保留两位小数****/
var validateNumMinus = function(strValue) {
    var objRegExp  =  /(^\d\d*\.\d{0,2}$)|(^\d\d*$)/;
    return objRegExp.test(strValue);
};
var clearNoNumAndMinus = function (obj) {
    var ipt = $(obj);
    if(!validateNumMinus(ipt.val()))
    {
        ipt.val(ipt.val().replace(/[^\d.]/g,"")); //清除"数字"和"."以外的字符
        ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
        ipt.val(ipt.val().replace(/\.{2,}/g,".")); //只保留第一个. 清除多余的
        // ipt.val(ipt.val().replace(/\d{7,}./g,".")); //只保留第一个. 清除多余的
        ipt.val(ipt.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
        ipt.val(ipt.val().replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')); //只能输入两个小数
    }
};
/****价格类输入框限制输入负数可以输小数点，保留两位小数END****/
/****价格类输入框限制输入负数可以输小数点，保留小数点后三位****/
var validateNumMinusDecimal3 = function(strValue) {
    var objRegExp  =  /(^\d\d*\.\d{0,3}$)|(^\d\d*$)/;
    return objRegExp.test(strValue);
};
var clearNoNumAndMinusDecimal3 = function (obj) {
    var ipt = $(obj);
    if(!validateNumMinusDecimal3(ipt.val()))
    {
        ipt.val(ipt.val().replace(/[^\d.]/g,"")); //清除"数字"和"."以外的字符
        ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
        ipt.val(ipt.val().replace(/\.{2,}/g,".")); //只保留第一个. 清除多余的
        // ipt.val(ipt.val().replace(/\d{7,}./g,".")); //只保留第一个. 清除多余的
        ipt.val(ipt.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
        ipt.val(ipt.val().replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3')); //只能输入两个小数
    }
};
/****价格类输入框限制输入负数可以输小数点，保留小数点后三位END****/
/****价格类输入框限制输入负数可以输小数点，保留小数点后5位****/
var validateNumMinusDecimal5 = function(strValue) {
    var objRegExp  =  /(^\d\d*\.\d{0,5}$)|(^\d\d*$)/;
    return objRegExp.test(strValue);
};
var clearNoNumAndMinusDecimal5 = function (obj) {
    var ipt = $(obj);
    if(!validateNumMinusDecimal5(ipt.val()))
    {
        ipt.val(ipt.val().replace(/[^\d.]/g,"")); //清除"数字"和"."以外的字符
        ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
        ipt.val(ipt.val().replace(/\.{2,}/g,".")); //只保留第一个. 清除多余的
        // ipt.val(ipt.val().replace(/\d{7,}./g,".")); //只保留第一个. 清除多余的
        ipt.val(ipt.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
        ipt.val(ipt.val().replace(/^(\-)*(\d+)\.(\d\d\d\d\d).*$/,'$1$2.$3')); //只能输入两个小数
    }
};
/****价格类输入框限制输入负数可以输小数点，保留小数点后5位END****/
/****价格类输入框限制输入小数点可以输负数****/
var validateNumFloat = function(strValue) {
    var objRegExp  =  /^-?\d\d*$/;
    return objRegExp.test(strValue);
};
var clearNoNumAndFloat = function (obj) {
    var ipt = $(obj);
    if(!validateNumFloat(ipt.val()))
    {
        ipt.val(ipt.val().replace(/[^-\d]/g,"")); //清除"数字"和"."以外的字符
        ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
        ipt.val(ipt.val().replace(/[-]{2,}/g,"-")); //只保留第一个. 清除多余的
        ipt.val(ipt.val().replace("-","$%$").replace(/\-/g,"").replace("$%$","-"));
        ipt.val(ipt.val().replace(/\d{1,}-|\d{1,}-\d{1,}/,""));
    }
};
/****价格类输入框限制输入小数点可以输负数END****/
/****价格类输入框4位限制输入****/
function clearNoNumFour(obj){
    var ipt = $(obj);
    if(!validateNumeric(ipt.val()))
    {
        ipt.val(ipt.val().replace(/[^\d.]/g,"")); //清除"数字"和"."以外的字符
        ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
        ipt.val(ipt.val().replace(/\.{2,}/g,".")); //只保留第一个. 清除多余的
        ipt.val(ipt.val().replace(/\d{7,}./g,".")); //只保留第一个. 清除多余的
        ipt.val(ipt.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
        ipt.val(ipt.val().replace(/^(\-)*(\d+)\.(\d\d\d\d).*$/,'$1$2.$3')); //只能输入四个小数
    }
}
/****价格类输入框4位限制输入End****/

/****小数类输入框限制输入****/
function  validateDecimal(strValue) {
	var objRegExp  =  /(^-?\d\d*\.\d{0,15}$)|(^-?\d\d*$)|(^-?\.\d{0,15}$)/;
	return objRegExp.test(strValue);
}

//onkeyup事件
function clearDecimalNum(obj){
	var ipt = $(obj);
	if(!validateDecimal(ipt.val()))
	{
		ipt.val(ipt.val().replace(/[^\d.]/g,"")); //清除"数字"和"."以外的字符
		ipt.val(ipt.val().replace(/^\./g,"")); //验证第一个字符是数字而不是
		ipt.val(ipt.val().replace(/\.{2,}/g,".")); //只保留第一个. 清除多余的
		ipt.val(ipt.val().replace(".","$#$").replace(/\./g,"").replace("$#$","."));
		//ipt.val(ipt.val().replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3')); //只能输入两个小数
	}
}
/****小数类输入框限制输入end****/
/****非中文输入框限制输入****/
function  validateCJK(strValue) {
    var objRegExp  =  /[\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FBB\uF900-\uFA2D\uFA30-\uFA6A\uFA70-\uFAD9\uFF00-\uFFEF\u2E80-\u2EFF\u3000-\u303F\u31C0-\u31EF]/;
    return objRegExp.test(strValue);
}

//onkeyup事件
function clearCJK(obj){
    var ipt = $(obj);
    if(validateCJK(ipt.val()))
    {
        ipt.val(ipt.val().replace(/[\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FBB\uF900-\uFA2D\uFA30-\uFA6A\uFA70-\uFAD9\uFF00-\uFFEF\u2E80-\u2EFF\u3000-\u303F\u31C0-\u31EF]/g,"")); //清除"数字"和"."以外的字符
    }
}
/****非中文输入框限制输入end****/
/****字母和数字输入框限制输入****/
function  validateCN(strValue) {
	var objRegExp  =  /[^a-zA_Z0-9]*$/;
	return objRegExp.test(strValue);
}

//onkeyup事件
function validateInCN(obj){
	var ipt = $(obj);
	if(validateCN(ipt.val()))
	{
		ipt.val(ipt.val().replace(/[^a-zA_Z0-9]/g,"")); //清除"数字"和"字母"以外的字符
	}
}
/****字母和数字输入框限制输入end****/

/****限制输入除数字、字母、空格、汉字之外的其它字符****/
var validateSpecialCharacter = function (strValue) {
	var objRegExp = /[^a-zA-Z0-9\u4e00-\u9fa5\s]+/g;
    return objRegExp.test(strValue);
};
var clearNoSpecialCharacter = function (obj){
	var $ipt = $(obj),
		val = $ipt.val();
	if(validateSpecialCharacter(val)) {
		$ipt.val(val.replace(/[^a-zA-Z0-9\u4e00-\u9fa5\s]+/g,'')); //清除数字、字母、空格和汉字以外的字符
	}
};
/****字限制输入除数字、字母、空格、汉字之外的其它字符end****/

/****整数类输入框限制输入****/
//onkeyup事件
function clearMistakeNumber(obj){
	var ipt = $(obj);
	var objRegExp  =  /[^0-9]/;
	if(objRegExp.test(ipt.val())){
		ipt.val(ipt.val().replace(/[^\d]/g,""));
	}
}
/****整数类输入框限制输入end****/

/****电话类输入框限制输入****/
//onkeyup事件
function clearPhoneFormat(obj){
	var ipt = $(obj);
	var objRegExp  =  /[^0-9,-]/;
	if(objRegExp.test(ipt.val())){
		ipt.val(ipt.val().replace(/[^0-9,-]/g,""));
	}
}
/****电话类输入框限制输入end****/

/** 价格验证 start**/
function verifyPrice(str){
	if(str.match(/^(:?(:?\d+.\d+)|(:?\d+))$/)) return true;
	else false;
}
/** 价格验证 end**/

//二级下拉菜单 阻止主按钮点击关闭下拉层
$(document).off('click','.dropdown-menu .removefromcart');
$(document).on('click','.dropdown-menu .removefromcart',function(e){
	e.stopPropagation();
});

/**字符串与unicode互转**/
//to code
function strToUc(str) {
	if ('' != str) {
		var st, t, i;
		st = '';
		for (i = 1; i <= str.length; i ++){
			t = str.charCodeAt(i - 1).toString(16);
			if (t.length < 4)
				while(t.length <4)
					t = '0'.concat(t);
			t = t.slice(2, 4).concat(t.slice(0, 2));
			st = st.concat(t);
		}
		return(st.toUpperCase());
	}else {
		return('');
	}
}
//to str
function ucToStr(str) {
	if ('' != str) {
		var st, t, i;
		st = '';
		for (i = 1; i <= str.length/4; i ++){
			t = str.slice(4*i-4, 4*i-2);
			t = str.slice(4*i-2, 4*i).concat(t);
			st = st.concat('%u').concat(t);
		}
		st = unescape(st);
		return(st);
	}else{
		return('');
	}
}
/**字符串与unicode互转 end**/

/*
* localstorage set and get
* add 2016.8.19 fuyi
*
* 设(string)     myjStorage.set(key,string)()
* 设(obj)        myjStorage.objSet(key,obj)()
* 取(string)     myjStorage.get(key)()
* 取(obj)        myjStorage.objGet(key)()
* 查             myjStorage.storageObj()  返回整个storageObj对象
* 删             myjStorage.remove(key)()  销毁storageObj中的key
* 查Key          myjStorage.getKeyName()()  返回arr storageObj中的key
* 时间比较       myjStorage.dateDiff(DateOne,DateTwo)   日期格式 YYYY/MM/dd 返回Number
*
* use key :
*           userId_indexChart （index图表操作）
*           userId_needNoticeId
*           userId_globalWarehoseZ
*           userId_globalPairWarehoseZ
*           userId_offSmallYellowUser（用户订单页可关闭小黄条）
*           createRUCT (index右侧上面广告关闭时间)
*           createRDCT (index右侧下面广告关闭时间)
*           wish_listImgType_add (wish变种图是否显示)
*           t1001(用户列表搜索type)t_userId:{wish:0or1;purWare:0or1;} 产品wish  采购和仓库列表 purWare
*			t1002(用户处理错误订单){t_userId:date}(hhj)
*           t1003(用户是否点击分批次发货)
*           t1007(扫描校验是否启用只校验，不打印)
*           t1007_sku / t1007_item_single / t1007_items_single / t_1007_item_batch / t_1007_items_batch(扫描校验包裹类型/扫描校验单品单数（单个）/单品多数（单个）/ 单品单数（批量）/ 单品多数（批量） )
*           t1007_print_design（扫描立即打印设置）
*           t1008(扫描发货)
*           wave（波次)
*           wave_create_storage/波次生成上一次选的仓库Id
*         	wave_manage_storage/波次管理上一次选的仓库Id
*         	wave_twice_sort_sku_type/二次分拣上一次所选的扫描类型
*       	wave_scan_sku_type/扫描包装上一次所选的扫描类型
*           scanShipWeightUnit (波次扫描出库 重量记忆)
*           img_resize(图片改尺寸用户上次用值){min:number,set:number}
*           init_repository (初始化仓库选中值){type:Array}
*           allocated_has_moduleName(记录用户在待打单-有货所选的板块 normal:常规板块；group:分组板块)
*			stat_01A(记录数据模块-各个子版块的统计图维度选择和币种选择和统计图类型)
*			wishBatchGuide(记录用户是否进入过wish批量编辑并点击)
*/
var myjStorage = {
    storageObj : function(){
        return window.localStorage;
    },
    get : function(key){
        return function(){
            return this.myjStorage.storageObj().getItem(key);//this (window)
        };
    },
	objGet : function(key){
		return function(){
			return JSON.parse(this.myjStorage.storageObj().getItem(key));//this (window)
		};
	},
    set : function(key,value){
		return function(){
			this.myjStorage.storageObj().setItem(key,value);
		}
	},
	objSet : function(key,value){
		return function(){
			this.myjStorage.storageObj().setItem(key,JSON.stringify(value));
		}
	},
    remove : function(key){
        return function(){
            this.myjStorage.storageObj().removeItem(key);
        }
    },
    dateDiff : function(DateOne,DateTwo){
		var DateOne = DateOne.replace(/-|\//g,'');
		var DateTwo = DateTwo.replace(/-|\//g,'');
		var cha = 1;
		if(DateOne == DateTwo){
			cha = 0;
		}
        //return Math.abs(cha);
        return cha;
    },
    getKeyName : function(){
        return function(){
            var arr = [];
            $.each(this.myjStorage.storageObj(),function(keyName,value){
                arr.push(keyName);
            });
            return arr;
        }
    }

};
/***localstorage set and get***/

//类目搜索（单级内）
$(document).off('keyup','.categoryChooseOutDiv .serch input[type="text"]');
$(document).on('keyup','.categoryChooseOutDiv .serch input[type="text"]',function(){
    var searchValue = $.trim($(this).val()),
        $list = $(this).closest('.categoryChooseInDiv').find('div.categoryDiv'),
        html = '',
		newStrArr = []; //根据查询字段把转了小写之后的数据字段进行分割
    if(searchValue){
        $list.addClass('myj-hide');
        $.each($list,function (i,j) {
            html = $(j).find('.categoryNames').text();
            $(j).attr('content',html);
            newStrArr = html.toLowerCase().split(searchValue.toLowerCase());
            if(newStrArr && newStrArr.length > 1){
                $(j).removeClass('myj-hide').find('.categoryNames').html(returnSearchContentMarkedRedResult(html,searchValue));
			}
        });
    }else{
        $list.removeClass('myj-hide');
        $.each($list,function (i,j) {
            $(j).find('.categoryNames').html($(j).attr('content'));
        });
    }
});
//搜索内容标红方法
var returnSearchContentMarkedRedResult = function (searchOriginalCon,searchCon) {
	if(!searchOriginalCon || !searchCon) return searchOriginalCon;
	var toLowerCaseCategoryNames = searchOriginalCon.toLowerCase(),//转小写之后的数据字段
		toLowerCaseVal = searchCon.toLowerCase(),//转小写之后的查询字段
		newStrArr = toLowerCaseCategoryNames.split(toLowerCaseVal), //根据查询字段把转了小写之后的数据字段进行分割
		joinStr = '',//用来拼接转了小写之后的数据字段，主要是为了截取原数据字段中的内容
		dataStr = '',//用来接收截取出来的原数据字段内容
		endStr = '',//用来接收拼接完之后的原数据字段
		length = newStrArr.length;
	for(var i = 0; i < length; i++){
		dataStr = searchOriginalCon.substring(joinStr.length, joinStr.length + newStrArr[i].length);//这里每次循环获取到的内容就是没有转小写的原数据字段
		joinStr += newStrArr[i]; //这里把当前循环数组下标中转了小写的内容拼接起来
		/*这里把上面获取的没转小写的内容拼接上要查询的内容,然后把要查询的内容用标签包起来字体变红色
		 * htmls.substring(joinStr.length,joinStr.length+toLowerCaseVal.length)获取当前循环内容长度到当前循环内容长度加上查询内容长度之间的内容，这个内容就是没转小写的要查询内容
		 */
		if(i === (length-1)){
			endStr += dataStr;
		}else{
			endStr += dataStr + '<span class="f-red">' + searchOriginalCon.substring(joinStr.length,joinStr.length+toLowerCaseVal.length) + '</span>';
		}
		joinStr += toLowerCaseVal; //把上面拼接的循环内容在加上要查询的内容,用来下次循环的时候截取下一段内容
	}
	return endStr;

};

/*
* 产品订单显示条目数
*
* objNode，判断是否为order用Table
*/
function showSelCheckboxNum(obj,objNode){
	var $id = $(obj).attr('selIptId'),
		$table = $(obj).closest('table'),
		$selectAll = $table.find('[selIptId="selectAll"]'),
		tableCeil = $(obj).closest('body').find('#ceilingDiv').find('#selectedData'),
		normalInput = $table.find('input[datename = "showdate"]'),
		inputNumtwo = normalInput.length,
		numtd = 0,
		checkedNum = '',
		selectAllState = 0;

	numtd = $table.children("thead").children('tr:first-child').children('th').length;
	if(+numtd === 0) numtd = 9; //兼容不规范列表

	var $newStr = $('<tr class="show-sel-check-num" selId="showSelCheckboxNum"  id="showSelCheckboxNum">' +
		'<td class="check-num-con" colspan="'+numtd+'">' +
		'<span class="initialize"><span class="check-num-in showSelCheckboxNumIn"></span>' +
		'<span class="ctrl-selection ctrlSelection"></span></span></td></tr>'),
		$ceilStr = $('<span class="initialize">&nbsp;&nbsp;已选中<span class="checkedNum"></span>条数据<span class="ctrlSelection"></span></span>');

	if ($id == 'selectAll'){ //判断全选
		if($selectAll.is(':checked')){
			normalInput.prop('checked',true);
			selectAllState = 1;
		}else{
			normalInput.prop('checked',false);
		}
	}
	checkedNum = $table.find('input[datename = "showdate"]:checked').length;
	if (inputNumtwo == checkedNum) { //判断全选选中
		if (!$selectAll.is(':checked')){
			$selectAll.prop('checked', true);
			selectAllState = 1;
		}
	} else {
		if ($selectAll.is(':checked')) {
			$selectAll.prop('checked', false);
		}
	}
	if(checkedNum > 0){ //添加小黄条
		if (!+$('tr[selId="showSelCheckboxNum"]').length){
			$table.find('tr:first').after($newStr);
		}
		$ceilStr.find('.checkedNum').html(checkedNum);
		tableCeil.html($ceilStr);
		$('tr[selId="showSelCheckboxNum"] span.showSelCheckboxNumIn').html('已选中'+checkedNum+'条数据');
		if($selectAll.is(':checked')){ //再次判断全选是否选中
			selectAllState = 1;
		}
		if(objNode == 'order'){
			ctrSelectionNum(selectAllState);
		}
		if(objNode == 'orderUpdate'){
			ctrUpdateSelectionNum(selectAllState);
		}
	}else{
		$table.find('tr[selId="showSelCheckboxNum"]').remove();
		tableCeil.empty();
	}
}

/*
* 物流追踪显示条目数
*
*/
function showSelCheckboxTrackNum(obj){
    var $id = $(obj).attr('selIptId'),
        $table = $(obj).closest('table'),
        $selectAll = $table.find('[selIptId="selectAll"]'),
        tableCeil = $(obj).closest('body').find('#ceilingDiv').find('#selectedData'),
        normalInput = $table.find('input[datename = "showdate"]'),
        inputNumtwo = normalInput.length,
        numtd = $table.find("tr").eq(2).find('td').length,
        checkedNum = '',
        selectAllState = 0;
    if(numtd < 8){
        numtd = 12;
    }
    var $newStr = $('<tr class="show-sel-check-num" selId="showSelCheckboxNum"  id="showSelCheckboxNum">' +
        '<td class="check-num-con" colspan="'+numtd+'">' +
        '<span class="initialize"><span class="check-num-in showSelCheckboxNumIn"></span>' +
        '<span class="ctrl-selection ctrlSelection"></span></span></td></tr>'),
        $ceilStr = $('<span class="initialize">&nbsp;&nbsp;已选中<span class="checkedNum"></span>条数据<span class="ctrlSelection"></span></span>');
    if ($id == 'selectAll'){ //判断全选
        if($selectAll.is(':checked')){
            normalInput.prop('checked',true);
            selectAllState = 1;
        }else{
            normalInput.prop('checked',false);
        }
    }
    checkedNum = $table.find('input[datename = "showdate"]:checked').length;
    if (inputNumtwo == checkedNum) { //判断全选选中
        if (!$selectAll.is(':checked')){
            $selectAll.prop('checked', true);
            selectAllState = 1;
        }
    } else {
        if ($selectAll.is(':checked')) {
            $selectAll.prop('checked', false);
        }
    }
    if(checkedNum > 0){ //添加小黄条
        if (!+$('tr[selId="showSelCheckboxNum"]').length){
            $table.find('tr:first').after($newStr);
        }
        $ceilStr.find('.checkedNum').html(checkedNum);
        tableCeil.html($ceilStr);
        $('tr[selId="showSelCheckboxNum"] span.showSelCheckboxNumIn').html('已选中'+checkedNum+'条数据');
        if($selectAll.is(':checked')){ //再次判断全选是否选中
            selectAllState = 1;
        }

    }else{
        $table.find('tr[selId="showSelCheckboxNum"]').remove();
        tableCeil.empty();
    }
};

/*
 *New页面全选等处理
 * checkbox选择范围扩大到整个td,并显示选中条数
 * 注册方法：$('.a').setClick(setting);
 * setting 设置为 1 时显示选中条数;
 * input父级必须添加name，两级以上列表父级为 name="header"，单级父级列表为 name="content"
 * 全选input添加 iptId="selectAll"，单个input添加 iptId="selectSingle"
 * 吸顶数据显示在 .selectedData 标签中
 * */

/*
* table 表头吸顶效果
* (必加)在包裹着table的div上添加class名 ceilingType
* *需要保持thead和tbody的th与td都带有一致的min-width属性
* tbody必须存在数据 不然无法产生吸顶效果
* 如果表格用的是标准盒子模型 thead或tbody任意一个加了padding另一个需同样加上padding
* 如果表格用的是怪异盒子模型 tbody加了padding thead不需要加padding 同时加padding会导致吸顶时表头表格不对齐
* 当页面需要两个table切换做吸顶效果时，需要在一个吸顶时移除另一个table父级元素div的class
* * 注册方法：$('.ceilingType').ceilingType();*/
(function($,W){
    $.fn.setClick = function(o){
        var _this = $(this),
            checkBoxLen = '',
            setting = o && o.setting ? o.setting : false;
        var checkboxCheck = function(e,obj,objNode){
            e = e || event;
            e.stopPropagation();
            var iptId = $(obj).attr('iptId'),
                table = $(obj).closest('table'),
                selected = $(obj).is(':checked'),
                selectedAll = table.find('[iptId="selectAll"]').is(':checked'),
                numtd = table.children("thead").children('tr:first-child').children('th').length,
                header =  $(obj).closest('tr[name = "header"]').length,
                tableCeil = $(obj).closest('body').find('.ceilingDiv').find('.selectedData'),
                newStr = '';
            if (iptId === 'selectAll'){
                var checked = '';
                $(obj).closest('table').find('input[iptId="selectSingle"]').each(function(){
                    checked = $(this).is(":checked");
                    if(selected && !checked){
                        $(this).click();
                    }else if(!selected && checked){
                        $(this).click();
                    }
                });
            }else{
                if(selected){
                    if(header){
                        $(obj).closest('tr[name = "header"]').addClass('selected').next('tr[name = "content"]').addClass('selected');
                    }else{
                        $(obj).closest('tr[name = "content"]').addClass('selected')
                    }
                }else{
                    if(header){
                        $(obj).closest('tr[name = "header"]').removeClass('selected').next('tr[name = "content"]').removeClass('selected');
                    }else{
                        $(obj).closest('tr[name = "content"]').removeClass('selected')
                    }
                }
            }
            var inputNumtwo = $(table).find('input[iptId="selectSingle"]').length,
                checkedNum = $(table).find('input[iptId="selectSingle"]:checked').length;
            if(inputNumtwo == checkedNum && !selectedAll){
                table.find('[iptId="selectAll"]').prop('checked',true);
            }else if(inputNumtwo != checkedNum && selectedAll){
                table.find('[iptId="selectAll"]').prop('checked',false);
            }
            if(objNode){
                newStr = $('<tr class="showSelCheckboxNum articles-num-box" id="showSelCheckboxNum"><td ' +
					'class="articles-num-title articlesNumBox" colspan="'+numtd+'"><span class="articles-num-con articlesNumCon"></span><span class="articlesNumConAdd"></span></td></tr>');
                if(checkedNum > 0){
                    if (!+$('tr.showSelCheckboxNum').length){
                        table.find('tr:first').after(newStr);
                    }
                    tableCeil.html('&nbsp;&nbsp;已选中'+checkedNum+'条数据');
                    $('tr.showSelCheckboxNum .articlesNumCon').html('已选中<span class="f-black m-left5 m-right5 f-w600">'+checkedNum+'</span>条数据');
                    if(setting){
                        var transferObj = {
                            setting : setting
                        };
                        o && o.callBack && o.callBack(transferObj);
					}
                }else{
                    table.find('tr.showSelCheckboxNum').remove();
                    tableCeil.empty();
                }
            }
        };
        _this.on('click',function(){
            checkBoxLen = $(this).find('[type="checkbox"]').length;
            if(checkBoxLen == 1){
                $(this).find('[type="checkbox"]').click();
            }
        });
        _this.find('[type="checkbox"]').on('click',function (e) {
            e.stopPropagation();
            checkboxCheck(e,this,setting);
        });
    };
    $.fn.ceilingType = function () {
        var $ceilingType = $('.ceilingType'),
            $td = $ceilingType.find('tbody:first').find('td');

        if($td.length >= 2 ){  //判断是否需要吸顶效果
            var $thead = $ceilingType.find('thead:first'),
                $tbody = $ceilingType.find('tbody:first'),
				$td = $tbody.find('tr:first > td'),
                oTop = $thead.offset().top,
                $Caret = $('[name="Caret"]'),
                $tooltip = $('[data-toggle="tooltip"]'),
                $popPurCost = $('.pop-pur-cost');

            W.onscroll = function(){  //鼠标滚动事件
                var $width = $tbody.find('tr:first').find('td'),
					height = $thead.height();
                if(W.pageYOffset > oTop){	//大于ul与顶端的距离时，固定定位
                    var left = $ceilingType[0].getBoundingClientRect().left,
                        addWidth = document.body.scrollLeft;
                    $thead.css({
                        'position': 'fixed',
                        'top': '0',
                        'left': left,
                        'z-index': '99'
                    });
                    //$('.ceilingType').css({'padding-top': height});
                    $.each($width,function (i,j) {
                        var elseWidth = j.offsetWidth - 10, //tbody 下 tr 每个 td 的 width
                            gapNum = $width.length - $thead.find('tr:last').find('th').length, //表头最后一行tr 与 tbody 的 td 差数
                            $th;
                        if($thead.find('tr').length === 1 || i < gapNum){    //判断表头是否存在合并行的存在
                            $th =  $($thead.find('th').get(i));
                        }else{
                            $th = $($thead.find('tr:last').find('th').get(i-gapNum));
                        }
                        if($th){
                            $th.css({
                                'width': elseWidth,
                                'min-width': elseWidth,
                                'max-width': elseWidth
                            });
                        }
                    });
                    if(addWidth){
                        $thead.css('left',left);
                    }
                    if($Caret){
                        $.each($Caret,function (i,j) {
                            $(j).addClass('hide')
                        })
                    }
                    if($tooltip){
                        $tooltip.tooltip('destroy');
                        $.each($tooltip,function (i,j) {
                            $(j).data('placement', 'bottom');
                        });
                        $tooltip.tooltip();
                    }
                    if($popPurCost){
                        $popPurCost.css('top','20px')
                    }
                }else{
                    $thead[0].removeAttribute('style');	//改成默认定位
                    $.each($td,function (i,j) {
                        j.removeAttribute('style');
                    });
                    $.each($width,function (i,j) {
                        var elseWidth = j.offsetWidth;
                        $thead.find('th').get(i).removeAttribute('style');
                    });
                    if($Caret){
                        $.each($Caret,function (i,j) {
                            $(j).removeClass('hide')
                        })
                    }
                    if($tooltip){
                        $tooltip.tooltip('destroy');
                        $.each($tooltip,function (i,j) {
                            $(j).data('placement','top')
                        });
                        $tooltip.tooltip();
                    }
                    if($popPurCost){
                        $popPurCost.css('top','-66px')
                    }
                }
            };
            W.onresize = function(){ //X轴滚动条滚动时
                var firstWidth = $ceilingType.find('.content:first').find('td:first')[0].offsetWidth,
                    $elseWidth = $ceilingType.find('.content:first').find('td:not(:first)');
                $thead.find('th:first').css('width',firstWidth);
                $.each($elseWidth,function (i,j) {
                    var elseWidth = j.offsetWidth;
                    $($thead.find('th').get(i+1)).css('width',elseWidth);
                });
            };
        }
    };
    window.requestAnimationFrame( $.fn.ceilingType);
})(jQuery,window);


/*
 *页面全选处理(出货管理)
 * checkbox选择范围扩大到整个td,并显示选中条数
 * 注册方法：$('.a').selectAllFn(setting);
 * selecting 为设置的参数
 * 最外层添加class selectAllBox
 * tr父级必须添加name，两级以上列表父级为 name="header"，单级父级列表为 name="content"
 * 全选input添加 iptId="selectAll"，单个input添加 iptId="selectSingle"
 * */
(function($){
	$.fn.selectAllFn = function(o){
		var options = {
			displaySelNum: false //显示选中条数 false 不显示 true 显示
		};
		var _this = $(this),
			checkBoxLen = '',
			setting = o ? o : {};

		var newOptions = $.extend({}, options, setting);
		o && o.callBack && o.callBack(newOptions);
		var checkboxCheck = function(e, obj, options){
			e = e || event;
			e.stopPropagation();
			var iptId = $(obj).attr('iptId'),
				$box = $(obj).closest('.selectAllBox'),
				selected = $(obj).is(':checked'),
				selectedAll = $box.find('[iptId="selectAll"]').is(':checked'),
				numtd = $box.find('[iptId="selectAll"]').closest('table').children("thead").children('tr:first-child').children('th').length,
				header =  $(obj).closest('tr[name = "header"]').length,
				tableCeil = $(obj).closest('body').find('.ceilingDiv').find('.selectedData'),
				newStr = '';

			if (iptId === 'selectAll'){
				var checked = '';
				$box.find('input[iptId="selectSingle"]').each(function(){
					checked = $(this).is(":checked");
					if(selected && !checked){
						$(this).click();
					}else if(!selected && checked){
						$(this).click();
					}
				});
			}
			var inputNumtwo = $box.find('input[iptId="selectSingle"]').length,
				checkedNum = $box.find('input[iptId="selectSingle"]:checked').length;

			if(+inputNumtwo === +checkedNum && !selectedAll){
				$box.find('[iptId="selectAll"]').prop('checked', true);
			}else if(+inputNumtwo !== +checkedNum && selectedAll){
				$box.find('[iptId="selectAll"]').prop('checked', false);
			}
			if(options.displaySelNum){
				newStr = $('<tr class="showSelCheckboxNum articles-num-box" id="showSelCheckboxNum">' +
					'<td class="articles-num-title articlesNumBox" colspan="'+numtd+'">' +
					'<span class="articles-num-con p-left20 articlesNumCon"></span><span class="articlesNumConAdd"></span></td></tr>');

				if(checkedNum > 0){
					if (!+$('tr.showSelCheckboxNum').length){
						$box.find('tr:first').after(newStr);
					}
					tableCeil.html('&nbsp;&nbsp;已选中'+checkedNum+'条数据');
					$('tr.showSelCheckboxNum .articlesNumCon').html('已选中<span class="f-black m-left5 m-right5 f-w600">'+checkedNum+'</span>条数据');
				}else{
					$box.find('tr.showSelCheckboxNum').remove();
					tableCeil.empty();
				}
			}
		};
		_this.on('click',function(){
			checkBoxLen = $(this).find('[type="checkbox"]').length;
			if(+checkBoxLen === 1){
				$(this).find('[type="checkbox"]').click();
			}
		});
		_this.find('[type="checkbox"]').on('click',function (e) {
			e.stopPropagation();
			checkboxCheck(e, this, setting);
		});
	}
})(jQuery);

/****************************2017.2.20 byzym 添加select选择框end*****************/
//订单留言 闭包 maijia_p=买家姓名 maijia_span=时间  maijia=买家留言   dianpu_p=店铺名 dianpu_span=时间 dianpu=店铺留言
(function ($) {
    var strOrder='<div class="word-box"><div class="whiteBar"></div><div class="wordbox scrollBar"></div><div class="triangle"></div></div>';
    var infoHtml = '<p class="&{class}">' +
        '<span class="&{class1}" style="margin-right:10px;">&{name}</span>' +
        '<span class="&{class2}">&{gmtCreateTime}</span>' +
        '</p><div class="&{class3}">&{content}</div> ';
    $(document).on('mouseover','.yan',function () {
        var yanModalObj=$(strOrder);
        var nameCent = JSON.parse($(this).attr('nameCent'));
        var plateform = nameCent.platform;//获取平台名称
        var msgList = nameCent.content;
        var newStr = '';
        if ($(this).find('.word-box').length < 1) {
            $.each(msgList,function(i){
                var oneList = msgList[i];
                var listType = oneList.type;
                if(listType == 0){
                    oneList.class = 'maijia_p';
                    oneList.class1 = 'maijia_name';
                    oneList.class2 = 'maijia_date';
                    oneList.class3 = 'maijia';
                }else{
                    oneList.class = 'dianpu_p';
                    oneList.class1 = 'dianpu_name';
                    oneList.class2 = 'dianpu_span';
                    oneList.class3 = 'dianpu';
                }
                newStr += infoHtml.format(oneList);
            });
            yanModalObj.find('.wordbox').append(newStr);
            $(this).append(yanModalObj);
            var divTop = $(this).find('.word-box').outerHeight() / 2;
            $(this).find('.word-box').css({'top': '-' + (divTop - 10) + 'px'});
            $(this).find('.triangle').css({'top': (divTop - 14) + 'px'});
        }
    })
})(jQuery);

// 显示或移除添加按钮
var showAdd =  function (type) {
	if(type == 1){
		if($("tr[sourceUrlMarkTr='sourceUrlMarkTr']").length == 5){
			$("a[fstRow='fstAdd']").css('display','none');
		}
	}else{
		$("a[fstRow='fstAdd']").css('display','block');
	}
};

//数组唯一值
function uniqueArray(sourseArr){
	var res = [];
	var json = {};
	for(var i = 0; i < sourseArr.length; i++){
		if(!json[sourseArr[i]]){
			res.push(sourseArr[i]);
			json[sourseArr[i]] = 1;
		}
	}
	return res;
}
var expand_click = function(){
	//点击td选中checked  选中范围扩大 + 连动选择
	var objs = $('.input1');
	$('.expand_scope').click(function(){
		$(this).prop('checked')==true ? objs.prop('checked',true) : objs.prop('checked',false);
	});
	objs.click(function(e){
		objs.length == objs.filter(':checked').length ? $('.expand_scope').prop('checked',true) : $('.expand_scope').prop('checked',false)
		e.stopPropagation();
	});
	$('.expand_scope2').on('click',function(){
		if($(this).find('input').is(':checked')){
			$(this).find('input').prop('checked',false);
		}else{
			$(this).find('input').prop('checked',true);
		}
		objs.length == objs.filter(':checked').length ? $('.expand_scope').prop('checked',true) : $('.expand_scope').prop('checked',false)
	});
};
var expand_click2 = function(){
	//点击td选中checked  选中范围扩大
	$('.expand_scope').on('click',function(){
		if($(this).closest('.parent').find('.input1').is(':checked')){
			$(this).closest('.parent').find('.input1').prop('checked',false);
		}else{
			$(this).closest('.parent').find('.input1').prop('checked',true);
		}
		showSelCheckboxNum($(this).closest('.parent').find('.input1'));
	});
};
/*2017/11/3 byzym*/
var expand_click3 = function(){
    //点击td选中checked  选中范围扩大
    $('.expand_scope').on('click',function(){
        if($(this).closest('.parent').find('.input1').is(':checked')){
            $(this).closest('.parent').find('.input1').prop('checked',false);
        }else{
            $(this).closest('.parent').find('.input1').prop('checked',true);
        }
        showSelCheckboxNum($(this).closest('.parent').find('.input1'),'tableSingle');
    });
};
// base64加密开始
var keyBase64Str = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
	+ "wxyz0123456789+/" + "=";

var encode64 = function(input) {
	var output = "";
	var chr1, chr2, chr3 = "";
	var enc1, enc2, enc3, enc4 = "";
	var i = 0;
	do {
		chr1 = input.charCodeAt(i++);
		chr2 = input.charCodeAt(i++);
		chr3 = input.charCodeAt(i++);
		enc1 = chr1 >> 2;
		enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
		enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
		enc4 = chr3 & 63;
		if (isNaN(chr2)) {
			enc3 = enc4 = 64;
		} else if (isNaN(chr3)) {
			enc4 = 64;
		}
		output = output + keyBase64Str.charAt(enc1) + keyBase64Str.charAt(enc2)
			+ keyBase64Str.charAt(enc3) + keyBase64Str.charAt(enc4);
		chr1 = chr2 = chr3 = "";
		enc1 = enc2 = enc3 = enc4 = "";
	} while (i < input.length);

	return output;
};
// base64加密结束

/**
 * 转化成北京时间
 * @param dateDime
 * @param timeZone
 * @returns {string}
 */
var parseToBeijingTime = function(dateTime,timeZone){
	if(dateTime=="" || dateTime==undefined || timeZone =="" || timeZone==undefined){
		return null;
	}else{
		var dateBefore = dateTime.slice(0,10);
		var timeBefore = dateTime.slice(11,20);
		var dateArray = dateBefore.split("-");
		var timeArray = timeBefore.split(":");
		var beijingTime = new Date();
		beijingTime.setTime(Date.UTC(dateArray[0],dateArray[1]-1/*月份从0开始*/,dateArray[2],timeArray[0],timeArray[1],timeArray[2],0) + timeZone*60*60*1000);
		var year=beijingTime.getUTCFullYear(),month,date,hour,minute;
		//年月日
		if (beijingTime.getUTCMonth()<10){
			var m=0;
			m=beijingTime.getUTCMonth()+1;
			month = "0" + m;
		}else{month = beijingTime.getUTCMonth()+1;}
		if (beijingTime.getUTCDate()<10){
			var d=0;
			d=beijingTime.getUTCDate();
			date = "0" + d;
		}else{date = beijingTime.getUTCDate();}
		var dateAfter = year + "-" + month + "-" + date;
		//时分
		if(beijingTime.getUTCHours()<10){hour = "0" + beijingTime.getUTCHours();}
		else{hour = beijingTime.getUTCHours();}
		if(beijingTime.getUTCMinutes()<10){minute = "0" + beijingTime.getUTCMinutes();}
		else{minute = beijingTime.getUTCMinutes();}

		var timeAfter = hour + ":" + minute +":" + timeArray[2];
		return dateAfter + " " + timeAfter;
	}
};
/*************转化成北京时间end***************/
//dxm图片地址换成https
var picURLTransformation = function(str){
    if(!str)return;
    var objRegExp  =  /http:\/\/wxalbum-10001658.image.myqcloud/g,
        newURL = 'https://wxalbum-10001658.image.myqcloud';
    return str.replace(objRegExp,newURL);
};
/*
 *tab切换,没有内容切换不要加 defaultCheck*
 *联系以li上uid与.headTabPane上的id进行绑定*
 */
(function($,w,d){
    $(d).off('click', '.headTabBox.defaultCheck .headTabNav li');
    $(d).on('click', '.headTabBox.defaultCheck .headTabNav li', function(){
        var _this = this,
            tabId = $(_this).attr('uid');
        $(_this).closest('.headTabNav').find('li').removeClass('tab-check');
        $(_this).addClass('tab-check');
        $(_this).closest('.headTabBox').find('.headTabPane').addClass('myj-hide');
        $(_this).closest('.headTabBox').find('#'+tabId).removeClass('myj-hide');
    })
})(jQuery, window, document);
//验证是否为阿里图片并转成https
var aliImgHandle = (function(){
	return {
		isaliToHttps: function(url){
            // 判断是否为阿里图片
            var alicdnCom = /alicdn.com/g.test(url);
            if(alicdnCom){
                var regExp = /g[0-9]{2}.a/g;
                url = url.replace(regExp,'ae01');
            }
			return url;
		}
	}
})();
/****图片base64前端直接传图片服务器****/
/*
* 还有问题，没全部走通
* FRONTENDIMGHANDLE.imgUpLoad(url,call,name,option) option未启用
*
*/
var FRONTENDIMGHANDLE = (function($){
    var ajaxURL = gitWinLocHref()+'album/getSign.json',
        bucket = 'wxalbum',
        fullcid = '',
        isNeedTree = 0,
        regExp = 'wxalbum-10001658.image.myqcloud';
    return{
        urlToBlob: function(u,call){
            var _self = this;
            if(u.indexOf(regExp) == -1 && u.indexOf(';base64,') == -1){
                //var nu = u.replace('http:','').replace('https:','');
                var xhr = new XMLHttpRequest();
                xhr.responseType = 'blob';
                xhr.onload = function() {
                    this.status == 200 ? call({base:this.response,error:''}) : call({error:'error'}) ;
                };
                xhr.addEventListener('error',function(e){
                    call({error:'error'});
                },false);
                xhr.open('get', u, true);
                xhr.send();
                return true;
            }else if(u.indexOf(regExp) != -1){
                console.log('请选择非图片空间图片');
            }else if(u.indexOf(';base64,') != -1){
                _self.baseToBlob(u,call);
            }
        },
		downLoadReportIMG: function(imgPathURL) {
			var a =  '<a id="imgDownLoad" href="" download="imgLoad"></a>';
			if($('#imgDownLoad').length == 0){
				$('body').append($(a));
			}
			$('#imgDownLoad').attr('href',imgPathURL).click();
		},
        baseToBlob: function(b){
            if(b){
                var bytes = window.atob(b.split(',')[1]);
                var ab = new ArrayBuffer(bytes.length);
                var ia = new Uint8Array(ab);
                for(var i = 0; i < bytes.length; i++){
                    ia[i] = bytes.charCodeAt(i);
                }
                var blob = new Blob([ab],{type: b.split(',')[0].replace('data:','').replace(';base64','')});
                return blob;
            }
        },
        urlGetImgName: function(u){
            var imgName = u.split('/').pop();
            imgName = imgName.split('?')[0];
            return imgName;
        },
        imgUpLoad: function(url,call,name,option){
            console.log('1');
            if(!url) return;
            var _self = this;
            if(url.indexOf('base64') == -1){
                _self.urlToBlob(url,function(obj){
                    if(!obj.error){
                        _self.ajaxWX(obj.base,_self.urlGetImgName(u),call);
                    }
                })
            }else{
                if(name){
                    _self.ajaxWX(_self.baseToBlob(url),name,call);
                }else{
                    console.log('没有图片名');
                }
            }
        },
        ajaxWX: function(b,name,call,errorCall){
            if(!b || !name) return;
            var uploadOver = false;
            var formdata = {},
                url = ajaxURL,
                xhr = false;
            formdata.fileName = name;
            formdata.fullcid = fullcid;
            formdata.isNeedTree = isNeedTree;
            try{
                xhr = new XMLHttpRequest();//尝试创建 XMLHttpRequest 对象，除 IE 外的浏览器都支持这个方法。
            }catch(e){
                xhr = ActiveXobject("Msxml12.XMLHTTP");//使用较新版本的 IE 创建 IE 兼容的对象（Msxml2.XMLHTTP）。
            }
            xhr.onreadystatechange = function() {
                if(xhr.readyState == 4){
                    uploadOver = true;
                    if (xhr.status == 200) {
                        var returnData = xhr.responseText ? JSON.parse(xhr.responseText) : {} ;
                        if(returnData.data){
                            call(returnData.data);
                            clientPicCallBack(bucket,returnData.data.fileid,fullcid,name,isNeedTree);
                        }else{/*console.log('没有返回')*/}
                    } else {/*console.log('error');*/}
                    //这步有问题，报超过最大堆栈大小
                    /*if(uploadOver){
                        //设置图片空间
                        clientPicCallBack(bucket,returnData.data.fileid,fullCid,name,isNeedTree);
                    }*/
                }
            };
            xhr.addEventListener('error',function(e){
                /*console.log(e);*/
            },false);
            var test = true;
            var errorData = null;
            $.ajax({
                type:'POST',
                url: url,
                data:{
                    "bucket":bucket,
                    "fileName":name
                },
                dataType:'json',
                async : false,
                success:function(data){
                    if(data != null){
                        if(data.code == 0){
                            var sign = data.sign;
                            url = data.url + '?sign=' + encodeURIComponent(sign);
                        }else{
                            test = false;
                            errorData = data;
                        }
                    }
                }
            });
            if(test){
                formdata.lastModifiedDate = new Date().getTime();
                var dataStr = JSON.stringify(formdata);
                formdata = {
                    MagicContext:dataStr
                };
                xhr.open('post', url, true);
                var fd = new FormData();
                fd.append('FileContent',b);
                if(formdata){
                    $.each(formdata,function(i,j){
                        fd.append(i,j);
                    })
                }
                xhr.send(fd);
            }else{
                if(errorData && errorCall){
                    errorCall(errorData);
                }
            }
        }
    };
})(jQuery);
/****图片base64前端直接传图片服务器end****/
//翻译用，过滤数组中空元素和非中文
var transateArrHandle = function(arr){
	if(arr && arr instanceof Array){
		var newArr = [];
		$.each(arr,function(i,j){
			j = $.trim(j);
			if(j && isContainChinese(j)){
				newArr.push(j);
			}
		});
		return newArr;
	}else{
		return arr;
	}
};
/************ 对象/数组深度克隆 [songpengyuan 2017.12.04] *********************/
function myjDeepClone(obj) {
	var o, i, j, k;
	if (typeof(obj) != "object" || obj === null)return obj;
	if (obj instanceof (Array)) {
		o = [];
		i = 0;
		j = obj.length;
		for (; i < j; i++) {
			if (typeof(obj[i]) == "object" && obj[i] != null) {
				o[i] = arguments.callee(obj[i]);
			} else {
				o[i] = obj[i];
			}
		}
	} else {
		o = {};
		for (i in obj) {
			if (typeof(obj[i]) == "object" && obj[i] != null) {
				o[i] = arguments.callee(obj[i]);
			} else {
				o[i] = obj[i];
			}
		}
	}
	return o;
}
/*
 * 产品通用方法
 * */

/*打开设置分类弹层*/
var editCateGoryShow = function(){
    $('#editCategory').modal('show');
};
/*打开选择网络图片弹层*/
var netImgModalShow = function(){
    $('#imgModal').modal('show');
};
/*打开批量编辑选择网络图片弹层*/
var batchImgEditModalShow = function(){
    $('#imgEditModel').modal('show');
};

/*
*字段排序
* listDataSort(result,'name','az');
* list 排序方法 list: 数组对象  key: 排序字段  type: 排序方法 'az'从小到大 'za'从大到小
* 按照A-z 0-9的排序方式排序
 * */
/*
const listDataSort = function(list,key,type){
    let newList = list.concat();
    const sortKeyAZ = function(a,b){
            return a[key] > b[key] ? 1 : -1;
        },
        sortKeyZA = function(a,b){
            return a[key] < b[key] ? 1 : -1;
        };
    newList.sort(type == 'az' ? sortKeyAZ : sortKeyZA);
    return newList
};*/

/*根据平台码获取用于页面显示的平台名称*/
var platformDisplayMapping = function (platformCode) {
    var platformName = '';
    switch (platformCode){
        case 'All':
            platformName = '全部';
            break;
        case 'wish':
            platformName = 'Wish';
            break;
        case 'smt':
            platformName = '速卖通';
            break;
        case 'ebay':
            platformName = 'eBay';
            break;
        case 'amazon':
            platformName = 'Amazon';
            break;
        case 'lazada':
            platformName = 'Lazada';
            break;
        case 'dh':
            platformName = '敦煌';
            break;
        case 'cd':
            platformName = 'Cdiscount';
            break;
        case 'shopify':
            platformName = 'Shopify';
            break;
        case 'magento':
            platformName = 'Magento';
            break;
        case 'magento2':
            platformName = 'Magento2';
            break;
        case 'woocomm':
            platformName = 'Woocommerce';
            break;
        case 'shopee':
            platformName = 'Shopee';
            break;
        case 'joom':
            platformName = 'Joom';
            break;
        case 'tophatter':
            platformName = 'Tophatter';
            break;
        case 'walmart':
            platformName = 'Walmart';
            break;
        case 'mymall':
            platformName = 'myMall';
            break;
        case 'factorymkt':
            platformName = 'Factorymarket';
            break;
        case 'ueeshop':
            platformName = 'Ueeshop';
            break;
        case 'shopyy':
            platformName = 'Shopyy';
            break;
        case 'vova':
            platformName = 'Vova';
            break;
        case 'jd':
            platformName = '京东';
            break;
    }
    return platformName;
};
/*判断网络图片添加的url中是否存在base64编码格式的图片url*/
var judgeUrlIsBase64 = function (urlArr) {
    var reg = /^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$/;
    if(typeof urlArr == "object"){
        for(var i in urlArr){
            urlArr[i] = $.trim(urlArr[i]);
            if (reg.test(urlArr[i])) {
                $.fn.message({type:"error",msg:"第"+(+i+1)+"条图片链接格式不支持添加！"});
                return false;
            }
        }
	}else{
        if (reg.test(urlArr)) {
            $.fn.message({type:"error",msg:"图片链接格式不支持添加！"});
            return false;
        }
	}
    return true;
};

/** 数字空默认设置为0 */
var setNumericDefault = function (source) {
	if (source){
		return source;
	}
	return 0;
}

/*
*
* window.open打开新窗体并用post方式传参
*
* */
function comOpenWinPost(url,args,options){
    var option = {
        'id':'comOpenWinPostForm',
        'method':'post',
        'action':url,
        'target':'_blank',
        'style':'display:none'
    };
    if(options && !isEmptyObject(options)){
        for(var key in options){
            option[key] = options[key];
        }
    }
    var _form = $("<form></form>",option).appendTo($("body"));

    for(var i in args){
        _form.append($("<input>",{'type':'hidden','name':i,'value':args[i]}));
    }

    _form.submit();
    _form.remove();
}

// 批量下载图片
function batchDownloadImgage(urls,fileName,uniqueKey) {
	if ($.trim(urls) == ""){
        $.fn.message({type:"error",msg:"图片地址为空"});
        return false;
	}

    var data = {
        urls : urls,
        fileName : fileName,
        uniqueKey : uniqueKey
    };
    $("#batchCheckReplicaProgressBar").css("width", "0%");
    $("#batchCheckReplicaProcessNum").text("0");
    $("#reportShow").removeClass("fRed").html("正在导出中，请稍后...");
    $('#batchDownloadImageModal').modal('show');
    $('div[uid="downloadFill"]').removeClass('dropdown').find('button[uid="dropdown"]').removeClass('btn-primary').addClass('btn-default').attr('disabled',true).css('color','#000000');
    $.ajax({
        type:'POST',
        url: gitWinLocHref()+"/album/batchDownloadFromProduct.json",
        data:data,
        dataType:'json',
        success:function(back){
            if (data.msg){
                $.fn.message({type:"error",msg:data.msg});
                return;
            }

            var total = back.totalNum;
            var uuid = back.uuid;
            var p = 0;    // 记录进度条的值

            checkProcessStatus(uuid, function(data){
                var num 	= Math.round(data.num / data.totalNum * 100);
                var status = "检测中...";

                if(num<p && data.code!=1){
                    // 为了去掉变为n->0时的动画效果，在此先隐藏，然后再置0，最后再定时显示出来
                    $("#batchCheckReplicaProgressBar").css("display", "none");
                    setTimeout(function(){
                        $("#batchCheckReplicaProgressBar").css("display", "");
                    },1000);
                }
                p = num;

                if(data.code==0){
                    $("#batchCheckReplicaProgressBar").css("width", num + "%");
                    $("#batchCheckReplicaProcessNum").text(data.num);
                    $("#reportShow").text("正在导出中，请稍后...");
                }else if(data.code==-1){
                    $("#batchCheckReplicaProgressBar").css("width", num + "%");
                    $("#batchCheckReplicaProgressBar").removeClass("active");
                    $("#batchCheckReplicaProcessNum").text(data.num);
                    $("#reportShow").addClass("fRed").html(data.msg);
                }else{
                    $("#batchCheckReplicaProgressBar").css("width", "100%");
                    $("#batchCheckReplicaProgressBar").removeClass("active");
                    $("#batchCheckReplicaProcessNum").text(data.num);
                    $("#reportShow").empty();
                    $("#reportShow").html('导出文件已生成，文件将会在30分钟后失效,请立即下载<br/>');
                    $('div[uid="downloadFill"]').addClass('dropdown').find('button[uid="dropdown"]').removeClass('btn-default').addClass('btn-primary').attr('disabled',false).css('color','#FFFFFF');
                    $('.highSpeedDownload').find('a').attr('href',data.msg);
                    var msg = data.msg;
                    var msgStart = msg.substring(10);
                    var msgHttp = 'http://download.';
                    var msgOver = msgHttp + msgStart;
                    $('.standbDownload').find('a').attr('href',msgOver);
                }
            });
        }
    });
}

/**批量导出**/
var batchExportData = function(url,param,title,pageSize){
	// 设置此次导出标题
	$(".batchExportDataTitle").text(title);

    $("#batchExportCosProductModal").modal("show");
    /*2017.2.10 byzym 二次刷新初始化*/
    $("#batchExportCosCheckReplicaProgressBarr").css("display", "none");
    setTimeout(function(){
        $("#batchExportCosCheckReplicaProgressBarr").css("width", 0+"%");
        $("#batchExportCosCheckReplicaProgressBarr").css("display", "");
    },10);
    $("#batchExportCosCheckReplicaProcessNumm").html("0");
    $("#exportCosReportShoww").empty();
    $('#exportCosUpDownloadboxZ').empty();
    $('ul[uid="dropdown-menuZ"]').empty();
    $('button[uid="dropdownZ"]').addClass('myj-hide');
    $('div[uid="downloadFill"]').removeClass('dropdown').find('button[uid="dropdown"]').removeClass('btn-primary').addClass('btn-default').attr('disabled',true).css('color','#000000');
    var p = 0;		// 记录进度条的值
    $.ajax({
        type: "POST",
        url: url,
        data: param,
        dataType: "json",
        success: function(data){
        	if (data.msg){
                $.fn.message({type:"error",msg:data.msg});
        		return;
			}
            var uuid = data.uuid;
            checkProcessStatus(uuid, function(data){
                var num 	= data.num % 100;
                if(num<p && data.code!=1){
                    // 为了去掉变为n->0时的动画效果，在此先隐藏，然后再置0，最后再定时显示出来
                    $("#batchExportCosCheckReplicaProgressBarr").css("display", "none");
                    setTimeout(function(){
                        $("#batchExportCosCheckReplicaProgressBarr").css("display", "");
                    },1000);
                }
                p = num;
                if(data.code==0){
                    $("#batchExportCosCheckReplicaProgressBarr").css("width", num + "%");
                    $("#batchExportCosCheckReplicaProcessNumm").text(data.num);
                    $("#exportCosReportShoww").text(title + "正在导出中，请稍后下载...");
                    $("#exportCosReportShowww").text("");
                    $("#exportCosUpDownloadboxZu").text("");

                }else{
                    $("#batchExportCosCheckReplicaProgressBarr").css("width", "100%");
                    $("#batchExportCosCheckReplicaProgressBarr").removeClass("active");
                    $("#batchExportCosCheckReplicaProcessNumm").text(data.totalNum);
                    $("#exportCosReportShoww").empty();
                    $("#exportCosReportShowww").empty();
                    $('div[uid="downloadFill"]').addClass('dropdown').find('button[uid="dropdownZ"]').removeClass('btn-default').addClass('btn-primary').attr('disabled',false).css('color','#FFFFFF');
                    if (data.msg) {
                        var downUrl = JSON.parse(data.msg),
                            size = pageSize,spanStr= '',urls = downUrl.downUrlList,start = 1,
                            end;
                        if (urls.length > 1) {
                            $.each(urls, function (i,val) {
                                if (i == urls.length - 1) {
                                    end = "末尾";
                                } else {
                                    end = start - 1 + size;
                                }
                                spanStr += '<span class="inline-block" style="width: 33.33%;line-height: 30px;"><a role="menuitem" tabindex="-1" href="'+val+'">下载文件('+start+'-'+end+')</a></span>';
                                start += size;
                            });
                            $('#exportCosUpDownloadboxZ').html(spanStr);
                            $("#exportCosReportShoww").html('由于文件过大，已拆分为 '+urls.length+'个文件<br/>');
                        } else {
                            var downUrlAdder = urls[0];
                            if(downUrlAdder){
                                $("#exportCosReportShoww").html('导出文件已经生成: <span class="inline-block" style="width: 33.33%;line-height: 30px;"><a role="menuitem" tabindex="-1" href="'+downUrlAdder+'">立即下载文件</a></span>');
                            }
                        }
                    }
                }
            });
        },
        error:function(msg){
        }
    });
};

/**
 * 防止ajax加载的页面中含有的js请求自动加时间戳
 */
(function ($) {
    jQuery._evalUrl = function (url) {
        return jQuery.ajax({
            url: url,
            type: "GET",
            dataType: "script",
            async: false,
            global: false,
            "throws": true,
            cache: true
        });
    };
})(jQuery);

/*保留小数不四舍五入*/
function formatDecimal(num, decimal) {
	if(num !== ''){
        num = num.toString();
        var index = num.indexOf('.');
        if (index !== -1) {
            num = num.substring(0, decimal + index + 1)
        } else {
            num = num.substring(0)
        }
        return parseFloat(num).toFixed(decimal)
    }else{
        return '';
	}
}
/*保留小数不四舍五入*/

/*body点击其他地方时隐藏*/
var bodyClickHideDom = function (parentClass,childClass) {//parentClass = 点击展示的气泡之类的触发节点class；childClass = 展示之后的节点class
    var e = null,
        targ = null,
        tagBox = null;
    $(document).off('click','body').on('click','body',function (event) {
        e = event || window.event; //获取事件点击元素
        targ = e.target;
        tagBox = $(targ).closest(parentClass).length;

        $(childClass+'.block').removeClass('block');
        if(tagBox){
            $(targ).closest(parentClass).find(childClass).addClass('block');
        }
        e = null;
        targ = null;
        tagBox = null;
    });
};
/*body点击其他地方时隐藏end*/

/*根据产品链接获取产品来源平台*/
var getSourceName = function (sourceUrl) {
	var name = '';
	if(sourceUrl) {
		if (sourceUrl.indexOf("taobao.com") > 0) {
			name = "淘宝";
		} else if (sourceUrl.indexOf("tmall.com") > 0) {
			name = "天猫";
		} else if (sourceUrl.indexOf("1688.com") > 0) {
			name = "1688";
		} else if (sourceUrl.indexOf("aliexpress.com") > 0) {
			name = "速卖通";
		} else if (sourceUrl.indexOf("ebay.com/") > 0) {
			name = "ebay(美国)";
		} else if (sourceUrl.indexOf("ebay.com.au") > 0) {
			name = "ebay(澳大利亚)";
		} else if (sourceUrl.indexOf("ebay.at") > 0) {
			name = "ebay(奥地利)";
		} else if (sourceUrl.indexOf("ebay.ca") > 0) {
			name = "ebay(加拿大)";
		} else if (sourceUrl.indexOf("cafr.ebay.ca") > 0) {
			name = "ebay(加拿大 法语)";
		} else if (sourceUrl.indexOf("ebay.fr") > 0) {
			name = "ebay(法国)";
		} else if (sourceUrl.indexOf("ebay.de") > 0) {
			name = "ebay(德国)";
		} else if (sourceUrl.indexOf("ebay.in") > 0) {
			name = "ebay(印度)";
		} else if (sourceUrl.indexOf("ebay.ie") > 0) {
			name = "ebay(爱尔兰)";
		} else if (sourceUrl.indexOf("ebay.it") > 0) {
			name = "ebay(意大利)";
		} else if (sourceUrl.indexOf("ebay.com.my") > 0) {
			name = "ebay(马来西亚)";
		} else if (sourceUrl.indexOf("ebay.nl") > 0) {
			name = "ebay(荷兰)";
		} else if (sourceUrl.indexOf("ebay.ph") > 0) {
			name = "ebay(菲律宾)";
		} else if (sourceUrl.indexOf("ebay.pl") > 0) {
			name = "ebay(波兰)";
		} else if (sourceUrl.indexOf("ebay.com.sg") > 0) {
			name = "ebay(新加坡)";
		} else if (sourceUrl.indexOf("ebay.es") > 0) {
			name = "ebay(西班牙)";
		} else if (sourceUrl.indexOf("ebay.ch") > 0) {
			name = "ebay(瑞士)";
		} else if (sourceUrl.indexOf("ebay.vn") > 0) {
			name = "ebay(越南)";
		} else if (sourceUrl.indexOf("ebay.co.uk") > 0) {
			name = "ebay(英国)";
		} else if (sourceUrl.indexOf("ebay.com.hk") > 0) {
			name = "ebay(香港)";
		} else if (sourceUrl.indexOf("amazon.com/") > 0) {
			name = "亚马逊(美国)";
		} else if (sourceUrl.indexOf("amazon.ca") > 0) {
			name = "亚马逊(加拿大)";
		} else if (sourceUrl.indexOf("amazon.fr") > 0) {
			name = "亚马逊(法国)";
		} else if (sourceUrl.indexOf("amazon.de") > 0) {
			name = "亚马逊(德国)";
		} else if (sourceUrl.indexOf("amazon.it") > 0) {
			name = "亚马逊(意大利)";
		} else if (sourceUrl.indexOf("amazon.co.jp") > 0) {
			name = "亚马逊(日本)";
		} else if (sourceUrl.indexOf("amazon.com.mx") > 0) {
			name = "亚马逊(墨西哥)";
		} else if (sourceUrl.indexOf("amazon.co.uk") > 0) {
			name = "亚马逊(英国)";
		} else if (sourceUrl.indexOf("amazon.cn") > 0) {
			name = "亚马逊(中国)";
		} else if (sourceUrl.indexOf("amazon.in") > 0) {
			name = "亚马逊(印度)";
		} else if (sourceUrl.indexOf("amazon.es") > 0) {
			name = "亚马逊(西班牙)";
		} else if (sourceUrl.indexOf("jd.com") > 0) {
			name = "京东";
		} else if (sourceUrl.indexOf("alibaba.com") > 0) {
			name = "alibaba";
		} else if (sourceUrl.indexOf("dhgate.com") > 0) {
			name = "敦煌";
		} else if (sourceUrl.indexOf("etsy.com") > 0) {
			name = "etsy";
		} else if (sourceUrl.indexOf("lazada.co.id") > 0) {
			name = "lazada(印尼)";
		} else if (sourceUrl.indexOf("lazada.sg") > 0) {
			name = "lazada(新加坡)";
		} else if (sourceUrl.indexOf("lazada.com.my") > 0) {
			name = "lazada(马来)";
		} else if (sourceUrl.indexOf("lazada.vn") > 0) {
			name = "lazada(越南)";
		} else if (sourceUrl.indexOf("lazada.co.th") > 0) {
			name = "lazada(泰国)";
		} else if (sourceUrl.indexOf("lazada.com.ph") > 0) {
			name = "lazada(菲律宾)";
		} else if (sourceUrl.indexOf("pfhoo.com") > 0) {
			name = "PFHOO";
		} else if (sourceUrl.indexOf("wish.com") > 0) {
			name = "wish";
		} else if (sourceUrl.indexOf("chinabrands.com") > 0 || sourceUrl.indexOf("chinabrands.cn") > 0) {
			name = "chinabrands";
		} else if (sourceUrl.indexOf("www.banggood.com") > 0) {
			name = "banggood";
		} else if (sourceUrl.indexOf("us.banggood.com") > 0) {
			name = "usBanggood";
		} else if (sourceUrl.indexOf("haiyingshuju.com") > 0) {
			name = "海鹰";
		} else if (sourceUrl.indexOf("yixuanpin.cn") > 0) {
			name = "易选品";
		} else if (sourceUrl.indexOf("gearbest.com") > 0) {
			name = "gearbest";
		} else if (sourceUrl.indexOf("walmart.com") > 0) {
			name = "沃尔玛";
		} else if (sourceUrl.indexOf("joom.com") > 0) {
			name = "joom";
		} else if (sourceUrl.indexOf("tophatter.com") > 0) {
			name = "tophatter";
		} else if (sourceUrl.indexOf("shopee.tw") > 0) {
			name = "shopee(台湾)";
		} else if (sourceUrl.indexOf("shopee.sg") > 0) {
			name = "shopee(新加坡)";
		} else if (sourceUrl.indexOf("shopee.com.my") > 0) {
			name = "shopee(马来西亚)";
		} else if (sourceUrl.indexOf("shopee.co.th") > 0) {
			name = "shopee(泰国)";
		} else if (sourceUrl.indexOf("shopee.co.id") > 0) {
			name = "shopee(印尼)";
		} else if (sourceUrl.indexOf("shopee.ph") > 0) {
			name = "shopee(菲律宾)";
		} else if (sourceUrl.indexOf("shopee.vn") > 0) {
			name = "shopee(越南)";
		} else {
			name = "其它";
		}
	}
		
	return name;
};

//获取输入框光标位置，插入内容到光标位置
var getIptCursorInsertContent = function(iptId,textClass){//iptId=被插入的输入框节点id,textClass=要插入的内容节点class,这个class可以多个,
    var $iptId = document.getElementById(iptId), //获取输入框节点
        index = null;//光标所在输入框的位置下标,初始化为null

    $(document).off('click','.'+textClass).on('click','.'+textClass,function () {//给要插入的内容节点绑定点击事件
        var val = $iptId ? $iptId.value : '',//获取输入框现在的值
            value = '';
        if(index === null || index > val.length){//如果index = null或者大于当前输入框的值的长度,则默认把内容插入到输入框最后
            index = val.length; //获取当前输入框内容的长度
        }
        value = val.substring(0,index);
        val = val.substring(index) || '';

        value += $(this).attr('data-value') + val; //获取当前要插入的内容,这个内容需要用data-value来保存,因为不确定要插入的值到底是当前节点的html内容还是别的值
        $iptId ? $iptId.value = value : '';
        index += $(this).attr('data-value').length;//把光标下标位置改变成插入当前的内容之后的下标位置
    });

    $(document).off('click keyup','#'+iptId).on('click keyup','#'+iptId,function () { //给被插入内容的输入框节点绑定事件
        var cursurPosition=0;
        if(this.selectionStart){//非IE
            cursurPosition= this.selectionStart;
        }else{//IE
            try{
                var range = document.selection.createRange();
                range.moveStart('character',-this.value.length);
                cursurPosition = range.text.length;
            }catch(e){
                cursurPosition = 0;
            }
        }
        index = cursurPosition; //上面是去获取当前光标所在位置,如果还没有聚焦获取过光标,那么index = 0
    });
};

//字符串替换
/*
 *字符串内容替换，strings=原字符串，replaces=原字符串中要替换的字符，replaceResult=更换掉要替换的字符,istoLowerCase=是否要忽略大小写(true=是，false=否)；
 * 如：returnStringReplaceResult('abc','A','d',true) => 'dbc';
 *     returnStringReplaceResult('abc','A','d',false) => 'abc';
 *     istoLowerCase不传则默认false,istoLowerCase只能传boolean类型，如果是其他的也默认为false
 * 如果replaceResult参数不传，则默认把要替换的值去除，('abc','a','',false) => 'bc'
 * */
var returnStringReplaceResult = function (strings,replaces,replaceResult,istoLowerCase) {
    if(!strings || !replaces) return strings;
    var toLowerCaseCategoryNames = istoLowerCase === true ? strings.toLowerCase() : strings,//转小写之后的数据字段
        toLowerCaseVal = istoLowerCase === true ? replaces.toLowerCase() : replaces,//转小写之后的查询字段
        newStrArr = toLowerCaseCategoryNames.split(toLowerCaseVal), //根据查询字段把转了小写之后的数据字段进行分割
        joinStr = '',//用来拼接转了小写之后的数据字段，主要是为了截取原数据字段中的内容
        dataStr = '',//用来接收截取出来的原数据字段内容
        endStr = '',//用来接收拼接完之后的原数据字段
        length = newStrArr.length;
    for(var i = 0; i < length; i++){
        dataStr = strings.substring(joinStr.length, joinStr.length + newStrArr[i].length);//这里每次循环获取到的内容就是没有转小写的原数据字段
        joinStr += newStrArr[i]; //这里把当前循环数组下标中转了小写的内容拼接起来
        if(i === (length-1)){
            endStr += dataStr;
        }else{
            endStr += dataStr + replaceResult; //把更换的内容拼接
        }
        joinStr += toLowerCaseVal; //把上面拼接的循环内容在加上要查询的内容,用来下次循环的时候截取下一段内容
    }
    return endStr;
};