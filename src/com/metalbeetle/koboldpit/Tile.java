package com.metalbeetle.koboldpit;

import java.awt.Color;

public class Tile {
	public static enum Type {
		PIT(new Color(110, 105, 70), 20),
		GRASS(new Color(95, 180, 60), 0);
		
		public final Color c;
		public final int speedDelta;

		private Type(Color c, int speedDelta) {
			this.c = c;
			this.speedDelta = speedDelta;
		}
	}
	
	public Type type = Type.GRASS;
	public int erosion = 0;
	public int blood = 0;
	public int skulls = 0;
	
	public void tick(GameWorld gw) {
		if (blood > 0) { blood--; }
		if (gw.tick % 200 == 0) { erosion--; }
		//if (erosion > 100) { type = Type.PIT; blood = 0; }
	}
}
