package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.util.PageUtil;

public class BuyHouseListAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		HouseDAO house = HouseDAO.getInstance();
		int count = house.getBuyListCount(user_num, null, null);
		PageUtil page = new PageUtil(Integer.parseInt(pageNum),count,10,5,"buyHouseList.do");
		List<HouseDetailVO> houseList = null;
		if(count>0) {
			houseList = house.getBuyList(user_num, page.getStartRow(), page.getEndRow(),null,null);
		}
		
		request.setAttribute("houseList", houseList);
		request.setAttribute("count", count);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/member/sellHouseList.jsp";
	}

}
