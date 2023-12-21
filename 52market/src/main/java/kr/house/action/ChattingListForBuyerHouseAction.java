package kr.house.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.ChatHouseDAO;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.house.vo.House_ChatroomVO;

public class ChattingListForBuyerHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		String house_num = request.getParameter("house_num");
		ChatHouseDAO chat = ChatHouseDAO.getInsttance();
		if(house_num != null) {
			int chatroom_num = chat.checkChatRoomHouse(Integer.parseInt(house_num), user_num);
			if(chatroom_num == 0) {
				HouseDAO house = HouseDAO.getInstance();
				HouseDetailVO vo = house.getHouseDetail(Integer.parseInt(house_num));
				//채팅롬이 존재하지 않은 경우 채팅룸이 생성하고 채팅 데이터 저장
				House_ChatroomVO chatRoom = new House_ChatroomVO();
				chatRoom.setHouse_num(Integer.parseInt(house_num));
				chatRoom.setSeller_num(vo.getMem_num());//판매자
				chatRoom.setBuyer_num(user_num);//구매자
				//채팅룸을 생성한 후 채팅룸 번호를 반환 받음
				chatroom_num = chat.insertChatRoomHouse(chatRoom);
			}
			return "redirect:/chatting/chatDetailHouse.do?chatroom_num="+chatroom_num;
		}
		List<House_ChatroomVO> list = chat.getChattingListForBuyerHouse(user_num);
		request.setAttribute("list", list);
		
		//JSP 경로 반환
		return "/WEB-INF/views/chatting/chattingListForBuyerHouse.jsp";
	}

}
