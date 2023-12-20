package kr.house.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;

public class UpdateStatusAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 확인
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		int house_num = Integer.parseInt(request.getParameter("house_num"));
		
		HouseDAO dao = HouseDAO.getInstance();
		HouseDetailVO db_detail = dao.getHouseDetail(house_num);
		
		//회원정보 일치여부확인
		if(user_num!=db_detail.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		int house_status = Integer.parseInt(request.getParameter("house_status"));
		
		dao.updateHouseStatus(house_status, house_num);
		
		//JSP 경로 반환
		return "redirect:/house/detailHouse.do?house_num="+house_num;
	}

}
