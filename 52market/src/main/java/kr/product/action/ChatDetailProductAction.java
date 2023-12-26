package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ChatProductDAO;
import kr.product.vo.Product_DetailVO;

public class ChatDetailProductAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		ChatProductDAO chat = ChatProductDAO.getInstance();
		Product_DetailVO detail = chat.getProductByChatroom(chatroom_num);
		
		request.setAttribute("seller_num", detail.getProduct_seller());
		request.setAttribute("chatroom_num", chatroom_num);
		return "/WEB-INF/views/chatting/chatDetailProduct.jsp";
	}
}
