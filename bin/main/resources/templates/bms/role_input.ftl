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
				<legend>角色<#if menu??>修改<#else>添加</#if></legend>
			</fieldset>

			<form class="layui-form" action="" id="form">
			   <#if role??><input type="hidden" name="id" value="${role.id}"></#if>
				<div class="layui-form-item">
					<label class="layui-form-label">角色名称</label>
					<div class="layui-input-block">
						<input type="text" name="roleName" <#if role??>value=${role.roleName} readonly</#if>  lay-verify="required" autocomplete="off" placeholder="请输入菜单名" class="layui-input" style="width:190px;">
					</div>
				</div>
				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 16px;">角色菜单</legend>
			    </fieldset>
				<blockquote class="layui-elem-quote layui-quote-nm">

				<#list mList as menu>
				   <#if menu.parentId=="0">
				      <input type="checkbox" name="menuId" title="${menu.menuName}" value="${menu.id}"  <#if menu.isHas==true>checked </#if> id="p_${menu_index}">
				      <#list mList as subMenu>
				        <#if subMenu.parentId==menu.id>
				          <input type="checkbox" name="menuId" title="${subMenu.menuName}" value="${subMenu.id}" lay-skin="primary" <#if subMenu.isHas==true>checked</#if> lay-filter="_check_" id="${menu_index}">
				        </#if>
				      </#list> 
				      <hr>
				   </#if>
				</#list>
			</blockquote>
			
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
		
		     layui.config({
				base: '../plugins/layui/modules/'
			});
			layui.use(['form','jquery','icheck'], function() {
				var form = layui.form(),
					layer = layui.layer;
					
				//监听提交
				form.on('submit(demo1)', function(data) {
					   $.ajax({
				           url:'../admin/role_input',
				           type:'post',
				           data:$('#form').serialize(),
				           success:function(data) { 
				             if("0000"==data){
				                layer.msg('添加成功',{time:2000})
				               setTimeout(function(){window.location="../admin/role_list"},1000);
				              }else if("0002"==data){
				                 layer.msg("角色名已被使用！", {time:2000 })
				                
				              }else{
				                 layer.msg('修改成功',{time:2000})
				                 setTimeout(function(){window.location="../admin/role_list"},1000);
				               
				               }
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				 })
					return false;
				});
				   
				form.on('checkbox(_check_)', function(data){
                     var dom = data.elem //得到checkbox原始DOM对象
                     var isOpen = data.elem.checked; //开关是否开启，true或者false
                     if(isOpen){
                        var $input = $('#p_'+data.elem.id);
					    $input.prop('checked',true);
                      }
                     
              });  
			});
		</script>
	</body>

</html>