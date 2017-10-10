<!DOCTYPE html>

<html>
	<head>
		<meta charset="utf-8">
		<title>统计分析</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="format-detection" content="telephone=no">
		<link rel="stylesheet" href="plugins/layui/css/layui.css" media="all" />
		<link rel="stylesheet" href="../css/global.css" media="all">
		<link rel="stylesheet" href="../plugins/font-awesome/css/font-awesome.min.css">
	</head>

	<body>
<div class="layui-tab">
  <ul class="layui-tab-title">
    <li class="layui-this">用户分析</li>
    <li>需求分析</li>
   
  </ul>
  <div class="layui-tab-content">
    <div class="layui-tab-item layui-show">
   
   <fieldset class="layui-elem-field">
      <legend>昨日指标</legend>
      <div class="layui-field-box">
      <table class="layui-table" lay-skin="line">
         <colgroup>
    <col width="150">
    <col width="200">
    <col>
  </colgroup>
  <thead>
    <tr>
      <th>指标</th>
      <th>人数</th>
    </tr> 
  </thead>
  <tbody>
    <tr>
      <td>总人数</td>
      <td id="totalCount">0</td>
    </tr>
    <tr>
      <td>昨日新增</td>
      <td id="lastDayAdd">0</td>
    </tr>
    <tr>
      <td>实名采购人员</td>
      <td id="buyerCount">0</td>
    </tr>
    <tr>
      <td>实名sku经理</td>
      <td id="skuerCount">0</td>
    </tr>
    <tr>
      <td>昨日绑定sku经理</td>
      <td id="skuBind">0</td>
    </tr>
    <tr>
      <td>昨日采购绑定</td>
      <td id="buyerBind">0</td>
    </tr>
  </tbody>
</table>    
  </div>
  
</fieldset>

<fieldset class="layui-elem-field">

            <legend>按时间段对比</legend>
      <div class="layui-field-box">
     <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始时间</label>
      <div class="layui-input-block">
        <input type="text" name="s_date" lay-verify="required" id="s_date" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
      
    </div>         
 
 <div class="layui-inline">
      <label class="layui-form-label">结束时间</label>
      <div class="layui-input-block">
        <input type="text" name="e_date" lay-verify="required" id="e_date" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
    </div> 
     <div class="layui-inline">
      <div class="layui-input-block">
         <button class="layui-btn layui-btn-primary" id="usertongji">统计</button>  
      </div>
    </div> 
    
  </div>
  <blockquote class="layui-elem-quote layui-quote-nm">
  <!--图表div-->
  <div id="container_1">
  <div> 
</blockquote>
</fieldset>
    </div>
    
<div class="layui-tab-item">
<fieldset class="layui-elem-field">
      <legend>总指标</legend>
      <div class="layui-field-box">
      <table class="layui-table" lay-skin="line">
         <colgroup>
    <col width="150">
    <col width="200">
    <col>
  </colgroup>
  <thead>
    <tr>
      <th>指标</th>
      <th>数值</th>
    </tr> 
  </thead>
  <tbody>
    <tr>
      <td>总需求</td>
      <td id="totalDemand">0</td>
    </tr>
    <tr>
      <td>平均响应时间（时）</td>
      <td>0</td>
    </tr>

  </tbody>
</table>    
  </div>
  
</fieldset>
  <fieldset class="layui-elem-field">
            <legend>按品类对比</legend>
<div class="layui-field-box">
<form class="layui-form" id="form" method="post" action="">
 <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始时间</label>
      <div class="layui-input-block">
        <input type="text" name="xq_s_date" lay-verify="required" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
    </div>         
 
 <div class="layui-inline">
      <label class="layui-form-label">结束时间</label>
      <div class="layui-input-block">
        <input type="text" name="xq_e_date" lay-verify="required" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
    </div> 
     
    <div class="layui-form-item">
    <label class="layui-form-label">统计品类</label>
    <div class="layui-input-block">
       <#list list as ca>
            <input type="checkbox" name="categoryId" value="${ca.id}" title="${ca.categoryName}" checked="">
      </#list>
    </div>
  </div>
   <div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div class="layui-input-inline">
  
       <select name="demandStatus" id="demandStatus" lay-verify="required">
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
  </div>
    <div class="layui-inline">
      <div class="layui-input-block">
         <button class="layui-btn layui-btn-primary" lay-submit lay-skin="primary" lay-filter="xqtongji">统计</button>  
      </div>
    </div> 
  </div>
</form>
 

  <blockquote class="layui-elem-quote layui-quote-nm">
  <!--需求总图表div-->
  <div id="container_2">
  <div> 
</blockquote>
</fieldset>  

<fieldset class="layui-elem-field">
            <legend>按状态对比</legend>
