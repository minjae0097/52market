package kr.alba.dao;

public class AlbalistDAO {
	private static AlbalistDAO instance = new AlbalistDAO();
	
	public static AlbalistDAO getInstance() {
		return instance;
	}
	
	private AlbalistDAO() {}
	
	//글등록
	//전체 레코드수/검색 레코드수
	//전체 글/검색 글 목록
	//글 상세
	//조회수 증가
	//파일 삭제
	//글 수정
	//글 삭제
	//관심(좋아요) 등록
	//관심(좋아요) 개수
	//회원번호와 게시물 번호를 이용한 관심(좋아요) 정보(좋아요 선택 여부)
	//관심(좋아요) 삭제
	//내가 선택한 관심(좋아요) 목록
}
