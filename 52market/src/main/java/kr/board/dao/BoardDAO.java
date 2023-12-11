package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private BoardDAO() {}
	
	//[[[[[게시글 등록]]]]]
	//글 등록
	public void insertBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO board (board_num,board_title,board_content,board_filename,board_ip,mem_num) "
					+ "VALUES (board_seq.nextval,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getBoard_title());
			pstmt.setString(2, board.getBoard_content());
			pstmt.setString(3, board.getBoard_filename());
			pstmt.setString(4, board.getBoard_ip());
			pstmt.setInt(5, board.getMem_num());
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//글 상세
	public BoardVO getBoard(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO board = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM board JOIN member USING(mem_num) LEFT OUTER JOIN member_detail USING(mem_num) WHERE board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_title(rs.getString("board_title"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_hit(rs.getInt("board_hit"));
				board.setBoard_regdate(rs.getDate("board_regdate"));
				board.setBoard_modifydate(rs.getDate("board_modifydate"));
				board.setBoard_filename(rs.getString("board_filename"));
				board.setMem_num(rs.getInt("mem_num"));
				board.setMem_id(rs.getString("mem_id"));
				board.setMem_photo(rs.getString("mem_photo"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return board;
	}

	//글 수정
	//글 삭제
	//전체&검색 레코드 수
	public int getBoardCount(String keyfield,String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			conn = DBUtil.getConnection();
			if(keyword!=null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) {
					sub_sql += "WHERE board_title LIKE ?";
				}else if(keyfield.equals("2")) {
					sub_sql += "WHERE mem_id LIKE ?";
				}else if(keyfield.equals("3")) {
					sub_sql += "WHERE board_content LIKE ?";
				}
			}
			
			sql = "SELECT COUNT(*) FROM board JOIN member USING(mem_num) " + sub_sql;
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
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
	
	//전체&검색 글 목록
	public List<BoardVO> getListBoard(int start,int end,String keyfield,String keyword) throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs= null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				if(keyfield.equals("1")) sub_sql += "WHERE board_title LIKE ?";
				else if(keyfield.equals("2")) sub_sql += "WHERE mem_id LIKE ?";
				else if (keyfield.equals("3")) sub_sql += "WHERE board_content LIKE ?";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM board JOIN member USING(mem_num) " 
				  +sub_sql+" ORDER BY board_num DESC)a) WHERE rnum>=? AND rnum <=?"; 
			
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, "%"+keyword+"%");
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getInt("board_num"));
				board.setBoard_title(StringUtil.useNoHtml(rs.getString("board_title")));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_hit(rs.getInt("board_hit"));
				board.setBoard_regdate(rs.getDate("board_regdate"));
				board.setMem_id(rs.getString("mem_id"));
				
				list.add(board);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
	//파일 삭제
	
	//[[[[[조회수]]]]]
	//조회수 증가
	public void updateReadCount(int board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "UPDATE board SET board_hit=board_hit+1 WHERE board_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	//[[[[[좋아요]]]]]
	//좋아요 등록
	//좋아요 개수
	//좋아요 삭제
	//내가 선택한 좋아요 목록
	//회원번호와 게시물 번호를 이용한 좋아요 정보(좋아요 선택 여부)
	
	//[[[[[댓글]]]]]
	//댓글 등록
	//댓글 개수
	//댓글 목록
	//댓글 상세
	//댓글 수정
	//댓글 삭제
}
