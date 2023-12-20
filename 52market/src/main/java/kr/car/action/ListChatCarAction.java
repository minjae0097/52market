package kr.car.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.car.dao.ChatCarDAO;
import kr.car.vo.Car_ChatVO;
import kr.controller.Action;

public class ListChatCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");

		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");

		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		ChatCarDAO chat = ChatCarDAO.getInstance();
		
		
		List<Car_ChatVO > list = chat.getChatListCar(chatroom_num);
		request.setAttribute("list", list);
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		mapAjax.put("list", list);
		//로그인한 사람이 작성자인지 체크하기 위해서 전송
		mapAjax.put("user_num",user_num);

		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);

		request.setAttribute("ajaxData", ajaxData);

		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
