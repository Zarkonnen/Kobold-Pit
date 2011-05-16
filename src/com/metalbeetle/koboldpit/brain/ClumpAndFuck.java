package com.metalbeetle.koboldpit.brain;

import com.metalbeetle.koboldpit.GameWorld;
import com.metalbeetle.koboldpit.Item;
import com.metalbeetle.koboldpit.Kobold;
import static com.metalbeetle.koboldpit.Utils.*;

public class ClumpAndFuck implements Drive {

	public int amount(Kobold k, GameWorld gw) {
		return 150;
	}

	public boolean act(Kobold k, GameWorld gw) {
		int skulls = 0;
		for (int dy = -1; dy < 2; dy++) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = -1; dx < 2; dx++) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				skulls += gw.map[k.y + dy][k.x + dx].skulls;
			}
		}
		if (skulls == 0) { return false; }
		for (int dy = -1; dy < 2; dy++) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = -1; dx < 2; dx++) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				Item it = gw.at(k.x + dx, k.y + dy);
				if (it instanceof Kobold && gw.r.nextInt(Math.max(10, 1000 - skulls)) == 0 && k.hunger < k.deathHunger / 10) {
					k.pregnant = Math.max(k.pregnant, 1);
					return true;
				}
			}
		}
		return false;
	}

	public Pair<Integer, Integer> getDirection(Kobold k, GameWorld gw) {
		if (gw.r.nextBoolean()) { return null; }
		int yStart = gw.r.nextBoolean() ? -5 : 5;
		int yEnd = yStart == -5 ? 5 : -5;
		int xStart = gw.r.nextBoolean() ? -5 : 5;
		int xEnd = xStart == -5 ? 5 : -5;
		for (int dy = yStart; dy != yEnd; dy = yEnd == 5 ? dy + 1 : dy - 1) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = xStart; dx != xEnd; dx = xEnd == 5 ? dx + 1 : dx - 1) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				Item it = gw.at(k.x + dx, k.y + dy);
				if (it instanceof Kobold) {
					return p(dx > 0 ? 1 : dx < 0 ? -1 : 0, dy > 0 ? 1 : dy < 0 ? -1 : 0);
				}
			}
		}
		return null;
	}
}
