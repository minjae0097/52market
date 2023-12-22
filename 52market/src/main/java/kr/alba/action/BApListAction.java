package kr.alba.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AplistVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class BApListAction implements Action{

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
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum="1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		AlbaDAO dao = AlbaDAO.getInstance();
		int count = dao.getBApListCount(keyfield, keyword, user_num);
		
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum),count,20,10,"b_apDetail.do");
		
		List<AplistVO> list = null;
		
		if(count > 0) {
			list = dao.BApDetail(page.getStartRow(), page.getEndRow(), keyfield, keyword, user_num);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/alba/b_apDetail.jsp";
	}

}
