package com.sokah.geometryx;

public class Triangletrix extends SpaceShip{

	private String type= "Triangletrix";
	public Triangletrix(int posx, int posy) {
		super(posy, posy);
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
