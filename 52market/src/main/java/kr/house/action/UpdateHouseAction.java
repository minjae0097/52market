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

public class UpdateHouseAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int house_num = Integer.parseInt(multi.getParameter("house_num"));
		System.out.println("house_num : " + house_num);
		String house_photo1 = multi.getFilesystemName("house_photo1");
		String house_photo2 = multi.getFilesystemName("house_photo2");
		
		HouseDAO dao = HouseDAO.getInstance();
		//수정전 데이터 반환
		HouseDetailVO db_detail = dao.getHouseDetail(house_num);
		if(user_num != db_detail.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, house_photo1);
			FileUtil.removeFile(request, house_photo2);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		HouseListVO list = new HouseListVO();
		HouseDetailVO detail = new HouseDetailVO();
		
		list.setHouse_content(multi.getParameter("house_content"));
		detail.setHouse_num(house_num);
		detail.setHouse_title(multi.getParameter("house_title"));
		detail.setHouse_seller_type(Integer.parseInt(multi.getParameter("house_seller_type")));
		detail.setHouse_type(Integer.parseInt(multi.getParameter("house_type")));
		detail.setZipcode(multi.getParameter("zipcode"));
		detail.setHouse_address1(multi.getParameter("house_address1"));
		detail.setHouse_address2(multi.getParameter("house_address2"));
		detail.setHouse_deal_type(Integer.parseInt(multi.getParameter("house_deal_type")));
		detail.setHouse_price(Long.parseLong(multi.getParameter("house_price")));
		detail.setHouse_diposit(Long.parseLong(multi.getParameter("house_diposit")));
		detail.setHouse_cost(Integer.parseInt(multi.getParameter("house_cost")));
		detail.setHouse_space(Integer.parseInt(multi.getParameter("house_space")));
		detail.setHouse_floor(Integer.parseInt(multi.getParameter("house_floor")));
		detail.setHouse_move_in(Integer.parseInt(multi.getParameter("house_move_in")));
		if(house_photo1 == null) {
			detail.setHouse_photo1(db_detail.getHouse_photo1());
		}else {
			detail.setHouse_photo1(house_photo1);
		}
		if(house_photo2 == null) {
			detail.setHouse_photo2(db_detail.getHouse_photo2());
		}else {
			detail.setHouse_photo2(house_photo2);
		}
		
		//글 수정
		dao.updateHouse(list, detail);
		
		if(house_photo1!=null) {//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_detail.getHouse_photo1());			
		}			
		if(house_photo2!=null) {
			FileUtil.removeFile(request, db_detail.getHouse_photo2());
		}
		
		//JSP 경로 반환
		return "redirect:/house/detailHouse.do?house_num=" + house_num;
	}

}
