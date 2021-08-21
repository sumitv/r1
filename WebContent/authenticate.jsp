<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.su.AdminDAO"%>
<jsp:useBean id="obj" class="com.su.Admin"></jsp:useBean>

<%
	obj.setUsername(request.getParameter("username"));
	obj.setPassword(request.getParameter("password"));

	session.setAttribute("user", request.getParameter("username"));

	if (session.getAttribute("firstTime").equals("true")) {
		if (AdminDAO.authenticate(obj) != null) {
			out.println("<center>Registration successfull</center>");
			out.println("<br><a href=login.jsp>Please click here to login</a></center>");
			session.setAttribute("validsession", "false");
		}
	} else {
		if (AdminDAO.authenticate(obj) != null) {
			session.setAttribute("validsession", "true");
			response.sendRedirect("fileManager.jsp");
		} else {
			out.println("<center>Invalid username or password</center>");
			out.println("<br><a href=login.jsp>Please click here to login again</a></center>");
			session.setAttribute("validsession", "false");
		}
	}
%>