package kr.car.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.car.vo.Car_ChatroomVO;
import kr.util.DBUtil;

public class ChatCarDAO {
	private static ChatCarDAO instance = new ChatCarDAO();
	public static ChatCarDAO getInstance() {
		return instance;
	}
	private ChatCarDAO() {};
	
	//채팅방 체크
		public int checkChatRoomCar(int used_num, int mem_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int chatroom_num = 0;
			try {
				conn = DBUtil.getConnection();

				sql = "SELECT chatroom_num FROM zchatroom WHERE used_num = ? AND (seller_num = ? OR buyer_num = ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, used_num);
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
		//채팅방 생성
		public int insertChatRoomCar(Car_ChatroomVO chatroom)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			int cn = 0;
			try {
				conn  = DBUtil.getConnection();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return cn;
		}
}
