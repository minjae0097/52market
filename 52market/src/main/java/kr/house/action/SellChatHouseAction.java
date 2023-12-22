package kr.house.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.house.dao.ChatHouseDAO;
import kr.house.vo.House_ChatVO;

public class SellChatHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,String> mapAjax = new HashMap<String,String>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			request.setCharacterEncoding("utf-8");
			House_ChatVO chat = new House_ChatVO();
			chat.setChatroom_num(Integer.parseInt(request.getParameter("chatroom_num")));
			chat.setMem_num(user_num);
			
			String message = "구매확정하시겠습니까?<br>*확정되면 취소할 수 없습니다.<br>*거래후에 확정을 눌러주세요<br>"
					+ "<button id='buy' onclick=\"location.href='buyChatHouse.do?chatroom_num="+Integer.parseInt(request.getParameter("chatroom_num"))+"'\">구매확정</button>";
			chat.setMessage(message);
			
			ChatHouseDAO dao = ChatHouseDAO.getInsttance();
			dao.insertChatHouse(chat);
			
			mapAjax.put("result", "success");
			
		}
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
