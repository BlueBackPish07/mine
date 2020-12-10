package com.koreait.board2.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Statement;

import com.koreait.board2.common.Utils;
import com.koreait.board2.model.BoardVO;

public class BoardDAO {

	public static int RegmodBoard(BoardVO param) {
		int result =0;
		Connection con = null;
		PreparedStatement ps =null;
		
		String sql = " UPDATE t_board_?"
				+ " SET title = ? "
				+ " , ctnt = ? "
				+ " WHERE i_board = ? ";
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql); // preparStatement => sql물음표에다가 값 넣는 용도이다
			ps.setInt(1, param.getTyp());
			ps.setNString(2, param.getTitle());
			ps.setNString(3, param.getCtnt());
			ps.setInt(4, param.getI_board());
			
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps);
		}
		return 0;
		
		
		
					
		}
	public static void addHits(BoardVO param) {
		
		String sql = "UPDATE t_board_? "
				+ " SET hits = hits + 1 "
				+ "WHERE i_board = ? ";
		
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con= DBUtils.getCon();
			ps= con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getI_board());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps);
		}
		
		
			//detail 페이지 열 때마다 호출하기
				// list에서도 볼수 있게 구상해보기
	
	}
	
	public static int delBoard(BoardVO param) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " DELETE FROM t_board_? WHERE i_board = ? ";

		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			ps.setInt(2, param.getI_board());			
			return ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con, ps);
		}
		return 0;
			
		
	}
	
	public static BoardVO selBoard(final BoardVO param){
		//디테일 화면이 나타나도록 짜라
		BoardVO vo = null;
		Connection con = null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		
		
		String sql = " SELECT i_board, title, ctnt, r_dt, hits "
				+" FROM t_board_?"
				+" WHERE i_board = ?";
			
		try {
			
			
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, param.getTyp()); 
			ps.setInt(2, param.getI_board()); 
			
			rs= ps.executeQuery();
			if (rs.next()) { // rs에 결과값이 있으면 실행

				vo = new BoardVO(); // 검색결과값이 있으면 객체를 생성해서 db에서 값을 가져와
				int i_board = rs.getInt("i_board");
				String title = rs.getNString("title"); // 각 정보를 담을 그릇 준비
				String ctnt = rs.getNString("ctnt");
				int hits = rs.getInt("hits");
				String r_dt = rs.getString("r_dt");
				vo.setI_board(i_board); // 세팅한다.
				vo.setTitle(title);
				vo.setCtnt(ctnt);
				vo.setR_dt(r_dt);
				vo.setHits(hits);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps, rs);
		}
		return vo;
	}
	
	
	public static List<BoardVO> selBoardList(BoardVO param){
		List<BoardVO> list = new ArrayList<>();
		String sql = " SELECT i_board, title, hits, r_dt FROM t_board_? ORDER BY i_board desc ";
		
		
		Connection con = null;
		PreparedStatement ps =null;
		ResultSet rs = null;
		
		
		try {		
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getTyp());
			rs = ps.executeQuery();
			
			BoardVO vo = null; // 비우는 이유?
			while (rs.next()) {
				
				vo = new BoardVO();	
				list.add(vo);
				
				vo.setI_board(rs.getInt("i_board"));
				vo.setTitle(rs.getNString("title"));
				//리스트 뿌릴땐 내용 가져오면 x 트레픽먹는다
				vo.setR_dt(rs.getString("r_dt"));
				vo.setHits(rs.getInt("hits"));
				
			}
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}finally {
			DBUtils.close(con, ps, rs);
		}		
		return list;		
	}


	public static BoardVO selBoard(int i_board){
		
		
		BoardVO vo = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT i_board, title, ctnt, r_dt "
				+ " FROM t_board "
				+ " WHERE i_board = ? ";
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql); // preparStatement => sql물음표에다가 값 넣는 용도이다
			ps.setInt(1, i_board); // 첫번째 물음표에 값 넣는다.
			rs = ps.executeQuery(); //  SELECT 구문을 수행할 때 사용되는 함수입니다.

			if (rs.next()) { // rs에 결과값이 있으면 실행

				vo = new BoardVO(); // 검색결과값이 있으면 객체를 생성해서
				String title = rs.getString("title"); // 각 정보를 담을 그릇 준비
				String ctnt = rs.getString("ctnt");
				String r_dt = rs.getString("r_dt");
				vo.setI_board(i_board); // 세팅한다.
				vo.setTitle(title);
				vo.setCtnt(ctnt);
				vo.setR_dt(r_dt);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

		} finally {
			DBUtils.close(con, ps, rs);
		}
		return vo;
			
	} 
	
	public static int insBoard(final BoardVO param) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " INSERT INTO t_board_? "
		+" (title, ctnt) " 
		+" VALUES " + " ( ? , ? )"; 
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); 
			// insert 후 생성된 행의 키 값을 받아오기 위함. 쿼리문 sql을 보낼때 같이 보내줘야한다. 원래는 1임
			ps.setInt(1, param.getTyp());
			ps.setNString(2, param.getTitle());
			ps.setNString(3, param.getCtnt());
			int result = ps.executeUpdate();
			
			rs = ps.getGeneratedKeys(); // pk가 auto increasement로 지정되어있었고 1 증가된 PK값을 getGeneratedKeys 로 받아서 rs로 리턴해준다.
			if(rs.next()) {
				int i_board = rs.getInt(1); //검색한 값에서 첫번째 컬럼
				param.setI_board(i_board); // 글 쓰고 나서 글의 디테일 페이지로 보낼 수 있게 pk값이 담긴 i_board값을 넘긴다.
				//설정하지 않으면 i_board는 0이였을것
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con, ps, rs);
		}
		return 0;
	}
	
}
