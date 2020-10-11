package com.sokah.geometryx.model;

public class Shoot {
	
	private String type = "Shoot";
	private boolean superShoot;
	
	public Shoot() {
		
	}

	public boolean isSuperShoot() {
		return superShoot;
	}

	public void setSuperShoot(boolean superShoot) {
		this.superShoot = superShoot;
	}
}
