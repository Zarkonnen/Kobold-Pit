package com.metalbeetle.koboldpit.brain;

import com.metalbeetle.koboldpit.GameWorld;
import com.metalbeetle.koboldpit.Kobold;
import com.metalbeetle.koboldpit.Tile;
import com.metalbeetle.koboldpit.Utils.Pair;
import static com.metalbeetle.koboldpit.Utils.*;

public class SkullPile implements Drive {

	public int amount(Kobold k, GameWorld gw) {
		return k.skulls * k.skulls * 150;
	}

	public Pair<Integer, Integer> getDirection(Kobold k, GameWorld gw) {
		return p(
				k.x > 40 ? -1 : k.x < 40 ? 1 : 0,
				k.y > 30 ? -1 : k.y < 30 ? 1 : 0
		);
	}

	public boolean act(Kobold k, GameWorld gw) {
		if (k.skulls > 0 && gw.map[k.y][k.x].type == Tile.Type.PIT) {
			boolean skullsAdj = false;
			for (int dy = -1; dy < 2; dy++) {
				if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
				for (int dx = -1; dx < 2; dx++) {
					if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
					if (gw.map[k.y + dy][k.x + dx].skulls > 0 && gw.map[k.y + dy][k.x + dx].skulls < 24) {
						gw.map[k.y + dy][k.x + dx].skulls += k.skulls;
						k.skulls = 0;
						return true;
					}
					if (gw.map[k.y + dy][k.x + dx].skulls > 0) { skullsAdj = true; }
				}
			}
			if (skullsAdj) {
				for (int dy = -1; dy < 2; dy++) {
					if (k.y + dy < 0 || k.y + dy >= gw.map.length) { continue; }
					for (int dx = -1; dx < 2; dx++) {
						if (k.x + dx < 0 || k.x + dx >= gw.map[0].length) { continue; }
						if (gw.map[k.y + dy][k.x + dx].skulls < 24) {
							gw.map[k.y + dy][k.x + dx].skulls += k.skulls;
							k.skulls = 0;
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
}
