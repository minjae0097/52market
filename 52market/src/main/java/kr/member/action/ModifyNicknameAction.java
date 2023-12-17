package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ModifyNicknameAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//로그인 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//로그인 된 경우
		//전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String nickname= request.getParameter("mem_nickname");//아이디
		String passwd = request.getParameter("mem_passwd");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.getMember(user_num);
		boolean check = false;
		check = member.isCheckedPassword(passwd);
		if(check) {//인증 성공
			dao.updateNickname(nickname, user_num);
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/modifyNickname.jsp";
	}

}
