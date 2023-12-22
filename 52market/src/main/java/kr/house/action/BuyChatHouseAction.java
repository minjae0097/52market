package kr.house.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.ChatHouseDAO;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.house.vo.House_ChatroomVO;

public class BuyChatHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		
		ChatHouseDAO chat = ChatHouseDAO.getInsttance();
		House_ChatroomVO houselist = chat.getHouselistByChatroom(chatroom_num);
		
		if(houselist.getBuyer_num()>0) {
			request.setAttribute("notice_msg", "이미 판매된 상품입니다");
			request.setAttribute("notice_url", 
					request.getContextPath()+"/chatting/chatDetailHouse.do?chatroom_num="+chatroom_num);
		
		}else {
			HouseDAO house = HouseDAO.getInstance();
			house.sellHouse(houselist.getHouse_num(), user_num);
			request.setAttribute("notice_msg", "구매확정이 완료되었습니다.");
			request.setAttribute("notice_url", 
					request.getContextPath()+"/chatting/chatDetailHouse.do?chatroom_num="+chatroom_num);
		}
		
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
