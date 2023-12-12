package kr.qna.vo;

import java.sql.Date;

public class QnaVO {
	private int qna_num;				//문의글번호
	private int mem_num;				//회원번호
	private String qna_title;			//문의글 제목
	private String question_content;	//문의글 내용
	private String question_filename;	//문의글 이미지
	private Date question_regdate;		//문의글 등록 날짜
	
	private String ask_content;			//답변글 내용
	private Date ask_regdate;			//답변글 등록 날짜
	
	private String mem_nickname;			//member(닉네임)
	private String mem_photo;				//member(프로필사진)
	
	public int getQna_num() {
		return qna_num;
	}
	public void setQna_num(int qna_num) {
		this.qna_num = qna_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getQna_title() {
		return qna_title;
	}
	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	public String getQuestion_content() {
		return question_content;
	}
	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}
	public String getQuestion_filename() {
		return question_filename;
	}
	public void setQuestion_filename(String question_filename) {
		this.question_filename = question_filename;
	}
	public Date getQuestion_regdate() {
		return question_regdate;
	}
	public void setQuestion_regdate(Date question_regdate) {
		this.question_regdate = question_regdate;
	}
	public String getAsk_content() {
		return ask_content;
	}
	public void setAsk_content(String ask_content) {
		this.ask_content = ask_content;
	}
	public Date getAsk_regdate() {
		return ask_regdate;
	}
	public void setAsk_regdate(Date ask_regdate) {
		this.ask_regdate = ask_regdate;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
}
