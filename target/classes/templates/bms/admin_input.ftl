<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>管理员</title>
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
				<legend>管理员<#if admin??>修改<#else>添加</#if></legend>
			</fieldset>

			<form class="layui-form" action="" id="form">
			<#if admin??><input type="hidden" name="id" value="${admin.id}"/></#if>
			 
				<div class="layui-form-item">
					<label class="layui-form-label">账号：</label>
					<div class="layui-input-block">
						<input type="text" name="account" <#if admin??>value="${admin.account}" readonly</#if> lay-verify="account" style="width:190px;" autocomplete="off" placeholder="请输入账号" class="layui-input">
					</div>
				</div>
			   <div class="layui-form-item">
					<label class="layui-form-label">密码</label>
					<div class="layui-input-inline">
						<input type="password" name="password" <#if admin??>value="${admin.passWord}"</#if> lay-verify="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
					</div>
					<div class="layui-form-mid layui-word-aux">请填写6到18位密码</div>
				</div>
			<div class="layui-form-item">
					<label class="layui-form-label">确认密码</label>
					<div class="layui-input-inline">
						<input type="password" name="password1" <#if admin??>value="${admin.passWord}"</#if>  lay-verify="password1" placeholder="请输入确认密码" autocomplete="off" class="layui-input">
					</div>
					<div class="layui-form-mid layui-word-aux">请填写确认密码</div>
				</div>
                   <div class="layui-form-item">
					<label class="layui-form-label">密码提示：</label>
					<div class="layui-input-block">
						<input type="text" name="passwordPrompt" <#if admin??>value="${admin.passwordPrompt}"</#if>  lay-verify="required" style="width:190px;" autocomplete="off" placeholder="请输入密码提示" class="layui-input">
					</div>
				</div>
			
				<div class="layui-form-item">
					<label class="layui-form-label">状态</label>
					<div class="layui-input-block">
						<input type="checkbox" name="isLock" lay-skin="switch" title="开关" <#if admin??><#if admin.isLock=="0">checked</#if></#if>>
					</div>
				</div>
				<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
				<legend style="font-size: 16px;">所属角色</legend>
			    </fieldset>
				<blockquote class="layui-elem-quote layui-quote-nm">

				<#list rList as role>
				      <input type="checkbox" name="roleId" title="${role.roleName}" value="${role.id}"  <#if role.isHas==true>checked </#if> >
				</#list>
			
				
			</blockquote>
			
			
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
						<button type="reset" class="layui-btn layui-btn-primary">重置</button>
					   <#if admin??><button type="button" class="layui-btn layui-btn-primary" onclick="javascript:window.location.href='/admin/admin_list'">返回</button></#if>
					</div>
				</div>
			</form>
		</div>
		<script type="text/javascript" src="../plugins/layui/layui.js"></script>
		<script type="text/javascript" src="../js/jquery.min.js"></script>
		<script>
			layui.use(['form'], function() {
				var form = layui.form(),
				layer = parent.layer === undefined ? layui.layer : parent.layer;
				//自定义验证规则
				form.verify({
					account: function(value) {
						if(value.length < 2) {
							return '账号长度不能少于2';
						}
						if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                                 return '用户名不能有特殊字符';
                        }
					},
					password: [/(.+){6,18}$/, '密码必须6到18位'],
					password1: function(value){
					  var  password =  $("input[name='password']").val();
					  if(value!=password){
					     return '确认密码不一致';
					  }
					}
					
				});

				//监听提交
				form.on('submit(demo1)', function(data) {
				    	$.ajax({
				           url:'../admin/admin_input',
				           type:'post',
				           data:$('#form').serialize(),
				           success:function(data) { 
				             if("0000"==data){
				                layer.msg('添加成功',{time:2000})
				                  setTimeout(function(){window.location.href="../admin/admin_list";},1000);
				              }else if("0001"==data){
				                  layer.msg("账号已被使用！", {time:2000 })
				                
				              }else{
				                 layer.msg('修改成功',{time:2000})
				                 setTimeout(function(){window.location.href="../admin/admin_list";},1000);
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