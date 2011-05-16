package com.metalbeetle.koboldpit;

import java.awt.Color;

public class Item {
	public int x;
	public int y;
	public Color c;
	public String l;
	public String name;
	int id = idc++;
	static int idc = 0;

	public Item(int x, int y, Color c, String l, String name) {
		this.x = x;
		this.y = y;
		this.c = c;
		this.l = l;
		this.name = name;
	}
	
	public boolean tick(GameWorld gw) {
		return false;
	}
}
