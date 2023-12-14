package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.product.vo.Product_DetailVO;
import kr.util.FileUtil;

public class ProductUpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) { //로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		int product_num = Integer.parseInt(multi.getParameter("product_num"));
		String filename = multi.getFilesystemName("product_image");
		
		ProductDAO dao = ProductDAO.getInstance();
		
		ProductVO db_product = dao.getProduct(product_num);
		if(user_num != db_product.getProduct_mem()) {
			FileUtil.removeFile(request, filename);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		ProductVO product = new ProductVO();
		product.setProduct_num(product_num);
		product.setProduct_title(multi.getParameter("product_title"));
		product.setProduct_content(multi.getParameter("product_content"));
		
		
		
		return null;
	}

}
