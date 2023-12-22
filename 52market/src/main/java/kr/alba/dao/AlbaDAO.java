package kr.alba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.alba.vo.Alba_FavVO;
import kr.alba.vo.AplistVO;
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
				+ "mem_num,alba_zipcode,alba_address1,alba_address2,alba_location,mem_nickname) "
				+ "VALUES (alba_seq.nextval,?,?,?,?,?,?,?,?,?,?,?)";
		
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
		pstmt.setString(11, alba.getMem_nickname());
		
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
				alba.setApcount(dao.getApListCount(alba.getAlba_num()));
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
			AlbaDAO dao = AlbaDAO.getInstance();
			
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
				alba.setApcount(dao.getApListCount(alba_num));
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
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "DELETE FROM aplist WHERE alba_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, alba_num);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM alba_fav WHERE alba_num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, alba_num);
			pstmt2.executeUpdate();
			
			
			sql = "DELETE FROM alba WHERE alba_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setInt(1, alba_num);
			pstmt3.executeUpdate();
			
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
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
	
	//지원서 등록
	public void insertAlbaAp(AlbaVO albaAp)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO aplist (aplist_num, alba_num, alba_title, mem_num, alba_filename, mem_nickname) VALUES (aplist_seq.nextval,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, albaAp.getAlba_num());
			pstmt.setString(2, albaAp.getAlba_title());
			pstmt.setInt(3, albaAp.getMem_num());
			pstmt.setString(4, albaAp.getAlba_filename());
			pstmt.setString(5, albaAp.getMem_nickname());
			
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
	//지원서 리스트
	public List<AplistVO> ApList(int start, int end, String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AplistVO> list = null;
		String sub_sql = "";
		int cnt = 0;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " WHERE alba_title LIKE ?";
			}
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM aplist JOIN member USING(mem_num) " + sub_sql
					+ "ORDER BY aplist_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<AplistVO>();
			while(rs.next()) {
				AplistVO aplist = new AplistVO();
				aplist.setAplist_num(rs.getInt("aplist_num"));
				aplist.setAlba_title(StringUtil.useBrNoHtml(rs.getString("alba_title")));
				aplist.setAlba_filename(rs.getString("alba_filename"));
				aplist.setMem_nickname(rs.getString("mem_nickname"));
				aplist.setAplist_reg_date(rs.getDate("aplist_reg_date"));

				list.add(aplist);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	//사업자가 확인하는 알바 지원서 목록
	public List<AplistVO> BApDetail(int start,int end, String keyfield, String keyword,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AplistVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " WHERE alba_title LIKE ?";
			}
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM aplist b INNER JOIN (SELECT * FROM alba WHERE mem_num=?) c USING(alba_num) " + sub_sql
					+ "ORDER BY aplist_num DESC)a) WHERE rnum >= ? AND rnum <= ?  ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<AplistVO>();
			while(rs.next()) {
				AplistVO aplist = new AplistVO();
				aplist.setAlba_num(rs.getInt("alba_num"));
				aplist.setAplist_num(rs.getInt("aplist_num"));
				aplist.setAlba_title(StringUtil.useBrNoHtml(rs.getString("alba_title")));
				aplist.setAlba_filename(rs.getString("alba_filename"));
				aplist.setMem_nickname(rs.getString("mem_nickname"));
				aplist.setAplist_reg_date(rs.getDate("aplist_reg_date"));

				list.add(aplist);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	
	public int getBApListCount(String keyfield,String keyword,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " WHERE alba_title LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM aplist INNER JOIN (SELECT * FROM alba WHERE mem_num=?) USING(alba_num)" + sub_sql
					+ "ORDER BY aplist_num DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
				pstmt.setInt(++cnt, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			//SQL문 실행
			rs = pstmt.executeQuery();;
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
	//일반회원이 확인하는 알바 지원서 목록
	public List<AplistVO> UApDetail(int start,int end, String keyfield, String keyword,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<AplistVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM aplist JOIN member USING(mem_num) WHERE mem_num=? ORDER BY aplist_num DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<AplistVO>();
			while(rs.next()) {
				AplistVO aplist = new AplistVO();
				aplist.setAlba_num(rs.getInt("alba_num"));
				aplist.setAplist_num(rs.getInt("aplist_num"));
				aplist.setAlba_title(StringUtil.useBrNoHtml(rs.getString("alba_title")));
				aplist.setAlba_filename(rs.getString("alba_filename"));
				aplist.setMem_nickname(rs.getString("mem_nickname"));
				aplist.setAplist_reg_date(rs.getDate("aplist_reg_date"));

				list.add(aplist);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}

	public int getUApListCount(String keyfield,String keyword,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) sub_sql += " AND alba_title LIKE ?";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM aplist JOIN member USING(mem_num) WHERE mem_num=? " + sub_sql
					+ "ORDER BY aplist_num DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
				pstmt.setInt(++cnt, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
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
	
	//작성글당 지원자 수
	public int getApListCount(int alba_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM aplist WHERE alba_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, alba_num);
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
}