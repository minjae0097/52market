package kr.car.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_MapVO;
import kr.car.vo.CarlistVO;
import kr.member.vo.MemberVO;
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
	public List<CarList_DetailVO> getList(int start, int end, String keyfield, String keyword, int carlist_status)throws Exception{
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
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) WHERE carlist_status>=? " + sub_sql +
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
				detail.setCarlist_num(rs.getInt("carlist_num"));
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
	//중고차 상세 detail
	public CarList_DetailVO getCarList_Detail(int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarList_DetailVO detail = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM  carlist_detail d INNER JOIN member m on d.car_seller=m.mem_num WHERE carlist_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carlist_num);
			rs = pstmt.executeQuery();
			if(rs.next()){
				detail = new CarList_DetailVO();
				detail.setCarlist_num(rs.getInt("carlist_num"));
				detail.setCar_seller(rs.getInt("car_seller"));
				detail.setCar_type(rs.getInt("car_type"));
				detail.setCar_fuel(rs.getInt("car_fuel"));
				detail.setCar_price(rs.getInt("car_price"));
				detail.setCar_model_year(rs.getInt("car_model_year"));
				detail.setCar_distance(rs.getInt("car_distance"));
				detail.setCar_transmission(rs.getInt("car_transmission"));
				detail.setCar_origin(rs.getInt("car_origin"));
				detail.setCar_image(rs.getString("car_image"));
				detail.setCar_tradedate(rs.getDate("car_tradedate"));
				detail.setCar_title(rs.getString("car_title"));
				detail.setMem_nickname(rs.getString("mem_nickname"));
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return detail;
	}
	// 중고차 상세 list
	public CarlistVO getCarList(int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		CarlistVO list = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM carlist WHERE carlist_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carlist_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new CarlistVO();
				list.setCarlist_num(rs.getInt("carlist_num"));
				list.setCarlist_content(rs.getString("carlist_content"));
				list.setCarlist_reg_date(rs.getDate("carlist_reg_date"));
				list.setCarlist_modify_date(rs.getDate("carlist_modify_date"));
				list.setCarlist_status(rs.getInt("carlist_status"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	//중고차 상세 map
	public Car_MapVO getCarMap(int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		Car_MapVO map = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM car_map WHERE carlist_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carlist_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				map = new Car_MapVO();
				map.setLocation_x(rs.getString("location_x"));
				map.setLocation_y(rs.getString("location_y"));
				map.setLocation(rs.getString("location"));
				map.setRoad_address_name(rs.getString("road_address_name"));
				map.setAddress_name(rs.getString("address_name"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return map;
	}
	//회원정보 받아오기
	public MemberVO getCarMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberVO member = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM member_detail WHERE mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_nickname(rs.getString("mem_nickname"));
				member.setMem_photo(rs.getString("mem_photo"));
			}
			
		}catch(Exception e) {
			
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
	//중고차 수정
	public void updateCar(CarlistVO list, CarList_DetailVO detail, Car_MapVO map, int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			
			sql = "UPDATE carlist_detail SET car_type=?,car_fuel=?,car_price=?,"
					+ "car_model_year=?,car_distance=?,car_transmission=?,car_origin=?,car_image=?,car_title=? WHERE carlist_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, detail.getCar_type());
			pstmt.setInt(2, detail.getCar_fuel());
			pstmt.setInt(3, detail.getCar_price());
			pstmt.setInt(4, detail.getCar_model_year());
			pstmt.setInt(5, detail.getCar_distance());
			pstmt.setInt(6, detail.getCar_transmission());
			pstmt.setInt(7, detail.getCar_origin());
			pstmt.setString(8, detail.getCar_image());
			pstmt.setString(9, detail.getCar_title());
			pstmt.setInt(10, carlist_num);
			pstmt.executeUpdate();
			
			sql = "UPDATE carlist SET carlist_content=? WHERE carlist_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, list.getCarlist_content());
			pstmt2.setInt(2, carlist_num);
			pstmt2.executeUpdate();
			
			sql = "UPDATE car_map SET location_x=?,location_y=?,location=?,road_address_name=?,address_name=? WHERE carlist_num=? ";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setString(1, map.getLocation_x());
			pstmt3.setString(2, map.getLocation_y());
			pstmt3.setString(3, map.getLocation());
			pstmt3.setString(4, map.getRoad_address_name());
			pstmt3.setString(5, map.getAddress_name());
			pstmt3.setInt(6, carlist_num);
			pstmt3.executeUpdate();
			
			conn.commit();
			
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt3, conn);
		}
		
	}
	//중고차 삭제
	
	
}





















