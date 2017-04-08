<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>		 
<div class="dtree">
			<!-- <p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p> -->
			<script type="text/javascript" src="../js/dtree.js"></script>
			<script type="text/javascript" >
					d = new dTree('d');
					d.add(0,-1,'Operation HBase');
					d.add(1,0,"Import data");
					d.add(2,1,'Import by Mapreduce','importByMapreduce.jsp',",", 'leftdown');
					d.add(3,1,'Import by Single put','importBySinglePut.jsp',",", 'leftdown');
					d.add(4,0,'search Data');
					d.add(5,4,'searchbyTime','searchByTime.jsp',",",'leftdown');
					d.add(6,4,'searchbyTimeAndRange','searchByTimeAndRange.jsp',",",'leftdown');
					d.add(7,4,'searchbySemantics','searchbysemantics.jsp',",",'leftdown');
					document.write(d);
					 function show( )
			         {
			             alert(d) ;
			         }
			</script>
</div>
    <!-- <input type="button" value="显示html" onclick="show()"> -->		 
</body>
</html>