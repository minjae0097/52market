package kr.house.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.house.vo.HouseDetailVO;
import kr.house.vo.House_ChatVO;
import kr.house.vo.House_ChatroomVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

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
	//채팅방 생성
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
			
			sql = "SELECT * FROM member b JOIN (select * FROM house_chatroom INNER JOIN house_detail USING(house_num))a ON a.mem_num=b.mem_num WHERE buyer_num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			ChatHouseDAO dao = ChatHouseDAO.getInsttance();
			rs = pstmt.executeQuery();
			list = new ArrayList<House_ChatroomVO>();
			while(rs.next()) {
				House_ChatroomVO room = new House_ChatroomVO();
				room.setChatroom_num(rs.getInt("chatroom_num"));
				room.setHouse_num(rs.getInt("house_num"));
				room.setSeller_num(rs.getInt("seller_num"));
				room.setBuyer_num(rs.getInt("buyer_num"));
				room.setCnt(dao.getreadcountSeller(mem_num, room.getChatroom_num()));
				room.setHouse_title(rs.getString("house_title"));
				room.setMem_nickname(rs.getString("mem_nickname"));
				
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
				chat.setMessage(StringUtil.useBrHtml(rs.getString("message")));
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
	
	//판매자 house_num 채팅방 받아오기
	public List<House_ChatroomVO> getChattingListForSellerHouse(int house_num,int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<House_ChatroomVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM house_chatroom c JOIN (SELECT chatroom_num FROM house_chat group by chatroom_num) "
					+ " USING(chatroom_num) LEFT OUTER JOIN (SELECT COUNT(*) cnt, chatroom_num FROM house_chat WHERE read_check=1 AND mem_num!=? "
					+ " GROUP BY chatroom_num) USING(chatroom_num) "
					+ "JOIN member m ON c.buyer_num=m.mem_num WHERE house_num=? ORDER BY cnt DESC NULLS LAST";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, house_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<House_ChatroomVO>();
			while(rs.next()) {
				House_ChatroomVO room = new House_ChatroomVO();
				room.setChatroom_num(rs.getInt("chatroom_num"));
				room.setHouse_num(rs.getInt("house_num"));
				room.setSeller_num(rs.getInt("seller_num"));
				room.setBuyer_num(rs.getInt("buyer_num"));
				room.setCnt(rs.getInt("cnt"));
				room.setMem_nickname(rs.getString("mem_nickname"));
				list.add(room);
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//채팅 내용 입력
	public void insertChatHouse(House_ChatVO chat)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO house_chat (chat_num,chatroom_num,mem_num,message) "
					+ "VALUES(house_chat_seq.nextval,?,?,?)";
			
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
	
	//채팅방번호로 house_num 불러오기
	public HouseDetailVO getHouselistByChatroom(int chatroom_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		ResultSet rs = null;
		HouseDetailVO houselist = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM house_detail INNER JOIN house_chatroom USING(house_num) WHERE chatroom_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroom_num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				houselist = new HouseDetailVO();
				houselist.setHouse_buyer(rs.getInt("house_buyer"));
				houselist.setHouse_num(rs.getInt("house_num"));
				houselist.setMem_num(rs.getInt("mem_num"));
			}
		
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return houselist;
	}
	//판매자 readcount 개수
	public int getreadcountSeller(int mem_num, int house_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		int cnt = 0;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM house_chat INNER JOIN house_chatroom USING(chatroom_num) WHERE read_check=1 AND house_num=? AND mem_num!=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, house_num);
			pstmt.setInt(2, mem_num);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return cnt;
	}
}
