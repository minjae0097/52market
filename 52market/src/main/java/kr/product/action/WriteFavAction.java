package kr.product.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_FavVO;

public class WriteFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) { //로그인x
			mapAjax.put("result", "logout");
		}else {
			request.setCharacterEncoding("utf-8");
			int product_num = Integer.parseInt(request.getParameter("product_num"));
			
			//데이터 세팅
			Product_FavVO favVO = new Product_FavVO();
			favVO.setProduct_num(product_num);
			favVO.setMem_num(user_num);
			
			//좋아요 동작여부
			ProductDAO dao = ProductDAO.getInstance();
			Product_FavVO db_fav = dao.selectFav(favVO);
			
			if(db_fav!=null) {
				dao.deleteFav(db_fav);
				mapAjax.put("status", "noFav");
			}else {
				dao.insertFav(favVO);
				mapAjax.put("status", "yesFav");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectFavCount(product_num));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax); //status: object -> string 
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
