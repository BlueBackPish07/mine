package com.koreait.board2.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.xdevapi.PreparableStatement;

public class DBUtils {
	

	public static Connection getCon() throws ClassNotFoundException, SQLException {
		final String URL = "jdbc:mysql://localhost:3306/i_board";  // db 이름으로 하자!
		final String USER = "root";
		final String PW = "koreait2020";
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(URL, USER, PW);
		// Connetction 계속 열어두고 있으면 과부하 걸림
		
		
		System.out.println("DB연결 성공!");
		
		return con;
	}
	public static void close(Connection con, PreparedStatement ps) {
		//Connection은 연결 해주는 역할
		// PreparedStatement 실행시키는 역할  insert delete 담당 
		// Resultset => DB에서 select 한 것들이 여기 담겨있음
		// 순서대로 cone > Pre로 열고 닫을땐 역순으로 
		if(ps != null) {
			try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
		
		if(con != null) {
			try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
					
		
		
	}
	
	public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		
		if(rs != null) {
			try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
		}		
		close(con, ps);
		
	}

}
