package kr.house.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class InsertHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 체크
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//JSP 경로 반환
		return "/WEB-INF/views/house/insertHouse.jsp";
	}

}
