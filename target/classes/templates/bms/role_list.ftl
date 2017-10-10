<!doctype html>
<html>

	<head>
		<meta charset="utf-8">
		<title>角色</title>
		<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="../css/global.css" media="all">
		<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="../css/table.css" />
	</head>

	<body>
	
			<blockquote class="layui-elem-quote">
				角色名称：<input type="text" name="roleName" value="${(roleName)!}" id="roleName">
		       <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe615;</i> 搜索
				</a>
				 <a href="/admin/role_to_input" class="layui-btn layui-btn-small" >
					<i class="layui-icon">&#xe61f;</i> 添加 
				</a>
			</blockquote>
				
			<fieldset class="layui-elem-field">
				<legend>角色列表</legend>
				<div class="layui-field-box">
					<table class="site-table table-hover">
						<thead>
							<tr>
								<th>角色名称</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<#list list as role>
						    <tr>
								<td>${role.roleName}</td>
								
								<td>
									<a href="../admin/role_to_input?id=${role.id}"  class="layui-btn layui-btn-mini">编辑</a>
									<a href="javascript:del('${role.id}');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" >删除</a>
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
					pages: ${role.pages} //总页数
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
				           url:'../admin/role_getlist',
				           type:'post',
				           data:{"pageNo":obj.curr,"roleName":$('#roleName').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                                str += '<tr>'
								str += '<td>'+val.roleName+'</td>'
								str += '<td>'
								str += '<a href="../admin/role_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini">编辑</a>'
								str += '<a href="javascript:del('+val.id+');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id='+ val.id+'>删除</a>'
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
				           url:'../admin/role_getlist',
				           type:'post',
				           data:{"roleName":$('#roleName').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                                str += '<tr>'
								str += '<td>'+val.roleName+'</td>'
								str += '<td>'
								str += '<a href="../admin/role_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini">编辑</a>'
								str += '<a href="javascript:del('+val.id+');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id='+ val.id+'>删除</a>'
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
				           url:'../admin/role_del',
				           type:'post',
				           data:{"id":id},
				           success:function(data) { 
				              layer.msg("删除成功",{time:2000});
				              setTimeout(function(){window.location.href="../admin/role_list";},1000);
				              
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				      })
			
			}
			
		</script>
	</body>

</html>