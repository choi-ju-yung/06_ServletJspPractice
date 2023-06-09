package com.web.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.notice.model.service.NoticeService;
import com.web.notice.model.vo.Notice;


@WebServlet("/notice/noticeView.do")
public class NoticeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트가 보낸 공지사항을 DB에서 가져와 전달해주는 기능
		int no=Integer.parseInt(request.getParameter("no"));
		Notice n = new NoticeService().selectNoticeByNo(no);
		request.setAttribute("notice", n);
		request.getRequestDispatcher("/views/notice/noticeView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
