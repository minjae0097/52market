package kr.qna.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.qna.dao.QnaDAO;
import kr.qna.vo.QnaVO;
import kr.util.FileUtil;

public class WriteQAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {
			return "redirect:/member/loginForm.do";
		}
		
		MultipartRequest multi = FileUtil.createFile(request);
		QnaVO qna = new QnaVO();
		qna.setMem_num(user_num);
		qna.setQna_title(multi.getParameter("qna_title"));
		qna.setQuestion_content(multi.getParameter("question_content"));
		qna.setQuestion_filename(multi.getFilesystemName("question_filename"));
		
		QnaDAO dao = QnaDAO.getInstance();
		dao.insertQna(qna);
		
		return "/WEB-INF/views/qna/q_write.jsp";
	}

}
