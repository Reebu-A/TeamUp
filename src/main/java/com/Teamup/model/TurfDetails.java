package com.Teamup.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


@Entity
public class TurfDetails 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int turfid;
	private String turfname;
	private String turflocation;
	private String phone;
	private String designtype;
	private String features;
	private String text;
	private int standardrate;
	private int peaktimerate;
	private int turfownerid;
	private int status;
	private int deactivatedstatus;
	
	  @Lob
	    @Column(columnDefinition="MEDIUMBLOB")
	    private byte[] turfimage;
	  
	public int getTurfid() {
		return turfid;
	}
	public void setTurfid(int turfid) {
		this.turfid = turfid;
	}
	public String getTurfname() {
		return turfname;
	}
	public void setTurfname(String turfname) {
		this.turfname = turfname;
	}
	public String getTurflocation() {
		return turflocation;
	}
	public void setTurflocation(String turflocation) {
		this.turflocation = turflocation;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getStandardrate() {
		return standardrate;
	}
	public void setStandardrate(int standardrate) {
		this.standardrate = standardrate;
	}
	public int getPeaktimerate() {
		return peaktimerate;
	}
	public void setPeaktimerate(int peaktimerate) {
		this.peaktimerate = peaktimerate;
	}
	public int getTurfownerid() {
		return turfownerid;
	}
	public void setTurfownerid(int turfownerid) {
		this.turfownerid = turfownerid;
	}
	
	
	public String getDesigntype() {
		return designtype;
	}
	public void setDesigntype(String designtype) {
		this.designtype = designtype;
	}
	
	
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
	}
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public int getDeactivatedstatus() {
		return deactivatedstatus;
	}
	public void setDeactivatedstatus(int deactivatedstatus) {
		this.deactivatedstatus = deactivatedstatus;
	}
	public byte[] getTurfimage() {
		return turfimage;
	}
	public void setTurfimage(byte[] turfimage) {
		this.turfimage = turfimage;
	}

	
	public TurfDetails(int turfid, String turfname, String turflocation, String phone, String designtype,
			String features, String text, int standardrate, int peaktimerate, int turfownerid, int status,
			int deactivatedstatus, byte[] turfimage) {
		super();
		this.turfid = turfid;
		this.turfname = turfname;
		this.turflocation = turflocation;
		this.phone = phone;
		this.designtype = designtype;
		this.features = features;
		this.text = text;
		this.standardrate = standardrate;
		this.peaktimerate = peaktimerate;
		this.turfownerid = turfownerid;
		this.status = status;
		this.deactivatedstatus = deactivatedstatus;
		this.turfimage = turfimage;
	}
	public TurfDetails() {
		
	}
	
	

}
