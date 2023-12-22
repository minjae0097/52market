package kr.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ChatProductDAO;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_ChatroomVO;
import kr.product.vo.Product_DetailVO;

public class ChattingListForBuyerProductAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		String product_num = request.getParameter("product_num");
		ChatProductDAO chat = ChatProductDAO.getInstance();
		if(product_num!=null) {
			int chatroom_num = chat.checkChatRoomProduct(Integer.parseInt(product_num), user_num);
			if(chatroom_num == 0) {
				ProductDAO product = ProductDAO.getInstance();
				Product_DetailVO vo = product.getProductDetail(Integer.parseInt(product_num));
				//채팅룸이 존재하지 않은 경우 생성 후 데이터 저장
				Product_ChatroomVO chatRoom = new Product_ChatroomVO();
				chatRoom.setProduct_num(Integer.parseInt(product_num));
				chatRoom.setSeller_num(vo.getProduct_seller());//판매자
				chatRoom.setBuyer_num(user_num);//구매자
				
				//생성 후 번호 반환
				chatroom_num = chat.insertChatRoomProduct(chatRoom);
			}
			return "redirect:/chatting/chatDetailProduct.do?chatroom_num="+chatroom_num;		
		}
		List<Product_ChatroomVO> list = chat.getChattingListForBuyerProduct(user_num);
		request.setAttribute("list", list);
		return "/WEB-INF/views/chatting/chattingListForBuyerProduct.jsp";
	}

}
