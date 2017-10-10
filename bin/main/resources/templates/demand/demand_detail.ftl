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
	<div class="layui-tab">
  <ul class="layui-tab-title">
    <li  class="layui-this">需求信息</li>
    <li>相关人员信息</li>
    <li>物流信息</li>
    <li>付款信息</li>
    <li>日志</li>
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item  layui-show">
        <fieldset class="layui-elem-field">
        <legend>需求信息</legend>
         <div class="layui-field-box">
                       状态：<#if demand.demandStatus=='0'>
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
           <hr>
           <#if demand.demandStatus!='0'>               
                             响应时间：${demand.responseTime?string("yyyy-MM-dd HH:mm:ss")}   
           <#else>
                             响应时间：--
           </#if>   
           <hr>
                            需求名称: ${(demandExt.demandTitle)!'--'}
           <hr>
                            商品名称：${(demand.productName)!'--'}
           <hr>
                            商品单价：${(demandExt.productPrice)!'--'} 
           <hr>
                          总数量:${(demandExt.productCount)!'--'}
            <hr>  
                            总价格：${(demandExt.totalPrice)!'--'}
          <hr>
                             需求描述：
           <textarea name="" required lay-verify="required" placeholder="暂无" class="layui-textarea">${(demand.demandDesc)!''}</textarea>                  
          <hr>
                          需求图片：<a href="javascript:void(0);" id="viewPics" class="layui-btn layui-btn-mini" >点击查看</a> 
           <hr>
                            合同图片：<a href="javascript:void(0);" id="viewContractPics" class="layui-btn layui-btn-mini" >点击查看</a>                                                      
         </div>
     </fieldset>
       
     
    
    </div>
    <div class="layui-tab-item">
     <fieldset class="layui-elem-field">
        <legend>人员信息</legend>
         <div class="layui-field-box">
                            发布人:${demand.publishedName}
           <hr>
                            发布人手机：${demand.publishedPhone}
           <hr>
                            发布时间：${demand.pushlishedTime?string("yyyy-MM-dd HH:mm:ss")} 
           <hr>
                            状态：<#if demand.demandStatus=='0'>
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
           <hr>
           <#if demand.demandStatus!='0'>               
                             响应时间：${demand.responseTime?string("yyyy-MM-dd HH:mm:ss")}   
           <#else>
                             响应时间：--
           </#if>   
           <hr>
                         响应sku经理：${(demandExt.skuUserName)!'--'}
          <hr>
                         响应sku手机：${(demandExt.skuUserPhone)!'--'}  
          <hr>
                         响应sku经理公司：${(demandExt.skuCompany)!'--'}                                                         
         </div>
     </fieldset>

    </div>
   
    <div class="layui-tab-item">
     <fieldset class="layui-elem-field">
  <legend>物流</legend>
  <div class="layui-field-box">
    交货时间:
    <#if demandExt.deliveryDeadLine??>
         ${(demandExt.deliveryDeadLine)?string('yyyy-MM-dd HH:mm')}
    <#else>
      ---
    </#if>
   
   <hr> 
      物流公司：${(demandExt.expressCompany)!'--'}
      <hr>
   物流单号：${(demandExt.expressCode)!'--'} 
   <hr>
   物流相关图片：  <a href="javascript:void(0);" id="viewExpressPics" class="layui-btn layui-btn-mini" >点击查看</a>        
  </div>
</fieldset>

   </div>
    <div class="layui-tab-item">
    
    <fieldset class="layui-elem-field">
  <legend>付款信息</legend>
  <div class="layui-field-box">
  付款截止：
    <#if demandExt.deliveryDeadLine??>
         ${(demandExt.paymentTime)?string('yyyy-MM-dd HH:mm')}
    <#else>
         ---
    </#if>
  <hr>
       付款：  <a href="javascript:void(0);" id="viewPaymentPics" class="layui-btn layui-btn-mini" >点击查看</a>
  </div>
