package kr.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardReplyVO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class BoardMyPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		MemberDAO dao = MemberDAO.getInstance();
		//MemberVO member = dao.getMember(user_num);
		
		BoardDAO boardDao = BoardDAO.getInstance();
		List<BoardVO> boardFavList = boardDao.getListBoardFav(1, 5, user_num);
		List<BoardReplyVO> boardReplyList = boardDao.getListReplyBoard(1, 5, user_num);
		
		//request.setAttribute("member", member);
		request.setAttribute("boardFavList", boardFavList);
		request.setAttribute("boardReplyList", boardReplyList);
		
		return "/WEB-INF/views/board/board_myPage.jsp";
	}

}
