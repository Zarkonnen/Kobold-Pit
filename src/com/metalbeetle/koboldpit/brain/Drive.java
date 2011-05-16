package com.metalbeetle.koboldpit.brain;

import com.metalbeetle.koboldpit.GameWorld;
import com.metalbeetle.koboldpit.Kobold;
import com.metalbeetle.koboldpit.Utils.Pair;

public interface Drive {
	public int amount(Kobold k, GameWorld gw);
	public Pair<Integer, Integer> getDirection(Kobold k, GameWorld gw);
	public boolean act(Kobold k, GameWorld gw);
}
