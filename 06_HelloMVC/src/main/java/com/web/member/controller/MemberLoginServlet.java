package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.member.model.vo.Member;
import com.web.member.service.MemberService;



@WebServlet(name="login",urlPatterns="/login.do")
public class MemberLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MemberLoginServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 클라이언트가 보낸 데이터를 가져온다
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		
		String saveId=request.getParameter("saveId");
		System.out.println(saveId);
		
		// checkbox에 check가 되면 on
		// checkbox에 check가 안되면 null
		if(saveId!=null) {  // 저장된값이 널이 아니면
			Cookie c = new Cookie("saveId",userId); // 쿠키생성
			c.setMaxAge(60*60*24*7); 
			response.addCookie(c);  // 쿠키만들어줌
		}else {
			Cookie c = new Cookie("saveId","");
			c.setMaxAge(0); // 쿠키 삭제
			response.addCookie(c);
		}
		
		
		//2. DB에 접속해서 id와 password가 일치하는 회원이 있는지 확인
		Member loginMember = new MemberService()
				.selectByUserIdAndPw(userId, password);
		
		System.out.println(loginMember);
		

		// loginMember null을 기준으로 로그인처리 여부를 결정할 수 있음
		if(loginMember!=null) {
			//로그인성공 -> 인증받음  (세션을 받아야함)
			HttpSession session=request.getSession();
			session.setAttribute("loginMember", loginMember);
			// 세션에 저장하면 sendRedirect해도 데이터가 날라가지 않음
			
			response.sendRedirect(request.getContextPath());
			
		}else {
			// 로그인실패 -> 인증못받음
			// 실패 메세지 출력
			request.setAttribute("msg", "아이디,패스워드가 일치하지 않습니다");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp")
			.forward(request, response);
		}
		
		//request.getRequestDispatcher("/views/searchmember.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
