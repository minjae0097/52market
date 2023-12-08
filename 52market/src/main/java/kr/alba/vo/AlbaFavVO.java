package kr.alba.vo;

public class AlbaFavVO {
	private int albalist_num;
	private int mem_num;
	
	public AlbaFavVO () {}
	
	public AlbaFavVO(int albalist_num, int mem_num) {
		this.albalist_num = albalist_num;
		this.mem_num = mem_num;
	}

	public int getAlbalist_num() {
		return albalist_num;
	}
	public void setAlbalist_num(int albalist_num) {
		this.albalist_num = albalist_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
}
