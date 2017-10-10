<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>商品</title>
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
				<legend>商品<#if product??>修改<#else>添加</#if></legend>
			</fieldset>

			<form class="layui-form" action="" id="form">
			   <#if product??><input type="hidden" name="id" value="${product.id}"></#if>
				<div class="layui-form-item">
					<label class="layui-form-label">商品名称</label>
					<div class="layui-input-block">
						<input type="text" name="productName" <#if product??>value="${product.productName}"</#if>  lay-verify="required" autocomplete="off" placeholder="请输入商品名" class="layui-input" style="width:190px;">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">一级品类</label>
					<div class="layui-input-inline">
						<select name="categoryId" lay-verify="" lay-verify="required">
						  <option value="">请选择</option>
						  <#list caList as ca>
						  <option value="${ca.id}" <#if product??><#if product.categoryId==ca.id>selected</#if> </#if>>${ca.categoryName}</option>
						  </#list>
						</select>   
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">商品描述</label>
					<div class="layui-input-block">
					    <textarea name="productDesc" required lay-verify="required" placeholder="请输入" class="layui-textarea"><#if product??>${product.productDesc}</#if></textarea>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">预览图片</label>
				<div class="site-demo-upload">
				  <img id="LAY_demo_upload" width="80px" <#if product??> src="${product.productPics}" </#if>>
				  <input type="hidden" name="productPics"  <#if product??>value="${product.productPics}"</#if> id="LAY_IMG_URL">
              </div>
				</div>
			  	<div class="layui-form-item">
					<label class="layui-form-label">添加图片</label>
				<div class="site-demo-upload">
				   <input type="file" name="file" lay-type="images" width="90px" class="layui-upload-file">
              </div>
				</div>
			  
			
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
						<button type="reset" class="layui-btn layui-btn-primary">重置</button>
					    <a href="javascript:history.go(-1);" class="layui-btn">返回</a>
					</div>
				</div>
			</form>
		</div>
		<script type="text/javascript" src="../plugins/layui/layui.js"></script>
		<script type="text/javascript" src="../js/jquery.min.js"></script>
		<script>
			layui.use(['form','jquery','upload'], function() {
				var form = layui.form(),
					layer = layui.layer;
				 layui.upload({
				    url: '/upload'
				    ,method: 'post' //上传接口的http类型
				    ,success: function(res){
				     if(res.code!='0'){
				         layer.msg(res.msg,{time:2000})
				         return ;
				       }
				      LAY_demo_upload.src = res.data.src;
				      LAY_IMG_URL.value=res.data.src;
				    }
  });	
				//监听提交
				form.on('submit(demo1)', function(data) {
					   $.ajax({
				           url:'/admin/product_input',
				           type:'post',
				           data:$('#form').serialize(),
				           success:function(data) { 
				             if("1000"==data){
				                layer.msg('添加成功',{time:2000})
				               setTimeout(function(){window.location="/admin/product_list"},1000);
				              }else{
				                 layer.msg('修改成功',{time:2000})
				                 setTimeout(function(){window.location="/admin/product_list"},1000);
				               
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
                        $('#p_'+data.elem.id).attr('checked',true);
                      }
                     
              });  
			});
		</script>
	</body>

</html>