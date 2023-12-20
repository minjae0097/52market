package kr.car.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.CarDAO;
import kr.car.dao.ChatCarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_ChatroomVO;
import kr.controller.Action;

public class ChattingListForBuyerCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		String carlist_num = request.getParameter("carlist_num");
		ChatCarDAO chat = ChatCarDAO.getInstance();
		if(carlist_num != null) {
			int chatroom_num = chat.checkChatRoomCar(Integer.parseInt(carlist_num), user_num);
			if(chatroom_num == 0) {
				CarDAO car = CarDAO.getInstance();
				CarList_DetailVO vo = car.getCarList_Detail(Integer.parseInt(carlist_num));
				//채팅롬이 존재하지 않은 경우 채팅룸이 생성하고 채팅 데이터 저장
				Car_ChatroomVO chatRoom = new Car_ChatroomVO();
				chatRoom.setCarlist_num(Integer.parseInt(carlist_num));
				chatRoom.setSeller_num(vo.getCar_seller());//판매자
				chatRoom.setBuyer_num(user_num);//구매자
				//채팅룸을 생성한 후 채팅룸 번호를 반환 받음
				chatroom_num = chat.insertChatRoomCar(chatRoom);
			}
			return "redirect:/chatting/chatDetailCar.do?chatroom_num="+chatroom_num;
		}
		List<Car_ChatroomVO> list = chat.getChattingListForBuyerCar(user_num);
		request.setAttribute("list", list);
		return "/WEB-INF/views/chatting/chattingListForBuyerCar.jsp";
		
	}

}
