package kr.house.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.util.StringUtil;

public class DetailHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int house_num = Integer.parseInt(request.getParameter("house_num"));
		
		HouseDAO dao = HouseDAO.getInstance();
		HouseDetailVO detail = dao.getHouseList_Detail(house_num);
		
		
		detail.setHouse_title(StringUtil.useNoHtml(detail.getHouse_title()));
		
		
		//JSP 경로 반환
		return null;
	}

}
