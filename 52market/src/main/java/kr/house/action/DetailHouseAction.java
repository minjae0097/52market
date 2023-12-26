package kr.house.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.house.vo.HouseListVO;
import kr.member.vo.MemberVO;
import kr.util.StringUtil;

public class DetailHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int house_num = Integer.parseInt(request.getParameter("house_num"));
		
		HouseDAO dao = HouseDAO.getInstance();
		//조회수 증가
		dao.updateReadcount(house_num);
		
		HouseDetailVO detail = dao.getHouseDetail(house_num);
		HouseListVO list = dao.getHouseList(house_num);
		MemberVO seller = dao.getHouseMember(detail.getMem_num());
		int favcount = dao.selectFavCount(house_num);
		
		detail.setHouse_title(StringUtil.useNoHtml(detail.getHouse_title()));
		list.setHouse_content(StringUtil.useBrNoHtml(list.getHouse_content()));
		
		request.setAttribute("detail", detail);
		request.setAttribute("list", list);
		request.setAttribute("seller", seller);
		request.setAttribute("favcount", favcount);
		
		//JSP 경로 반환
		return "/WEB-INF/views/house/detailHouse.jsp";
	}

}
