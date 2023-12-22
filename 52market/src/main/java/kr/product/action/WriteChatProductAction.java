package kr.product.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.product.dao.ChatProductDAO;
import kr.product.vo.Product_ChatVO;

public class WriteChatProductAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = new HashMap<String,String>();

		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			
			request.setCharacterEncoding("utf-8");
			Product_ChatVO chat = new Product_ChatVO();
			chat.setChatroom_num(Integer.parseInt(request.getParameter("chatroom_num")));
			chat.setMem_num(Integer.parseInt(request.getParameter("mem_num")));
			chat.setMessage(request.getParameter("message"));
			
			ChatProductDAO dao = ChatProductDAO.getInstance();
			dao.insertChatProduct(chat);
			
			mapAjax.put("result", "success");
		}
		
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
