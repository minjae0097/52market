package kr.house.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.ChatHouseDAO;
import kr.house.vo.HouseDetailVO;

public class ChatDetailHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		ChatHouseDAO chat = ChatHouseDAO.getInsttance();
		HouseDetailVO detail = chat.getHouselistByChatroom(chatroom_num);
		
		
		
		request.setAttribute("seller_num", detail.getMem_num());
		request.setAttribute("chatroom_num", chatroom_num);
		
		//JSP 경로 반환
		return "/WEB-INF/views/chatting/chatDetailHouse.jsp";
	}

}
