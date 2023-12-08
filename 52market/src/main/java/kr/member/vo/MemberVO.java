package kr.member.vo;

import java.sql.Date;

public class MemberVO {
	private int mem_num;		//회원 번호
	private String mem_id;			//아이디
	private int mem_auth;			//회원등급
	private String mem_nickname;
	private String mem_name;		//이름
	private String mem_passwd;		//비밀번호
	private String mem_phone;		//전화번호
	private String mem_email;		//이메일
	private String mem_zipcode;		//우편번호
	private String mem_address1;	//주소
	private String mem_address2;	//상세주소
	private String mem_photo;	//프로필 사진
	private Date mem_regdate;		//가입일
	
	//비밀번호 일치 여부 체크
		public boolean isCheckedPassword(String userPasswd) {
			//회원등급(mem_auth) : 0탈퇴회원,1정지회원,2일반회원,9관리자
			if(mem_auth > 1 && mem_passwd.equals(userPasswd)) {
				return true;
			}
			return false;
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
	public int getMem_auth() {
		return mem_auth;
	}
	public void setMem_auth(int mem_auth) {
		this.mem_auth = mem_auth;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public String getMem_passwd() {
		return mem_passwd;
	}
	public void setMem_passwd(String mem_passwd) {
		this.mem_passwd = mem_passwd;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public String getMem_email() {
		return mem_email;
	}
	public void setMem_email(String mem_email) {
		this.mem_email = mem_email;
	}
	public String getMem_zipcode() {
		return mem_zipcode;
	}
	public void setMem_zipcode(String mem_zipcode) {
		this.mem_zipcode = mem_zipcode;
	}
	public String getMem_address1() {
		return mem_address1;
	}
	public void setMem_address1(String mem_address1) {
		this.mem_address1 = mem_address1;
	}
	public String getMem_address2() {
		return mem_address2;
	}
	public void setMem_address2(String mem_address2) {
		this.mem_address2 = mem_address2;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	public Date getMem_regdate() {
		return mem_regdate;
	}
	public void setMem_regdate(Date mem_regdate) {
		this.mem_regdate = mem_regdate;
	}
	
	
	
	
	
}
