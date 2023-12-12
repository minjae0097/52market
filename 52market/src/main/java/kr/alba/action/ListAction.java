package kr.alba.action;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		AlbaDAO dao = AlbaDAO.getInstance();
		List<AlbaVO> albaList = dao.getList (1, 8, null, null, 0);
		
		request.setAttribute("albaList", albaList);
		
		return "/WEB-INF/views/alba/list.jsp";
	}

}
