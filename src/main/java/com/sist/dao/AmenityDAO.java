package com.sist.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.sist.db.ConnectionProvider;
import com.sist.vo.AmenityVO;
import com.sist.vo.QnaVO;

public class AmenityDAO {
	//싱글턴 객체 다오
	public static AmenityDAO dao = null;
	
	private AmenityDAO() {
	}
	
	public static AmenityDAO getInstance() {
		if(dao==null) {
			dao = new AmenityDAO();
		}
		return dao;
	}
	
	//이하 메소드
	
	//<편의시설 조회>
	public ArrayList<AmenityVO> listAmenity(int pno){
		ArrayList<AmenityVO> list = new ArrayList<AmenityVO>();
		String sql = "select * from amenity where pno="+pno;
		try {
			Connection conn =ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new AmenityVO(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6)));
			}
			ConnectionProvider.close(conn, stmt, rs);
		} catch (Exception e) {
			System.out.println("예외:"+e.getMessage());
		}
		return list;
	}
}
