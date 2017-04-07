<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- <link rel="StyleSheet" href="js/dtree.css" type="text/css" /> -->
<script type="text/javascript" src="js/dtree.js"></script>
</head>
			
<body style="text-align:center;">
<%-- 	<td><% List<Node> node =session.getAttribute("path_name"); %><br></td> --%>
<div class="dtree" >	
			<script type="text/javascript">
			HBase = new dTree('HBase');
			HBase.add(0, -1, 'HBase');
			var node = <%=request.getAttribute("path_name")%>;
			for(var i=0;i<node.length;i++){
				HBase.add(parseInt(node[i].id), parseInt(node[i].pid), node[i].name);
			}
			document.write(HBase);
			</script>
</div>
</body>
</html>
