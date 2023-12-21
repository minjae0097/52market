package kr.house.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class ChatHouseDAO {
	private static ChatHouseDAO instance = new ChatHouseDAO();
	public static ChatHouseDAO getInsttance() {
		return instance;
	}
	private ChatHouseDAO() {};
	
	//채팅방 체크
	public int checkChatRoomHouse(int house_num,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int chatroom_num = 0;
		
		try {
			conn = DBUtil.getConnection();

			sql = "SELECT chatroom_num FROM house_chatroom WHERE house_num = ? "
					+ "AND (seller_num = ? OR buyer_num = ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, house_num);
			pstmt.setInt(2, mem_num);
			pstmt.setInt(3, mem_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				chatroom_num = rs.getInt("chatroom_num");
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return chatroom_num;
	}
}
