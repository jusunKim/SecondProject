package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.sist.db.ConnectionProvider;
import com.sist.vo.ReviewVO;
import com.sist.vo.WishlistVO;

public class ReviewDAO {
	//싱글턴 객체 다오
	public static ReviewDAO dao = null;
	
	private ReviewDAO() {
	}
	
	public static ReviewDAO getInstance() {
		if(dao==null) {
			dao = new ReviewDAO();
		}
		return dao;
	}
	
	//이하 메소드
	
	//<내 리뷰 조회>
	//마이 리뷰: 이미지, 숙소이름, 리뷰제목, 별점, 날짜
	//select img1, p_name, re_title, r.rating, re_date
	//from review r, product p, image i
	//where r.pno = p.pno and i.pno = r.pno
	//and r.uno=3 order by re_date desc;
	public ArrayList<HashMap<String, Object>> listMyReview(int uno){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "select img1, p_name, re_title, r.rating, re_date "
				+ "from review r, product p, image i "
				+ "where r.pno = p.pno "
				+ "and i.pno = r.pno "
				+ "and r.uno="+uno+" order by re_date desc";
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("img1", rs.getString(1));
				map.put("p_name", rs.getString(2));
				map.put("re_title", rs.getString(3));
				map.put("rating", rs.getDouble(4));
				map.put("re_date", rs.getDate(5));
				list.add(map);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<전체 리뷰 조회>관리자 입장. 모든 리뷰 목록 불러오기. 추후 검색 기능 추가 시 매개변수 필요할 듯
	//관리자 입장에서 리뷰 뽑기: 리뷰번호, 숙소이름, 작성자명, 리뷰제목, 리뷰내용, 작성일, 별정
	//select reno, p.p_name, u.u_name, re_title, re_content, re_date, r.rating
	//from review r, product p, users u
	//where r.pno = p.pno and r.uno=u.uno
	//order by re_date desc;
	public ArrayList<HashMap<String, Object>> listAllReview(){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "select reno, p.p_name, u.u_name, re_title, re_content, re_date, r.rating "
				+ "from review r, product p, users u "
				+ "where r.pno = p.pno and r.uno=u.uno "
				+ "order by re_date desc";
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("reno", rs.getInt(1));
				map.put("p_name", rs.getString(2));
				map.put("u_name", rs.getString(3));
				map.put("re_title", rs.getString(4));
				map.put("re_content", rs.getString(5));
				map.put("re_date", rs.getDate(6));
				map.put("rating", rs.getDouble(7));
				list.add(map);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<상품페이지 리뷰 표시 : 최신순 2개!>
	//select * from
	//(select  pno, reno, nickname, re_title, re_content, rating, re_date from 
	//review r, users u where r.uno=u.uno and pno=1001
	//order by re_date desc) a
	//where rownum<=2;
	public ArrayList<HashMap<String, Object>> listTwoReview(int pno){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "select * from "
				+ "(select pno, reno, nickname, re_title, re_content, rating, re_date from "
				+ "review r, users u where r.uno=u.uno and pno="+pno
				+ " order by re_date desc) a "
				+ "where rownum<=2";
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("pno", rs.getInt(1));
				map.put("reno", rs.getInt(2));
				map.put("nickname", rs.getString(3));
				map.put("re_title", rs.getString(4));
				map.put("re_content", rs.getString(5));
				map.put("rating", rs.getDouble(6));
				map.put("re_date", rs.getDate(7));
				list.add(map);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<리뷰 상세>
	//특정 상품 페이지에서 리뷰 더보기 클릭 시 페이지: 닉네임 별점 작성일 제목 내용
	//select nickname, re_title, re_content, rating, re_date from 
	//review r, users u where r.uno=u.uno and pno=1001
	//order by re_date desc;
	public ArrayList<HashMap<String, Object>> detailReview(int pno){
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "select nickname, re_title, re_content, rating, re_date from "
				+ "review r, users u where r.uno=u.uno and pno="+pno
				+ "order by re_date desc";
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("nickname", rs.getString(1));
				map.put("re_title", rs.getString(2));
				map.put("re_content", rs.getString(3));
				map.put("rating", rs.getDouble(4));
				map.put("re_date", rs.getDate(5));
				list.add(map);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<리뷰 추가>
	public int insertReview(ReviewVO r) {
		int re =-1;
		String sql = "insert into review values(?,?,?,sysdate,?,?,?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getReno());
			pstmt.setString(2, r.getRe_title());
			pstmt.setString(3, r.getRe_content());
			pstmt.setDouble(4, r.getRating());
			pstmt.setInt(5, r.getPno());
			pstmt.setInt(6, r.getUno());			
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return re;
	}
	
	//<리뷰 삭제> 리뷰 번호 받아서 삭제
	public int deleteReview(int reno) {
		int re =-1;
		String sql = "delete review where reno="+reno;
		try {
			Connection conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			re = stmt.executeUpdate(sql);
			ConnectionProvider.close(conn, stmt);
		} catch (Exception e) {
			System.out.println("예외 발생:"+e.getMessage());
		}
		return re;
		
	}
	
	public int getNextNo() {
	      int no = 0;
	      String sql  = "select nvl(max(no),0) + 1 from review";
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
