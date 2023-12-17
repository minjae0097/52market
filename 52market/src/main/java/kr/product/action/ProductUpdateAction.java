package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.ProductVO;
import kr.product.vo.Product_DetailVO;
import kr.product.vo.Product_MapVO;
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
		String product_image = multi.getFilesystemName("product_image");
		
		ProductDAO dao = ProductDAO.getInstance();
		
		ProductVO db_product = dao.getProduct(product_num);
		if(user_num != db_product.getProduct_mem()) {
			//로그인한 회원번호와 작성자 회원번호가 불일치
			FileUtil.removeFile(request, product_image);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		ProductVO product = new ProductVO();
		Product_DetailVO detail = new Product_DetailVO();
		Product_MapVO map = new Product_MapVO();
		
		//Product
		product.setProduct_title(multi.getParameter("product_title"));
		product.setProduct_content(multi.getParameter("product_content"));
		if(product_image==null) {
			product.setProduct_image(db_product.getProduct_image());
		}else {
			product.setProduct_image(product_image);
		}
		//Detail
		detail.setProduct_category(Integer.parseInt(multi.getParameter("product_category")));
		detail.setProduct_price(Integer.parseInt(multi.getParameter("product_price")));
		detail.setProduct_name(multi.getParameter("product_name"));
		if(product_image==null) {
			detail.setProduct_image(db_product.getProduct_image());
		}else {
			detail.setProduct_image(product_image);
		}
		//Map
		map.setLocation_x(multi.getParameter("location_x"));
		map.setLocation_y(multi.getParameter("location_y"));
		map.setLocation(multi.getParameter("location"));
		map.setRoad_address_name(multi.getParameter("road_address_name"));
		map.setAddress_name(multi.getParameter("address_name"));
		
		dao.updateProduct(product, detail, map, product_num);
		if(product_image!=null) {
			FileUtil.removeFile(request, db_product.getProduct_image());
		}
		
		return "redirect:/product/productDetail.do?product_num="+product_num;
	}

}
