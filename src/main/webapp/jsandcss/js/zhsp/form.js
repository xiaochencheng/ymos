layui.use(['element','form'], function(){
	//加载select,checkbox,radio样式
			 var form = layui.form; 
			 var $ = layui.jquery;
			//全选全不选功能
			form.on('checkbox(allChoose)', function(data){
			    var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
			    child.each(function(index, item){
			      item.checked = data.elem.checked;
			    });
			    form.render('checkbox');
			});
			form.on('checkbox(itemChoose)',function(data){
			    var sib = $(data.elem).parents('table').find('tbody input[type="checkbox"]:checked').length;
			    var total = $(data.elem).parents('table').find('tbody input[type="checkbox"]').length;
			    if(sib == total){
			        $(data.elem).parents('table').find('thead input[type="checkbox"]').prop("checked",true);
			        form.render();
			    }else{
			        $(data.elem).parents('table').find('thead input[type="checkbox"]').prop("checked",false);
			        form.render('checkbox');
			    }
			}); 
		}); 