package com.sist.vo;

public class UserVO {
	private int uno;
	private String u_name;
	private String u_email;
	private String u_phone;
	private String u_pwd;
	private String nickname;
	public UserVO(int uno, String u_name, String u_email, String u_phone, String u_pwd, String nickname) {
		super();
		this.uno = uno;
		this.u_name = u_name;
		this.u_email = u_email;
		this.u_phone = u_phone;
		this.u_pwd = u_pwd;
		this.nickname = nickname;
	}
	public UserVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getUno() {
		return uno;
	}
	public void setUno(int uno) {
		this.uno = uno;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_email() {
		return u_email;
	}
	public void setU_email(String u_email) {
		this.u_email = u_email;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getU_pwd() {
		return u_pwd;
	}
	public void setU_pwd(String u_pwd) {
		this.u_pwd = u_pwd;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
