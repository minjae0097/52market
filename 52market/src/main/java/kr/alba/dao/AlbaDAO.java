package kr.alba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.alba.vo.AlbaVO;
import kr.util.DBUtil;

public class AlbaDAO {
	private static AlbaDAO instance = new AlbaDAO();
	
	public static AlbaDAO getInstance() {
		return instance;
	}
	
	private AlbaDAO() {}
	
	//글등록
	public void insertAlba(AlbaVO alba)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
		conn = DBUtil.getConnection();
		
		sql = "INSERT INTO alba (alba_num,alba_photo,alba_title,alba_content1,alba_content2,alba_ip,"
				+ "mem_num,alba_zipcode,alba_address1,alba_address2,alba_location) "
				+ "VALUES (alba_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, alba.getAlba_photo());
		pstmt.setString(2, alba.getAlba_title());
		pstmt.setString(3, alba.getAlba_content1());
		pstmt.setString(4, alba.getAlba_content2());
		pstmt.setString(5, alba.getAlba_ip());
		pstmt.setInt(6, alba.getMem_num());
		pstmt.setString(7, alba.getAlba_zipcode());
		pstmt.setString(8, alba.getAlba_address1());
		pstmt.setString(9, alba.getAlba_address2());
		pstmt.setString(10, alba.getAlba_location());
		
		pstmt.executeUpdate();
		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//알바 리스트
	public List<AlbaVO> getList(int start,int end, String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AlbaVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += " AND title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += " AND detail LIKE ?";
			}
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM alba JOIN member USING(mem_num) "
					+ sub_sql + "ORDER BY alba_num DESC)a) WHERE rnum >=? AND rnum <=?";
			
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<AlbaVO>();
			while(rs.next()) {
				AlbaVO albaVO = new AlbaVO();
				albaVO.setAlba_photo(rs.getString("alba_photo"));
				albaVO.setAlba_title(rs.getString("alba_title"));
				albaVO.setAlba_content1(rs.getString("alba_content1"));
				albaVO.setAlba_address1(rs.getString("alba_address1"));
				albaVO.setAlba_reg_date(rs.getDate("alba_reg_date"));
				albaVO.setAlba_num(rs.getInt("alba_num"));
				
				list.add(albaVO);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	//전체 레코드수/검색 레코드수
	//전체 글/검색 글 목록
	//글 상세
	public AlbaVO getAlba(int alba_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AlbaVO alba = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM alba a INNER JOIN member m on a.mem_num = m.mem_num WHERE alba_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, alba_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				alba = new AlbaVO();
				alba.setAlba_photo(rs.getString("alba_photo"));
				alba.setAlba_title(rs.getString("alba_title"));
				alba.setAlba_content1(rs.getString("alba_content1"));
				alba.setAlba_content2(rs.getString("alba_content2"));
				alba.setAlba_zipcode(rs.getString("alba_zipcode"));
				alba.setAlba_address1(rs.getString("alba_address1"));
				alba.setAlba_address2(rs.getString("alba_address2"));
				alba.setAlba_location(rs.getString("alba_location"));
				alba.setAlba_reg_date(rs.getDate("alba_reg_date"));
				alba.setAlba_modify_date(rs.getDate("alba_modify_date"));
				alba.setMem_nickname(rs.getString("mem_nickname"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return alba;
	}
	//조회수 증가
	//파일 삭제
	//글 수정
	public void updateAlba(AlbaVO alba)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
			
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(alba.getAlba_photo()!= null) {
				sub_sql += ",alba_photo=?";
			}
			if(alba.getAlba_location() != null) {
				sub_sql += ",alba_location=?";
			}
			//SQL문 작성
			sql = "UPDATE alba SET alba_title=?,alba_content1=?,alba_content2=?,"
					+ "alba_modify_date=SYSDATE,alba_ip=?,alba_zipcode=?,alba_address1=?,alba_address2=?,"
					+ "alba_location=?" + sub_sql + "WHERE alba_num=2";
			//PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, alba.getAlba_title());
			pstmt.setString(++cnt, alba.getAlba_content1());
			pstmt.setString(++cnt, alba.getAlba_content2());
			pstmt.setString(++cnt, alba.getAlba_ip());
			pstmt.setString(++cnt, alba.getAlba_zipcode());
			pstmt.setString(++cnt, alba.getAlba_address1());
			pstmt.setString(++cnt, alba.getAlba_address2());
			pstmt.setString(++cnt, alba.getAlba_location());
			if(alba.getAlba_photo() != null) {
				pstmt.setString(++cnt, alba.getAlba_photo());
			}
			if(alba.getAlba_location() != null) {
				pstmt.setString(++cnt, alba.getAlba_location());
			}
			pstmt.setInt(++cnt, alba.getAlba_num());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//글 삭제
	
	//관심(좋아요) 등록
	//관심(좋아요) 개수
	//회원번호와 게시물 번호를 이용한 관심(좋아요) 정보(좋아요 선택 여부)
	//관심(좋아요) 삭제
	//내가 선택한 관심(좋아요) 목록
}
