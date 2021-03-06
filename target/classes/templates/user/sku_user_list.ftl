<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>人员列表</title>
		<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="../css/global.css" media="all">
		<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="../css/table.css" />
		
		<style>
		  img {
		    display: inline-block;
		    border: none;
		    width: 20px;
          }
		</style>
	</head>

	<body>
		<div class="admin-main">
		<form id="form" action="/admin/sku_user_list" method="post">
			<blockquote class="layui-elem-quote">
				姓名：<input type="text" name="name" value="${(name)!}" id="name">
				手机：<input type="text" name="phone" value="${(phone)!}" id="phone">
				公司名称：<input type="text" name="companyName" value="${(companyName)!}" id="companyName">
		        <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe615;</i> 搜索
				</a>
			</blockquote>
		</form>		
			<fieldset class="layui-elem-field">
				<legend>列表</legend>
				<div class="layui-field-box">
					<table class="site-table table-hover">
						<thead>
							<tr>
							    <th>昵称</th>
								<th>头像</th>
								<th>姓名</th>
								<th>公司名称</th>
								<th>手机</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<#list page.list as user>
						    <tr>
						       
						        <td>${(user.nick)!''}</td>
								<td><img src="${(user.wxHeadImg)!''}" /></td>
								<td>${(user.skuName)!''}</td>
								<td>${(user.skuCompanyName)!''}</td>
								<td>${(user.skuPhone)!''}</td>
								
								<td>
									<a href="/admin/sku_user_to_input?id=${user.id}"  class="layui-btn layui-btn-mini">查看</a>
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
				           url:'/admin/sku_user_getList',
				           type:'post',
				           data:{"pageNo":obj.curr,"name":$('#name').val(),"phone":$('#phone').val(),"companyName":$('#companyName').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                                str += '<tr>'
                                str += '<td>'+val.nick+'</td>' 
                                str += '<td><img src="'+val.wxHeadImg+'" /></td>'     
								str += '<td>'+(val.hasOwnProperty('skuName')?val.skuName:'')+'</td>'
								str += '<td>'+(val.hasOwnProperty('skuCompanyName')?val.skuCompanyName:'')+'</td>'
								str += '<td>'+(val.hasOwnProperty('skuPhone')?val.skuPhone:'')+'</td>'
								
								str += '<td>'
								str += '<a href="/admin/sku_user_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini">查看</a>'
								str += '</td>'
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
						$('#form').submit();
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
		</script>
	</body>

</html>