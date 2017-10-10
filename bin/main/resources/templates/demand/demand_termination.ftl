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
	<fieldset class="layui-elem-field">
  <legend>需求终止申请信息</legend>
  <div class="layui-field-box">
                             申请人:
                  <#if record.applyRole=='0'>
                                                  采购者-${record.user.name}
                  <#else>
                    sku经理-${record.user.skuName}
                  </#if>                              

           <hr>
                            申请时间：${record.createTime?string("yyyy-MM-dd HH:mm:ss")}
           <hr>
                             申请理由：${record.applyReson} 
           <hr>
                             当前审核状态：<#if record.status=='0'>
                                      未审核
               </#if>
               <#if record.status=='1'>
                                      已同意终止
               </#if>
               <#if record.status=='2'>
                                     拒绝申请
               </#if>
               <#if  record.status!='0'>
                   <hr>
                   审核理由：${record.refuseReason}
               
               </#if>
                <#if  record.status=='0'>
                   <hr>
                  
   审核理由：
   
      <textarea name="desc" id="desc" placeholder="请输入本次审核结果的依据" class="layui-textarea"></textarea>
   
  </div>
               
               </#if>
               
  </div>
</fieldset><input type="hidden" value="${record.demandId}" id="id" />
 <#if  record.status=='0'>
       &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<button data_type="1"  class="layui-btn layui-btn-primary" id="bind">允许终止</button>
       <button  class="layui-btn layui-btn-danger"  id="unBind">拒绝终止</button>
  <a href="javascript:history.go(-1);" class="layui-btn">返回</a>
 <#else>
   <#if record.status=='2'>
         &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <button  class="layui-btn layui-btn-primary" id="reStart">重启需求</button>
         <a href="javascript:history.go(-1);" class="layui-btn">返回</a>
   <#else>
          &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:history.go(-1);" class="layui-btn">返回</a>
    </#if>
 </#if> 
   <script type="text/javascript" src="../plugins/layui/layui.js"></script>
       	<script type="text/javascript" src="../js/jquery.min.js"></script>
       <script>
       layui.use('layer', function() {
				    var $ = layui.jquery,
					laypage = layui.laypage,
					layer = parent.layer === undefined ? layui.layer : parent.layer;
					
					$('#bind').on('click', function(){
					   layer.confirm('审核结果【允许终止】？', {
                                     btn: ['确认','取消'] //按钮
                                     }, function(){
   	                                        $.ajax({
				                            url:'/admin/demand_termination',
				                            type:'post',
				                           data:{"id":$('#id').val(),"desc":$('#desc').val(),"type":"1"},
				                      success:function(data) { 
				                      layer.msg('审核成功', {icon: 1});
				                         window.location="/admin/demand_list"
                                      },    
                                     error : function() {    
                                           layer.msg("异常！");    
                                       } 
				                   })
  
                         }, function(){
							  layer.msg('取消审核');
                            });
					
					
				 
					});
					$('#unBind').on('click', function(){
					  layer.confirm('审核结果【拒绝终止】？', {
                                     btn: ['确认','取消'] //按钮
                                     }, function(){
   	                                        $.ajax({
				                            url:'/admin/demand_termination',
				                            type:'post',
				                           data:{"id":$('#id').val(),"desc":$('#desc').val(),"type":"2"},
				                      success:function(data) { 
				                             if(data.errCode=='0000'){
				                             layer.msg('拒绝终止,状态回滚至【'+data.status+'】', {icon: 1});
				                              window.location="/admin/demand_list"
				                            
				                            }
                                      },    
                                     error : function() {    
                                           layer.msg("异常！");    
                                       } 
				                   })
  
                         }, function(){
							  layer.msg('取消审核');
                            });
					});
					$('#reStart').on('click', function(){
					  layer.confirm('重启需求【状态将回到终止前】？', {
                                     btn: ['确认','取消'] //按钮
                                     }, function(){
   	                                        $.ajax({
				                            url:'/admin/demand_termination',
				                            type:'post',
				                           data:{"id":$('#id').val(),"desc":$('#desc').val(),"type":"3"},
				                      success:function(data) { 
				                             if(data.errCode=='0000'){
				                             layer.msg('重启需求,状态回滚至【'+data.status+'】', {icon: 1});
				                              window.location="/admin/demand_list"
				                            
				                            }
                                      },    
                                     error : function() {    
                                           layer.msg("异常！");    
                                       } 
				                   })
  
                         }, function(){
							  layer.msg('取消审核');
                            });
					
					
					});
					
				})
          
         
       </script>
  
	</body>
</html>	