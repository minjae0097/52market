package kr.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_DetailVO;

public class ListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		int product_category = 0;
		if(request.getParameter("product_category")!=null) {
			product_category=Integer.parseInt(request.getParameter("product_category"));
		}
		
		//판매중인것만 보기
		int product_status = 1;
		if(request.getParameter("product_status")!=null) {
			product_status=0;
		}
		
		ProductDAO dao = ProductDAO.getInstance();
		int count = dao.getProductCount(keyfield, keyword, product_status, product_category);
		
		PageUtilProduct productpage = new PageUtilProduct(keyfield, keyword, Integer.parseInt(pageNum),count,9,5,"list.do",product_status);
		
		List<Product_DetailVO> productList = null;
		if(count>0) {
			productList = dao.getListProduct(productpage.getStartRow(), productpage.getEndRow(), keyfield, keyword, product_status, product_category);
		}
		
		
	
		
		request.setAttribute("count", count);
		request.setAttribute("productList", productList);
		request.setAttribute("page", productpage.getPage());
		request.setAttribute("product_status", product_status);
		
		//JSP 경로 반환
		return "/WEB-INF/views/product/list.jsp";
	}
}