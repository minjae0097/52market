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

public class GetAlbaFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int alba_num = Integer.parseInt(request.getParameter("alba_num"));
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		AlbaDAO dao = AlbaDAO.getInstance();
		Map<String, Object> mapAjax = new HashMap<String, Object>();
		
		if(user_num == null) {
			mapAjax.put("status", "noFav");
		}else {
			Alba_FavVO albafav = dao.selectFav(new Alba_FavVO(alba_num, user_num));
			if(albafav!=null) {
				mapAjax.put("status", "yesFav");
			}else {
				mapAjax.put("status", "noFav");
			}
			//좋아요 개수 보내기
			mapAjax.put("count", dao.selectFavCount(alba_num));
			ObjectMapper mapper = new ObjectMapper();
			String ajaxData = mapper.writeValueAsString(mapAjax);
			
			request.setAttribute("ajaxData", ajaxData);
		}
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
