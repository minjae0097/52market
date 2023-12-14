package kr.house.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.util.FileUtil;

public class DeleteHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		//로그인 확인
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		int house_num = Integer.parseInt(request.getParameter("house_num"));
		
		HouseDAO dao = HouseDAO.getInstance();
		HouseDetailVO db_detail = dao.getHouseDetail(house_num);
		//회원정보 일치여부확인 또는 관리자
		if(user_num != db_detail.getMem_num() && user_auth != 9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		dao.deleteHouse(house_num);
		
		FileUtil.removeFile(request, db_detail.getHouse_photo1());
		FileUtil.removeFile(request, db_detail.getHouse_photo2());
		
		//JSP 경로 반환
		return "redirect:/house/list.do";
	}

}
