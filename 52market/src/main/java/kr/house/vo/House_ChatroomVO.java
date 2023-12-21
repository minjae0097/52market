package kr.house.vo;

public class House_ChatroomVO {
	private int chatroom_num;
	private int house_num;
	private int seller_num;
	private int buyer_num;
	
	private int cnt; //읽지 않은 메시지수
	private String house_title;//상품명
	private String seller;//판매자 id
	private String buyer;//구매자 id
	private String mem_nickname;//별명
	
	
	public int getChatroom_num() {
		return chatroom_num;
	}
	public void setChatroom_num(int chatroom_num) {
		this.chatroom_num = chatroom_num;
	}
	public int getHouse_num() {
		return house_num;
	}
	public void setHouse_num(int house_num) {
		this.house_num = house_num;
	}
	public int getSeller_num() {
		return seller_num;
	}
	public void setSeller_num(int seller_num) {
		this.seller_num = seller_num;
	}
	public int getBuyer_num() {
		return buyer_num;
	}
	public void setBuyer_num(int buyer_num) {
		this.buyer_num = buyer_num;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public String getHouse_title() {
		return house_title;
	}
	public void setHouse_title(String house_title) {
		this.house_title = house_title;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getMem_nickname() {
		return mem_nickname;
	}
	public void setMem_nickname(String mem_nickname) {
		this.mem_nickname = mem_nickname;
	}
	
	
}