<div class="layui-field-box">
<form class="layui-form" id="form1" method="post" action="">
 <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始时间</label>
      <div class="layui-input-block">
        <input type="text" name="xq_s_date" lay-verify="required" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
    </div>         
 
 <div class="layui-inline">
      <label class="layui-form-label">结束时间</label>
      <div class="layui-input-block">
        <input type="text" name="xq_e_date" lay-verify="required" autocomplete="off" class="layui-input" onclick="layui.laydate({elem: this})">
      </div>
    </div> 
     
    <div class="layui-form-item">
    <label class="layui-form-label">统计状态</label>
    <div class="layui-input-block">
         <input type="checkbox" name="demandStatus" value="0" title="待响应" checked="">
          <input type="checkbox" name="demandStatus" value="1" title="需求确认中" checked="">
          <input type="checkbox" name="demandStatus" value="2" title="订单确认中" checked="">
          <input type="checkbox" name="demandStatus" value="3" title="订单进行中" checked="">
          <input type="checkbox" name="demandStatus" value="4" title="配送中" checked="">
          <input type="checkbox" name="demandStatus" value="5" title="已结款" checked="">
          <input type="checkbox" name="demandStatus" value="6" title="已完结" checked="">
          <input type="checkbox" name="demandStatus" value="7" title="已终止" checked="">
    </div>
  </div>
   <div class="layui-form-item">
    <label class="layui-form-label">品类</label>
    <div class="layui-input-inline">
  
       <select name="categoryId"  lay-verify="required">
					  <option value="">全部</option>
					  <#list list as ca>
					  <option value="${ca.id}">${ca.categoryName}</option>
					   </#list>
					  
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
  </div>
    <div class="layui-inline">
      <div class="layui-input-block">
         <button class="layui-btn layui-btn-primary" lay-submit lay-skin="primary" lay-filter="xqtongji_1">统计</button>  
      </div>
    </div> 
  </div>
</form>
 

  <blockquote class="layui-elem-quote layui-quote-nm">
  <!--需求总图表div-->
  <div id="container_3">
  <div> 
</blockquote>
</fieldset>  



    </div>
  </div>
</div>
<script type="text/javascript" src="plugins/layui/layui.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/highcharts/highcharts.js"></script>
<script type="text/javascript" src="js/highcharts/exporting.js"></script>
<script type="text/javascript">
$(function () {
 <!--用户统计-->
 Highcharts.setOptions({
	lang: {
	    printChart: '打印图表',
		contextButtonTitle:'导出图片',
		downloadJPEG: '导出jpeg',
		downloadPDF: '导出pdf',
		downloadPNG: '导出png',
		downloadSVG: '导出svg'
	}
});
 $('#usertongji').on("click",function(){
 layer.msg('加载中...');  
    $.ajax({
      url:'/admin/user_days_statistical',
      data:{"s_date":$('#s_date').val(),"e_date":$('#e_date').val()},
      type:'post',
      success:function(data) { 
          if(data.errCode=='0001'){
          layer.msg(data.resultMsg);   
            return;
          }
		 $('#container_1').highcharts({
		 credits:{
		   enabled: false
		 },
		 
        title: {
            text: '用户时间段统计',
            x: -20 //center
        },
        subtitle: {
            text: data.title,
            x: -20
        },
        xAxis: {
            categories: data.dates
        },
        yAxis: {
            allowDecimals: false,
            title: {
                text: '人（个）'
            },
            plotLines: [{
                value: 1,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            animation: true,
            valueSuffix: '个',
            followPointer: true
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '增加',
            data: data.adds
        }, {
            name: '采购人员绑定',
            data: data.buyers
        }, {
            name: 'sku经理绑定',
            data: data.skuers
        }]
    });		      
      },    
    error : function() {    
                        
     } 
    })
 });
 
 
});
</script>	
<script>
//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
layui.use(['form','element','layer','laydate'], function(){
  var element = layui.element();
  var form = layui.form();
  var $ = layui.jquery, layer = layui.layer,laydate = layui.laydate;; 
  $(document).ready(function(){
            $.ajax({
				   url:'/admin/total_statistical',
				   type:'post',
				   data:{"id":"1"},
				   success:function(data) { 
				       $('#totalCount').html(data.totalCount);
				       $('#lastDayAdd').html(data.lastDayAdd);
				       $('#buyerCount').html(data.buyerCount);
				       $('#skuerCount').html(data.skuerCount);
				       $('#buyerBind').html(data.buyerBind);
				       $('#skuBind').html(data.skuBind);
				       $('#totalDemand').html(data.demandTotal);
                     },    
                  error : function() {    
                        
                  } 
				 })
          });
          
          
          //需求统计xqtongji
 form.on('submit(xqtongji)',function(data){
 layer.msg('加载中...');  
   $.ajax({
      url:'/admin/demand_analysis',
      data:$("#form").serialize(),
      type:'post',
      success:function(data) { 
          if(data.errCode=='0001'){
          layer.msg(data.resultMsg);   
            return;
          }
          $('#container_2').highcharts({
                chart: {
            type: 'column'
        },
         credits:{
		   enabled: false
		 },
        title: {
            text: data.title
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: data.dates,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '需求数'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: data.series
         });
    }
  
      }) ; 
        return false;  
 });
 
 //需求统计 状态
    //需求统计xqtongji
 form.on('submit(xqtongji_1)',function(data){
  layer.msg('加载中...');  
   $.ajax({
      url:'/admin/demand_analysis_status',
      data:$("#form1").serialize(),
      type:'post',
      success:function(data) { 
          if(data.errCode=='0001'){
          layer.msg(data.resultMsg);   
            return;
          }
          $('#container_3').highcharts({
                chart: {
            type: 'column'
         },
         credits:{
		   enabled: false
		 },
         title: {
            text: data.title
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: data.dates,
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: '需求数'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: data.series
         });
    }
  
      }) ; 
        return false;  
 });
 
 
  
});
</script>

	</body>
	</html>