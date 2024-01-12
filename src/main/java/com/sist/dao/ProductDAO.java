package com.sist.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.sist.db.ConnectionProvider;
import com.sist.vo.ProductVO;

public class ProductDAO {
   public static ProductDAO dao = null;
   private ProductDAO() { }
   public static ProductDAO getInstance() {
      if( dao == null ) {
         dao = new ProductDAO();
      }
      return dao;
   }
   
   // 상품 삭제시 자식 테이블에 연결되어있는 레코드도 삭제시키는 트리거
//   CREATE OR REPLACE TRIGGER cascade_delete_trigger
//   BEFORE DELETE ON product
//   FOR EACH ROW
//   BEGIN
//     DELETE FROM Amenity WHERE pno = :OLD.pno;
//     DELETE FROM Review WHERE pno = :OLD.pno;
//     DELETE FROM Wishlist WHERE pno = :OLD.pno;
//     DELETE FROM Image WHERE pno = :OLD.pno;
//     DELETE FROM Reservation WHERE pno = :OLD.pno;
//   END;
//   /
   
   // review 를 등록완료시 상품번호에 따른 평균 평점을 구하여 상품테이블 평점 업데이트
      public int avgRating(int pno) {
         int re = -1;
         String sql = "update product set rating ="
               + " (select avg(rating from review"
               + " where pno = "+ pno +") where pno = " + pno;
         try {
            Connection conn = ConnectionProvider.getConnection();
            Statement stmt = conn.createStatement();
            re = stmt.executeUpdate(sql);
            ConnectionProvider.close(conn, stmt);
         } catch (Exception e) {
            System.out.println("예외발생:"+e.getMessage());
         }
         return re;
      }
   
   // 검색은 상품명으로 하지만 상품번호를 받아와서 삭제 -- 트리거 설정
   public int deleteProduct(int pno) {
      int re = -1;
      String sql  = "delete product where pno = "+pno; 
      try {
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, pno);
         re = pstmt.executeUpdate();
         ConnectionProvider.close(conn, pstmt);
      } catch (Exception e) {
         System.out.println("예외발생:"+e.getMessage());
      }
      return re;
   }
   
   // 리뷰를 전체 조회해서 평균평점 계산
