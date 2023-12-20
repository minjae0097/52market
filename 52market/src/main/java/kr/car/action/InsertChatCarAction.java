package kr.car.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.ChatCarDAO;
import kr.car.vo.Car_ChatVO;
import kr.controller.Action;

public class InsertChatCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("utf-8");
		Car_ChatVO chat = new Car_ChatVO();
		chat.setChatroom_num(Integer.parseInt(request.getParameter("chatroom_num")));
		chat.setMem_num(Integer.parseInt(request.getParameter("mem_num")));
		chat.setMessage(request.getParameter("message"));
		
		ChatCarDAO dao = ChatCarDAO.getInstance();
		dao.insertChatCar(chat);
		
		return "redirect:/chatting/chatDetailCar.do?chatroom_num="+chat.getChatroom_num();
	}

}
