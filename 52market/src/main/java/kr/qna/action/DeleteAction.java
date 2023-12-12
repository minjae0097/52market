package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		int qna_num = Integer.parseInt(request.getParameter("qna_num"));
		QnaDAO dao = QnaDAO.getInstance();
		QnaVO db_qna = dao.getQna(qna_num);
		if(user_num != db_qna.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		dao.deleteQna(qna_num);
		FileUtil.removeFile(request, db_qna.getQuestion_filename());
		
		return "redirect:/qna/list.do";
	}

}
