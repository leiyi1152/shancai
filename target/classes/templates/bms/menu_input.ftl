<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>菜单</title>
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
				<legend>菜单<#if menu??>修改<#else>添加</#if></legend>
			</fieldset>

			<form class="layui-form" action="" id="form">
			   <#if menu??><input type="hidden" name="id" value="${menu.id}"></#if>
				<div class="layui-form-item">
					<label class="layui-form-label">菜单名称</label>
					<div class="layui-input-block">
						<input type="text" name="menuName" <#if menu??>value=${menu.menuName} readonly</#if>  lay-verify="required" autocomplete="off" placeholder="请输入菜单名" class="layui-input" style="width:190px;">
					</div>
				</div>
				
				

				<div class="layui-form-item" >
					
					<div class="layui-inline">
						<label class="layui-form-label">菜单链接</label>
						<div class="layui-input-block">
							<input type="text" name="menuUrl" <#if menu??>value=${menu.menuUrl}</#if> lay-verify="required" autocomplete="off" placeholder="请输入菜单链接" class="layui-input" style="width:190px;">
						</div>
					</div>
				</div>
			
               
				<div class="layui-form-item">
					<label class="layui-form-label">上级菜单</label>
					<div class="layui-input-inline">
						<select name="parentId" lay-filter="aihao" style="width:190px;">
							<option value="0">顶级菜单</option>
							<#list list as m>
							  <option value="${m.id}" <#if menu??><#if menu.parentId==m.id>selected</#if></#if>  >${m.menuName}</option>
							
							</#list>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
               <div class="layui-inline">
						<label class="layui-form-label">排序</label>
						<div class="layui-input-inline">
							<input type="number" name="sortNum" <#if menu??>value=${menu.sortNum}</#if>  lay-verify="number" autocomplete="off" class="layui-input">
						</div>
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
			layui.use(['form', 'layedit', 'laydate'], function() {
				var form = layui.form(),
					layer = layui.layer;
					
				//监听提交
				form.on('submit(demo1)', function(data) {
					   $.ajax({
				           url:'../admin/menu_input',
				           type:'post',
				           data:$('#form').serialize(),
				           success:function(data) { 
				             if("0000"==data){
				                layer.msg('添加成功',{time:2000})
				               setTimeout(function(){window.location="../admin/menu_list"},1000);
				              }else if("0001"==data){
				                 layer.msg("菜单名已被使用！", {time:2000 })
				                
				              }else{
				                 layer.msg('修改成功',{time:2000})
				                 setTimeout(function(){window.location="../admin/menu_list"},1000);
				               
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