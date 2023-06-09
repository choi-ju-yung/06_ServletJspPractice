package com.web.notice.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.spi.DirStateFactory.Result;

import com.web.notice.model.vo.Notice;
import static com.web.common.JDBCTemplate.close;

public class NoticeDao {

	private Properties sql = new Properties();

	public NoticeDao() {
		String path = NoticeDao.class.getResource("/sql/notice/noticesql.properties").getPath();
		try {
			sql.load(new FileReader(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Notice> selectNotice(Connection conn, int cPage, int numPerPage){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		List<Notice> list=new ArrayList();
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectNotice"));
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				list.add(getNotice(rs));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return list;
	}

	public int selectNoticeCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectNoticeCount"));
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt("RN"); // 별칭명으로 가져올수있음 rs.getInt(1); 도 가능함 컬럼은 하나이기때문에
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	
	public int insertNotice(Connection conn, Notice n) {
		PreparedStatement pstmt=null;
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertNotice"));
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeWriter());
			pstmt.setString(3, n.getNoticeContent());
			pstmt.setString(4, n.getFilePath());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	
	public Notice selectNoticeByNo(Connection conn, int no) {
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		Notice n = null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectNoticeByNo"));
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			
			if(rs.next()) n=getNotice(rs); // no는 기본키이기때문에 최소 하나는 있어야함
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return n;
	}
	
	
	
	
	private Notice getNotice(ResultSet rs) throws SQLException {
		return Notice.builder()
				.noticeNo(rs.getInt("notice_no"))
				.noticeTitle(rs.getString("notice_title"))
				.noticeWriter(rs.getString("notice_writer"))
				.noticeContent(rs.getString("notice_content"))
				.noticeDate(rs.getDate("notice_date"))
				.filePath(rs.getString("filepath"))
				.build();
	}
	
}