//   public int avgRating() {
//       
//   }
   
   // 상품번호에 따른 연락처, 최대 인원수, 가격 변경
   public int updateProduct(ProductVO p) {
      int re = -1;
      String sql = "update Product set p_phone = ?, occ_max = ?, price = ? where pno = ?";
      try {      
         Connection conn =ConnectionProvider.getConnection();   
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, p.getP_phone());
         pstmt.setInt(2, p.getOcc_max());
         pstmt.setInt(3, p.getPrice());
         pstmt.setInt(4, p.getPno());
         
         re = pstmt.executeUpdate();
         ConnectionProvider.close(conn, pstmt);      
      }catch (Exception e) {
         System.out.println("예외발생:"+e.getMessage());
      }      
      return re;
   }
   
   // 상품등록
   public int insertProduct(ProductVO p) {
      String sql = "insert into Product values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      int re = -1;
      try {
         Connection conn = ConnectionProvider.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, p.getPno());
         pstmt.setString(2, p.getP_phone());
         pstmt.setString(3, p.getP_name());
         pstmt.setString(4, p.getAddr());
         pstmt.setInt(5, p.getOcc_max());
         pstmt.setInt(6, p.getPrice());
         pstmt.setDouble(7, p.getRating());
         pstmt.setString(8, p.getP_explain());
         pstmt.setDouble(9, p.getLat());
         pstmt.setDouble(10, p.getLng());
         pstmt.setInt(11, p.getA_code());
         pstmt.setInt(12, p.getP_code());
         
         re = pstmt.executeUpdate();
         ConnectionProvider.close(conn, pstmt);
      } catch (Exception e) {
         System.out.println("예외발생 : " + e.getMessage());
      }
      return re;
   }
   
   
   //필터링 검색
   //select p.pno, i.img1, p_name, price, rating
   //from product p, image i
   //where p.pno = i.pno
   //and price between 10000 and 100000
   //and rating >=4
   //and occ_max>=2;
   public ArrayList<HashMap<String, Object>> filterSearchProduct(int min_price, int max_price, double rating, int occ_max){
	      ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	      String sql = "select p.pno, i.img1, p_name, price, rating "
	      		+ "from product p, image i "
	      		+ "where p.pno = i.pno "
	      		+ "and price between ? and ? "
	      		+ "and rating >=? "
	      		+ "and occ_max>=?";
	      try {
				Connection conn =ConnectionProvider.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, min_price);
				pstmt.setInt(2, max_price);
				pstmt.setDouble(3, rating);
				pstmt.setInt(4, occ_max);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					System.out.println(1);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("pno", rs.getInt(1));
					map.put("img", rs.getString(2));
					map.put("p_name", rs.getString(3));
					map.put("price", rs.getInt(4));
					map.put("rating", rs.getDouble(5));
					list.add(map);
				}
				ConnectionProvider.close(conn, pstmt, rs);
			} catch (Exception e) {
				System.out.println("예외:"+e.getMessage());
			}
	      return list;
	   }
   
   //위치로 검색
   //select p.pno, i.img1, p.p_name, p.price, p.rating
   //from product p, image i
   //where p.pno = i.pno
   //and a_code=?;
   public ArrayList<HashMap<String, Object>> mapSearchProduct(int a_code){
	   ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	   String sql = "select p.pno, i.img1, p.p_name, p.price, p.rating "
	   		+ "from product p, image i "
	   		+ "where p.pno = i.pno "
	   		+ "and a_code="+a_code;
	   try {
		   Connection conn =ConnectionProvider.getConnection();
		   Statement stmt = conn.createStatement();
		   ResultSet rs = stmt.executeQuery(sql);
		   while(rs.next()) {
			   System.out.println(1);
			   HashMap<String, Object> map = new HashMap<String, Object>();
			   map.put("pno", rs.getInt(1));
			   map.put("img", rs.getString(2));
			   map.put("p_name", rs.getString(3));
			   map.put("price", rs.getInt(4));
			   map.put("rating", rs.getDouble(5));
			   list.add(map);
		   }
		   ConnectionProvider.close(conn, stmt, rs);
	   } catch (Exception e) {
		   System.out.println("예외:"+e.getMessage());
	   }
	   return list;
   }
   
   //상단바 검색
   //select p.pno, i.img1, p.p_name, p.price, p.rating
   //from product p, image i
   //where p.pno=i.pno
   //and occ_max>=2
   //and a_code=2
   //and p.pno in (
   //select distinct p.pno
   //from product p, reservationstatus rs
   //where p.pno=rs.pno
   //and reserved_date not between '23/12/23' and '23/12/30');
   public ArrayList<HashMap<String, Object>> menuSearchProduct(int occ_max, int a_code, String start_date, String end_date){
	   ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	   String sql = "select p.pno, i.img1, p.p_name, p.price, p.rating "
	   		+ "from product p, image i "
	   		+ "where p.pno=i.pno "
	   		+ "and occ_max>=? "
	   		+ "and a_code=? "
	   		+ "and p.pno in ( "
	   		+ "select distinct p.pno "
	   		+ "from product p, reservationstatus rs "
	   		+ "where p.pno=rs.pno "
	   		+ "and reserved_date not between ? and ?)";
	   try {
		   Connection conn =ConnectionProvider.getConnection();
		   PreparedStatement pstmt = conn.prepareStatement(sql);
		   pstmt.setInt(1, occ_max);
		   pstmt.setInt(2, a_code);
		   pstmt.setString(3, start_date);
		   pstmt.setString(4, end_date);
		   ResultSet rs = pstmt.executeQuery();
		   while(rs.next()) {
			   HashMap<String, Object> map = new HashMap<String, Object>();
			   map.put("pno", rs.getInt(1));
			   map.put("img", rs.getString(2));
			   map.put("p_name", rs.getString(3));
			   map.put("price", rs.getInt(4));
			   map.put("rating", rs.getDouble(5));
			   list.add(map);
			   System.out.println(rs.getInt(1));
		   }
		   ConnectionProvider.close(conn, pstmt, rs);
	   } catch (Exception e) {
		   System.out.println("예외:"+e.getMessage());
	   }
	   return list;
   }
   
   // 상품 전체 조회
   public ArrayList<HashMap<String, Object>> listProduct(){
      ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
      String sql = "select i.img1, p.p_name, p.price, p.rating"
            + "from product p, image i"
            + "where p.pno = i.pno";
      try {
         Connection conn = ConnectionProvider.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while(rs.next()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("img", rs.getString(1));
            map.put("p_name", rs.getString(2));
            map.put("price", rs.getInt(3));
            map.put("rating", rs.getDouble(4));
         }
         ConnectionProvider.close(conn, stmt, rs);
      } catch (Exception e) {
         System.out.println("예외발생 : " + e.getMessage());
      }
      return list;
   }
   
   // 상세페이지 조회 조건 추가시 매개변수,sql 수정 -- 미완성
   public ArrayList<ProductVO> detailProduct(){
      ArrayList<ProductVO> list = new ArrayList<ProductVO>();
      String sql = "select * from product";
      try {
         Connection conn = ConnectionProvider.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while(rs.next()) {
            list.add(new ProductVO( rs.getInt(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getInt(5),
                  rs.getInt(6),
                  rs.getDouble(7),
                  rs.getString(8),
                  rs.getDouble(9),
                  rs.getDouble(10),
                  rs.getInt(11),
                  rs.getInt(12)   
                  ));
         }
         ConnectionProvider.close(conn, stmt, rs);
      } catch (Exception e) {
         System.out.println("예외발생 : " + e.getMessage());
      }
      return list;
   }
   
   // 카드 view 내용에 따라 매개변수, sql 수정 - 미완성
   public ArrayList<ProductVO> listCard(){
      ArrayList<ProductVO> list = new ArrayList<ProductVO>();
      String sql = "select * from product";
      try {
         Connection conn = ConnectionProvider.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while(rs.next()) {
            list.add(new ProductVO( rs.getInt(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getInt(5),
                  rs.getInt(6),
                  rs.getDouble(7),
                  rs.getString(8),
                  rs.getDouble(9),
                  rs.getDouble(10),
                  rs.getInt(11),
                  rs.getInt(12)   
                  ));
         }
         ConnectionProvider.close(conn, stmt, rs);
      } catch (Exception e) {
         System.out.println("예외발생 : " + e.getMessage());
      }
      return list;
   }
   
   // 상품 등록시 번호 부여
   public int getNextNo() {
      int no = 0;
      String sql  = "select nvl(max(no),0) + 1 from Product";
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
   
   //주소 넣으면 a_code반환하는 메소드. product에 insert할 때 a_code 노가다로 찾지 말고 메소드 불러와서 넣으면 될듯
   public int getACode(String addr) {
	   int re = 0;
	   String keyword = null;
	   if(addr.indexOf("제주시")!=-1) {
		   if(addr.indexOf("한경면")!=-1) {
			   keyword = "한경면";
		   }else if(addr.indexOf("한림읍")!=-1) {
			   keyword = "한림읍";
		   }else if(addr.indexOf("애월읍")!=-1) {
			   keyword = "애월읍";
		   }else if(addr.indexOf("조천읍")!=-1) {
			   keyword = "조천읍";
		   }else if(addr.indexOf("구좌읍")!=-1) {
			   keyword = "구좌읍";
		   }else {
			   keyword= "제주시";
		   }
	   }else {
		   if(addr.indexOf("대정읍")!=-1) {
			   keyword = "대정읍";
		   }else if(addr.indexOf("안덕면")!=-1) {
			   keyword = "안덕면";
		   }else if(addr.indexOf("중문")!=-1) {
			   keyword = "중문";
		   }else if(addr.indexOf("남원읍")!=-1) {
			   keyword = "남원읍";
		   }else if(addr.indexOf("표선면")!=-1) {
			   keyword = "표선면";
		   }else if(addr.indexOf("성산읍")!=-1) {
			   keyword = "성산읍";
		   }else {
			   keyword= "서귀포시";
		   }
	   }
	   String sql = "select a_code from local where local = '"+keyword+"' ";
	     try {
	         Connection conn = ConnectionProvider.getConnection();
	         Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(sql);
	         if(rs.next()) {
	            re = rs.getInt(1);
	         }
	         ConnectionProvider.close(conn, stmt, rs);
	      } catch (Exception e) {
	         System.out.println("예외발생 : " + e.getMessage());
	      }
	   
	   return re;
   }
}