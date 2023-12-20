package kr.car.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.car.vo.Car_ChatVO;
import kr.car.vo.Car_ChatroomVO;
import kr.util.DBUtil;

public class ChatCarDAO {
	private static ChatCarDAO instance = new ChatCarDAO();
	public static ChatCarDAO getInstance() {
		return instance;
	}
	private ChatCarDAO() {};
	
	//채팅방 체크
		public int checkChatRoomCar(int carlist_num, int mem_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			int chatroom_num = 0;
			try {
				conn = DBUtil.getConnection();

				sql = "SELECT chatroom_num FROM car_chatroom WHERE carlist_num = ? AND (seller_num = ? OR buyer_num = ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, carlist_num);
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
			PreparedStatement pstmt2 = null;
			String sql = null;
			ResultSet rs = null;
			int chatroom_num = 0;
			try {
				conn  = DBUtil.getConnection();
				
				conn.setAutoCommit(false);
				
				sql = "SELECT car_chatroom_seq.nextval FROM dual";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					chatroom_num = rs.getInt(1);
				}
				
				sql = "INSERT INTO car_chatroom (chatroom_num,carlist_num,seller_num,buyer_num) "
						+ "VALUES(?,?,?,?)";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, chatroom_num);
				pstmt2.setInt(2, chatroom.getCarlist_num());
				pstmt2.setInt(3, chatroom.getSeller_num());
				pstmt2.setInt(4, chatroom.getBuyer_num());
				pstmt2.executeUpdate();
				
				conn.commit();
			}catch(Exception e) {
				conn.rollback();
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt2, null);
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return chatroom_num;
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
				
				sql = "SELECT * FROM car_chatroom INNER JOIN carlist_detail USING(carlist_num) WHERE buyer_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mem_num);
				
				rs = pstmt.executeQuery();
				list = new ArrayList<Car_ChatroomVO>();
				while(rs.next()) {
					Car_ChatroomVO room = new Car_ChatroomVO();
					room.setChatroom_num(rs.getInt("chatroom_num"));
					room.setCarlist_num(rs.getInt("carlist_num"));
					room.setSeller_num(rs.getInt("seller_num"));
					room.setBuyer_num(rs.getInt("buyer_num"));
					room.setCar_title(rs.getString("car_title"));
					list.add(room);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			return list;
		}
		//채팅 내용 불러오기
		public List<Car_ChatVO> getChatListCar(int chatroom_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			ResultSet rs = null;
			List<Car_ChatVO> list = null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM car_chat WHERE chatroom_num=? ORDER BY reg_date ASC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, chatroom_num);
				rs = pstmt.executeQuery();
				
				list = new ArrayList<Car_ChatVO>();
				while(rs.next()) {
					Car_ChatVO chat = new Car_ChatVO();
					chat.setChat_num(rs.getInt("chat_num"));
					chat.setChatroom_num(rs.getInt("chatroom_num"));
					chat.setMem_num(rs.getInt("mem_num"));
					chat.setMessage(rs.getString("message"));
					chat.setReg_date(rs.getDate("reg_date"));
					chat.setRead_check(rs.getInt("read_check"));
					
					list.add(chat);
				}
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			
			return list;
		}
		
		//판매자 carlist_num 채팅방 받아오기
		public List<Car_ChatroomVO> getChattingListForSellerCar(int carlist_num)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			List<Car_ChatroomVO> list = null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "SELECT * FROM car_chatroom INNER JOIN carlist_detail USING(carlist_num) WHERE carlist_num=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, carlist_num);
				rs = pstmt.executeQuery();
				
				list = new ArrayList<Car_ChatroomVO>();
				while(rs.next()) {
					Car_ChatroomVO room = new Car_ChatroomVO();
					room.setChatroom_num(rs.getInt("chatroom_num"));
					room.setCarlist_num(rs.getInt("carlist_num"));
					room.setSeller_num(rs.getInt("seller_num"));
					room.setBuyer_num(rs.getInt("buyer_num"));
					room.setCar_title(rs.getString("car_title"));
					
					list.add(room);
				}
			
				
			}catch (Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			
			
			return list;
		}
		//채팅 내용 입력
		public void insertChatCar(Car_ChatVO chat)throws Exception{
			Connection conn = null;
			PreparedStatement pstmt = null;
			String sql = null;
			
			try {
				conn = DBUtil.getConnection();
				sql = "INSERT INTO car_chat (chat_num,chatroom_num,mem_num,message) "
						+ "VALUES(car_chat_seq.nextval,?,?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, chat.getChatroom_num());
				pstmt.setInt(2, chat.getMem_num());
				pstmt.setString(3, chat.getMessage());
				
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				throw new Exception(e);
			}finally {
				DBUtil.executeClose(null, pstmt, conn);
			}
		}
}



















