package kr.alba.vo;

import java.sql.Date;

public class AlbalistVO {
	private int albalist_num;			//글 번호
	private String albalist_title;		//글 제목
	private String albalist_content;	//글 내용
	private Date albalist_reg_date;		//글 등록 날짜
	private Date albalist_modify_date;	//글 수정 날짜
	private String albalist_photo1;		//리스트에 나오는 사진
	private String albalist_photo2;		//글 내용에 나오는 사진
	private String albalist_ip;			//아이피
	private String albalist_location;	//알바 위치
	private int mem_num;				//회원번호
	private String albalist_filename;	//알바첨부파일
	
	private AlbalistVO albalistVO;

	public int getAlbalist_num() {
		return albalist_num;
	}
	public void setAlbalist_num(int albalist_num) {
		this.albalist_num = albalist_num;
	}
	public String getAlbalist_title() {
		return albalist_title;
	}
	public void setAlbalist_title(String albalist_title) {
		this.albalist_title = albalist_title;
	}
	public String getAlbalist_content() {
		return albalist_content;
	}
	public void setAlbalist_content(String albalist_content) {
		this.albalist_content = albalist_content;
	}
	public Date getAlbalist_reg_date() {
		return albalist_reg_date;
	}
	public void setAlbalist_reg_date(Date albalist_reg_date) {
		this.albalist_reg_date = albalist_reg_date;
	}
	public Date getAlbalist_modify_date() {
		return albalist_modify_date;
	}
	public void setAlbalist_modify_date(Date albalist_modify_date) {
		this.albalist_modify_date = albalist_modify_date;
	}
	public String getAlbalist_photo1() {
		return albalist_photo1;
	}
	public void setAlbalist_photo1(String albalist_photo1) {
		this.albalist_photo1 = albalist_photo1;
	}
	public String getAlbalist_photo2() {
		return albalist_photo2;
	}
	public void setAlbalist_photo2(String albalist_photo2) {
		this.albalist_photo2 = albalist_photo2;
	}
	public String getAlbalist_ip() {
		return albalist_ip;
	}
	public void setAlbalist_ip(String albalist_ip) {
		this.albalist_ip = albalist_ip;
	}
	public String getAlbalist_location() {
		return albalist_location;
	}
	public void setAlbalist_location(String albalist_location) {
		this.albalist_location = albalist_location;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getAlbalist_filename() {
		return albalist_filename;
	}
	public void setAlbalist_filename(String albalist_filename) {
		this.albalist_filename = albalist_filename;
	}
	public AlbalistVO getAlbalistVO() {
		return albalistVO;
	}
	public void setAlbalistVO(AlbalistVO albalistVO) {
		this.albalistVO = albalistVO;
	}
}