package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;

import com.javaex.utill.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/bo")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");

		if ("read".equals(actionName)) {
			/* 주소가 왔고 요청내 파라미터에 있는 번호를 불러와서 형변환하고 담아줘 */
			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao dao = new BoardDao();
			BoardVo vo = dao.getBoard(no);
			System.out.println(vo.toString());
			request.setAttribute("bovo", vo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("writeform".equals(actionName)) {

			WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp");

		} else if ("write".equals(actionName)) {

			String title = request.getParameter("title");
			String content = request.getParameter("content");

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			int no = authUser.getNo();

			BoardVo vo = new BoardVo(title, content, no);
			System.out.println(vo.toString());
			BoardDao dao = new BoardDao();
			dao.insert(vo);

			// forward쓰면 뿌려주는 코드 또 써야되니깐 리다이랙트쓰는게 편하지 사용자입장에서 주소침.
			WebUtil.redirect(request, response, "/mysite/bo");

		} else if ("delete".equals(actionName)) {

			String no = request.getParameter("no");
			System.out.println(no);

			BoardVo vo = new BoardVo(Integer.parseInt(no));

			BoardDao dao = new BoardDao();
			dao.delete(vo);
			System.out.println("삭제");

			WebUtil.redirect(request, response, "/mysite/bo");

		} else if ("modifyform".equals(actionName)) {
			// 읽기
			int no = Integer.parseInt(request.getParameter("no"));

			BoardDao dao = new BoardDao();
			BoardVo vo = dao.getBoard(no);
			System.out.println(vo.toString());
			request.setAttribute("bovo", vo);

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");

		} else if ("modify".equals(actionName)) {

			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println();

			BoardVo vo = new BoardVo(no, title, content);
			System.out.println(vo.toString());
			BoardDao dao = new BoardDao();
			dao.update(vo);
			String url = "/mysite/bo?a=read&no=" + no;
			WebUtil.redirect(request, response, url);

		} else {
			System.out.println("리스트");

			BoardDao dao = new BoardDao();
			List<BoardVo> list = dao.getList();

			System.out.println(list.toString());
			request.setAttribute("list", list);

			// 정보담아서 리스트 뿌려주고 우리입장에서 주소침.
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
