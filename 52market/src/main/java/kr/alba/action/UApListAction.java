package kr.alba.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class UApListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
	
		if(user_num ==null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 2) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		String pageNum = request.getParameter("pagenum");
		if(pageNum == null) pageNum="1";
		
		
		AlbaDAO dao = AlbaDAO.getInstance();
		int count = dao.getAlbaCount(null, null);
		
		PageUtil page = new PageUtil(null, null, Integer.parseInt(pageNum),count,20,10,"b_apDetail.do");
		
		List<AlbaVO> list = null;
		if(count > 0) {
			list = dao.getListAlba(page.getStartRow(), page.getEndRow(),null, null);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/alba/u_apDetail.jsp";
	}

}
