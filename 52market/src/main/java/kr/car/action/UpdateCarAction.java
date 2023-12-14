package kr.car.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.car.dao.CarDAO;
import kr.car.vo.CarList_DetailVO;
import kr.car.vo.Car_MapVO;
import kr.car.vo.CarlistVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class UpdateCarAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int carlist_num = Integer.parseInt(multi.getParameter("carlist_num"));
		String car_image = multi.getFilesystemName("car_image");
		
		CarDAO car = CarDAO.getInstance();
		//수정전 데이터 반환
		CarList_DetailVO db_detail = car.getCarList_Detail(carlist_num);
		if(user_num != db_detail.getCar_seller()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, car_image);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		
		CarlistVO list = new CarlistVO();
		CarList_DetailVO detail = new CarList_DetailVO();
		Car_MapVO map = new Car_MapVO();
		
		list.setCarlist_content(multi.getParameter("carlist_content"));
		
		detail.setCar_type((multi.getParameter("car_type")));
		detail.setCar_fuel((multi.getParameter("car_fuel")));
		detail.setCar_price(Integer.parseInt(multi.getParameter("car_price")));
		detail.setCar_model_year(Integer.parseInt(multi.getParameter("car_model_year")));
		detail.setCar_distance(Integer.parseInt(multi.getParameter("car_distance")));
		detail.setCar_transmission((multi.getParameter("car_transmission")));
		detail.setCar_origin((multi.getParameter("car_origin")));
		if(car_image==null) {
			detail.setCar_image(db_detail.getCar_image());
		}else {
			detail.setCar_image(car_image);
		}
		detail.setCar_title(multi.getParameter("car_title"));
		
		map.setLocation_x(multi.getParameter("location_x"));
		map.setLocation_y(multi.getParameter("location_y"));
		map.setLocation(multi.getParameter("location"));
		map.setRoad_address_name(multi.getParameter("road_address_name"));
		map.setAddress_name(multi.getParameter("address_name"));
		
		//글 수정
		car.updateCar(list, detail, map, carlist_num);
		
		if(car_image!=null) {//새 파일로 교체할 때 원래 파일 제거
			FileUtil.removeFile(request, db_detail.getCar_image());			
		}
		
		return "redirect:/car/detailCar.do?carlist_num=" + carlist_num;
		
	}

}
