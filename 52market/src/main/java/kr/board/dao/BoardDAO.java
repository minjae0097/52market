package kr.board.dao;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private BoardDAO() {}
	
	//[[[[[게시글 등록]]]]]
	//글 등록
	//글 상세
	//글 수정
	//글 삭제
	//전체&검색 레코드 수
	//전체&검색 글 목록
	//파일 삭제
	
	//[[[[[조회수]]]]]
	//조회수 증가
	
	//[[[[[좋아요]]]]]
	//좋아요 등록
	//좋아요 개수
	//좋아요 삭제
	//내가 선택한 좋아요 목록
	//회원번호와 게시물 번호를 이용한 좋아요 정보(좋아요 선택 여부)
	
	//[[[[[댓글]]]]]
	//댓글 등록
	//댓글 개수
	//댓글 목록
	//댓글 상세
	//댓글 수정
	//댓글 삭제
}
