package com.metalbeetle.koboldpit.brain;

import com.metalbeetle.koboldpit.GameWorld;
import com.metalbeetle.koboldpit.Kobold;
import static com.metalbeetle.koboldpit.Utils.*;

public class FollowCursor implements Drive {

	public int amount(Kobold k, GameWorld gw) {
		int d = (k.x - gw.cursorX) * (k.x - gw.cursorX) + (k.y - gw.cursorY) * (k.y - gw.cursorY);
		k.following = d < 200;
		return d < 200 ? 2000 : 0;//100000 / (d + 100);
	}

	public Pair<Integer, Integer> getDirection(Kobold k, GameWorld gw) {
		return p(gw.cursorX - k.x < 0 ? -1 : gw.cursorX - k.x > 0 ? 1 : 0,
				gw.cursorY - k.y < 0 ? -1 : gw.cursorY - k.y > 0 ? 1 : 0);
	}

	public boolean act(Kobold k, GameWorld gw) { return false; }
}
