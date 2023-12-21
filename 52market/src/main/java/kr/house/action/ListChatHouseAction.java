package kr.house.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.house.dao.ChatHouseDAO;
import kr.house.vo.House_ChatVO;

public class ListChatHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");

		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		ChatHouseDAO chat = ChatHouseDAO.getInsttance();
		
		List<House_ChatVO> list = chat.getChatListHouse(chatroom_num, user_num);
		request.setAttribute("list", list);
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("list", list);
		//로그인한 사람이 작성자인지 체크하기 위해서 전송
		mapAjax.put("user_num", user_num);
		
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
