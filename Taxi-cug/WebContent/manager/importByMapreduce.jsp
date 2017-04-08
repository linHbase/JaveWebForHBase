<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">  
       h1 {font-size:15px;}
    </style>  
</head>
<body>
<h1>MR导入数据</h1>
<!-- <script type="text/javascript">
	alert("/biye}/login/Import" );
</script> -->
 <form  action="${pageContext.request.contextPath }/login/Import" method="get" enctype="multipart/form-data" >
 	  
      <!-- <input type="file"  name="inputPath"/><br> -->
      	inputPath:<br>
      	<input type="text"  name="inputPath"/><br>
      tableName :<br>
      <input type="text"  name="tableName"/><br>
      outputPath :<br>
      <input type="text" name="outputPath"/><br> 
       <input type="submit" value="start" />    
    </form>
</body>
</html>

<!-- style="width:200px; height:16px;position: absolute;left: 29px;top: 105px;"
style="width:200px; height:16px; position: absolute;left: 29px;top: 150px;"
style="width:200px; height:16px; position: absolute;left: 29px;top: 195px;" 
style="position: absolute;left: 180px;top: 235px;" -->