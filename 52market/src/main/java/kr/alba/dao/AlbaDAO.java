package kr.alba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		
		sql = "INSERT INTO alba (alba_num,alba_title,alba_content1,alba_content2,alba_photo,alba_ip,"
				+ "mem_num,alba_location,alba_zipcode,abla_address1,alba_address2,alba_filename) "
				+ "VALUES (alba_seq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, alba.getAlba_title());
		pstmt.setString(2, alba.getAlba_content1());
		pstmt.setString(3, alba.getAlba_content2());
		pstmt.setString(4, alba.getAlba_photo());
		pstmt.setString(5, alba.getAlba_ip());
		pstmt.setInt(6, alba.getMem_num());
		pstmt.setString(7, alba.getAlba_location());
		pstmt.setString(8, alba.getAlba_zipcode());
		pstmt.setString(9, alba.getAlba_address1());
		pstmt.setString(10, alba.getAlba_address2());
		pstmt.setString(11, alba.getAlba_filename());
		
		pstmt.executeUpdate();
		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//알바 리스트
	//전체 레코드수/검색 레코드수
	//전체 글/검색 글 목록
	//글 상세
	public AlbaVO getAlba(int abla_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		AlbaVO alba = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM alba JOIN member USING(mem_num) LEFT OUTER JOIN member_detail USING(mem_num) WHERE alba_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, abla_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				alba = new AlbaVO();
				alba.setAlba_filename(rs.getString("alba_filename"));
				alba.setAlba_title(rs.getString("alba_title"));
				alba.setAlba_content1(rs.getString("alba_content1"));
				alba.setAlba_content2(rs.getString("alba_content2"));
				alba.setal
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
	//글 삭제
	//관심(좋아요) 등록
	//관심(좋아요) 개수
	//회원번호와 게시물 번호를 이용한 관심(좋아요) 정보(좋아요 선택 여부)
	//관심(좋아요) 삭제
	//내가 선택한 관심(좋아요) 목록
}
