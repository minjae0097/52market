package kr.car.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.controller.Action;

public class UpdateStatusAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 확인
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		int carlist_num = Integer.parseInt(request.getParameter("carlist_num"));
		
		CarDAO car = CarDAO.getInstance();
		CarList_DetailVO db_detail = car.getCarList_Detail(carlist_num);
		//회원정보 일치여부확인
		if(user_num!=db_detail.getCar_seller()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		int carlist_status = Integer.parseInt(request.getParameter("carlist_status"));
		
		car.updateCarStatus(carlist_status, carlist_num);
		
		return "redirect:/car/detailCar.do?carlist_num="+carlist_num;
	}
	
}
