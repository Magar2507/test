<%@page import="in.co.sunrays.project4.util.DataUtility"%>
<%@page import="in.co.sunrays.project4.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.sunrays.project4.util.ServletUtility"%>
<%@page import="in.co.sunrays.project4.bean.MarksheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Marksheet Merit List View</title>
</head>

<html>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

<body>
	<%@include file="Header.jsp"%>
	<center>
		<h1>Marksheet Merit List</h1>

		<form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
			<br>
		<table border="1" width="100%" align="center" cellpadding=6px cellspacing=".2">
				<tr>
					<th>S.No.</th>
					<th>Roll No</th>
					<th>Name</th>
					<th>Physics</th>
					<th>Chemistry</th>
					<th>Maths</th>
					<th>Percentage(%)</th>
					<!-- <th>ID</th> -->
					</tr>
				<%
					int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

					List list = ServletUtility.getList(request);
					Iterator<MarksheetBean> it = list.iterator();

					while (it.hasNext()) {

						MarksheetBean bean = it.next();
						int phy = DataUtility.getInt(DataUtility.getStringData(bean.getPhysics()));
						int chem = DataUtility.getInt(DataUtility.getStringData(bean.getChemistry()));
						int math = DataUtility.getInt(DataUtility.getStringData(bean.getMaths()));
						float PERC = (phy + chem + math)/3;
				%>
				<tr>
				<td align="center"><%=index++%></td>
					<td align="center"><%=bean.getRollNo()%></td>
					<td align="center"><%=bean.getName()%></td>
					<td align="center"><%=bean.getPhysics()%></td>
					<td align="center"><%=bean.getChemistry()%></td>
					<td align="center"><%=bean.getMaths()%></td>
					<td align="center"><%=PERC%></td>
					<%-- <td><%=bean.getID()%></td> --%>
				</tr>

				<%
					}
				%>
			</table>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</html>
