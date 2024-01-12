package com.sist.vo;

public class ProductVO {
   private int pno;
   private String p_phone;
   private String p_name;
   private String addr;
   private int occ_max;
   private int price;
   private double rating;
   private String p_explain;
   private double lat;
   private double lng;
   private int a_code;
   private int p_code;
   public int getPno() {
      return pno;
   }
   public void setPno(int pno) {
      this.pno = pno;
   }
   public String getP_phone() {
      return p_phone;
   }
   public void setP_phone(String p_phone) {
      this.p_phone = p_phone;
   }
   public String getP_name() {
      return p_name;
   }
   public void setP_name(String p_name) {
      this.p_name = p_name;
   }
   public String getAddr() {
      return addr;
   }
   public void setAddr(String addr) {
      this.addr = addr;
   }
   public int getOcc_max() {
      return occ_max;
   }
   public void setOcc_max(int occ_max) {
      this.occ_max = occ_max;
   }
   public int getPrice() {
      return price;
   }
   public void setPrice(int price) {
      this.price = price;
   }
   public double getRating() {
      return rating;
   }
   public void setRating(double rating) {
      this.rating = rating;
   }
   public String getP_explain() {
      return p_explain;
   }
   public void setP_explain(String p_explain) {
      this.p_explain = p_explain;
   }
   public double getLat() {
      return lat;
   }
   public void setLat(double lat) {
      this.lat = lat;
   }
   public double getLng() {
      return lng;
   }
   public void setLng(double lng) {
      this.lng = lng;
   }
   public int getA_code() {
      return a_code;
   }
   public void setA_code(int a_code) {
      this.a_code = a_code;
   }
   public int getP_code() {
      return p_code;
   }
   public void setP_code(int p_code) {
      this.p_code = p_code;
   }
   public ProductVO(int pno, String p_phone, String p_name, String addr, int occ_max, int price, double rating,
         String p_explain, double lat, double lng, int a_code, int p_code) {
      super();
      this.pno = pno;
      this.p_phone = p_phone;
      this.p_name = p_name;
      this.addr = addr;
      this.occ_max = occ_max;
      this.price = price;
      this.rating = rating;
      this.p_explain = p_explain;
      this.lat = lat;
      this.lng = lng;
      this.a_code = a_code;
      this.p_code = p_code;
   }
   public ProductVO() {
      super();
      // TODO Auto-generated constructor stub
   }
   
   
}