package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.FileUtil;

public class UpdateAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//로그인 체크
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		//동일한 작성자인지 확인
		MultipartRequest multi = FileUtil.createFile(request);
		int board_num = Integer.parseInt(multi.getParameter("board_num"));
		//filename 읽어오기
		String board_filename = multi.getFilesystemName("board_filename");
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO db_board = dao.getBoard(board_num);
		
		if(user_num != db_board.getMem_num()) {
			FileUtil.removeFile(request, board_filename);
			return "/WEB-INF/views/common/notice.jsp";
		}
		
		BoardVO board = new BoardVO();
		board.setBoard_num(board_num);
		board.setBoard_title(multi.getParameter("board_title"));
		board.setBoard_content(multi.getParameter("board_content"));
		board.setBoard_ip(request.getRemoteAddr());
		board.setBoard_filename(board_filename);
		
		dao.updateBoard(board);
		if(board_filename != null) {
			FileUtil.removeFile(request, db_board.getBoard_filename());
		}
		
		return "redirect:/board/detail.do?board_num=" + board_num;
	}

}
