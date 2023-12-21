package kr.alba.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;

public class UpdateAlbaFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
	
		if(user_num ==null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 3) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		int alba_num = Integer.parseInt(request.getParameter("alba_num"));
		
		AlbaDAO dao = AlbaDAO.getInstance();
		AlbaVO alba = dao.getAlba(alba_num);	
		
		request.setAttribute("alba", alba);
			
		return "/WEB-INF/views/alba/updateAlbaForm.jsp";
	}

}
