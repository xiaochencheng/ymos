var wxPicUpload = (function($){
    // 请求用到的参数
    var getDataOp = {
        album:{//店小秘图片空间
            bucket:'wxalbum',
            region:'ap-shanghai',
            rp:'cos.ap-shanghai',
            to:'picsh'
        },
        product:{//商品图片空间
            bucket:'productimage',
            region:'ap-guangzhou',
            rp:'cos.ap-guangzhou',
            to:'picgz'
        },
        watermark:{//水印
            bucket:'',
            region:'',
            rp:'',
            to:''
        }
    };
    var getWinLocHref = function(){
        var location = (window.location.href).split('/');
        var basePath = location[0]+'//'+location[2]+'/';
        return basePath;
    };
    //店小秘系统回调
    var clientPicCallBack = function(bucket, fileId, fileName, isQc){
        $.ajax({
            type: 'POST',
            url: '/cos/cosDxmCallBack.json',
            data:{
                bucket: bucket,
                fileId: fileId,
                fileName: fileName,
                isQc: isQc
            },
            dataType: 'json',
            async : true,
            success:function(data){
                //console.log(data);
                if(data.code != 0){
                    $.fn.message({type:'error', msg:data.msg});
                } else {
                    $.fn.message({type:'success', msg:'上传成功'});
                }
            }
        });
    };
    return {
        getAuthorization : function (_op, op, callback) {
            if(!_op || !op) return callback('Type errror');
            var xhr = new XMLHttpRequest();
            // 商品图片
            var authOp = {
                bucket: op.bucket,
                region: op.region,
                productId: _op.productId,
                isQc: _op.isQc || 0
            };
            var getUrl = getWinLocHref()+'cos/getSign.json?op='+JSON.stringify(authOp);
            //bucket='+op.bucket+'&region='+op.region+'&productId='+_op.productId+'&isQc='+_op.isQc
            //console.log(getUrl);
            xhr.open('GET',getUrl, true);
            /*if(_op.type === 'product') xhr.setRequestHeader('productId', _op.productId);*/
            xhr.onload = function (e) {
                var data;
                try {
                    data = JSON.parse(xhr.responseText)
                } catch (e) {}
                if (data) {
                    //console.log(data);
                    callback(null, data);
                } else {
                    callback('获取签名出错');
                }
            };
            xhr.onerror = function (e) {
                callback('获取签名出错');
            };
            xhr.send();
        },
        uploadFile : function (file, options) {
            if(file && file.size > 2097152) {
                alert('文件'+file.name+'大小超出限制！文件最大不能超过2048KB！');
                return;
            }
            /*
            *  files 上传的图片集合 arr
            *
            *  options
            *   - type: 上传的文件夹 string
            *   - productId 如果是商品图片空间，要传productId
            *   - callback  完成后回调  function(error,data){}
            *
            */
            if(!options || !options.type || !getDataOp[options.type]) return;
            var _self = this;
            var op = getDataOp[options.type];
            _self.getAuthorization(options, op, function (err, info) {
                if (err) return console.log(err);
                var xhr = new XMLHttpRequest();
                //console.log(info);
                xhr.open('PUT', info.url, true);
                xhr.setRequestHeader('Authorization', info.sign);
                xhr.onload = function () {
                    //console.log(xhr.responseText);
                    //console.log(JSON.parse(xhr.responseText));
                    var url = info.url;
                    if ((url && url.indexOf('blob:') === -1) && (xhr.status === 200 || xhr.status === 206)) {
                        //var data = xhr.responseText;
                        //var ETag = xhr.getResponseHeader('etag');
                        clientPicCallBack(op.bucket,info.fileId,file.name, options.isQc);//店小秘系统回调
                        url = url.replace(op.rp,op.to);
                        options.callback && options.callback(null, {url:url,size:file.size,name:file.name});
                    } else {
                        console.log('文件上传失败，状态码：' + xhr.status );
                        options.callback && options.callback({error:xhr.status,file:file});
                    }
                };
                xhr.onerror = function () {
                    options.callback && options.callback({error:xhr.responseText,file:file});
                    console.log('文件上传失败,请检查是否没配置CORS跨域规则');
                };
                xhr.send(file);
            });
        }
    }
})(jQuery);
(function ($) {
    /*
    *  options
    *   - type: 上传的文件夹 string
    *   - productId 如果是商品图片空间，要传productId
    *   - callback  完成后回调  function(error,data){}
    *
    */
    function picUpload(element,options){
        this.init(element,options);
    }
    picUpload.prototype = {
        init : function(element,options){
            var _self = this;
            if($('#loadipt').length == 0){
                $('body').append('<div style="display:none;"><input type="file" multiple="multiple" id="loadipt"/></div>');
            }
            $ipt = $('#loadipt');
            $(element).on('click',function(){
                var _that = $(this);
                $ipt.click();
                $ipt.off('change').on('change',function(){
                    var files = this.files,
                        arr=[],filesArr = [];
                    var settime = function(filesLen,arrLen,call){
                        if(filesLen == arrLen){
                            call();
                        }
                    };
                    $.each(files,function(i,j){
                        filesArr.push(j);
                        wxPicUpload.uploadFile(j,{
                            type:options.type,
                            productId:options.productId,
                            callback:function(e,data){
                                arr.push(e || data);
                                settime(filesArr.length,arr.length,function(){
                                    options.callback && options.callback(arr);
                                })
                            }
                        })
                    });
                });
            })
        }
    };
    $.fn.wxPicUpload = function (options) {
        this.each(function(){
            new picUpload(this, options);
        });
    };
    $.fn.wxPicUpload.defaults = {
    };
    $.fn.wxPicUpload.Constructor = picUpload;
})(jQuery);