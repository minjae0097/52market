package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardFavVO;
import kr.controller.Action;

public class WriteFavAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			mapAjax.put("result", "logout");
		}else {
			request.setCharacterEncoding("utf-8");
			int board_num = Integer.parseInt(request.getParameter("board_num"));
			
			BoardFavVO favVO = new BoardFavVO();
			favVO.setBoard_num(board_num);
			favVO.setMem_num(user_num);
			
			BoardDAO dao = BoardDAO.getInstance();
			BoardFavVO db_fav = dao.selectFav(favVO);
			if(db_fav != null) {
				dao.deleteFav(favVO);
				mapAjax.put("status", "noFav");
			}else {
				dao.insertFav(favVO);
				mapAjax.put("status", "success");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectFavCount(board_num));
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
