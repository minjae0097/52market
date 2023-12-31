package kr.car.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.ChatCarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.controller.Action;

public class ChatDetailCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		ChatCarDAO chat = ChatCarDAO.getInstance();
		CarList_DetailVO detail = chat.getCarlistByChatroom(chatroom_num);
		
		request.setAttribute("seller_num", detail.getCar_seller());
		request.setAttribute("chatroom_num", chatroom_num);
		return "/WEB-INF/views/chatting/chatDetailCar.jsp";
	}

}
