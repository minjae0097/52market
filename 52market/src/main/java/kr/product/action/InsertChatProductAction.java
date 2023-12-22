package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ChatProductDAO;
import kr.product.vo.Product_ChatVO;

public class InsertChatProductAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		request.setCharacterEncoding("utf-8");
		Product_ChatVO chat = new Product_ChatVO();
		chat.setChatroom_num(Integer.parseInt(request.getParameter("chatroom_num")));
		chat.setMem_num(Integer.parseInt(request.getParameter("mem_num")));
		chat.setMessage(request.getParameter("message"));
		
		ChatProductDAO dao = ChatProductDAO.getInstance();
		dao.insertChatProduct(chat);
		
		return "redirect:/chatting/chatDetailProduct.do?chatroom_num="+chat.getChatroom_num();
	}

}
