package kr.car.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.ChatCarDAO;
import kr.car.vo.Car_ChatroomVO;
import kr.controller.Action;

public class ChattingListForSellerCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		String carlist_num = request.getParameter("carlist_num");
		ChatCarDAO chat = ChatCarDAO.getInstance();
		
		List<Car_ChatroomVO> list = chat.getChattingListForSellerCar(Integer.parseInt(carlist_num));
		request.setAttribute("list", list);
		
		
		return "/WEB-INF/views/chatting/chattingListForSellerCar.jsp";
	}
	
}
