<%@page import="in.co.sunrays.project4.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="in.co.sunrays.project4.controller.*"%>
<%@page import="in.co.sunrays.project4.bean.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome To ORS</title>
</head>

<body>
    <form action="<%=ORSView.WELCOME_CTL%>">
        <%@ include file="Header.jsp"%>
                <%@ include file="Footer.jsp" %>
        
                    <h1 align="Center">
                        <font size="10px" color="red">Welcome to ORS </font>
                    </h1>
                    <h1><%=ServletUtility.getSuccessMessage(request) %></h1>
        
                    <%
                  UserBean usb = (UserBean) session.getAttribute("user");
                       if (ub != null) {
                           if (ub.getRoleId() == 2) {
                    %>
        
                    <h2 align="Center">
                        <a href="<%=ORSView.GET_MARKSHEET_CTL%>">Click here to see your Marksheet </a>
                    </h2>
                     
                     <%
                          }
                       }
                     %>
                
                </form>
        
</body>
</html>