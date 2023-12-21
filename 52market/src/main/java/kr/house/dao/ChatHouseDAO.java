package kr.house.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.house.vo.House_ChatVO;
import kr.house.vo.House_ChatroomVO;
import kr.util.DBUtil;

public class ChatHouseDAO {
	private static ChatHouseDAO instance = new ChatHouseDAO();
	public static ChatHouseDAO getInsttance() {
		return instance;
	}
	private ChatHouseDAO() {};
	
	//채팅방 체크(채팅방 유무)
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
	public int insertChatRoomHouse(House_ChatroomVO chatroom)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		ResultSet rs = null;
		int chatroom_num = 0;
		
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "SELECT house_chatroom_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				chatroom_num = rs.getInt(1);
			}
			
			sql = "INSERT INTO house_chatroom (chatroom_num,house_num,seller_num,buyer_num) "
					+ "VALUES(?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, chatroom_num);
			pstmt2.setInt(2, chatroom.getHouse_num());
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
	public List<House_ChatroomVO> getChattingListForBuyerHouse(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		List<House_ChatroomVO> list = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM house_chatroom INNER JOIN house_detail USING(house_num) WHERE buyer_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<House_ChatroomVO>();
			while(rs.next()) {
				House_ChatroomVO room = new House_ChatroomVO();
				room.setChatroom_num(rs.getInt("chatroom_num"));
				room.setHouse_num(rs.getInt("house_num"));
				room.setSeller_num(rs.getInt("seller_num"));
				room.setBuyer_num(rs.getInt("buyer_num"));
				room.setHouse_title(rs.getString("house_title"));
				
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
	public List<House_ChatVO> getChatListHouse(int chatroom_num,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		ResultSet rs = null;
		List<House_ChatVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "UPDATE house_chat SET read_check=0 WHERE chatroom_num=? AND mem_num!=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroom_num);
			pstmt.setInt(2, mem_num);
			pstmt.executeUpdate();
			
			sql = "SELECT * FROM house_chat WHERE chatroom_num=? ORDER BY reg_date ASC";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, chatroom_num);
			
			rs = pstmt2.executeQuery();
			list = new ArrayList<House_ChatVO>();
			while(rs.next()) {
				House_ChatVO chat = new House_ChatVO();
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
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return list;
	}
}
