package edu.ncsu.billiards.world;

import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.dynamics.contact.ContactListener;

public class PredictionWorld extends BilliardsWorld {
	public PredictionWorld(ContactListener predictionContactHandler) {
		this.addListener(predictionContactHandler);
	}

	public void runSimulation(GameWorld gameWorld) {

		clearPockets();
		ArrayList<Pocket> gameWorldPockets = gameWorld.getPockets();
		while (!gameWorldPockets.isEmpty()) {
			Pocket pocket = gameWorldPockets.remove(0);
			gameWorld.removePocket(pocket);
			addPocket(pocket);
		}

		sync(gameWorld);

		// update until all bodies in this prediction world are asleep
		while (hasMovingBalls()) {
			this.update(1.0 / 60.0);
		}

		gameWorld.clearPockets();
		ArrayList<Pocket> predictionWorldPockets = getPockets();
		while (!predictionWorldPockets.isEmpty()) {
			Pocket pocket = predictionWorldPockets.get(0);
			removePocket(pocket);
			gameWorld.addPocket(pocket);
		}
	}

	private void sync(GameWorld gameWorld) {
		clearCurrentBalls();

		// copy balls from gameWorld to this world
		for (PoolBall ball : gameWorld.getCurrentBalls()) {
			PoolBall newBall = new PoolBall((float) ball.getWorldCenter().x,
			                                (float) ball.getWorldCenter().y,
			                                        ball.getColor());

			newBall.setAngularVelocity(ball.getAngularVelocity());
			newBall.setLinearVelocity(ball.getLinearVelocity());

			newBall.applyForce(ball.getAccumulatedForce());
			newBall.applyTorque(ball.getAccumulatedTorque());

			addCurrentBall(newBall);
		}

		setTime(gameWorld.getTime());
	}
}
