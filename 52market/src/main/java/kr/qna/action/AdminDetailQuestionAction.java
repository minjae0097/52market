package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.StringUtil;

public class AdminDetailQuestionAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		//로그인 x
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_auth = (Integer)session.getAttribute("user_auth");
		//관리자x
		if(user_auth!=9) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		QnaDAO dao = QnaDAO.getInstance();
		QnaVO qna = dao.getQna(qna_num);
		
		qna.setQna_title(StringUtil.useNoHtml(qna.getQna_title()));
		qna.setQuestion_content(StringUtil.useBrNoHtml(qna.getQuestion_content()));
		
		request.setAttribute("qna", qna);
		
		return "/WEB-INF/views/qna/admin_detail.jsp";
	}

}
