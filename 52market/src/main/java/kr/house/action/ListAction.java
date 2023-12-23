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
		
		//판매중인것만 보기
		int house_status = 1;
		if(request.getParameter("house_status")!=null) {
			house_status = 0;
		}
		//필터
		int house_seller_type=0;
		if(request.getParameter("house_seller_type")!=null) {
			house_seller_type= Integer.parseInt(request.getParameter("house_seller_type"));
		}
		int house_type = 0;
		if(request.getParameter("house_type")!=null) {
			house_type = Integer.parseInt(request.getParameter("house_type"));
		}		
		int house_deal_type = 0;
		if(request.getParameter("house_deal_type")!=null) {
			house_deal_type = Integer.parseInt(request.getParameter("house_deal_type"));
		}		
		int house_move_in = 0;
		if(request.getParameter("house_move_in")!=null) {
			house_move_in = Integer.parseInt(request.getParameter("house_move_in"));
		}
			
		HouseDAO houseDao = HouseDAO.getInstance();
		int count = houseDao.getHouseCount(keyfield, keyword, house_status,house_seller_type,house_type,house_deal_type,house_move_in);
		//System.out.println("count : " + count);
		
		//페이지 처리
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),
														count,8,5,"list.do","&house_status="+house_status+"&house_seller_type="+house_seller_type+"&house_type="+house_type+"&house_deal_type="+house_deal_type+"&house_move_in="+house_move_in);
		List<HouseDetailVO> houseList = null;
		if(count > 0) {
			houseList = houseDao.getListHouse(page.getStartRow(), page.getEndRow(), keyfield, keyword, house_status,house_seller_type,house_type,house_deal_type,house_move_in);
		}
		
		
		request.setAttribute("count", count);
		request.setAttribute("houseList", houseList);
		request.setAttribute("page", page.getPage());
		
		if(house_seller_type >= 1 && house_seller_type <= 9) request.setAttribute("house_seller_type", house_seller_type);
		if(house_type >= 1 && house_type <= 9)request.setAttribute("house_type", house_type);
		if(house_deal_type >=1 && house_deal_type <=9)request.setAttribute("house_deal_type", house_deal_type);
		if(house_move_in >=1 && house_move_in <= 9)request.setAttribute("house_move_in", house_move_in);
		request.setAttribute("house_status", house_status);
		return "/WEB-INF/views/house/list.jsp";
	}

}
