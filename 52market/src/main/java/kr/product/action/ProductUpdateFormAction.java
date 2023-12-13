package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.util.StringUtil;

public class ProductUpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//로그인한 회원번호와 작성자 회원번호 일치 체크
		int product_num = Integer.parseInt(request.getParameter("product_num"));
		ProductDAO dao = ProductDAO.getInstance();
		ProductVO db_product = dao.getProduct(product_num);
		
		if(user_num != db_product.getProduct_mem()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		db_product.setProduct_title(StringUtil.parseQuot(db_product.getProduct_title()));
		request.setAttribute("product", db_product);
		
		return "/WEB-INF/views/product/product_updateForm.jsp";
	}

}
