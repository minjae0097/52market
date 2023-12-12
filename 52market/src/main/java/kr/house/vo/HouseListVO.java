package kr.house.vo;

import java.sql.Date;

public class HouseListVO {
	private int house_num;			//부동산 판매글 번호
	private String house_content;	//내용
	private Date house_reg_date;	//부동산 판매글 작성일
	private Date house_modify_date;	//부동산 판매글 수정일
	private int house_status;		//부동산 판매여부
	

	public int getHouse_num() {
		return house_num;
	}

	public void setHouse_num(int house_num) {
		this.house_num = house_num;
	}
	public String getHouse_content() {
		return house_content;
	}

	public void setHouse_content(String house_content) {
		this.house_content = house_content;
	}

	public Date getHouse_reg_date() {
		return house_reg_date;
	}

	public void setHouse_reg_date(Date house_reg_date) {
		this.house_reg_date = house_reg_date;
	}

	public Date getHouse_modify_date() {
		return house_modify_date;
	}

	public void setHouse_modify_date(Date house_modify_date) {
		this.house_modify_date = house_modify_date;
	}

	public int getHouse_status() {
		return house_status;
	}

	public void setHouse_status(int house_status) {
		this.house_status = house_status;
	}

	
}
