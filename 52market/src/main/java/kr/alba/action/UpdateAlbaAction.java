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
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int alba_num = Integer.parseInt(multi.getParameter("alba_num"));
		String alba_photo = multi.getFilesystemName("alba_photo");
		String alba_location = multi.getFilesystemName("alba_location");
		
		AlbaDAO dao = AlbaDAO.getInstance();
		AlbaVO alba = dao.getAlba(alba_num);
		if(user_num != alba.getMem_num()) {
			FileUtil.removeFile(request, alba_photo);
		}
		if(user_num.equals(alba.getMem_num())) {
			FileUtil.removeFile(request, alba_location);
			
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		if(alba_photo == null) {
			alba.setAlba_photo(alba.getAlba_photo());
		}else {
			alba.setAlba_photo(alba_photo);
		}
		
		alba.setAlba_title(multi.getParameter("alba_title"));
		alba.setAlba_content1(multi.getParameter("alba_content1"));
		alba.setAlba_content2(multi.getParameter("alba_content2"));
		if(alba_location == null) {
			alba.setAlba_location(alba.getAlba_location());
		}else {
			alba.setAlba_location(alba_location);
		}
		alba.setAlba_zipcode(multi.getParameter("alba_zipcode"));
		alba.setAlba_address1(multi.getParameter("alba_address1"));
		alba.setAlba_address2(multi.getParameter("alba_address2"));
		alba.setAlba_ip(request.getRemoteAddr());
		
		dao.updateAlba(alba);
		
		
		return "redirect:/alba/detailAlba.do?alba_num=" + alba_num;
	}

}
