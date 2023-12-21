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
		
		int alba_num = Integer.parseInt(request.getParameter("alba_num"));
		
		MultipartRequest multi = FileUtil.createFile(request);
		AlbaVO alba = new AlbaVO();
		alba.setAlba_title(multi.getParameter("alba_title"));
		alba.setMem_num(Integer.parseInt(multi.getParameter("mem_num")));
		alba.setAlba_filename(multi.getFilesystemName("alba_filename"));
		alba.setAlba_num(alba_num);
		
		AlbaDAO dao = AlbaDAO.getInstance();
		dao.insertAlbaAp(alba);
		
		request.setAttribute("notice_msg", "지원서 등록이 되었습니다");
		request.setAttribute("notice_url", request.getContextPath()+"/alba/detailAlba.do?alba_num="+alba_num);
		
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
