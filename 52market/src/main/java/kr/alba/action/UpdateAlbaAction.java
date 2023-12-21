package kr.alba.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class UpdateAlbaAction implements Action{

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
		
		MultipartRequest multi = FileUtil.createFile(request);
		
		int alba_num = Integer.parseInt(multi.getParameter("alba_num"));
		String alba_photo = multi.getFilesystemName("alba_photo");
		String alba_location = multi.getFilesystemName("alba_location");
		
		AlbaDAO dao = AlbaDAO.getInstance();
		AlbaVO db_alba = dao.getAlba(alba_num);
		
		AlbaVO alba = new AlbaVO();
		alba.setAlba_num(alba_num);
		alba.setAlba_photo(alba_photo);
		alba.setAlba_title(multi.getParameter("alba_title"));
		alba.setAlba_content1(multi.getParameter("alba_content1"));
		alba.setAlba_content2(multi.getParameter("alba_content2"));
		alba.setAlba_location(alba_location);
		alba.setAlba_ip(request.getRemoteAddr());
		alba.setAlba_zipcode(multi.getParameter("alba_zipcode"));
		alba.setAlba_address1(multi.getParameter("alba_address1"));
		alba.setAlba_address2(multi.getParameter("alba_address2"));
		
		dao.updateAlba(alba);
		
		if(alba_photo != null) FileUtil.removeFile(request, db_alba.getAlba_photo());
		if(alba_location != null) FileUtil.removeFile(request, db_alba.getAlba_location());
		
		request.setAttribute("notice_msg", "정상적으로 수정되었습니다.");
		request.setAttribute("notice_url", request.getContextPath()+"/alba/detailAlba.do?alba_num="+alba_num);
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
