package com.metalbeetle.koboldpit.brain;

import com.metalbeetle.koboldpit.GameWorld;
import com.metalbeetle.koboldpit.Kobold;
import com.metalbeetle.koboldpit.Tile;
import static com.metalbeetle.koboldpit.Utils.*;

public class StayInPit implements Drive {

	public int amount(Kobold k, GameWorld gw) {
		return 100 + k.pregnant * 4;//gw.map[k.y][k.x].type == Tile.Type.PIT ? 100 : 600;
	}

	public boolean act(Kobold k, GameWorld gw) { return false; }

	public Pair<Integer, Integer> getDirection(Kobold k, GameWorld gw) {
		if (gw.map[k.y][k.x].type == Tile.Type.PIT) {
			int dx = gw.r.nextInt(3) != 0 ? (k.x > 40 ? -1 : k.x < 40 ? 1 : 0) : gw.r.nextInt(3) - 1;
			int dy = gw.r.nextInt(3) != 0 ? (k.y > 30 ? -1 : k.y < 30 ? 1 : 0) : gw.r.nextInt(3) - 1;
			if (
				dx >= 0 && dx + k.x < gw.map[0].length &&
				dy >= 0 && dy + k.y < gw.map.length &&
				gw.map[k.y + dy][k.x + dx].type == Tile.Type.PIT)
			{
				return p(dx, dy);
			} else {
				return null;
			}
		}
		int yStart = gw.r.nextBoolean() ? -5 : 5;
		int yEnd = yStart == -5 ? 5 : -5;
		int xStart = gw.r.nextBoolean() ? -5 : 5;
		int xEnd = xStart == -5 ? 5 : -5;
		for (int dy = yStart; dy != yEnd; dy = yEnd == 5 ? dy + 1 : dy - 1) {
			if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
			for (int dx = xStart; dx != xEnd; dx = xEnd == 5 ? dx + 1 : dx - 1) {
				if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
				if (gw.map[k.y + dy][k.x + dx].type == Tile.Type.PIT) {
					return p(dx > 0 ? 1 : dx < 0 ? -1 : 0, dy > 0 ? 1 : dy < 0 ? -1 : 0);
				}
			}
		}
		return null;
	}
}
