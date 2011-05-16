package com.metalbeetle.koboldpit;

public class Controls {
	Display d;
	GameWorld w;
	Input input;

	public Controls(Display d, GameWorld w, Input input) {
		this.d = d;
		this.w = w;
		this.input = input;
	}

	public void processInput() {
		if (input.mouse != null) {
			w.cursorX = input.mouse.x / 10;
			w.cursorY = input.mouse.y / 10;
		}
		if (input.click != null) {
			w.intro = false;
		}
	}
}
