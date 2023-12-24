package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_DetailVO;

public class MyBuyListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		CarDAO car = CarDAO.getInstance();
		List<CarList_DetailVO> carList = null;
		carList = car.getBuyList(user_num, 1, 2, null, null);
		
		HouseDAO house = HouseDAO.getInstance();
		List<HouseDetailVO> houseList = null;
		houseList = house.getBuyList(user_num, 1, 2, null, null);

		ProductDAO product = ProductDAO.getInstance();
		List<Product_DetailVO> productList = null;
		productList = product.getBuyList(user_num, 1, 2, null, null);
		
		request.setAttribute("carList", carList);
		request.setAttribute("houseList", houseList);
		request.setAttribute("productList", productList);
		
		return "/WEB-INF/views/member/myBuyList.jsp";
	}

}
