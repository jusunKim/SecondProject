package com.sist.vo;

public class AmenityVO {
	private int pno;
	private String wifi;
	private String parking;
	private String cooking;
	private String ac;
	private String wm;
	public AmenityVO(int pno, String wifi, String parking, String cooking, String ac, String wm) {
		super();
		this.pno = pno;
		this.wifi = wifi;
		this.parking = parking;
		this.cooking = cooking;
		this.ac = ac;
		this.wm = wm;
	}
	public AmenityVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public String getWifi() {
		return wifi;
	}
	public void setWifi(String wifi) {
		this.wifi = wifi;
	}
	public String getParking() {
		return parking;
	}
	public void setParking(String parking) {
		this.parking = parking;
	}
	public String getCooking() {
		return cooking;
	}
	public void setCooking(String cooking) {
		this.cooking = cooking;
	}
	public String getAc() {
		return ac;
	}
	public void setAc(String ac) {
		this.ac = ac;
	}
	public String getWm() {
		return wm;
	}
	public void setWm(String wm) {
		this.wm = wm;
	}
	
	
	
}
