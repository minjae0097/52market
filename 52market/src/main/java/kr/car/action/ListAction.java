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
		
		CarDAO dao = CarDAO.getInstance();
		List<CarList_DetailVO> carList = dao.getList(1, 8, null, null, 0);
		
		request.setAttribute("carList", carList);
		
		
		return "/WEB-INF/views/car/list.jsp";
	}

}
