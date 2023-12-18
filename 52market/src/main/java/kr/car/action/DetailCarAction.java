package kr.car.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_MapVO;
import kr.car.vo.CarlistVO;
import kr.controller.Action;
import kr.member.vo.MemberVO;
import kr.util.StringUtil;

public class DetailCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int carlist_num = Integer.parseInt(request.getParameter("carlist_num"));
		
		CarDAO car = CarDAO.getInstance();
		CarList_DetailVO detail = car.getCarList_Detail(carlist_num);
		CarlistVO list = car.getCarList(carlist_num);
		Car_MapVO map = car.getCarMap(carlist_num);
		MemberVO seller = car.getCarMember(detail.getCar_seller());
		
		detail.setCar_title(StringUtil.useNoHtml(detail.getCar_title()));
		list.setCarlist_content(StringUtil.useBrNoHtml(list.getCarlist_content()));
		
		request.setAttribute("detail", detail);
		request.setAttribute("list", list);
		request.setAttribute("map", map);
		request.setAttribute("seller", seller);
		
		
		return "/WEB-INF/views/car/detailCar.jsp";
	}

}
