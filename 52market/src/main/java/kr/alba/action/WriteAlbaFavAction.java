package kr.alba.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.alba.dao.AlbaDAO;
import kr.alba.vo.Alba_FavVO;
import kr.controller.Action;

public class WriteAlbaFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			request.setCharacterEncoding("utf-8");
			
			int alba_num = Integer.parseInt(request.getParameter("alba_num"));
			Alba_FavVO albafav = new Alba_FavVO();
			albafav.setAlba_num(alba_num);
			albafav.setMem_num(user_num);
			
			AlbaDAO dao = AlbaDAO.getInstance();
			//좋아요 등록여부 확인
			Alba_FavVO vo_fav = dao.selectFav(albafav);
			
			if(vo_fav!=null) {

				dao.deleteAlbaFav(vo_fav);;
				mapAjax.put("status", "noFav");
			}else {

				dao.insertAlbaFav(albafav);;
				mapAjax.put("status", "yesFav");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectFavCount(alba_num));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
		
	}

}
