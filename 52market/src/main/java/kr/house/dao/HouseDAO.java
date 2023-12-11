package kr.house.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.house.vo.HouseDetailVO;
import kr.house.vo.HouseListVO;
import kr.util.DBUtil;

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
			
			sql="INSERT INTO housedetail (house_num,house_title,house_seller_type,house_type,"
					+ "zipcode,house_address1,house_address2,house_deal_type,house_price,"
					+ "house_space,house_floor,house_photo1,house_photo2) VALUES ("
					+ "?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			pstmt2.executeUpdate();
			
			sql = "INSERT INTO houselist (house_num,house_content) VALUES(?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, num);
			pstmt3.setString(2, list.getHouse_content());
			pstmt3.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt3, conn);
		}
	}
	//전체 레코드수/검색 레코드수
	//전체 글/검색 글 목록
	//글 상세
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