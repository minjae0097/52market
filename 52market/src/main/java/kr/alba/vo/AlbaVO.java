package kr.alba.vo;

import java.sql.Date;

public class AlbaVO {
	private int alba_num;			//글 번호
	private String alba_title;		//글 제목
	private String alba_content1;	//글 정보
	private String alba_content2;	//글 상세정보
	private Date alba_reg_date;		//글 등록 날짜
	private Date alba_modify_date;	//글 수정 날짜
	private String alba_photo;		//알바 사진
	private String alba_ip;			//아이피
	private String alba_location;	//알바 위치
	private String alba_zipcode;	//우편번호
	private String alba_address1;//알바 주소
	private String alba_address2;//알바 상세주소
	private int mem_num;			//회원번호
	private String alba_filename;	//알바첨부파일
	
	private AlbaVO albaVO;

	public int getAlba_num() {
		return alba_num;
	}
	public void setAlba_num(int alba_num) {
		this.alba_num = alba_num;
	}
	public String getAlba_title() {
		return alba_title;
	}
	public void setAlba_title(String alba_title) {
		this.alba_title = alba_title;
	}
	public String getAlba_content1() {
		return alba_content1;
	}
	public void setAlba_content1(String alba_content1) {
		this.alba_content1 = alba_content1;
	}
	public String getAlba_content2() {
		return alba_content2;
	}
	public void setAlba_content2(String alba_content2) {
		this.alba_content2 = alba_content2;
	}
	public Date getAlba_reg_date() {
		return alba_reg_date;
	}
	public void setAlba_reg_date(Date alba_reg_date) {
		this.alba_reg_date = alba_reg_date;
	}
	public Date getAlba_modify_date() {
		return alba_modify_date;
	}
	public void setAlba_modify_date(Date alba_modify_date) {
		this.alba_modify_date = alba_modify_date;
	}
	public String getAlba_photo() {
		return alba_photo;
	}
	public void setAlba_photo(String alba_photo) {
		this.alba_photo = alba_photo;
	}
	public String getAlba_ip() {
		return alba_ip;
	}
	public void setAlba_ip(String alba_ip) {
		this.alba_ip = alba_ip;
	}
	public String getAlba_location() {
		return alba_location;
	}
	public void setAlba_location(String alba_location) {
		this.alba_location = alba_location;
	}
	public String getAlba_zipcode() {
		return alba_zipcode;
	}
	public void setAlba_zipcode(String alba_zipcode) {
		this.alba_zipcode = alba_zipcode;
	}
	public String getAlba_address1() {
		return alba_address1;
	}
	public void setAlba_address1(String alba_address1) {
		this.alba_address1 = alba_address1;
	}
	public String getAlba_address2() {
		return alba_address2;
	}
	public void setAlba_address2(String alba_address2) {
		this.alba_address2 = alba_address2;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getAlba_filename() {
		return alba_filename;
	}
	public void setAlba_filename(String alba_filename) {
		this.alba_filename = alba_filename;
	}
	public AlbaVO getAlbaVO() {
		return albaVO;
	}
	public void setAlbaVO(AlbaVO albaVO) {
		this.albaVO = albaVO;
	}
}
