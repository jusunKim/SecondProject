package com.sist.vo;

import java.util.Date;

public class QnaVO {
	private int qno;
	private String q_title;
	private String q_content;
	private Date q_date;
	private String a_content;
	private int uno;
	public QnaVO(int qno, String q_title, String q_content, Date q_date, String a_content, int uno) {
		super();
		this.qno = qno;
		this.q_title = q_title;
		this.q_content = q_content;
		this.q_date = q_date;
		this.a_content = a_content;
		this.uno = uno;
	}
	public QnaVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getQno() {
		return qno;
	}
	public void setQno(int qno) {
		this.qno = qno;
	}
	public String getQ_title() {
		return q_title;
	}
	public void setQ_title(String q_title) {
		this.q_title = q_title;
	}
	public String getQ_content() {
		return q_content;
	}
	public void setQ_content(String q_content) {
		this.q_content = q_content;
	}
	public Date getQ_date() {
		return q_date;
	}
	public void setQ_date(Date q_date) {
		this.q_date = q_date;
	}
	public String getA_content() {
		return a_content;
	}
	public void setA_content(String a_content) {
		this.a_content = a_content;
	}
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	
	
	
}
