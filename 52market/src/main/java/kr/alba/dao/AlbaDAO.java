package kr.alba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.alba.vo.Alba_FavVO;
import kr.alba.vo.AlbaVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

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
	
	//전체 레코드수/검색 레코드수
	public int getAlbaCount(String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += " WHERE alba_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql +=" WHERE (alba_content2 LIKE ? or alba_content1 LIKE ?)";
				else if(keyfield.equals("3")) sub_sql +=" WHERE (alba_title LIKE ? or alba_content2 LIKE ? or alba_content1 LIKE ?)";
			}
			
			sql = "SELECT COUNT (*) FROM alba JOIN member USING(mem_num) " + sub_sql;
			//PreparedStatement 객체
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)&&keyfield.equals("1")) {
				pstmt.setString(1, "%"+keyword+"%");
			}
			if(keyword != null && !"".equals(keyword)&&keyfield.equals("2")) {
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, "%"+keyword+"%");
			}
			if(keyword != null && !"".equals(keyword)&&keyfield.equals("3")) {
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
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
	//전체 글/검색 글 목록
	public List<AlbaVO> getListAlba(int start, int end, String keyfield, String keyword)throws Exception{
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
				//검색 처리
				if(keyfield.equals("1")) sub_sql += "WHERE alba_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE (alba_content2 LIKE ? or alba_content1 LIKE ?)";
				else if(keyfield.equals("3")) sub_sql +=" WHERE (alba_title LIKE ? or alba_content2 LIKE ? or alba_content1 LIKE ?)";
			}
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM alba JOIN member USING(mem_num) " +sub_sql
					+ "ORDER BY alba_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			if(keyword != null && !"".equals(keyword)&&keyfield.equals("1")) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			if(keyword != null && !"".equals(keyword)&&keyfield.equals("2")) {
				pstmt.setString(++cnt, "%"+keyword+"%");
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			if(keyword != null && !"".equals(keyword)&&keyfield.equals("3")) {
				pstmt.setString(++cnt, "%"+keyword+"%");
				pstmt.setString(++cnt, "%"+keyword+"%");
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			AlbaDAO dao = AlbaDAO.instance;
			list = new ArrayList<AlbaVO>();
			while(rs.next()) {
				AlbaVO alba = new AlbaVO();
				alba.setAlba_num(rs.getInt("alba_num"));
				//HTML을 허용하지 않음
				alba.setAlba_title(StringUtil.useBrNoHtml(rs.getString("alba_title")));
				alba.setAlba_hit(rs.getInt("alba_hit"));
				alba.setAlba_fav(rs.getInt("alba_fav"));
				alba.setAlba_content1("alba_content1");
				alba.setAlba_photo(rs.getString("alba_photo"));
				alba.setAlba_address1(rs.getString("alba_address1"));
				alba.setAlba_fav(dao.selectFavCount(alba.getAlba_num()));
				
				list.add(alba);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
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
				alba.setAlba_num(rs.getInt("alba_num"));
				alba.setAlba_photo(rs.getString("alba_photo"));
				alba.setAlba_hit(rs.getInt("alba_hit"));
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
				alba.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return alba;
	}
	//조회수 증가
	public void updateReadcount(int alba_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE alba SET alba_hit=alba_hit+1 WHERE alba_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, alba_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
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
					+ "alba_modify_date=SYSDATE,alba_ip=?,alba_zipcode=?,alba_address1=?,alba_address2=?"
					+ sub_sql + "WHERE alba_num=?";
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
	public void deleteAlba(int alba_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "DELETE FROM alba WHERE alba_num=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, alba_num);
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	//관심(좋아요) 등록
	public void insertAlbaFav(Alba_FavVO albafav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO alba_fav(alba_num,mem_num) VALUES(?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, albafav.getAlba_num());
			pstmt.setInt(2, albafav.getMem_num());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//관심(좋아요) 개수
	public int selectFavCount(int alba_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM alba_fav WHERE alba_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, alba_num);
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
	//회원번호와 게시물 번호를 이용한 관심(좋아요) 정보(좋아요 선택 여부)
	public Alba_FavVO selectFav(Alba_FavVO albafav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Alba_FavVO fav = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM alba_fav WHERE alba_num=? AND mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, albafav.getAlba_num());
			pstmt.setInt(2, albafav.getMem_num());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				fav = new Alba_FavVO();
				fav.setAlba_num(rs.getInt("alba_num"));
				fav.setMem_num(rs.getInt("mem_num"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return fav;
	}
	//관심(좋아요) 삭제
	public void deleteAlbaFav(Alba_FavVO albafav)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM alba_fav WHERE alba_num=? AND mem_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, albafav.getAlba_num());
			pstmt.setInt(2, albafav.getMem_num());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
