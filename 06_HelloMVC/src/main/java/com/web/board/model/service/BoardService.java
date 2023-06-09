package com.web.board.model.service;

import java.sql.Connection;
import java.util.List;

import com.web.board.model.dao.BoardDao;
import com.web.board.model.vo.Board;
import static com.web.common.JDBCTemplate.*;

public class BoardService {
	private BoardDao dao=new BoardDao();
	
	public List<Board> selectBoard(int cPage, int numPerPage){
		Connection conn=getConnection();
		List<Board> boards=dao.selectBoard(conn,cPage,numPerPage);
		close(conn);
		return boards;
	}
	
	public int selectBoardCount() {
		Connection conn=getConnection();
		int result=dao.selectBoardCount(conn);
		close(conn);
		return result;
	}
	
	public Board selectBoardByNo(int no) {
		Connection conn=getConnection();
		Board b=dao.selectBoardByNo(conn,no);
		close(conn);
		return b;
	}
	
	public int insertBoard(Board b) {
		Connection conn=getConnection();
		int result=dao.insertBoard(conn,b);
		if(result>0)commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int updateBoard(Board b) {
		return 0;
	}
	
	public int deleteBoard(int no) {
		return 0;
	}
}
