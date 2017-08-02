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
import com.javaex.utill.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");
		if ("joinform".equals(actionName)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");
			/*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp");
			rd.forward(request, response);*/

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

			WebUtil.forward(request, response, "/WEB-INF/views/user/joinsuccess.jsp");
			/*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp");
			rd.forward(request, response);*/

		} else if ("modify".equals(actionName)) {//vo에 g/s없어서 직접 set해줘
			String name=request.getParameter("name");//요청에서 파라미터에있는 이름꺼내와//수정폼에서 이름맞나 확인해봐
			String password=request.getParameter("password");	
			String gender=request.getParameter("gender");
			
			UserVo vo=new UserVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setGender(gender);
			
			HttpSession session = request.getSession();//이 코드를 통해 세션에 접근할수 있어 로그인한 사용자의 넘버값빼올려고
			UserVo authUser=(UserVo)session.getAttribute("authUser");//object로 다 나오게끔 형변환
			int no = authUser.getNo();
			vo.setNo(no);
			
			UserDao dao=new UserDao();//DB에 저장하려면 dao이용
			dao.update(vo);
			
			authUser.setName(name);//세션에 이름바꿔줬어
			
			WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
			/*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/main/index.jsp");//사용자한테 화면 띄워줌
			rd.forward(request, response);*/

		} else if ("modifyform".equals(actionName)) {
			// session에 있는 넘버 꺼내올꺼야
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			int no = authUser.getNo();

			UserDao dao = new UserDao();
			UserVo userVo = dao.getUser(no);
			/* System.out.println(userVo.toString()); */

			request.setAttribute("userVo", userVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");
			/*RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/user/modifyform.jsp");
			rd.forward(request, response);*/

		} else if ("loginform".equals(actionName)) {
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

				WebUtil.redirect(request, response, "/mysite/main");
				/*response.sendRedirect("/mysite/main");// main띄워
*/				return;// 여기서 끝 이거안써주면 아래남아있는 코드들 실행될수도 있어서
			}
		} else if ("logout".equals(actionName)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");// 세션제거
			session.invalidate();
			response.sendRedirect("/mysite/main");
		} else {// 이상한 주소치면 메인으로 보내줄께
			
			WebUtil.redirect(request, response, ".mysite/main");
			/*response.sendRedirect("/mysite/main");*/
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
