package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.utill.WebUtil;
import com.javaex.vo.GuestbookVo;


@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//한글 안깨지게
		
		String actionName=request.getParameter("a");//요청할때 쓸껴
		
		if("add".equals(actionName)) {
			System.out.println("추가");
			
			String name=request.getParameter("name");
			String password=request.getParameter("pass");
			String content=request.getParameter("content");
			
			GuestbookVo vo=new GuestbookVo(name,  password, content);
			
			GuestbookDao bookdao =new GuestbookDao();
			bookdao.insert(vo);
			
			
			WebUtil.redirect(request, response, "/mysite/gb");
			/*response.sendRedirect("/mysite/gb");*/
			
			
		}else if("delete".equals(actionName)) {
			System.out.println("삭제");
			
			String password=request.getParameter("password");
			String no=request.getParameter("no");
			
			GuestbookVo vo=new GuestbookVo(Integer.valueOf(no), password);
			
			GuestbookDao bookdao =new GuestbookDao();
			bookdao.delete(vo);
			
			WebUtil.redirect(request, response, "/mysite/gb");
			/*response.sendRedirect("/mysite/gb");*/
			
			
		}else if("deleteform".equals(actionName)) {
			System.out.println("삭제형식");
			
			WebUtil.forward(request, response, "/WEB-INF/views/book/deleteform.jsp");
			/*RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/book/deleteform.jsp");
			rd.forward(request, response);*/
			
			
			
		}else {
			System.out.println("리스트");
			
			GuestbookDao dao=new GuestbookDao();
			List<GuestbookVo> list=dao.getList();
			/*vo형태로 데이터받아옴*/
			System.out.println(list.toString());
			request.setAttribute("list", list);
							
			WebUtil.forward(request, response, "/WEB-INF/views/book/list.jsp");
			/*RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/views/book/list.jsp");
			rd.forward(request, response);*/
			
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
