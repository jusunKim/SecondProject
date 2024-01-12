package com.sist.vo;

import java.util.Date;

public class ReviewVO {
	private int reno;
	private String re_title;
	private String re_content;
	private Date re_date;
	private double rating;
	private int pno;
	private int uno;
	public ReviewVO(int reno, String re_title, String re_content, Date re_date, double rating, int pno, int uno) {
		super();
		this.reno = reno;
		this.re_title = re_title;
		this.re_content = re_content;
		this.re_date = re_date;
		this.rating = rating;
		this.pno = pno;
		this.uno = uno;
	}
	public ReviewVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getReno() {
		return reno;
	}
	public void setReno(int reno) {
		this.reno = reno;
	}
	public String getRe_title() {
		return re_title;
	}
	public void setRe_title(String re_title) {
		this.re_title = re_title;
	}
	public String getRe_content() {
		return re_content;
	}
	public void setRe_content(String re_content) {
		this.re_content = re_content;
	}
	public Date getRe_date() {
		return re_date;
	}
	public void setRe_date(Date re_date) {
		this.re_date = re_date;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getPno() {
		return pno;
	}
	public void setPno(int pno) {
		this.pno = pno;
	}
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	
	
	
	
}
