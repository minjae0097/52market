package kr.alba.vo;

import java.sql.Date;

public class AplistVO {
	private int aplist_num;
	private int albalist_num;
	private int mem_num;
	private String aplist_title;
	private Date aplist_reg_date;
	private String albalist_filename;
	
	private AplistVO aplistVO;

	public int getAplist_num() {
		return aplist_num;
	}
	public void setAplist_num(int aplist_num) {
		this.aplist_num = aplist_num;
	}
	public int getAlbalist_num() {
		return albalist_num;
	}
	public void setAlbalist_num(int albalist_num) {
		this.albalist_num = albalist_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getAplist_title() {
		return aplist_title;
	}
	public void setAplist_title(String aplist_title) {
		this.aplist_title = aplist_title;
	}
	public Date getAplist_reg_date() {
		return aplist_reg_date;
	}
	public void setAplist_reg_date(Date aplist_reg_date) {
		this.aplist_reg_date = aplist_reg_date;
	}
	public String getAlbalist_filename() {
		return albalist_filename;
	}
	public void setAlbalist_filename(String albalist_filename) {
		this.albalist_filename = albalist_filename;
	}
	public AplistVO getAplistVO() {
		return aplistVO;
	}
	public void setAplistVO(AplistVO aplistVO) {
		this.aplistVO = aplistVO;
	}
}
