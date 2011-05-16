package com.metalbeetle.koboldpit;

import java.awt.Color;

public class Animal extends Item {
	public int walkErosion = 0;
	public int hunger = 0;
	public int deathHunger = 3000;
	public int speed = 8;
	public int bloody = 0;
	public boolean killed = false;

	public Animal(int x, int y, Color c, String l, String name) {
		super(x, y, c, l, name);
	}
	
	public void wander(GameWorld gw) {
		move(gw.r.nextInt(3) - 1, gw.r.nextInt(3) - 1, gw);
	}
	
	public void behave(GameWorld gw) {
		wander(gw);
	}
	
	public int speed(GameWorld gw) {
		return speed + gw.map[y][x].type.speedDelta;
	}
	
	public void slaughter(GameWorld gw) {
		gw.map[y][x].blood += 1500;
		for (int dy = -1; dy < 2; dy++) {
			if (y + dy < 0 || y + dy >= gw.map.length) { continue; }
			for (int dx = -1; dx < 2; dx++) {
				if (x + dx < 0 || x + dx >= gw.map[y].length) { continue; }
				if (gw.r.nextInt(3) == 0) {
					gw.map[y + dy][x + dx].blood += 500;
				}
			}
		}
		killed = true;
	}
	
	@Override
	public boolean tick(GameWorld gw) {
		behave(gw);
		hunger++;
		if (bloody > 0) {
			bloody--;
		}
		if (hunger >= deathHunger) {
			slaughter(gw);
			gw.info(name + " has starved to death", Color.BLACK);
		}
		return killed;
	}
	
	public boolean move(int dx, int dy, GameWorld gw) {
		if ((gw.tick + id) % speed(gw) != 0) { return false; }
		if (x + dx < 0) { return false; }
		if (y + dy < 0) { return false; }
		if (x + dx >= gw.map[0].length) { return false; }
		if (y + dy >= gw.map.length) { return false; }
		
		if (gw.at(x + dx, y + dy) != null || gw.map[y + dy][x + dx].skulls > 12) { return false; }
		x += dx;
		y += dy;
		if (walkErosion > 0) {
			gw.map[y][x].erosion += walkErosion;
			if (gw.map[y][x].erosion > 100 && gw.map[y][x].type != Tile.Type.PIT) {
				lp: for (dy = -1; dy < 2; dy++) {
					if (y + dy < 0 || y + dy >= gw.map.length) { continue; }
					for (dx = -1; dx < 2; dx++) {
						if (x + dx < 0 || x + dx >= gw.map[0].length) { continue; }
						if (gw.map[y + dy][x + dx].type == Tile.Type.PIT) {
							gw.map[y][x].type = Tile.Type.PIT;
							break lp;
						}
					}
				}
			}
		}
		if (bloody > 0) {
			gw.map[y][x].blood += gw.r.nextInt(4) == 0 ? gw.r.nextInt(bloody) : 0;
		}
		return true;
	}
}
