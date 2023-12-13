package kr.car.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.car.dao.CarDAO;
import kr.car.vo.Car_FavVO;
import kr.controller.Action;

public class WriteCarFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 확인
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			request.setCharacterEncoding("utf-8");
			
			int carlist_num = Integer.parseInt(request.getParameter("carlist_num"));
			Car_FavVO carfav = new Car_FavVO();
			carfav.setCarlist_num(carlist_num);
			carfav.setMem_num(user_num);
			
			CarDAO car = CarDAO.getInstance();
			//좋아요 등록여부 확인
			Car_FavVO db_fav = car.selectFav(carfav);
			
			if(db_fav!=null) {//좋아요 등록 O
				//좋아요 삭제
				car.deleteCarFav(db_fav);;
				mapAjax.put("status", "noFav");
			}else {//좋아요 등록 X
				//좋아요 등록
				car.insertCarFav(carfav);;
				mapAjax.put("status", "yesFav");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", car.selectFavCount(carlist_num));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
