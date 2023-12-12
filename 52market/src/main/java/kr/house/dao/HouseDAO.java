package kr.house.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.house.vo.HouseDetailVO;
import kr.house.vo.HouseListVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class HouseDAO {
	//싱글턴 패턴
	private static HouseDAO instance = new HouseDAO();
	public static HouseDAO getInstance() {
		return instance;
	}
	private HouseDAO() {}
	
	//부동산 글쓰기
	public void insertHouse(HouseListVO list, HouseDetailVO detail)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		int num = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			//SQL문 작성
			sql = "SELECT house_detail_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				num = rs.getInt(1);
			}
			
			sql="INSERT INTO house_detail (house_num,house_title,house_seller_type,house_type,"
					+ "zipcode,house_address1,house_address2,house_deal_type,house_price,"
					+ "house_space,house_floor,house_photo1,house_photo2,house_diposit,"
					+ "house_cost,house_move_in) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, detail.getHouse_title());
			pstmt2.setInt(3, detail.getHouse_seller_type());
			pstmt2.setInt(4, detail.getHouse_type());
			pstmt2.setString(5, detail.getZipcode());
			pstmt2.setString(6, detail.getHouse_address1());
			pstmt2.setString(7, detail.getHouse_address2());
			pstmt2.setInt(8, detail.getHouse_deal_type());
			pstmt2.setInt(9, detail.getHouse_price());
			pstmt2.setInt(10, detail.getHouse_space());
			pstmt2.setInt(11, detail.getHouse_floor());
			pstmt2.setString(12, detail.getHouse_photo1());
			pstmt2.setString(13, detail.getHouse_photo2());
			pstmt2.setInt(14, detail.getHouse_diposit());
			pstmt2.setInt(15, detail.getHouse_cost());
			pstmt2.setInt(16, detail.getHouse_move_in());
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO houselist (house_num,house_content) VALUES(?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, list.getHouse_content());
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
	//전체 레코드수/검색 레코드수
	public int getHouseCount(String keyfield,String keyword,int house_status)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "AND name LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "AND detail LIKE ?";
			}
			
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM houselist JOIN house_detail USING(house_num)WHERE house_status >= ? " + sub_sql;
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, house_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, "%"+keyword+"%");
			}
			
			//SQL문 실행 4단계
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
	//전체 글/검색 글 목록
	public List<HouseDetailVO> getListHouse(int start,int end,String keyfield,String keyword,int house_status)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<HouseDetailVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커네션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "AND name LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "AND detail LIKE ?";
			}
			
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM "
					+ "houselist JOIN house_detail USING(house_num) WHERE house_status >= ? " + sub_sql
					+ " ORDER BY house_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, house_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<HouseDetailVO>();
			while(rs.next()) {
				HouseDetailVO detail = new HouseDetailVO();
				detail.setHouse_num(rs.getInt("house_num"));
				//HTML을 허용하지 않음
				detail.setHouse_title(StringUtil.useNoHtml(rs.getString("house_title")));
				detail.setHit(rs.getInt("hit"));
				detail.setHouse_photo1(rs.getString("house_photo1"));
				detail.setHouse_photo2(rs.getString("house_photo2"));
				detail.setHouse_price(rs.getInt("house_price"));
				detail.setMem_num(rs.getInt("mem_num"));
				
				
				list.add(detail);
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	//부동산 상세 detail
	public HouseDetailVO getHouseList_Detail(int house_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HouseDetailVO detail = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM house_detail INNER JOIN member USING(mem_num) WHERE house_num=?";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, house_num);
			
			//SQL문 실행 4단계
			rs = pstmt.executeQuery();
			if(rs.next()) {
				detail = new HouseDetailVO();
				detail.setHouse_num(rs.getInt("house_num"));
				detail.setMem_num(rs.getInt("mem_num"));
				detail.setHouse_title(rs.getString("house_title"));
				detail.setHouse_photo1(rs.getString("house_photo1"));
				detail.setHouse_trade_date(rs.getDate("house_trade_date"));
				detail.setHouse_price(rs.getInt("house_price"));
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return detail;
	}
	//조회수 증가 - 진입시 무조건 +1 증가
	//글 수정
	//글 삭제
	
	//--------------------
	
	//좋아요 등록
	//좋아요 개수
	//회원번호와 개시물 번호를 이용한 좋아요 정보(좋아요 선택 여부)
	//좋아요 삭제
	//내가 선택한 좋아요 목록
	
}