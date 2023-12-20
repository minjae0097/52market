package kr.alba.vo;

import java.sql.Date;

public class AplistVO {
	private int aplist_num;
	private int alba_num;
	private int mem_num;
	private Date aplist_reg_date;
	private String alba_filename;
	private String alba_title;
	private String mem_nickname;
	
	
	private AplistVO aplistVO;

	public int getAplist_num() {
		return aplist_num;
	}
	public void setAplist_num(int aplist_num) {
		this.aplist_num = aplist_num;
	}
	public int getAlba_num() {
		return alba_num;
	}
	public void setAlba_num(int alba_num) {
		this.alba_num = alba_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public Date getAplist_reg_date() {
		return aplist_reg_date;
	}
	public void setAplist_reg_date(Date aplist_reg_date) {
		this.aplist_reg_date = aplist_reg_date;
	}
	public String getAlba_filename() {
		return alba_filename;
	}
	public void setAlba_filename(String alba_filename) {
		this.alba_filename = alba_filename;
	}
	public String getAlba_title() {
		return alba_title;
	}
	public void setAlba_title(String alba_title) {
		this.alba_title = alba_title;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public AplistVO getAplistVO() {
		return aplistVO;
	}
	public void setAplistVO(AplistVO aplistVO) {
		this.aplistVO = aplistVO;
	}
}