package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.product.vo.Product_DetailVO;
import kr.product.vo.Product_MapVO;
import kr.util.StringUtil;

public class ProductDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//상품번호 반환
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		ProductDAO dao = ProductDAO.getInstance();
		dao.updateReadcount(product_num);
		ProductVO product = dao.getProduct(product_num);
		Product_DetailVO detail = dao.getProductDetail(product_num);
		Product_MapVO map = dao.getProductMap(product_num);
		int favcount = dao.selectFavCount(product_num);
		
		
		product.setProduct_title(StringUtil.useNoHtml(product.getProduct_title()));
		product.setProduct_content(StringUtil.useBrNoHtml(product.getProduct_content()));
		
		request.setAttribute("product", product);
		request.setAttribute("detail", detail);
		request.setAttribute("map", map);
		request.setAttribute("favcount", favcount);
		
		return "/WEB-INF/views/product/product_detail.jsp";
	}

}
