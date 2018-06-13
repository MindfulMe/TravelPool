package edu.ncsu.billiards.setups;

import edu.ncsu.billiards.gameobjects.Cushion;
import edu.ncsu.billiards.gameobjects.Pocket;
import edu.ncsu.billiards.gameobjects.PoolBall;

import java.util.ArrayList;

import org.dyn4j.geometry.Vector2;

import org.newdawn.slick.Color;

public class GlancingBlowSetup extends Setup {
	public ArrayList<Pocket> generatePockets() {
		ArrayList<Pocket> pockets = new ArrayList<Pocket>();

		Pocket pocket1 = new Pocket(1, .317f, new Vector2(-1, 0));
		Pocket pocket2 = new Pocket(2, .617f, new Vector2(-1, -.2));

		pocket1.setDestination(pocket2);
		pocket1.setTimeDifference(-0.5);

		pocket2.setDestination(pocket1);
		pocket2.setTimeDifference(-0.5);

		pockets.add(pocket1);
		pockets.add(pocket2);

		return pockets;
	}

	public ArrayList<PoolBall> generateBalls() {
		ArrayList<PoolBall> balls = new ArrayList<PoolBall>();

		PoolBall ball = new PoolBall(.738f, .317f, Color.black);

		balls.add(ball);

		return balls;
	}
}
