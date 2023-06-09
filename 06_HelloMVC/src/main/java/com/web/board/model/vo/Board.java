package com.web.board.model.vo;

import java.sql.Date;


import com.web.notice.model.vo.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
	private int boardNo;
	private String boardTitle;
	private String boardWriter; // DB 따라서 작성했음
// private Member boardWriter  // 참조관계이기때문에 원래는 이런식으로 작성해야함 
	private String boardContent;
	private String boardOriginalFilename;
	private String boardRenamedFilename;
	private Date boardDate;
	private int boardReadCount;
	
	
}
