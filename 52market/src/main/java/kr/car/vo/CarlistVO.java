package kr.car.vo;

import java.sql.Date;

public class CarlistVO {
	private int carlist_num;
	private String carlist_title;		//글 제목
	private String carlist_content; 	//글 내용
	private Date carlist_reg_date;  	//작성일
	private Date carlist_modify_date;	//수정일
	private int carlist_status;			//판매여부 default 0
	private int carlist_hit;
	

	public int getCarlist_hit() {
		return carlist_hit;
	}

	public void setCarlist_hit(int carlist_hit) {
		this.carlist_hit = carlist_hit;
	}

	public int getCarlist_num() {
		return carlist_num;
	}

	public void setCarlist_num(int carlist_num) {
		this.carlist_num = carlist_num;
	}

	public String getCarlist_title() {
		return carlist_title;
	}

	public void setCarlist_title(String carlist_title) {
		this.carlist_title = carlist_title;
	}

	public String getCarlist_content() {
		return carlist_content;
	}

	public void setCarlist_content(String carlist_content) {
		this.carlist_content = carlist_content;
	}

	public Date getCarlist_reg_date() {
		return carlist_reg_date;
	}

	public void setCarlist_reg_date(Date carlist_reg_date) {
		this.carlist_reg_date = carlist_reg_date;
	}

	public Date getCarlist_modify_date() {
		return carlist_modify_date;
	}

	public void setCarlist_modify_date(Date carlist_modify_date) {
		this.carlist_modify_date = carlist_modify_date;
	}

	public int getCarlist_status() {
		return carlist_status;
	}

	public void setCarlist_status(int carlist_status) {
		this.carlist_status = carlist_status;
	}

	
	
	
}
