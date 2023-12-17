package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class RegisterAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		MemberVO member = new MemberVO();
		member.setMem_auth(Integer.parseInt(request.getParameter("mem_auth")));
		member.setMem_id(request.getParameter("mem_id"));
		member.setMem_name(request.getParameter("mem_name"));
		member.setMem_nickname(request.getParameter("mem_nickname"));
		member.setMem_passwd(request.getParameter("mem_passwd"));
		member.setMem_phone(request.getParameter("mem_phone"));
		member.setMem_email(request.getParameter("mem_email"));
		member.setMem_zipcode(request.getParameter("mem_zipcode"));
		member.setMem_address1(request.getParameter("mem_address1"));
		member.setMem_address2(request.getParameter("mem_address2"));
		
		MemberDAO dao = MemberDAO.getInstance();
		dao.insertMember(member);
		
		return "/WEB-INF/views/member/register.jsp";
	}

}
