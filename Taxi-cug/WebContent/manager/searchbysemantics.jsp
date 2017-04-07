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
<h1>语义查询</h1>
 <form action="${pageContext.request.contextPath }/login/searchByMantics" method="get" target="right">
 	<p>	semantic:
 		<select id="semantic" >
 			<option value="" selected> choose a semantic</option>
 			<option value="roadCongestion">Road congestion situation</option>
 			<option value="accidents">car accidents</option>
 			<option value="taxiDistribution">Taxi passenger distribution</option>
 		</select>
 	</p>
 		startTime:<input type="text" name="startTime"/><br>
 		endTime  :<input type="text" name="endTime"/><br>
 <!-- 	<input type="checkbox" style="position: absolute;left: 110px;top: 75px;"name="startTime"/><br> -->
 	 <input type="submit" value="查询" /><br>
      <%-- Result:<input type="text" style="width:1000px; height:500px;position: absolute;left: 60px;top: 180px;"  name="outputResult" value='<%request.getAttribute("result_output"); %>'/><br>   --%> 
    </form>
</body>
</html>

<!-- style="position: absolute;left: 110px;top: 125px;"
style="position: absolute;left: 110px;top: 155px;" 
style="position: absolute;left: 295px;top: 185px;"  -->