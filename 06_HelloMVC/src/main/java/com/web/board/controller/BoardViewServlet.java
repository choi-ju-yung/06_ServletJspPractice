package com.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.board.model.service.BoardService;
import com.web.board.model.vo.Board;
import com.web.board.model.vo.BoardComment;


@WebServlet("/board/boardView.do")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BoardViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo=Integer.parseInt(request.getParameter("no"));
		
	
		Cookie[] cookies=request.getCookies();  // 쿠키를 미리가져와서 확인함
		String boardRead="";
		boolean isRead=false;
		if(cookies!=null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("boardRead")) { // 해당 키값이 있으면
					boardRead = c.getValue(); // 해당 대응대는 값 저장
					if(boardRead.contains("|"+boardNo+"|")) {
						isRead=true;
					}
					break;
				}
			}
		}
		
		if(!isRead) {
			Cookie c = new Cookie("boardRead",boardRead+"|"+boardNo+"|"); // 번호를 구분하기위해서 앞뒤로 ||넣음
			c.setMaxAge(60*60*24);
			response.addCookie(c);
		}
		
		Board b =new BoardService().selectBoardByNo(boardNo,isRead);
		
		//댓글을 가져와 출력하기
		List<BoardComment> comments=new BoardService().selectBoardComment(boardNo);
		
		request.setAttribute("comments", comments);
		
		
		// 중간에 삭제할경우 널포인터오류 나올수있으므로 분기처리해야함
		request.setAttribute("board",b);
		request.getRequestDispatcher("/views/board/boardView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
