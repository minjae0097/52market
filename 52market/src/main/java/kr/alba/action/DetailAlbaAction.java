package kr.alba.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.alba.dao.AlbaDAO;
import kr.alba.vo.AlbaVO;
import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.StringUtil;

public class DetailAlbaAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int alba_num = Integer.parseInt(request.getParameter("alba_num")); 
		
		AlbaDAO dao = AlbaDAO.getInstance();
		
		dao.updateReadcount(alba_num);
		
		AlbaVO alba = dao.getAlba(alba_num);
		
		MemberDAO memberDao = MemberDAO.getInstance();
		MemberVO db_member = memberDao.getMember(alba.getMem_num());
		
		alba.setAlba_title(StringUtil.useNoHtml(alba.getAlba_title()));
		alba.setAlba_content1(StringUtil.useBrNoHtml(alba.getAlba_content1()));
		alba.setAlba_content2(StringUtil.useBrNoHtml(alba.getAlba_content2()));
		db_member.setMem_phone(StringUtil.useBrNoHtml(db_member.getMem_phone()));
		
		request.setAttribute("alba", alba);
		request.setAttribute("db_member", db_member);
		
		return "/WEB-INF/views/alba/detailAlba.jsp";
	}

}
