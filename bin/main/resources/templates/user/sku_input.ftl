<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>品类</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no">

		<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">
	</head>

	<body>
		<div style="margin: 15px;">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend>导入记录<#if sku??>修改<#else>添加</#if></legend>
			</fieldset>

			<form class="layui-form" action="" id="form">
			   <#if sku??><input type="hidden" name="id" value="${sku.id}"></#if>
				<div class="layui-form-item">
					<label class="layui-form-label">姓名</label>
					<div class="layui-input-block">
						<input type="text" name="name" <#if sku??>value=${sku.name} </#if>  lay-verify="required" autocomplete="off" placeholder="" class="layui-input" style="width:190px;">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">手机</label>
					<div class="layui-input-block">
						<input type="text" name="phone" <#if sku??>value=${sku.phone} </#if>  lay-verify="required" autocomplete="off" placeholder="" class="layui-input" style="width:190px;">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">绑定状态</label>
					<div class="layui-input-block">
					    <input type="checkbox" name="isBind" value="1" <#if sku??><#if sku.isBind=='1'>checked</#if> </#if>lay-skin="switch">
					</div>
				</div>
				
				
			
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
						<button type="reset" class="layui-btn layui-btn-primary">重置</button>
					</div>
				</div>
			</form>
		</div>
		<script type="text/javascript" src="../plugins/layui/layui.js"></script>
		<script type="text/javascript" src="../js/jquery.min.js"></script>
		<script>
			layui.use(['form','jquery'], function() {
				var form = layui.form(),
					layer = layui.layer;
					
				//监听提交
				form.on('submit(demo1)', function(data) {
					   $.ajax({
				           url:'/admin/sku_input',
				           type:'post',
				           data:$('#form').serialize(),
				           success:function(data) { 
				             if("0000"==data){
				              
				                 layer.msg('修改成功',{time:2000})
				                 setTimeout(function(){window.location="../admin/sku_import_list"},1000);
				               
				               }else{
				                layer.msg('添加成功',{time:2000})
				                 setTimeout(function(){window.location="../admin/sku_import_list"},1000);
				               }
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				 })
					return false;
				});
				
			});
		</script>
	</body>

</html>