package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.qna.vo.QnaVO;
import kr.util.DBUtil;

public class QnaDAO {
	private static QnaDAO instance = new QnaDAO();
	
	public static QnaDAO getInstance() {
		return instance;
	}
	private QnaDAO() {}
	
	//문의글 등록
	public void insertQna(QnaVO qna)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO qna (qna_num,mem_num,qna_title,question_content,question_filename)"
					+ " VALUES (qna_seq.nextval,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna.getMem_num());
			pstmt.setString(2, qna.getQna_title());
			pstmt.setString(3, qna.getQuestion_content());
			pstmt.setString(4, qna.getQuestion_filename());
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	//전체 레코드수/검색 레코드 수
	public int getQnaCount(String keyfield,String keyword, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "AND qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "AND question_content LIKE ?";
			}
			
			sql = "SELECT COUNT(*) FROM qna JOIN member USING(mem_num) WHERE mem_num=? " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			if(keyword!=null && !"".equals(keyword)) {
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
	
	
	//전체,검색 글 목록
	public List<QnaVO> getListQna(int start,int end,String keyfield, String keyword ,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "and qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "and question_content LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM qna JOIN member USING(mem_num) WHERE mem_num=? " + sub_sql
					+ " ORDER BY qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, mem_num);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<QnaVO>();
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(rs.getString("qna_title"));
				qna.setAsk_content(rs.getString("ask_content"));
				qna.setMem_num(rs.getInt("mem_num"));
				qna.setMem_nickname(rs.getString("mem_nickname"));
				qna.setQuestion_regdate(rs.getDate("question_regdate"));
				qna.setAsk_regdate(rs.getDate("ask_regdate"));
				
				list.add(qna);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	
	
	
	
	//관리자 adminlist
	//전체 레코드수/검색 레코드 수
	public int getAdminQnaCount(String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE question_content LIKE ?";
			}
			
			sql = "SELECT COUNT(*) FROM qna JOIN member USING(mem_num) " + sub_sql;
			
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(1, "%"+keyword+"%");
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
	
	
	//전체,검색 글 목록
	public List<QnaVO> getAdminListQna(int start,int end,String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword!=null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE qna_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE question_content LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM "
					+ "(SELECT * FROM qna JOIN member USING(mem_num) " + sub_sql
					+ " ORDER BY qna_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			pstmt = conn.prepareStatement(sql);
			if(keyword!=null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<QnaVO>();
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(rs.getString("qna_title"));
				qna.setAsk_content(rs.getString("ask_content"));
				qna.setMem_num(rs.getInt("mem_num"));
				qna.setMem_nickname(rs.getString("mem_nickname"));
				qna.setQuestion_regdate(rs.getDate("question_regdate"));
				qna.setAsk_regdate(rs.getDate("ask_regdate"));
				
				list.add(qna);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	
	//문의글 상세
	public QnaVO getQna(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		QnaVO qna = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM qna JOIN member USING(mem_num) "
					+ "LEFT OUTER JOIN member_detail USING(mem_num) "
					+ "WHERE qna_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(rs.getString("qna_title"));
				qna.setQuestion_content(rs.getString("question_content"));
				qna.setQuestion_filename(rs.getString("question_filename"));
				qna.setQuestion_regdate(rs.getDate("question_regdate"));
				qna.setMem_num(rs.getInt("mem_num"));
				qna.setAsk_content(rs.getString("ask_content"));
				qna.setAsk_regdate(rs.getDate("ask_regdate"));
				qna.setMem_nickname(rs.getString("mem_nickname"));
				qna.setMem_photo(rs.getString("mem_photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return qna;
	}
	
	
	//문의글 삭제
	public void deleteQna(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM qna WHERE qna_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//답변 등록 (update)
	public void updateAsk(QnaVO qna)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "UPDATE qna SET ask_content=?,ask_regdate=SYSDATE WHERE qna_num=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, qna.getAsk_content());
			pstmt.setInt(2, qna.getQna_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//개별 문의글 목록
	public List<QnaVO> getListQnaDetail(int qna_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<QnaVO> list = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM qna WHERE qna_num=? ORDER BY question_regdate DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qna_num);
			rs = pstmt.executeQuery();
			list = new ArrayList<QnaVO>();
			while(rs.next()) {
				QnaVO qna = new QnaVO();
				qna.setQna_num(rs.getInt("qna_num"));
				qna.setQna_title(rs.getString("qna_title"));
				qna.setQuestion_content(rs.getString("question_content"));
				qna.setQuestion_filename(rs.getString("question_filename"));
				qna.setQuestion_regdate(rs.getDate("question_regdate"));
				qna.setAsk_content(rs.getString("ask_content"));
				qna.setAsk_regdate(rs.getDate("ask_regdate"));
				
				list.add(qna);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
}
