package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.common.AESEncryptor;
import com.web.member.model.vo.Member;
import com.web.member.service.MemberService;

@WebServlet(name="memberView", urlPatterns="/member/memberView.do")
public class MemberViewSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    public MemberViewSerlvet() {
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원정보를 보여주는 화면으로 이동
		// (회원정보 : 현재 DB, 세션에 저장되어있음) -> 이것으로 처리 가능함
		String userId=request.getParameter("userId");
		
		
		
		System.out.println(userId);
		
		// 1. DB에 로그인한 회원의 정보를 가져와서 화면에 전달.
		Member m = new MemberService().selectByUserId(userId);
		
		try {
			m.setEmail(AESEncryptor.decryptData(m.getEmail())); // 이메일 복호화해서 화면에 저장
			
		}catch(Exception e) {
			
		}
		
		try {
			m.setPhone(AESEncryptor.decryptData(m.getPhone())); // 핸드폰번호 복호화해서 화면에 저장
			
		}catch(Exception e) {
			
		}
		
		
		
		request.setAttribute("infoMember",m);
		// 2. 화면에서 전달받는 회원데이터 출력
		
		request.getRequestDispatcher("/views/member/memberView.jsp")
		.forward(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
