package com.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.member.model.vo.Member;


@WebFilter(urlPatterns = {"/admin/*","/notice/insertForm.do"})
// urlPatterns 는 한개만 적용할때는 생략해도됨
public class AdminCheckFilter extends HttpFilter implements Filter {
       

    public AdminCheckFilter() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=((HttpServletRequest)request);
		HttpSession session=((HttpServletRequest)request).getSession();
		Member loginMember=(Member)session.getAttribute("loginMember");
		
		if(loginMember==null || !loginMember.getUserId().equals("admin")) {
			req.setAttribute("msg", "잘못된 접근입니다, 관리자에게 문의하세요!");
			String prevPage=req.getHeader("Referer");
			System.out.println(prevPage);
			req.setAttribute("loc", "/");
			req.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
