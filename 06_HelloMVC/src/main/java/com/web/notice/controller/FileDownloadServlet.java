package com.web.notice.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/fileDownload.do")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public FileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 파일에 대한 다운로드기능 구현하기
		// 1. 클라이언트가 보낸 파일 이름 받기
		String fileName=request.getParameter("name");
		
		// 스트림을사용해서 하드안에 있는 upload 폴더내에 파일 불러오기
		//2. 파일에 대한 절대경로 (getRealPath())를 가져옴 
		String path = getServletContext().getRealPath("/upload/notice/");
		File f = new File(path+fileName);
		
		//3. InputStream을 생성 -> FileInputStream을 생성
		// FileInputStream 안에다 경로 넣어줌 (빨대 꽂음)
		FileInputStream fis=new FileInputStream(f);
		
		// 속도를 위해서 버퍼스트림사용 (필수는 아님)
		BufferedInputStream bis=new BufferedInputStream(fis);
		
		// 바이트단위이므로 한글이 깨질수 있음 -> 파일자체이름이 한글이없으면 밑 작업을안해도됨
		// 4. 한글명이 깨지지 않도록 인코딩처리하기
		String fileRename="";
		String header=request.getHeader("user-agent"); // user-agent : 브라우저에 대한 정보를 가져옴		
		// IE, 그 외 브라우저랑 인코딩처리방식이 달라서 인코딩을 분리해서 처리해줘야함
		boolean isMSIE=header.indexOf("MSIE")!=-1 || header.indexOf("Trident")!=-1;
		
		if(isMSIE) {
			fileRename=URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+","%20"); 
		}else {
			fileRename=new String(fileName.getBytes("UTF-8"),"ISO-8859-1"); // ISO-8859-1 규격에 맞춰서 세팅함 
		}
		
		//5. 응답헤더설정 -> contentType 설정
		// contentType 검색해서 공부하기
		response.setContentType("application/octet-stream"); // 파일 타입자체가 지정되지 않았을 때 처리해주는 타입
		response.setHeader("Content-disposition", "attachment; filename="+fileRename); 
		// attachment : 파일 경로에 알아서 다운로드해준다
		// filename : 다운로드될 파일명
		// index : 브라우저에서 열 수 있는 파일을 열어줌
		
		//6. 사용자에게 파일 보내기
		// 클라이언트에 스트림을 가져오기 -> 스트림 여는 함수 => getWriter() 및 getOutputStream()
		// getWriter()과 getOutputStream() 의 차이 -> 
		// -> Write 클래스의 2바이트 형식(char)
		// -> Strem 클래스의 1바이트 형식(byte)
		
		ServletOutputStream sos=response.getOutputStream();
		BufferedOutputStream bos = new BufferedOutputStream(sos); // 속도증가 버퍼
		
		int read=-1;
		while((read=bis.read())!=-1) {
			bos.write(read);
		}
		
		bis.close();
		bos.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
