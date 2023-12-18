package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		int user_auth = (Integer)session.getAttribute("user_auth");
		
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		
		BoardDAO dao = BoardDAO.getInstance();
		
		dao.updateReadCount(board_num);
		
		BoardVO board = dao.getBoard(board_num);
		
		board.setBoard_title(StringUtil.useNoHtml(board.getBoard_title()));
		board.setBoard_content(StringUtil.useBrNoHtml(board.getBoard_content()));
		
		request.setAttribute("board", board);
		request.setAttribute("user_auth", user_auth);
		
		return "/WEB-INF/views/board/detail.jsp";
	}

}