</fieldset>


   </div>
    <div class="layui-tab-item">
     <table class="layui-table" lay-skin="line">
  <colgroup>
    <col width="150">
    <col width="200">
    <col width="200">
    <col>
  </colgroup>
  <thead>
    <tr>
      <th>操作者</th>
      <th>操作时间</th>
      <th>操作日志</th>
      <th>需求状态</th>
    </tr> 
  </thead>
  <tbody>
  <#list logList as log>
    <tr>
      <td>
     <#if log.user??>
       ${log.user.nick}
     <#else>
             管理员
     </#if>
    </td>
      <td>${log.createTime?string('yyyy-MM-dd HH:mm:ss')}</td>
      <td>${log.logInfo}</td>
      <td>
      <#if log.demandStatus=='0'>
								    待响应
								 </#if>
								 <#if log.demandStatus=='1'>
								 需求确认中
								 </#if>
								<#if log.demandStatus=='2'>
								    订单确认中
								 </#if>
								 <#if log.demandStatus=='3'>
								 订单进行中
								 </#if><#if log.demandStatus=='4'>
								    正在配货
								 </#if>
								 <#if log.demandStatus=='5'>
								 等待付款
								 </#if>
                                 <#if log.demandStatus=='6'>
								 已完成
								 </#if>
                                 <#if log.demandStatus=='7'>
								需求终止中
								 </#if>
                                 <#if log.demandStatus=='8'>
								 已终止
								 </#if>   

      </td>
    </tr>
 </#list>   
  </tbody>
</table>   
   
    </div>
  </div>
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="javascript:history.go(-1);" class="layui-btn">返回</a>
</div>
 <script type="text/javascript" src="../plugins/layui/layui.js"></script>
<script>
//需求商品图片
var json= new Object();
json.title="需求图片"
json.id = "1"
json.start=0
var arrA = new Array();
var picStr = ${(demand.demandPics)!'[]'};
for(var i=0;i<picStr.length;i++){
    var obj = new Object();
    obj.alt='';
    obj.pid=i;
    obj.src=picStr[i];
    obj.thumb='';
    arrA[i]=obj;
}
json.data = arrA;
//物流图片
var expressPic = new Object();
expressPic.title="物流图片"
expressPic.id = "2"
expressPic.start=0
var arrB = new Array();
var expressStr = ${(demandExt.expressPics)!'[]'};
for(var i=0;i<expressStr.length;i++){
    var obj = new Object();
    obj.alt='';
    obj.pid=i;
    obj.src=expressStr[i];
    obj.thumb='';
    arrB[i]=obj;
}
expressPic.data = arrB;

//付款凭证
var paymentPic = new Object();
paymentPic.title="付款图片"
paymentPic.id = "3"
paymentPic.start=0
var arrC = new Array();
var paymentStr = ${(demandExt.paymentPics)!'[]'};
for(var i=0;i<paymentStr.length;i++){
    var obj = new Object();
    obj.alt='';
    obj.pid=i;
    obj.src=paymentStr[i];
    obj.thumb='';
    arrC[i]=obj;
}
paymentPic.data = arrC;
//合同图片
var contractPic = new Object();
contractPic.title="付款图片"
contractPic.id = "3"
contractPic.start=0
var arrD = new Array();
var contractStr = ${(demandExt.contractPics)!'[]'};
for(var i=0;i<contractStr.length;i++){
    var obj = new Object();
    obj.alt='';
    obj.pid=i;
    obj.src=contractStr[i];
    obj.thumb='';
    arrD[i]=obj;
}
contractPic.data = arrD;


layui.use(['element','layer'], function(){
  var element = layui.element();
  var $ = layui.jquery, layer = layui.layer;
  $('#viewPics').on('click',function(){
     layer.photos({
         photos: json 
         ,anim: 5 
  });
  });
  
    $('#viewExpressPics').on('click',function(){
     layer.photos({
         photos: expressPic 
         ,anim: 5 
  });
  });
 
  $('#viewPaymentPics').on('click',function(){
     layer.photos({
         photos: paymentPic 
         ,anim: 5 
  });
  });
   $('#viewContractPics').on('click',function(){
     layer.photos({
         photos: contractPic 
         ,anim: 5 
  });
  });
});
</script>
	</body>
	
	</html>