package kr.house.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.house.dao.HouseDAO;
import kr.house.vo.HouseFavVO;

public class WriteHouseFavAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String,Object> mapAjax = new HashMap<String,Object>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		//로그인 확인
		if(user_num == null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//전송된 데이터 반환
			int house_num = Integer.parseInt(request.getParameter("house_num"));
			
			HouseFavVO housefav = new HouseFavVO();
			housefav.setHouse_num(house_num);
			housefav.setMem_num(user_num);
			
			HouseDAO dao = HouseDAO.getInstance();
			//좋아요 등록 여부 체크
			HouseFavVO db_fav = dao.selectFav(housefav);
			
			//토글형태
			if(db_fav != null) {//좋아요 등록 O
				//좋아요 삭제
				dao.deleteHouseFav(db_fav);
				mapAjax.put("status", "noFav");
			}else {//좋아요 등록 X
				//좋아요 등록
				dao.insertHouseFav(housefav);
				mapAjax.put("status", "yesFav");
			}
			mapAjax.put("result", "success");
			mapAjax.put("count", dao.selectFavCount(house_num));//count값 넘겨줌
		}
		
		//JSON 문자열 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		//JSP 경로 반환
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
