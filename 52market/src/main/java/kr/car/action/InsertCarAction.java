package kr.car.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_MapVO;
import kr.car.vo.CarlistVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class InsertCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 체크
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		CarlistVO list = new CarlistVO();
		CarList_DetailVO detail = new CarList_DetailVO();
		Car_MapVO map = new Car_MapVO();
		list.setCarlist_content(multi.getParameter("carlist_content"));
		
		detail.setCar_seller(user_num);
		detail.setCar_type((multi.getParameter("car_type")));
		detail.setCar_fuel((multi.getParameter("car_fuel")));
		detail.setCar_price(Integer.parseInt(multi.getParameter("car_price")));
		detail.setCar_model_year(Integer.parseInt(multi.getParameter("car_model_year")));
		detail.setCar_distance(Integer.parseInt(multi.getParameter("car_distance")));
		detail.setCar_transmission((multi.getParameter("car_transmission")));
		detail.setCar_origin((multi.getParameter("car_origin")));
		detail.setCar_image(multi.getFilesystemName("car_image"));
		detail.setCar_title(multi.getParameter("car_title"));

		map.setLocation_x(multi.getParameter("location_x"));
		map.setLocation_y(multi.getParameter("location_y"));
		map.setLocation(multi.getParameter("location"));
		map.setRoad_address_name(multi.getParameter("road_address_name"));
		map.setAddress_name(multi.getParameter("address_name"));
		
		CarDAO dao = CarDAO.getInstance();
		dao.insertCar(list, detail, map);
		
		return "/WEB-INF/views/car/insertCar.jsp";
	}

}
