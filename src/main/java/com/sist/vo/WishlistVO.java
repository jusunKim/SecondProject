package com.sist.vo;

public class WishlistVO {
	private int wno;
	private int uno;
	private int pno;
	public WishlistVO(int wno, int uno, int pno) {
		super();
		this.wno = wno;
		this.uno = uno;
		this.pno = pno;
	}
	public WishlistVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getWno() {
		return wno;
	}
	public void setWno(int wno) {
		this.wno = wno;
	}
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	
	
}
