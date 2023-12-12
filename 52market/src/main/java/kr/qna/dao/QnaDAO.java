package kr.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
	
	
	//문의글 삭제
	
	//전체,검색 레코드 수
	//전체,검색 글 목록
	
	//답변 등록
}
