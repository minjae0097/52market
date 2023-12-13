package kr.product.vo;

import java.sql.Date;

public class ProductVO {
	private int product_num;			//글번호
	private String product_title;		//거래글 제목
	private String product_image;
	private String product_content; 	//거래글 내용
	private Date product_reg_date;  	//작성일
	private Date product_modify_date;	//수정일
	private int product_status;			//판매여부 미판매:0(기본),판매:1
	private int product_mem;
	
	private String mem_nickname;			//member(닉네임)
	private String mem_photo;				//member(프로필사진)
	
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public String getProduct_title() {
		return product_title;
	}
	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}
	public String getProduct_content() {
		return product_content;
	}
	public void setProduct_content(String product_content) {
		this.product_content = product_content;
	}
	public Date getProduct_reg_date() {
		return product_reg_date;
	}
	public void setProduct_reg_date(Date product_reg_date) {
		this.product_reg_date = product_reg_date;
	}
	public Date getProduct_modify_date() {
		return product_modify_date;
	}
	public void setProduct_modify_date(Date product_modify_date) {
		this.product_modify_date = product_modify_date;
	}
	public int getProduct_status() {
		return product_status;
	}
	public void setProduct_status(int product_status) {
		this.product_status = product_status;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	public String getMem_photo() {
		return mem_photo;
	}
	public void setMem_photo(String mem_photo) {
		this.mem_photo = mem_photo;
	}
	public int getProduct_mem() {
		return product_mem;
	}
	public void setProduct_mem(int product_mem) {
		this.product_mem = product_mem;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
	
}
