package kr.product.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.product.dao.ChatProductDAO;
import kr.product.dao.ProductDAO;
import kr.product.vo.Product_DetailVO;

public class BuyChatProductAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		int chatroom_num = Integer.parseInt(request.getParameter("chatroom_num"));
		
		ChatProductDAO chat = ChatProductDAO.getInstance();
		Product_DetailVO productlist = chat.getProductByChatroom(chatroom_num);
		if(user_num.equals(productlist.getProduct_seller())) {
			request.setAttribute("notice_msg", "자신이 판매한 상품을 구매할 수 없습니다.");
			request.setAttribute("notice_url", 
					request.getContextPath()+"/chatting/chatDetailProduct.do?chatroom_num="+chatroom_num);
		}else {
			if(productlist.getProduct_buyer()>0) {
				request.setAttribute("notice_msg", "이미 판매된 상품입니다");
				request.setAttribute("notice_url", 
						request.getContextPath()+"/chatting/chatDetailProduct.do?chatroom_num="+chatroom_num);
			}else {
				ProductDAO product = ProductDAO.getInstance();
				product.sellProduct(productlist.getProduct_num(), user_num);
				product.updateProductStatusChat(chatroom_num);
				request.setAttribute("notice_msg", "구매확정이 완료되었습니다.");
				request.setAttribute("notice_url",
						request.getContextPath()+"/chatting/chatDetailProduct.do?chatroom_num="+chatroom_num);
			}
		}
		
		return "/WEB-INF/views/common/alert_singleView.jsp";
	}

}
