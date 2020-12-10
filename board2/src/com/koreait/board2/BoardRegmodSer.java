package com.koreait.board2;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.board2.common.Utils;
import com.koreait.board2.db.BoardDAO;
import com.koreait.board2.model.BoardVO;


@WebServlet("/bRegMod")
public class BoardRegmodSer extends HttpServlet {
	
  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int i_board = Utils.getIntParam(request, "i_board"); // 값을 덜 넣어줘서 0이 날아온다.
		int typ = Utils.getIntParam(request, "typ"); //0
		if(typ == 0) { //에러페이지로 보냄
			Utils.forwardErr(request, response);
			return;
		}
		
		String title = "글 등록";
		if (i_board > 0) {//수정 request 로 값을 담을것입니다.
			BoardVO param= new BoardVO();
			param.setTyp(typ);
			param.setI_board(i_board);
			request.setAttribute("data", BoardService.selBoard(param));
		} 
		request.setAttribute("typ", typ);
		Utils.forward(title, "bRegMod", request, response);
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int typ = Utils.getIntParam(request, "typ");		
		if(typ == 0) {
			request.setAttribute("err", "에러가 발생하였습니다.");
			doGet(request, response);
			return;
		}
		int i_board = Utils.getIntParam(request, "i_board");
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		
		BoardVO param = new BoardVO();
		param.setTyp(typ);
		param.setI_board(i_board);
		param.setTitle(title);
		param.setCtnt(ctnt);
		
		int result = BoardService.regmod(param); //여기서 오류가 일어나면 에러 메세지 띄우게함
		
		if(result == 0) {
			request.setAttribute("err", "에러가 발생하였습니다.");
			doGet(request, response);
			return;
		}
		// 오류가 없었으면 pk값이 있으므로 디테일페이지로 성공적으로 보내짐!
		
		response.sendRedirect("/bDetail?typ=" + typ 
				+ "&i_board=" + param.getI_board());
		
		
	}

}
