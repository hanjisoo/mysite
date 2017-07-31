
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.javaex.vo.GuestbookVo"%>
<%@ page import="java.util.List"%>

<%
	List<GuestbookVo> list = (List<GuestbookVo>) request.getAttribute("list");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">
<title>방명록</title>
</head>
<body>

	<div id="container">

		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include>


		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include>


	<div id="wrapper">
			<div id="content">
				<div id="user">
					<form action="/mysite/gb" method="post">
						<table border=1 width=500>
							<tr>
								<td>이름</td>
								<td><input type="text" name="name"></td>
								<td>비밀번호</td>
								<td><input type="password" name="pass"></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" cols=60 rows=5></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
							</tr>
							<input type="text" name="a" value="add">
							
						</table>
					</form>
					<br />

					<%
						for (GuestbookVo vo : list) {
							//insert  리스트에서 하나씩 돌아 dao에 getlist씀
					%>
					<table width="510" border="1">
						<tr>
							<td><%=vo.getNo()%></td>
							<td><%=vo.getName()%></td>
							<td><%=vo.getRegDate()%></td>
							<td><a href="gb?a=deleteform&no=<%=vo.getNo()%>">삭제</a></td>
						</tr>
						
						<tr>
							<td colspan=4>안녕하세요~<br /><%=vo.getContent().replace("\n", "<br>")%></td>
							
						</tr>
					</table>
					<br />
					<%
						}
					%>

				</div>
				<!-- /user -->
			</div>
			<!-- /content -->
		</div>
		<!-- /wrapper -->
	


		<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include>


	</div>
	<!-- /container -->


</body>
</html>