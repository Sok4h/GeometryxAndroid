package com.sokah.geometryx;

import java.util.ArrayList;

public abstract class SpaceShip {
	protected int vida;
	protected int dano;
	protected int danoSuper;
	protected int posx;
	protected int posy;
	//protected ArrayList<Bullet> bullets;
	
	public SpaceShip(int posx, int posy) {
		super();
		this.posx = posx;
		this.posy = posy;
	}


}
