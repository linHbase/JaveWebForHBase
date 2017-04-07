<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page  import="cug.HbaseManager" %>
<%@ page  import="java.util.*" %>
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
<h1>时空范围查询</h1>
 <form action="${pageContext.request.contextPath }/login/searchByTimeAndRange" method="get">
 	startTime:<input type="text" name="startTime"/><br>
 	endTime  :<input type="text" name="endTime"/><br>
 	queryRange:<input type="text" name="queryRange"/><br>
 	 <input type="submit" value="查询"/><br>
      <%-- Result:<input type="text" style="width:1000px; height:500px;position: absolute;left: 60px;top: 180px;"  name="outputResult" value='<%request.getAttribute("result_output"); %>'/><br>   --%> 
    </form>
</body>
</html>
<!-- style="position: absolute;left: 110px;top: 75px;"
style="position: absolute;left: 110px;top: 105px;"
style="position: absolute;left: 110px;top: 135px;" 
position: absolute;left: 295px;top: 175px;"  -->