package com.metalbeetle.koboldpit;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import javax.swing.JOptionPane;

public class GameThread implements Runnable {
	GameWorld world;
	Display display;
	BufferStrategy bs;
	Input input;
	Controls controls;
	public GameThread(GameWorld world, Input input, Display display, Controls controls, BufferStrategy bs) {
		this.world = world;
		this.input = input;
		this.display = display;
		this.controls = controls;
		this.bs = bs;
	}

	public void run() {
		try {
			while (true) {
				controls.processInput();
				for (int i = 0; i < 4; i++) {
					world.tick();
				}
				Graphics2D g = (Graphics2D) bs.getDrawGraphics();
				display.draw(g);
				bs.show();

				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
