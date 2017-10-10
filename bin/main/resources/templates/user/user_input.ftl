<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>用户信息</title>
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
<fieldset class="layui-elem-field">
  <legend>采购身份信息</legend>
  <div class="layui-field-box">
             姓名：${(user.name)!''}<br/>
             <hr>
             手机：${(user.phone)!''}<br/>
             <hr>
             公司：${(user.companyName)!''}<br/>    
             <hr>
     采购人员绑定时间：
     <#if user.userStatus=='2'>
     ${(user.buyerBindTime)?string('yyyy-MM-dd HH:mm:ss')}        
     </#if>   
    
  </div>
</fieldset>
<fieldset class="layui-elem-field">
  <legend>sku经理身份信息</legend>
  <div class="layui-field-box">
             姓名：${(user.skuName)!''}<br/>
             <hr>
             手机：${(user.skuPhone)!''}<br/>
             <hr>
             公司：${(user.skuCompanyName)!''}<br/>  
             <hr>
     sku经理绑定时间：
      <#if user.skuStatus=='2'>${(user.skuBindTime)?string('yyyy-MM-dd HH:mm:ss')}   
      </#if>          
  </div>
</fieldset>
              
     <#if user.skuStatus=='2'>
      <input type="hidden" value="${user.id}" id="id" />
       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<button  class="layui-btn layui-btn-primary" id="unBind">解绑sku经理</button>
       <a href="javascript:history.go(-1);" class="layui-btn">返回</a>
     <#else>
     &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <a href="javascript:history.go(-1);" class="layui-btn">返回</a>
     
     </#if>
     <script type="text/javascript" src="../plugins/layui/layui.js"></script>
       	<script type="text/javascript" src="../js/jquery.min.js"></script>
       <script>
       var layer;
       layui.use('layer', function() {
				    var $ = layui.jquery,
					laypage = layui.laypage,
					layer = parent.layer === undefined ? layui.layer : parent.layer;
					
					$('#unBind').on('click', function(){
					   $.ajax({
				           url:'/admin/user_input',
				           type:'post',
				           data:{"id":$('#id').val()},
				           success:function(data) { 
				             layer.msg('解绑成功');
				             window.location="/admin/user_list"
                         },    
                        error : function() {    
                           layer.msg("异常！");    
                        } 
				 })
          
					})
				})
          
         
       </script>
</body>
</html>	
	