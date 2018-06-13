package ivanov.ncsu.billiards.gamestates;

import ivanov.ncsu.billiards.Billiards;
import ivanov.ncsu.billiards.Renderer;

import ivanov.ncsu.billiards.gamestates.SimulationState;

import ivanov.ncsu.billiards.setups.GlancingBlowSetup;

import ivanov.ncsu.billiards.ui.Button;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MenuState implements GameState {
	private Image menuBackground;

	private int windowWidth;
	private int windowHeight;

	private Billiards game;

	private InputHandler inputHandler;

	private Button glancingBlowButton;
	
	private Button exitButton;

	private ArrayList<Button> buttons;


	public MenuState(int windowWidth, int windowHeight) throws SlickException {
		menuBackground = new Image("res/menu-bg.png");

		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;

		inputHandler = new InputHandler();

		setupButtons();
	}

	private void setupButtons() {
		glancingBlowButton = new Button("// PLAY!");
	
		exitButton = new Button("// EXIT GAME!");

		buttons = new ArrayList<Button>();

		buttons.add(glancingBlowButton);
		
		buttons.add(exitButton);

		// 10 pixels of vertical space between buttons
		int margin = 10;

		// height of all buttons plus margin
		int totalButtonHeight = margin * (buttons.size() - 1);
		for (Button button : buttons) {
			totalButtonHeight = totalButtonHeight + button.getHeight();
		}

		int verticalOffset = (windowHeight - totalButtonHeight) / 2;

		for (int i = 0; i < buttons.size(); i++) {
			Button button = buttons.get(i);
			
			button.setX((windowWidth - button.getWidth()));
			button.setY(verticalOffset);

			verticalOffset = verticalOffset + button.getHeight() + margin;
		}
	}

	public void update(double delta) {
		
	}

	public void render(Graphics graphics) {
		Renderer.render(menuBackground, 0, 0, graphics);

		for (Button button : buttons) {
			Renderer.render(button, graphics);
		}
	}

	public void enter(Billiards game) {
		this.game = game;
	}

	public void mouseClicked(int button, float x, float y, int clickCount) {
		inputHandler.mouseClicked(button, x, y, clickCount);
	}

	public void mouseDragged(float oldx, float oldy, float newx, float newy) {
		inputHandler.mouseDragged(oldx, oldy, newx, newy);
	}

	public void mouseMoved(float oldx, float oldy, float newx, float newy) {
		inputHandler.mouseMoved(oldx, oldy, newx, newy);
	}

	public void mousePressed(int button, float x, float y) {
		inputHandler.mousePressed(button, x, y);
	}

	public void mouseReleased(int button, float x, float y) {
		inputHandler.mouseReleased(button, x, y);
	}

	private class InputHandler {
		public void mouseDragged(float oldX, float oldY,
		                         float newX, float newY) {
		}

		public void mousePressed(int button, float x, float y) {
		}

		public void mouseReleased(int button, float x, float y) {
		}

		public void mouseClicked(int button, float x, float y, int clickCount) {
			if (glancingBlowButton.getHitBox().contains(x, y)) {
				game.changeState(new SimulationState(new GlancingBlowSetup()));

			} else if (exitButton.getHitBox().contains(x, y)) {
				System.exit(0);
			}
		}

		public void mouseMoved(float oldx, float oldy, float newx, float newy) {
		}
	}
}
