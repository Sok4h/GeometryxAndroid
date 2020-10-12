package com.sokah.geometryx.model;

public class Vibration {
	
	
	private String type= "Vibration";
	private boolean vibrating;
	
	
	public Vibration(boolean vibrating) {
	
		this.vibrating=vibrating;
		
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isVibrating() {
		return vibrating;
	}


	public void setVibrating(boolean vibrating) {
		this.vibrating = vibrating;
	}

	
}
