package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;

public class ProductDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//상품번호 반환
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		ProductDAO dao = ProductDAO.getInstance();
		
		dao.updateReadcount(product_num);
		ProductVO product = dao.getProduct(product_num);
		
		request.setAttribute("product", product);
		
		return "/WEB-INF/views/product/product_detail.jsp";
	}

}
