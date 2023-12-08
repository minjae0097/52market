package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	//ID 중복 체크 및 로그인 처리
	
	public MemberVO checkMember(String id)throws Exception{
		MemberVO member = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM member LEFT OUTER JOIN member_detail USING(mem_num) WHERE mem_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getInt("mem_num"));
				member.setMem_id(rs.getString(("mem_id")));
				member.setMem_auth(rs.getInt("mem_auth"));
				member.setMem_passwd(rs.getString("mem_passwd"));
				member.setMem_photo(rs.getString("mem_photo"));
				member.setMem_email(rs.getString("mem_email"));
			}
			
			
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return member;
	}
}
