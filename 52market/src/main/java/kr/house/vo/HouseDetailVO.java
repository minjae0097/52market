package kr.house.vo;

import java.sql.Date;

public class HouseDetailVO {
	private int house_num;			//부동산 판매글 번호
	private String house_title;		//부동산 판매글 제목
	private int house_seller_type; 	//부동산 판매자 타입
	private int house_type;			//매물 종류
	private int house_deal_type;	//거래방식
	private int house_diposit;		//보증금
	private int house_price;		//가격
	private int house_cost;			//관리비
	private String zipcode;			//우편번호
	private String house_address1;	//매물주소
	private String house_address2;	//매물 상세주소
	private int house_space;		//평수
	private int house_floor;		//층수
	private int house_move_in;		//입주시기 타입
	private String house_photo1;	//섬네일 사진
	private String house_photo2;	//디테일 사진
	private Date house_trade_date;	//부동산 거래완료일
	private int house_buyer;		//부동산 구매자
	private int mem_num;			//판매자 회원번호	
	
	private String mem_nickname;		//닉네임
	private Date house_modify_date;		//수정일
	
	public int getHouse_num() {
		return house_num;
	}
	public void setHouse_num(int house_num) {
		this.house_num = house_num;
	}
	public String getHouse_title() {
		return house_title;
	}
	public void setHouse_title(String house_title) {
		this.house_title = house_title;
	}
	public int getHouse_seller_type() {
		return house_seller_type;
	}
	public void setHouse_seller_type(int house_seller_type) {
		this.house_seller_type = house_seller_type;
	}
	public int getHouse_type() {
		return house_type;
	}
	public void setHouse_type(int house_type) {
		this.house_type = house_type;
	}
	public int getHouse_deal_type() {
		return house_deal_type;
	}
	public void setHouse_deal_type(int house_deal_type) {
		this.house_deal_type = house_deal_type;
	}
	public int getHouse_diposit() {
		return house_diposit;
	}
	public void setHouse_diposit(int house_diposit) {
		this.house_diposit = house_diposit;
	}
	public int getHouse_price() {
		return house_price;
	}
	public void setHouse_price(int house_price) {
		this.house_price = house_price;
	}
	public int getHouse_cost() {
		return house_cost;
	}
	public void setHouse_cost(int house_cost) {
		this.house_cost = house_cost;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getHouse_address1() {
		return house_address1;
	}
	public void setHouse_address1(String house_address1) {
		this.house_address1 = house_address1;
	}
	public String getHouse_address2() {
		return house_address2;
	}
	public void setHouse_address2(String house_address2) {
		this.house_address2 = house_address2;
	}
	public int getHouse_space() {
		return house_space;
	}
	public void setHouse_space(int house_space) {
		this.house_space = house_space;
	}
	public int getHouse_floor() {
		return house_floor;
	}
	public void setHouse_floor(int house_floor) {
		this.house_floor = house_floor;
	}
	public int getHouse_move_in() {
		return house_move_in;
	}
	public void setHouse_move_in(int house_move_in) {
		this.house_move_in = house_move_in;
	}
	public String getHouse_photo1() {
		return house_photo1;
	}
	public void setHouse_photo1(String house_photo1) {
		this.house_photo1 = house_photo1;
	}
	public String getHouse_photo2() {
		return house_photo2;
	}
	public void setHouse_photo2(String house_photo2) {
		this.house_photo2 = house_photo2;
	}
	public Date getHouse_trade_date() {
		return house_trade_date;
	}
	public void setHouse_trade_date(Date house_trade_date) {
		this.house_trade_date = house_trade_date;
	}
	public int getHouse_buyer() {
		return house_buyer;
	}
	public void setHouse_buyer(int house_buyer) {
		this.house_buyer = house_buyer;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public Date getHouse_modify_date() {
		return house_modify_date;
	}
	public void setHouse_modify_date(Date house_modify_date) {
		this.house_modify_date = house_modify_date;
	}
	
	
}
