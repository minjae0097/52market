package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_DetailVO;

public class UpdateStatusAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 확인
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		
		ProductDAO product = ProductDAO.getInstance();
		Product_DetailVO db_detail = product.getProductDetail(product_num);
		
		if(user_num!=db_detail.getProduct_seller()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		int product_status = Integer.parseInt(request.getParameter("product_status"));
		product.updateProductStatus(product_status, product_num);
		
		return "redirect:/product/productDetail.do?product_num="+product_num;
	}

}
