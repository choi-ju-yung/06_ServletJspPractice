package com.web.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.notice.model.service.NoticeService;
import com.web.notice.model.vo.Notice;


@WebServlet("/notice/noticeList.do")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int cPage, numPerPage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		try {
			numPerPage=Integer.parseInt(request.getParameter("numPerPage"));
		}catch(NumberFormatException e) {
			numPerPage=5;
		}
		
		
		
		
		String pageBar="";
		int totalData=new NoticeService().selectNoticeCount();
		
		int totalPage=(int)Math.ceil((double)totalData/numPerPage);
		int pageBarSize=5;
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		
		if(pageNo==1) {  // -> 1~5페이지까지 해당됨
			pageBar+="<span>[이전]</span>";
		}else {  // -> 그 이후부터는 이전페이지를 클릭 가능한 태그를 만들어서 이동할때의 페이지에 값을 넘겨줌
			pageBar+="<a href='"+request.getRequestURI()
			+"?cPage="+(pageNo-1)
			+"&numPerPage="+numPerPage+">[이전]</a>'"; // 현재페이지에서 이동
		}
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar+="<span>"+pageNo+"</span>";
			}else {
				pageBar+="<a href='"+request.getRequestURI()
				+"?cPage="+pageNo
				+"&numPerPage="+numPerPage+">"+pageNo+"</a>'";
			}
			pageNo++;
		}
		
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			pageBar+="<a href='"+request.getRequestURI()
			+"?cPage="+(pageNo-1)
			+"&numPerPage="+numPerPage+">[다음]</a>'"; // 현재페이지에서 이동
		}
		
		request.setAttribute("pageBar", pageBar); // 밑에 페이지바 부분 
		List<Notice> list=new NoticeService().selectNotice(cPage,numPerPage);
		request.setAttribute("notices", list); // 실질적인 공지사항 데이터 부분
		
		request.getRequestDispatcher("/views/notice/noticeList.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
