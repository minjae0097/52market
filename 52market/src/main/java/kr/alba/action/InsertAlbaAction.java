package kr.alba.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.FileUtil;

public class InsertAlbaAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		MemberDAO memberdao = MemberDAO.getInstance();
		MemberVO member = memberdao.getMember(user_num); 
		
		MultipartRequest multi = FileUtil.createFile(request);
		AlbaVO alba = new AlbaVO();
		alba.setAlba_photo(multi.getFilesystemName("alba_photo"));
		alba.setAlba_title(multi.getParameter("alba_title"));
		alba.setAlba_content1(multi.getParameter("alba_content1"));
		alba.setAlba_content2(multi.getParameter("alba_content2"));
		alba.setAlba_ip(request.getRemoteAddr());
		alba.setAlba_location(multi.getFilesystemName("alba_location"));
		alba.setAlba_zipcode(multi.getParameter("alba_zipcode"));
		alba.setAlba_address1(multi.getParameter("alba_address1"));
		alba.setAlba_address2(multi.getParameter("alba_address2"));
		alba.setMem_nickname(member.getMem_nickname());
		alba.setMem_num(user_num);
		
		AlbaDAO dao = AlbaDAO.getInstance();
		dao.insertAlba(alba);
		return "/WEB-INF/views/alba/insertAlba.jsp";
	}

}
