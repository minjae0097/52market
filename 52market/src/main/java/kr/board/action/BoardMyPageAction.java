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
import kr.util.PageUtil;

public class BoardMyPageAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		//회원 정보 청리
		MemberDAO memberDao = MemberDAO.getInstance();
		MemberVO member = memberDao.getMember(user_num);
		
		//address 처리
		String address = member.getMem_address1();
		int space1 = address.indexOf(" ");
		int space2 = address.indexOf(" ", space1+1);
		
		member.setMem_address1(address.substring(space1,space2));
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null) pageNum = "1";
		
		String keyfield = request.getParameter("keyfield");
		String keyword = request.getParameter("keyword");
		
		BoardDAO dao = BoardDAO.getInstance();

		int count = dao.getBoardCount(keyfield,keyword);
		
		//페이지 처리
		PageUtil page = new PageUtil(keyfield,keyword,Integer.parseInt(pageNum),count,10,10,"list.do");
		
		List<BoardVO> list = null;
		if(count > 0) {
			list = dao.getListBoard(page.getStartRow(),page.getEndRow(),keyfield,keyword);
		}
	
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("page", page.getPage());
		
		//좋아요 게시물, 댓글 게시물 정보
		BoardDAO boardDao = BoardDAO.getInstance();
		List<BoardVO> boardFavList = boardDao.getListBoardFav(1, 10, user_num);
		List<BoardReplyVO> boardReplyList = boardDao.getListBoardReply(1, 10, user_num);

		request.setAttribute("member", member);
		request.setAttribute("boardFavList", boardFavList);
		request.setAttribute("boardReplyList", boardReplyList);
		
		return "/WEB-INF/views/board/board_myPage.jsp";
	}

}
