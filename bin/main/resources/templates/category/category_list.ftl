<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>一级品类列表</title>
		<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="../css/global.css" media="all">
		<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="../css/table.css" />
	</head>

	<body>
		<div class="admin-main">

			<blockquote class="layui-elem-quote">
				品类名称：<input type="text" name="categoryName" value="${(categoryName)!}" id="categoryName">
		       <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe615;</i> 搜索
				</a>
				 <a href="/admin/category_to_input" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe61f;</i> 添加品类
				</a>
			</blockquote>
				
			<fieldset class="layui-elem-field">
				<legend>列表</legend>
				<div class="layui-field-box">
					<table class="site-table table-hover">
						<thead>
							<tr>
								<th>品类名称</th>
								<th>上级品类</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<#list page.list as category>
						    <tr>
								<td>${category.categoryName}</td>
								<td>一级品类</td>
								<td style="text-align:center;"><#if category.deletStatus=="0"><i class="layui-icon" style="color:red;">ဇ</i></#if><#if category.deletStatus=="1"><i class="layui-icon" style="color:green;"></i></#if></td>
								<td>
									<a href="/admin/category_to_input?id=${category.id}"  class="layui-btn layui-btn-mini">编辑</a>
									<#if category.deletStatus=="0">
								 	<a href="javascript:del('${category.id}');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id="${category.id}">启用</a>
								    </#if>
								    <#if category.deletStatus=="1">
								 	<a href="javascript:del('${category.id}');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id="${category.id}">停用</a>
								    </#if>
								</td>
							</tr>
						</#list>
						</tbody>
					</table>

				</div>
			</fieldset>
			<div class="admin-table-page">
				<div id="page" class="page">
				</div>
			</div>
		</div>
		<script type="text/javascript" src="../plugins/layui/layui.js"></script>
		<script type="text/javascript" src="../js/jquery.min.js"></script>
		<script>
			layui.config({
				base: '../plugins/layui/modules/'
			});

			layui.use(['icheck', 'laypage','layer'], function() {
				var $ = layui.jquery,
					laypage = layui.laypage,
					layer = parent.layer === undefined ? layui.layer : parent.layer;
				$('input').iCheck({
					checkboxClass: 'icheckbox_flat-green'
				});

				//page
				laypage({
					cont: 'page',
					pages: ${page.pages} //总页数
						,
					groups: 10 //连续显示分页数
						,
					first:true,
					last:true,
					jump: function(obj, first) {
						//得到了当前页，用于向服务端请求对应数据
						var curr = obj.curr;
						if(!first) {
						 $.ajax({
				           url:'/admin/category_getList',
				           type:'post',
				           data:{"pageNo":obj.curr,"categoryName":$('#categoryName').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                                str += '<tr>'      
								str += '<td>'+val.categoryName+'</td>'
								
								str += '<td>一级品类</td>'
								str += '<td style="text-align:center;">'					
								str += val.deletStatus=='1'?'<i class="layui-icon" style="color:green;"></i>':'<i class="layui-icon" style="color:red;">ဇ</i>'			
								str += '</td>'
								str += '<td>'
								str += '<a href="/admin/category_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini">编辑</a>'
								str += '<a href="javascript:del(\''+val.id+'\');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id='+ val.id+'>'+(val.deletStatus=='1'?'停用':'启用')+'</a>'
                                str += '</tr>'
                            
                            })
                            $('.layui-elem-field').find('tbody').html(str)
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				 })
				 	}
					}
				});

				$('#search').on('click', function() {
						 $.ajax({
				           url:'/admin/category_getList',
				           type:'post',
				           data:{"categoryName":$('#categoryName').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                                str += '<tr>'      
								str += '<td>'+val.categoryName+'</td>'
								
								str += '<td>一级品类</td>'
								str += '<td style="text-align:center;">'					
								str += val.deletStatus=='1'?'<i class="layui-icon" style="color:green;"></i>':'<i class="layui-icon" style="color:red;">ဇ</i>'			
								str += '</td>'
								str += '<td>'
								str += '<a href="../admin/category_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini">编辑</a>'
								str += '<a href="javascript:del(\''+val.id+'\');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id='+ val.id+'>'+(val.deletStatus=='1'?'停用':'启用')+'</a>'
                                str += '</tr>'
                            
                            })
                            $('.layui-elem-field').find('tbody').html(str)
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				 })
				});
				$('.site-table tbody tr').on('click', function(event) {
					var $this = $(this);
					var $input = $this.children('td').eq(0).find('input');
					$input.on('ifChecked', function(e) {
						$this.css('background-color', '#EEEEEE');
					});
					$input.on('ifUnchecked', function(e) {
						$this.removeAttr('style');
					});
					$input.iCheck('toggle');
				}).find('input').each(function() {
					var $this = $(this);
					$this.on('ifChecked', function(e) {
						$this.parents('tr').css('background-color', '#EEEEEE');
					});
					$this.on('ifUnchecked', function(e) {
						$this.parents('tr').removeAttr('style');
					});
				});
						
			});
			function del(id){
			$.ajax({
				           url:'../admin/category_del',
				           type:'post',
				           data:{"id":id},
				           success:function(data) { 
				              layer.msg("状态修改成功",{time:2000});
				              setTimeout(function(){window.location.href="../admin/category_list";},1000);
				              
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				      })
			
			}
			
		</script>
	</body>

</html>