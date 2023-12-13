package kr.car.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		CarDAO dao = CarDAO.getInstance();
		int count = dao.getCarCount(keyfield, keyword);
		
		PageUtil page = new PageUtil(keyfield, keyword, Integer.parseInt(pageNum),count,8,5,"list.do");
		
		
		List<CarList_DetailVO> carList = null;
		if(count>0) {
			carList = dao.getList(page.getStartRow(), page.getEndRow(), keyfield, keyword, 0);
		}
		request.setAttribute("count", count);
		request.setAttribute("carList", carList);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/car/list.jsp";
	}

}
