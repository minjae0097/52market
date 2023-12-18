package kr.board.vo;

import java.sql.Date;

public class BoardVO {
	private int board_category;
	private int board_num;					//글번호
	private String board_title;				//글제목
	private String board_content;			//글내용
	private int board_hit;					//조회수
	private Date board_regdate;				//등록일
	private Date board_modifydate;			//수정일
	private String board_filename;			//파일명
	private String board_ip;				//ip주소
	private int mem_num;					//회원번호
	
	private String mem_id;
	private String mem_nickname;
	private String mem_photo;
	private String mem_address1;
	private String short_address;
	
	
	
	public int getBoard_category() {
		return board_category;
	}
	public void setBoard_category(int board_category) {
		this.board_category = board_category;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getBoard_title() {
		return board_title;
	}
	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public int getBoard_hit() {
		return board_hit;
	}
	public void setBoard_hit(int board_hit) {
		this.board_hit = board_hit;
	}
	public Date getBoard_regdate() {
		return board_regdate;
	}
	public void setBoard_regdate(Date board_regdate) {
		this.board_regdate = board_regdate;
	}
	public Date getBoard_modifydate() {
		return board_modifydate;
	}
	public void setBoard_modifydate(Date board_modifydate) {
		this.board_modifydate = board_modifydate;
	}
	public String getBoard_filename() {
		return board_filename;
	}
	public void setBoard_filename(String board_filename) {
		this.board_filename = board_filename;
	}
	public String getBoard_ip() {
		return board_ip;
	}
	public void setBoard_ip(String board_ip) {
		this.board_ip = board_ip;
	}
	
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_id() {
		return mem_id;
	}
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	public String getMem_address1() {
		return mem_address1;
	}
	public void setMem_address1(String mem_address1) {
		this.mem_address1 = mem_address1;
	}
	public void setMem_address11(String string) {
		// TODO Auto-generated method stub
		
	}
	public String getShort_address() {
		return short_address;
	}
	public void setShort_address(String short_address) {
		this.short_address = short_address;
	}
	
	
}
