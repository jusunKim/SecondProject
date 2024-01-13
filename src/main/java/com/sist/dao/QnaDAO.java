package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.sist.db.ConnectionProvider;
import com.sist.vo.QnaVO;
import com.sist.vo.ReviewVO;

public class QnaDAO {
	//싱글턴 객체 다오
	public static QnaDAO dao = null;
	
	private QnaDAO() {
	}
	
	public static QnaDAO getInstance() {
		if(dao==null) {
			dao = new QnaDAO();
		}
		return dao;
	}
	
	//이하 메소드
	
	//<문의하기>
	public int insertQna(QnaVO q) {
		int re =-1;
		String sql = "insert into qna values(?,?,?,sysdate,null,?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, q.getQno());
			pstmt.setString(2, q.getQ_title());		
			pstmt.setString(3, q.getQ_content());		
			pstmt.setInt(4, q.getUno());			
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return re;
	}
	
	//<관리자 답변>: 답변만 a_content에 추가
	public int updateQna(String a_content, int qno){
		int re =-1;
		String sql = "update qna set a_content=? where qno=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a_content);
			pstmt.setInt(2, qno);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return re;
	}
	
	//<내 문의사항> 마이페이지 들어가면 처음 뜨는 거.문의번호, 문의제목, 문의내용, 문의날짜
	public ArrayList<HashMap<String, Object>> listMyQna(int uno){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "select qno, q_title, q_content, q_date from qna where uno=" +uno;
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("qno", rs.getInt(1));
				map.put("q_title", rs.getString(2));
				map.put("q_content", rs.getString(3));
				map.put("q_date", rs.getDate(4));
				list.add(map);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<문의사항 상세> 문의사항 번호 받아서 띄우기
	public ArrayList<QnaVO> detailQna(int qno){
		ArrayList<QnaVO> list = new ArrayList<QnaVO>();
		String sql = "select * from qna where qno="+qno;
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new QnaVO(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getDate(4), rs.getString(5), rs.getInt(6)));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<전체 문의사항 조회>관리자용. 추후 검색기능 추가 시 매개변수 들어갈 듯
	public ArrayList<QnaVO> listQna(){
		ArrayList<QnaVO> list = new ArrayList<QnaVO>();
		String sql = "select * from qna";
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new QnaVO(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getDate(4), rs.getString(5), rs.getInt(6)));
				System.out.println(rs.getString(2));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	
	public int getNextNo() {
	      int no = 0;
	      String sql  = "select nvl(max(no),0) + 1 from qna";
	      try {
	         Connection conn = ConnectionProvider.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql);
	         if(rs.next()) {
	            no = rs.getInt(1);
	         }
	         ConnectionProvider.close(conn, stmt, rs);
	      } catch (Exception e) {
	         System.out.println("예외발생 : " + e.getMessage());
	      }
	      return no;
	   }
}
