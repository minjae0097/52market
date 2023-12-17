package kr.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_DetailVO;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProductDAO dao = ProductDAO.getInstance();
		List<Product_DetailVO> detail = dao.getListProduct(1, 9, null, null, 0);
		
		request.setAttribute("detail", detail);
		
		//JSP 경로 반환
		return "/WEB-INF/views/product/list.jsp";
	}
}