<%@page import="in.co.sunrays.project4.bean.CourseBean"%>
<%@page import="java.util.List"%>
<%@page import="in.co.sunrays.project4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.project4.controller.SubjectCtl"%>
<%@page import="in.co.sunrays.project4.util.DataUtility"%>
<%@page import="in.co.sunrays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	
<html>
<head>
<link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16"/>
<title>Subject Registration Page</title>

</head>
<body>
<jsp:useBean id="bean" class="in.co.sunrays.project4.bean.SubjectBean" scope="request"></jsp:useBean>
<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

<%@include file ="Header.jsp"%>
	

	<%
		List<CourseBean> courseId =(List<CourseBean>) request.getAttribute("CourseList");
	%>
<center>
	<h1>
		<% if(bean != null && bean.getId() > 0 ) {%>
			<tr><th>Update Subject</th></tr>
		<%}else{ %>
			<tr><th>Add Subject</th></tr>
		<% }%>
	</h1>
<div>	
	<h1>
	<font style="color: green"><%=ServletUtility.getSuccessMessage(request) %>
	</font>
	</h1>
	
	<h1>
	<font style="color: red"><%=ServletUtility.getErrorMessage(request)%>
	</font>
	</h1>
</div>
	
	<input type="hidden" name="id" value="<%=bean.getId()%>">
 	<input type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
 	<input type="hidden" name="modifiedby" value="<%=bean.getModifiedBy()%>">
 	<input type="hidden" name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
 	<input type="hidden" name="modifieddatetime" value="<%=bean.getModifiedDatetime()%>">
 	
	<table>
	
	<tr>
	<th align="left">Course Name<span style="color: red">*</span> </th>
	<td><%=HTMLUtility.getList("coursename", String.valueOf(bean.getCourseId()), courseId) %>
	</td><td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("coursename",request) %>
	</font></td></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	
	<tr><th align="left">Subject Name <span style="color: red">*</span>
	<td><input type="text" name="name" placeholder="Enter Subject Name" size="25" value="<%=DataUtility.getStringData(bean.getName())%>">
	</td><td style="position: fixed"><font  color="red"><%=ServletUtility.getErrorMessage("name",request) %>
	</font>	</td></th></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	<tr><th align="left">Description <span style="color: red">*</span>
	<td><input type="text" name="description" placeholder="Enter Description" size="25" value="<%=DataUtility.getStringData(bean.getDescription())%>">
	</td><td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("description",request) %>
	</font></td></th></tr>
	
	 <tr><th style="padding: 3px"></th></tr>    
	 
	<tr><th></th>
	<%
		if(bean.getId() > 0){	%>
	<td>
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_UPDATE %>">
	
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL %>">	
	</td>
	<%}else{ %>
	
	<td>
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_SAVE %>">
	
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_RESET %>">	
	</td>
	<%} %>
	
	</tr>
	</table>
</center>

</form>
<%@include file ="Footer.jsp"%>
</body>
</html>