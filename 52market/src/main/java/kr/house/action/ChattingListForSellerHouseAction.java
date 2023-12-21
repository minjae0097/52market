package kr.house.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.ChatHouseDAO;
import kr.house.vo.House_ChatroomVO;

public class ChattingListForSellerHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		String house_num = request.getParameter("house_num");
		ChatHouseDAO chat = ChatHouseDAO.getInsttance();
		
		List<House_ChatroomVO> list = chat.getChattingListForSellerHouse(Integer.parseInt(house_num), user_num);
		request.setAttribute("list", list);
		
		//JSP 경로 반환
		return "/WEB-INF/views/chatting/chattingListForSellerHouse.jsp";
	}

}
