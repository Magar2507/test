<%@page import="in.co.sunrays.project4.util.HTMLUtility"%>
<%@page import="in.co.sunrays.project4.model.StudentModel"%>
<%@page import="in.co.sunrays.project4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="in.co.sunrays.project4.bean.StudentBean"%>
<%@page import="in.co.sunrays.project4.controller.StudentListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Student List</title>

<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js""></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

</head>
<body>
	<jsp:useBean id="bean" class="in.co.sunrays.project4.bean.StudentBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
		<%@include file="Header.jsp"%>
		<%
			List slist = (List) request.getAttribute("name");
		%>


		<center>

			<div align="center">
				<h1>Student List</h1>
				<h1>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h1>
			</div>
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<StudentBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<table width="100%" align="center">
				<tr>
					<td align="center"><label>Name :</label> <%=HTMLUtility.getList("firstName", String.valueOf(bean.getId()), slist)%>
						<label>Email_Id :</label> <input type="text" name="email"
						placeholder="Enter Email_id" Size="25"
						value="<%=ServletUtility.getParameter("email", request)%>">
						&emsp; <input type="submit" name="operation"
						value="<%=StudentListCtl.OP_SEARCH%>"> <input
						type="submit" name="operation"
						value="<%=StudentListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr>
					<th><input type="checkbox" id="select_all"></>Select
						All</th>

					<th>S.NO</th>
					<th>College</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Date Of Birth</th>
					<th>Mobile No</th>
					<th>Email_Id</th>
					<th>Edit</th>
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
				%>



				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>
					<td><%=index++%></td>
					<td><%=bean.getCollegeName()%></td>
					<td><%=bean.getFirstName()%></td>
					<td><%=bean.getLastName()%></td>
					<td><%=bean.getDob()%></td>
					<td><%=bean.getMobileNo()%></td>
					<td><%=bean.getEmail()%></td>
					<td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
				</tr>
				<%
					}
				%>
			</table>
			<table width="100%">
				<tr>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=StudentListCtl.OP_PREVIOUS%>"> <%
 	} else {
 %>
					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEW%>"></td>

					<%
						StudentModel model = new StudentModel();
					%>
					<%
						if (list.size() < pageSize || model.nextPK() - 1 == bean.getId()) {
					%>
					<td align="right"><input type="submit" name="operation"
						disabled="disabled" value="<%=StudentListCtl.OP_NEXT%>"></td>
					<%
						} else {
					%>
					<td align="right"><input type="submit" name="operation"
						value="<%=StudentListCtl.OP_NEXT%>"></td>
					<%
						}
					%>

				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=StudentListCtl.OP_BACK%>"></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</center>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>

	<br>
	<br>
	<br>
	<br>
	<br>
	<%@include file="Footer.jsp"%>
</body>
</html>