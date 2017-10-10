<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title>数据导入</title>
		<link rel="stylesheet" href="../plugins/layui/css/layui.css" media="all" />

	</head>

	<body>
<fieldset class="layui-elem-field">
  <legend>导入数据</legend>
  <div class="layui-field-box">

  <input type="file" name="excelfile" lay-type="file" class="layui-upload-file" id="file" > 
  </div>
</fieldset>
<script type="text/javascript" src="../plugins/layui/layui.js"></script>
<script>
layui.use(['layer','upload'], function(){

  layer = parent.layer === undefined ? layui.layer : parent.layer;
			layui.upload({
			
             url: '/admin/sku_imort',
             before: function(input){
               layer.msg('文件上传中...'); 
                  },
            elem: '#file' //指定原始元素，默认直接查找class="layui-upload-file"
             ,ext: 'xls|xlsx'
            ,method: 'post' //上传接口的http类型
            ,success: function(res){
             
               layer.msg('本次导入：'+res.count+'条记录');
               window.location='/admin/sku_import_list' 
          }
         });		
         
       
  
});
</script>
</body>

</html>