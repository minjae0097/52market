package kr.car.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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

				sql = "SELECT chatroom_num FROM car_chatroom WHERE used_num = ? AND (seller_num = ? OR buyer_num = ?)";

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
				
				sql = "INSERT INTO car_chatroom (chatroom_num,carlist_num,seller_num,buyer_num) "
						+ "VALUES(car_chatroom_seq.nextval,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, chatroom.getCarlist_num());
				pstmt.setInt(2, chatroom.getSeller_num());
				pstmt.setInt(3, chatroom.getBuyer_num());
				
				rs=pstmt.executeQuery();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return cn;
		}
		//구매자 채팅방 입장
		public List<Car_ChatroomVO>  getChattingListForBuyerCar(int mem_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			List<Car_ChatroomVO> list = null;
			ResultSet rs = null;
			
			try {
				conn = DBUtil.getConnection();
				
				sql = "SELECT * FROM car_chatroom WHERE buyer_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_num);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<Car_ChatroomVO>();
				while(rs.next()) {
					Car_ChatroomVO room = new Car_ChatroomVO();
					room.setCarlist_num(rs.getInt("chatroom_num"));
					room.setCarlist_num(rs.getInt("carlist_num"));
					room.setSeller_num(rs.getInt("seller_num"));
					room.setBuyer_num(rs.getInt("buyer_num"));
					
					list.add(room);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
}
