package kr.alba.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DetailAlbaAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_auth = (Integer)session.getAttribute("user_auth");
	
		
		int alba_num = Integer.parseInt(request.getParameter("alba_num")); 
		
		AlbaDAO dao = AlbaDAO.getInstance();
		
		dao.updateReadcount(alba_num);
		
		AlbaVO alba = dao.getAlba(alba_num);
		
		alba.setAlba_title(StringUtil.useNoHtml(alba.getAlba_title()));
		alba.setAlba_content1(StringUtil.useBrNoHtml(alba.getAlba_content1()));
		alba.setAlba_content2(StringUtil.useBrNoHtml(alba.getAlba_content2()));
		request.setAttribute("user_auth", user_auth);
		request.setAttribute("alba", alba);
		
		return "/WEB-INF/views/alba/detailAlba.jsp";
	}

}
