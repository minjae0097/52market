package kr.product.vo;

import java.sql.Date;

public class ProductDetailVO {
	private int product_num;		//글번호
	private int product_seller;		//판매자
	private int product_buyer;		//구매자
	private int product_category;//상품카테고리
	private int product_price;		//가격
	private Date product_tradedate;	//판매완료일
	private String product_image;	//상품이미지
	private String product_name;	//상품명
	
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public int getProduct_seller() {
		return product_seller;
	}
	public void setProduct_seller(int product_seller) {
		this.product_seller = product_seller;
	}
	public int getProduct_buyer() {
		return product_buyer;
	}
	public void setProduct_buyer(int product_buyer) {
		this.product_buyer = product_buyer;
	}
	public int getProduct_category() {
		return product_category;
	}
	public void setProduct_category(int product_category) {
		this.product_category = product_category;
	}
	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	public Date getProduct_tradedate() {
		return product_tradedate;
	}
	public void setProduct_tradedate(Date product_tradedate) {
		this.product_tradedate = product_tradedate;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
}
