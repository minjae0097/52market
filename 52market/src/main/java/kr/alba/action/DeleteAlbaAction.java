package kr.alba.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class DeleteAlbaAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int alba_num = Integer.parseInt(request.getParameter("alba_num"));
		
		AlbaDAO dao = AlbaDAO.getInstance();
		AlbaVO alba = dao.getAlba(alba_num);
		
		if(!user_num.equals(alba.getMem_num()) && user_auth!=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		dao.deleteAlba(alba_num);
		
		FileUtil.removeFile(request, alba.getAlba_photo());
		FileUtil.removeFile(request, alba.getAlba_location());
		
		return "redirect:/alba/list.do";
	}

}
