package com.metalbeetle.koboldpit.brain;

import com.metalbeetle.koboldpit.Tile;
import java.awt.Color;
import com.metalbeetle.koboldpit.Animal;
import com.metalbeetle.koboldpit.GameWorld;
import com.metalbeetle.koboldpit.Item;
import com.metalbeetle.koboldpit.Kobold;
import static com.metalbeetle.koboldpit.Utils.*;

public class Eat implements Drive {

	public int amount(Kobold k, GameWorld gw) {
		/*int collectiveHunger = k.hunger / 10;
		for (int dy = -2; dy < 5; dy++) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = -2; dx < 5; dx++) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				Item it = gw.at(k.x + dx, k.y + dy);
				if (it instanceof Kobold) {
					collectiveHunger += ((Kobold) it).hunger / 30;
				}
			}
		}
		System.out.println(collectiveHunger);
		return collectiveHunger;*/
		return k.hunger > 5000 ? k.hunger > 7000 ? 1000 : 500 : 0;
	}
	
	boolean eat(Kobold k, GameWorld gw) {
		for (int dy = -1; dy < 2; dy++) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = -1; dx < 2; dx++) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				if (dx == 0 && dy == 0) { continue; }
				Item it = gw.at(k.x + dx, k.y + dy);
				if (it instanceof Animal && (!(it instanceof Kobold) ||
						(k.l.equals("K") && k.hunger > k.deathHunger * 4 / 5)))
				{
					((Animal) it).slaughter(gw);
					if (it instanceof Kobold) {
						gw.info(k.name + " was very hungry and snacked on " + it.name, new Color(255, 200, 200));
					}
					killErosion(k.x + dx, k.y + dy, gw);
					k.skulls++;
					k.kills++;
					k.bloody = gw.r.nextInt(500) + 150;
					if (k.kills == 12) {
						k.l = "K";
						k.deathHunger = 12000;
						gw.info(k.name + " has become a great champion!", new Color(255, 255, 200));
					}
					feed(k, gw);
					return true;
				}
			}
		}
		return false;
	}
	
	void killErosion(int x, int y, GameWorld gw) {
		gw.map[y][x].erosion += 20;
		if (gw.map[y][x].erosion > 100 && gw.map[y][x].type != Tile.Type.PIT) {
			lp: for (int dy = -1; dy < 2; dy++) {
				if (y + dy < 0 || y + dy >= gw.map.length) { continue; }
				for (int dx = -1; dx < 2; dx++) {
					if (x + dx < 0 || x + dx >= gw.map[0].length) { continue; }
					if (gw.map[y + dy][x + dx].type == Tile.Type.PIT) {
						gw.map[y][x].type = Tile.Type.PIT;
						break lp;
					}
				}
			}
		}
	}
	
	void feed(Kobold k, GameWorld gw) {
		for (int dy = -2; dy < 5; dy++) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = -2; dx < 5; dx++) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				Item it = gw.at(k.x + dx, k.y + dy);
				if (it instanceof Kobold) {
					int foodAmt = 2000 - Math.abs(dx) * 350 - Math.abs(dy) * 350;
					((Kobold) it).hunger = Math.max(0, ((Kobold) it).hunger - foodAmt);
				}
			}
		}
	}

	public Pair<Integer, Integer> getDirection(Kobold k, GameWorld gw) {
		/*int yStart = gw.r.nextBoolean() ? -20 : 20;
		int yEnd = yStart == -20 ? 20 : -20;
		int xStart = gw.r.nextBoolean() ? -20 : 20;
		int xEnd = xStart == -20 ? 20 : -20;
		for (int dy = yStart; dy != yEnd; dy = yEnd == 20 ? dy + 1 : dy - 1) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = xStart; dx != xEnd; dx = xEnd == 20 ? dx + 1 : dx - 1) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				Item it = gw.at(k.x + dx, k.y + dy);
				if (it instanceof Animal && !(it instanceof Kobold)) {
					return p(dx > 0 ? 1 : dx < 0 ? -1 : 0, dy > 0 ? 1 : dy < 0 ? -1 : 0);
				}
			}
		}*/
		return k.hunger < k.deathHunger * 3 / 4 ? null : p(
				(gw.tick / 141) % 3 - 1,
				(gw.tick / 87 + 17) % 3 - 1
				);
	}

	public boolean act(Kobold k, GameWorld gw) {
		return eat(k, gw);
	}
}
