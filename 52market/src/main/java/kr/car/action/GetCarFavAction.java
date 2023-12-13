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

public class GetCarFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int carlist_num = Integer.parseInt(request.getParameter("carlist_num"));
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		CarDAO car = CarDAO.getInstance();
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		if(user_num==null) {
			mapAjax.put("status", "noFav");
		}else {
			Car_FavVO carfav = car.selectFav(new Car_FavVO(carlist_num,user_num));
			if(carfav!=null) {
				mapAjax.put("status", "yesFav");
			}else {
				mapAjax.put("status", "noFav");
			}
		}
		//좋아요 개수 보내기
		mapAjax.put("count", car.selectFavCount(carlist_num));
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
