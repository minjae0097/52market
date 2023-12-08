package kr.house.vo;

import java.sql.Date;

public class HouseDetailVO {
	private int house_num;			//부동산 판매글 번호
	private int house_seller_type; 	//부동산 판매자 타입
	private int house_type;			//매물 종류
	private int house_deal_type;	//거래방식
	private int house_diposit;		//보증금
	private int house_price;		//가격
	private int house_cost;			//관리비
	private String house_address1;	//매물주소
	private String house_address2;	//매물 상세주소
	private int house_space;		//평수
	private int house_floor;		//층수
	private int house_move_in;		//입주시기 타입
	private String house_filename;	//첨부사진
	private Date house_trade_date;	//부동산 거래완료일
	private int house_buyer;		//부동산 구매자
	private int mem_num;			//판매자 회원번호
	
	public int getHouse_num() {
		return house_num;
	}
	public void setHouse_num(int house_num) {
		this.house_num = house_num;
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
	public String getHouse_filename() {
		return house_filename;
	}
	public void setHouse_filename(String house_filename) {
		this.house_filename = house_filename;
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
	
}
