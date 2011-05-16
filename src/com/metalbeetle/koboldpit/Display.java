package com.metalbeetle.koboldpit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Display {
	final GameWorld w;
	final int width;
	final int height;
	int prevMilestone = 50;

	Display(GameWorld w, int width, int height) {
		this.w = w;
		this.width = width;
		this.height = height;
	}

	void draw(Graphics2D g) {
		g.setFont(new Font("Courier", Font.PLAIN, 11));
		int xOff = 1;
		int yOff = g.getFontMetrics().getAscent();
		
		if (w.intro) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);
			g.setColor(Color.RED);
			g.drawString("KOBOLD PIT", 20 + xOff, 60 + yOff);
			g.drawString("Tales of skull piles and ecological degradation!", 20 + xOff, 80 + yOff);
			
			g.setColor(new Color(0, 71, 171));
			g.drawString("k", 20 + xOff, 100 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A kobold. It likes to eat and sit in pits.", 20 + xOff, 100 + yOff);
			g.setColor(new Color(250, 250, 245));
			g.drawString("r", 20 + xOff, 120 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A rabbit. It's what kobolds eat. When they're not eating each other.", 20 + xOff, 120 + yOff);
			g.setColor(new Color(245, 245, 215));
			g.drawString("ø", 20 + xOff, 140 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A skull. Kobolds make big piles of them in the center of the pit. Any skull will do.", 20 + xOff, 140 + yOff);
			
			g.setColor(Tile.Type.GRASS.c);
			g.fillRect(20, 160, 10, 10);
			g.setColor(Color.WHITE);
			g.drawString("    Grass. Rabbits like it. Kobolds don't. They much prefer...", 20 + xOff, 160 + yOff);
			g.setColor(Tile.Type.PIT.c);
			g.fillRect(20, 180, 10, 10);
			g.setColor(Color.WHITE);
			g.drawString("    ...pits! Kobolds move much faster through pits and get less hungry resting in them.", 20 + xOff, 180 + yOff);
			g.drawString("    Kobold claws are so sharp that they tear up the ground beneath them, causing the pit to widen with time.", 20 + xOff, 200 + yOff);
			g.setColor(Color.YELLOW);
			g.drawString("h", 20 + xOff, 220 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A hungry kobold.", 20 + xOff, 220 + yOff);
			g.drawString("H", 20 + xOff, 240 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A very hungry kobold.", 20 + xOff, 240 + yOff);
			g.setColor(new Color(255, 80, 180));
			g.drawString("P", 20 + xOff, 260 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A pregnant kobold. They only get pregnant in the presence of skulls. Don't ask.", 20 + xOff, 260 + yOff);
			g.setColor(new Color(0, 71, 171));
			g.drawString("K", 20 + xOff, 280 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A kobold champion. It has earned the right to eat other kobolds!", 20 + xOff, 280 + yOff);
			g.setColor(new Color(10, 100, 10));
			g.drawString("t", 20 + xOff, 300 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    A tree. It's mostly in the way. But it will fall into your pit eventually.", 20 + xOff, 300 + yOff);
			
			g.setColor(Color.WHITE);
			g.drawString("Instructions:  Use your mouse to direct the kobolds to victory!", 20 + xOff, 340 + yOff);
			g.setColor(new Color(30, 101, 201));
			g.drawString("k", 20 + xOff, 360 + yOff);
			g.setColor(Color.WHITE);
			g.drawString("    This kobold is following your cursor. Kobolds close to the cursor tend to follow it.", 20 + xOff, 360 + yOff);
			
			g.setColor(Color.WHITE);
			g.drawString("That's all! Click to start the game. Contact david.stark@zarkonnen.com if you have questions.", 20 + xOff, 400 + yOff);
			return;
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		int skulls = 0;
		
		for (int y = 0; y < w.map.length; y++) {
			for (int x = 0; x < w.map[y].length; x++) {
				g.setColor(w.map[y][x].type.c);
				/*if (x == w.cursorX && y == w.cursorY) {
					g.setColor(Color.YELLOW);
				}*/
				g.fillRect(x * 10, y * 10, 10, 10);
				if (w.map[y][x].erosion > 0 && w.map[y][x].type != Tile.Type.PIT) {
					g.setColor(new Color(130, 125, 90));
					g.drawString("#", x * 10 + xOff, y * 10 + yOff);
				}
				if (w.map[y][x].blood > 0) {
					g.setColor(Color.RED);
					g.drawString("*", x * 10 + xOff, y * 10 + yOff);
				}
				if (w.map[y][x].skulls > 0) {
					g.setColor(new Color(245, 245, 215));
					g.drawString("ø", x * 10 + xOff, y * 10 + yOff);
					skulls += w.map[y][x].skulls;
				}
			}
		}
		for (Item item : w.items) {
			if (item instanceof Animal) {
				Animal a = (Animal) item;
				if (a.hunger > a.deathHunger * 3 / 4 && (w.tick / 4) % 12 < 3) {
					g.setColor(Color.YELLOW);
					g.drawString(a.hunger > a.deathHunger * 6 / 7 ? "H" : "h", item.x * 10 + xOff, item.y * 10 + yOff);
				} else {
					if (a instanceof Kobold && ((Kobold) a).pregnant > 0 && (w.tick / 4) % 12 < 6) {
						g.setColor(new Color(255, 80, 180));
						g.drawString("P", item.x * 10 + xOff, item.y * 10 + yOff);
					} else {
						if (a instanceof Kobold && ((Kobold) a).skulls > 0 && (w.tick / 4) % 12 < 6) {
							g.setColor(new Color(245, 245, 215));
							g.drawString("ø", item.x * 10 + xOff, item.y * 10 + yOff);
						} else {
							g.setColor(item.c);
							if (a instanceof Kobold && ((Kobold) a).following) {
								g.setColor(new Color(30, 101, 201));
							}
							g.drawString(item.l, item.x * 10 + xOff, item.y * 10 + yOff);
						}
					}
				}
			} else {
				g.setColor(item.c);
				g.drawString(item.l, item.x * 10 + xOff, item.y * 10 + yOff);
			}
		}
		
		int nks = 0;
		for (Item i : w.items) {
			if (i instanceof Kobold) { nks++; }
		}
		
		if (skulls > prevMilestone * 2) {
			w.info("Your skull pile has grown to more than " + (prevMilestone * 2) + " skulls.", new Color(70, 0, 0));
			prevMilestone *= 2;
		}
		
		g.setColor(Color.WHITE);
		g.drawString(nks + " kobolds, " + skulls + " skulls in pile", 20 + xOff, 560 + yOff);
		if (w.infoT != null) {
			g.setColor(w.infoC);
			g.drawString(w.infoT, 20 + xOff, 540 + yOff);
		}
	}
}
