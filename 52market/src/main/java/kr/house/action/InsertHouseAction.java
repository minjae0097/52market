package kr.house.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseDetailVO;
import kr.house.vo.HouseListVO;
import kr.util.FileUtil;

public class InsertHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 체크
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);		
		HouseListVO list = new HouseListVO();
		HouseDetailVO detail = new HouseDetailVO();
		list.setHouse_content(multi.getParameter("house_content"));
		
		detail.setMem_num(user_num);
		detail.setHouse_title(multi.getParameter("house_title"));
		detail.setHouse_seller_type(Integer.parseInt(multi.getParameter("house_seller_type")));
		detail.setHouse_type(Integer.parseInt(multi.getParameter("house_type")));
		detail.setZipcode(multi.getParameter("zipcode"));
		detail.setHouse_address1(multi.getParameter("house_address1"));
		detail.setHouse_address2(multi.getParameter("house_address2"));
		detail.setHouse_deal_type(Integer.parseInt(multi.getParameter("house_deal_type")));
		detail.setHouse_price(Long.parseLong(multi.getParameter("house_price")));
		detail.setHouse_space(Integer.parseInt(multi.getParameter("house_space")));
		detail.setHouse_floor(Integer.parseInt(multi.getParameter("house_floor")));
		detail.setHouse_diposit(Long.parseLong(multi.getParameter("house_diposit")));
		detail.setHouse_cost(Integer.parseInt(multi.getParameter("house_cost")));
		detail.setHouse_move_in(Integer.parseInt(multi.getParameter("house_move_in")));
		detail.setHouse_photo1(multi.getFilesystemName("house_photo1"));
		detail.setHouse_photo2(multi.getFilesystemName("house_photo2"));
		
		HouseDAO dao = HouseDAO.getInstance();
		dao.insertHouse(list, detail);
		
		//JSP 경로 반환
		return "/WEB-INF/views/house/insertHouse.jsp";
	}

}
