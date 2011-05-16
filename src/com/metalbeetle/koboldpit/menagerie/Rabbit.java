package com.metalbeetle.koboldpit.menagerie;

import com.metalbeetle.koboldpit.Animal;
import com.metalbeetle.koboldpit.GameWorld;
import com.metalbeetle.koboldpit.Tile;
import java.awt.Color;

public class Rabbit extends Animal {
	public Rabbit(int x, int y) {
		super(x, y, new Color(250, 250, 245), "r", "Rabbit");
		speed = 40;
	}
	
	@Override
	public void behave(GameWorld gw) {
		if (hunger > 1000 && gw.map[y][x].type == Tile.Type.GRASS) {
			hunger = 0;
			gw.map[y][x].erosion += 10;
		}
		super.behave(gw);
	}
}
