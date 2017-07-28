package com.javaex.controller;
 
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");
		if ("joinform".equals(actionName)) {

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp");
			rd.forward(request, response);

		} else if ("join".equals(actionName)) {
			// 회원정보저장->성공페이지
			// joinform에서 모은 정보 넘겨줘야지
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");

			// vo하나 만들어서 쟤네 다 넣어줄꺼야
			UserVo vo = new UserVo(name, email, password, gender);
			// dao에 넣어주고 
			UserDao dao = new UserDao();
			dao.insert(vo);

			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp");
			rd.forward(request, response);

		}else if("modifyform".equals(actionName)) {
			//session에 있는 넘버 꺼내올꺼야
			HttpSession session=request.getSession();
			UserVo authUser=(UserVo)session.getAttribute("authUser");
			int no=authUser.getNo();
			
			UserDao dao=new UserDao();
			UserVo userVo= dao.getUser(no);
			/*System.out.println(userVo.toString());*/
			
			request.setAttribute("userVo", userVo);
			RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/user/modifyform.jsp");
			rd.forward(request, response);
			
		}else if ("loginform".equals(actionName)) {
			// 로그인폼
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp");
			rd.forward(request, response);

		} else if ("login".equals(actionName)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			UserDao dao = new UserDao();
			UserVo vo = dao.getUser(email, password);

			if (vo == null) {
				System.out.println("실패");

				response.sendRedirect("/mysite/user?a=loginform&result=fail");// 꼬리표 로그인폼에서 패일이라고 받겠지 user로다시가
			} else {
				System.out.println("성공");
				// 메모리공간
				HttpSession session = request.getSession(true);
				// 세션에 담아놔 별명이랑 no와 비번이 들어있는 vo넘겨줘 아래데이터들어있으면 로그인 성공한걸로 치고 반복해서 안물어봄
				session.setAttribute("authUser", vo);

				response.sendRedirect("/mysite/main");// main띄워
				return;// 여기서 끝 이거안써주면 아래남아있는 코드들 실행될수도 있어서
			}
		} else if ("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");//세션제거
			session.invalidate();
			response.sendRedirect("/mysite/main");
		} else {// 이상한 주소치면 메인으로 보내줄께
			response.sendRedirect("/mysite/main");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
