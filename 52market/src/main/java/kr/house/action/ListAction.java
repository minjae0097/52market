package kr.house.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.util.PageUtil;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		HouseDAO houseDao = HouseDAO.getInstance();
		int count = houseDao.getHouseCount(keyfield, keyword, 0);
		
		//페이지 처리
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),
														count,20,10,"list.do");
		
		List<HouseDetailVO> houseList = houseDao.getListHouse(1, 8, null, null,0);
		if(count > 0) {
			houseList = houseDao.getListHouse(page.getStartRow(), page.getEndRow(), keyfield, keyword, 0);
		}
		
		request.setAttribute("count", count);
		request.setAttribute("houseList", houseList);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/house/list.jsp";
	}

}
