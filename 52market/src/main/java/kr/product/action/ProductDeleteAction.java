package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.FileUtil;

public class ProductDeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		ProductDAO dao = ProductDAO.getInstance();
		ProductVO db_product = dao.getProduct(product_num);
		if(user_num != db_product.getProduct_mem()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		dao.deleteProduct(product_num);
		FileUtil.removeFile(request, db_product.getProduct_image1());
		
		return "redirect:/product/list.do";
	}

}
