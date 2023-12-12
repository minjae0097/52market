package kr.car.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_MapVO;
import kr.car.vo.CarlistVO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.util.StringUtil;

public class UpdateCarFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		int carlist_num = Integer.parseInt(request.getParameter("carlist_num"));
		if(user_num ==null) {
			return "redirect:/member/loginForm.do";
		}
		CarDAO car = CarDAO.getInstance();
		CarList_DetailVO db_detail = car.getCarList_Detail(carlist_num);
		
		if(!user_num.equals(db_detail.getCar_seller())) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		CarList_DetailVO detail = car.getCarList_Detail(carlist_num);
		CarlistVO list = car.getCarList(carlist_num);
		Car_MapVO map = car.getCarMap(carlist_num);
		
		request.setAttribute("detail", detail);
		request.setAttribute("list", list);
		request.setAttribute("map", map);
		
		return "/WEB-INF/views/car/updateCarForm.jsp";
	}

}
