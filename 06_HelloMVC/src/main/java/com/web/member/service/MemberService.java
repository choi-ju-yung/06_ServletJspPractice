package com.web.member.service;

import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.commit;
import static com.web.common.JDBCTemplate.getConnection;
import static com.web.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.web.member.dao.MemberDao;
import com.web.member.model.vo.Member;

public class MemberService {
	
	private MemberDao dao = new MemberDao();
	
	public Member selectByUserIdAndPw(String userId, String password) {
		Connection conn=getConnection();
		Member m=dao.selectByUserIdAndPw(conn,userId,password);
		close(conn);
		return m;
	}
	
	
	
	
	public int insertMember(Member m) {
		Connection conn = getConnection();
		int result = dao.insertMember(conn,m);
		if(result>0)commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public Member selectByUserId(String userId) {
		Connection conn=getConnection();
		Member m = dao.selectByUserId(conn,userId);
		close(conn);
		return m;
	}
	
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = dao.updateMember(conn,m);
		if(result>0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}
	
	
	public int updatePassword(String userId, String password) {
		Connection conn = getConnection();
		int result=dao.updatePassword(conn, userId, password);
		if(result>0)commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	
	
	
	
}
