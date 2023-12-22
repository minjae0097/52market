package kr.car.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.CarDAO;
import kr.car.dao.ChatCarDAO;
import kr.car.vo.Car_ChatroomVO;
import kr.controller.Action;

public class BuyChatCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		
		ChatCarDAO chat = ChatCarDAO.getInstance();
		Car_ChatroomVO carlist = chat.getCarlistByChatroom(chatroom_num);
		
		if(carlist.getBuyer_num()>0) {
			request.setAttribute("notice_msg", "이미 판매된 상품입니다");
			request.setAttribute("notice_url", 
					request.getContextPath()+"/chatting/chatDetailCar.do?chatroom_num="+chatroom_num);
		}else {
			CarDAO car = CarDAO.getInstance();
			car.sellCar(carlist.getCarlist_num(), user_num);
			request.setAttribute("notice_msg", "구매확정이 완료되었습니다.");
			request.setAttribute("notice_url", 
					request.getContextPath()+"/chatting/chatDetailCar.do?chatroom_num="+chatroom_num);
		}
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
