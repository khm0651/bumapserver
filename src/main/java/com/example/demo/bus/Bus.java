package com.example.demo.bus;

import java.util.ArrayList;
import java.util.HashMap;

public class Bus {
	private ArrayList<String> img;
	private String busStation;
	private HashMap<String,String> toBu;
	private HashMap<String,String> toHome;
	private String toBuTakePlace;
	private String toHomeTakePlace;
	private String pirce;
	

	public String getBusStation() {
		return busStation;
	}
	public void setBusStation(String busStation) {
		this.busStation = busStation;
	}
	public HashMap<String,String> getToBu() {
		return toBu;
	}
	public void setToBu(HashMap<String,String> toBu) {
		this.toBu = toBu;
	}
	public HashMap<String,String> getToHome() {
		return toHome;
	}
	public void setToHome(HashMap<String,String> toHome) {
		this.toHome = toHome;
	}
	public String getToBuTakePlace() {
		return toBuTakePlace;
	}
	public void setToBuTakePlace(String toBuTakePlace) {
		this.toBuTakePlace = toBuTakePlace;
	}
	public String getToHomeTakePlace() {
		return toHomeTakePlace;
	}
	public void setToHomeTakePlace(String toHomeTakePlace) {
		this.toHomeTakePlace = toHomeTakePlace;
	}
	public String getPirce() {
		return pirce;
	}
	public void setPirce(String pirce) {
		this.pirce = pirce;
	}
	public ArrayList<String> getImg() {
		return img;
	}
	public void setImg(ArrayList<String> img) {
		this.img = img;
	}
	
	
	
}
