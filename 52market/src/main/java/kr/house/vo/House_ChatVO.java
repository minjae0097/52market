package kr.house.vo;

import java.sql.Date;

public class House_ChatVO {
	private int chat_num;
	private int chatroom_num;
	private int mem_num;
	private String message;
	private Date reg_date;
	private int read_check;
	
	
	public int getChat_num() {
		return chat_num;
	}
	public void setChat_num(int chat_num) {
		this.chat_num = chat_num;
	}
	public int getChatroom_num() {
		return chatroom_num;
	}
	public void setChatroom_num(int chatroom_num) {
		this.chatroom_num = chatroom_num;
	}
	public int getMem_num() {
		return mem_num;
	}
	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public int getRead_check() {
		return read_check;
	}
	public void setRead_check(int read_check) {
		this.read_check = read_check;
	}
	
	
}
