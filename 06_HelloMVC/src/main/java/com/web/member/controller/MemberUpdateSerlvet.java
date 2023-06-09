package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.member.model.vo.Member;
import com.web.member.service.MemberService;


@WebServlet("/member/updateEndMember.do")
public class MemberUpdateSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberUpdateSerlvet() {
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원정보를 수정하는 서비스
		// 1. 클라이언트가 보낸 데이터를 받아온다
		// readOnly로 전송했기때문에 값을 받아올수있다.
		Member m=Member.builder()
				.userId(request.getParameter("userId"))
				.userName(request.getParameter("userName"))
				.age(Integer.parseInt(request.getParameter("age")))
				.gender(request.getParameter("gender").charAt(0))
				.email(request.getParameter("email"))
				.phone(request.getParameter("phone"))
				.address(request.getParameter("address"))
				.hobby(request.getParameterValues("hobby"))
				.build();
				
		
		// 2. 회원정보를 (DB에 있는 데이터를)수정해줌
		int result=new MemberService().updateMember(m);
		
		// 3. 결과를 전송하기
		String msg="",loc="";
		if(result>0) {
			msg="회원정보가 수정되었습니다.";
			loc="/";
			HttpSession session=request.getSession();
			session.setAttribute("loginMember", new MemberService().selectByUserId(m.getUserId()));
		}else {
			msg="회원정보 수정실패했습니다.다시 시도하세요";
			loc="/member/memberView.do?userId="+m.getUserId();
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
