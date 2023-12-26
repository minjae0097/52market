package kr.product.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ChatProductDAO;
import kr.product.vo.Product_ChatroomVO;

public class ChattingListForSellerProductAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 안 된 경우
			return "redirect:/member/loginForm.do";
		}
		String product_num = request.getParameter("product_num");
		ChatProductDAO chat = ChatProductDAO.getInstance();
		
		List<Product_ChatroomVO> list = chat.getChattingListForSellerProduct(Integer.parseInt(product_num), user_num);
		request.setAttribute("list", list);
		
		return "/WEB-INF/views/chatting/chattingListForSellerProduct.jsp";
	}

}