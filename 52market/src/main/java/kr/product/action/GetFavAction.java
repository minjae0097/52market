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

public class GetFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		ProductDAO dao = ProductDAO.getInstance();
		if(user_num == null) {
			mapAjax.put("status", "noFav");
		}else {
			Product_FavVO productFav = dao.selectFav(new Product_FavVO(product_num, user_num));
			
			if(productFav!=null) {
				mapAjax.put("status", "yesFav");
			}else {
				mapAjax.put("status", "noFav");
			}
		}
		mapAjax.put("count", dao.selectFavCount(product_num));
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
