package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.member.model.vo.Member;
import com.web.member.service.MemberService;

/**
 * Servlet implementation class UpdatePasswordEndServlet
 */
@WebServlet("/updatePasswordEnd")
public class UpdatePasswordEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdatePasswordEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId=request.getParameter("userId");
		String oriPw=request.getParameter("password");
		String newPw=request.getParameter("password_new");
		
		Member m=new MemberService().selectByUserIdAndPw(userId,oriPw);
		String msg="",loc="/member/updatePassword.do?userId="+userId; // 기본경로를 loc부분으로 해놓음 
		
		if(m==null) {
			//비밀번호가 일치하지 않음
			msg="비밀번호가 일치하지 않습니다.";
		}else {
			//비밀번호가 일치함
			int result = new MemberService().updatePassword(userId,newPw);
			if(result>0) {
				msg="비밀번호 수정완료";
				loc="/";
				request.setAttribute("script", "opener.location.replace('"+request.getContextPath()+"/logout.do'); close();");
				// opener -> 부모 사이트(팝업사이트 나오기전)에서 로그아웃서블릿으로 넘겨서 로그아웃하고 그 후에 창 닫기 
			}else {
				msg="비밀번호 수정실패";
			}
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("views/common/msg.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
