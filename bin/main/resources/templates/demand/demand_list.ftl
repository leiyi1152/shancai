<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>需求列表</title>
		<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="../css/global.css" media="all">
		<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="../css/table.css" />
	</head>

	<body>
		<div class="admin-main">

			<blockquote class="layui-elem-quote">
				发布人：	<div class="layui-input-inline"><input type="text" name="publishedName" value="${(publishedName)!}" id="publishedName"></div>
				发布人手机：  <div class="layui-input-inline"> <input type="text" name="publishedPhone" value="${(publishedPhone)!}" id="publishedPhone"></div>
		        状态：  <div class="layui-input-inline"> 
		        <select name="demandStatus" id="demandStatus">
					  <option value="">全部</option>
					  <option value="0">待响应</option>
					  <option value="1">需求确认中</option>
					  <option value="2">订单确认</option>
					  <option value="3">订单进行</option>
					  <option value="4">正在配货</option>
					  <option value="5">等待付款</option>
					  <option value="6">已完成</option>
					  <option value="7">终止申请中</option>
					  <option value="8">已终止</option>
                </select>
                </div>
                品类： <div class="layui-input-inline"> 
                <select id="categoryId">
                  <option value="">全部</option>
                  <#list list as ca>
                      <option value="${ca.id}">${ca.categoryName}</option>
                  </#list>
                </select>
                </div>
                                       发布时间：
		        <div class="layui-input-inline">
                   <input class="layui-input" placeholder="开始区间" id="LAY_demorange_s">
                </div>
			    <div class="layui-input-inline">
			      <input class="layui-input" placeholder="结束区间" id="LAY_demorange_e">
			    </div>
		       <a href="javascript:;" class="layui-btn layui-btn-small" id="search">
					<i class="layui-icon">&#xe615;</i> 搜索
				</a>
			</blockquote>
				
			<fieldset class="layui-elem-field">
				<legend>列表</legend>
				<div class="layui-field-box">
					<table class="site-table table-hover">
						<thead>
							<tr>
								<th>发布人</th>
								<th>发布人手机</th>
								<th>所属品类</th>
								<th>发布时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<#list page.list as demand>
						    <tr title="点击查看sku经理">
								<td id="td${demand.id}">${demand.publishedName}</td>
								<td>${demand.publishedPhone}</td>
								<td>${demand.category.categoryName}</td>
								<td>${demand.pushlishedTime?string("yyyy-MM-dd HH:mm:ss")}</td>
								<td>
								 <#if demand.demandStatus=='0'>
								    待响应
								 </#if>
								 <#if demand.demandStatus=='1'>
								 需求确认中
								 </#if>
								<#if demand.demandStatus=='2'>
								    订单确认中
								 </#if>
								 <#if demand.demandStatus=='3'>
								 订单进行中
								 </#if><#if demand.demandStatus=='4'>
								    正在配货
								 </#if>
								 <#if demand.demandStatus=='5'>
								 等待付款
								 </#if>
                                 <#if demand.demandStatus=='6'>
								 已完成
								 </#if>
                                 <#if demand.demandStatus=='7'>
								需求终止中
								 </#if>
                                 <#if demand.demandStatus=='8'>
								 已终止
								 </#if>
                                </td>
								<td>
								    <input type="hidden" value="${demand.id}">
									<a href="/admin/demand_to_input?id=${demand.id}" class="layui-btn layui-btn-mini" titie="详情">详情</a>
									<#if demand.demandStatus=="7"||demand.demandStatus=="8">
								 	<a href="/admin/demand_to_termination?id=${demand.id}" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" titie="审核">审核</a>
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

			layui.use(['icheck', 'laypage','layer','laydate'], function() {
				    var $ = layui.jquery,
					laypage = layui.laypage,
					layer = parent.layer === undefined ? layui.layer : parent.layer;
					var start = {
				    min: '1900-01-01'
				    ,max: '2099-06-16'
				    ,istoday: true
				    ,istime: true
				    , format: 'YYYY-MM-DD'
				    ,choose: function(datas){
				      end.min = datas; //开始日选好后，重置结束日的最小日期
				      end.start = datas //将结束日的初始值设定为开始日
				    }
				  };
				  
				  var end = {
				    min: '1900-01-01'
				    ,max: '2099-06-16'
				    ,istoday: true
				    ,istime: true
				    , format: 'YYYY-MM-DD'
				    ,choose: function(datas){
				      start.max = datas; //结束日选好后，重置开始日的最大日期
				    }
				  };
				  
				  document.getElementById('LAY_demorange_s').onclick = function(){
				    start.elem = this;
				    laydate(start);
				  }
				  document.getElementById('LAY_demorange_e').onclick = function(){
				    end.elem = this
				    laydate(end);
				  }
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
				           url:'/admin/demand_getList',
				           type:'post',
				           data:{"pageNo":obj.curr,"publishedName":$('#publishedName').val(),"publishedPhone":$('#publishedPhone').val(),"demandStatus":$('#demandStatus').val(),"publishedTimeStart":$('#LAY_demorange_s').val(),"publishedTimeEnd":$('#LAY_demorange_e').val(),"categoryId":$('#categoryId').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                                str += '<tr title="点击查看sku经理">'      
								str += '<td>'+val.publishedName+'</td>'
								str += '<td>'+val.publishedPhone+'</td>'
								str += '<td>'+val.categoryName+'</td>'
								str += '<td>'+val.publishedTime+'</td>'
								str += '<td>'+val.demandStatus+'</td>'
								str += '<td>'
								str += '<input type="hidden" value="'+val.id+'">'
								str += '<a href="/admin/demand_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini" title="详情">详情</a>'
							     if(val.demandStatus=='终止申请中'||val.demandStatus=='已终止'){
							        	str += '<a href="/admin/demand_to_termination?id='+val.id+'" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id='+ val.id+' title="审核">审核</a>'
							    }
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
				           url:'/admin/demand_getList',
				           type:'post',
				           data:{"publishedName":$('#publishedName').val(),"publishedPhone":$('#publishedPhone').val(),"demandStatus":$('#demandStatus').val(),"publishedTimeStart":$('#LAY_demorange_s').val(),"publishedTimeEnd":$('#LAY_demorange_e').val(),"categoryId":$('#categoryId').val()},
				           success:function(data) { 
				            str = ''; 
                            $.each(data.list,function(i,val){
                               str += '<tr title="点击查看sku经理">'      
								str += '<td>'+val.publishedName+'</td>'
								str += '<td>'+val.publishedPhone+'</td>'
								str += '<td>'+val.categoryName+'</td>'
								str += '<td>'+val.publishedTime+'</td>'
								str += '<td>'+val.demandStatus+'</td>'
								str += '<td>'
								str += '<input type="hidden" value="'+val.id+'">'
								str += '<a href="/admin/demand_to_input?id='+val.id+'"  class="layui-btn layui-btn-mini" title="详情">详情</a>'
							    if(val.demandStatus=='终止申请中'||val.demandStatus=='已终止'){
							      str += '<a href="/admin/demand_to_termination?id='+val.id+'" data-id="1" data-opt="del" class="layui-btn layui-btn-danger layui-btn-mini" id='+ val.id+' title="审核">审核</a>'
							    }
                                str += '</tr>'
                            })
                            $('.layui-elem-field').find('tbody').html(str)
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				 })
				});
				$(document).on('click','.site-table tbody tr',function(event) {
				         if(event.target.nodeName!='TD'){
				             return ;
				         }
					    var $this = $(this);
					  	var id = $this.find('input').eq(0).val();
					  	$.ajax({
					  	   url:"/admin/demand_getDemand",
					  	   data:{"id":id},
					  	   type:'post',
					  	   success:function(data) { 
					  	       layer.open({
									  type: 0,
									  skin: 'layui-layer-demo', //样式类名
									  closeBtn: 0, //不显示关闭按钮
									  anim: 2,
									  shadeClose: true, //开启遮罩关闭
									  title:'需求响应人',
									  content: '需求名称:'+(data.hasOwnProperty('title')?data.title:'--')+';响应人:'+(data.hasOwnProperty('skuer')?data.skuer:'--')+';手机:'+(data.hasOwnProperty('skurPhone')?data.skurPhone:'--')
							  });
					  	   
					  	   },
					  	   error:function(){
					  	   
					  	   }
					  	
					  	})
					
				
				});
			});
			
		</script>
	</body>

</html>