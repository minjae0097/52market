package kr.car.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.controller.Action;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		//필터
		String car_type = request.getParameter("car_type");
		String car_fuel = request.getParameter("car_fuel");
		String car_transmission = request.getParameter("car_transmission");
		String car_origin = request.getParameter("car_origin");
		
		
		CarDAO dao = CarDAO.getInstance();
		int count = dao.getCarCount(keyfield, keyword,0,car_type,car_fuel,car_transmission,car_origin);
		
		/*
		 * PageUtil page = new PageUtil(keyfield, keyword,
		 * Integer.parseInt(pageNum),count,8,5,"list.do");
		 */
		PageUtilCar carpage = new PageUtilCar(keyfield, keyword, Integer.parseInt(pageNum),count,8,5,"list.do",0, car_type, car_fuel, car_transmission, car_origin);
		
		List<CarList_DetailVO> carList = null;
		if(count>0) {
			carList = dao.getList(carpage.getStartRow(), carpage.getEndRow(), keyfield, keyword, 0 ,car_type,car_fuel,car_transmission,car_origin);
		}
		request.setAttribute("count", count);
		request.setAttribute("carList", carList);
		request.setAttribute("page", carpage.getPage());
		
		if(car_type!=null) request.setAttribute("car_type", car_type);
		if(car_fuel!=null) request.setAttribute("car_fuel", car_fuel);
		if(car_transmission!=null) request.setAttribute("car_transmission", car_transmission);
		if(car_origin!=null) request.setAttribute("car_origin", car_origin);
		
		return "/WEB-INF/views/car/list.jsp";
	}

}
