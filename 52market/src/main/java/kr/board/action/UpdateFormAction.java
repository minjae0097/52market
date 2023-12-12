package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class UpdateFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//회원번호와 작성자 회원번호 일치 확인
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO db_board = dao.getBoard(board_num);
		
		if(user_num != db_board.getMem_num()) {
			return "/WEB-INF/views/common/notice.jsp"; //잘못된 접속입니다.
		}
		
		db_board.setBoard_title(StringUtil.parseQuot(db_board.getBoard_title()));
		request.setAttribute("board", db_board);
		
		return "/WEB-INF/views/board/updateForm.jsp";
	}

}
