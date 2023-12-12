package kr.alba.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DetailAlbaAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int alba_num = Integer.parseInt(request.getParameter("alba_num"));
		
		AlbaDAO dao = AlbaDAO.getInstance();
		AlbaVO alba = dao.getAlba(alba_num);
		
		alba.setAlba_title(StringUtil.useNoHtml(alba.getAlba_title()));
		alba.setAlba_content1(StringUtil.useBrNoHtml(alba.getAlba_content1()));
		alba.setAlba_content2(StringUtil.useBrNoHtml(alba.getAlba_content2()));
		
		request.setAttribute("alba", alba);
		
		return "WEB-INF/views/alba/detailAlba.jsp";
	}

}
