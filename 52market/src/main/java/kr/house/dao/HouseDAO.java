package kr.house.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import kr.house.vo.HouseDetailVO;
import kr.house.vo.HouseFavVO;
import kr.house.vo.HouseListVO;
import kr.member.vo.MemberVO;
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
					+ "house_cost,house_move_in,mem_num) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, num);
			pstmt2.setString(2, detail.getHouse_title());
			pstmt2.setInt(3, detail.getHouse_seller_type());
			pstmt2.setInt(4, detail.getHouse_type());
			pstmt2.setString(5, detail.getZipcode());
			pstmt2.setString(6, detail.getHouse_address1());
			pstmt2.setString(7, detail.getHouse_address2());
			pstmt2.setInt(8, detail.getHouse_deal_type());
			pstmt2.setLong(9, detail.getHouse_price());
			pstmt2.setInt(10, detail.getHouse_space());
			pstmt2.setInt(11, detail.getHouse_floor());
			pstmt2.setString(12, detail.getHouse_photo1());
			pstmt2.setString(13, detail.getHouse_photo2());
			pstmt2.setLong(14, detail.getHouse_diposit());
			pstmt2.setInt(15, detail.getHouse_cost());
			pstmt2.setInt(16, detail.getHouse_move_in());
			pstmt2.setInt(17, detail.getMem_num());
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
	public int getHouseCount(String keyfield,String keyword,int house_status,int house_seller_type,int house_type,int house_deal_type,int house_move_in)throws Exception{
		//System.out.println("!!!!!!!!!!");
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "AND house_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "AND mem_nickname LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "AND house_content LIKE ?";
			}
			
			//필터
			if(house_seller_type >= 1 && house_seller_type <= 9) {
				sub_sql += " AND house_seller_type = ?";
			}
			if(house_type >= 1 && house_type <= 9) {
				sub_sql += " AND house_type = ?";
			}
			if(house_deal_type >= 1 && house_deal_type <= 9) {
				sub_sql += " AND house_deal_type = ?";
			}
			if(house_move_in >=1 && house_move_in <= 9) {
				sub_sql += " AND house_move_in = ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM member m INNER JOIN (SELECT * FROM houselist INNER "
					+ "JOIN house_detail USING(house_num)) a ON m.mem_num = a.mem_num "
					+ "WHERE house_status<=?" + sub_sql;
			
			//System.out.println("getHouseCount sql : " + sql);
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, house_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			if(house_seller_type >= 1 && house_seller_type <= 9) {
				pstmt.setInt(++cnt, house_seller_type);
			}
			if(house_type >= 1 && house_type <= 9) {
				pstmt.setInt(++cnt, house_type);
			}
			if(house_deal_type >=1 && house_deal_type <= 9) {
				pstmt.setInt(++cnt, house_deal_type);
			}
			if(house_move_in >=1 && house_move_in <= 9) {
				pstmt.setInt(++cnt, house_move_in);
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
	public List<HouseDetailVO> getListHouse(int start,int end,String keyfield,
							String keyword,int house_status,int house_seller_type,int house_type,int house_deal_type,int house_move_in)throws Exception{
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
				if(keyfield.equals("1")) sub_sql += "AND house_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "AND mem_nickname LIKE ?";
				else if(keyfield.equals("3")) sub_sql += "AND house_content LIKE ?";
			}
			//필터
			if(house_seller_type >= 1 && house_seller_type <= 9) {
				//System.out.println("house_seller_type : " + house_seller_type);
				sub_sql += " AND house_seller_type = ?";
			}
			if(house_type >= 1 && house_type <= 9) {
				sub_sql += " AND house_type = ?";
			}
			if(house_deal_type >= 1 && house_deal_type <= 9) {
				sub_sql += " AND house_deal_type = ?";
			}
			if(house_move_in >=1 && house_move_in <= 9) {
				sub_sql += " AND house_move_in = ?";
			}
			
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM "
					+ "member m INNER JOIN (SELECT * FROM houselist INNER JOIN "
					+ "house_detail USING(house_num)) b on m.mem_num=b.mem_num WHERE"
					+ " house_status<=?" + sub_sql +" ORDER BY house_num DESC )a) WHERE rnum >= ? AND rnum <= ?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(++cnt, house_status);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			if(house_seller_type >= 1 && house_seller_type <= 9) {
				pstmt.setInt(++cnt, house_seller_type);
			}
			if(house_type >= 1 && house_type <= 9) {
				pstmt.setInt(++cnt, house_type);
			}
			if(house_deal_type >=1 && house_deal_type <= 9) {
				pstmt.setInt(++cnt, house_deal_type);
			}
			if(house_move_in >=1 && house_move_in <= 9) {
				pstmt.setInt(++cnt, house_move_in);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//SQL문 실행
			rs = pstmt.executeQuery();
			HouseDAO house = HouseDAO.getInstance();
			list = new ArrayList<HouseDetailVO>();
			while(rs.next()) {
				HouseDetailVO detail = new HouseDetailVO();
				detail.setHouse_num(rs.getInt("house_num"));
				//HTML을 허용하지 않음
				detail.setHouse_title(StringUtil.useNoHtml(rs.getString("house_title")));
				detail.setHouse_photo1(rs.getString("house_photo1"));
				detail.setHouse_photo2(rs.getString("house_photo2"));
				detail.setHouse_price(rs.getLong("house_price"));
				detail.setMem_num(rs.getInt("mem_num"));
				detail.setMem_nickname(rs.getString("mem_nickname"));
				detail.setHouse_seller_type(rs.getInt("house_seller_type"));
				detail.setHouse_type(rs.getInt("house_type"));
				detail.setHouse_deal_type(rs.getInt("house_deal_type"));
				detail.setHouse_move_in(rs.getInt("house_move_in"));
				detail.setHouse_status(rs.getInt("house_status"));
				detail.setHit(rs.getInt("hit"));
				detail.setFavcount(house.selectFavCount(rs.getInt("house_num")));
				
				
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
	public HouseDetailVO getHouseDetail(int house_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HouseDetailVO detail = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM houselist JOIN (SELECT * FROM house_detail JOIN "
					+ "member USING(mem_num)) USING(house_num) WHERE house_num=?";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, house_num);
			
			//SQL문 실행 4단계
			rs = pstmt.executeQuery();
			if(rs.next()) {
				detail = new HouseDetailVO();
				detail.setHouse_num(rs.getInt("house_num"));
				detail.setHouse_title(rs.getString("house_title"));
				detail.setHouse_seller_type(rs.getInt("house_seller_type"));
				detail.setHouse_type(rs.getInt("house_type"));
				detail.setHouse_deal_type(rs.getInt("house_deal_type"));
				detail.setHouse_diposit(rs.getLong("house_diposit"));
				detail.setHouse_price(rs.getLong("house_price"));
				detail.setHouse_cost(rs.getInt("house_cost"));
				detail.setZipcode(rs.getString("zipcode"));
				detail.setHouse_address1(rs.getString("house_address1"));
				detail.setHouse_address2(rs.getString("house_address2"));
				detail.setHouse_space(rs.getInt("house_space"));
				detail.setHouse_floor(rs.getInt("house_floor"));
				detail.setHouse_move_in(rs.getInt("house_move_in"));
				detail.setHouse_photo1(rs.getString("house_photo1"));
				detail.setHouse_photo2(rs.getString("house_photo2"));
				detail.setHouse_trade_date(rs.getDate("house_trade_date"));
				detail.setHouse_buyer(rs.getInt("house_buyer"));
				detail.setMem_num(rs.getInt("mem_num"));
				detail.setMem_nickname(rs.getString("mem_nickname"));
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return detail;
	}
	//부동산 상세 list
	public HouseListVO getHouseList(int house_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		HouseListVO list = null;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM houselist WHERE house_num=?";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, house_num);
			
			//SQL문 실행 4단계
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new HouseListVO();
				list.setHouse_num(rs.getInt("house_num"));
				list.setHouse_content(rs.getString("house_content"));
				list.setHouse_modify_date(rs.getDate("house_modify_date"));
				list.setHouse_reg_date(rs.getDate("house_reg_date"));
				list.setHouse_status(rs.getInt("house_status"));
				list.setHit(rs.getInt("hit"));
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//회원정보
	public MemberVO getHouseMember(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		MemberVO member = null;
		
		try {
			//커넥션풀로부터 커넥션 할당 
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM member INNER JOIN member_detail USING(mem_num) WHERE mem_num=?";
			
			//PreparedStatement 객체 생성 
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, mem_num);
			
			//SQL문 실행 
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_nickname(rs.getString("mem_nickname"));
				member.setMem_photo(rs.getString("mem_photo"));
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	//조회수 증가 - 진입시 무조건 +1 증가
	public void updateReadcount (int house_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "UPDATE houselist SET hit=hit+1 WHERE house_num=?";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, house_num);
			
			//SQL문 실행 4단계
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//글 수정
	public void updateHouse(HouseListVO list,HouseDetailVO detail)throws Exception{
		//System.out.println(detail);
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			sql = "UPDATE house_detail SET house_title=?,house_seller_type=?,house_type=?,zipcode=?,"
					+ "house_address1=?,house_address2=?,house_deal_type=?,house_price=?,house_space=?,"
					+ "house_floor=?,house_photo1=?,house_photo2=?,house_move_in=?,house_diposit=? WHERE house_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, detail.getHouse_title());
			pstmt.setInt(2, detail.getHouse_seller_type());
			pstmt.setInt(3, detail.getHouse_type());
			pstmt.setString(4, detail.getZipcode());
			pstmt.setString(5, detail.getHouse_address1());
			pstmt.setString(6, detail.getHouse_address2());
			pstmt.setInt(7, detail.getHouse_deal_type());
			pstmt.setLong(8, detail.getHouse_price());
			pstmt.setInt(9, detail.getHouse_space());
			pstmt.setInt(10, detail.getHouse_floor());
			pstmt.setString(11, detail.getHouse_photo1());
			pstmt.setString(12, detail.getHouse_photo2());
			pstmt.setInt(13, detail.getHouse_move_in());
			pstmt.setLong(14, detail.getHouse_diposit());
			pstmt.setInt(15, detail.getHouse_num());
			pstmt.executeUpdate();

			sql = "UPDATE houselist SET house_content=?,house_modify_date=sysdate WHERE house_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setString(1, list.getHouse_content());
			pstmt2.setInt(2, detail.getHouse_num());
			pstmt2.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//글 삭제
	public void deleteHouse(int house_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		String sql = null;
		int status = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//좋아요 삭제
			sql = "DELETE FROM house_fav WHERE house_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, house_num);
			pstmt.executeUpdate();
			
			//status 불러오기
			sql = "SELECT * FROM houselist WHERE house_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, house_num);
			rs = pstmt2.executeQuery();
			if(rs.next()) {
				status = rs.getInt("house_status");
			}
			//houselist 삭제
			sql = "DELETE FROM houselist WHERE house_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, house_num);
			pstmt3.executeUpdate();
			//status=0일때 house_detail삭제->미판매된것만 삭제
			if(status==0) {
				sql = "DELETE FROM house_detail WHERE house_num=?";
				pstmt4 = conn.prepareStatement(sql);
				pstmt4.setInt(1, house_num);
				pstmt4.executeUpdate();
			}
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt4, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
	
	}
	
	//--------------------
	
	//좋아요 등록
	public void insertHouseFav(HouseFavVO housefav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "INSERT INTO house_fav(house_num,mem_num) VALUES(?,?)";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, housefav.getHouse_num());
			pstmt.setInt(2, housefav.getMem_num());
			
			//SQL문 실행 4단계
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//좋아요 개수
	public int selectFavCount(int house_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM house_fav WHERE house_num=?";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, house_num);			
			
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
	//회원번호와 개시물 번호를 이용한 좋아요 정보(좋아요 선택 여부)
	public HouseFavVO selectFav(HouseFavVO housefav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HouseFavVO fav = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT * FROM house_fav WHERE house_num=? AND mem_num=?";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, housefav.getHouse_num());
			pstmt.setInt(2, housefav.getMem_num());
			
			//SQL문 실행 4단계
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav = new HouseFavVO();
				fav.setHouse_num(rs.getInt("house_num"));
				fav.setMem_num(rs.getInt("mem_num"));
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return fav;
	}
	//좋아요 삭제
	public void deleteHouseFav(HouseFavVO housefav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당 1,2단계
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "DELETE FROM house_fav WHERE house_num=? AND mem_num=?";
			
			//PreparedStatement 객체 생성 3단계
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩
			pstmt.setInt(1, housefav.getHouse_num());
			pstmt.setInt(2, housefav.getMem_num());
			
			//SQL문 실행 4단계
			pstmt.executeUpdate();

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//내가 선택한 좋아요 목록
	//-----------------
	//부동산 관심리스트
		public List<HouseDetailVO> getFavList(int mem_num,int start,int end,String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<HouseDetailVO> list = null;
			String sub_sql = "";
			int cnt = 0;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당
				conn = DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " AND house_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND house_content LIKE ?";
				}
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT *FROM house_fav f "
						+ "INNER JOIN (SELECT * FROM houselist INNER JOIN house_detail "
						+ "USING(house_num) WHERE mem_num=?) b on f.house_num=b.house_num " +sub_sql
						+ " ORDER BY reg_date DESC)a) WHERE rnum >=? AND rnum <=?";
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
				list = new ArrayList<HouseDetailVO>();
				while(rs.next()) {
					HouseDetailVO detail = new HouseDetailVO();
					detail.setHouse_num(rs.getInt("house_num"));
					detail.setHouse_title(rs.getString("house_title"));
					detail.setHouse_seller_type(rs.getInt("house_seller_type"));
					detail.setHouse_type(rs.getInt("house_type"));
					detail.setHouse_deal_type(rs.getInt("house_deal_type"));
					detail.setHouse_diposit(rs.getLong("house_diposit"));
					detail.setHouse_price(rs.getLong("house_price"));
					detail.setHouse_cost(rs.getInt("house_cost"));
					detail.setZipcode(rs.getString("zipcode"));
					detail.setHouse_address1(rs.getString("house_address1"));
					detail.setHouse_address2(rs.getString("house_address2"));
					detail.setHouse_space(rs.getInt("house_space"));
					detail.setHouse_floor(rs.getInt("house_floor"));
					detail.setHouse_move_in(rs.getInt("house_move_in"));
					detail.setHouse_photo1(rs.getString("house_photo1"));
					detail.setHouse_photo2(rs.getString("house_photo2"));
					detail.setHouse_trade_date(rs.getDate("house_trade_date"));
					detail.setHouse_buyer(rs.getInt("house_buyer"));
					detail.setMem_num(rs.getInt("mem_num"));
					detail.setHouse_modify_date(rs.getDate("house_modify_date"));
					
					list.add(detail);
				}
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		//부동산 관심리스트 개수
		public int getHouseFavListCount(int mem_num,String keyfield,String keyword)throws Exception{
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
					if(keyfield.equals("1")) sub_sql += " AND house_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND house_content LIKE ?";
				}
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM (SELECT * FROM house_fav f INNER JOIN (SELECT * FROM houselist "
						+ "INNER JOIN house_detail USING(house_num) WHERE mem_num=?) b on f.house_num=b.house_num" +sub_sql+" )";
				//PreparedStatement 객체
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_num);
				rs = pstmt.executeQuery();
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(2, "%"+keyword+"%");
				}
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
		
		//부동산 판매여부 변경
		public void updateHouseStatus(int house_status,int house_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당 1,2단계
				conn = DBUtil.getConnection();
				
				//SQL문 작성
				sql = "UPDATE houselist SET house_status=? WHERE house_num=?";
				
				//PreparedStatement 객체 생성 3단계
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 바인딩
				pstmt.setInt(1, house_status);
				pstmt.setInt(2, house_num);
				
				//SQL문 실행 4단계
				pstmt.executeUpdate();

			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
			
		}
		//부동산 판매리스트
		public List<HouseDetailVO> getSellList(int mem_num,int start,int end,String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<HouseDetailVO> list = null;
			String sub_sql = "";
			int cnt = 0;
			String sql = null;
			
			try {
				//커넥션풀로부터 커넥션 할당 1,2단계
				conn = DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " AND house_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND house_content LIKE ?";
				}
				
				//SQL문 작성
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM member f INNER JOIN "
						+ "(SELECT * FROM houselist INNER JOIN house_detail USING(house_num) WHERE mem_num=?"
						+ " ) b on f.mem_num=b.mem_num " + sub_sql 
						+ " ORDER BY NVL(house_modify_date,house_reg_date)DESC, house_reg_date DESC, house_num DESC)a) WHERE rnum >=? AND rnum <=?";
				
				//PreparedStatement 객체 생성 3단계
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 바인딩
				pstmt.setInt(++cnt, mem_num);
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				
				//SQL문 실행 4단계
				rs = pstmt.executeQuery();
				list = new ArrayList<HouseDetailVO>();
				while(rs.next()) {
					HouseDetailVO detail = new HouseDetailVO();
					detail.setHouse_num(rs.getInt("house_num"));
					detail.setHouse_title(rs.getString("house_title"));
					detail.setHouse_seller_type(rs.getInt("house_seller_type"));
					detail.setHouse_type(rs.getInt("house_type"));
					detail.setHouse_deal_type(rs.getInt("house_deal_type"));
					detail.setHouse_diposit(rs.getLong("house_diposit"));
					detail.setHouse_price(rs.getLong("house_price"));
					detail.setHouse_cost(rs.getInt("house_cost"));
					detail.setZipcode(rs.getString("zipcode"));
					detail.setHouse_address1(rs.getString("house_address1"));
					detail.setHouse_address2(rs.getString("house_address2"));
					detail.setHouse_space(rs.getInt("house_space"));
					detail.setHouse_floor(rs.getInt("house_floor"));
					detail.setHouse_move_in(rs.getInt("house_move_in"));
					detail.setHouse_photo1(rs.getString("house_photo1"));
					detail.setHouse_photo2(rs.getString("house_photo2"));
					detail.setHouse_trade_date(rs.getDate("house_trade_date"));
					detail.setHouse_buyer(rs.getInt("house_buyer"));
					detail.setMem_num(rs.getInt("mem_num"));
					detail.setHouse_modify_date(rs.getDate("house_modify_date"));
					
					list.add(detail);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		//부동산 판매리스트 개수
		public int getSellListCount(int mem_num, String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				//커넥션풀로부터 커넥션 할당 1,2단계
				conn = DBUtil.getConnection();
				
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " AND house_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND house_content LIKE ?";
				}
				
				//SQL문 작성
				sql = "SELECT COUNT(*) FROM (SELECT * FROM member f INNER JOIN "
						+ "(SELECT * FROM houselist INNER JOIN house_detail USING(house_num) WHERE mem_num=?"
						+ " ) b on f.mem_num=b.mem_num "+sub_sql+" )";
				
				//PreparedStatement 객체 생성 3단계
				pstmt = conn.prepareStatement(sql);
				
				//?에 데이터 바인딩
				pstmt.setInt(1, mem_num);
				
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
		//부동산 판매처리
		public void sellHouse(int house_num,int house_buyer)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "UPDATE house_detail SET house_buyer=?, house_trade_date=sysdate WHERE house_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, house_buyer);
				pstmt.setInt(2, house_num);
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
		//부동산 구매리스트
		public List<HouseDetailVO> getBuyList(int mem_num,int start, int end ,String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<HouseDetailVO> list = null;
			String sub_sql = "";
			int cnt = 0;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " AND house_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND house_content LIKE ?";
				}
				sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM member f INNER JOIN (SELECT * FROM houselist INNER JOIN house_detail USING(house_num) ) "
						+ "b on f.mem_num=b.house_buyer  WHERE house_buyer=? "+sub_sql+"ORDER BY NVL(house_modify_date, house_reg_date) DESC, house_reg_date DESC, house_num DESC)a) WHERE rnum >=? AND rnum <=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(++cnt, mem_num);
				if(keyword != null && !"".equals(keyword)) {
					pstmt.setString(++cnt, "%"+keyword+"%");
				}
				pstmt.setInt(++cnt, start);
				pstmt.setInt(++cnt, end);
				rs = pstmt.executeQuery();
				list = new ArrayList<HouseDetailVO>();
				while(rs.next()) {
					HouseDetailVO detail = new HouseDetailVO();
					detail.setHouse_num(rs.getInt("house_num"));
					detail.setHouse_title(rs.getString("house_title"));
					detail.setMem_num(rs.getInt("mem_num"));
					detail.setHouse_buyer(rs.getInt("house_buyer"));
					detail.setZipcode(rs.getString("zipcode"));
					detail.setHouse_address1(rs.getString("house_address1"));
					detail.setHouse_address2(rs.getString("house_address2"));
					detail.setHouse_cost(rs.getInt("house_cost"));
					detail.setHouse_deal_type(rs.getInt("house_deal_type"));
					detail.setHouse_diposit(rs.getInt("house_diposit"));
					detail.setHouse_floor(rs.getInt("house_floor"));
					detail.setHouse_modify_date(rs.getDate("house_modify_date"));
					detail.setHouse_move_in(rs.getInt("house_move_in"));
					detail.setHouse_photo1(rs.getString("house_photo1"));
					detail.setHouse_photo2(rs.getString("house_photo2"));
					detail.setHouse_price(rs.getInt("house_price"));
					detail.setHouse_seller_type(rs.getInt("house_seller_type"));
					detail.setHouse_space(rs.getInt("house_space"));
					detail.setHouse_type(rs.getInt("house_type"));
					detail.setHouse_seller_type(rs.getInt("house_seller_type"));
					
					list.add(detail);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
					
			return list;
		}
		//부동산 구매리스트 개수
		public int getBuyListCount(int mem_num, String keyfield,String keyword)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			String sub_sql = "";
			int count = 0;
			
			try {
				conn = DBUtil.getConnection();
				if(keyword!=null && !"".equals(keyword)) {
					//검색 처리
					if(keyfield.equals("1")) sub_sql += " AND house_title LIKE ?";
					else if(keyfield.equals("2")) sub_sql += " AND house_content LIKE ?";
				}
				sql = "SELECT count(*) FROM (SELECT * FROM member f INNER JOIN (SELECT * FROM houselist INNER JOIN house_detail USING(house_num) ) "
						+ "b on f.mem_num=b.house_buyer WHERE house_buyer=? "+sub_sql+")";
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
}