package kr.car.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_FavVO;
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
			pstmt2.setString(3, detail.getCar_type());
			pstmt2.setString(4, detail.getCar_fuel());
			pstmt2.setInt(5, detail.getCar_price());
			pstmt2.setInt(6, detail.getCar_model_year());
			pstmt2.setInt(7, detail.getCar_distance());
			pstmt2.setString(8, detail.getCar_transmission());
			pstmt2.setString(9, detail.getCar_origin());
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
	//전체 레코드수/검색 레코드수
	public int getCarCount(String keyfield,String keyword,int status,String car_type,String car_fuel,String car_transmission,String car_origin)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		String type = "";
		String fuel = "";
		String transmission = "";
		String origin = "";
		int cnt = 0;
		int count = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
				else if(keyfield.equals("3")) sub_sql += " AND carlist_content LIKE ?";
			}
			//필터
			if(car_type!=null) {
				type += " AND car_type in ? ";
			}
			if(car_fuel!=null) {
				fuel += " AND car_fuel in ? ";
			}
			if(car_transmission!=null) {
				transmission += " AND car_transmission in ? ";
			}
			if(car_origin!=null) {
				origin += " AND car_origin in ? ";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM member m INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num)) a "
					+ " ON m.mem_num = a.car_seller WHERE carlist_status<=?" + sub_sql +type+fuel+transmission+origin;
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			if(car_type!=null) {
				pstmt.setString(++cnt, car_type);
			}
			if(car_fuel!=null) {
				pstmt.setString(++cnt, car_fuel);
			}
			if(car_transmission!=null) {
				pstmt.setString(++cnt, car_transmission);
			}
			if(car_origin!=null) {
				pstmt.setString(++cnt, car_origin);
			}
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return count;
	}
	//중고차 리스트
	public List<CarList_DetailVO> getList(int start, int end, String keyfield, String keyword, int carlist_status,String car_type,String car_fuel,String car_transmission,String car_origin)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarList_DetailVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		String type = "";
		String fuel = "";
		String transmission = "";
		String origin = "";
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();

			if(keyword!=null&&!"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
				else if(keyfield.equals("3")) sub_sql += " AND carlist_content LIKE ?";
			}
			//필터
			if(car_type!=null) {
				type += " AND car_type in ? ";
			}
			if(car_fuel!=null) {
				fuel += " AND car_fuel in ? ";
			}
			if(car_transmission!=null) {
				transmission += " AND car_transmission in ? ";
			}
			if(car_origin!=null) {
				origin += " AND car_origin in ? ";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM member m INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) "
					+ "WHERE carlist_status<=? "+ sub_sql + type + fuel + transmission + origin+" ORDER BY NVL(carlist_modify_date, carlist_reg_date) DESC, carlist_reg_date DESC, carlist_num DESC) b on m.mem_num=b.car_seller )a) WHERE rnum >=? AND rnum <=?";
			//PreparedStatrment 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, carlist_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			if(car_type!=null) {
				pstmt.setString(++cnt, car_type);
			}
			if(car_fuel!=null) {
				pstmt.setString(++cnt, car_fuel);
			}
			if(car_transmission!=null) {
				pstmt.setString(++cnt, car_transmission);
			}
			if(car_origin!=null) {
				pstmt.setString(++cnt, car_origin);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			CarDAO car = CarDAO.getInstance();
			list = new ArrayList<CarList_DetailVO>();
			while(rs.next()) {
				CarList_DetailVO detail = new CarList_DetailVO();
				detail.setCarlist_num(rs.getInt("carlist_num"));
				detail.setCar_title(rs.getString("car_title"));
				detail.setCar_buyer(rs.getInt("car_buyer"));
				detail.setCar_type(rs.getString("car_type"));
				detail.setCar_fuel(rs.getString("car_fuel"));
				detail.setCar_price(rs.getInt("car_price"));
				detail.setCar_model_year(rs.getInt("car_model_year"));
				detail.setCar_distance(rs.getInt("car_distance"));
				detail.setCar_transmission(rs.getString("car_transmission"));
				detail.setCar_origin(rs.getString("car_origin"));
				detail.setCar_image(rs.getString("car_image"));
				detail.setFavcount(car.selectFavCount(rs.getInt("carlist_num")));
				detail.setCarlist_hit(rs.getInt("carlist_hit"));
				detail.setCarlist_status(rs.getInt("carlist_status"));
				
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
				detail.setCar_type(rs.getString("car_type"));
				detail.setCar_fuel(rs.getString("car_fuel"));
				detail.setCar_price(rs.getInt("car_price"));
				detail.setCar_model_year(rs.getInt("car_model_year"));
				detail.setCar_distance(rs.getInt("car_distance"));
				detail.setCar_transmission(rs.getString("car_transmission"));
				detail.setCar_origin(rs.getString("car_origin"));
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
				list.setCarlist_hit(rs.getInt("carlist_hit"));
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
			sql = "SELECT * FROM member INNEF JOIN member_detail USING(mem_num) WHERE mem_num=?";
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
			pstmt.setString(1, detail.getCar_type());
			pstmt.setString(2, detail.getCar_fuel());
			pstmt.setInt(3, detail.getCar_price());
			pstmt.setInt(4, detail.getCar_model_year());
			pstmt.setInt(5, detail.getCar_distance());
			pstmt.setString(6, detail.getCar_transmission());
			pstmt.setString(7, detail.getCar_origin());
			pstmt.setString(8, detail.getCar_image());
			pstmt.setString(9, detail.getCar_title());
			pstmt.setInt(10, carlist_num);
			pstmt.executeUpdate();

			sql = "UPDATE carlist SET carlist_content=?, carlist_modify_date=sysdate WHERE carlist_num=?";
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
	public void deleteCar(int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		PreparedStatement pstmt5 = null;
		ResultSet rs = null;
		String sql = null;
		int status = 0;

		try {
			conn = DBUtil.getConnection();

			conn.setAutoCommit(false);

			//car_fav 삭제
			sql = "DELETE FROM car_fav WHERE carlist_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carlist_num);
			pstmt.executeUpdate();
			//car_map 삭제
			sql = "DELETE FROM car_map WHERE carlist_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, carlist_num);
			pstmt2.executeUpdate();
			//status 불러오기
			sql = "SELECT * FROM carlist WHERE carlist_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, carlist_num);
			rs = pstmt3.executeQuery();
			if(rs.next()) {
				status = rs.getInt("carlist_status");
			}
			//carlist 삭제
			sql = "DELETE FROM carlist WHERE carlist_num=?";
			pstmt4 = conn.prepareStatement(sql);
			pstmt4.setInt(1, carlist_num);
			pstmt4.executeUpdate();
			//status==0일때 carlist_detail 삭제
			if(status==0) {
				sql = "DELETE FROM carlist_detail WHERE carlist_num=?";
				pstmt5 = conn.prepareStatement(sql);
				pstmt5.setInt(1, carlist_num);
				pstmt5.executeUpdate();
			}
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt5, null);
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}

	}
	//중고차 관심 등록
	public void insertCarFav(Car_FavVO carfav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();

			sql = "INSERT INTO car_fav(carlist_num,mem_num) VALUES(?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carfav.getCarlist_num());
			pstmt.setInt(2, carfav.getMem_num());
			pstmt.executeUpdate();



		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//중고차 관심 삭제
	public void deleteCarFav(Car_FavVO carfav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();

			sql = "DELETE FROM car_fav WHERE carlist_num=? AND mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carfav.getCarlist_num());
			pstmt.setInt(2, carfav.getMem_num());
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//좋아요 개수
	public int selectFavCount(int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM car_fav WHERE carlist_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, carlist_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return count;
	}
	//회원번호와 게시물 번호를 이용한 좋아요 정보(좋아요 선택 여부)
	public Car_FavVO selectFav(Car_FavVO carfav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Car_FavVO fav = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM car_fav WHERE carlist_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, carfav.getCarlist_num());
			pstmt.setInt(2, carfav.getMem_num());
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav = new Car_FavVO();
				fav.setCarlist_num(rs.getInt("carlist_num"));
				fav.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return fav;
	}
	//중고차 관심리스트
	public List<CarList_DetailVO> getFavList(int mem_num,int start, int end ,String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarList_DetailVO> list = null;
		String sub_sql = "";
		int cnt = 0;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += " AND carlist_content LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM car_fav f INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) "
					+ " ) b on f.carlist_num=b.carlist_num WHERE mem_num=? "+sub_sql+" ORDER BY regdate DESC)a) WHERE rnum >=? AND rnum <=?";
			//PreparedStatrment 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, mem_num);
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
				detail.setCar_type(rs.getString("car_type"));
				detail.setCar_fuel(rs.getString("car_fuel"));
				detail.setCar_price(rs.getInt("car_price"));
				detail.setCar_model_year(rs.getInt("car_model_year"));
				detail.setCar_distance(rs.getInt("car_distance"));
				detail.setCar_transmission(rs.getString("car_transmission"));
				detail.setCar_origin(rs.getString("car_origin"));
				detail.setCar_image(rs.getString("car_image"));
				detail.setCarlist_modify_date(rs.getDate("carlist_modify_date"));
				
				
				list.add(detail);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}


		return list;
	}
	//중고차 관심리스트 개수
	public int getCarFavListCount( int mem_num, String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		String sub_sql = "";
		int count = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += " AND carlist_content LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT count(*) FROM (SELECT * FROM car_fav f INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) "
					+ " ) b on f.carlist_num=b.carlist_num WHERE mem_num=? "+sub_sql+" )";
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, "%"+keyword+"%");
			}
			rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt(1);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return count;
	}


	//중고차 판매여부 변경
	public void updateCarStatus(int carlist_status, int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE carlist SET carlist_status=? WHERE carlist_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, carlist_status);
			pstmt.setInt(2, carlist_num);
			//SQL문 실행
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}		
	}

	//중고차 판매리스트
	public List<CarList_DetailVO> getSellList(int mem_num,int start, int end ,String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CarList_DetailVO> list = null;
		String sub_sql = "";
		int cnt = 0;
		String sql = null;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += " AND carlist_content LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM member f INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) "
					+ " ) b on f.mem_num=b.car_seller WHERE car_seller=? "+sub_sql+" ORDER BY NVL(carlist_modify_date, carlist_reg_date) DESC, carlist_reg_date DESC, carlist_num DESC)a) WHERE rnum >=? AND rnum <=?";
			//PreparedStatrment 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			rs = pstmt.executeQuery();
			ChatCarDAO dao = ChatCarDAO.getInstance();
			list = new ArrayList<CarList_DetailVO>();
			while(rs.next()) {
				CarList_DetailVO detail = new CarList_DetailVO();
				detail.setCarlist_num(rs.getInt("carlist_num"));
				detail.setCar_title(rs.getString("car_title"));
				detail.setCar_buyer(rs.getInt("car_buyer"));
				detail.setCar_type(rs.getString("car_type"));
				detail.setCar_fuel(rs.getString("car_fuel"));
				detail.setCar_price(rs.getInt("car_price"));
				detail.setCar_model_year(rs.getInt("car_model_year"));
				detail.setCar_distance(rs.getInt("car_distance"));
				detail.setCar_transmission(rs.getString("car_transmission"));
				detail.setCar_origin(rs.getString("car_origin"));
				detail.setCar_image(rs.getString("car_image"));
				detail.setCarlist_modify_date(rs.getDate("carlist_modify_date"));
				detail.setChatcnt(dao.getreadcountSeller(mem_num, detail.getCarlist_num()));

				list.add(detail);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}


		return list;
	}
	//중고차 판매리스트 개수
	public int getSellListCount(int mem_num, String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		String sub_sql = "";
		int count = 0;

		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += " AND carlist_content LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT count(*) FROM (SELECT * FROM member f INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) "
					+ " ) b on f.mem_num=b.car_seller WHERE car_seller=? "+sub_sql+" )";
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, "%"+keyword+"%");
			}
			rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt(1);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return count;
	}
	
	//중고차 판매처리
	public void sellCar(int carlist_num, int car_buyer)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE carlist_detail SET car_buyer=?, car_tradedate=sysdate WHERE carlist_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, car_buyer);
			pstmt.setInt(2, carlist_num);
			pstmt.executeUpdate();
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//조회수 증가
	public void updateReadcount(int carlist_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE carlist SET carlist_hit=carlist_hit+1 WHERE carlist_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, carlist_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//중고차 구매리스트
		public List<CarList_DetailVO> getBuyList(int mem_num,int start, int end ,String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<CarList_DetailVO> list = null;
			String sub_sql = "";
			int cnt = 0;
			String sql = null;

			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND carlist_content LIKE ?";
				}
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM member f INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) "
						+ " ) b on f.mem_num=b.car_buyer WHERE car_buyer=? "+sub_sql+" ORDER BY NVL(carlist_modify_date, carlist_reg_date) DESC, carlist_reg_date DESC, carlist_num DESC)a) WHERE rnum >=? AND rnum <=?";
				//PreparedStatrment 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				pstmt.setInt(++cnt, mem_num);
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
					detail.setCar_type(rs.getString("car_type"));
					detail.setCar_fuel(rs.getString("car_fuel"));
					detail.setCar_price(rs.getInt("car_price"));
					detail.setCar_model_year(rs.getInt("car_model_year"));
					detail.setCar_distance(rs.getInt("car_distance"));
					detail.setCar_transmission(rs.getString("car_transmission"));
					detail.setCar_origin(rs.getString("car_origin"));
					detail.setCar_image(rs.getString("car_image"));
					detail.setCarlist_modify_date(rs.getDate("carlist_modify_date"));

					list.add(detail);
				}

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}


			return list;
		}
		//중고차 구매리스트 개수
		public int getBuyListCount(int mem_num, String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			String sub_sql = "";
			int count = 0;

			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " AND car_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND carlist_content LIKE ?";
				}
				//SQL문 작성
				sql = "SELECT count(*) FROM (SELECT * FROM member f INNER JOIN (SELECT * FROM carlist INNER JOIN carlist_detail USING(carlist_num) "
						+ " ) b on f.mem_num=b.car_buyer WHERE car_buyer=? "+sub_sql+" )";
				//PreparedStatement 객체
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_num);
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(2, "%"+keyword+"%");
				}
				rs = pstmt.executeQuery();

				if(rs.next()) {
					count = rs.getInt(1);
				}

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}		
			return count;
		}
		//중고차 판매여부 변경(chat)
		public void updateCarStatusChat(int chatroom_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;

			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "UPDATE carlist SET carlist_status=1 WHERE carlist_num=?";
				//PreparedStatement 객체 생성
				pstmt = conn.prepareStatement(sql);
				//?에 데이터 바인딩
				ChatCarDAO chat = ChatCarDAO.getInstance();
				pstmt.setInt(1, chat.getCarlistByChatroom(chatroom_num).getCarlist_num());
				//SQL문 실행
				pstmt.executeUpdate();

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}		
		}
}





















