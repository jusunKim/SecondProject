package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.sist.db.ConnectionProvider;
import com.sist.vo.WishlistVO;

public class WishlistDAO {
	//싱글턴 객체 다오
	public static WishlistDAO dao = null;
	
	private WishlistDAO() {
	}
	
	public static WishlistDAO getInstance() {
		if(dao==null) {
			dao = new WishlistDAO();
		}
		return dao;
	}
	
	//이하 메소드
	
	//<회원의 위시리스트 정보 가져오기>uno에 따라 : 상품번호, 이미지, 상품이름, 별점, 상품가격
	//select p.pno, i.img1, p_name, rating, price
	//from wishlist w, product p, image i
	//where w.pno=p.pno and p.pno=i.pno
	//and w.uno=5;
	public ArrayList<HashMap<String, Object>> listWishlist(int uno) {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		String sql = "select p.pno, i.img1, p_name, rating, price "
				+ "from wishlist w, product p, image i "
				+ "where w.pno=p.pno and p.pno=i.pno "
				+ "and w.uno="+uno;
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("pno", rs.getInt(1));
				map.put("img1", rs.getString(2));
				map.put("p_name", rs.getString(3));
				map.put("rating", rs.getInt(4));
				map.put("price", rs.getInt(5));
				list.add(map);
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<위시리스트 추가>
	public int insertWishlist(WishlistVO w) {
		int re =-1;
		String sql = "insert into wishlist values(?,?,?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, w.getWno());
			pstmt.setInt(2, w.getUno());
			pstmt.setInt(3, w.getPno());
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return re;
	}
	
	//<위시리스트 삭제>
	public int deleteWishlist(int wno) {
		int re =-1;
		String sql = "delete wishlist where wno="+wno;
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
	      String sql  = "select nvl(max(no),0) + 1 from wishlist";
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
