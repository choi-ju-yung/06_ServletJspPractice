package com.web.board.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BoardDao {
	
	private Properties sql=new Properties();
	
	public BoardDao() {
		String path=BoardDao.class.getResource("/sql/board/boardsql.properties").getPath();
		try {
			sql.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
