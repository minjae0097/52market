package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.StringUtil;

public class DetailQuestionAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		
		QnaDAO dao = QnaDAO.getInstance();
		QnaVO qna = dao.getQna(qna_num);
		
		qna.setQna_title(StringUtil.useNoHtml(qna.getQna_title()));
		qna.setQuestion_content(StringUtil.useBrNoHtml(qna.getQuestion_content()));
		
		request.setAttribute("qna", qna);
		
		return "/WEB-INF/views/qna/detail.jsp";
	}

}
