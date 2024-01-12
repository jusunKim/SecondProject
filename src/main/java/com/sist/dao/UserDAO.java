package com.sist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.sist.db.ConnectionProvider;
import com.sist.vo.UserVO;

public class UserDAO {
	
	//싱글턴 객체 다오
	public static UserDAO dao = null;
	
	private UserDAO() {
	}
	
	public static UserDAO getInstance() {
		if(dao==null) {
			dao = new UserDAO();
		}
		return dao;
	}
	
	//이하 메소드
	
	//<회원가입>. 매개변수는 UserVO
	public int joinUser(UserVO u) {
		int re =-1;
		String sql = "insert into users values(?,?,?,?,?,?)";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, u.getUno());
			pstmt.setString(2, u.getU_name());
			pstmt.setString(3, u.getU_email());
			pstmt.setString(4, u.getU_phone());
			pstmt.setString(5, u.getU_pwd());
			pstmt.setString(6, u.getNickname());
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return re;
	}
	
	//<로그인>이메일과 패스워드 받아서 일치하는 정보 있으면 1 반환.
	public int login(String u_email, String u_pwd) {
		int re =-1;
		String sql="select * from users where u_email=? and u_pwd=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u_email);
			pstmt.setString(2, u_pwd);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				re=1;
			}
			ConnectionProvider.close(conn, pstmt,rs);
		} catch (Exception e) {
			System.out.println("예외발생:"+e.getMessage());
		}
		return re;
	}
	
	
	//<비밀번호찾기>이메일과 전화번호 받아서 비밀번호 찾기
	public String findPassword(String u_email, String u_phone) {
		String pw = null;
		String sql = "select u_pwd from users where u_email=? and u_phone";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u_email);
			pstmt.setString(2, u_phone);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				pw = rs.getString(1);
			}
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return pw;
	}
	
	//<회원정보수정> 패스워드 한 번 더 치라고 해서 맞으면 => uno기준으로 휴대폰번호, 별명 변경 
	public int updateUser(String u_phone, String nickname, int uno){
		int re =-1;
		String sql = "update users set u_phone=?, nickname=? where uno=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u_phone);
			pstmt.setString(2, nickname);
			pstmt.setInt(3, uno);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return re;
	}
	
	//<회원정보수정> 패스워드 한 번 더 치라고 해서 맞는지 확인하고 이하 메소드 사용 
	public int updatePassword(String u_pwd, int uno){
		int re =-1;
		String sql = "update users set u_pwd=? where uno=?";
		try {
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u_pwd);
			pstmt.setInt(2, uno);
			re = pstmt.executeUpdate();
			ConnectionProvider.close(conn, pstmt);
		} catch (Exception e) {
			System.out.println("예외 발생"+e.getMessage());
		}
		return re;
	}
	
	//<회원탈퇴>삭제/수정 시 패스워드 한 번 더 치라고 해서 맞으면 이하 메소드 사용
	public int deleteUser(int uno) {
		int re =-1;
		String sql = "delete users where uno="+uno;
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
	
	//<회원 내 정보 가져오기> uno에 따라 특정 회원의 정보 가져옴(회원 마이페이지용)
	public ArrayList<UserVO> listMyData(int uno) {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String sql = "select * from users where uno = "+uno;
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new UserVO(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	//<회원 전체 정보 가져오기> 전체 회원 리스팅.(관리자용) - 추후 검색 기능 구체화에 따라 매개변수 생길 수 있을듯.
	public ArrayList<UserVO> listAllUserData() {
		ArrayList<UserVO> list = new ArrayList<UserVO>();
		String sql = "select * from users";
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new UserVO(rs.getInt(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
	
	public int getNextNo() {
	      int no = 0;
	      String sql  = "select nvl(max(no),0) + 1 from users";
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
