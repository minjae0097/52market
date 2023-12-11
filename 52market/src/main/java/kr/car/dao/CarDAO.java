package kr.car.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_MapVO;
import kr.car.vo.CarlistVO;
import kr.util.DBUtil;

public class CarDAO {
	private static CarDAO instance = new CarDAO();
	public static CarDAO getInstance() {
		return instance;
	}
	private CarDAO() {}
	
	//중고차 글쓰기
	public void insertCar(CarlistVO list, CarList_DetailVO detail,Car_MapVO map)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		String sql = null;
		ResultSet rs = null;
		int num = 0;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "SELECT carlist_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql = "INSERT INTO carlist_detail (carlist_num, car_seller,car_type,car_fuel,car_price,"
					+ "car_model_year,car_distance,car_transmission,car_origin,car_image,car_title) VALUES("
					+ "?,?,?,?,?,?,?,?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setInt(2, detail.getCar_seller());
			pstmt2.setInt(3, detail.getCar_type());
			pstmt2.setInt(4, detail.getCar_fuel());
			pstmt2.setInt(5, detail.getCar_price());
			pstmt2.setInt(6, detail.getCar_model_year());
			pstmt2.setInt(7, detail.getCar_distance());
			pstmt2.setInt(8, detail.getCar_transmission());
			pstmt2.setInt(9, detail.getCar_origin());
			pstmt2.setString(10, detail.getCar_image());
			pstmt2.setString(11, detail.getCar_title());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO carlist (carlist_num,carlist_content) VALUES(?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, list.getCarlist_content());
			pstmt3.executeUpdate();
			
			sql = "INSERT INTO car_map (carlist_num,location_x,location_y,location,road_address_name,address_name) "
					+ " VALUES(?,?,?,?,?,?)";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setInt(1, num);
			pstmt4.setString(2, map.getLocation_x());
			pstmt4.setString(3, map.getLocation_y());
			pstmt4.setString(4, map.getLocation());
			pstmt4.setString(5, map.getRoad_address_name());
			pstmt4.setString(6, map.getAddress_name());
			pstmt4.executeUpdate();
			
			conn.commit();
			
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(rs, pstmt4, conn);
		}
		
	}
	//중고차 리스트
	public List<CarList_DetailVO> getCarList(int start, int end, String keyfield, String keyword, int carlist_status)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarList_DetailVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword!=null&&!"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND name LIKE ?";
				else if(keyfield.equals("2")) sub_sql += " AND detail LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) WHERE carlist_status>? " + sub_sql +
					" ORDER BY carlist_num DESC)a) WHERE rnum >=? AND rnum <=?";
			//PreparedStatrment 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, carlist_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			list = new ArrayList<CarList_DetailVO>();
			while(rs.next()) {
				CarList_DetailVO detail = new CarList_DetailVO();
				detail.setCar_title(rs.getString("car_title"));
				detail.setCar_buyer(rs.getInt("car_buyer"));
				detail.setCar_type(rs.getInt("car_type"));
				detail.setCar_fuel(rs.getInt("car_fuel"));
				detail.setCar_price(rs.getInt("car_price"));
				detail.setCar_model_year(rs.getInt("car_model_year"));
				detail.setCar_distance(rs.getInt("car_distance"));
				detail.setCar_transmission(rs.getInt("car_transmission"));
				detail.setCar_origin(rs.getInt("car_origin"));
				detail.setCar_image(rs.getString("car_image"));
				
				list.add(detail);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return list;
	}
	
	//중고차 수정
	//중고차 삭제
	
	
}





















