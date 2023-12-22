package kr.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.product.vo.Product_ChatVO;
import kr.product.vo.Product_ChatroomVO;
import kr.util.DBUtil;

public class ChatProductDAO {
	private static ChatProductDAO instance = new ChatProductDAO();
	public static ChatProductDAO getInstance() {
		return instance;
	}
	private ChatProductDAO() {};
	
	//채팅방 체크
	public int checkChatRoomProduct(int product_num, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int chatroom_num = 0;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT chatroom_num FROM product_chatroom WHERE product_num = ? "
					+ "AND (seller_num=? OR buyer_num=?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_num);
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
	public int insertChatRoomProduct(Product_ChatroomVO chatroom)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		ResultSet rs = null;
		int chatroom_num = 0;
		try {
			conn  = DBUtil.getConnection();
			
			conn.setAutoCommit(false);
			
			sql = "SELECT product_chatroom_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				chatroom_num = rs.getInt(1);
			}
			
			sql = "INSERT INTO product_chatroom (chatroom_num,product_num,seller_num,buyer_num) "
					+ "VALUES(?,?,?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, chatroom_num);
			pstmt2.setInt(2, chatroom.getProduct_num());
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
	public List<Product_ChatroomVO> getChattingListForBuyerProduct(int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		List<Product_ChatroomVO> list = null;
		ResultSet rs = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM member JOIN product_chatroom INNER JOIN product_detail USING(product_num) "
					+ "ON mem_num=product_seller WHERE buyer_num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			
			rs = pstmt.executeQuery();
			list = new ArrayList<Product_ChatroomVO>();
			while(rs.next()) {
				Product_ChatroomVO room = new Product_ChatroomVO();
				room.setChatroom_num(rs.getInt("chatroom_num"));
				room.setProduct_num(rs.getInt("product_num"));
				room.setSeller_num(rs.getInt("seller_num"));
				room.setBuyer_num(rs.getInt("buyer_num"));
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
	public List<Product_ChatVO> getChatListProduct(int chatroom_num, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		ResultSet rs = null;
		List<Product_ChatVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			sql = "UPDATE product_chat SET read_check=0 WHERE chatroom_num=? AND mem_num!=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chatroom_num);
			pstmt.setInt(2, mem_num);
			pstmt.executeUpdate();
			
			sql = "SELECT * FROM product_chat WHERE chatroom_num=? ORDER BY reg_date ASC";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setInt(1, chatroom_num);
			rs = pstmt2.executeQuery();
			
			list = new ArrayList<Product_ChatVO>();
			while(rs.next()) {
				Product_ChatVO chat = new Product_ChatVO();
				chat.setChat_num(rs.getInt("chat_num"));
				chat.setChatroom_num(rs.getInt("chatroom_num"));
				chat.setMem_num(rs.getInt("mem_num"));
				chat.setMessage(rs.getString("message"));
				chat.setReg_date(rs.getDate("reg_date"));
				chat.setRead_check(rs.getInt("read_check"));
				
				list.add(chat);
			}
			conn.commit();
		}catch(Exception e) {
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//판매자 product_num 채팅방 받아오기
	public List<Product_ChatroomVO> getChattingListForSellerProduct(int product_num, int mem_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<Product_ChatroomVO> list = null;
		
		try {
			conn = DBUtil.getConnection();
			
			sql = "SELECT * FROM product_chatroom c JOIN (SELECT chatroom_num FROM product_chat "
					+ "group by chatroom_num) OUTER JOIN (SELECT COUNT(*) cnt, "
					+ "chatroom_num FROM product_chat WHERE read_check=1 AND mem_num!=? "
					+ "GROUP BY chatroom_num) USING (chatroom_num) JOIN member m on c.buyer_num=m.mem_num "
					+ "WHERE product_num=? ORDER BY cnt DESC NULLS LAST";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, mem_num);
			pstmt.setInt(2, product_num);
			rs = pstmt.executeQuery();
			
			list = new ArrayList<Product_ChatroomVO>();
			while(rs.next()) {
				Product_ChatroomVO room = new Product_ChatroomVO();
				room.setChatroom_num(rs.getInt("chatroom_num"));
				room.setProduct_num(rs.getInt("product_num"));
				room.setSeller_num(rs.getInt("seller_num"));
				room.setBuyer_num(rs.getInt("buyer_num"));
				room.setCnt(rs.getInt("cnt"));
				room.setMem_nickname(rs.getString("mem_nickname"));
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
	public void insertChatProduct(Product_ChatVO chat)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			sql = "INSERT INTO product_chat (chat_num,chatroom_num,mem_num,message) "
					+ "VALUES(product_chat_seq.nextval,?,?,?)";
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
