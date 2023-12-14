package kr.house.vo;

public class HouseFavVO {
	private int house_num;	//부동산 판매글 번호
	private int mem_num;	//회원번호
	
	public HouseFavVO() {}//인자가 없는 생성자를 만들었을경우에 명시해주어야함
	
	public HouseFavVO(int house_num, int mem_num) {
		this.house_num = house_num;
		this.mem_num = mem_num;
	}
	
	public int getHouse_num() {
		return house_num;
	}

	public void setHouse_num(int house_num) {
		this.house_num = house_num;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}	
}
