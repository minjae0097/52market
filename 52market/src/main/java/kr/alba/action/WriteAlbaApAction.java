package kr.alba.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class WriteAlbaApAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		if(user_auth != 2) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		AlbaVO alba = new AlbaVO();
		alba.setAlba_title(multi.getParameter("alba_title"));
		alba.setAlba_filename(multi.getFilesystemName("alba_filename"));
		alba.setMem_nickname(multi.getParameter("mem_nickname"));
		
		AlbaDAO dao = AlbaDAO.getInstance();
		dao.insertAlbaAp(alba);
		
		return "/WEB-INF/views/alba/apList.jsp";
	}

}
