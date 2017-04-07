<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
 <h1>this is a page setting operation conditions and show query result</h1>
</body>
</html>

<%-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
     <h1>output result</h1>
<%
	List<String> ret =(List<String>)request.getAttribute("result_output");
if(ret!=null){
	for(int i=0; i< ret.size(); i++){
		String line=ret.get(i)+"\n";
		%>
<table>
	<tr>
	<td>
		<%=line%>
	</td>
	</tr>
</table>
  <% }
}else{
%>
		DB没数据！！
<%
}%>
 
 <%
 	Result:<textarea rows="10" cols="50  name="outputResult" value=<%
 			List<String> ret =(List<String>)request.getAttribute("result_output");
			for(int i=0; i< ret.size(); i++){
				out.println(ret.get(i)+"\n");
			}
 	%>></textarea><br>
	 	<c:forEach var="x"  items= <%request.getAttribute("result_output");%>> 
        <tr> 
          <td id="title" colspan="2">标题：${x.n_title }</td> 
        </tr> 
        <tr> 
          <td colspan="2">作者：${x.n_user }td> 
        </tr> 
</c:forEach> 
</body>
</html> --%>