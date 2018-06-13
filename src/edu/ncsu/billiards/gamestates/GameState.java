package edu.ncsu.billiards.gamestates;

import edu.ncsu.billiards.Billiards;

import org.newdawn.slick.Graphics;

public interface GameState {

	void update(double elapsedTime);

	void render(Graphics g);




	void enter(Billiards game);



	void mouseClicked(int button, float x, float y, int clickCount);
	void mouseDragged(float oldx, float oldy, float newx, float newy);
	void mouseMoved(float oldx, float oldy, float newx, float newy);
	void mousePressed(int button, float x, float y);
	void mouseReleased(int button, float x, float y);
}
