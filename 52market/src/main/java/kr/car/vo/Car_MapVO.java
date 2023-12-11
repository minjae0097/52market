package kr.car.vo;

public class Car_MapVO {
	private int carlist_num;
	private String location_x;
	private String location_y;
	private String location;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	private String road_address_name;
	private String address_name;
	
	public int getCarlist_num() {
		return carlist_num;
	}
	public void setCarlist_num(int carlist_num) {
		this.carlist_num = carlist_num;
	}
	public String getLocation_x() {
		return location_x;
	}
	public void setLocation_x(String location_x) {
		this.location_x = location_x;
	}
	public String getLocation_y() {
		return location_y;
	}
	public void setLocation_y(String location_y) {
		this.location_y = location_y;
	}
	
	public String getRoad_address_name() {
		return road_address_name;
	}
	public void setRoad_address_name(String road_address_name) {
		this.road_address_name = road_address_name;
	}
	public String getAddress_name() {
		return address_name;
	}
	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}
}
