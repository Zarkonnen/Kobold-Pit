package com.metalbeetle.koboldpit;

import com.metalbeetle.koboldpit.brain.ClumpAndFuck;
import com.metalbeetle.koboldpit.brain.Drive;
import com.metalbeetle.koboldpit.brain.Eat;
import com.metalbeetle.koboldpit.brain.FollowCursor;
import com.metalbeetle.koboldpit.brain.SkullPile;
import com.metalbeetle.koboldpit.brain.StayInPit;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import static com.metalbeetle.koboldpit.Utils.*;

public class Kobold extends Animal {
	static final List<Drive> DRIVES = l(new ClumpAndFuck(), new StayInPit(), new Eat(),
			new FollowCursor(), new SkullPile());
	
	public int kills = 0;
	public int skulls = 0;
	public int pregnant = 0;
	public boolean following = false;
	
	static Random r = new Random();
	
	static String name() {
		return new String[] {
			"Grzikngh",
			"Brghz",
			"Zraa",
			"Klutt",
			"Murgezzog",
			"Okkog Okkog",
			"Frix",
			"Zrippo",
			"Zazapakka",
			"Krull",
			"Blorgorz",
			"Uzzakk",
			"Hittehelmettepol",
			"Zong",
			"Krghl"
		}[r.nextInt(15)] + " " +
		new String[] {
			"Jameson",
			"Smith",
			"Jones",
			"Taylor",
			"Brown",
			"Williams",
			"Smythe",
			"Clarke",
			"Robinson",
			"Wilson",
			"Johnson",
			"Walker",
			"Wood",
			"Hall",
			"Thompson"
		}[r.nextInt(15)];
	}
	
	public Kobold(int x, int y) {
		super(x, y, new Color(0, 71, 171), "k", name());
		walkErosion = 22;
		deathHunger = 8000;
	}
	
	@Override
	public int speed(GameWorld gw) {
		return speed + (gw.map[y][x].type == Tile.Type.PIT ? - 4 : gw.map[y][x].type.speedDelta) -
				(hunger > deathHunger * 3 / 4 ? 2 : 0) + pregnant / 300;
	}
	
	@Override
	public void behave(GameWorld gw) {
		following = false;
		ArrayList<Drive> ds = new ArrayList<Drive>(DRIVES);
		final HashMap<Drive, Integer> dToAmt = new HashMap<Drive, Integer>();
		for (Drive d : ds) {
			dToAmt.put(d, d.amount(this, gw));
		}
		
		Collections.sort(ds, new Comparator<Drive>() {
			public int compare(Drive d0, Drive d1) {
				return dToAmt.get(d1) - dToAmt.get(d0);
			}
		});
		
		Pair<Integer, Integer> dir = null;
		for (Drive d : ds) {
			dir = d.getDirection(this, gw);
			if (dir != null) { break; }
		}
		if (dir == null) {
			wander(gw);
		} else {
			if (!move(dir.a, dir.b, gw)) {
				if (!move(x > 40 ? -1 : x < 40 ? 1 : 0, y > 30 ? -1 : y > 30 ? 1 : 0, gw)) {
					wander(gw);
				}
			}
		}
		
		for (Drive d : ds) {
			if (d.act(this, gw)) { break; }
		}
		
		if (gw.map[y][x].type != Tile.Type.PIT) {
			hunger += 2;
		}
		
		if (pregnant > 0 && hunger > deathHunger * 8 / 9) {
			pregnant = 0;
		}
		
		if (pregnant > 0 && ++pregnant >= 2000) {
			hunger += 2;
			int litter = 1 + gw.r.nextInt(3);
			for (int dy = -1; dy < 2; dy++) {
				if (y + dy < 0 || y + dy >= gw.map.length) { continue; }
				for (int dx = -1; dx < 2; dx++) {
					if (x + dx < 0 || x + dx >= gw.map[0].length) { continue; }
					if (gw.at(x + dx, y + dy) == null) {
						Kobold k = new Kobold(x + dx, y + dy);
						k.hunger = k.deathHunger / 2 + gw.r.nextInt(k.deathHunger / 3);
						gw.items.add(k);
						litter--;
						if (pregnant != 0) {
							gw.info(name + " has given birth!", new Color(255, 190, 230));
						}
						pregnant = 0;
						hunger += 1000;
						if (litter == 0) {
							return;
						}
					}
				}
			}
		}
	}
}
