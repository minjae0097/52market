package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_DetailVO;
import kr.product.vo.Product_MapVO;
import kr.product.vo.ProductVO;
import kr.util.FileUtil;

public class ProductWriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		//로그인 된 경우 글쓰기 가능
		MultipartRequest multi = FileUtil.createFile(request);
		ProductVO product = new ProductVO();
		Product_DetailVO detail = new Product_DetailVO();
		Product_MapVO map = new Product_MapVO();
		
		//product
		product.setProduct_title(multi.getParameter("product_title"));
		product.setProduct_image1(multi.getFilesystemName("product_image"));
		product.setProduct_content(multi.getParameter("product_content"));
		product.setProduct_mem(user_num);
		//detail
		detail.setProduct_seller(user_num);
		detail.setProduct_category(Integer.parseInt(multi.getParameter("product_category")));
		detail.setProduct_price(Integer.parseInt(multi.getParameter("product_price")));
		detail.setProduct_image(multi.getFilesystemName("product_image"));
		detail.setProduct_name(multi.getParameter("product_name"));
		//map
		map.setLocation_x(multi.getParameter("location_x"));
		map.setLocation_y(multi.getParameter("location_y"));
		map.setLocation(multi.getParameter("location"));
		map.setRoad_address_name(multi.getParameter("road_address_name"));
		map.setAddress_name(multi.getParameter("address_name"));
		
		ProductDAO dao = ProductDAO.getInstance();
		dao.insertProduct(product,detail,map);
		
		return "/WEB-INF/views/product/product_write.jsp";
	}

}
