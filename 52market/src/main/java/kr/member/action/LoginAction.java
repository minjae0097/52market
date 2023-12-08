package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
  
public class LoginAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String id= request.getParameter("id");
		String passwd = request.getParameter("passwd");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO vo = dao.checkMember(id);
		boolean check = false;
		
		if(vo!=null) {
			//비밀번호 일치 여부 체크
			check = vo.isCheckedPassword(passwd);
			//정지회원의 경우 상태 표시
			request.setAttribute("auth", vo.getMem_auth());
		}
		if(check) {//인증 성공
			HttpSession session = request.getSession();
			session.setAttribute("user_num", vo.getMem_num());
			session.setAttribute("user_id", vo.getMem_id());
			session.setAttribute("user_auth", vo.getMem_auth());
			session.setAttribute("user_photo", vo.getMem_photo());
			//메인으로 리다이렉트
			return "redirect:/main/main.do";
		}  
		//인증 실패
		return "/WEB-INF/views/member/login.jsp";
	}

}
