<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- <script type="text/javascript">
	alert("${pageContext.request.contextPath }/login" );
</script> -->
 <form action="${pageContext.request.contextPath }/login" method="get">
      hbase.root.dir:<input type="text" name="rootdir"/><br>
      zookeeper:<input type="text" name="zookeeper"/><br>
      <input type="submit" value="登录" />
    </form>
</body>
</html>