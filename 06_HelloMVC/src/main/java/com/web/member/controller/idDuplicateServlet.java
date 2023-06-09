package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.vo.Member;
import com.web.member.service.MemberService;


@WebServlet("/member/idDuplicate.do")
public class idDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public idDuplicateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트가 전송한 값(userId)이 DB(member테이블)에 있는지 확인하기
		String userId=request.getParameter("userId");
		System.out.println(userId);
		
		Member m = new MemberService().selectByUserId(userId);
		
		request.setAttribute("result", m);
		
		request.getRequestDispatcher("/views/member/idDuplicate.jsp")
		.forward(request, response);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
