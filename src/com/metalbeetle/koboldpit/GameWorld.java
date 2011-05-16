package com.metalbeetle.koboldpit;

import com.metalbeetle.koboldpit.menagerie.Rabbit;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameWorld {
	public Random r = new Random();
	public ArrayList<Item> items = new ArrayList<Item>();
	public Tile[][] map;
	public int tick = 0;
	public int cursorX;
	public int cursorY;
	public String infoT;
	public Color infoC;
	public int infoLeft;
	boolean intro = true;
	
	public void info(String t, Color c) {
		infoT = t;
		infoC = c;
		infoLeft = 400;
	}

	public GameWorld() {
		map = new Tile[60][80];
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				map[y][x] = new Tile();
				if (r.nextInt((y - 30) * (y - 30) * (y - 30) * (y - 30) + (x - 40) * (x - 40) * (x - 40) * (x - 40) + 1) < 20) {
					map[y][x].type = Tile.Type.PIT;
				}
			}
		}
		
		map[30][40].skulls = 3;
		
		for (int i = 0; i < 6; i++) {
			int x = 37 + r.nextInt(6);
			int y = 27 + r.nextInt(6);
			if (at(x, y) == null) {
				Kobold k = new Kobold(x, y);
				k.hunger = r.nextInt(k.deathHunger / 4);
				items.add(k);
			}
		}
		
		for (int j = 0; j < 10; j++) {
			int x0 = r.nextInt(60);
			int y0 = r.nextInt(40);
			for (int i = 0; i < 50; i++) {
				int x = x0 + r.nextInt(20);
				int y = y0 + r.nextInt(20);
				if (at(x, y) == null && map[y][x].type == Tile.Type.GRASS) {
					items.add(new Item(x, y, new Color(10, 100, 10), "t", "Tree") {
						@Override
						public boolean tick(GameWorld gw) {
							if (gw.tick % 1000 == id) {
								for (int dy = -1; dy < 2; dy++) {
									if (y + dy < 0 || y + dy >= gw.map.length) { continue; }
									for (int dx = -1; dx < 2; dx++) {
										if (x + dx < 0 || x + dx >= gw.map[0].length) { continue; }
										if (gw.map[y + dy][x + dx].type == Tile.Type.PIT) {
											return true;
										}
									}
								}
							}
							return false;
						}
					});
				}
			}
		}
		
		for (int i = 0; i < 30; i++) {
			int x = r.nextInt(80);
			int y = r.nextInt(60);
			if (at(x, y) == null) {
				items.add(new Rabbit(x, y));
			}
		}
	}
	
	public Item at(int x, int y) {
		for (Item a : items) {
			if (a.x == x && a.y == y) { return a; }
		}
		return null;
	}
	
	public void tick() {
		if (intro) { return; }
		tick++;
		infoLeft--;
		if (infoLeft == 0) { infoT = null; }
		int fertileSquares = 0;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				map[y][x].tick(this);
				if (map[y][x].type == Tile.Type.GRASS) { fertileSquares++; }
			}
		}
		
		for (Item it : new ArrayList<Item>(items)) {
			if (it.tick(this)) { items.remove(it); }
		}
		
		int nRabbits = 0;
		for (Item i : items) {
			if (i instanceof Rabbit) { nRabbits++; }
		}
		
		if (nRabbits < fertileSquares / 130 && r.nextInt(400) == 0) {
			int rabbits = r.nextInt(10);
			int x = r.nextBoolean() ? 0 : 79;
			int y = r.nextInt(50);
			
			for (int i = 0; i < rabbits; i++) {
				if (at(x, y + i) == null && map[y][x].type == Tile.Type.GRASS) {
					items.add(new Rabbit(x, y + i));
				}
			}
		}
		
		if (nRabbits < fertileSquares / 130 && r.nextInt(300) == 0) {
			int rabbits = r.nextInt(10);
			int x = r.nextInt(70);
			int y = r.nextBoolean() ? 0 : 59;
			
			for (int i = 0; i < rabbits; i++) {
				if (at(x + i, y) == null) {
					items.add(new Rabbit(x + i, y));
				}
			}
		}
	}
}
