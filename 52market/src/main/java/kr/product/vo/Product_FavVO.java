package kr.product.vo;

public class Product_FavVO {
	private int product_num;
	private int mem_num;
	
	public Product_FavVO() {}
	
	public Product_FavVO(int product_num, int mem_num) {
		this.product_num = product_num;
		this.mem_num = mem_num;
	}
	
	public int getProduct_num() {
		return product_num;
	}
	public void setProduct_num(int product_num) {
		this.product_num = product_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
}
