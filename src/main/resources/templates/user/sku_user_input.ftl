<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<title>sku经理信息</title>
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
  <legend>sku身份信息</legend>
  <div class="layui-field-box">
             姓名：${(user.skuName)!''}<br/>
             <hr>
             手机：${(user.skuPhone)!''}<br/>
             <hr>
             公司：${(user.skuCompanyName)!''}<br/>    
             <hr>
     绑定时间：
     
      ${(user.createTime)?string('yyyy-MM-dd HH:mm:ss')}        
     
    
  </div>
</fieldset>
 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
      <#if user.status=='1'>
      <input type="hidden" value="${user.id}" id="uid">
       <a href="javascript:;" id="unBind" class="layui-btn">解绑</a>
    </#if> 
     <a href="javascript:history.go(-1);" class="layui-btn">返回</a>
     
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
				           url:'/admin/sku_user_input',
				           type:'post',
				           data:{"id":$('#uid').val()},
				           success:function(data) { 
				             layer.msg('解绑成功');
				             window.location="/admin/sku_user_list"
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
	