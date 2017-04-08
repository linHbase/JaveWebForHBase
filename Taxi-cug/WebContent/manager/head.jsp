<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
    <title>后台页头</title>
  </head>  
  <body style="text-align:center;">
 
  <div style="float:right;">
    	<c:if test="${admin==null }">
    	<form style="text-align:right;" action="${pageContext.request.contextPath }/login" method="post">
      	hbase.root.dir:<input type="text" name="rootdir" style="width:160px;"/><br>
      			zookeeper:<input type="text" name="zookeeper" style="width:160px;"/><br>
      <input type="submit" value="登录" />
   	 </form>
  </c:if>
	   
	<c:if test="${admin!=null }">
	    	欢迎您：${"lin" } <a href="${pageContext.request.contextPath }/loginOut">注销</a>
	    </c:if>
 </div>
  
    <h1>Spatial Temporal  Manager System</h1>
     	
    <!-- <div class="dtree">
			<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
			<script type="text/javascript" src="../js/dtree.js"></script>
			<script type="text/javascript" >
					d = new dTree('d');
					d.add(0,-1,'My example tree');
					d.add(1,0,'Node 1','example01.html');
					d.add(2,0,'Node 2','example01.html');
					d.add(3,1,'Node 1.1','example01.html');
					d.add(4,0,'Node 3','example01.html');
					d.add(5,3,'Node 1.1.1','example01.html');
					d.add(6,5,'Node 1.1.1.1','example01.html');
					d.add(7,0,'Node 4','example01.html');
					d.add(8,1,'Node 1.2','example01.html');
					d.add(9,0,'My Pictures','example01.html','Pictures I ve taken over the years','','','img/imgfolder.gif');
					d.add(10,9,'The trip to Iceland','example01.html','Pictures of Gullfoss and Geysir');
					d.add(11,9,'Mom is birthday','example01.html');
					d.add(12,0,'Recycle Bin','example01.html','','','img/trash.gif');
					document.write(d);
					 function show( )
			         {
			             alert(d) ;
			         }
			</script>
</div>
    <input type="button" value="显示html" onclick="show()"> -->
  </body>

</html>