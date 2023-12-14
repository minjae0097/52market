package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class UpdateAAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 x
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		//관리자x
		if(user_auth!=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		request.setCharacterEncoding("utf-8");
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		QnaDAO dao = QnaDAO.getInstance();
		
		QnaVO qna = new QnaVO();
		qna.setQna_num(qna_num);
		qna.setAsk_content(request.getParameter("ask_content"));
		
		dao.updateAsk(qna);

		return "redirect:/qna/detail.do?qna_num=" + qna_num;
	}

}
