package kr.car.vo;

public class Car_FavVO {
	private int carlist_num;
	private int mem_num;
	
	public Car_FavVO() {}
	
	public Car_FavVO(int carlist_num,int mem_num) {
		this.carlist_num = carlist_num;
		this.mem_num = mem_num;
	}
	
	public int getCarlist_num() {
		return carlist_num;
	}
	public void setCarlist_num(int carlist_num) {
		this.carlist_num = carlist_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
}
