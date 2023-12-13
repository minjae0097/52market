package kr.house.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.house.vo.HouseListVO;

public class UpdateHouseFormAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int house_num = Integer.parseInt(request.getParameter("house_num"));
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		HouseDAO dao = HouseDAO.getInstance();
		HouseDetailVO db_detail = dao.getHouseDetail(house_num);
		
		if(!user_num.equals(db_detail.getMem_num())) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		HouseDetailVO detail = dao.getHouseDetail(house_num);
		HouseListVO list = dao.getHouseList(house_num);
		
		request.setAttribute("detail", detail);
		request.setAttribute("list", list);
		
		//JSP 경로 반환
		return "/WEB-INF/views/house/updateHouseForm.jsp";
	}

}
