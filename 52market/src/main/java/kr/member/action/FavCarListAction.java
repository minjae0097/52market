package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.controller.Action;
import kr.util.PageUtil;

public class FavCarListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		CarDAO car = CarDAO.getInstance();
		int count = car.getCarFavListCount(user_num);
		
		PageUtil page = new PageUtil(Integer.parseInt(pageNum), count, 10, 5, "favCarList.do");
		List<CarList_DetailVO> detail = null;
		if(count>0) {
			detail = car.getFavList(user_num, page.getStartRow(), page.getEndRow());
		}
		
		request.setAttribute("count", count);
		request.setAttribute("detail", detail);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/member/favCarList.jsp";
	}

}
