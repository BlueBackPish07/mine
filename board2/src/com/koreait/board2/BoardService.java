package com.koreait.board2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.taglibs.standard.lang.jstl.EnumeratedMap;

import com.koreait.board2.db.BoardDAO;
import com.koreait.board2.model.BoardVO;

public class BoardService {

	public static BoardVO detail(BoardVO param, HttpServletRequest request){
		String ip = request.getRemoteAddr(); // 클라이언트 ip값
		//192.어쩌구저쩌구
		System.out.println("ip : " + ip);
		
		String key = String.format("b_%d_%d", param.getTyp(), param.getI_board());
		// b_1_1
		ServletContext application = request.getServletContext(); 
		// ServletContext = 서블릿의 정보
		String savedIp = (String)application.getAttribute(key);
		// 
		if(!ip.equals(savedIp)) { // 만약에 새로 날아온 서블릿의 정보와 String ip값을 비교해서 다르면 조회수가 올라가게 한다.
			application.setAttribute(key, ip);
			BoardDAO.addHits(param);
		}
		
		Enumeration <String> strArr = application.getAttributeNames();
		
		while(strArr.hasMoreElements()) { // 값이 있으면 t
			String str = strArr.nextElement(); //다음값을 가르켜준다.
			if(str.startsWith("b_")) { // b_ 로 시작하면 t 아니면 f
				System.out.println("key : " + str);
				System.out.println("value : " + application.getAttribute(str));
			}
		}
		
		return selBoard(param);
	}
	
	public static List<BoardVO> selBoardList(BoardVO param){

		return BoardDAO.selBoardList(param);
	}
	
	public static BoardVO selBoard(BoardVO param) {
		return BoardDAO.selBoard(param);
	}
	
	public static int delBoard(final BoardVO param) {
		return BoardDAO.delBoard(param);
		
	}
	
	public static int regmod(BoardVO param) {
		if (param.getI_board() > 0) //수정
			{
			return BoardDAO.RegmodBoard(param);
			}
		return BoardDAO.insBoard(param);
		
	}
	
}
