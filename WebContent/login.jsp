<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.su.AdminDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome</title>

</head>
<body>

	<%
		String heading = "";
		String placeholderU = "";
		String placeholderP = "";

		if (AdminDAO.firstTime() == true) {
			heading = "SIGN UP";
			placeholderU = "\"Desired Username\"";
			placeholderP = "\"Desired Password\"";
			session.setAttribute("firstTime", "true");
		} else {
			heading = "SIGN IN";
			placeholderU = "Username";
			placeholderP = "Password";
			session.setAttribute("firstTime", "false");
		}
	%>

	<div align="center">
		<header>
			<%
				out.println(heading);
			%>
		</header>
	</div>
	<div align="center">
		<form action="authenticate.jsp" method="post">
			<table>
				<tr>
					<td><input type="text" name="username" size="30"
						placeholder=<%out.println(placeholderU);%>></td>
				</tr>

				<tr>
					<td><input type="password" name="password" size="30"
						placeholder=<%out.println(placeholderP);%>></td>
				</tr>

				<tr>
					<td align="center"><input type="submit" name="submit"
						value="Submit"></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>