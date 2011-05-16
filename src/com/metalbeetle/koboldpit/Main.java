package com.metalbeetle.koboldpit;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
		int width = 800;
		int height = 600;

		JFrame jf = new JFrame("Game");
		jf.setIgnoreRepaint(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Canvas c = new Canvas();
		c.setCursor(null);
		c.setIgnoreRepaint(true);
		jf.add(c);
		jf.setSize(width, height);
		jf.setResizable(false);
		jf.setVisible(true);
		MediaProvider.createInstance(c.getGraphicsConfiguration());
		GameWorld w = new GameWorld();
		Display d = new Display(w, width, height);
		c.createBufferStrategy(2);
		BufferStrategy bs = c.getBufferStrategy();
		Input input = new Input();
		c.addKeyListener(input);
		c.addMouseListener(input);
		c.addMouseMotionListener(input);
		c.requestFocus();
		GameThread gt = new GameThread(w, input, d, new Controls(d, w, input), c.getBufferStrategy());
		new Thread(gt, "Game Thread").start();
    }
}
