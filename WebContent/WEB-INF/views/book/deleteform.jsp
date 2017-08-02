<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
	String no = request.getParameter("no");
%>
<!-- 번호를 불러주고 그걸 담아주고 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">
<title>방명록</title>
</head>
<body>

	<div id="container">

		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include>


		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include>

		
<div id="wrapper">
			<div id="content">
				<div id="user">

					<form method="post" action="/mysite/gb">
						<input type='text' name="no" value="${param.no }">
						<!--hidden으로 숨겨줌 숫자보여주고 우리눈에 -->
						<input type="text" name="a" value="delete">
						<!-- 리스트에 리퉤스트에 담아진 a의 delete로 가 -->
						<table>
							<tr>
								<td>비밀번호</td>
								<td><input type="password" name="password"></td>
								<td><input type="submit" value="등록"></td>
								<td><a href="/mysite/main">메인으로 돌아가기</a></td>
							</tr>
						</table>
					</form>

				</div>	<!-- /user -->
			</div>	<!-- /content -->
		</div>	<!-- /wrapper -->

		<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include>


	</div>
	<!-- /container -->
</body>
</html>