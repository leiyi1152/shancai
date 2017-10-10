<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>商品列表</title>
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
          <form id="form" action="/admin/product_list" method="post">
			<blockquote class="layui-elem-quote">
				商品名称：<input type="text" name="productName" value="${(p.productName)!''}" id="productName">
		       
				一级品类：
				<select name="categoryId">
				 <option value="">请选择</option>
				 <#list caList as ca>
				   <option value="${ca.id}" <#if p.categoryId??><#if p.categoryId==ca.id>selected</#if></#if> >${ca.categoryName}</option>
				 </#list>
				</select>
				<a href="javascript:;" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe615;</i> 搜索
				</a>
				 <a href="/admin/product_to_input" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe61f;</i> 添加商品
				</a>
			</blockquote>
			</form>	
			<fieldset class="layui-elem-field">
				<legend>列表</legend>
				<div class="layui-field-box">
					<table class="site-table table-hover">
						<thead>
							<tr>
								<th>商品名称</th>
								<th>一级品类</th>
								<th>商品图片</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<#list page.list as product>
						    <tr>
								<td>${product.productName}</td>
								<td>${product.category.categoryName}</td>
								<td><img src="${product.productPics}"  onclick="viewImg($(this))"></td>
								<td>
									<a href="/admin/product_to_input?id=${product.id}"  class="layui-btn">编辑</a>
									
								 	<a href="javascript:del('${product.id}');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger"/>删除</a>
								    
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
				           url:'/admin/product_page_json',
				           type:'post',
				           data:{"pageNo":obj.curr,"productName":$('#categoryName').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                                str += '<tr>'      
								str += '<td>'+val.productName+'</td>'
								str += '<td>'+val.category.categoryName+'</td>'
								str += '<td><img src="'+val.productPics +'"  onclick="viewImg($(this))"/></td>'
								str += '<td>'
								str += '<a href="/admin/product_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini">编辑</a>'
								str += '<a href="javascript:del(\''+val.id+'\');" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id='+ val.id+'>删除</a>'
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
			function del(id){
			$.ajax({
				           url:'/admin/product_del',
				           type:'post',
				           data:{"id":id},
				           success:function(data) { 
				              layer.msg("删除成功",{time:2000});
				              setTimeout(function(){window.location.href="/admin/product_list";},1000);
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				      })
			
			}
			function viewImg(str){
		      var commentPic = new Object();
		      commentPic.title="商品图片"
		      commentPic.id = '1' 
		      commentPic.start=0
		      var arrE = new Array();
		      str.parent('td').find('img').each(function(i){
		      var obj = new Object();
				    obj.alt='';
				    obj.pid=i;
				    obj.src=this.src;
				    obj.thumb='';
				    arrE[i]=obj;
		      })
		   commentPic.data = arrE;
		      layer.photos({
		         photos: commentPic 
		         ,anim: 5
		      })
		   }		
		</script>
	</body>

</html>