package kr.alba.vo;

public class AlbaFavVO {
	private int alba_num;
	private int mem_num;
	
	public AlbaFavVO () {}
	
	public AlbaFavVO(int alba_num, int mem_num) {
		this.alba_num = alba_num;
		this.mem_num = mem_num;
	}

	public int getAlba_num() {
		return alba_num;
	}
	public void setAlbalist_num(int alba_num) {
		this.alba_num = alba_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
}
