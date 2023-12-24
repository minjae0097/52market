package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_DetailVO;
import kr.util.PageUtil;

public class SellProductListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null) pageNum = "1";
		
		ProductDAO product = ProductDAO.getInstance();
		int count = product.getSellListCount(user_num,null,null);
		PageUtil page = new PageUtil(Integer.parseInt(pageNum), count, 10, 5, "sellProductList.do");
		List<Product_DetailVO> productList = null;
		if(count>0) {
			productList = product.getSellList(user_num, page.getStartRow(),page.getEndRow(), null, null);
		}
		
		request.setAttribute("productList", productList);
		request.setAttribute("count", count);
		request.setAttribute("page", page.getPage());
		
		return "/WEB-INF/views/member/sellProductList.jsp";
	}

}
