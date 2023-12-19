package kr.car.vo;

import java.sql.Date;

public class CarList_DetailVO {
	private int carlist_num;		//글번호
	private int car_seller;			//판매자
	private int car_buyer;			//구매자
	private String car_type;			//차종
	private String car_fuel;			//연료 종류
	private int car_price;			//가격
	private int car_model_year;		//연식
	private int car_distance;		//주행거리
	private String car_transmission;	//변속기 종류
	private String car_origin;		//제조국
	private String car_title; 		//글제목
	private String car_image;		//중고차 이미지
	private Date car_tradedate;		//판매 완료일
	private String mem_nickname;		//닉네임
	private Date carlist_modify_date; //수정일
	public Date getCarlist_modify_date() {
		return carlist_modify_date;
	}

	public void setCarlist_modify_date(Date carlist_modify_date) {
		this.carlist_modify_date = carlist_modify_date;
	}

	public String getMem_nickname() {
		return mem_nickname;
	}

	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	
	
	public String getCar_title() {
		return car_title;
	}
	public void setCar_title(String car_title) {
		this.car_title = car_title;
	}
	public int getCarlist_num() {
		return carlist_num;
	}
	public void setCarlist_num(int carlist_num) {
		this.carlist_num = carlist_num;
	}
	public int getCar_seller() {
		return car_seller;
	}
	public void setCar_seller(int car_seller) {
		this.car_seller = car_seller;
	}
	public int getCar_buyer() {
		return car_buyer;
	}
	public void setCar_buyer(int car_buyer) {
		this.car_buyer = car_buyer;
	}
	
	public int getCar_price() {
		return car_price;
	}
	public void setCar_price(int car_price) {
		this.car_price = car_price;
	}
	public int getCar_model_year() {
		return car_model_year;
	}
	public void setCar_model_year(int car_model_year) {
		this.car_model_year = car_model_year;
	}
	public int getCar_distance() {
		return car_distance;
	}
	public void setCar_distance(int car_distance) {
		this.car_distance = car_distance;
	}
	
	public String getCar_type() {
		return car_type;
	}

	public void setCar_type(String car_type) {
		this.car_type = car_type;
	}

	public String getCar_fuel() {
		return car_fuel;
	}

	public void setCar_fuel(String car_fuel) {
		this.car_fuel = car_fuel;
	}

	public String getCar_transmission() {
		return car_transmission;
	}

	public void setCar_transmission(String car_transmission) {
		this.car_transmission = car_transmission;
	}

	public String getCar_origin() {
		return car_origin;
	}

	public void setCar_origin(String car_origin) {
		this.car_origin = car_origin;
	}

	public String getCar_image() {
		return car_image;
	}
	public void setCar_image(String car_image) {
		this.car_image = car_image;
	}
	public Date getCar_tradedate() {
		return car_tradedate;
	}
	public void setCar_tradedate(Date car_tradedate) {
		this.car_tradedate = car_tradedate;
	}
}
